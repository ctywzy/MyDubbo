package rpc.common.domain.impl;

import rpc.common.domain.RpcResponse;

import javax.sound.midi.VoiceStatus;

/**
 * @Description
 * @Author wangzy
 * @Date 2020/9/4 3:41 下午
 **/
public class DefaultRpcResponse implements RpcResponse {

    private static final long serialVersionUID = 2063619137369416106L;

    /**
     * 唯一标识号
     * @since 0.0.6
     */
    private String seqId;

    /**
     * 调用结果
     */
    private Object result;

    /**
     * 异常信息
     * @since 0.0.6
     */
    private Throwable error;

    public static DefaultRpcResponse newInstance() {
        return new DefaultRpcResponse();
    }

    public String seqId() {
        return this.seqId;
    }

    public DefaultRpcResponse seqId(String seqId) {
        this.seqId = seqId;
        return this;
    }

    public Throwable error() {
        return error;
    }

    public DefaultRpcResponse error(Throwable error) {
        this.error = error;
        return this;
    }

    public Object result() {
        return null;
    }

    public DefaultRpcResponse result(Object result) {
        this.result = result;
        return this;
    }

    @Override
    public String toString() {
        return "DefaultRpcResponse{" +
                "seqId='" + seqId + '\'' +
                ", error=" + error +
                ", result=" + result +
                '}';
    }
}
