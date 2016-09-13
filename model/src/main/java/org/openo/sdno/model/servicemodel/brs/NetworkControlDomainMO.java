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

import java.util.List;

import org.openo.sdno.model.servicemodel.BaseMO;
import org.openo.sdno.model.servicemodel.mss.annotation.MOResType;

/**
 * The data model class of network control domain.<br>
 * 
 * @author
 * @version SDNO 0.5 Jul 21, 2016
 */
@MOResType(infoModelName = "networkcontroldomain")
public class NetworkControlDomainMO extends BaseMO {

    private String nativeID;

    private String userLabel;

    private String parentNcdID;

    private String location;

    private String manufacturer;

    private String ipAddress;

    private int port;

    private String adminStatus;

    private String operateStatus;

    private List<String> managementElementIDs;

    public String getNativeID() {
        return nativeID;
    }

    public void setNativeID(final String nativeID) {
        this.nativeID = nativeID;
    }

    public String getUserLabel() {
        return userLabel;
    }

    public void setUserLabel(final String userLabel) {
        this.userLabel = userLabel;
    }

    public String getParentNcdID() {
        return parentNcdID;
    }

    public void setParentNcdID(final String parentNcdID) {
        this.parentNcdID = parentNcdID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(final String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(final String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(final int port) {
        this.port = port;
    }

    public String getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(final String adminStatus) {
        this.adminStatus = adminStatus;
    }

    public String getOperateStatus() {
        return operateStatus;
    }

    public void setOperateStatus(final String operateStatus) {
        this.operateStatus = operateStatus;
    }

    public List<String> getManagementElementIDs() {
        return managementElementIDs;
    }

    public void setManagementElementIDs(final List<String> managementElementIDs) {
        this.managementElementIDs = managementElementIDs;
    }

    @Override
    public String toJsonBody() {
        return "";
    }
}
