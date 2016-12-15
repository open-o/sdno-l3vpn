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

package org.openo.sdno.model.db.tunnel;

import org.openo.sdno.model.db.FieldConvertUtil;
import org.openo.sdno.model.db.PoModel;
import org.openo.sdno.model.servicemodel.mss.annotation.MOInvField;
import org.openo.sdno.model.servicemodel.mss.annotation.MOUUIDField;
import org.openo.sdno.model.servicemodel.tunnel.TunnelSpecificPathConstraint;
import org.springframework.util.CollectionUtils;

/**
 * TunnelSpecificPathConstraintPo abstract class.<br>
 *
 * @author
 * @version SDNO 0.5 August 8, 2016
 */
public abstract class AbstractTunnelSpecificPathConstraintPo implements PoModel<TunnelSpecificPathConstraint> {

    private static final long serialVersionUID = -1729606210736655591L;

    @MOUUIDField
    @MOInvField(invName = "id")
    private String uuid;

    private String vpnId;

    private String pathConnectionId;

    private String tunnelWorkMode;

    private String tunnelTech;

    private Long bandwitdh;

    private String bodMode;

    private String bindingTunnels;

    public String getVpnId() {
        return vpnId;
    }

    public void setVpnId(final String vpnId) {
        this.vpnId = vpnId;
    }

    public String getPathConnectionId() {
        return pathConnectionId;
    }

    public void setPathConnectionId(final String pathConnectionId) {
        this.pathConnectionId = pathConnectionId;
    }

    public String getTunnelWorkMode() {
        return tunnelWorkMode;
    }

    public void setTunnelWorkMode(final String tunnelWorkMode) {
        this.tunnelWorkMode = tunnelWorkMode;
    }

    public String getTunnelTech() {
        return tunnelTech;
    }

    public void setTunnelTech(final String tunnelTech) {
        this.tunnelTech = tunnelTech;
    }

    public Long getBandwitdh() {
        return bandwitdh;
    }

    public void setBandwitdh(final Long bandwitdh) {
        this.bandwitdh = bandwitdh;
    }

    public String getBodMode() {
        return bodMode;
    }

    public void setBodMode(String bodMode) {
        this.bodMode = bodMode;
    }

    public String getBindingTunnels() {
        return bindingTunnels;
    }

    public void setBindingTunnels(final String bindingTunnels) {
        this.bindingTunnels = bindingTunnels;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public TunnelSpecificPathConstraint toSvcModel() {
        final TunnelSpecificPathConstraint svcModel = new TunnelSpecificPathConstraint();
        FieldConvertUtil.setA2B(this, svcModel);
        svcModel.setBandwidth(bandwitdh);
        if(this.bindingTunnels != null) {
            svcModel.setBindingTunnels(CollectionUtils.arrayToList(bindingTunnels.split(",")));
        }
        return svcModel;
    }

    @Override
    public void fromSvcModel(final TunnelSpecificPathConstraint svcModel) {
        FieldConvertUtil.setA2B(svcModel, this);
        this.setBandwitdh(svcModel.getBandwidth());
        if(!CollectionUtils.isEmpty(svcModel.getBindingTunnels())) {
            StringBuilder tunnelSb = new StringBuilder(100);
            for(String tunnel : svcModel.getBindingTunnels()) {
                tunnelSb.append(tunnel).append(',');
            }
            this.bindingTunnels = tunnelSb.substring(0, tunnelSb.length() - 1);
        }
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
