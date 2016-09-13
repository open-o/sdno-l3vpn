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


package org.openo.sdno.model.db;

import org.openo.sdno.model.paradesc.EnumDesc;
import org.openo.sdno.model.paradesc.StringDesc;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;
import org.openo.sdno.model.servicemodel.common.enumeration.LabelModeType;

public class ObjectB extends AbstractSvcModel {

    @StringDesc(pattern = "[a-zA-Z0-9\\-\\_]{1,36}")
    private String id;

    @EnumDesc(LabelModeType.class)
    private String labelMode;

    private String frrEnable;

    private String aNotHave;

    private int ahave;

    private double onlyB;

    private double onlyB2;

    private String onlyA;

    public void setOnlyB(double onlyB) {
        this.onlyB = onlyB;
    }

    public void setAhave(int ahave) {
        this.ahave = ahave;
    }

    public void setOnlyA(String onlyA) {
        this.onlyA = onlyA;
    }

    public String getaNotHave() {
        return aNotHave;
    }

    public int setOnlyB2() {
        return 1;
    }

    public void setOnlyB2(String s) {
        return;
    }

    public void setOnlyB2(int i, int j) {
        return;
    }

    public void setaNotHave(String aNotHave) {
        this.aNotHave = aNotHave;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabelMode() {
        return labelMode;
    }

    public void setLabelMode(String labelMode) {
        this.labelMode = labelMode;
    }

    public String getFrrEnable() {
        return frrEnable;
    }

    public void setFrrEnable(String frrEnable) {
        this.frrEnable = frrEnable;
    }

    @Override
    public String getUuid() {
        return id;
    }

    @Override
    public void setUuid(String uuid) {
        id = uuid;
    }

}
