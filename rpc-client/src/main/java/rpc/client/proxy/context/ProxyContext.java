package rpc.client.proxy.context;

import io.netty.channel.ChannelFuture;
import rpc.client.invoke.InvokeService;

import java.util.List;

/**
 * @Description
 * @Author wangzy
 * @Date 2020/9/4 10:50 上午
 * @see rpc.client.config.reference.ReferenceConfig 对这里的信息进行一次转换，将配置信息填充到上下文中
 **/
public interface ProxyContext<T> {

    /**
     * 服务唯一标识
     * @since 0.0.6
     * @return 服务唯一标识
     */
    String serviceId();

    /**
     * 服务接口
     * @since 0.0.6
     * @return 服务接口
     */
    Class<T> serviceInterface();

    /**
     * netty channel 信息
     * @return channel 信息
     * @since 0.0.6
     */
    List<ChannelFuture> channelFutures();

    /**
     * 调用服务
     * @return 调用服务
     * @since 0.0.6
     */
    InvokeService invokeService();
}