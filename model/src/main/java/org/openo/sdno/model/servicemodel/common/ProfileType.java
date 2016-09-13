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

package org.openo.sdno.model.servicemodel.common;

import org.openo.sdno.model.paradesc.CommentDesc;
import org.openo.sdno.model.paradesc.EnumDesc;
import org.openo.sdno.model.paradesc.NotNullDesc;
import org.openo.sdno.model.paradesc.StringDesc;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;
import org.openo.sdno.model.servicemodel.common.enumeration.ProfileLevel;

/**
 * profile type enumeration.<br>
 * 
 * @author
 * @version SDNO 0.5 Aug 9, 2016
 */
public class ProfileType extends AbstractSvcModel {

    @StringDesc(maxLen = 36)
    @CommentDesc(means = "serialNum")
    private String uuid;

    @NotNullDesc
    @EnumDesc(ProfileLevel.class)
    @CommentDesc(means = "level")
    private String profileLevel;

    @StringDesc(maxLen = 200)
    @CommentDesc(means = "domainName")
    private String domainId;

    @StringDesc(maxLen = 36)
    @CommentDesc(means = "businessTypeId")
    private String businessTypeId;

    public String getProfileLevel() {
        return profileLevel;
    }

    public void setProfileLevel(final String profileLevel) {
        this.profileLevel = profileLevel;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(String businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

}
