package com.opencn.mesh.service;import com.opencn.mesh.model.PipelineRequest;import com.opencn.mesh.model.PipelineResponse;public interface BizCommonService {    PipelineResponse invoke(PipelineRequest request);    PipelineResponse callback(String bizKey, Object request);//////    default PipelineResponse callback(PipelineRequest request) {////        throw new PipelineException("nop");////        //return  BizMappingRegister.execute(request.getBizKey(), request.getBizData());////    }}