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

package org.openo.sdno.model.servicemodel.site;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.model.JsonFileUtil;
import org.openo.sdno.model.servicemodel.brs.NetworkElementMO;
import org.openo.sdno.model.servicemodel.brs.SiteMO;

public class SiteTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() throws IOException {
        String filePath = new File("src/test/resources/site.json").getCanonicalPath();
        Site site = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Site.class);
        assertNotNull(site.toString());
        assertNotNull(site);
        assertNotNull(JsonUtil.toJson(site));

        filePath = new File("src/test/resources/siteMO.json").getCanonicalPath();
        SiteMO siteMO = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), SiteMO.class);
        site = Site.moToVo(siteMO);
        assertEquals(site.getUuid(), siteMO.getId());
        assertEquals(site.getType(), siteMO.getType());

        Map<String, Object> paramMap2 = new HashMap<>();
        paramMap2.put("type", "test");
        SiteMO siteMO2 = new SiteMO(paramMap2);
        assertTrue(siteMO2.toString().contains("test"));
        assertTrue(siteMO2.toJsonBody().contains("rest/brs/v1/sites"));

        Map<String, Object> paramMap = new HashMap<>();
        NetworkElementMO networkElementMO = new NetworkElementMO();
        assertNotNull(networkElementMO);

        paramMap.put("id", "111");
        NetworkElementMO networkElementMO2 = new NetworkElementMO(paramMap);
        assertTrue(networkElementMO2.toJsonBody().contains("managedElement"));
        assertTrue(networkElementMO2.toString().contains("111"));
    }

}
