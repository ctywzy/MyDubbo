package rpc.client.config.reference;

public interface ReferenceConfig<T> {

    /**
     * 设置服务标识
     * @param serviceId 服务标识
     * @return this
     * @since 0.0.6
     */
    ReferenceConfig<T> serviceId(final String serviceId);

    /**
     * 服务唯一标识
     * @since 0.0.6
     */
    String serviceId();

    /**
     * 服务接口
     * @since 0.0.6
     * @return 接口信息
     */
    Class<T> serviceInterface();

    /**
     * 设置服务接口信息
     * @param serviceInterface 服务接口信息
     * @return this
     * @since 0.0.6
     */
    ReferenceConfig<T> serviceInterface(final Class<T> serviceInterface);

    /**
     * 设置服务地址信息
     * （1）单个写法：ip:port:weight
     * （2）集群写法：ip1:port1:weight1,ip2:port2:weight2
     *
     * 其中 weight 权重可以不写，默认为1.
     *
     * @param address 地址列表信息
     * @param port 端口号
     * @return this
     * @since 0.0.6
     */
    ReferenceConfig<T> addresses(String address, int port);

    /**
     * 获取对应的引用实现
     * @return 引用代理类
     * @since feature/0.0.6
     */
     T reference();

    /**
     * 设置超时时间
     * @return this
     */
    ReferenceConfig<T> timeout(Long timeoutMills);

    /**
     * 是否订阅发现服务
     * 注意：如果指定 {@link #addresses(String,int)} 信息，这个属性将会失效。
     * @param subscribe 是否订阅模式
     * @since 0.0.8
     * @return this
     */
    ReferenceConfig<T> subscribe(final boolean subscribe);

    /**
     * 注册中心地址
     * （1）正常使用中，这个属性是可以固定写死，对使用者不可见的。
     * @since 0.0.8
     * @return this
     */
    ReferenceConfig<T> registerCenter(final String addresses);
}
