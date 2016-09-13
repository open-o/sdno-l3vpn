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
import org.openo.sdno.l3vpnservice.service.util.ControllerUtils;
import org.openo.sdno.model.servicemodel.common.enumeration.ServiceType;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.uniformsbi.base.AdapterResponseInfo;
import org.openo.sdno.wanvpn.translator.uniformsbi.impl.ResponsTranslatorImpl;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.ResponsTranslator;
import org.openo.sdno.wanvpn.util.rest.RestUtil;

import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;

public class UniformL3VpnDeleteSvcServiceImplTest {

    private final UniformL3VpnDeleteSvcServiceImpl l3VpnDeleteSvcService = new UniformL3VpnDeleteSvcServiceImpl();

    private final UniformL3VpnQuerySvcServiceImpl l3VpnQuerySvcService = new UniformL3VpnQuerySvcServiceImpl();

    private final L3VpnSbiApi l3VpnSbiApiService = new L3VpnSbiApi();

    private final ResponsTranslator responsTranslator = new ResponsTranslatorImpl();

    @Mocked
    private @Context HttpServletRequest httpServletRequest;

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
            public RestfulResponse sendDeleteRequest(String url, RestfulParametes restfulParametes,
                    @Context HttpServletRequest httpServletRequest) throws ServiceException {
                RestfulResponse restfulResponse = new RestfulResponse();
                AdapterResponseInfo adapterResp = new AdapterResponseInfo(200, null);
                restfulResponse.setResponseJson(JsonUtil.toJson(adapterResp));
                restfulResponse.setStatus(HttpStatus.SC_OK);
                return restfulResponse;
            }
        };

        new MockUp<UniformL3VpnQuerySvcServiceImpl>() {

            @Mock
            public Vpn getL3vpnAdminStatus(Vpn vpn, String ctrluuid, @Context HttpServletRequest httpServletRequest)
                    throws ServiceException, IOException {
                String filePath1 = new File("src/test/resources/vpn.json").getCanonicalPath();
                final Vpn vpn1 = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath1), Vpn.class);
                return vpn1;
            }

        };

        new MockUp<L3VpnDao>() {

            @Mock
            public void addMos(List<Vpn> mos) throws ServiceException {
            }

            @Mock
            public Vpn getMoById(String tpId) throws ServiceException {
                return new Vpn();
            }

            @Mock
            public boolean delMos(List<Vpn> mos) throws ServiceException {
                return true;
            }
        };
    }

    @Test
    public void testDelete() throws ServiceException, IOException {
        String filePath = new File("src/test/resources/vpn.json").getCanonicalPath();
        final Vpn vpn = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Vpn.class);
        vpn.getVpnBasicInfo().setServiceType(ServiceType.L3VPN.getCommonName());

        new MockUp<L3VpnDao>() {

            @Mock
            public Vpn getMoById(String tpId) throws ServiceException {
                return vpn;
            }

        };

        l3VpnDeleteSvcService.setVpnDao(new L3VpnDao());
        l3VpnDeleteSvcService.setL3VpnQuerySvcService(l3VpnQuerySvcService);
        l3VpnDeleteSvcService.setL3VpnSbiApiService(l3VpnSbiApiService);
        l3VpnDeleteSvcService.delete(vpn, httpServletRequest);
    }
}
