/*
 * Copyright 2017 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.model.servicemodel.tepath;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class TePathQueryKeyTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() throws IOException {
        TePathQueryKey tePathQueryKey = new TePathQueryKey("vpnId", "srcNeId", "destNeId", "srcAcId", "destAcId");
        tePathQueryKey.setVpnId("newVpnId");
        tePathQueryKey.setSrcNeId("newSrcNeId");
        tePathQueryKey.setDestNeId("newDestNeId");
        tePathQueryKey.setSrcAcId("newSrcAcId");
        tePathQueryKey.setDestAcId("newDestAcId");
        assertEquals(tePathQueryKey.getVpnId(), "newVpnId");
        assertEquals(tePathQueryKey.getSrcNeId(), "newSrcNeId");
        assertEquals(tePathQueryKey.getDestNeId(), "newDestNeId");
        assertEquals(tePathQueryKey.getSrcAcId(), "newSrcAcId");
        assertEquals(tePathQueryKey.getDestAcId(), "newDestAcId");

        Map<String, String> params = tePathQueryKey.getParams();
        assertEquals(params.get("vpnId"), "newVpnId");
        assertEquals(params.get("srcNeId"), "newSrcNeId");
        assertEquals(params.get("destNeId"), "newDestNeId");
        assertEquals(params.get("srcAcId"), "newSrcAcId");
        assertEquals(params.get("destAcId"), "newDestAcId");

        Map<String, Object> params2 = tePathQueryKey.getObjectParams();
        assertEquals(params2.get("vpnId"), "newVpnId");
        assertEquals(params2.get("srcNeId"), "newSrcNeId");
        assertEquals(params2.get("destNeId"), "newDestNeId");
        assertEquals(params2.get("srcAcId"), "newSrcAcId");
        assertEquals(params2.get("destAcId"), "newDestAcId");
    }

}
