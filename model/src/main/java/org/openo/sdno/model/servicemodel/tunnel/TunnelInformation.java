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

package org.openo.sdno.model.servicemodel.tunnel;

import org.openo.sdno.model.paradesc.EnumDesc;
import org.openo.sdno.model.paradesc.StringDesc;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;
import org.openo.sdno.model.servicemodel.businesstype.PathConnection;
import org.openo.sdno.model.servicemodel.common.enumeration.TunnelTechType;

/**
 * tunnel information class.<br>
 * 
 * @author
 * @version SDNO 0.5 August 9, 2016
 */
public class TunnelInformation extends AbstractSvcModel {

    private String id;

    @StringDesc(maxLen = 200)
    private String name;

    @StringDesc(maxLen = 200)
    private String description;

    private String direction;

    @EnumDesc(TunnelTechType.class)
    private String tunnelType;

    private PathConnection pathinfo;

    private Integer bandwidth;

    private Integer remainedWidth;

    private Long tunnelLatency;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getTunnelType() {
        return tunnelType;
    }

    public void setTunnelType(String tunnelType) {
        this.tunnelType = tunnelType;
    }

    public PathConnection getPathinfo() {
        return pathinfo;
    }

    public void setPathinfo(PathConnection pathinfo) {
        this.pathinfo = pathinfo;
    }

    public Integer getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(Integer bandwidth) {
        this.bandwidth = bandwidth;
    }

    public Integer getRemainedWidth() {
        return remainedWidth;
    }

    public void setRemainedWidth(Integer remainedWidth) {
        this.remainedWidth = remainedWidth;
    }

    public Long getTunnelLatency() {
        return tunnelLatency;
    }

    public void setTunnelLatency(Long tunnelLatency) {
        this.tunnelLatency = tunnelLatency;
    }

    @Override
    public String getUuid() {
        return id;
    }

    @Override
    public void setUuid(final String id) {
        this.id = id;
    }

}
