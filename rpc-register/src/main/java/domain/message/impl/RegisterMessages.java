package domain.message.impl;

import com.google.common.collect.Lists;
import domain.entry.ServiceEntry;
import domain.message.RegisterMessage;
import rpc.common.support.id.impl.Uuid;

import java.util.List;

/**
 * @Description 注册消息工具类
 * @Author wangzy
 * @Date 2020/9/17 7:21 下午
 **/
public class RegisterMessages {

    /**
     * 初始化消息信息
     * @param type 类型
     * @param body 消息体
     * @return 注册消息
     * @since 0.0.8
     */
    public static RegisterMessage of(int type, List<ServiceEntry> body) {
        String seqId = Uuid.getInstance().id();
        return of(type, seqId, body);
    }

    /**
     * 初始化消息信息
     * @param type 类型
     * @param seqId 消息标识
     * @param body 消息体
     * @return 注册消息
     * @since 0.0.8
     */
    public static RegisterMessage of(int type, String seqId, List<ServiceEntry> body){

        DefaultRegisterMessage registerMessage = new DefaultRegisterMessage();
        DefaultRegisterMessageHeader registerMessageHeader = new DefaultRegisterMessageHeader();

        registerMessageHeader.type(type);

        registerMessage.header(registerMessageHeader);

        registerMessage.body(body);

        registerMessage.seqId(seqId);

        return registerMessage;
    }
}
