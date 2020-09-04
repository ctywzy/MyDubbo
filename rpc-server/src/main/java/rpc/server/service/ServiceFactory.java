package rpc.server.service;

import rpc.server.config.service.ServiceConfig;

import java.util.List;

public interface ServiceFactory {

    /**
     * 注册服务列表信息
     * @param serviceConfigs 服务配置列表
     * @return this
     * @since 0.0.6
     */
    ServiceFactory registerService(List<ServiceConfig> serviceConfigs);

    /**
     * 直接反射调用
     * （1）此处对于方法反射，为了提升性能，所有的 class.getFullName() 进行拼接然后放进 key 中。
     *
     * @param serviceId 服务名称
     * @param methodName 方法名称
     * @param paramTypeNames 参数类型名称列表
     * @param paramValues 参数值
     * @return 方法调用返回值
     * @since 0.0.6
     */
    Object invoke(final String serviceId, final String methodName,
                  List<String> paramTypeNames, final Object[] paramValues);
}
