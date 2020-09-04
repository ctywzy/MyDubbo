package rpc.client.proxy;

import rpc.client.proxy.context.ProxyContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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
        return null;
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
