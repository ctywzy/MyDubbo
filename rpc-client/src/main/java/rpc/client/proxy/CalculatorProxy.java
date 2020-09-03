package rpc.client.proxy;

import rpc.client.core.RpcClient;
import rpc.common.model.CalculateRequest;
import rpc.common.model.CalculateResponse;
import rpc.common.service.Calculator;

/**
 * @Description 计算增强代理类
 * @Author wangzy
 * @Date 2020/9/2 4:08 下午
 **/
public class CalculatorProxy implements Calculator {

    private RpcClient rpcClient;

    public CalculatorProxy() {
        this.rpcClient = new RpcClient();
        rpcClient.start();
    }


    @Override
    public CalculateResponse sum(CalculateRequest request) {

        return rpcClient.calculate(request);
    }
}
