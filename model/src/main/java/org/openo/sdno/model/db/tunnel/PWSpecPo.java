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
import org.openo.sdno.model.servicemodel.mss.annotation.MOResType;
import org.openo.sdno.model.servicemodel.mss.annotation.MOUUIDField;
import org.openo.sdno.model.servicemodel.tunnel.PWSpec;

/**
 * PW specification class.<br>
 * 
 * @author
 * @version SDNO 0.5 Aug 8, 2016
 */
@MOResType(infoModelName = "wan_pwspec")
public class PWSpecPo implements PoModel<PWSpec> {

    @MOUUIDField
    @MOInvField(invName = "id")
    private String uuid;

    private String tunnelSchemaId;

    private String controlWord;

    private String pwVlanAction;

    @Override
    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public void setTunnelSchemaId(String tunnelSchemaId) {
        this.tunnelSchemaId = tunnelSchemaId;
    }

    public String getTunnelSchemaId() {
        return tunnelSchemaId;
    }

    public void setControlWord(String controlWord) {
        this.controlWord = controlWord;
    }

    public String getControlWord() {
        return controlWord;
    }

    public void setPwVlanAction(String pwVlanAction) {
        this.pwVlanAction = pwVlanAction;
    }

    public String getPwVlanAction() {
        return pwVlanAction;
    }

    @Override
    public PWSpec toSvcModel() {
        final PWSpec svcModel = new PWSpec();
        FieldConvertUtil.setA2B(this, svcModel);
        return svcModel;
    }

    @Override
    public void fromSvcModel(final PWSpec svcModel) {
        FieldConvertUtil.setA2B(svcModel, this);
    }
}
