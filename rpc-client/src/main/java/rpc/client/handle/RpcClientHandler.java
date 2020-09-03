package rpc.client.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import rpc.common.model.CalculateRequest;
import rpc.common.model.CalculateResponse;

/**
 * @Description 客户端请求处理类
 * @Author wangzy
 * @Date 2020/9/2 1:59 下午
 **/
@Slf4j
public class RpcClientHandler extends SimpleChannelInboundHandler {

    CalculateResponse response;

    ObjectMapper mapper = new ObjectMapper();
    /**
     * @description 这个方法是收到消息时改如何处理
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        //先把msg转换为ByteBuf对象
        ByteBuf byteBuf = (ByteBuf)msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);

        response = mapper.convertValue(byteBuf, CalculateResponse.class);
        log.info("[Client] response is :{}", response);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //每次用完都要关闭，否则拿不到resposne，可能是不关闭会存在堵塞问题
        ctx.close();
        ctx.flush();
    }

    public CalculateResponse getResponse() {
        return response;
    }
}
