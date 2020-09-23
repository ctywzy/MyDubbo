package rpc.client.core;

import com.google.common.base.Throwables;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import rpc.client.core.context.RpcClientContext;
import rpc.common.constant.RpcConstant;

/**
 * @Description netty客户端类
 * @Author wangzy
 * @Date 2020/9/2 1:59 下午
 **/
@Slf4j
public class RpcClient extends Thread{

    /**
     * 地址信息
     */
    private final String address;

    /**
     * 监听端口号
     */
    private final int port;

    /**
     * 客户端处理 handler
     * 作用：用于获取请求信息
     */
    private final ChannelHandler channelHandler;

    public RpcClient(final RpcClientContext context){
        this.address = context.address();
        this.port = context.port();
        this.channelHandler = context.channelHandler();
    }

    public ChannelFuture connent() {

        // 启动客户端
        log.info("RPC 服务开始启动客户端");

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        /**
         * 用于写入请求信息
         */
        ChannelFuture channelFuture = null;
        try{
            Bootstrap bootStrap = new Bootstrap();
            channelFuture = bootStrap.group(workerGroup)
                                     .channel(NioSocketChannel.class)
                                     .option(ChannelOption.SO_KEEPALIVE, true)
                                     .handler(new ChannelInitializer<Channel>() {
                                         @Override
                                         protected void initChannel(Channel ch) throws Exception {
                                             //channelHandler = new RpcClientHandler();
                                             ch.pipeline()
                                                 .addLast(new LoggingHandler(LogLevel.INFO))
                                                 .addLast(channelHandler);
                                         }
                                     })
                                     .connect(address, port)
                                     .syncUninterruptibly();
            log.info("RPC 服务启动客户端完成，监听端口：" + port);
        } catch (Exception e){
            log.error("RPC 客户端遇到异常:{}", Throwables.getStackTraceAsString(e));
        }//去掉线程池的关闭

        return channelFuture;
    }

}
