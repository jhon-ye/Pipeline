package com.opencn.mesh.service;import com.opencn.mesh.model.BizModel;public interface BizCommonService {    <T> T request(BizModel<?> bizModel);    void subscribe(BizModel<?> bizModel);}