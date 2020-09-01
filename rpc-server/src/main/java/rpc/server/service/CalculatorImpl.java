package rpc.server.service;

import rpc.common.model.CalculateRequest;
import rpc.common.model.CalculateResponse;
import rpc.common.service.Calculator;

public class CalculatorImpl implements Calculator {

    @Override
    public CalculateResponse sum(CalculateRequest request) {
        Long sum = request.getOne() + request.getTwo();

        return new CalculateResponse(true, sum);
    }
}
