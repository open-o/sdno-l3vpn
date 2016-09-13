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

package org.openo.sdno.model.db.vpn;

import org.openo.sdno.model.db.FieldConvertUtil;
import org.openo.sdno.model.db.PoModel;
import org.openo.sdno.model.servicemodel.businesstype.TunnelSchema;
import org.openo.sdno.model.servicemodel.mss.annotation.MOInvField;
import org.openo.sdno.model.servicemodel.mss.annotation.MOUUIDField;

/**
 * Abstract class for the tunnel schema PO.
 * 
 * @author
 * @version SDNO 0.5 Aug 2, 2016
 */
public abstract class AbstractTunnelSchemaPo implements PoModel<TunnelSchema> {

    @MOUUIDField
    @MOInvField(invName = "id")
    private String uuid;

    private String tunnelTech;

    private Integer tunnelLatency; // delay

    private String tunnelSelectMode;

    private String vpnId;

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTunnelTech() {
        return tunnelTech;
    }

    public void setTunnelTech(String tunnelTech) {
        this.tunnelTech = tunnelTech;
    }

    public Integer getTunnelLatency() {
        return tunnelLatency;
    }

    public void setTunnelLatency(Integer tunnelLatency) {
        this.tunnelLatency = tunnelLatency;
    }

    public String getTunnelSelectMode() {
        return tunnelSelectMode;
    }

    public void setTunnelSelectMode(String tunnelSelectMode) {
        this.tunnelSelectMode = tunnelSelectMode;
    }

    public String getVpnId() {
        return vpnId;
    }

    public void setVpnId(String vpnId) {
        this.vpnId = vpnId;
    }

    @Override
    public TunnelSchema toSvcModel() {
        final TunnelSchema svcModel = new TunnelSchema();
        FieldConvertUtil.setA2B(this, svcModel);
        return svcModel;
    }

    @Override
    public void fromSvcModel(TunnelSchema svcModel) {
        FieldConvertUtil.setA2B(svcModel, this);
    }

}
