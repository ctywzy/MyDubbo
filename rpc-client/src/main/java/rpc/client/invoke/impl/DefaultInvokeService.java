package rpc.client.invoke.impl;

import lombok.extern.slf4j.Slf4j;
import rpc.client.invoke.InvokeService;
import rpc.common.support.time.impl.Times;
import rpc.common.constant.RpcConstant;
import rpc.common.domain.RpcResponse;
import rpc.common.domain.impl.RpcResponseFactory;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DefaultInvokeService implements InvokeService {

    /**
     * 请求参数集合
     * 以seqId作为唯一标识
     * 用来记录请求是否超时
     */
    private final ConcurrentHashMap<String , Long> requestMap;

    /**
     * 响应结果
     * @since feature/0.0.6
     */
    private final ConcurrentHashMap<String, RpcResponse> responseMap;


    public DefaultInvokeService() {
        responseMap = new ConcurrentHashMap<>();
        requestMap = new ConcurrentHashMap<>();

        final Runnable timeoutThread = new TimeoutCheckThread(requestMap, responseMap);


        /**
         * 创建对象的时候直接开启线程,定期清理已经超时的线程
         **/
        Executors.newScheduledThreadPool(RpcConstant.SCHEDUL_NUM).scheduleAtFixedRate(timeoutThread, RpcConstant.INITIAL_TIME,RpcConstant.PERIOD, TimeUnit.SECONDS);
    }

    /**
     * 方式时判断接口是否超时
     * @param seqId 唯一标识
     * @param response 响应结果
     * @return
     */
    @Override
    public InvokeService addResponse(String seqId, RpcResponse response) {
        // 这里放入之前，可以添加判断。
        // 如果 seqId 必须处理请求集合中，才允许放入。或者直接忽略丢弃。
        log.info("[Client] 获取结果信息，seq: {}, rpcResponse: {}", seqId, response);

        Long timeoutMills = requestMap.get(seqId);

        if(timeoutMills < Times.time()){
            log.info("[Client] 接口已经超时：{}", seqId);
            response = RpcResponseFactory.Timeout();
        }

        responseMap.putIfAbsent(seqId, response);
        // 通知所有等待方
        log.info("[Client] 获取结果信息，seqId: {}, rpcResponse: {}", seqId, response);

        log.info("[Client] seqId:{} 信息已经放入，通知所有等待方", seqId);

        synchronized (this) {
            //准备进行通知，移除该请求
            responseMap.remove(seqId);
            //当接收到response后
            // 唤醒堵塞线程
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
                // 如果请求没有处理好，则将线程堵塞，等待唤醒
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
    public InvokeService addRequest(String seqId, Long timeoutMills) {
        log.info("[Client] start add request for seqId: {}", seqId);
        /**
         * 这个方法和原来的区别
         * 如果key存在不塞值且返回旧的值
         */
        timeoutMills += Times.time();
        requestMap.putIfAbsent(seqId, timeoutMills);
        return this;
    }
}
