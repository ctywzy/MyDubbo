package rpc.server.service.impl;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import rpc.common.constant.PunctuationConst;
import rpc.server.config.service.ServiceConfig;
import rpc.server.service.ServiceFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @Description 服务处理工厂
 * @Author wangzy
 * @Date 2020/9/4 2:51 下午
 **/
@Slf4j
public class DefaultServiceFactory implements ServiceFactory {

    private static final DefaultServiceFactory INSTANCE = new DefaultServiceFactory();

    /**
     * 服务map,
     * key : 服务id
     * value :
     */
    private Map<String, Object> serviceMap;

    /**
     * 方法map
     */
    private Map<String, Method> methodMap;

    /**
     * 服务中需要被忽略的方法列表
     */
    private List<String> isIgnoreMethod;

    {
        isIgnoreMethod = Lists.newArrayList();
        isIgnoreMethod.add("wait");
        isIgnoreMethod.add("equals");
        isIgnoreMethod.add("toString");
        isIgnoreMethod.add("hashCode");
        isIgnoreMethod.add("getClass");
        isIgnoreMethod.add("notify");
        isIgnoreMethod.add("wait");
        serviceMap = Maps.newHashMap();

        methodMap = Maps.newHashMap();
    }
    /**
     * 获取单例对象
     * @return
     */
    public static DefaultServiceFactory getInstance() {
        return INSTANCE;
    }

    /**
     * 服务注册一般在项目启动的时候，进行处理。
     * 属于比较重的操作，而且一个服务按理说只应该初始化一次。
     * 此处加锁为了保证线程安全。
     * @param serviceConfigs 服务配置列表
     * @return this
     */
    @Override
    public synchronized ServiceFactory registerService(List<ServiceConfig> serviceConfigs) {

        serviceConfigs.stream().forEach(it -> {
            serviceMap.put(it.id(), it.reference());
        });

        // 存放方法名称
        for(Map.Entry<String, Object> entry : serviceMap.entrySet()) {
            String serviceId = entry.getKey();
            Object reference = entry.getValue();

            //获取服务中所有的方法
            Method[] methods = reference.getClass().getMethods();
            for(Method method : methods) {
                String methodName = method.getName();
                if(isIgnoreMethod.contains(methodName)) {
                    continue;
                }

                //获取参数列表
                List<String> paramTypeNames = getParamTypeNames(method);

                //生成方法对应的key值
                String key = buildMethodKey(serviceId, methodName, paramTypeNames);
                methodMap.put(key, method);
            }
        }

        return this;
    }

    @Override
    public Object invoke(String serviceId, String methodName, List<String> paramTypeNames, Object[] paramValues) {
        // 提供 cache，可以根据前三个值快速定位对应的 method
        // 根据 method 进行反射处理。
        // 对于 paramTypes 进行 string 连接处理。
        final Object reference = serviceMap.get(serviceId);
        final String methodKey = buildMethodKey(serviceId, methodName, paramTypeNames);
        final Method method = methodMap.get(methodKey);

        try {
            return method.invoke(reference, paramValues);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.info("DefaultServiceFactory.invoke.error:{}", Throwables.getStackTraceAsString(e));
        }
        return null;
    }

    /**
     * @description 获取方法的参数列表
     * @param method
     * @return
     */
    private List<String> getParamTypeNames(Method method) {
        Class<?>[] paramTypes = method.getParameterTypes();
        List<String> params = Lists.newArrayList();
        for(Class<?> param : paramTypes){
            params.add(param.getName());
        }
        return params;
    }

    /**
     * （1）多个之间才用 : 分隔
     * （2）参数之间采用 @ 分隔
     * @param serviceId 服务标识
     * @param methodName 方法名称
     * @param paramTypeNames 参数类型名称
     * @return 构建完整的 key
     * @since feature/0.0.6
     */
    private String buildMethodKey(String serviceId, String methodName, List<String> paramTypeNames) {
        String param = paramTypeNames.toString();
        return serviceId + PunctuationConst.COLON + methodName+PunctuationConst.COLON + param;
    }


}
