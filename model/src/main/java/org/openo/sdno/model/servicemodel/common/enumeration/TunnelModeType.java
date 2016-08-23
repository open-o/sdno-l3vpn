/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
 * The enum class of Tp type.<br/>
 * 
 * @author
 * @version SDNO 0.5 Aug 9, 2016
 */
public enum TunnelModeType implements CommonName {

    DELEGATE("delegate"), ONE_TO_ONE("one_to_one"), N_TO_ONE("n_to_one");

    private String commonName;

    /**
     * Constructor<br/>
     * 
     * @param commonName common name.
     * @since SDNO 0.5
     */
    TunnelModeType(String commonName) {
        this.commonName = commonName;
    }

    @Override
    public String getCommonName() {
        return commonName;
    }
}
