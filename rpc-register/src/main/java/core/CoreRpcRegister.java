package core;

import core.client.ClientRegisterService;
import core.server.ServerRegisterService;
import domain.entry.ServiceEntry;
import io.netty.channel.Channel;
import rpc.common.domain.RpcResponse;
import rpc.common.domain.impl.DefaultRpcResponse;
import spi.RpcRegister;

import java.util.List;

/**
 * @Description 注册中心实现类
 * @Author wangzy
 * @Date 2020/9/11 2:38 下午
 **/
public class CoreRpcRegister implements RpcRegister {

    /**
     * 服务端信息管理
     * @since feature/0.0.8
     */
    private ServerRegisterService serverRegisterService;

    /**
     * 客户端信息管理
     * @since feature/0.0.8
     */
    private ClientRegisterService clientRegisterService;

    public CoreRpcRegister(ServerRegisterService serverRegisterService, ClientRegisterService clientRegisterService){
        this.serverRegisterService = serverRegisterService;
        this.clientRegisterService = clientRegisterService;
    }

    @Override
    public void register(ServiceEntry serviceEntry) {
        List<ServiceEntry> serviceEntries = serverRegisterService.register(serviceEntry);
        clientRegisterService.notify(serviceEntry.serviceId(), serviceEntries);

    }

    @Override
    public void unRegister(ServiceEntry serviceEntry) {
        List<ServiceEntry> serviceEntris = serverRegisterService.unRegister(serviceEntry);
        clientRegisterService.notify(serviceEntry.serviceId(), serviceEntris);
    }

    @Override
    public void subscribe(ServiceEntry serviceEntry, Channel channel) {
        clientRegisterService.subscribe(serviceEntry, channel);
    }

    @Override
    public void unSubscribe(ServiceEntry serviceEntry, Channel channel) {
        clientRegisterService.unSubscribe(serviceEntry, channel);
    }

    @Override
    public void lookUp(String seqId, ServiceEntry serviceEntry, Channel channel) {
        String serviceId = serviceEntry.serviceId();

        List<ServiceEntry> serviceEntryList = serverRegisterService.lookUp(serviceId);

        RpcResponse rpcResponse = DefaultRpcResponse.newInstance()
                                                    .seqId(seqId)
                                                    .result(serviceEntryList);
        channel.writeAndFlush(rpcResponse);
    }
}
