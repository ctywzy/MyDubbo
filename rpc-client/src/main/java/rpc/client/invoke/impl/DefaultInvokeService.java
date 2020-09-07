package rpc.client.invoke.impl;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import rpc.client.invoke.InvokeService;
import rpc.common.domain.RpcResponse;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class DefaultInvokeService implements InvokeService {

    /**
     * 响应结果
     * @since feature/0.0.6
     */
    private final ConcurrentHashMap<String, RpcResponse> responseMap;

    /**
     * 请求序列号集合
     * （1）这里后期如果要添加超时检测，可以添加对应的超时时间。
     * 可以把这里调整为 map
     * @since feature/0.0.6
     */
    private final Set<String> requestSet;

    public DefaultInvokeService() {
        responseMap = new ConcurrentHashMap<>();
        requestSet = new HashSet<>();
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

    @Override
    public RpcResponse getResponse(String seqId) {
        try {
            RpcResponse rpcResponse = this.responseMap.get(seqId);
            if(Objects.nonNull(rpcResponse)) {
                log.info("[Client] seq {} 对应结果已经获取: {}", seqId, rpcResponse);
                return rpcResponse;
            }

            // 进入等待
            while (rpcResponse == null) {
                log.info("[Client] seq {} 对应结果为空，进入等待", seqId);
                // 同步等待锁
                synchronized (this) {
                    this.wait();
                }

                rpcResponse = this.responseMap.get(seqId);
                log.info("[Client] seq {} 对应结果已经获取: {}", seqId, rpcResponse);
            }

            return rpcResponse;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InvokeService addRequest(String seqId) {
        log.info("[Client] start add request for seqId: {}", seqId);
        requestSet.add(seqId);
        return this;
    }
}
