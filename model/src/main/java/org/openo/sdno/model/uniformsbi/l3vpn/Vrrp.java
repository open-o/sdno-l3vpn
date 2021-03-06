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

/**
 * VRRP class.<br>
 *
 * @author
 * @version SDNO 0.5 July 22, 2016
 */
public class Vrrp {

    private String virtualIp;

    private String vrrpTrackBfd;

    private BfdParameter bfdParameter;

    public String getVirtualIp() {
        return virtualIp;
    }

    public void setVirtualIp(String virtualIp) {
        this.virtualIp = virtualIp;
    }

    public String getVrrpTrackBfd() {
        return vrrpTrackBfd;
    }

    public void setVrrpTrackBfd(String vrrpTrackBfd) {
        this.vrrpTrackBfd = vrrpTrackBfd;
    }

    public BfdParameter getBfdParameter() {
        return bfdParameter;
    }

    public void setBfdParameter(BfdParameter bfdParameter) {
        this.bfdParameter = bfdParameter;
    }

}
