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


package org.openo.sdno.model.servicemodel.brs;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class NetworkElementMOTest {

    @Test
    public void testNetworkElementMONull() {
        NetworkElementMO demo = new NetworkElementMO(null);
        assertNull(demo.getId());
    }

    @Test
    public void testNetworkElementMONormal() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> ctrlid = new ArrayList<String>();
        List<String> nedid = new ArrayList<String>();
        ctrlid.add("value");
        nedid.add("value");
        map.put("id", "value");
        map.put("name", "value");
        map.put("description", "value");
        map.put("ipaddress", "value");
        map.put("manufacturer", "value");
        map.put("controllerID", ctrlid);
        map.put("networkControlDomainID", nedid);
        map.put("adminState", "value");
        map.put("operState", "value");
        NetworkElementMO demo = new NetworkElementMO(map);
        assertTrue(demo.getId().equals("value"));
    }

}
