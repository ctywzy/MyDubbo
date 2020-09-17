package domain.message;

/**
 * @Description 注册信息头
 * @Author wangzy
 * @Date 2020/9/17 7:18 下午
 **/
public interface RegisterMessageHeader {

    /**
     * 消息类型
     * @return 消息类型
     * @since 0.0.8
     * @see rpc.common.constant.MessageTypeConst 类型常量
     */
    int type();

}
