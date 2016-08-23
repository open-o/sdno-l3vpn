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

package org.openo.sdno.model.servicemodel.brs;

import java.util.HashMap;
import java.util.Map;

import org.openo.sdno.model.servicemodel.BaseMO;
import org.openo.sdno.framework.container.util.JsonUtil;

/**
 * The data model class of Logical Termination Point.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public class LtpMO extends BaseMO {

    private static final String MOKEY = "logicalTerminationPoint";

    private String description;

    private String meID;

    private String logicalType;

    private String layerRate;

    private String isEdgePoint;

    private String portIndex;

    private String source;

    private String owner;

    private String ipAddress;

    private String direction;

    private String phyBW;

    private String ipMask;

    private String adminState;

    private String operState;

    public static final String NE_ID = "meID";

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     */
    public LtpMO() {
        super();
    }

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     * @param paramMap The map of parameters to set
     */
    public LtpMO(Map<String, String> paramMap) {
        super();
        if(null != paramMap) {
            this.id = paramMap.get("id");
            this.meID = paramMap.get("meid");
            this.name = paramMap.get("name");
            this.description = paramMap.get("description");
            this.ipAddress = paramMap.get("ipaddress");
            this.ipMask = paramMap.get("ipmask");
            this.phyBW = paramMap.get("phyw");
            this.direction = paramMap.get("direction");
            this.logicalType = paramMap.get("logicalType");
            this.adminState = paramMap.get("adminState");
            this.operState = paramMap.get("operState");
        }
    }

    public String getDescription() {
        return this.description;
    }

    public String getMeID() {
        return this.meID;
    }

    public String getLogicalType() {
        return this.logicalType;
    }

    public String getLayerRate() {
        return this.layerRate;
    }

    public String getIsEdgePoint() {
        return this.isEdgePoint;
    }

    @Override
    public String toJsonBody() {
        Map<String, Object> moListMap = new HashMap<String, Object>();
        moListMap.put(MOKEY, this);
        return JsonUtil.toJson(moListMap);
    }

    public String getPortIndex() {
        return portIndex;
    }

    public String getSource() {
        return source;
    }

    public String getOwner() {
        return owner;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getDirection() {
        return direction;
    }

    public String getPhyBW() {
        return phyBW;
    }

    public String getIpMask() {
        return ipMask;
    }

    public String getAdminState() {
        return adminState;
    }

    public String getOperState() {
        return operState;
    }

    @Override
    public String toString() {
        return "LTP [id=" + id + ", name=" + name + ", description=" + description + ", meID=" + meID + ", layerRate="
                + layerRate + ", ipAddress=" + ipAddress + ", portIndex=" + portIndex + ", owner=" + owner
                + ", adminState=" + adminState + ", operState=" + operState + ",logicalType=" + logicalType
                + ",isEdgePoint=" + isEdgePoint + ", source=" + source + ",direction=" + direction + ",phyBW=" + phyBW
                + ",ipMask=" + ipMask + "]";
    }
}
