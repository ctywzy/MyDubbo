package client;

import lombok.extern.slf4j.Slf4j;
import rpc.client.core.RpcClient;
import rpc.client.proxy.CalculatorProxy;
import rpc.common.model.CalculateRequest;
import rpc.common.model.CalculateResponse;
import rpc.common.service.Calculator;

@Slf4j
public class RpcClientStart {

    public static void main(String[] args) {
        Calculator proxy = new CalculatorProxy();
        CalculateRequest request = new CalculateRequest(2L, 7L);

        CalculateResponse response = proxy.sum(request);

        log.info("[Client] rpc response : {}", response);
    }
}
