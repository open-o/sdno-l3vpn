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

package org.openo.sdno.model.servicemodel.businesstype;

import org.openo.sdno.model.paradesc.CommentDesc;
import org.openo.sdno.model.paradesc.EnumDesc;
import org.openo.sdno.model.paradesc.NotNullDesc;
import org.openo.sdno.model.paradesc.StringDesc;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;
import org.openo.sdno.model.servicemodel.common.enumeration.ExterAsVpnConnectPolicyType;
import org.openo.sdno.model.servicemodel.common.enumeration.MainDomainConstrain;

/**
 * DomainJointSchema class.<br>
 * 
 * @author
 * @version SDNO 0.5 August 4, 2016
 */
public class DomainJointSchema extends AbstractSvcModel {

    @StringDesc(maxLen = 36)
    private String uuid;

    @EnumDesc(ExterAsVpnConnectPolicyType.class)
    @CommentDesc(means = "Domain connect poclicy")
    private String exterAsVpnConnectPolicy;

    @NotNullDesc
    @EnumDesc(MainDomainConstrain.class)
    @CommentDesc(means = "Is main domain or not")
    private String mainDomainVPN;

    private String vpnInstanceNumber;

    public String getExterAsVpnConnectPolicy() {
        return exterAsVpnConnectPolicy;
    }

    public void setExterAsVpnConnectPolicy(String exterAsVpnConnectPolicy) {
        this.exterAsVpnConnectPolicy = exterAsVpnConnectPolicy;
    }

    public String getMainDomainVPN() {
        return mainDomainVPN;
    }

    public void setMainDomainVPN(String mainDomainVPN) {
        this.mainDomainVPN = mainDomainVPN;
    }

    public String getVpnInstanceNumber() {
        return vpnInstanceNumber;
    }

    public void setVpnInstanceNumber(String vpnInstanceNumber) {
        this.vpnInstanceNumber = vpnInstanceNumber;
    }

    @Override
    public String getUuid() {

        return uuid;
    }

    @Override
    public void setUuid(String uuid) {

        this.uuid = uuid;
    }

}
