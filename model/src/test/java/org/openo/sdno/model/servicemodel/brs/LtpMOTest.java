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


package org.openo.sdno.model.servicemodel.brs;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class LtpMOTest {

    @Test
    public void testLtpMONull() {
        LtpMO demo = new LtpMO(null);
        assertNull(demo.getId());
    }

    @Test
    public void testLtpMONormal() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "value");
        map.put("meid", "value");
        map.put("name", "value");
        map.put("description", "value");
        map.put("ipaddress", "value");
        map.put("ipmask", "value");
        map.put("phyw", "value");
        map.put("direction", "value");
        map.put("logicalType", "value");
        map.put("adminState", "value");
        map.put("operState", "value");
        LtpMO demo = new LtpMO(map);
        assertTrue(demo.getId().equals("value"));
    }

}
