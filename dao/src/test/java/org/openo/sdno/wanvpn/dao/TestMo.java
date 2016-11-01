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

package org.openo.sdno.wanvpn.dao;

import org.junit.Test;

public class TestMo {

    private String uuid;

    private String name;

    private int intVal;

    private boolean bool;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = String.valueOf(uuid);
    }

    public int getName() {
        return intVal;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIntVal() {
        return intVal;
    }

    public void setIntVal(int intVal) {
        this.intVal = intVal;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public void setBool2() {
        bool = true;
    }

    public int getInt2(int val) {
        name = "name";
        return intVal + val;
    }

    public String setInt2(int val) {
        return name;
    }

    @Test
    public void test() {

    }
}
