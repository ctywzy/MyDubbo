package rpc.client.config.reference.impl;

import com.google.common.collect.Lists;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.util.internal.StringUtil;
import rpc.client.config.reference.ReferenceConfig;
import rpc.client.core.RpcClient;
import rpc.client.core.context.impl.DefaultRpcClientContext;
import rpc.client.handle.RpcClientHandler;
import rpc.client.invoke.InvokeService;
import rpc.client.invoke.impl.DefaultInvokeService;
import rpc.client.proxy.ReferenceProxy;
import rpc.client.proxy.context.ProxyContext;
import rpc.client.proxy.context.impl.DefaultProxyContext;
import rpc.common.config.component.RpcAddress;

import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Objects;

/**
 * 引用配置类
 *
 * 后期配置：
 * （1）timeout 调用超时时间
 * （2）version 服务版本处理
 * （3）callType 调用方式 oneWay/sync/async
 * （4）check 是否必须要求服务启动。
 *
 * spi:
 * （1）codec 序列化方式
 * （2）netty 网络通讯架构
 * （3）load-balance 负载均衡
 * （4）失败策略 fail-over/fail-fast
 *
 * filter:
 * （1）路由
 * （2）耗时统计 monitor 服务治理
 *
 * 优化思考：
 * （1）对于唯一的 serviceId，其实其 interface 是固定的，是否可以省去？
 * @author binbin.hou
 * @since 0.0.6
 * @param <T> 接口泛型
 * @Author wangzy
 * @Date 2020/9/4 9:32 上午
 **/
public class DefaultReferenceConfig<T> implements ReferenceConfig<T> {

    /**
     * 服务唯一标识
     * @since 0.0.6
     */
    private String serviceId;

    /**
     * 服务接口
     * @since 0.0.6
     */
    private Class<T> serviceInterface;

    /**
     * 服务地址信息
     *（1）如果不为空，则直接根据地址获取
     *（2）如果为空，则采用自动发现的方式
     *
     * TODO: 这里调整为 set 更加合理。
     *
     * 如果为 subscribe 可以自动发现，然后填充这个字段信息。
     * @since 0.0.6
     */
    private List<RpcAddress> rpcAddresses;

    /**
     * 用于写入信息
     *（1）client 连接 server 端的 channel future
     *（2）后期进行 Load-balance 路由等操作。可以放在这里执行。
     * @since 0.0.6
     */
    private List<ChannelFuture> channelFutures;

    /**
     * 服务接口
     */
    private InvokeService invokeService;

    public DefaultReferenceConfig(){
        this.rpcAddresses = Lists.newArrayList();
        this.channelFutures = Lists.newArrayList();
        this.invokeService = new DefaultInvokeService();
    }

    @Override
    public ReferenceConfig<T> serviceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    @Override
    public String serviceId() {
        return serviceId;
    }

    @Override
    public ReferenceConfig<T> serviceInterface(Class<T> serviceInterface) {
        this.serviceInterface = serviceInterface;
        return this;
    }

    @Override
    public Class<T> serviceInterface() {
        return serviceInterface;
    }

    @Override
    public ReferenceConfig<T> addresses(String address, int port) {
        if(StringUtil.isNullOrEmpty(address)){

        }
        RpcAddress rpcAddress = new RpcAddress(address, port, 1);

        rpcAddresses.add(rpcAddress);

        return this;
    }


    /**
     * 获取对应的引用实现
     * （1）处理所有的反射代理信息-方法可以抽离，启动各自独立即可。
     * （2）启动对应的长连接
     * @return 引用代理类
     * @since feature/0.0.6
     */
    @Override
    public T reference() {

        // 1. 启动 client 端到 server 端的连接信息
        // 1.1 为了提升性能，可以将所有的 client=>server 的连接都调整为一个 thread。
        // 1.2 初期为了简单，直接使用同步循环的方式。
        // 创建 handler
        // 循环连接

        for(RpcAddress rpcAddress : rpcAddresses){
            final ChannelHandler channelHandler = new RpcClientHandler(invokeService);
            final DefaultRpcClientContext context = new DefaultRpcClientContext();
            context.address(rpcAddress.address()).port(rpcAddress.port()).channelHandler(channelHandler);
            ChannelFuture channelFuture = new RpcClient(context).connent();
            channelFutures.add(channelFuture);
        }
        // 2. 接口动态代理
        ProxyContext<T> proxyContext = buildReferenceProxyContext();

        return ReferenceProxy.newProxyInstance(proxyContext);
    }

    /**
     * 构建调用上下文
     * @return 引用代理上下文
     * @since feature/0.0.6
     */
    private ProxyContext<T> buildReferenceProxyContext(){
        DefaultProxyContext<T> proxyContext = new DefaultProxyContext<>();
        proxyContext.serviceId(this.serviceId)
                    .serviceInterface(this.serviceInterface)
                    .channelFutures(this.channelFutures)
                    .invokeService(this.invokeService);
        return proxyContext;
        
    }
}
