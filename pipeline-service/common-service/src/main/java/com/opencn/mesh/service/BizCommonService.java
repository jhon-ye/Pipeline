package com.opencn.mesh.service;import com.opencn.mesh.model.PipelineRequest;import com.opencn.mesh.model.PipelineResponse;public interface BizCommonService {    PipelineResponse invoke(PipelineRequest request);    PipelineResponse subscribe(PipelineRequest request);    PipelineResponse callback(PipelineRequest request);}