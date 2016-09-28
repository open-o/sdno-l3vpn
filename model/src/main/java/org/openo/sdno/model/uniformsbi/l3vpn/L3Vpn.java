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

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.openo.sdno.model.uniformsbi.base.Vpn;

/**
 * Sbi model for L3VPN.<br>
 *
 * @author
 * @version SDNO 0.5 Aug 2, 2016
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class L3Vpn extends Vpn {

    @JsonProperty("mtu")
    private Integer ipMtu;

    private Boolean frr;

    private L3Acs acs;

    @JsonProperty("loopbackifs")
    private L3LoopbackIfs l3Loopbackifs;

    private TopologyService topologyService;

    private ProtectGroup protectGroup;

    private DiffServ diffServ;

    public Integer getIpMtu() {
        return ipMtu;
    }

    public void setIpMtu(Integer ipMtu) {
        this.ipMtu = ipMtu;
    }

    public L3Acs getAcs() {
        return acs;
    }

    public void setAcs(L3Acs acs) {
        this.acs = acs;
    }

    public L3LoopbackIfs getL3Loopbackifs() {
        return l3Loopbackifs;
    }

    public void setL3Loopbackifs(L3LoopbackIfs l3Loopbackifs) {
        this.l3Loopbackifs = l3Loopbackifs;
    }

    public TopologyService getTopologyService() {
        return topologyService;
    }

    public void setTopologyService(TopologyService topologyService) {
        this.topologyService = topologyService;
    }

    public ProtectGroup getProtectGroup() {
        return protectGroup;
    }

    public void setProtectGroup(ProtectGroup protectGroup) {
        this.protectGroup = protectGroup;
    }

    public Boolean getFrr() {
        return frr;
    }

    public void setFrr(Boolean frr) {
        this.frr = frr;
    }

    public DiffServ getDiffServ() {
        return diffServ;
    }

    public void setDiffServ(DiffServ diffServ) {
        this.diffServ = diffServ;
    }

}
