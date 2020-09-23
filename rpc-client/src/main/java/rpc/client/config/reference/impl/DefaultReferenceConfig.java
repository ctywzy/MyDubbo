package rpc.client.config.reference.impl;

import com.google.common.collect.Lists;
import domain.entry.ServiceEntry;
import domain.entry.impl.ServiceEntryBuilder;
import domain.message.RegisterMessage;
import domain.message.impl.RegisterMessages;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import rpc.client.config.reference.ReferenceConfig;
import rpc.client.core.RpcClient;
import rpc.client.core.context.impl.DefaultRpcClientContext;
import rpc.client.handle.RpcClientHandler;
import rpc.client.handle.RpcClientRegisterHandler;
import rpc.client.invoke.InvokeService;
import rpc.client.invoke.impl.DefaultInvokeService;
import rpc.client.proxy.ReferenceProxy;
import rpc.client.proxy.context.ProxyContext;
import rpc.client.proxy.context.impl.DefaultProxyContext;
import rpc.common.config.component.RpcAddress;
import rpc.common.constant.MessageTypeConst;
import rpc.common.exception.RpcRuntimeException;
import rpc.client.handle.ChannelHandlers;
import rpc.common.remote.netty.impl.DefaultNettyClient;
import rpc.common.util.CollectionUtil;

import java.io.ObjectOutputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 引用配置类
 *
 * 后期配置：
 * （1）timeout 调用超时时间
 * （2）version 服务版本处理
 * （3）callType 调用方式 oneWay/sync/async
 * （4）check 是否必须要求服务启动。
 *
 * spi:
 * （1）codec 序列化方式
 * （2）netty 网络通讯架构
 * （3）load-balance 负载均衡
 * （4）失败策略 fail-over/fail-fast
 *
 * filter:
 * （1）路由
 * （2）耗时统计 monitor 服务治理
 *
 * 优化思考：
 * （1）对于唯一的 serviceId，其实其 interface 是固定的，是否可以省去？
 * @author binbin.hou
 * @since 0.0.6
 * @param <T> 接口泛型
 * @Author wangzy
 * @Date 2020/9/4 9:32 上午
 **/

@Slf4j
public class DefaultReferenceConfig<T> implements ReferenceConfig<T> {

    private static final Long MILLI = 1000L;

    /**
     * 服务唯一标识
     * @since 0.0.6
     */
    private String serviceId;

    /**
     * 服务接口
     * @since 0.0.6
     */
    private Class<T> serviceInterface;

    /**
     * 服务地址信息
     *（1）如果不为空，则直接根据地址获取
     *（2）如果为空，则采用自动发现的方式
     *
     * TODO: 这里调整为 set 更加合理。
     *
     * 如果为 subscribe 可以自动发现，然后填充这个字段信息。
     * @since 0.0.6
     */
    private List<RpcAddress> rpcAddresses;

    /**
     * 注册中心列表
     * @since 0.0.8
     */
    private List<RpcAddress> registerCenterList;

    /**
     * 用于写入信息
     *（1）client 连接 server 端的 channel future
     *（2）后期进行 Load-balance 路由等操作。可以放在这里执行。
     * @since 0.0.6
     */
    private List<ChannelFuture> channelFutures;

    /**
     * 服务接口
     */
    private InvokeService invokeService;

    /**
     * 超时时间
     */
    private Long timeout;

    /**
     * 是否进行订阅模式
     * @since 0.0.8
     */
    private boolean subscribe;

    /**
     * 注册中心超时时间
     */
    private Long registerCenterTimeout;

    public DefaultReferenceConfig(){
        this.rpcAddresses = Lists.newArrayList();
        this.channelFutures = Lists.newArrayList();
        this.invokeService = new DefaultInvokeService();
        this.timeout = 15 * MILLI;
        this.registerCenterList = Lists.newArrayList();
        this.registerCenterTimeout = 15 * MILLI;
    }

    @Override
    public ReferenceConfig<T> serviceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    @Override
    public String serviceId() {
        return serviceId;
    }

    @Override
    public ReferenceConfig<T> serviceInterface(Class<T> serviceInterface) {
        this.serviceInterface = serviceInterface;
        return this;
    }

    @Override
    public Class<T> serviceInterface() {
        return serviceInterface;
    }

    @Override
    public ReferenceConfig<T> addresses(String address, int port) {
        if(StringUtil.isNullOrEmpty(address)){

        }
        RpcAddress rpcAddress = new RpcAddress(address, port, 1);

        rpcAddresses.add(rpcAddress);

        return this;
    }


