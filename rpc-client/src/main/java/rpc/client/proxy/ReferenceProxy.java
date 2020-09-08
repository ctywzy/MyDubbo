package rpc.client.proxy;

import com.google.common.collect.Lists;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import rpc.client.proxy.context.ProxyContext;
import rpc.client.support.id.impl.Uuid;
import rpc.client.support.time.impl.DefaultSystemTime;
import rpc.common.domain.RpcResponse;
import rpc.common.domain.impl.DefaultRpcRequest;
import rpc.common.stream.StreamConvert;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Objects;

/**
 * 核心流程如下：
 *
 * （1）根据 proxyContext 构建 rpcRequest
 *
 * （2）将 rpcRequest 写入到服务端
 *
 * （3）同步等待服务端响应。
 * @Description
 * @Author wangzy
 * @Date 2020/9/4 11:11 上午
 **/

@Slf4j
public class ReferenceProxy<T> implements InvocationHandler {

    /**
     * 服务标识
     * @since 0.0.6
     */
    private final ProxyContext<T> proxyContext;

    /**
     * 暂时私有化该构造器
     * @param proxyContext 代理上下文
     * @since 0.0.6
     */
    public ReferenceProxy(ProxyContext<T> proxyContext) {
        this.proxyContext = proxyContext;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 反射信息处理成为 rpcRequest
        final String seqId = Uuid.getInstance().id();
        final long createTime = DefaultSystemTime.getInstance().time();
        DefaultRpcRequest request = new DefaultRpcRequest();
        request.serviceId(proxyContext.serviceId());
        request.seqId(seqId);
        request.createTime(createTime);
        request.paramValues(args);
        request.paramTypeNames(getParamTypeNames(method));
        request.methodName(method.getName());

        // 调用远程
        log.info("[Client] start call remote with request: {}", request.toString());
        proxyContext.invokeService().addRequest(seqId);

        // 这里使用 load-balance 进行选择 channel 写入。
        final Channel channel = getChannel();
        log.info("[Client] start call channel id: {}", channel.id().asLongText());

        // 对于信息的写入，实际上有着严格的要求。
        // writeAndFlush 实际是一个异步的操作，直接使用 sync() 可以看到异常信息。
        // 支持的必须是 ByteBuf
        /**
         * 一定要有信息写入的操作，服务端才能收到消息
         */
        channel.writeAndFlush(StreamConvert.objectToBytes(request)).sync();

        // 循环获取结果
        // 通过 Loop+match  wait/notifyAll 来获取
        // 分布式根据 redis+queue+loop
        log.info("[Client] start get resp for seqId: {}", seqId);
        RpcResponse response = proxyContext.invokeService().getResponse(seqId);
        log.info("[Client] start get resp for seqId: {}", seqId);
        Throwable error = response.error();
        if(Objects.nonNull(error)) {
            throw error;
        }
        return response.result();
    }

    /**
     * 获取对应的 channel
     * （1）暂时使用写死的第一个
     * （2）后期这里需要调整，ChannelFuture 加上权重信息。
     * @return 对应的 channel 信息。
     * @since 0.0.6
     */
    private Channel getChannel() {
        return proxyContext.channelFutures().get(0).channel();
    }


    private List<String> getParamTypeNames(Method method) {
        Class<?>[] paramTypes = method.getParameterTypes();
        List<String> params = Lists.newArrayList();
        for(Class<?> param : paramTypes){
            params.add(param.getName());
        }
        return params;
    }
    /**
     * 这里是直接使用 java 动态代理实现的。
     * 是服务端代理创建的核心实现。
     * 获取代理实例
     *（1）接口只是为了代理。
     *（2）实际调用中更加关心 的是 serviceId
     * @param proxyContext 代理上下文
     * @param <T> 泛型
     * @return 代理实例
     * @since 0.0.6
     */
    @SuppressWarnings("unchecked")
    public static <T> T newProxyInstance(ProxyContext<T> proxyContext) {

        // 获取服务接口
        final Class<T> interfaceClass = proxyContext.serviceInterface();

        // 根据服务接口Class字节码文件获取类加载器
        ClassLoader classLoader = interfaceClass.getClassLoader();


        Class<?>[] interfaces = new Class[]{interfaceClass};


        ReferenceProxy proxy = new ReferenceProxy(proxyContext);

        //返回一个代理对象
        return (T) Proxy.newProxyInstance(classLoader, interfaces, proxy);

    }

}
