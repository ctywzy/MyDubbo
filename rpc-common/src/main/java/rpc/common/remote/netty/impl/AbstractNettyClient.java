package rpc.common.remote.netty.impl;

import io.netty.channel.ChannelHandler;
import rpc.common.remote.netty.NettyClient;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Description 网络服务端
 * @Author wangzy
 * @Date 2020/9/22 7:27 下午
 **/
public class AbstractNettyClient<T> implements NettyClient<T> {

    /**
     * ip 信息
     * @since 0.0.8
     */
    protected String ip;

    /**
     * 端口信息
     * @since 0.0.8
     */
    protected int port;

    /**
     * channel handler
     * @Since 0.0.8
     */
    protected ChannelHandler channelHandler;

    public AbstractNettyClient(String ip, int port, ChannelHandler channelHandler) {
        this.ip = ip;
        this.port = port;
        this.channelHandler = channelHandler;
    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public T call() throws Exception {
        return null;
    }
}
