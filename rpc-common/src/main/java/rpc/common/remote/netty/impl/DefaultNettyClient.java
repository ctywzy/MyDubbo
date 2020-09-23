package rpc.common.remote.netty.impl;

import com.google.common.base.Throwables;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import rpc.common.exception.RpcRuntimeException;

/**
 * @Description 网络客户端
 * @Author wangzy
 * @Date 2020/9/22 7:34 下午
 **/
@Slf4j
public class DefaultNettyClient extends AbstractNettyClient<ChannelFuture>{

    /**
     * 工作线程池
     */
    private EventLoopGroup workerGroup;

    /**
     * 信息
     */
    private ChannelFuture channelFuture;


    public DefaultNettyClient(String ip, int port, ChannelHandler channelHandler) {
        super(ip, port, channelHandler);
    }

    /**
     * 创建新的对象实例
     * @return 对象实例
     */
    public static DefaultNettyClient newInstance(String ip, int port,
                                                 ChannelHandler channelHandler) {
        return new DefaultNettyClient(ip, port, channelHandler);
    }

    /**
     * 客户端的启动方法
     * @return
     */
    @Override
    public ChannelFuture call() {
        // 启动服务端
        log.info("[Netty Client] 开始启动客户端");

        workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            channelFuture = bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(channelHandler)
                    .connect(ip, port)
                    .syncUninterruptibly();
            log.info("[Netty Client] 启动客户端完成，监听地址 {}:{}", ip, port);
        } catch (Exception e) {
            log.error("[Netty Client] 端启动遇到异常 : {}", Throwables.getStackTraceAsString(e));
            throw new RpcRuntimeException(e);
        }
        // 不要关闭线程池！！！
        return channelFuture;
    }

    @Override
    public void destroy() {
        try {
            channelFuture.channel().closeFuture().syncUninterruptibly();
            log.info("[Netty Client] 关闭完成");
        } catch (Exception e){
            log.error("[Netty Client] 关闭服务异常 : {}", Throwables.getStackTraceAsString(e));
            throw new RpcRuntimeException("e");
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
