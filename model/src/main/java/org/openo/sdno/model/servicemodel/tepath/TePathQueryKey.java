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

package org.openo.sdno.model.servicemodel.tepath;

import java.util.HashMap;
import java.util.Map;

/**
 * TE Path Query Key class<br/>
 * 
 * @author
 * @version SDNO 0.5 Aug 4, 2016
 */
public class TePathQueryKey {

    private String vpnId;

    private String srcNeId;

    private String destNeId;

    private String srcAcId;

    private String destAcId;

    /**
     * Constructor<br/>
     * 
     * @param vpnId vpn uuid
     * @param srcNeId source Ne uuid
     * @param destNeId destination Ne uuid
     * @param srcAcId source Ac uuid
     * @param destAcId destination uuid
     * @since SDNO 0.5
     */
    public TePathQueryKey(String vpnId, String srcNeId, String destNeId, String srcAcId, String destAcId) {
        this.vpnId = vpnId;
        this.srcNeId = srcNeId;
        this.destNeId = destNeId;
        this.srcAcId = srcAcId;
        this.destAcId = destAcId;
    }

    /**
     * @return the vpnId
     */
    public final String getVpnId() {
        return vpnId;
    }

    /**
     * @param vpnId the vpnId to set
     */
    public final void setVpnId(String vpnId) {
        this.vpnId = vpnId;
    }

    /**
     * @return the srcNeId
     */
    public final String getSrcNeId() {
        return srcNeId;
    }

    /**
     * @param srcNeId the srcNeId to set
     */
    public final void setSrcNeId(String srcNeId) {
        this.srcNeId = srcNeId;
    }

    /**
     * @return the destNeId
     */
    public final String getDestNeId() {
        return destNeId;
    }

    /**
     * @param destNeId the destNeId to set
     */
    public final void setDestNeId(String destNeId) {
        this.destNeId = destNeId;
    }

    /**
     * @return the srcAcId
     */
    public final String getSrcAcId() {
        return srcAcId;
    }

    /**
     * @param srcAcId the srcAcId to set
     */
    public final void setSrcAcId(String srcAcId) {
        this.srcAcId = srcAcId;
    }

    /**
     * @return the destAcId
     */
    public final String getDestAcId() {
        return destAcId;
    }

    /**
     * @param destAcId the destAcId to set
     */
    public final void setDestAcId(String destAcId) {
        this.destAcId = destAcId;
    }

    /**
     * get the paramaters.<br/>
     * 
     * @return map of paramaters.
     * @since SDNO 0.5
     */
    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("vpnId", vpnId);
        params.put("srcNeId", srcNeId);
        params.put("destNeId", destNeId);
        params.put("srcAcId", srcAcId);
        params.put("destAcId", destAcId);

        return params;
    }

    /**
     * get the object paramaters.<br/>
     * 
     * @return map of paramaters.
     * @since SDNO 0.5
     */
    public Map<String, Object> getObjectParams() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("vpnId", vpnId);
        params.put("srcNeId", srcNeId);
        params.put("destNeId", destNeId);
        params.put("srcAcId", srcAcId);
        params.put("destAcId", destAcId);
        return params;
    }
}
