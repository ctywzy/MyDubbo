package rpc.server.core;

import com.google.common.base.Throwables;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import rpc.common.constant.RpcConstant;
import rpc.server.handle.RpcServerHandler;

/**
 * @Description 服务启动类
 * @Author wangzy
 * @Date 2020/8/26 5:50 下午
 **/

@Slf4j
public class RpcServer extends Thread{

    private final int port;

    public RpcServer() {
        this.port = RpcConstant.PORT;
    }


    @Override
    public void run() {
        log.info("RPC服务端开始启动");

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(workerGroup, bossGroup)
                     .channel(NioServerSocketChannel.class)
                     .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline()
                                .addLast(new RpcServerHandler());

                         }
                     })
                     // 这个参数影响的是还没有被accept 取出的连接
                     .option(ChannelOption.SO_BACKLOG, 128)
                     // 这个参数只是过一段时间内客户端没有响应，服务端会发送一个 ack 包，以判断客户端是否还活着。
                     .childOption(ChannelOption.SO_KEEPALIVE, true);

            // 绑定端口，开始接收进来的链接
            ChannelFuture channelFuture = bootstrap.bind(port).syncUninterruptibly();
            /**
             * netty中的操作都是异步的，会返回一个ChannelFuture，调用者不会立刻获得结果，
             */
            log.info("RPC 服务端启动完成，监听【" + port + "】端口");

            channelFuture.channel().closeFuture().syncUninterruptibly();
            log.info("RPC 服务端关闭完成");
        } catch (Exception e) {
            log.error("RPC 服务异常", Throwables.getStackTraceAsString(e));
        } finally {
            workerGroup. shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
