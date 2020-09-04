package rpc.client.invoke.impl;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import rpc.client.invoke.InvokeService;
import rpc.common.domain.RpcResponse;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class DefaultInvokeService implements InvokeService {

    /**
     * 响应结果
     * @since 0.0.6
     */
    private final ConcurrentHashMap<String, RpcResponse> responseMap;

    public DefaultInvokeService() {
        responseMap = new ConcurrentHashMap<>();
    }

    @Override
    public InvokeService addResponse(String seqId, RpcResponse response) {
        // 这里放入之前，可以添加判断。
        // 如果 seqId 必须处理请求集合中，才允许放入。或者直接忽略丢弃。
        log.info("[Client] 获取结果信息，seq: {}, rpcResponse: {}", seqId, response);
        responseMap.putIfAbsent(seqId, response);

        // 通知所有等待方
        log.info("[Client] seq 信息已经放入，通知所有等待方", seqId);

        synchronized (this) {
            this.notifyAll();
        }

        return this;
    }
}
