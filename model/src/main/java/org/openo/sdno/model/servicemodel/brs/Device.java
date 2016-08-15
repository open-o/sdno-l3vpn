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

package org.openo.sdno.model.servicemodel.brs;

import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.model.servicemodel.BaseMO;
import org.openo.sdno.ssl.EncryptionUtil;

/**
 * The device class extends BaseMO.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public class Device extends BaseMO {

    private String commParamId;

    private String profileId;

    private int port;

    private String metaModelId;

    private String objectId;

    private String protocol;

    private String commParams;

    private String hostName;

    private String ip;

    private String user;

    private String pwd;

    public String getCommParamId() {
        return commParamId;
    }

    public void setCommParamId(final String commParamId) {
        this.commParamId = commParamId;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(final String profileId) {
        this.profileId = profileId;
    }

    public int getPort() {
        return port;
    }

    public void setPort(final int port) {
        this.port = port;
    }

    public String getMetaModelId() {
        return metaModelId;
    }

    public void setMetaModelId(final String metaModelId) {
        this.metaModelId = metaModelId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(final String objectId) {
        this.objectId = objectId;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(final String protocol) {
        this.protocol = protocol;
    }

    public String getCommParams() {
        return commParams;
    }

    public void setCommParams(final String commParams) {
        this.commParams = commParams;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(final String hostName) {
        this.hostName = hostName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(final String ip) {
        this.ip = ip;
    }

    public String getUser() {
        return user;
    }

    public void setUser(final String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(final String pwd) {
        this.pwd = pwd;
    }

    /**
     * Get authentication information.<br/>
     * 
     * @return The auth information
     * @since SDNO 0.5
     */
    public AuthInfo getAuthInfo() {
        AuthInfo authInfo = new AuthInfo();
        if((null != commParams) && !commParams.isEmpty()) {
            char[] decodedParams = EncryptionUtil.decode(commParams.toCharArray());

            if(null != decodedParams) {
                authInfo = JsonUtil.fromJson(String.valueOf(decodedParams), AuthInfo.class);
            }
        }

        return authInfo;
    }

    @Override
    public String toJsonBody() {
        return "";
    }

    @Override
    public String toString() {
        return "[ " + "id=" + id + ",commParamId:" + commParamId + ",profileId=" + profileId + ",port=" + port
                + ",metaModelId=" + metaModelId + ",objectId=" + objectId + ",protocol=" + protocol + ",hostName="
                + hostName + "]";
    }

}
