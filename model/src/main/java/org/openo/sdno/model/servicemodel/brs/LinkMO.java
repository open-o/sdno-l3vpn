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

package org.openo.sdno.model.servicemodel.brs;

import java.util.HashMap;
import java.util.Map;

import org.openo.sdno.model.servicemodel.BaseMO;
import org.openo.sdno.framework.container.util.JsonUtil;

/**
 * The data model class of link.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public class LinkMO extends BaseMO {

    private static final String MOKEY = "topologicalLink";

    private String description;

    private String logicalType;

    private String layerRate;

    private String aEnd;

    private String zEnd;

    private String aEndME;

    private String zEndME;

    private String source;

    private String owner;

    private String direction;

    private String phyBW;

    private String adminState;

    private String operState;

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     */
    public LinkMO() {
        super();
    }

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     * @param paramMap The map of parameters to set
     */
    public LinkMO(Map<String, String> paramMap) {
        super();
        this.id = paramMap.get("id");
        this.name = paramMap.get("name");
        this.layerRate = paramMap.get("layerrate");
        this.aEnd = paramMap.get("aEnd");
        this.aEndME = paramMap.get("aEndME");
        this.zEnd = paramMap.get("zEnd");
        this.zEndME = paramMap.get("zEndME");
        this.adminState = paramMap.get("adminState");
        this.operState = paramMap.get("operState");
        this.logicalType = paramMap.get("logicalType");
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLogicalType(String logicalType) {
        this.logicalType = logicalType;
    }

    public void setLayerRate(String layerRate) {
        this.layerRate = layerRate;
    }

    public void setAEnd(String aEnd) {
        this.aEnd = aEnd;
    }

    public void setZEnd(String zEnd) {
        this.zEnd = zEnd;
    }

    public void setAEndME(String aEndME) {
        this.aEndME = aEndME;
    }

    public void setZEndME(String zEndME) {
        this.zEndME = zEndME;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setPhyBW(String phyBW) {
        this.phyBW = phyBW;
    }

    public void setAdminState(String adminState) {
        this.adminState = adminState;
    }

    public void setOperState(String operState) {
        this.operState = operState;
    }

    public String getAEndME() {
        return aEndME;
    }

    public String getZEndME() {
        return zEndME;
    }

    @Override
    public String toJsonBody() {
        Map<String, Object> moListMap = new HashMap<String, Object>();
        moListMap.put(MOKEY, this);
        return JsonUtil.toJson(moListMap);
    }

    public String getDescription() {
        return description;
    }

    public String getLogicalType() {
        return logicalType;
    }

    public String getLayerRate() {
        return layerRate;
    }

    public String getAEnd() {
        return aEnd;
    }

    public String getZEnd() {
        return zEnd;
    }

    public String getSource() {
        return source;
    }

    public String getOwner() {
        return owner;
    }

    public String getDirection() {
        return direction;
    }

    public String getPhyBW() {
        return phyBW;
    }

    public String getAdminState() {
        return adminState;
    }

    public String getOperState() {
        return operState;
    }

    @Override
    public String toString() {
        return "Link [id=" + id + ", name=" + name + ", description=" + description + ", logicalType=" + logicalType
                + ", layerRate=" + layerRate + ", source=" + source + ", direction=" + direction + ", owner=" + owner
                + ", adminState=" + adminState + ", operState=" + operState + ",aEnd=" + aEnd + ",zEnd=" + zEnd
                + ",phyBW=" + phyBW + "]";
    }
}
