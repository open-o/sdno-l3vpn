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

package org.openo.sdno.model.uniformsbi.l3vpn;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;
import org.openo.sdno.model.uniformsbi.base.QosIfCar;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.AdminStatus;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.OperStatus;

/**
 * Sbi model, AC model class.<br/>
 *
 * @author
 * @version SDNO 0.5 Aug 2, 2016
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class L3Ac extends AbstractSvcModel {

    @JsonProperty("id")
    private String uuid;

    private String neId;

    private String ltpId;

    private String name;

    private String localName;

    private String tenantId;

    private String description;

    private AdminStatus adminStatus;

    private OperStatus operStatus;

    private QosIfCar upstreamBandwidth;

    private QosIfCar downstreamBandwidth;

    private L2Access l2Access;

    private L3Access l3Access;

    private String inboundQosPolicyId;

    private String outboundQosPolicyId;

    private String inboundQueuePolicyId;

    private String outboundQueuePolicyId;

    private String externalQosPolicyId;

    @Override
    public String getUuid() {
        return this.uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNeId() {
        return neId;
    }

    public void setNeId(String neId) {
        this.neId = neId;
    }

    public String getLtpId() {
        return ltpId;
    }

    public void setLtpId(String ltpId) {
        this.ltpId = ltpId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AdminStatus getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(AdminStatus adminStatus) {
        this.adminStatus = adminStatus;
    }

    public OperStatus getOperStatus() {
        return operStatus;
    }

    public void setOperStatus(OperStatus operStatus) {
        this.operStatus = operStatus;
    }

    public L2Access getL2Access() {
        return l2Access;
    }

    public void setL2Access(L2Access l2Access) {
        this.l2Access = l2Access;
    }

    public L3Access getL3Access() {
        return l3Access;
    }

    public void setL3Access(L3Access l3Access) {
        this.l3Access = l3Access;
    }

    public QosIfCar getUpstreamBandwidth() {
        return upstreamBandwidth;
    }

    public void setUpstreamBandwidth(QosIfCar upstreamBandwidth) {
        this.upstreamBandwidth = upstreamBandwidth;
    }

    public QosIfCar getDownstreamBandwidth() {
        return downstreamBandwidth;
    }

    public void setDownstreamBandwidth(QosIfCar downstreamBandwidth) {
        this.downstreamBandwidth = downstreamBandwidth;
    }

    public String getExternalQosPolicyId() {
        return externalQosPolicyId;
    }

    public void setExternalQosPolicyId(String externalQosPolicyId) {
        this.externalQosPolicyId = externalQosPolicyId;
    }

    public String getInboundQosPolicyId() {
        return inboundQosPolicyId;
    }

    public void setInboundQosPolicyId(String inboundQosPolicyId) {
        this.inboundQosPolicyId = inboundQosPolicyId;
    }

    public String getOutboundQosPolicyId() {
        return outboundQosPolicyId;
    }

    public void setOutboundQosPolicyId(String outboundQosPolicyId) {
        this.outboundQosPolicyId = outboundQosPolicyId;
    }

    public String getInboundQueuePolicyId() {
        return inboundQueuePolicyId;
    }

    public void setInboundQueuePolicyId(String inboundQueuePolicyId) {
        this.inboundQueuePolicyId = inboundQueuePolicyId;
    }

    public String getOutboundQueuePolicyId() {
        return outboundQueuePolicyId;
    }

    public void setOutboundQueuePolicyId(String outboundQueuePolicyId) {
        this.outboundQueuePolicyId = outboundQueuePolicyId;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

}
