package rpc.common.support.time.impl;

import rpc.common.support.time.Time;


/**
 * @Description 获取当前时间的工具类
 * @Author wangzy
 * @Date 2020/9/4 5:55 下午
 **/
public class DefaultSystemTime implements Time {

    public static final DefaultSystemTime INSTANCE = new DefaultSystemTime();

    public static DefaultSystemTime getInstance(){
        return INSTANCE;
    }

    @Override
    public long time() {
        return System.currentTimeMillis();
    }
}
