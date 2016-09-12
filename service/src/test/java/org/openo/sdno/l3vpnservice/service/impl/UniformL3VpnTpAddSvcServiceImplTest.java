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
import org.openo.sdno.l3vpnservice.dao.L3VpnTpDao;
import org.openo.sdno.l3vpnservice.service.api.L3VpnSbiApi;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnQuerySvcService;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnSbiApiService;
import org.openo.sdno.l3vpnservice.service.util.ControllerUtils;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Ac;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Vpn;
import org.openo.sdno.wanvpn.translator.common.OperType;
import org.openo.sdno.wanvpn.translator.impl.TranslatorCtxFactoryImpl;
import org.openo.sdno.wanvpn.translator.impl.TranslatorCtxImpl;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtxFactory;
import org.openo.sdno.wanvpn.translator.uniformsbi.impl.L3AcTranslatorImpl;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3AcTranslator;
import org.openo.sdno.wanvpn.util.rest.RestUtil;

import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.Tested;

public class UniformL3VpnTpAddSvcServiceImplTest {

    @Tested
    final UniformL3VpnTpAddSvcServiceImpl tpAddSvcService = new UniformL3VpnTpAddSvcServiceImpl();

    @Injectable
    private final L3VpnTpDao tpDao = new L3VpnTpDao() {

        @Override
        @Mock
        public boolean delMos(final List<Tp> mos) throws ServiceException {
            return true;
        }

        @Override
        @Mock
        public void addMos(final List<Tp> mos) throws ServiceException {
        }
    };

    @Injectable
    private final L3AcTranslator l3AcTranslator = new L3AcTranslatorImpl();

    @Injectable
    private final L3VpnQuerySvcService l3VpnQuerySvcService = new UniformL3VpnQuerySvcServiceImpl() {

        @Override
        @Mock
        public Vpn getStatus(Vpn vpn, @Context HttpServletRequest httpServletRequest) throws ServiceException {
            return new Vpn();
        }
    };

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
        public L3Ac createTp(L3Ac ac, String l3VpnId, String ctrlUuid, @Context HttpServletRequest httpServletRequest)
                throws ServiceException {
            return new L3Ac();
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
            public RestfulResponse sendPostRequest(String url, RestfulParametes restfulParametes,
                    @Context HttpServletRequest httpServletRequest, int timeout) throws ServiceException {
                RestfulResponse restfulResponse = new RestfulResponse();
                L3Vpn vpn = new L3Vpn();
                restfulResponse.setResponseJson(JsonUtil.toJson(vpn));
                return restfulResponse;
            }
        };

        new MockUp<L3AcTranslatorImpl>() {

            @Mock
            public L3Ac translate(TranslatorCtx ctx) throws ServiceException {
                return new L3Ac();
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
    public void testAddTp() throws ServiceException, IOException {
        String filePath = new File("src/test/resources/vpn.json").getCanonicalPath();
        final Vpn vpn = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Vpn.class);
        vpn.getId();
        Tp tp = vpn.getAccessPointList().get(0);
        tpAddSvcService.addTp(vpn, tp, httpServletRequest);
    }

    @Test
    public void testSet() {
        tpAddSvcService.setL3AcTranslator(new L3AcTranslatorImpl());
        tpAddSvcService.setL3VpnQuerySvcService(new UniformL3VpnQuerySvcServiceImpl());
        tpAddSvcService.setTpDao(new L3VpnTpDao());
    }
}
