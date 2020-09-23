package core.server.impl;

import core.server.ServerRegisterService;
import domain.entry.ServiceEntry;
import lombok.extern.slf4j.Slf4j;
import rpc.common.util.CollectionUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description 服务注册类
 * @Author wangzy
 * @Date 2020/9/14 5:14 下午
 **/

@Slf4j
public class DefaultServerReigsterService implements ServerRegisterService {

    /**
     * 存放服务信息
     * @key serviceId
     * @Value serviceId对应的服务集合
     */
    Map<String, Set<ServiceEntry>> map;

    @Override
    public List<ServiceEntry> register(ServiceEntry serviceEntry) {

        String serviceId = serviceEntry.serviceId();

        Set<ServiceEntry> serviceEntries = map.get(serviceId);

        if(CollectionUtil.isEmpty(serviceEntries)){
            serviceEntries = new HashSet();
        }

        log.info("[Register Server] add service: {}", serviceEntry);
        serviceEntries.add(serviceEntry);
        map.put(serviceId, serviceEntries);

        return CollectionUtil.newArrayList(serviceEntries);
    }

    @Override
    public List<ServiceEntry> unRegister(ServiceEntry serviceEntry) {
        String serviceId = serviceEntry.serviceId();
        Set<ServiceEntry> serviceEntries = map.get(serviceId);

        if(CollectionUtil.isEmpty(serviceEntries)){
            serviceEntries = new HashSet();
        }

        log.info("[Register Server] add service: {}", serviceEntry);

        serviceEntries.remove(serviceEntry);

        return CollectionUtil.newArrayList(serviceEntries);

    }

    @Override
    public List<ServiceEntry> lookUp(String serviceId) {

        log.info("[Register Server] start lookUp serviceId: {}", serviceId);
        Set<ServiceEntry> serviceEntrySet = map.get(serviceId);

        return CollectionUtil.newArrayList(serviceEntrySet);
    }
}
