package rpc.common.domain;

import rpc.common.domain.BaseRpc;

import java.util.List;

/**
 * @Description
 * @Author wangzy
 * @Date 2020/9/4 3:41 下午
 **/
public interface RpcRequest extends BaseRpc {

    /**
     * 唯一服务标识
     * @return
     */
    String serviceId();

    /**
     * 方法名称
     * @return
     */
    String methodName();

    /**
     * 方法参数列表
     * @return
     */
    List<String> paramTypeNames();

    /**
     * 调用参数值
     * @return
     */
    Object[] paramValues();

    Long createTime();
}
