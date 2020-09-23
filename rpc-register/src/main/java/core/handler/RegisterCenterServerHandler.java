package core.handler;

import domain.entry.ServiceEntry;
import domain.message.RegisterMessage;
import domain.message.impl.RegisterMessages;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import rpc.common.constant.MessageTypeConst;
import rpc.common.stream.StreamConvert;
import spi.RpcRegister;

/**
 * @Description 注册中心接收到请求后的处理器
 * @Author wangzy
 * @Date 2020/9/15 4:21 下午
 **/

@Slf4j
public class RegisterCenterServerHandler extends SimpleChannelInboundHandler {

    /**
     * 服务注册中心
     * 0.0.8
     */
    private RpcRegister rpcRegister;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        final String id = ctx.channel().id().asLongText();
        log.info("[Register Server] channel {} connected " + id);
    }

    /**
     * 通道中收到消息进行处理
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        RegisterMessage registerMessage = StreamConvert.bytesToObject(msg);

        Object body = registerMessage.body();
        int type = RegisterMessages.type(registerMessage);
        String seqId = registerMessage.seqId();
        log.info("[Register Server] received message type: {}, seqId: {} ", type,
                seqId);

        Channel channel = ctx.channel();
        ServiceEntry serviceEntry = (ServiceEntry)body;

        switch (type){
            case MessageTypeConst.SERVER_REGISTER:
                rpcRegister.register(serviceEntry);
                break;

            case MessageTypeConst.SERVER_UN_REGISTER:
                rpcRegister.unRegister(serviceEntry);
                break;

            case MessageTypeConst.CLIENT_SUBSCRIBE:
                rpcRegister.subscribe(serviceEntry, channel);
                break;

            case MessageTypeConst.CLIENT_UN_SUBSCRIBE:
                rpcRegister.unSubscribe(serviceEntry, channel);
                break;

            case MessageTypeConst.CLIENT_LOOK_UP:
                rpcRegister.lookUp(seqId, serviceEntry, channel);
                break;
            default:
                log.warn("[Register Center] not support type: {} and seqId: {}",
                        type, seqId);
        }
    }
}
