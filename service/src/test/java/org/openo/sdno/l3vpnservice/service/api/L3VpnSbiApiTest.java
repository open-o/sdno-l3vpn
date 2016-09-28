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

package org.openo.sdno.l3vpnservice.service.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.commons.httpclient.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.l3vpnservice.service.util.ControllerUtils;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.uniformsbi.base.AdapterResponseInfo;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.AdminStatus;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.OperStatus;
import org.openo.sdno.model.uniformsbi.l3vpn.BgpRoute;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Ac;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Acs;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Vpn;
import org.openo.sdno.model.uniformsbi.l3vpn.StaticRoute;
import org.openo.sdno.result.Result;
import org.openo.sdno.wanvpn.translator.uniformsbi.L3TranslatorProviderImpl;
import org.openo.sdno.wanvpn.translator.uniformsbi.impl.ResponsTranslatorImpl;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.ResponsTranslator;
import org.openo.sdno.wanvpn.util.rest.RestUtil;

import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;

public class L3VpnSbiApiTest {

    @Mocked
    private @Context HttpServletRequest httpServletRequest;

    private final L3VpnSbiApi l3VpnSbiApi = new L3VpnSbiApi();

    private final L3TranslatorProviderImpl l3TranslatorProvider = new L3TranslatorProviderImpl();

    private final ResponsTranslator responsTranslator = new ResponsTranslatorImpl();

    @Before
    public void setUp() throws Exception {

        l3TranslatorProvider.setResponsTranslator(responsTranslator);
        l3VpnSbiApi.setResponsTranslator(responsTranslator);
        new MockUp<RestUtil>() {

            @Mock
            public RestfulResponse sendPutRequest(String url, RestfulParametes restfulParametes,
                    @Context HttpServletRequest httpServletRequest) throws ServiceException {
                RestfulResponse response = new RestfulResponse();
                AdapterResponseInfo adapterResp = new AdapterResponseInfo(200, null);
                response.setResponseJson(JsonUtil.toJson(adapterResp));
                response.setStatus(HttpStatus.SC_OK);
                return response;
            }

            @Mock
            public RestfulResponse sendGetRequest(final String url, final RestfulParametes restfulParametes,
                    final @Context HttpServletRequest httpServletRequest) throws ServiceException {
                RestfulResponse response = new RestfulResponse();
                AdapterResponseInfo adapterResp = new AdapterResponseInfo(200, null);
                L3Vpn l3Vpn = new L3Vpn();
                l3Vpn.setName("L3Vpn4Test");
                l3Vpn.setUuid(UuidUtils.createUuid());
                L3Acs acs = new L3Acs();
                List<L3Ac> l3AcList = new ArrayList<L3Ac>();
                L3Ac ac = new L3Ac();
                ac.setAdminStatus(AdminStatus.ADMIN_UP);
                ac.setOperStatus(OperStatus.OPERATE_UP);
                ac.setName("NEPort1");
                ac.setUuid(UuidUtils.createUuid());
                l3AcList.add(ac);
                acs.setL3Ac(l3AcList);
                l3Vpn.setAcs(acs);
                adapterResp.setMsg(JsonUtil.toJson(l3Vpn));

                Result<String> result = new Result<String>();
                result.setResultObj(JsonUtil.toJson(adapterResp));
                response.setResponseJson(JsonUtil.toJson(result));
                response.setStatus(HttpStatus.SC_OK);
                return response;
            }

            @Mock
            public RestfulResponse sendDeleteRequest(final String url, final RestfulParametes restfulParametes,
                    final @Context HttpServletRequest httpServletRequest) throws ServiceException {
                RestfulResponse response = new RestfulResponse();
                AdapterResponseInfo adapterResp = new AdapterResponseInfo(200, null);
                response.setResponseJson(JsonUtil.toJson(adapterResp));
                response.setStatus(HttpStatus.SC_OK);
                return response;
            }

            @Mock
            public RestfulResponse sendPostRequest(final String url, final RestfulParametes restfulParametes,
                    final @Context HttpServletRequest httpServletRequest, final int timeout) throws ServiceException {
                RestfulResponse response = new RestfulResponse();
                AdapterResponseInfo adapterResp = new AdapterResponseInfo(200, null);
                response.setResponseJson(JsonUtil.toJson(adapterResp));
                response.setStatus(HttpStatus.SC_OK);
                return response;
            }

        };

        new MockUp<ControllerUtils>() {

            @Mock
            public String getControllerUUID(final Vpn vpn) throws ServiceException {
                return UuidUtils.createUuid();
            }
        };
    }

