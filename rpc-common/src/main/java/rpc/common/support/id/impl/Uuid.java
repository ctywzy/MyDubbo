package rpc.common.support.id.impl;

import rpc.common.support.id.Id;

import java.util.UUID;

/**
 * @Description UUID生成工具类
 * @Author wangzy
 * @Date 2020/9/23 9:55 上午
 **/
public class Uuid implements Id {

    private Uuid(){}

    private static final Uuid INSTANCE = new Uuid();

    public static  Uuid getInstance(){
        return INSTANCE;
    }

    @Override
    public String id() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
