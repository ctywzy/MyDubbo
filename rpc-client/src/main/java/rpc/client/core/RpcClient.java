package rpc.client.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import rpc.client.code.ResponseDecode;
import rpc.client.code.RequestEncode;
import rpc.client.handle.RpcClientHandler;
import rpc.common.constant.RpcConstant;
import rpc.common.model.CalculateRequest;
import rpc.common.model.CalculateResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @Description netty客户端类
 * @Author wangzy
 * @Date 2020/9/2 1:59 下午
 **/
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

    /**
     * 信息
     */
    private ChannelFuture channelFuture;

    /**
     * 客户端处理
     */
    private RpcClientHandler channelHandler;

    /**
     * 序列化工具对象
     */
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void start() {

        // 启动客户端
        log.info("RPC 服务开始启动客户端");

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            Bootstrap bootStrap = new Bootstrap();
            channelFuture = bootStrap.group(workerGroup)
                                     .channel(NioSocketChannel.class)
                                     .option(ChannelOption.SO_KEEPALIVE, true)
                                     .handler(new ChannelInitializer<Channel>() {
                                         @Override
                                         protected void initChannel(Channel ch) throws Exception {
                                             channelHandler = new RpcClientHandler();
                                             ch.pipeline()
                                                 .addLast(new LoggingHandler(LogLevel.INFO))
//                                                 .addLast(new ResponseDecode())
//                                                 .addLast(new RequestEncode())
                                                 .addLast(channelHandler);
                                         }
                                     })
                                     .connect(RpcConstant.ADDRESS, port)
                                     .syncUninterruptibly();
            log.info("RPC 服务启动客户端完成，监听端口：" + port);
        } catch (Exception e){
            log.error("RPC 客户端遇到异常:{}", Throwables.getStackTraceAsString(e));
        }//去掉线程池的关闭
    }

    /**
     * 可被调用的计算方法,由自动连接改为自己控制连接
     * 每调用一次
     */
    public CalculateResponse calculate(CalculateRequest request) throws IOException {
        //发起请求
        Channel channel = channelFuture.channel();


        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream ops = new ObjectOutputStream(bos);
        ops.writeObject(request);

        byte[] bytes = bos.toByteArray();

        ByteBuf byteBuf = Unpooled.copiedBuffer(bytes);
        channel.writeAndFlush(byteBuf);

        channel.closeFuture().syncUninterruptibly();
        log.info("RPC 服务开始客户端已关闭");

        return channelHandler.getResponse();


    }
}