    @Test
    public void testCreateL3VPN() throws ServiceException {
        L3Vpn l3Vpn = new L3Vpn();
        Assert.assertNotNull(l3VpnSbiApi.createL3VPN(l3Vpn, UuidUtils.createUuid(), httpServletRequest));
    }

    @Test
    public void testCreateTp() throws ServiceException {
        L3Ac ac = new L3Ac();
        Assert.assertNotNull(
                l3VpnSbiApi.createTp(ac, UuidUtils.createUuid(), UuidUtils.createUuid(), httpServletRequest));
    }

    @Test
    public void testGetL3vpnDetail() throws ServiceException {
        Assert.assertNotNull(
                l3VpnSbiApi.getL3vpnDetail(UuidUtils.createUuid(), UuidUtils.createUuid(), httpServletRequest));
    }

    @Test
    public void testCreateBgpRoute() throws ServiceException {
        BgpRoute bgpRoute = new BgpRoute();
        l3VpnSbiApi.createBgpRoute(bgpRoute, UuidUtils.createUuid(), UuidUtils.createUuid(), UuidUtils.createUuid(),
                httpServletRequest);
    }

    @Test
    public void testDeleteStaticRoute() throws ServiceException {
        Vpn tempVpn = new Vpn();
        tempVpn.setId(UuidUtils.createUuid());
        l3VpnSbiApi.deleteStaticRoute(tempVpn, UuidUtils.createUuid(), "", httpServletRequest);
    }

    @Test
    public void testDeleteTp() throws ServiceException {
        l3VpnSbiApi.deleteTp(UuidUtils.createUuid(), UuidUtils.createUuid(), UuidUtils.createUuid(),
                httpServletRequest);
    }

    @Test
    public void testDeleteVpn() throws ServiceException {
        l3VpnSbiApi.deleteVpn(UuidUtils.createUuid(), UuidUtils.createUuid(), httpServletRequest);
    }

    @Test
    public void testDeployTpStatus() throws ServiceException {
        L3Ac l3ac = new L3Ac();
        l3ac.setUuid(UuidUtils.createUuid());
        l3VpnSbiApi.deployTpStatus(UuidUtils.createUuid(), l3ac, UuidUtils.createUuid(), httpServletRequest);
    }

    @Test
    public void testDeployVpnStatus() throws ServiceException {
        L3Vpn l3Vpn = new L3Vpn();
        l3Vpn.setUuid(UuidUtils.createUuid());
        l3VpnSbiApi.deployVpnStatus(l3Vpn, UuidUtils.createUuid(), httpServletRequest);
    }

    @Test
    public void testModifyVpnDescrition() throws ServiceException {
        L3Vpn l3Vpn = new L3Vpn();
        l3Vpn.setUuid(UuidUtils.createUuid());
        l3VpnSbiApi.modifyVpnDescrition(UuidUtils.createUuid(), l3Vpn, httpServletRequest);
    }

    @Test
    public void testCreateStaticRoute() throws ServiceException {
        StaticRoute staticRoute = new StaticRoute();
        l3VpnSbiApi.createStaticRoute(staticRoute, UuidUtils.createUuid(), UuidUtils.createUuid(),
                UuidUtils.createUuid(), httpServletRequest);
    }

    @Test
    public void testDeleteBgpRoute() throws ServiceException {
        Vpn tempVpn = new Vpn();
        tempVpn.setId(UuidUtils.createUuid());
        l3VpnSbiApi.deleteBgpRoute(tempVpn, UuidUtils.createUuid(), "", httpServletRequest);
    }
}
