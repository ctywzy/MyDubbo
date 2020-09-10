package rpc.client.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import rpc.client.invoke.InvokeService;
import rpc.common.domain.RpcResponse;
import rpc.common.model.CalculateResponse;
import rpc.common.stream.StreamConvert;

/**
 * @Description 客户端请求处理类
 * @Author wangzy
 * @Date 2020/9/2 1:59 下午
 **/
@Slf4j
public class RpcClientHandler extends SimpleChannelInboundHandler {

    RpcResponse response;

    private InvokeService invokeService;

    public RpcClientHandler(InvokeService invokeService) {
        this.invokeService = invokeService;
    }

    /**
     * @description 这个方法是收到消息时改如何处理
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        //解析ByteBuf对象
        response = StreamConvert.bytesToObject(msg);
        //
        invokeService.addResponse(response.seqId(), response);
        log.info("[Client] response is :{}", response);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //每次用完都要关闭，否则拿不到resposne，可能是不关闭会存在堵塞问题
        ctx.close();
        ctx.flush();
    }


}
