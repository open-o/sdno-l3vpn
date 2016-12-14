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

import org.openo.sdno.model.uniformsbi.base.QosIfCar;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.QosFlowDirectionType;

/**
 * <br>
 *
 * @author zhaozhongchao@huawei.com
 * @version SDNO 0.5 Dec 6, 2016
 */
public class OverrideFlow {

    private String id;

    private String externalFlowId;

    private String name;

    private QosFlowDirectionType direction;

    private QosIfCar car;

    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Returns the externalFlowId.
     */
    public String getExternalFlowId() {
        return externalFlowId;
    }

    /**
     * @param externalFlowId The externalFlowId to set.
     */
    public void setExternalFlowId(String externalFlowId) {
        this.externalFlowId = externalFlowId;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Returns the direction.
     */
    public QosFlowDirectionType getDirection() {
        return direction;
    }

    /**
     * @param direction The direction to set.
     */
    public void setDirection(QosFlowDirectionType direction) {
        this.direction = direction;
    }

    /**
     * @return Returns the car.
     */
    public QosIfCar getCar() {
        return car;
    }

    /**
     * @param car The car to set.
     */
    public void setCar(QosIfCar car) {
        this.car = car;
    }

}
