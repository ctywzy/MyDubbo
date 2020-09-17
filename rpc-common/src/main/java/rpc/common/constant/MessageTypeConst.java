package rpc.common.constant;

/**
 * @Description 注册消息枚举
 * @Author wangzy
 * @Date 2020/9/17 7:24 下午
 **/
public class MessageTypeConst {

    /**
     * 服务端注册
     * @since 0.0.8
     */
    public static final int SERVER_REGISTER = 1;

    /**
     * 服务端注销
     * @since 0.0.8
     */
    public static final int SERVER_UN_REGISTER = 2;

    /**
     * 客户端订阅
     * @since 0.0.8
     */
    public static final int CLIENT_SUBSCRIBE = 3;

    /**
     * 客户端取关
     * @since 0.0.8
     */
    public static final int CLIENT_UN_SUBSCRIBE = 4;

    /**
     * 客户端查询
     * @since 0.0.8
     */
    public static final int CLIENT_LOOK_UP = 5;

    /**
     * 注册中心通知
     * @since 0.0.8
     */
    public static final int REGISTER_NOTIFY = 6;

    /**
     * 注册客户端查询响应
     * @since 0.0.8
     */
    public static final int REGISTER_LOOK_UP_RESP = 7;
}
