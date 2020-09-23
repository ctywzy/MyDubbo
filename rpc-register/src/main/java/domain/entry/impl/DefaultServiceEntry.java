package domain.entry.impl;

import domain.entry.ServiceEntry;
import lombok.ToString;


@ToString
public class DefaultServiceEntry implements ServiceEntry {

    private static final long serialVersionUID = 2057276144580488783L;

    /**
     * 服务标识
     */
    private String serviceId;

    /**
     * 服务描述
     */
    private String description;

    /**
     * 机器 ip 信息
     *
     * <pre>
     *     InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
     *     String clientIP = insocket.getAddress().getHostAddress();
     * </pre>
     */
    private String ip;

    /**
     * 服务端口号
     */
    private Integer port;

    /**
     * 服务权重
     */
    private Integer weight;

    @Override
    public String serviceId() {
        return serviceId;
    }

    public DefaultServiceEntry serviceId(String serviceId){
        this.serviceId = serviceId;
        return this;
    }

    @Override
    public String description() {
        return description;
    }

    public DefaultServiceEntry description(String description){
        this.description = description;
        return this;
    }

    @Override
    public String ip() {
        return ip;
    }

    public DefaultServiceEntry ip(String ip) {
        this.ip = ip;
        return this;
    }

    @Override
    public Integer port() {
        return port;
    }

    public DefaultServiceEntry port(int port) {
        this.port = port;
        return this;
    }

    @Override
    public Integer weight() {
        return weight;
    }

    public DefaultServiceEntry weight(int weight) {
        this.weight = weight;
        return this;
    }
}
