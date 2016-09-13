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

package org.openo.sdno.wanvpn.util.inventory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.openo.sdno.model.servicemodel.brs.LtpMO;

public class TerminationPointUtilTest {

    @Test
    public void testIsSubEthInterfaceNull() {
        assertFalse(TerminationPointUtil.isSubEthInterface(null));
    }

    @Test
    public void testIsSubEthInterfaceNotETH() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("logicalType", "NOTETH");
        LtpMO ltpMo = new LtpMO(paramMap);
        assertFalse(TerminationPointUtil.isSubEthInterface(ltpMo));
    }

    @Test
    public void testIsSubEthInterfaceNameNotMatch() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("logicalType", "ETH");
        paramMap.put("name", "a.");
        LtpMO ltpMo = new LtpMO(paramMap);
        assertFalse(TerminationPointUtil.isSubEthInterface(ltpMo));
    }

    @Test
    public void testIsSubEthInterfaceTrue() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("logicalType", "ETH");
        paramMap.put("name", "a.1");
        LtpMO ltpMo = new LtpMO(paramMap);
        assertTrue(TerminationPointUtil.isSubEthInterface(ltpMo));
    }

    @Test
    public void testIsLoopbackInterfaceTrue() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("logicalType", "Loopback");
        LtpMO ltpMo = new LtpMO(paramMap);
        assertTrue(TerminationPointUtil.isLoopbackInterface(ltpMo));
    }

    @Test
    public void testIsLoopbackInterfaceTypeNotMatch() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("logicalType", "NotLoopback");
        LtpMO ltpMo = new LtpMO(paramMap);
        assertFalse(TerminationPointUtil.isLoopbackInterface(ltpMo));
    }

    @Test
    public void testIsLoopbackInterfaceNull() {
        assertFalse(TerminationPointUtil.isLoopbackInterface(null));
    }

}
