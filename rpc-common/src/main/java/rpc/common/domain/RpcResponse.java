package rpc.common.domain;

/**
 * @Description
 * @Author wangzy
 * @Date 2020/9/4 3:41 下午
 **/
public interface RpcResponse extends BaseRpc{

    /**
     * 异常信息
     * @return 异常信息
     * @since 0.0.6
     */
    Throwable error();

    /**
     * 请求结果
     * @return 请求结果
     * @since 0.0.6
     */
    Object result();

}
