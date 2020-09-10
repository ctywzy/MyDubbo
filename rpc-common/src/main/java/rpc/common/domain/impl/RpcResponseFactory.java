package rpc.common.domain.impl;

import rpc.common.domain.RpcResponse;
import rpc.common.exception.RpcTimeOutException;

/**
 * @Description 超时返回异常对象
 * @Author wangzy
 * @Date 2020/9/10 1:48 下午
 **/
public class RpcResponseFactory {

    /**
     * 超时异常信息
     */
    private static DefaultRpcResponse TIMEOUT;

    static{
        TIMEOUT = new DefaultRpcResponse();
        TIMEOUT.error(new RpcTimeOutException());
    }

    /**
     * 获取超时响应结果
     * @return
     */
    public static RpcResponse Timeout() {
        return TIMEOUT;
    }
}
