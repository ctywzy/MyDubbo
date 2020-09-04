package rpc.client.core.context.impl;

import io.netty.channel.ChannelHandler;
import rpc.client.core.context.RpcClientContext;

public class DefaultRpcClientContext implements RpcClientContext {

    /**
     * 服务地址信息
     * @since 0.0.6
     */
    private String address;

    /**
     * 端口信息
     * @since 0.0.6
     */
    private int port;

    /**
     * 客户端处理 handler
     * @since 0.0.6
     */
    private ChannelHandler channelHandler;


    @Override
    public String address() {
        return address;
    }

    public DefaultRpcClientContext address(String address) {
        this.address = address;
        return this;
    }

    @Override
    public int port() {
        return port;
    }

    public DefaultRpcClientContext port(int port) {
        this.port = port;
        return this;
    }

    @Override
    public ChannelHandler channelHandler() {
        return channelHandler;
    }

    public DefaultRpcClientContext channelHandler(ChannelHandler channelHandler){
        this.channelHandler = channelHandler;
        return this;
    }
}
