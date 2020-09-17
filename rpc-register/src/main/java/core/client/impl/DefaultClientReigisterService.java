package core.client.impl;

import core.client.ClientRegisterService;
import domain.entry.ServiceEntry;
import domain.message.RegisterMessage;
import domain.message.impl.RegisterMessages;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import rpc.common.constant.MessageTypeConst;
import rpc.common.util.CollectionUtil;

import java.util.*;

@Slf4j
public class DefaultClientReigisterService implements ClientRegisterService {

    /**
     * 服务信息-客户端列表 map
     * key: serviceId
     * value: 对应的客户端列表信息。
     *
     * 客户端使用定期拉取的方式：
     * （1）传入 host 信息，返回对应的 service 列表。
     * （2）根据 service 列表，变化时定期推送给客户端。
     *
     * 只是在第一次采用拉取的方式，后面全部采用推送的方式。
     * （1）只有变更的时候，才会进行推送，保证实时性。
     * （2）客户端启动时拉取，作为保底措施。避免客户端不在线等情况。
     *
     * @since 0.0.8
     */
    private final Map<String, Set<Channel>> serviceClientChannelMap;

    public DefaultClientReigisterService() {
        this.serviceClientChannelMap = new HashMap();
    }

    public void subscribe(ServiceEntry serviceEntry, Channel clientChannel) {
        String serviceId = serviceEntry.serviceId();

        Set<Channel> channelSet = serviceClientChannelMap.get(serviceId);

        if(CollectionUtil.isEmpty(channelSet)){
            channelSet = new HashSet();
        }

        channelSet.add(clientChannel);

        serviceClientChannelMap.put(serviceId, channelSet);
    }

    public void unSubscribe(ServiceEntry serviceEntry, Channel clientChannel) {
        String serviceId = serviceEntry.serviceId();

        Set<Channel> channelSet = serviceClientChannelMap.get(serviceId);

        if(CollectionUtil.isEmpty(channelSet)){
            channelSet = new HashSet();
        }

        channelSet.remove(clientChannel);

        serviceClientChannelMap.put(serviceId, channelSet);
    }

    public void notify(String serviceId, final List<ServiceEntry> serviceEntryList) {

        final Set<Channel> channelSet = serviceClientChannelMap.get(serviceId);

        if(CollectionUtil.isEmpty(channelSet)){

        }

        channelSet.stream().forEach( channel -> {
            RegisterMessage registerMessage = RegisterMessages.of(MessageTypeConst.REGISTER_NOTIFY, serviceEntryList);
            channel.writeAndFlush(registerMessage);
        });

    }
}
