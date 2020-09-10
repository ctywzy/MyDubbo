package rpc.client.proxy.context.impl;

import io.netty.channel.ChannelFuture;
import rpc.client.invoke.InvokeService;
import rpc.client.proxy.context.ProxyContext;

import java.util.List;

public class DefaultProxyContext<T> implements ProxyContext<T> {

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
     * channel future 信息
     *
     * @since 0.0.6
     */
    private List<ChannelFuture> channelFutures;

    /**
     * channel handler 信息
     *
     * @since 0.0.6
     */
    private InvokeService invokeService;

    /**
     * 超时时间
     * @since feature/0.0.7
     */
    private Long timeout;

    @Override
    public String serviceId() {
        return serviceId;
    }

    public DefaultProxyContext<T> serviceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    @Override
    public Class<T> serviceInterface() {
        return serviceInterface;
    }

    public DefaultProxyContext<T> serviceInterface(Class<T> serviceInterface) {
        this.serviceInterface = serviceInterface;
        return this;
    }

    @Override
    public List<ChannelFuture> channelFutures() {
        return channelFutures;
    }

    public DefaultProxyContext<T> channelFutures(List<ChannelFuture> channelFutures) {
        this.channelFutures = channelFutures;
        return this;
    }

    @Override
    public InvokeService invokeService() {
        return invokeService;
    }

    @Override
    public Long timeout() {
        return this.timeout;
    }

    public DefaultProxyContext<T> timeout(Long timeoutMills) {
        this.timeout = timeoutMills;
        return this;
    }
    public DefaultProxyContext<T> invokeService(InvokeService invokeService) {
        this.invokeService = invokeService;
        return this;
    }
}
