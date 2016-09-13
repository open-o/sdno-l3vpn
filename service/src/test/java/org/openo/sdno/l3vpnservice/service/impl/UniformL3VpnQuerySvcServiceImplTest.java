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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.l3vpnservice.dao.L3VpnDao;
import org.openo.sdno.l3vpnservice.dao.L3VpnTpDao;
import org.openo.sdno.l3vpnservice.service.api.L3VpnSbiApi;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnSbiApiService;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.servicemodel.vpn.VpnBasicInfo;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Acs;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Vpn;
import org.openo.sdno.wanvpn.translator.common.OperStatusUtils;

import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.Tested;

public class UniformL3VpnQuerySvcServiceImplTest {

    @Tested
    private final UniformL3VpnQuerySvcServiceImpl service = new UniformL3VpnQuerySvcServiceImpl();

    @Mocked
    private @Context HttpServletRequest httpServletRequest;

    @Injectable
    private final L3VpnTpDao tpDao = new L3VpnTpDao() {

        @Override
        @Mock
        public boolean updateMos(final List<Tp> mos) throws ServiceException {
            return true;
        }
    };

    @Injectable
    private final L3VpnDao vpnDao = new L3VpnDao() {

        @Override
        @Mock
        public Vpn getMoById(final String uuid) throws ServiceException {
            return new Vpn();
        }

        @Override
        @Mock
        public void updateVpn(final Vpn oldVpn) throws ServiceException {

        }
    };

    @Injectable
    private final L3VpnSbiApiService l3VpnSbiApiService = new L3VpnSbiApi() {

        @Override
        @Mock
        public L3Vpn getL3vpnDetail(final String uuid, final String ctrluuid,
                @Context HttpServletRequest httpServletRequest) throws ServiceException {
            L3Vpn l3vpn = new L3Vpn();
            l3vpn.setUuid("38d081c6-fdf3-4195-a83a-74805ea9b431");
            l3vpn.setName("DefaultL3VPNOverLdp_F");
            return l3vpn;
        }
    };

    @Test
    public void testGetStatus() throws ServiceException {
        Vpn vpn = new Vpn();

        VpnBasicInfo vpnBasicInfo = new VpnBasicInfo();
        vpn.setVpnBasicInfo(vpnBasicInfo);

        new MockUp<OperStatusUtils>() {

            @Mock
            public void setVpnDefaultStatus(Vpn vpn) {

            }

            @Mock
            public void updateTpOperAndName(Vpn vpn, L3Acs l3Acs) {

            }

        };
        service.getStatus(vpn, httpServletRequest);
        service.getL3vpnAdminStatus(vpn, "", httpServletRequest);
        service.getDetail("", httpServletRequest);
        service.getOperStatus("", "", httpServletRequest);
    }

}