    /**
     * 获取对应的引用实现
     * （1）处理所有的反射代理信息-方法可以抽离，启动各自独立即可。
     * （2）启动对应的长连接
     * @return 引用代理类
     * @since feature/0.0.6
     */
    @Override
    public T reference() {

        // 1. 启动 client 端到 server 端的连接信息
        // 1.1 为了提升性能，可以将所有的 client=>server 的连接都调整为一个 thread。
        // 1.2 初期为了简单，直接使用同步循环的方式。
        // 创建 handler
        // 循环连接

        /**
         * 从注册中心处获取所有注册的列表
         * 会建立与注册中心的通道
         */
        List<RpcAddress> rpcAddressList = getRpcAddressList();

        for(RpcAddress rpcAddress : rpcAddressList){
            // 收到消息时的处理方式
            final ChannelHandler channelHandler = new RpcClientHandler(invokeService);
            final ChannelHandler actualChannlHandler = ChannelHandlers.objectCodecHandler(channelHandler);
            ChannelFuture channelFuture = DefaultNettyClient.newInstance(rpcAddress.address(), rpcAddress.port(), actualChannlHandler).call();
            channelFutures.add(channelFuture);
        }
        // 2. 接口动态代理,设置上下文信息
        ProxyContext<T> proxyContext = buildReferenceProxyContext();
        return ReferenceProxy.newProxyInstance(proxyContext);
    }

    /**
     * 获取rpc地址信息列表
     * 通过与注册中心连接
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<RpcAddress> getRpcAddressList() {

        // 0 快速返回
        if(CollectionUtil.isNotEmpty(rpcAddresses)){
            return rpcAddresses;
        }

        registerCenterParamCheck();

        //2. 查询服务信息
        List<ServiceEntry> serviceEntries = lookUpServiceEntryList();
        log.info("[Client] register center serviceEntries: {}", serviceEntries);
        return serviceEntries.stream()
                             .map(it -> new RpcAddress(it.ip(), it.port(), it.weight()))
                             .collect(Collectors.toList());
    }

    /**
     * 校验参数是否合理
     */
    private void registerCenterParamCheck() {

        if(!subscribe){
            log.error("[Rpc Client] no available services found for serviceId:{}",
                    serviceId);
            throw new RpcRuntimeException();
        }

        if(CollectionUtil.isEmpty(registerCenterList)){
            log.error("[Rpc Client] register center address can't be null!:{}",
                    serviceId);
            throw new RpcRuntimeException();
        }
    }

    /**
     * 查询服务信息
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<ServiceEntry> lookUpServiceEntryList() {

        /**
         * 1 连接到注册中心
         */
        List<ChannelFuture> channelFutureList = connectRegisterCenter();

        // 2 选择一个
        ChannelFuture channelFuture = Optional.ofNullable(channelFutureList)
                                              .orElseGet(Collections::emptyList)
                                              .stream()
                                              .findAny()
                                              .orElse(null);
        // 3 发送查询请求
        ServiceEntry serviceEntry = ServiceEntryBuilder.of(serviceId);
        RegisterMessage registerMessage = RegisterMessages.of(MessageTypeConst.CLIENT_LOOK_UP, serviceEntry);
        final String seqId = registerMessage.seqId();
        invokeService.addRequest(seqId, registerCenterTimeout);
        channelFuture.channel().writeAndFlush(registerMessage);

    }

    private List<ChannelFuture> connectRegisterCenter() {
        List<ChannelFuture> futureList = new ArrayList<>(registerCenterList.size());
        ChannelHandler channelHandler = ChannelHandlers.objectCodecHandler(new RpcClientRegisterHandler(invokeService));

        for(RpcAddress rpcAddress : registerCenterList){
            final String ip = rpcAddress.address();
            final int port = rpcAddress.port();
            log.info("[Rpc Client] connect to register {}:{} ", ip, port);
            /**
             * 调用客户端启动方法
             */
            ChannelFuture channelFuture = DefaultNettyClient.newInstance(ip, port, channelHandler).call();
            futureList.add(channelFuture);
        }
        return futureList;
    }

    @Override
    public ReferenceConfig<T> timeout(Long timeoutMill) {
        this.timeout = timeoutMill;
        return this;
    }

    @Override
    public ReferenceConfig<T> subscribe(boolean subscribe) {
        this.subscribe = subscribe;
        return this;
    }

    @Override
    public ReferenceConfig<T> registerCenter(String addresses) {
        this.registerCenterList = registerCenterList;
        return this;
    }

    /**
     * 构建调用上下文
     * @return 引用代理上下文
     * @since feature/0.0.6
     */
    private ProxyContext<T> buildReferenceProxyContext(){
        DefaultProxyContext<T> proxyContext = new DefaultProxyContext<>();
        proxyContext.serviceId(this.serviceId)
                    .serviceInterface(this.serviceInterface)
                    .channelFutures(this.channelFutures)
                    .invokeService(this.invokeService)
                    .timeout(this.timeout);
        return proxyContext;
        
    }


}
