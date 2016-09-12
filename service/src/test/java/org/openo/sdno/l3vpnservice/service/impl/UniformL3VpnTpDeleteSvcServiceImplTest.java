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

package org.openo.sdno.l3vpnservice.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.l3vpnservice.dao.L3VpnTpDao;
import org.openo.sdno.l3vpnservice.service.api.L3VpnSbiApi;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnQuerySvcService;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnSbiApiService;
import org.openo.sdno.l3vpnservice.service.util.ControllerUtils;
import org.openo.sdno.l3vpnservice.service.util.L3VpnServiceUtils;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;

import org.openo.sdno.l3vpnservice.JsonFileUtil;

import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.Tested;

public class UniformL3VpnTpDeleteSvcServiceImplTest {

    @Tested
    final UniformL3VpnTpDeleteSvcServiceImpl tpDeleteSvcService = new UniformL3VpnTpDeleteSvcServiceImpl();

    @Injectable
    private final L3VpnTpDao tpDao = new L3VpnTpDao() {

        @Override
        @Mock
        public boolean delMos(final List<Tp> mos) throws ServiceException {
            return true;
        }
    };

    @Injectable
    private final L3VpnQuerySvcService l3VpnQuerySvcService = new UniformL3VpnQuerySvcServiceImpl() {

        @Override
        @Mock
        public Vpn getStatus(Vpn vpn, @Context HttpServletRequest httpServletRequest) throws ServiceException {
            return new Vpn();
        }
    };

    @Injectable
    private final L3VpnSbiApiService l3VpnSbiApiService = new L3VpnSbiApi() {

        @Override
        @Mock
        public void deleteTp(String l3vpnUuid, String tpUuid, String ctrlUuid,
                @Context HttpServletRequest httpServletRequest) throws ServiceException {
        }
    };

    @Mocked
    private @Context HttpServletRequest httpServletRequest;

    @Mocked
    private HttpServletRequest request;

    @Before
    public void setUp() throws Exception {
        new MockUp<ControllerUtils>() {

            @Mock
            public String getControllerUUID(Vpn vpn) throws ServiceException {
                return UuidUtils.createUuid();
            }

            @Mock
            public String getControllerType(Vpn vpn) throws ServiceException {
                return "NSP";
            }
        };

        new MockUp<ControllerUtils>() {

            @Mock
            public String getControllerUUID(Vpn vpn) throws ServiceException {
                return UuidUtils.createUuid();
            }
        };

    }

    @Test
    public void testDeleteTp() throws ServiceException, IOException {
        String filePath = new File("src/test/resources/vpn.json").getCanonicalPath();
        final Vpn vpn = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Vpn.class);
        final Tp tp = vpn.getAccessPointList().get(0);
        String tpUuid = tp.getId();

        new MockUp<L3VpnServiceUtils>() {

            @Mock
            public Tp getTpFromVpn(final Vpn vpn, final String tpId) {
                return tp;
            }
        };

        tpDeleteSvcService.deleteTp(vpn, tpUuid, httpServletRequest);

        tpDeleteSvcService.setTpDao(new L3VpnTpDao());

    }
}
