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

package org.openo.sdno.l3vpnservice.service.impl;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.l3vpnservice.JsonFileUtil;
import org.openo.sdno.model.servicemodel.tepath.TePathQueryKey;
import org.openo.sdno.model.servicemodel.vpn.Vpn;

import mockit.Mocked;

public class UniformL3VpnQueryTePathServiceImplTest {

    @Mocked
    private @Context HttpServletRequest httpServletRequest;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetTePath() throws ServiceException, IOException {
        UniformL3VpnQueryTePathServiceImpl impl = new UniformL3VpnQueryTePathServiceImpl();
        String filePath = new File("src/test/resources/vpn.json").getCanonicalPath();
        final Vpn vpn = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Vpn.class);
        TePathQueryKey queryKey = new TePathQueryKey("", "", "", "", "");
        impl.getTePath(vpn, queryKey, httpServletRequest);
    }

}
