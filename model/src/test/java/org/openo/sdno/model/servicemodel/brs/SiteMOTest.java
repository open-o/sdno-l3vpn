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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class SiteMOTest {

    @Test
    public void testSiteMONull() {
        Map<String, Object> map = new HashMap<String, Object>();
        map = null;
        SiteMO demo = new SiteMO(map);
        assertNull(demo.getId());
    }

    @Test
    public void testSiteMONormal() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> ctrlid = new ArrayList<String>();
        List<String> nedid = new ArrayList<String>();
        map.put("type", "value");
        map.put("location", "value");
        map.put("description", "value");
        map.put("tenantID", "value");
        SiteMO demo = new SiteMO(map);
        assertTrue(demo.getDescription().equals("value"));
    }

}
