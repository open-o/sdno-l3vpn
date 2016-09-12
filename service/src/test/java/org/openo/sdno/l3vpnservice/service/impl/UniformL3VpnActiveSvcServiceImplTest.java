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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.l3vpnservice.JsonFileUtil;
import org.openo.sdno.l3vpnservice.dao.L3VpnBasicInfoDao;
import org.openo.sdno.l3vpnservice.dao.L3VpnTpDao;
import org.openo.sdno.l3vpnservice.service.api.L3VpnSbiApi;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnSbiApiService;
import org.openo.sdno.l3vpnservice.service.util.ControllerUtils;
import org.openo.sdno.model.servicemodel.common.enumeration.ServiceType;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.servicemodel.vpn.VpnBasicInfo;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Ac;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Vpn;

import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.Tested;

public class UniformL3VpnActiveSvcServiceImplTest {

    @Tested
    private final UniformL3VpnActiveSvcServiceImpl l3VpnActiveSvcService = new UniformL3VpnActiveSvcServiceImpl();

    @Injectable
    private final L3VpnBasicInfoDao vpnBasicInfoDao = new L3VpnBasicInfoDao() {

        @Override
        @Mock
        public void updateStatus(final List<VpnBasicInfo> vpnBasicInfos) throws ServiceException {
        }
    };

    @Injectable
    private final L3VpnTpDao tpDao = new L3VpnTpDao() {

        @Override
        @Mock
        public void updateStatus(final List<Tp> tps) throws ServiceException {
        }

        @Override
        @Mock
        public Tp getMoById(final String uuid) throws ServiceException {
            return new Tp();
        }
    };

    @Injectable
    private final L3VpnSbiApiService l3VpnSbiApiService = new L3VpnSbiApi() {

        @Override
        @Mock
        public void deployTpStatus(final String vpnId, final L3Ac l3ac, String ctrlUuid,
                final @Context HttpServletRequest httpServletRequest) throws ServiceException {
        }

        @Override
        @Mock
        public void deployVpnStatus(final L3Vpn l3Vpn, final String controllerUuid,
                final @Context HttpServletRequest httpServletRequest) throws ServiceException {

        }

    };

    @Mocked
    private @Context HttpServletRequest httpServletRequest;

    @Before
    public void setUp() throws Exception {

        new MockUp<ControllerUtils>() {

            @Mock
            public String getControllerUUID(Vpn vpn) throws ServiceException {
                return UuidUtils.createUuid();
            }
        };
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDeactive() throws ServiceException, IOException {
        String filePath = new File("src/test/resources/vpn.json").getCanonicalPath();
        final Vpn vpn = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Vpn.class);
        vpn.getVpnBasicInfo().setServiceType(ServiceType.L3VPN.getCommonName());

        l3VpnActiveSvcService.active(vpn, httpServletRequest);
        Tp tp = vpn.getAccessPointList().get(0);
        l3VpnActiveSvcService.activeSite(vpn, tp.getUuid(), httpServletRequest);
        l3VpnActiveSvcService.deactiveSite(vpn, tp.getUuid(), httpServletRequest);
        l3VpnActiveSvcService.setL3VpnSbiApiService(new L3VpnSbiApi());
        l3VpnActiveSvcService.setTpDao(new L3VpnTpDao());
        l3VpnActiveSvcService.setVpnBasicInfoDao(new L3VpnBasicInfoDao());
    }

}
