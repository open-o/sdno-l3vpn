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
import org.openo.sdno.l3vpnservice.dao.L3VpnTpDao;
import org.openo.sdno.l3vpnservice.service.util.ControllerUtils;
import org.openo.sdno.l3vpnservice.service.util.L3VpnServiceUtils;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Ac;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Vpn;
import org.openo.sdno.wanvpn.translator.common.OperType;
import org.openo.sdno.wanvpn.translator.impl.TranslatorCtxFactoryImpl;
import org.openo.sdno.wanvpn.translator.impl.TranslatorCtxImpl;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtxFactory;
import org.openo.sdno.wanvpn.translator.uniformsbi.L3TranslatorProvider;
import org.openo.sdno.wanvpn.translator.uniformsbi.L3TranslatorProviderImpl;
import org.openo.sdno.wanvpn.translator.uniformsbi.impl.L3AcTranslatorImpl;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3AcTranslator;
import org.openo.sdno.wanvpn.util.rest.RestUtil;

import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.Tested;

public class UniformL3VpnTpModifySvcServiceImplTest {

    @Tested
    final UniformL3VpnTpModifySvcServiceImpl tpModifySvcService = new UniformL3VpnTpModifySvcServiceImpl();

    @Injectable
    private final L3VpnTpDao tpDao = new L3VpnTpDao() {

        @Override
        @Mock
        public boolean updateMos(final List<Tp> mos) throws ServiceException {
            return true;
        }
    };

    @Injectable
    private final L3TranslatorProvider l3TranslatorProvider = new L3TranslatorProviderImpl() {

        @Override
        @Mock
        public L3AcTranslator getL3AcTranslator() {
            return new L3AcTranslatorImpl();
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
            public RestfulResponse sendPutRequest(String url, RestfulParametes restfulParametes,
                    @Context HttpServletRequest httpServletRequest) throws ServiceException {
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

    }

    @Test
    public void testModifyTp() throws ServiceException, IOException {
        String filePath = new File("src/test/resources/vpn.json").getCanonicalPath();
        final Vpn vpn = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Vpn.class);
        final Tp tp = vpn.getAccessPointList().get(0);

        new MockUp<L3VpnServiceUtils>() {

            @Mock
            public Tp getTpFromVpn(final Vpn vpn, final String tpId) {
                return tp;
            }
        };

        new MockUp<L3VpnServiceUtils>() {

            @Mock
            public Tp getTpFromVpn(final Vpn vpn, final String tpId) {
                return null;
            }
        };

    }
}
