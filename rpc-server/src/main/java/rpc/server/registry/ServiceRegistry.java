package rpc.server.registry;

/**
 * 服务注册类
 *（1）每个应用唯一
 *（2）每个服务的暴露协议应该保持一致
 * 暂时不提供单个服务的特殊处理，后期可以考虑添加
 * @Author wangzy
 * @Date 2020/9/4 2:03 下午
 **/
public interface ServiceRegistry<T> {

    /**
     * 注册服务实现
     * @param serviceId 服务标识
     * @param serviceImpl 服务实现
     * @return this
     * @since 0.0.6
     */
    ServiceRegistry register(final String serviceId, final T serviceImpl);

    /**
     * 暴露所有服务信息
     * （1）启动服务端
     * @return this
     * @since 0.0.6
     */
    ServiceRegistry expose();
}
