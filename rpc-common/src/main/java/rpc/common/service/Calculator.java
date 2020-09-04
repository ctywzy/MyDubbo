package rpc.common.service;

import rpc.common.model.CalculateRequest;
import rpc.common.model.CalculateResponse;

public interface Calculator {


    CalculateResponse sum(CalculateRequest request);
}
