package domain.message.impl;

import domain.message.RegisterMessage;
import domain.message.RegisterMessageHeader;
import rpc.common.domain.BaseRpc;

/**
 * @Description 默认注册消息
 * @Author wangzy
 * @Date 2020/9/17 7:21 下午
 **/
public class DefaultRegisterMessage implements RegisterMessage {

    /**
     * 唯一标识序列号
     * 0.0.8
     */
    private String seqId;

    /**
     * 消息头
     * 0.0.8
     */
    private RegisterMessageHeader registerMessageHeader;

    /**
     * 消息体
     * 0.0.8
     */
    private Object body;

    @Override
    public RegisterMessageHeader header() {
        return registerMessageHeader;
    }

    public RegisterMessage header(RegisterMessageHeader registerMessageHeader){
        this.registerMessageHeader = registerMessageHeader;
        return this;
    }

    @Override
    public Object body() {
        return this.body;
    }

    public RegisterMessage body(Object body){
        this.body = body;
        return this;
    }

    @Override
    public String seqId() {
        return seqId;
    }

    @Override
    public DefaultRegisterMessage seqId(String seqId) {
        this.seqId = seqId;
        return this;
    }
}
