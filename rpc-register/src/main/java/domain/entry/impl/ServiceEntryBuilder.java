package domain.entry.impl;

import domain.entry.ServiceEntry;

/**
 * @Description 服务明细工具类
 * @Author wangzy
 * @Date 2020/9/22 7:58 下午
 **/
public final class ServiceEntryBuilder {

    private ServiceEntryBuilder(){}

    /**
     * 指定服务标识
     * @param serviceId 服务标识
     * @param ip ip 地址
     * @param port 端口号
     * @return 服务明细
     * @since 0.0.8
     */
    public static ServiceEntry of(final String serviceId,
                                  final String ip,
                                  final int port) {
        DefaultServiceEntry entry = new DefaultServiceEntry();
        entry.serviceId(serviceId).port(port).ip(ip);
        return entry;
    }

    /**
     * 指定服务标识
     * @param serviceId 服务标识
     * @return 服务明细
     * @since 0.0.8
     */
    public static ServiceEntry of(final String serviceId) {
        return of(serviceId, null, 0);
    }
}
