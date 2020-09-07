package rpc.client.invoke;

import rpc.common.domain.RpcResponse;

/**
 * @Description 调用服务接口
 * @Author wangzy
 * @Date 2020/9/4 9:56 上午
 **/
public interface InvokeService {

    /**
     * 放入结果
     * @param seqId 唯一标识
     * @param response 响应结果
     * @return this
     * @since feature/0.0.6
     */
    InvokeService addResponse(String seqId, RpcResponse response);

    /**
     * 获取标志信息对应的结果
     * @param seqId 序列号
     * @return 结果
     * @since feature/0.0.6
     */
    RpcResponse getResponse(String seqId);

    /**
     * 添加请求信息
     * @param seqId 序列号
     * @return this
     * @since feature/0.0.6
     */
    InvokeService addRequest(String seqId);
}
