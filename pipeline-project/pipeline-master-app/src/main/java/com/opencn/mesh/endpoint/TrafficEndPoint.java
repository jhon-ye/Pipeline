package com.opencn.mesh.endpoint;import com.opencn.mesh.common.BizCommonServiceFactory;import com.opencn.mesh.log.PipelineLoggerFactory;import com.opencn.mesh.model.PipelineRequest;import com.opencn.mesh.model.PipelineResponse;import com.opencn.mesh.service.BizCommonService;import org.slf4j.Logger;import org.springframework.web.bind.annotation.*;import javax.annotation.Resource;@RestController("/")public class TrafficEndPoint {    private static final Logger pipelineLogger = PipelineLoggerFactory.getLogger(TrafficEndPoint.class);    @Resource    private BizCommonServiceFactory commonServiceFactory;    @PostMapping("/request")    @ResponseBody    public PipelineResponse req(@RequestBody PipelineRequest pipelineRequest,                                @RequestHeader(value = "biz-identify") String bizIdentify,                                @RequestHeader(value = "biz-key") String bizKey) {        try {            BizCommonService proxy = commonServiceFactory.getProxy(bizIdentify);            PipelineResponse response = proxy.invoke(bizKey, pipelineRequest);            pipelineLogger.debug("[pipeline] req success with bizIdentify:{} bizKey:{} data:{}",                    bizIdentify, bizKey, pipelineRequest.getBizData());            return response;        } catch (Exception e) {            pipelineLogger.error("[pipeline] req error with bizIdentify:{} bizKey:{} cause {}",                    bizIdentify, bizKey, e.getMessage());            return PipelineResponse.builder().success(false).data("plugin server error").build();        }    }    @PostMapping("/async-request")    @ResponseBody    public PipelineResponse asyncReq(@RequestBody PipelineRequest pipelineRequest,                                @RequestHeader(value = "biz-identify") String bizIdentify,                                @RequestHeader(value = "biz-key") String bizKey) {        try {            BizCommonService proxy = commonServiceFactory.getProxy(bizIdentify, false);            proxy.invoke(bizKey, pipelineRequest);            pipelineLogger.debug("[pipeline] req success with bizIdentify:{} bizKey:{} data:{}",                    bizIdentify, bizKey, pipelineRequest.getBizData());            return PipelineResponse.builder().success(true).build();        } catch (Exception e) {            pipelineLogger.error("[pipeline] req error with bizIdentify:{} bizKey:{} cause {}",                    bizIdentify, bizKey, e.getMessage());            return PipelineResponse.builder().success(false).error(e.getMessage()).build();        }    }    @PostMapping("/callback/{bizIdentify}/{bizKey}")    @ResponseBody    public PipelineResponse callback(@PathVariable String bizIdentify, @PathVariable String bizKey, @RequestBody Object request) {        try {            BizCommonService proxy = commonServiceFactory.getProxy(bizIdentify, false);            proxy.callback(bizKey, request);            pipelineLogger.debug("[pipeline] callback success with bizIdentify:{} bizKey:{} data:{}",                    bizIdentify, bizKey, request);            return PipelineResponse.builder().success(true).build();        } catch (Exception e) {            pipelineLogger.error("[pipeline] callback error with bizIdentify:{} bizKey:{} cause {}",                    bizIdentify, bizKey, e.getMessage());            return PipelineResponse.builder().success(false).error(e.getMessage()).build();        }    }}