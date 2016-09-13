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
import java.util.List;
import java.util.Map;

import org.openo.sdno.model.servicemodel.BaseMO;
import org.openo.sdno.model.servicemodel.mss.annotation.MOResType;
import org.openo.sdno.framework.container.util.JsonUtil;

/**
 * The data model class of site.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
@MOResType(infoModelName = "site")
public class SiteMO extends BaseMO {

    private static final String MOKEY = "rest/brs/v1/sites";

    private String type;

    private String location;

    private String description;

    private String tenantID;

    public static final String TENANT_ID = "tenantID";

    public static final String TENANT_NAME = "name";

    public static final String TENANT_SITE_ID = "tenantSiteIDs";

    public static final String TYPE = "type";

    public static final String TYPE_TENANT = "tenant_site";

    private List<String> tenantSiteIDs;

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     */
    public SiteMO() {
        super();
    }

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     * @param paramMap The map of parameters to set
     */
    public SiteMO(final Map<String, Object> paramMap) {
        super();
        if(null != paramMap) {
            this.type = (String)paramMap.get("type");
            this.location = (String)paramMap.get("location");
            this.description = (String)paramMap.get("description");
            this.tenantID = (String)paramMap.get("tenantID");
        }
    }

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     * @param id Site id to set
     */
    public SiteMO(final String id) {
        super();
        super.setId(id);
    }

    public final String getType() {
        return type;
    }

    public final void setType(final String type) {
        this.type = type;
    }

    public final String getLocation() {
        return location;
    }

    public final void setLocation(final String location) {
        this.location = location;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(final String description) {
        this.description = description;
    }

    public final String getTenantID() {
        return tenantID;
    }

    public final void setTenantID(final String tenantID) {
        this.tenantID = tenantID;
    }

    @Override
    public String toString() {
        return "Site [uuid=" + id + ", name=" + name + ", type=" + type + ", location=" + location + ", description="
                + description + ", tenantID=" + tenantID + "]";
    }

    @Override
    public String toJsonBody() {
        Map<String, Object> moListMap = new HashMap<String, Object>();
        moListMap.put(MOKEY, this);
        return JsonUtil.toJson(moListMap);
    }

    public List<String> getTenantSiteIDs() {
        return tenantSiteIDs;
    }

    public void setTenantSiteIDs(List<String> tenantSiteIDs) {
        this.tenantSiteIDs = tenantSiteIDs;
    }
}
