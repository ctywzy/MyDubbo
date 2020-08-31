package rpc.client.core;

import com.google.common.base.Throwables;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import rpc.client.handle.RpcClientHandler;
import rpc.common.constant.RpcConstant;

@Slf4j
public class RpcClient extends Thread{

    /**
     * 监听端口号
     */
    private final int port;

    public RpcClient(int port) {
        this.port = port;
    }

    public RpcClient(){
        this(RpcConstant.PORT);
    }

    @Override
    public void run() {
        // 启动客户端

        log.info("RPC 服务开始启动客户端");

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            Bootstrap bootStrap = new Bootstrap();
            ChannelFuture channelFuture = bootStrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new LoggingHandler(LogLevel.INFO))
                                    .addLast(new RpcClientHandler());
                        }
                    })
                    .connect("localhost",port)
                    .syncUninterruptibly();
            log.info("RPC 服务启动客户端完成，监听端口：" + port);
            channelFuture.channel().closeFuture().syncUninterruptibly();
            log.info("RPC 服务开始客户端已关闭");
        } catch (Exception e){
            log.error("RPC 客户端遇到异常", Throwables.getStackTraceAsString(e));
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
