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

package org.openo.sdno.model.uniformsbi.l2vpn;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.openo.sdno.model.uniformsbi.base.Ac;

/**
 * L2ac external qos policy class.<br>
 * 
 * @author
 * @version SDNO 0.5 Aug 8, 2016
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class L2Ac extends Ac {

    private String externalQosPolicyId;

    public String getExternalQosPolicyId() {
        return externalQosPolicyId;
    }

    public void setExternalQosPolicyId(String externalQosPolicyId) {
        this.externalQosPolicyId = externalQosPolicyId;
    }

}
