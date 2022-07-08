package com.opencn.mesh.common;import com.alipay.sofa.rpc.boot.runtime.param.BoltBindingParam;import com.alipay.sofa.runtime.api.annotation.SofaClientFactory;import com.alipay.sofa.runtime.api.client.ReferenceClient;import com.alipay.sofa.runtime.api.client.param.BindingParam;import com.alipay.sofa.runtime.api.client.param.ReferenceParam;import com.opencn.mesh.service.BizCommonService;import org.springframework.stereotype.Component;@Componentpublic class BizCommonServiceFactory {    @SofaClientFactory    private ReferenceClient referenceClient;    public BizCommonService getProxy(String bizIdentify) {        ReferenceParam<BizCommonService> referenceParam = new ReferenceParam<>();        referenceParam.setInterfaceType(BizCommonService.class);        referenceParam.setUniqueId(bizIdentify);        referenceParam.setJvmFirst(true);        BoltBindingParam boltBindingParam = new BoltBindingParam();        boltBindingParam.setConnectTimeout(2000);        boltBindingParam.setTimeout(2000);        boltBindingParam.setRetries(3);        referenceParam.setBindingParam(boltBindingParam);        return referenceClient.reference(referenceParam);    }}