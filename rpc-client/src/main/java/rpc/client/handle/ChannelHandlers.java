package rpc.client.handle;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;

/**
 * @Description 通讯工具类
 * @Author wangzy
 * @Date 2020/9/18 4:41 下午
 **/
public class ChannelHandlers {

    /**
     * 设置通道对连接后的请求处理
     * @param channelHandlers
     * @return
     */
    public static ChannelHandler objectCodecHandler(ChannelHandler ... channelHandlers) {

        return new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline()
                  .addLast(channelHandlers);
            }
        };
    }
}
