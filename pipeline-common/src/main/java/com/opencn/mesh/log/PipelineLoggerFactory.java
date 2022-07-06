package com.opencn.mesh.log;

import com.alipay.sofa.common.log.CommonLoggingConfigurations;
import com.alipay.sofa.common.log.MultiAppLoggerSpaceManager;
import org.slf4j.Logger;

import java.util.Map;


/**
 * Customized logger factory
 *
 */
public class PipelineLoggerFactory {

    private static final String BOLT_LOG_SPACE            = "com.opencn.mesh";

    static {
        Map<String, String> externalConfigurations = CommonLoggingConfigurations.getExternalConfigurations();
        MultiAppLoggerSpaceManager.init(BOLT_LOG_SPACE, externalConfigurations);
    }


    public static Logger getLogger(Class<?> clazz) {
        if (clazz == null) {
            return getLogger("");
        }
        return getLogger(clazz.getCanonicalName());
    }

    public static Logger getLogger(String name) {
        if (name == null || name.isEmpty()) {
            return MultiAppLoggerSpaceManager.getLoggerBySpace("", BOLT_LOG_SPACE);
        }
        return MultiAppLoggerSpaceManager.getLoggerBySpace(name, BOLT_LOG_SPACE);
    }
}