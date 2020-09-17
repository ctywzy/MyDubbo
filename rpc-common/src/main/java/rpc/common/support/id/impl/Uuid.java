package rpc.common.support.id.impl;

import rpc.common.support.id.Id;

import java.util.UUID;

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
