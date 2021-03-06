package rpc.client.core.context;

import io.netty.channel.ChannelHandler;

public interface RpcClientContext {

    /**
     * 服务地址信息
     * @return 服务地址信息
     * @since 0.0.6
     */
    String address();

    /**
     * 端口信息
     * @return 端口信息
     * @since 0.0.6
     */
    int port();

    /**
     * 客户端处理 handler
     * @since 0.0.6
     * @return handler
     */
    ChannelHandler channelHandler();
}
