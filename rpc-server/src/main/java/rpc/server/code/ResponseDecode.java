package rpc.server.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import rpc.common.model.CalculateRequest;

import java.util.List;

/**
 * @Description 响应参数解码类
 * @Author wangzy
 * @Date 2020/9/2 2:03 下午
 **/
public class ResponseDecode extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //获取流中的字节对象
        Long one = in.readLong();
        Long two = in.readLong();
        CalculateRequest request = new CalculateRequest(one, two);
        out.add(request);
    }
}
