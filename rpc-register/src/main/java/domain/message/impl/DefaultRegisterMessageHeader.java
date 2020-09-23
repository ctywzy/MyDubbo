package domain.message.impl;

import domain.message.RegisterMessageHeader;

/**
 * @Description 注册消息头
 * @Author wangzy
 * @Date 2020/9/17 7:21 下午
 **/
public class DefaultRegisterMessageHeader implements RegisterMessageHeader {

    /**
     * 消息类型
     * @since 0.0.8
     */
    int type;

    @Override
    public int type() {
        return 0;
    }

    public DefaultRegisterMessageHeader type(int type){
        this.type = type;
        return this;
    }
}
