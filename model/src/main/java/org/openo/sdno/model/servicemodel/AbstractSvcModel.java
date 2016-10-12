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

package org.openo.sdno.model.servicemodel;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Abstract Service Model Class.<br>
 * 
 * @author
 * @version SDNO 0.5 August 4, 2016
 */

public abstract class AbstractSvcModel implements SvcModel {

    @JsonIgnore
    private final Map<String, Object> valuel4PoMap = new HashMap<String, Object>();

    /**
     * When PO model object field is not defined then SVC model can be set by this method, -
     * the value set in the object when fromSvcModel PO calls will be set to the PO object
     * For publishers who want to call before fromSvcModel method to determine the value of PO
     * If there SVC model and PO model same field, use this method to set the value of the object -
     * is not assigned to PO model recommended SVC model original setting method<br>
     * 
     * @param poFieldName PO object field name
     * @param val set the desired PO object field values
     * @since SDNO 0.5
     */
    @Override
    public void setValue4Po(String poFieldName, Object val) {
        valuel4PoMap.put(poFieldName, val);
    }

    public Map<String, Object> getValuel4PoMap() {
        return valuel4PoMap;
    }
}
