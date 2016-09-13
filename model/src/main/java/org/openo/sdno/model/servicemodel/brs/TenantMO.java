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

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.openo.sdno.model.servicemodel.mss.annotation.MOResType;

/**
 * The data model class of tenant.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
@MOResType(infoModelName = "tenant")
public class TenantMO {

    private String uuid;

    @NotNull
    @Max(255)
    private String name;

    @NotNull
    @Max(255)
    private String id;

    /**
     * Constructor.<br/>
     * 
     * @param uuid UUID
     * @since SDNO 0.5
     */
    public TenantMO(final String uuid) {
        this.uuid = uuid;
    }

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     */
    public TenantMO() {
        // a empty constructor that construct a object without set any thing
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        final TenantMO other = (TenantMO)obj;
        if(id == null) {
            if(other.id != null) {
                return false;
            }
        } else if(!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TenantMO [uuid=" + uuid + ", name=" + name + ", id=" + id + "]";
    }

}
