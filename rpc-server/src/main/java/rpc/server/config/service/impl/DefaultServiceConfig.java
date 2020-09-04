package rpc.server.config.service.impl;

import rpc.server.config.service.ServiceConfig;

public class DefaultServiceConfig<T> implements ServiceConfig<T> {

    /**
     * 服务的唯一标识
     * @since feature/0.0.6
     */
    private String id;

    /**
     * 设置引用类
     * @since feature/0.0.6
     */
    private T reference;


    @Override
    public ServiceConfig<T> id(String serviceId) {
        this.id = serviceId;
        return this;
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public ServiceConfig<T> reference(T reference) {
        this.reference = reference;
        return this;
    }

    @Override
    public T reference() {
        return this.reference;
    }
}
