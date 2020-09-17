package rpc.client.invoke.impl;

import rpc.common.support.time.impl.DefaultSystemTime;
import rpc.common.domain.RpcResponse;
import rpc.common.domain.impl.RpcResponseFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TimeoutCheckThread implements Runnable{

    /**
     * 请求信息，用来记录请求是否超时
     * @key reqId
     * @Value 超时时间
     * @since feature/0.0.7
     */
    private final ConcurrentHashMap<String, Long> requestMap;

    /**
     * 答复信息
     * @since feature/0.0.7
     */
    private final ConcurrentHashMap<String, RpcResponse> responseMap;

    /**
     * 新建
     * @param requestMap  请求 Map
     * @param responseMap 结果 map
     * @since feature/0.0.7
     */
    public TimeoutCheckThread(ConcurrentHashMap<String, Long> requestMap,
                              ConcurrentHashMap<String, RpcResponse> responseMap) {

        this.requestMap = requestMap;
        this.responseMap = responseMap;
    }

    /**
     * @description 这个线程的作用就是遍历检查所有请求的时间
     * 如果有判定了超时，就抛出超时异常然后直接移除这个请求
     * 因为他已经失败了
     * 并直接将请求结果设置为请求异常
     */
    @Override
    public void run() {
        for(Map.Entry<String, Long> entry : requestMap.entrySet()){
            Long expriseTime = entry.getValue();
            Long currentTime = DefaultSystemTime.getInstance().time();
            if(expriseTime < currentTime){
                String key = entry.getKey();
                requestMap.remove(entry.getKey());
                responseMap.put(key, RpcResponseFactory.Timeout());
            }

        }
    }
}
