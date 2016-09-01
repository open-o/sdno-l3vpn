/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.openo.sdno.model.common.query;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class BatchQueryParamsTest {

    @Test
    public void testGetIntListBusinessParam() {
        BatchQueryParams batchQueryParams = new BatchQueryParams();
        List<Integer> intList = new ArrayList<Integer>();
        intList.add(1);
        batchQueryParams.addBusinessParam("intNumber", intList);
        List<Integer> integerList = batchQueryParams.getIntListBusinessParam("intNumber");
        assertEquals(Integer.valueOf(1), integerList.get(0));
    }

    @Test
    public void testGetStrListBusinessParamNull() {
        BatchQueryParams batchQueryParams = new BatchQueryParams();
        List<String> strList = batchQueryParams.getStrListBusinessParam("strNumber");
        assertEquals(null, strList);
    }

    @Test
    public void testGetIntBusinessParam() {
        BatchQueryParams batchQueryParams = new BatchQueryParams();
        batchQueryParams.addBusinessParam("intNumber", 1);
        Integer integerNumber = batchQueryParams.getIntBusinessParam("intNumber");
        assertEquals(Integer.valueOf(1), integerNumber);
    }

    @Test
    public void testGetIntBusinessParamNull() {
        BatchQueryParams batchQueryParams = new BatchQueryParams();
        batchQueryParams.addBusinessParam("intNumber", 1);
        Integer integerNumber = batchQueryParams.getIntBusinessParam("number");
        assertEquals(null, integerNumber);
    }

    @Test
    public void testGetIntBusinessParamNotAssignable() {
        BatchQueryParams batchQueryParams = new BatchQueryParams();
        batchQueryParams.addBusinessParam("strNumber", "test");
        Integer integerNumber = batchQueryParams.getIntBusinessParam("strNumber");
        assertEquals(null, integerNumber);
    }

}
