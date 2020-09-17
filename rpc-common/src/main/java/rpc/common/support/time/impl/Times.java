package rpc.common.support.time.impl;

public class Times {

    private static DefaultSystemTime INSTANCE = new DefaultSystemTime();

    public static Long time(){
        return INSTANCE.time();
    }
}
