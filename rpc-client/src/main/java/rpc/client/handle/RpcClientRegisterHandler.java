package rpc.client.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import rpc.client.invoke.InvokeService;

/**
 * @Description 客户端与注册中心通讯处理器
 * @Author wangzy
 * @Date 2020/9/18 4:37 下午
 **/
public class RpcClientRegisterHandler extends SimpleChannelInboundHandler {


    private InvokeService invokeService;

    public RpcClientRegisterHandler(InvokeService invokeService) {
        this.invokeService = invokeService;
    }

    /**
     * 收到消息的处理类
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

    }
}
