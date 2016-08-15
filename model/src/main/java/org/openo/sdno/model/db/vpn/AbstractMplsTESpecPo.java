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

import org.openo.sdno.model.db.FieldConvertUtil;
import org.openo.sdno.model.db.PoModel;
import org.openo.sdno.model.servicemodel.mss.annotation.MOInvField;
import org.openo.sdno.model.servicemodel.mss.annotation.MOUUIDField;
import org.openo.sdno.model.servicemodel.tunnel.MplsTESpec;

/**
 * Abstract class for the MPLS TE specification.<br/>
 * 
 * @author
 * @version SDNO 0.5 Aug 4, 2016
 */
public abstract class AbstractMplsTESpecPo implements PoModel<MplsTESpec> {

    @MOUUIDField
    @MOInvField(invName = "id")
    private String uuid;

    private String bestEffort;

    private String shareMode;

    private String coRoute;

    private String bfdEnable;

    private String tunnelSchemaId;

    @Override
    public MplsTESpec toSvcModel() {
        final MplsTESpec svcModel = new MplsTESpec();
        FieldConvertUtil.setA2B(this, svcModel);
        return svcModel;
    }

    @Override
    public void fromSvcModel(final MplsTESpec svcModel) {
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

    public String getBestEffort() {
        return bestEffort;
    }

    public void setBestEffort(String bestEffort) {
        this.bestEffort = bestEffort;
    }

    public String getCoRoute() {
        return coRoute;
    }

    public String getBfdEnable() {
        return bfdEnable;
    }

    public String getShareMode() {
        return shareMode;
    }

    public void setShareMode(String shareMode) {
        this.shareMode = shareMode;
    }

    public void setCoRoute(String coRoute) {
        this.coRoute = coRoute;
    }

    public void setBfdEnable(String bfdEnable) {
        this.bfdEnable = bfdEnable;
    }

    public String getTunnelSchemaId() {
        return tunnelSchemaId;
    }

    public void setTunnelSchemaId(String tunnelSchemaId) {
        this.tunnelSchemaId = tunnelSchemaId;
    }
}
