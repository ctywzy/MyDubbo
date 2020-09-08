package rpc.common.domain.impl;

import lombok.ToString;
import rpc.common.domain.BaseRpc;
import rpc.common.domain.RpcRequest;
import rpc.common.domain.RpcResponse;

import java.util.List;

/**
 * @Description
 * @Author wangzy
 * @Date 2020/9/4 3:41 下午
 **/
@ToString
public class DefaultRpcRequest implements RpcRequest {

    private static final long serialVersionUID = -6552255621120853412L;

    /**
     * 唯一标识号
     * （）
     * @since 0.0.6
     */
    private String seqId;

    private String serviceId;

    private String methodName;

    /**
     * 参数类型
     */
    private List<String> paramTypeNames;

    /**
     * 参数值
     */
    private Object[] paramValues;

    private Long createTime;

    public String seqId() {
        return this.seqId;
    }

    public DefaultRpcRequest seqId(String seqId) {
        this.seqId = seqId;
        return this;
    }

    public String serviceId() {
        return serviceId;
    }

    public DefaultRpcRequest serviceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public String methodName() {
        return methodName;
    }

    public DefaultRpcRequest methodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public List<String> paramTypeNames() {
        return paramTypeNames;
    }

    public DefaultRpcRequest paramTypeNames(List<String> paramTypeNames) {
        this.paramTypeNames = paramTypeNames;
        return this;
    }

    public Object[] paramValues() {
        return paramValues;
    }

    public DefaultRpcRequest paramValues(Object[] paramValues) {
        this.paramValues = paramValues;
        return this;
    }

    public Long createTime(){
        return this.createTime;
    }

    public DefaultRpcRequest createTime(Long createTime) {
        this.createTime = createTime;
        return this;
    }
}
