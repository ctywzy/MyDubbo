package client;

import lombok.extern.slf4j.Slf4j;
import rpc.client.config.reference.impl.DefaultReferenceConfig;
import rpc.common.constant.RpcConstant;
import rpc.common.constant.ServiceIdConst;
import rpc.common.model.CalculateRequest;
import rpc.common.model.CalculateResponse;
import rpc.common.service.Calculator;
import rpc.client.config.reference.ReferenceConfig;


@Slf4j
public class RpcClientStart {

    public static void main(String[] args) {
        // 服务配置信息
        ReferenceConfig<Calculator> config = new DefaultReferenceConfig<Calculator>();
        config.serviceId(ServiceIdConst.CALC);
        config.serviceInterface(Calculator.class);

        config.addresses(RpcConstant.ADDRESS, RpcConstant.PORT);

        Calculator calculator = config.reference();
        CalculateRequest request = new CalculateRequest();
        request.setOne(10L);
        request.setTwo(20L);

        CalculateResponse response = calculator.sum(request);
        System.out.println(response);
    }
}
