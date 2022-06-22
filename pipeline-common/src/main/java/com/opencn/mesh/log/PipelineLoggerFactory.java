/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.opencn.mesh.log;

import com.alipay.sofa.common.log.MultiAppLoggerSpaceManager;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;


public class PipelineLoggerFactory {

    /***
     * pipeline log space
     */
    public static final String PIPELINE_LOG_SPACE = "com.opencn.mesh";

    static {
        // Note: this step is important, as in Ark environment your SDK may be used in module dependency
        // and will be initialized multiple times.
        if (!MultiAppLoggerSpaceManager.isSpaceInitialized(PIPELINE_LOG_SPACE)) {
            Map spaceIdProperties = new HashMap<String, String>();
            // Initialize your parameters here
            MultiAppLoggerSpaceManager.init(PIPELINE_LOG_SPACE, spaceIdProperties);
        }
    }


    /**
     * get Logger Object
     *
     * @param name name of the logger
     * @return Logger Object
     */
    public static Logger getLogger(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        return MultiAppLoggerSpaceManager.getLoggerBySpace(name, PIPELINE_LOG_SPACE);
    }

    /**
     * get Logger Object
     * @param clazz the returned logger will be named after clazz
     * @return Logger Object
     */
    public static Logger getLogger(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        return getLogger(clazz.getCanonicalName());
    }
}
