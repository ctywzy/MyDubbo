package rpc.server.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import rpc.common.domain.RpcRequest;
import rpc.common.domain.impl.DefaultRpcResponse;
import rpc.common.stream.StreamConvert;
import rpc.server.service.impl.DefaultServiceFactory;


/**
 * @Description 服务端请求处理类
 * @Author wangzy
 * @Date 2020/8/31 4:51 下午
 **/
@Slf4j
public class RpcServerHandler extends SimpleChannelInboundHandler {

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

    /**
     * @description 这个方法是
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        final String id = ctx.channel().id().asLongText();

        RpcRequest request = StreamConvert.bytesToObject(msg);
        log.info("[Server] receive channel : {} request: {} from ", id, request.toString());

        DefaultRpcResponse response = handleRpcRequest(request);
        //回写到client客户端
        ctx.writeAndFlush(StreamConvert.objectToBytes(response));

        log.info("[Server] channel {} response {}", id, response);
    }

    /**
     * 处理请求信息
     * @param request 请求信息
     * @return 结果信息
     */
    private DefaultRpcResponse handleRpcRequest(final RpcRequest request){

        DefaultRpcResponse response = new DefaultRpcResponse();
        response.seqId(request.seqId());

        try {
            // 获取对应的 service 实现类
            // rpcRequest=>invocationRequest
            // 执行 invoke
            Object result = DefaultServiceFactory.getInstance()
                                                 .invoke(request.serviceId(),
                                                         request.methodName(),
                                                         request.paramTypeNames(),
                                                         request.paramValues());
            response.result(result);
        } catch (Exception e) {
            response.error(e);
            log.error("[Server] execute meet ex for request", request, e);
        }

        // 构建结果值
        return response;

    }
}
