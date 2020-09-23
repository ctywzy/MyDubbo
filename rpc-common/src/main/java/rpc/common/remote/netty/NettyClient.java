package rpc.common.remote.netty;

import rpc.common.api.Destroyable;
import rpc.common.api.Initializable;

import java.util.concurrent.Callable;

/**
 * @Description 网络客户端
 * @Author wangzy
 * @Date 2020/9/22 7:25 下午
 **/
public interface NettyClient<T> extends Callable<T>, Destroyable, Initializable {
}
