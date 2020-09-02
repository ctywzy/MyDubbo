package rpc.server.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import rpc.common.model.CalculateResponse;

/**
 * @Description 响应参数编码类
 * @Author wangzy
 * @Date 2020/9/2 2:03 下午
 **/
public class ResponseEncode extends MessageToByteEncoder<CalculateResponse> {

    @Override
    protected void encode(ChannelHandlerContext ctx, CalculateResponse msg, ByteBuf out) throws Exception {

        Boolean success = msg.getSuccess();

        Long sum = msg.getSum();

        out.writeBoolean(success);
        out.writeLong(sum);
    }
}
