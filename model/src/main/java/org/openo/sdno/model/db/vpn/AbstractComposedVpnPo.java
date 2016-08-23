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

package org.openo.sdno.model.db.vpn;

import java.util.List;

import org.openo.sdno.model.common.NVString;
import org.openo.sdno.model.db.FieldConvertUtil;
import org.openo.sdno.model.db.PoModel;
import org.openo.sdno.model.servicemodel.mss.annotation.MOInvField;
import org.openo.sdno.model.servicemodel.mss.annotation.MOUUIDField;
import org.openo.sdno.model.servicemodel.mss.annotation.NONInvField;
import org.openo.sdno.model.servicemodel.vpn.ComposedVpn;

/**
 * Abstract class for the composed VPN.
 * 
 * @author
 * @version SDNO 0.5 Aug 2, 2016
 */
public abstract class AbstractComposedVpnPo implements PoModel<ComposedVpn> {

    @MOUUIDField
    @MOInvField(invName = "id")
    private String uuid;

    private String name;

    private String tenantId;

    private String description;

    private String businessTypeId;

    private String vpnBasicInfoId;

    private String operStatus;

    private String syncStatus;

    private String startTime;

    private String endTime;

    @NONInvField
    private List<NVString> addtionalInfo;

    public AbstractComposedVpnPo() {
    }

    /**
     * Constructor.<br/>
     * 
     * @param uuid
     * @param operStatus
     * @since SDNO 0.5
     */
    public AbstractComposedVpnPo(final String uuid, final String operStatus) {
        this.uuid = uuid;
        this.operStatus = operStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(final String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(final String endTime) {
        this.endTime = endTime;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(final String tenantId) {
        this.tenantId = tenantId;
    }

    public String getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(final String businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(final String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getOperStatus() {
        return operStatus;
    }

    public void setOperStatus(final String operStatus) {
        this.operStatus = operStatus;
    }

    public String getVpnBasicInfoId() {
        return vpnBasicInfoId;
    }

    public void setVpnBasicInfoId(final String vpnBasicInfoId) {
        this.vpnBasicInfoId = vpnBasicInfoId;
    }

    public List<NVString> getAddtionalInfo() {
        return addtionalInfo;
    }

    public void setAddtionalInfo(final List<NVString> addtionalInfo) {
        this.addtionalInfo = addtionalInfo;
    }

    @Override
    public ComposedVpn toSvcModel() {
        final ComposedVpn svcModel = new ComposedVpn();
        FieldConvertUtil.setA2B(this, svcModel);
        return svcModel;
    }

    @Override
    public void fromSvcModel(final ComposedVpn svcModel) {
        FieldConvertUtil.setA2B(svcModel, this);
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }
}
