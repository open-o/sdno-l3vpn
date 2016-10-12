/*
 * Copyright 2016 Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.model.servicemodel.common.enumeration;

import org.openo.sdno.model.common.CommonName;

/**
 * The enumerationF class of reversion mode.<br>
 * 
 * @author
 * @version SDNO 0.5 August 9, 2016
 */
public enum ReversionMode implements CommonName {
    UNKNOWN("RM_UNKNOWN"), NON_REVERTIVE("RM_NON_REVERTIVE"), REVERTIVE("RM_REVERTIVE");

    private String commonName;

    /**
     * Constructor<br>
     * 
     * @param commonName common name.
     * @since SDNO 0.5
     */
    ReversionMode(String commonName) {
        this.commonName = commonName;
    }

    @Override
    public String getCommonName() {
        return commonName;
    }
}
