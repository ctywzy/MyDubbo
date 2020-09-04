package rpc.server.registry.impl;

import com.google.common.collect.Lists;
import rpc.common.constant.RpcConstant;
import rpc.server.config.service.ServiceConfig;
import rpc.server.config.service.impl.DefaultServiceConfig;
import rpc.server.core.RpcServer;
import rpc.server.registry.ServiceRegistry;
import rpc.server.service.ServiceFactory;
import rpc.server.service.impl.DefaultServiceFactory;

import java.util.List;

/**
 * @Description
 * @Author wangzy
 * @Date 2020/9/4 2:21 下午
 **/
public class DefaultServiceRegistry<T> implements ServiceRegistry<T> {

    
    private static final DefaultServiceRegistry INSTANCE = new DefaultServiceRegistry();

    /**
     * 服务配置信息列表
     * todo 这个地方改成set会不会好一些，去重
     */
    private List<ServiceConfig> serviceConfigs;

    /**
     * 服务启动端口号
     */
    private int port;

    public DefaultServiceRegistry(){
        //初始化默认参数
        this.serviceConfigs = Lists.newArrayList();
        port = RpcConstant.PORT;
    }
    /**
     * 单例获取注册容器
     * @return
     */
    public static DefaultServiceRegistry getInstance() {

        return INSTANCE;
    }

    /**
     * 注册服务实现
     * （1）主要用于后期服务调用
     * （2）如何根据 id 获取实现？非常简单，id 是唯一的。
     * 有就是有，没有就抛出异常，直接返回。
     * （3）如果根据 {@link com.github.houbb.rpc.common.rpc.domain.RpcRequest} 获取对应的方法。
     *
     * 3.1 根据 serviceId 获取唯一的实现
     * 3.2 根据 {@link Class#getMethod(String, Class[])} 方法名称+参数类型唯一获取方法
     * 3.3 根据 {@link java.lang.reflect.Method#invoke(Object, Object...)} 执行方法
     *
     * @param serviceId 服务标识
     * @param serviceImpl 服务实现
     * @return this
     */
    @Override
    @SuppressWarnings("unchecked")
    public synchronized ServiceRegistry register(final String serviceId, final T serviceImpl) {
        //对入参参数判空处理
        ServiceConfig serviceConfig = new DefaultServiceConfig();
        //以id号作为区分来注册bean
        serviceConfig.id(serviceId).reference(serviceImpl);
        //将注册号的bean加入到list容器中
        serviceConfigs.add(serviceConfig);
        return this;
    }

    @Override
    public ServiceRegistry expose() {

        DefaultServiceFactory.getInstance()
                             .registerService(serviceConfigs);

        new RpcServer().start();

        return this;
    }
}
