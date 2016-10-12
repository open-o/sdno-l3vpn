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

package org.openo.sdno.model.db.common;

import org.openo.sdno.model.db.FieldConvertUtil;
import org.openo.sdno.model.db.PoModel;
import org.openo.sdno.model.servicemodel.common.ProfileType;
import org.openo.sdno.model.servicemodel.mss.annotation.MOInvField;
import org.openo.sdno.model.servicemodel.mss.annotation.MOUUIDField;

/**
 * Abstract profile type PO class.
 * 
 * @author
 * @version SDNO 0.5 August 2, 2016
 */
public abstract class ProfileTypePo implements PoModel<ProfileType> {

    @MOUUIDField
    @MOInvField(invName = "id")
    private String uuid;

    private String profileLevel;

    private String domainId;

    @MOInvField(invName = "businesstypeid")
    private String businessTypeId;

    private String vpnPolicyId;

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    @Override
    public ProfileType toSvcModel() {
        final ProfileType svcModel = new ProfileType();
        FieldConvertUtil.setA2B(this, svcModel);
        return svcModel;
    }

    @Override
    public void fromSvcModel(final ProfileType svcModel) {
        FieldConvertUtil.setA2B(svcModel, this);
    }

    public String getProfileLevel() {
        return profileLevel;
    }

    public void setProfileLevel(final String profileLevel) {
        this.profileLevel = profileLevel;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(final String domainId) {
        this.domainId = domainId;
    }

    public String getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(final String businesstypeid) {
        this.businessTypeId = businesstypeid;
    }

    public String getVpnPolicyId() {
        return vpnPolicyId;
    }

    public void setVpnPolicyId(final String vpnPolicyId) {
        this.vpnPolicyId = vpnPolicyId;
    }

}
