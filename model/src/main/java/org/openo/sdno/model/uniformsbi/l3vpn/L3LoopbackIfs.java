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

package org.openo.sdno.model.uniformsbi.l3vpn;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * L3LoopbackIfs class.<br>
 * 
 * @author
 * @version SDNO 0.5 Jul 22, 2016
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class L3LoopbackIfs {

    @JsonProperty("loopback")
    private List<L3LoopbackIf> l3LoopbackIf = null;

    public List<L3LoopbackIf> getL3LoopbackIf() {
        return l3LoopbackIf;
    }

    public void setL3LoopbackIf(List<L3LoopbackIf> l3LoopbackIf) {
        this.l3LoopbackIf = l3LoopbackIf;
    }

}
