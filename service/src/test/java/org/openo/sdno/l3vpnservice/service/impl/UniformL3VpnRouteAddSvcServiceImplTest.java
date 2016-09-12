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
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.l3vpnservice.JsonFileUtil;
import org.openo.sdno.l3vpnservice.dao.L3VpnDao;
import org.openo.sdno.l3vpnservice.dao.L3VpnRouteProtocolSpecDao;
import org.openo.sdno.l3vpnservice.service.api.L3VpnSbiApi;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnSbiApiService;
import org.openo.sdno.l3vpnservice.service.util.ControllerUtils;
import org.openo.sdno.model.servicemodel.routeprotocol.RouteProtocolSpec;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.uniformsbi.l3vpn.BgpRoute;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Vpn;
import org.openo.sdno.model.uniformsbi.l3vpn.Route;
import org.openo.sdno.model.uniformsbi.l3vpn.StaticRoute;
import org.openo.sdno.rest.ResponseUtils;
import org.openo.sdno.wanvpn.translator.common.OperType;
import org.openo.sdno.wanvpn.translator.impl.TranslatorCtxFactoryImpl;
import org.openo.sdno.wanvpn.translator.impl.TranslatorCtxImpl;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtxFactory;
import org.openo.sdno.wanvpn.translator.uniformsbi.impl.L3VpnRouteTranslatorImpl;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3VpnRouteTranslator;
import org.openo.sdno.wanvpn.util.rest.RestUtil;

import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.Tested;

public class UniformL3VpnRouteAddSvcServiceImplTest {

    @Tested
    final UniformL3VpnRouteAddSvcServiceImpl routeAddSvcService = new UniformL3VpnRouteAddSvcServiceImpl();

    @Mocked
    private @Context HttpServletRequest httpServletRequest;

    @Mocked
    private HttpServletRequest request;

    @Injectable
    private final L3VpnRouteProtocolSpecDao l3VpnRouteProDao = new L3VpnRouteProtocolSpecDao() {

        @Override
        @Mock
        public void addMos(final List<RouteProtocolSpec> mos) throws ServiceException {
            return;
        }

        @Override
        @Mock
        public boolean delMos(final List<RouteProtocolSpec> mos) throws ServiceException {
            return true;
        }
    };

    @Injectable
    private final L3VpnDao vpnDao = new L3VpnDao() {};

    @Injectable
    private final L3VpnRouteTranslator l3VpnRouteTranslator = new L3VpnRouteTranslatorImpl();

    @Injectable
    private final TranslatorCtxFactory translatorCtxFactory = new TranslatorCtxFactoryImpl() {

        @Override
        @Mock
        public TranslatorCtx getTranslatorCtx(final OperType operType) {
            final TranslatorCtx ctx = new TranslatorCtxImpl();
            return ctx;
        }
    };

    @Injectable
    private final L3VpnSbiApiService l3VpnSbiApiService = new L3VpnSbiApi() {

        @Override
        @Mock
        public void createStaticRoute(StaticRoute staticRoute, String l3vpnUuid, String tpUuid, String ctrlUuid,
                @Context HttpServletRequest httpServletRequest) throws ServiceException {
        }

        @Override
        @Mock
        public void createBgpRoute(BgpRoute bgpRoute, String l3vpnUuid, String tpUuid, String ctrlUuid,
                @Context HttpServletRequest httpServletRequest) throws ServiceException {
        }
    };

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
            public RestfulResponse sendPostRequest(String url, RestfulParametes restfulParametes,
                    @Context HttpServletRequest httpServletRequest, int timeout) throws ServiceException {
                RestfulResponse restfulResponse = new RestfulResponse();
                L3Vpn vpn = new L3Vpn();
                restfulResponse.setResponseJson(JsonUtil.toJson(vpn));
                return restfulResponse;
            }
        };

        new MockUp<L3VpnRouteTranslatorImpl>() {

            @Mock
            public Route translate(TranslatorCtx ctx) throws ServiceException, IOException {
                String routeFilePath = new File("src/test/resources/route.json").getCanonicalPath();
                final Route route = JsonUtil.fromJson(JsonFileUtil.getJsonString(routeFilePath), Route.class);
                return route;
            }
        };

        new MockUp<ControllerUtils>() {

            @Mock
            public String getControllerUUID(Vpn vpn) throws ServiceException {
                return UuidUtils.createUuid();
            }
        };

        new MockUp<ResponseUtils>() {

            @Mock
            public void checkResonseAndThrowException(RestfulResponse response) {

            }

        };

    }

    @Test
    public void testAddStaticRoute() throws ServiceException, IOException {
        String filePath = new File("src/test/resources/vpn.json").getCanonicalPath();
        final Vpn vpn = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Vpn.class);

        Tp tp = vpn.getAccessPointList().get(0);
        String tpUuid = tp.getId();

        String routeFilePath = new File("src/test/resources/staticRoute.json").getCanonicalPath();
        final RouteProtocolSpec routeProtocolSpec =
                JsonUtil.fromJson(JsonFileUtil.getJsonString(routeFilePath), RouteProtocolSpec.class);

        routeAddSvcService.addRouteProtocol(vpn, tpUuid, routeProtocolSpec, httpServletRequest);
    }

    @Test
    public void test() throws ServiceException, IOException {
        routeAddSvcService.setL3VpnRouteTranslator(new L3VpnRouteTranslatorImpl());
        routeAddSvcService.setL3VpnRouteProDao(new L3VpnRouteProtocolSpecDao());
    }
}
