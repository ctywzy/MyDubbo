package domain.entry;

import java.io.Serializable;

/**
 * @Description 服务信息
 * 每一个服务可以对应多台 ip:port 信息的
 * @Author wangzy
 * @Date 2020/9/11 1:47 下午
 **/
public interface ServiceEntry extends Serializable {

    /**
     * 服务标识
     * @return 服务标识
     * @since 0.0.8
     */
    String serviceId();

    /**
     * 服务描述
     * @return 服务描述
     * @since 0.0.8
     */
    String description();

    /**
     * 机器 ip 信息
     *
     * <pre>
     *     InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
     *     String clientIP = insocket.getAddress().getHostAddress();
     * </pre>
     *
     * @return 机器 ip 信息
     * @since 0.0.8
     */
    String ip();

    /**
     * 端口信息
     * @return 端口信息
     * @since 0.0.8
     */
    Integer port();

    /**
     * 权重信息
     * @return 权重信息
     * @since 0.0.8
     */
    Integer weight();

}
