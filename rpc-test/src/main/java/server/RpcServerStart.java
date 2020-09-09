package server;

import rpc.common.constant.ServiceIdConst;
import rpc.server.core.RpcServer;
import rpc.server.registry.impl.DefaultServiceRegistry;
import rpc.server.service.CalculatorImpl;

public class RpcServerStart {

    public static void main(String[] args) {
        // 启动服务
        /**
         * 手动的注册是否可以改为某种读取配置文件的方式
         */
        DefaultServiceRegistry.getInstance()
                .register(ServiceIdConst.CALC, new CalculatorImpl())
                .expose();
    }
}
