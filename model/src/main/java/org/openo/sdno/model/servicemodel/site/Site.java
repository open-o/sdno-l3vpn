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

package org.openo.sdno.model.servicemodel.site;

import java.util.List;

import org.openo.sdno.model.paradesc.StringDesc;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;
import org.openo.sdno.model.servicemodel.brs.NetworkElementMO;
import org.openo.sdno.model.servicemodel.brs.SiteMO;

/**
 * Class for VPN site details.
 * <br/>
 * 
 * @author
 * @version SDNO 0.5 Aug 4, 2016
 */
public class Site extends AbstractSvcModel {

    @StringDesc(maxLen = 36)
    private String uuid;

    private String name;

    @StringDesc(maxLen = 36)
    private String tenantUUID;

    private String description;

    private String location;

    private List<NetworkElementMO> cpes;

    private String type;

    private String asName;

    private String asUuid;

    /**
     * @return the uuid
     */
    @Override
    public final String getUuid() {
        return uuid;
    }

    /**
     * @param uuid the uuid to set
     */
    @Override
    public final void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    /**
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public final void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the tenantUUID
     */
    public final String getTenantUUID() {
        return tenantUUID;
    }

    /**
     * @param tenantUUID the tenantUUID to set
     */
    public final void setTenantUUID(final String tenantUUID) {
        this.tenantUUID = tenantUUID;
    }

    /**
     * @return the description
     */
    public final String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public final void setDescription(final String description) {
        this.description = description;
    }

    /**
     * @return the location
     */
    public final String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public final void setLocation(final String location) {
        this.location = location;
    }

    public List<NetworkElementMO> getCpes() {
        return cpes;
    }

    public void setCpes(List<NetworkElementMO> cpes) {
        this.cpes = cpes;
    }

    /**
     * @return the asName
     */
    public final String getAsName() {
        return asName;
    }

    /**
     * @param asName the asName to set
     */
    public final void setAsName(final String asName) {
        this.asName = asName;
    }

    /**
     * @return the asUuid
     */
    public final String getAsUuid() {
        return asUuid;
    }

    /**
     * @param asUuid the asUuid to set
     */
    public final void setAsUuid(final String asUuid) {
        this.asUuid = asUuid;
    }

    /**
     * @return the type
     */
    public final String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public final void setType(final String type) {
        this.type = type;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Site [uuid=" + uuid + ", name=" + name + ", tenantUUID=" + tenantUUID + ", description=" + description
                + ", location=" + location + ", type=" + type + ", asName=" + asName + ", asUuid=" + asUuid + "]";
    }

    /**
     * convert from siteMo to site<br/>
     * 
     * @param siteMo sitemo object to initizlize the site
     * @return site object to return
     * @since SDNO 0.5
     */
    public static Site moToVo(final SiteMO siteMo) {
        final Site site = new Site();
        site.setUuid(siteMo.getId());
        site.setLocation(siteMo.getLocation());
        site.setDescription(siteMo.getDescription());
        site.setName(siteMo.getName());
        site.setTenantUUID(siteMo.getTenantID());
        site.setType(siteMo.getType());
        return site;
    }

}
