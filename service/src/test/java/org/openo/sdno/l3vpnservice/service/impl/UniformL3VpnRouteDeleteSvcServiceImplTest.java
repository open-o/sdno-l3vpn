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
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.l3vpnservice.JsonFileUtil;
import org.openo.sdno.l3vpnservice.constant.L3VpnSvcErrorCode;
import org.openo.sdno.l3vpnservice.dao.L3VpnRouteProtocolSpecDao;
import org.openo.sdno.l3vpnservice.service.api.L3VpnSbiApi;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnSbiApiService;
import org.openo.sdno.l3vpnservice.service.util.ControllerUtils;
import org.openo.sdno.l3vpnservice.service.util.L3VpnServiceUtils;
import org.openo.sdno.model.servicemodel.routeprotocol.RouteProtocolSpec;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Vpn;
import org.openo.sdno.wanvpn.util.rest.RestUtil;

import junit.framework.Assert;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.Tested;

public class UniformL3VpnRouteDeleteSvcServiceImplTest {

    @Tested
    final UniformL3VpnRouteDeleteSvcServiceImpl routeDeleteSvcService = new UniformL3VpnRouteDeleteSvcServiceImpl();

    @Injectable
    private final L3VpnRouteProtocolSpecDao routeDao = new L3VpnRouteProtocolSpecDao() {

        @Override
        @Mock
        public boolean delMos(final List<RouteProtocolSpec> mos) throws ServiceException {
            return true;
        }
    };

    @Injectable
    private final L3VpnSbiApiService l3VpnSbiApiService = new L3VpnSbiApi() {

        @Override
        @Mock
        public void deleteStaticRoute(Vpn tempVpn, String tpUuid, String urlBody,
                @Context HttpServletRequest httpServletRequest) throws ServiceException {

        }

        @Override
        @Mock
        public void deleteBgpRoute(Vpn tempVpn, String tpUuid, String urlBody,
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
        new MockUp<RestUtil>() {

            @Mock
            public RestfulResponse sendDeleteRequest(String url, RestfulParametes restfulParametes,
                    @Context HttpServletRequest httpServletRequest) throws ServiceException {
                RestfulResponse restfulResponse = new RestfulResponse();
                L3Vpn vpn = new L3Vpn();
                restfulResponse.setResponseJson(JsonUtil.toJson(vpn));
                return restfulResponse;
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
    public void testDeleteRoute() throws ServiceException, IOException {
        String filePath = new File("src/test/resources/vpn.json").getCanonicalPath();
        final Vpn vpn = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Vpn.class);
        String l3vpnUuid = vpn.getId();
        Tp tp = vpn.getAccessPointList().get(0);
        String tpUuid = tp.getId();
        String routeId = tp.getRouteProtocolSpecs().get(0).getUuid();
        routeDeleteSvcService.deleteRoute(vpn, tpUuid, routeId, httpServletRequest);

        new MockUp<L3VpnServiceUtils>() {

            @Mock
            public RouteProtocolSpec getRouteFromTp(Tp tempTp, String routeId) {
                return null;
            }
        };

        routeDeleteSvcService.deleteRoute(vpn, tpUuid, routeId, httpServletRequest);

    }

    @Test
    public void testException() throws ServiceException {
        try {
            routeDeleteSvcService.deleteRoute(null, "", "", httpServletRequest);
        } catch(ServiceException e) {
            Assert.assertEquals(e.getId(), L3VpnSvcErrorCode.L3VPN_DELETE_TPNOTEXIST);
        }

        new MockUp<L3VpnServiceUtils>() {

            @Mock
            public Tp getTpFromVpn(final Vpn vpn, final String tpId) {
                return null;
            }
        };

        try {
            routeDeleteSvcService.deleteRoute(new Vpn(), "", "", httpServletRequest);
        } catch(ServiceException e) {
            Assert.assertEquals(e.getId(), L3VpnSvcErrorCode.L3VPN_DELETE_TPNOTEXIST);
        }
    }
}
