package rpc.client.proxy;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import rpc.client.core.RpcClient;
import rpc.common.model.CalculateRequest;
import rpc.common.model.CalculateResponse;
import rpc.common.service.Calculator;

/**
 * @Description 计算增强代理类
 * @Author wangzy
 * @Date 2020/9/2 4:08 下午
 **/
@Slf4j
public class CalculatorProxy implements Calculator {

    private RpcClient rpcClient;

    public CalculatorProxy() {
        this.rpcClient = new RpcClient();
        rpcClient.start();
    }


    @Override
    public CalculateResponse sum(CalculateRequest request) {

        CalculateResponse response = null;
        try{
            response = rpcClient.calculate(request);
        }catch (Exception e){
            log.info("json转换错误");
        }
        return response;
    }
}
