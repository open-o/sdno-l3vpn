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

import org.apache.commons.httpclient.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.l3vpnservice.JsonFileUtil;
import org.openo.sdno.l3vpnservice.dao.L3VpnDao;
import org.openo.sdno.l3vpnservice.service.api.L3VpnSbiApi;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnQuerySvcService;
import org.openo.sdno.l3vpnservice.service.util.ControllerUtils;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.servicemodel.vpn.VpnVo;
import org.openo.sdno.model.uniformsbi.base.AdapterResponseInfo;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Vpn;
import org.openo.sdno.rest.ResponseUtils;
import org.openo.sdno.wanvpn.translator.common.OperType;
import org.openo.sdno.wanvpn.translator.impl.TranslatorCtxFactoryImpl;
import org.openo.sdno.wanvpn.translator.impl.TranslatorCtxImpl;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtxFactory;
import org.openo.sdno.wanvpn.translator.uniformsbi.L3TranslatorProviderImpl;
import org.openo.sdno.wanvpn.translator.uniformsbi.impl.L3VpnTranslatorImpl;
import org.openo.sdno.wanvpn.translator.uniformsbi.impl.ResponsTranslatorImpl;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3VpnTranslator;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.ResponsTranslator;
import org.openo.sdno.wanvpn.util.rest.RestUtil;

import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;

public class UniformL3VpnCreateSvcServiceImplTest {

    private final UniformL3VpnCreateSvcServiceImpl uniformL3VpnCreateSvcServiceImpl =
            new UniformL3VpnCreateSvcServiceImpl();

    private final L3VpnSbiApi l3VpnSbiApiService = new L3VpnSbiApi();

    private final ResponsTranslator responsTranslator = new ResponsTranslatorImpl();

    private final L3VpnDao vpnDao = new L3VpnDao();

    private final TranslatorCtxFactory translatorCtxFactory = new TranslatorCtxFactoryImpl();

    private final L3VpnQuerySvcService l3VpnQuerySvcService = new UniformL3VpnQuerySvcServiceImpl();

    private final L3VpnTranslatorImpl l3VpnTranslatorImpl = new L3VpnTranslatorImpl();

    @Mocked
    private HttpServletRequest httpServletRequest;

    @Before
    public void setUp() throws Exception {

        l3VpnSbiApiService.setResponsTranslator(responsTranslator);
        new MockUp<ControllerUtils>() {

            @Mock
            public String getControllerUUID(Vpn vpn) throws ServiceException {
                return UuidUtils.createUuid();
            }
        };
        new MockUp<RestUtil>() {

            @Mock
            public RestfulResponse sendPostRequest(String url, RestfulParametes restfulParametes,
                    @Context HttpServletRequest httpServletRequest, int timeout) throws ServiceException {
                RestfulResponse restfulResponse = new RestfulResponse();
                AdapterResponseInfo adapterResp = new AdapterResponseInfo(200, null);
                restfulResponse.setResponseJson(JsonUtil.toJson(adapterResp));
                restfulResponse.setStatus(HttpStatus.SC_OK);
                return restfulResponse;
            }
        };
        new MockUp<L3VpnDao>() {

            @Mock
            public void addMos(List<Vpn> mos) throws ServiceException {
            }

            @Mock
            public boolean isVpnNameExisted(final String name) throws ServiceException {
                return false;
            }
        };

        new MockUp<UniformL3VpnQuerySvcServiceImpl>() {

            @Mock
            public Vpn getStatus(final Vpn vpn, @Context HttpServletRequest httpServletRequest)
                    throws ServiceException {
                return new Vpn();
            }
        };

        new MockUp<TranslatorCtxFactoryImpl>() {

            @Mock
            public TranslatorCtx getTranslatorCtx(final OperType operType) {
                final TranslatorCtx ctx = new TranslatorCtxImpl();
                ctx.setOperType(operType);
                return ctx;
            }
        };

        new MockUp<L3TranslatorProviderImpl>() {

            @Mock
            public L3VpnTranslator getL3VpnTranslator() {
                return new L3VpnTranslatorImpl();
            }
        };

        new MockUp<L3VpnTranslatorImpl>() {

            @Mock
            public L3Vpn

                    translate(TranslatorCtx ctx) throws ServiceException {
                return new L3Vpn();
            }
        };

        new MockUp<ResponseUtils>() {

            @Mock
            public void checkResonseAndThrowException(RestfulResponse response) {

            }

        };

    }

    @Test
    public void testCreate() throws ServiceException, IOException {
        uniformL3VpnCreateSvcServiceImpl.setL3VpnSbiApiService(l3VpnSbiApiService);
        uniformL3VpnCreateSvcServiceImpl.setTranslatorCtxFactory(translatorCtxFactory);
        uniformL3VpnCreateSvcServiceImpl.setVpnDao(vpnDao);
        uniformL3VpnCreateSvcServiceImpl.setL3VpnQuerySvcService(l3VpnQuerySvcService);
        uniformL3VpnCreateSvcServiceImpl.setL3VpnTranslator(l3VpnTranslatorImpl);
        String filePath = new File("src/test/resources/vpnVo.json").getCanonicalPath();
        final VpnVo vpnVo = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), VpnVo.class);

        Assert.assertNotNull(uniformL3VpnCreateSvcServiceImpl.create(vpnVo, httpServletRequest));
    }

}
