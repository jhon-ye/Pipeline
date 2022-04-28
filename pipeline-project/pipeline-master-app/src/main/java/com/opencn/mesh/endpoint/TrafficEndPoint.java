package com.opencn.mesh.endpoint;import com.alipay.sofa.rpc.boot.runtime.param.BoltBindingParam;import com.alipay.sofa.runtime.api.annotation.SofaClientFactory;import com.alipay.sofa.runtime.api.client.ReferenceClient;import com.alipay.sofa.runtime.api.client.param.BindingParam;import com.alipay.sofa.runtime.api.client.param.ReferenceParam;import com.alipay.sofa.runtime.service.binding.JvmBindingParam;import com.opencn.mesh.log.PipelineLogger;import com.opencn.mesh.model.PipelineRequest;import com.opencn.mesh.model.PipelineResponse;import com.opencn.mesh.service.BizCommonService;import org.springframework.web.bind.annotation.*;@RestController("/")public class TrafficEndPoint {    @SofaClientFactory    private ReferenceClient referenceClient;    @PostMapping("/request")    @ResponseBody    public PipelineResponse req(@RequestBody PipelineRequest pipelineRequest) {        BizCommonService proxy = getProxy(pipelineRequest);        PipelineLogger.info(" Req-Rsp Model With Biz Identify {} Biz Key {} And BizData {} ",                pipelineRequest.getBizIdentify(), pipelineRequest.getBizKey(), pipelineRequest.getBizData());        return proxy.invoke(pipelineRequest);    }    @PostMapping("/subscribe")    @ResponseBody    public PipelineResponse subscribe(@RequestBody PipelineRequest pipelineRequest) {        BizCommonService proxy = getProxy(pipelineRequest);        PipelineLogger.info(" Pub-Sub Model With Biz Identify {} Biz Key {} And BizData {} ",                pipelineRequest.getBizIdentify(), pipelineRequest.getBizKey(), pipelineRequest.getBizData());        return proxy.subscribe(pipelineRequest);    }    @PostMapping("/callback")    @ResponseBody    public PipelineResponse callback(@RequestBody PipelineRequest pipelineRequest) {        BizCommonService proxy = getProxy(pipelineRequest);        PipelineLogger.info(" Callback Model With Biz Identify {} Biz Key {} And BizData {} ",                pipelineRequest.getBizIdentify(), pipelineRequest.getBizKey(), pipelineRequest.getBizData());        return proxy.callback(pipelineRequest);    }    private BizCommonService getProxy(PipelineRequest pipelineRequest) {        ReferenceParam<BizCommonService> referenceParam = new ReferenceParam<>();        referenceParam.setInterfaceType(BizCommonService.class);        referenceParam.setUniqueId(pipelineRequest.getBizIdentify());        referenceParam.setJvmFirst(true);        BindingParam boltBindingParam = new BoltBindingParam();        referenceParam.setBindingParam(boltBindingParam);        return referenceClient.reference(referenceParam);    }}