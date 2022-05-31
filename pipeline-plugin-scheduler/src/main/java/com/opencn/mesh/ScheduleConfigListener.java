package com.opencn.mesh;import com.alipay.sofa.ark.api.ArkClient;import com.alipay.sofa.ark.api.ClientResponse;import com.alipay.sofa.ark.common.util.FileUtils;import com.ctrip.framework.apollo.Config;import com.ctrip.framework.apollo.enums.PropertyChangeType;import com.ctrip.framework.apollo.model.ConfigChange;import com.ctrip.framework.apollo.model.ConfigChangeEvent;import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;import com.ctrip.framework.apollo.openapi.dto.NamespaceReleaseDTO;import com.ctrip.framework.apollo.openapi.dto.OpenItemDTO;import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;import com.google.gson.Gson;import com.google.gson.GsonBuilder;import com.opencn.mesh.log.PipelineLogger;import com.opencn.mesh.util.AddressUtils;import org.springframework.beans.factory.annotation.Value;import org.springframework.boot.CommandLineRunner;import org.springframework.core.Ordered;import org.springframework.stereotype.Component;import org.springframework.util.ObjectUtils;import org.springframework.util.StringUtils;import javax.annotation.Resource;import java.io.File;import java.net.Inet4Address;import java.net.URL;import java.util.Map;import java.util.Optional;@Componentpublic class ScheduleConfigListener implements CommandLineRunner, Ordered {    private static final Gson G = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();    @ApolloConfig("pipeline-instance.json")    private Config instanceConfig;    @Resource    private PipelineApolloEnvConfig envConfig;    @Resource    private ApolloOpenApiClient client;    @Value("${server.port}")    private String port;    @ApolloConfigChangeListener(value = {"pipeline-instance.json"})    public void onChange(ConfigChangeEvent changeEvent) {        try {            Optional<Inet4Address> localIp4Address = AddressUtils.getLocalIp4Address();            String instanceName = localIp4Address.get().getHostAddress().concat("@").concat(port);            for(String key: changeEvent.changedKeys()) {                ConfigChange change = changeEvent.getChange(key);                if(key.equals("content") && PropertyChangeType.MODIFIED.equals(change.getChangeType())) {                    String oldValue = change.getOldValue();                    String newValue = change.getNewValue();                    PipelineInstanceConfig oldInstanceConfig = G.fromJson(oldValue, PipelineInstanceConfig.class);                    PipelineInstanceConfig newInstanceConfig = G.fromJson(newValue, PipelineInstanceConfig.class);                    PipelineInstanceConfig.InstanceConfig oldInstance = oldInstanceConfig.getInstances().get(instanceName);                    Map<String, PipelineInstanceConfig.PluginConfig> oldPlugins = oldInstance.getPlugins();                    PipelineInstanceConfig.InstanceConfig newInstance = newInstanceConfig.getInstances().get(instanceName);                    Map<String, PipelineInstanceConfig.PluginConfig> newPlugins = newInstance.getPlugins();                    newPlugins.forEach((identify, plugin) -> {                        if (!oldPlugins.containsKey(identify)) {                            // install                            operateBiz(plugin.getName(), plugin.getVersion(), plugin.getRepo(), "INSTALL");                        }                    });                    oldPlugins.forEach((identify, plugin) -> {                        if (!newPlugins.containsKey(identify)) {                            // uninstall                            operateBiz(plugin.getName(), plugin.getVersion(), plugin.getRepo(), "UNINSTALL");                        }                    });                }            }        } catch (Exception e) {        }    }    @Override    public void run(String... args) throws Exception {            Optional<Inet4Address> localIp4Address = AddressUtils.getLocalIp4Address();            if (localIp4Address.isPresent()) {                String instanceName = localIp4Address.get().getHostAddress().concat("@").concat(port);                String instancesStr = instanceConfig.getProperty("content", null);                PipelineInstanceConfig pipelineInstanceConfig;                if (StringUtils.hasText(instancesStr)) {                    pipelineInstanceConfig = G.fromJson(instancesStr, PipelineInstanceConfig.class);                    Map<String ,PipelineInstanceConfig.InstanceConfig> instances = pipelineInstanceConfig.getInstances();                    // reload plugins of myself                    if (instances.containsKey(instanceName)) {                        PipelineInstanceConfig.InstanceConfig instance = instances.get(instanceName);                        Map<String, PipelineInstanceConfig.PluginConfig> plugins = instance.getPlugins();                        if (instance.isRestart() && !ObjectUtils.isEmpty(plugins)) {                            plugins.forEach((identify, plugin) -> {                                PipelineLogger.debug("[ScheduleConfig] load plugin {} in instance {}", identify, instanceName);                                operateBiz(plugin.getName(), plugin.getVersion(), plugin.getRepo(), "INSTALL");                            });                            return;                        } else if (!instance.isRestart()) {                            instance.setRestart(true);                        }                    } else {                        // add instance of myself                        instances.put(instanceName, PipelineInstanceConfig.InstanceConfig.builder().restart(true).build());                    }                } else {                    // no  pipeline-instance.json create new                    pipelineInstanceConfig = PipelineInstanceConfig.builder().build();                }                OpenItemDTO item = client.getItem(envConfig.getAppId(), envConfig.getEnv(), "default", "pipeline-instance.json", "content");                item.setValue(G.toJson(pipelineInstanceConfig));                client.createOrUpdateItem(envConfig.getAppId(), envConfig.getEnv(), "default","pipeline-instance.json", item);                NamespaceReleaseDTO namespaceReleaseDTO = new NamespaceReleaseDTO();                namespaceReleaseDTO.setReleasedBy("jhon.ye");                namespaceReleaseDTO.setEmergencyPublish(false);                namespaceReleaseDTO.setReleaseTitle("test");                client.publishNamespace(envConfig.getAppId(), envConfig.getEnv(), "default","pipeline-instance.json", namespaceReleaseDTO);            }    }    private ClientResponse operateBiz(String bizName, String bizVersion, String repo, String operateType) {        try {            URL url = new URL(repo);            File bizFile = ArkClient.createBizSaveFile(bizName, bizVersion);            FileUtils.copyInputStreamToFile(url.openStream(), bizFile);            switch (operateType) {                case "INSTALL":                    return ArkClient.installBiz(bizFile);                case "UNINSTALL":                default:                    return null;            }        } catch (Throwable e) {            PipelineLogger.error("[pipeline] start plugin  {} failed cause {}",                    bizName+ ":" + bizVersion, e.getMessage());        }        return null;    }    @Override    public int getOrder() {        return Integer.MIN_VALUE + 1;    }}