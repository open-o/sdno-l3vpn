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

package org.openo.sdno.model.uniformsbi.l3vpn;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.openo.sdno.model.paradesc.NoPrintField;

/**
 * Bgp route class.<br>
 * 
 * @author
 * @version SDNO 0.5 Jul 22, 2016
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class BgpRoute {

    private String remoteAs;

    private String localAs;

    private String peerIp;

    private String localIp;

    private Integer keepaliveTime;

    private Integer holdTime;

    @NoPrintField
    private String password;

    private boolean advertiseCommunity;

    private boolean advertiseExtCommunity;

    public String getRemoteAs() {
        return remoteAs;
    }

    public void setRemoteAs(String remoteAs) {
        this.remoteAs = remoteAs;
    }

    public String getLocalAs() {
        return localAs;
    }

    public void setLocalAs(String localAs) {
        this.localAs = localAs;
    }

    public String getPeerIp() {
        return peerIp;
    }

    public void setPeerIp(String peerIp) {
        this.peerIp = peerIp;
    }

    public String getLocalIp() {
        return localIp;
    }

    public void setLocalIp(String localIp) {
        this.localIp = localIp;
    }

    public Integer getKeepaliveTime() {
        return keepaliveTime;
    }

    public void setKeepaliveTime(Integer keepaliveTime) {
        this.keepaliveTime = keepaliveTime;
    }

    public Integer getHoldTime() {
        return holdTime;
    }

    public void setHoldTime(Integer holdTime) {
        this.holdTime = holdTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdvertiseCommunity() {
        return advertiseCommunity;
    }

    public void setAdvertiseCommunity(boolean advertiseCommunity) {
        this.advertiseCommunity = advertiseCommunity;
    }

    public boolean isAdvertiseExtCommunity() {
        return advertiseExtCommunity;
    }

    public void setAdvertiseExtCommunity(boolean advertiseExtCommunity) {
        this.advertiseExtCommunity = advertiseExtCommunity;
    }

}
