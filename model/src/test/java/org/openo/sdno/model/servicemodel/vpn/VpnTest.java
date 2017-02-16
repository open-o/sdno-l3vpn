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

package org.openo.sdno.model.servicemodel.vpn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.model.JsonFileUtil;

public class VpnTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() throws IOException {
        final String filePath = new File("src/test/resources/composedVpn.json").getCanonicalPath();
        ComposedVpn composedVpn = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), ComposedVpn.class);
        Vpn vpn = new Vpn(composedVpn);

        assertNotNull(composedVpn);
        assertNotNull(JsonUtil.toJson(composedVpn));
        assertNotNull(vpn.getUuid());
        assertEquals(vpn.getName(), composedVpn.getName());
        assertEquals(vpn.getDescription(), composedVpn.getDescription());
        assertEquals(vpn.getAccessPointList(), composedVpn.getAccessPointList());

        ComposedVpn composedVpn2 = new ComposedVpn("name", "tenantId", "businessTypeId", new VpnBasicInfo());
        assertNull(composedVpn2.getId());
        assertEquals(composedVpn2.getName(), "name");
        assertEquals(composedVpn2.getTenantId(), "tenantId");
        assertEquals(composedVpn2.getBusinessTypeId(), "businessTypeId");
    }

}
