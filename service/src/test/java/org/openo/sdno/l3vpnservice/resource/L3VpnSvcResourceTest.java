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

package org.openo.sdno.l3vpnservice.resource;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.util.RestUtils;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.l3vpnservice.JsonFileUtil;
import org.openo.sdno.l3vpnservice.service.impl.UniformL3VpnSvcServiceImpl;
import org.openo.sdno.l3vpnservice.service.util.ControllerUtils;
import org.openo.sdno.model.servicemodel.routeprotocol.RouteProtocolSpec;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.servicemodel.vpn.VpnVo;

import junit.framework.Assert;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;

public class L3VpnSvcResourceTest {

    private final L3VpnSvcResource l3VpnSvcResource = new L3VpnSvcResource();

    private final UniformL3VpnSvcServiceImpl l3VpnServiceImpl = new UniformL3VpnSvcServiceImpl();

    @Mocked
    private HttpServletRequest httpServletRequest;

    @Before
    public void setUp() throws Exception {

        l3VpnSvcResource.setService(l3VpnServiceImpl);

        new MockUp<UniformL3VpnSvcServiceImpl>() {

            @Mock
            public Vpn active(Vpn vpn, @Context HttpServletRequest httpServletRequest) throws ServiceException {
                return new Vpn();
            }

            @Mock
            public Vpn deactive(Vpn vpn, @Context HttpServletRequest httpServletRequest) throws ServiceException {
                return new Vpn();
            }

            @Mock
            public Vpn create(VpnVo vpnVo, @Context HttpServletRequest httpServletRequest) throws ServiceException {
                return new Vpn();
            }

            @Mock
            public Vpn delete(Vpn Vpn, @Context HttpServletRequest httpServletRequest) throws ServiceException {
                return new Vpn();
            }

            @Mock
            public Vpn getDetail(String uuid, final @Context HttpServletRequest httpServletRequest)
                    throws ServiceException {
                return new Vpn();
            }

            @Mock
            public Vpn getStatus(Vpn Vpn, @Context HttpServletRequest httpServletRequest) throws ServiceException {
                return new Vpn();
            }

            @Mock
            public Vpn modifyDesc(Vpn vpn, @Context HttpServletRequest httpServletRequest) throws ServiceException {
                return new Vpn();
            }

            @Mock
            public Tp activeSite(Vpn vpn, String tpId, @Context HttpServletRequest httpServletRequest)
                    throws ServiceException {
                return new Tp();
            }

            @Mock
            public Tp deactiveSite(Vpn vpn, String tpId, @Context HttpServletRequest httpServletRequest)
                    throws ServiceException {
                return new Tp();
            }

            @Mock
            public Tp addTp(Vpn vpn, Tp tp, @Context HttpServletRequest httpServletRequest) throws ServiceException {
                return new Tp();
            }

            @Mock
            public Tp deleteTp(Vpn vpn, String tpuuid, @Context HttpServletRequest httpServletRequest)
                    throws ServiceException {
                return new Tp();
            }

            @Mock
            public RouteProtocolSpec addRoute(Vpn vpn, String tpUuid, RouteProtocolSpec routeProtocolSpec,
                    @Context HttpServletRequest httpServletRequest) throws ServiceException {
                return new RouteProtocolSpec();
            }

            @Mock
            public RouteProtocolSpec deleteRoute(Vpn vpn, String tpUuid, String routeId,
                    @Context HttpServletRequest httpServletRequest) throws ServiceException {
                return new RouteProtocolSpec();
            }
        };
        new MockUp<ControllerUtils>() {

            @Mock
            public String getControllerType(Vpn vpn) {
                return "default";
            }

        };
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetResUri() {
        Assert.assertNotNull(l3VpnSvcResource.getResUri());
    }

    @Test
    public void testCreateSingleVpn() throws ServiceException, IOException {

        new MockUp<RestUtils>() {

            @Mock
            public String getRequestBody(HttpServletRequest request) throws IOException {
                VpnVo vpnVo = getVpnVoFromJson();
                return JsonUtil.toJson(vpnVo);
            }

        };

        Vpn rs = l3VpnSvcResource.createSingleVpn(httpServletRequest);
        Assert.assertNotNull(rs);
    }

    @Test
    public void testDeleteSingleVpn() throws ServiceException, IOException {
        String uuid = UuidUtils.createUuid();
        Vpn rs = l3VpnSvcResource.deleteSingleVpn(uuid, httpServletRequest);
        Assert.assertNotNull(rs);
    }

    @Test
    public void testGetDetail() throws ServiceException, IOException {
        String uuid = UuidUtils.createUuid();
        Vpn rs = l3VpnSvcResource.getDetail(uuid, httpServletRequest);
        Assert.assertNotNull(rs);
    }

    @Test
    public void testGetStatus() throws ServiceException, IOException {
        String uuid = UuidUtils.createUuid();
        Vpn rs = l3VpnSvcResource.getStatus(uuid, httpServletRequest);
        Assert.assertNotNull(rs);
    }

    @Test
    public void testUpdateDesc() throws ServiceException, IOException {

        new MockUp<RestUtils>() {

            @Mock
            public String getRequestBody(HttpServletRequest request) throws IOException {
                Vpn vpn = getVpnFromJson();
                return JsonUtil.toJson(vpn);
            }

        };

        String uuid = UuidUtils.createUuid();

        Vpn rs = l3VpnSvcResource.updateDesc(uuid, httpServletRequest);
        Assert.assertNotNull(rs);
    }

    @Test
    public void testAddTp() throws ServiceException, IOException {

        new MockUp<RestUtils>() {

            @Mock
            public String getRequestBody(HttpServletRequest request) throws IOException {
                Tp tp = getTpFromJson();
                return JsonUtil.toJson(tp);
            }

        };

        String uuid = UuidUtils.createUuid();

        Tp rs = l3VpnSvcResource.addTp(uuid, httpServletRequest);
        Assert.assertNotNull(rs);
    }

    @Test
    public void testdeleteSingleTp() throws ServiceException, IOException {
        String uuid = UuidUtils.createUuid();
        String tpUuid = UuidUtils.createUuid();
        Tp rs = l3VpnSvcResource.deleteSingleTp(tpUuid, uuid, httpServletRequest);
        Assert.assertNotNull(rs);
    }

    private Tp getTpFromJson() throws IOException {
        final String filePath = new File("src/test/resources/tp.json").getCanonicalPath();
        Tp tp = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Tp.class);
        Assert.assertNotNull(tp);
        return tp;
    }

    private VpnVo getVpnVoFromJson() throws IOException {
        final String filePath = new File("src/test/resources/vpnVo.json").getCanonicalPath();
        VpnVo vpn = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), VpnVo.class);
        Assert.assertNotNull(vpn);
        return vpn;
    }

    private Vpn getVpnFromJson() throws IOException {
        final String filePath = new File("src/test/resources/vpn.json").getCanonicalPath();
        Vpn vpn = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Vpn.class);
        Assert.assertNotNull(vpn);
        return vpn;
    }

}
