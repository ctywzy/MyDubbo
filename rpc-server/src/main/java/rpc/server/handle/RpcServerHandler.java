package rpc.server.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import rpc.common.model.CalculateRequest;
import rpc.common.model.CalculateResponse;
import rpc.common.service.Calculator;
import rpc.server.service.CalculatorImpl;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

/**
 * @Description 服务端请求处理类
 * @Author wangzy
 * @Date 2020/8/31 4:51 下午
 **/
@Slf4j
public class RpcServerHandler extends SimpleChannelInboundHandler {

    ObjectMapper mapper = new ObjectMapper();
    /**
     * @description 通信通道建立时会触发的动作
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        final String id = ctx.channel().id().asLongText();
        log.info("[Server] channel : {} connected " , id);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        final String id = ctx.channel().id().asLongText();

        ByteBuf byteBuf = (ByteBuf)msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        CalculateRequest request = (CalculateRequest) objectInputStream.readObject();
        log.info("[Server] receive channel : {} request: {} from ", id, request);

        Calculator calculator = new CalculatorImpl();

        CalculateResponse response = calculator.sum(request);

        //回写到client客户端
        ctx.writeAndFlush(response);
        log.info("[Server] channel {} response {}", id, response);
    }
}
