package rpc.client.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import rpc.common.model.CalculateRequest;

import java.util.List;

/**
 * @Description 请求参数编码类
 * @Author wangzy
 * @Date 2020/9/2 1:58 下午
 **/
public class RequestEncode extends MessageToByteEncoder<CalculateRequest> {

    @Override
    protected void encode(ChannelHandlerContext ctx, CalculateRequest msg, ByteBuf out) throws Exception {
        Long one = msg.getOne();
        Long two = msg.getTwo();
        out.writeLong(one);
        out.writeLong(two);
    }
}
