package core.server;

import domain.entry.ServiceEntry;

import java.util.List;

/**
 * @Description 服务注册接口
 * @Author wangzy
 * @Date 2020/9/14 5:14 下午
 **/
public interface ServerRegisterService {

    /**
     * 注册当前服务信息
     * （1）将该服务通过 {@link ServiceEntry#serviceId()} 进行分组
     * 订阅了这个 serviceId 的所有客户端
     * @param serviceEntry 注册当前服务信息
     * @since 0.0.8
     * @return 更新后的服务信息列表
     */
    List<ServiceEntry> register(final ServiceEntry serviceEntry);

    /**
     * 注销当前服务信息
     * @param serviceEntry 注册当前服务信息
     * @since 0.0.8
     * @return 更新后的服务信息列表
     */
    List<ServiceEntry> unRegister(final ServiceEntry serviceEntry);

    /**
     * 根据服务标识发现对应的服务器信息
     * （1）如果对应的列表为空，则返回空列表。
     * @param serviceId 服务标识
     * @return 服务信息列表
     * @since 0.0.8
     */
    List<ServiceEntry> lookUp(final String serviceId);
}
