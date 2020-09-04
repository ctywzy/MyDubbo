package rpc.server.config.service;

public interface ServiceConfig<T> {

    /**
     * 唯一的服务标示服务id
     * @param serviceId
     * @return this
     */
    ServiceConfig<T> id(String serviceId);

    /**
     * 获取服务id
     * @return
     */
    String id();


    /**
     * 设置引用实体实现
     * @param reference 引用实现
     * @return this
     */
    ServiceConfig<T> reference(T reference);

    /**
     * 获取引用类
     * @return
     */
    T reference();
}
