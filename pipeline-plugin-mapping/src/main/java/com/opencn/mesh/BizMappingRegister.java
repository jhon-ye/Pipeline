package com.opencn.mesh;import com.alipay.sofa.ark.api.ArkClient;import com.alipay.sofa.rpc.boot.runtime.param.BoltBindingParam;import com.alipay.sofa.runtime.api.client.ClientFactory;import com.alipay.sofa.runtime.api.client.ServiceClient;import com.alipay.sofa.runtime.api.client.param.BindingParam;import com.alipay.sofa.runtime.api.client.param.ServiceParam;import com.google.gson.Gson;import com.google.gson.GsonBuilder;import com.opencn.mesh.exception.PipelineException;import com.opencn.mesh.model.PipelineResponse;import com.opencn.mesh.service.BizCommonService;import org.springframework.aop.support.AopUtils;import org.springframework.context.ApplicationContext;import org.springframework.util.ObjectUtils;import org.springframework.util.ReflectionUtils;import java.lang.reflect.Method;import java.lang.reflect.Parameter;import java.nio.charset.StandardCharsets;import java.util.ArrayList;import java.util.HashMap;import java.util.List;import java.util.Map;import java.util.Set;import java.util.concurrent.ConcurrentMap;public class BizMappingRegister {    private static ConcurrentMap<String, Method> bizMappings;    private static ConcurrentMap<String, Method> originalBizMappings;    private static ConcurrentMap<String, Object> bizService;    private final static ConcurrentMap<String, ConcurrentMap<String, Object>> CM2 =  ArkClient.getBizManagerService().getBizService();    private final static ConcurrentMap<String, ConcurrentMap<String, Method>> MM2 =  ArkClient.getBizManagerService().getBizMappings();    private final static ConcurrentMap<String, ConcurrentMap<String, Method>> MM2Original =  ArkClient.getBizManagerService().getOriginalBizMappings();    private static final Gson G = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();    public static ApplicationContext applicationContext;    public static void registry(String bizIdentify, String bizKey, Object targetBean, Method method) {        if (CM2.containsKey(bizIdentify)) {            bizService = CM2.get(bizIdentify);            Object old = CM2.get(bizIdentify).putIfAbsent(bizKey, targetBean);            if (!ObjectUtils.isEmpty(old)) {                throw new PipelineException("not unique bizKey within biz named " + bizIdentify);            }        }        if (MM2.containsKey(bizIdentify)) {            bizMappings = MM2.get(bizIdentify);            MM2.get(bizIdentify).putIfAbsent(bizKey, method);        }        //CM.put(bizKey, targetBean);        //MM.put(bizKey, method);    }    public static void registryOriginalMethod(String bizIdentify, String bizKey, Method method) {        if (MM2Original.containsKey(bizIdentify)) {            originalBizMappings = MM2Original.get(bizIdentify);            MM2Original.get(bizIdentify).putIfAbsent(bizKey, method);        }    }    public static void routerRegistry(ClientFactory clientFactory, String bizIdentify) {        if (CM2.containsKey(bizIdentify) && ObjectUtils.isEmpty(CM2.get(bizIdentify))) {            ServiceClient serviceClient = clientFactory.getClient(ServiceClient.class);            ServiceParam serviceParam = new ServiceParam();            serviceParam.setUniqueId(bizIdentify);            serviceParam.setInterfaceType(BizCommonService.class);            serviceParam.setInstance(new DefaultBizCommonService());            List<BindingParam> params = new ArrayList<>();            BoltBindingParam serviceBindingParam = new BoltBindingParam();            serviceBindingParam.setConnectTimeout(5000);            serviceBindingParam.setTimeout(5000);            serviceBindingParam.setRetries(3);            params.add(serviceBindingParam);            serviceParam.setBindingParams(params);            serviceClient.service(serviceParam);        }    }    public static PipelineResponse execute(String bizKey, Object... args) {        Method method = originalBizMappings.get(bizKey);        Method proxyMethod = bizMappings.get(bizKey);        Object targetBean = bizService.get(bizKey);        if (ObjectUtils.isEmpty(method) || ObjectUtils.isEmpty(targetBean) || ObjectUtils.isEmpty(proxyMethod)) {            String error = "can not find biz method or biz service with biz key, check service is added annotation named BizService or BizMapping";            return PipelineResponse.builder().data(error).success(true).build();        }        Class<?>[] parameterTypes = method.getParameterTypes();        Parameter[] parameters = method.getParameters();        int length = parameterTypes.length;        try {            Map bizMap = G.fromJson(G.toJson(args[0]), Map.class);            Object data;            if (length == 0) {                data = ReflectionUtils.invokeMethod(proxyMethod, targetBean);            } else {                Object[] bizArgs = new Object[length];                for (int i = 0; i < length; i++) {                    Object bizObj = bizMap.get(parameters[i].getName());                    if(ObjectUtils.isEmpty(bizObj)) {                        String error = "can not find biz method param named " + parameters[i].getName() + ", check param";                        return PipelineResponse.builder().data(error).success(true).build();                    }                    bizArgs[i] = G.fromJson(G.toJson(bizObj), parameterTypes[i]);                }                data = ReflectionUtils.invokeMethod(proxyMethod, targetBean, bizArgs);            }            // fix bug must translate data to Object            return PipelineResponse.builder().data(G.fromJson(G.toJson(data), Object.class)).success(true).build();        } catch (Exception e) {            return PipelineResponse.builder().error(e.getMessage()).success(false).build();        }    }    public static void destroy() {        //CM.clear();        //MM.clear();    }}