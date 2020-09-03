package rpc.client.handle;

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
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        response = (CalculateResponse)msg;
        log.info("[Client] response is :{}", response);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //每次
        ctx.close();
        ctx.flush();
    }

    public CalculateResponse getResponse() {
        return response;
    }
}
