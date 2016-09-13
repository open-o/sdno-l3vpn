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

package org.openo.sdno.model.servicemodel.tp;

import org.openo.sdno.model.db.FieldConvertUtil;

/**
 * The service model class of SiteTp.<br>
 * 
 * @author
 * @version SDNO 0.5 Aug 9, 2016
 */
public class SiteTp extends Tp {

    private String siteName;

    private String peName;

    private String serviceType;

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     */
    public SiteTp() {
        super();
    }

    /**
     * Constructor.<br>
     * 
     * @param tp TP
     * @since SDNO 0.5
     */
    public SiteTp(final Tp tp) {
        super();
        FieldConvertUtil.setA2B(tp, this);

    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(final String siteName) {
        this.siteName = siteName;
    }

    public String getPeName() {
        return peName;
    }

    public void setPeName(final String peName) {
        this.peName = peName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(final String serviceType) {
        this.serviceType = serviceType;
    }

}
