package rpc.common.support.time.impl;

/**
 * @Description 当前时间获取工具类
 * @Author wangzy
 * @Date 2020/9/23 9:56 上午
 **/
public class Times {

    private static DefaultSystemTime INSTANCE = new DefaultSystemTime();

    public static Long time(){
        return INSTANCE.time();
    }
}
