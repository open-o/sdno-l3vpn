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

package org.openo.sdno.wanvpn.util.checker;

import java.util.List;

import org.openo.sdno.model.paradesc.IntegerDesc;
import org.openo.sdno.model.paradesc.NotNullDesc;
import org.openo.sdno.model.paradesc.StringDesc;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;

public class MockSvcModel extends AbstractSvcModel {

    @IntegerDesc(defaultVal = 0, hasDefault = true, minVal = 1, maxVal = 10)
    private long number;

    @StringDesc(maxLen = 36)
    private long strNumber;

    @NotNullDesc
    @IntegerDesc(minVal = 1, maxVal = 10)
    private String numberStr;

    @IntegerDesc(defaultVal = 0, hasDefault = true, minVal = 1, maxVal = 10)
    private Integer numberInteger;

    @IntegerDesc(defaultVal = 0, hasDefault = true, minVal = 1, maxVal = 10)
    private List<Integer> numList;

    @IntegerDesc(minVal = 1, maxVal = 10)
    private List<MockSubSvcModel> numberList;

    @StringDesc(maxLen = 36)
    private String str;

    @StringDesc(maxLen = 36)
    private List<MockSubSvcModel> strList;

    @StringDesc(maxLen = 36)
    private List<String> strListStr;

    private int intNumber;

    public int getIntNumber() {
        return intNumber;
    }

    public void setIntNumber(int intNumber) {
        this.intNumber = intNumber;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public long getStrNumber() {
        return strNumber;
    }

    public void setStrNumber(long strNumber) {
        this.strNumber = strNumber;
    }

    public String getNumberStr() {
        return numberStr;
    }

    public void setNumberStr(String numberStr) {
        this.numberStr = numberStr;
    }

    public Integer getNumberInteger() {
        return numberInteger;
    }

    public void setNumberInteger(Integer numberInteger) {
        this.numberInteger = numberInteger;
    }

    public List<Integer> getNumList() {
        return numList;
    }

    public void setNumList(List<Integer> numList) {
        this.numList = numList;
    }

    public List<MockSubSvcModel> getNumberList() {
        return numberList;
    }

    public void setNumberList(List<MockSubSvcModel> numberList) {
        this.numberList = numberList;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public List<MockSubSvcModel> getStrList() {
        return strList;
    }

    public void setStrList(List<MockSubSvcModel> strList) {
        this.strList = strList;
    }

    public List<String> getStrListStr() {
        return strListStr;
    }

    public void setStrListStr(List<String> strListStr) {
        this.strListStr = strListStr;
    }

    @Override
    public String getUuid() {
        return null;
    }

    @Override
    public void setUuid(String uuid) {
        // nothing to do
    }
}
