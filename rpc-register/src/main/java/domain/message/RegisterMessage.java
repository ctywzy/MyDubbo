package domain.message;

import rpc.common.domain.BaseRpc;

/**
 * @Description 注册体消息
 * @Author wangzy
 * @Date 2020/9/17 7:18 下午
 **/
public interface RegisterMessage extends BaseRpc {

    /**
     * 头信息
     * @return 头信息
     * @since 0.0.8
     */
    RegisterMessageHeader header();

    /**
     * 消息信息体
     * @return 消息信息体
     * @since 0.0.8
     */
    Object body();
}
