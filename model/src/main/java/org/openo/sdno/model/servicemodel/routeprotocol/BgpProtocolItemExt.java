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

package org.openo.sdno.model.servicemodel.routeprotocol;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Routing protocol item Ext class.<br>
 * 
 * @author
 * @version SDNO 0.5 August 4, 2016
 */
public class BgpProtocolItemExt extends BgpProtocolItem {

    private String password;

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public void setPassword(String password) {
        this.password = password;
    }

}
