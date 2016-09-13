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

/**
 * Bi-directional Forwarding Detection parameter class.<br>
 * 
 * @author
 * @version SDNO 0.5 Jul 22, 2016
 */
public class BfdParamter {

    private Integer minTxInterval;

    private Integer minRxInterval;

    public Integer getMinTxInterval() {
        return minTxInterval;
    }

    public void setMinTxInterval(Integer minTxInterval) {
        this.minTxInterval = minTxInterval;
    }

    public Integer getMinRxInterval() {
        return minRxInterval;
    }

    public void setMinRxInterval(Integer minRxInterval) {
        this.minRxInterval = minRxInterval;
    }

}
