package rpc.client.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import rpc.common.model.CalculateResponse;

import java.util.List;

/**
 * @Description 请求参数解码类
 * @Author wangzy
 * @Date 2020/9/2 11:54 上午
 **/
public class ResponseDecode extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Boolean success = in.readBoolean();
        Long sum = in.readLong();
        CalculateResponse response = new CalculateResponse(success, sum);
        out.add(response);
    }
}
