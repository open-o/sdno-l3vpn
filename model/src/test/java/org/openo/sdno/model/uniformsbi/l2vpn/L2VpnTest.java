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

package org.openo.sdno.model.uniformsbi.l2vpn;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.model.JsonFileUtil;

public class L2VpnTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() throws IOException {
        String filePath = new File("src/test/resources/l2Vpn.json").getCanonicalPath();
        L2Vpn l2Vpn = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), L2Vpn.class);
        assertNotNull(l2Vpn);
        assertNotNull(JsonUtil.toJson(l2Vpn));
    }

}