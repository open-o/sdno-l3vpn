/*
 * Copyright 2016-2017 Huawei Technologies Co., Ltd.
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.commons.httpclient.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulOptions;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.baseservice.util.RestUtils;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.l3vpnservice.JsonFileUtil;
import org.openo.sdno.l3vpnservice.service.util.ControllerUtils;
import org.openo.sdno.model.db.l3vpn.L3VpnPo;
import org.openo.sdno.model.servicemodel.brs.NetworkElementMO;
import org.openo.sdno.model.servicemodel.tepath.TePath;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.servicemodel.vpn.VpnBasicInfo;
import org.openo.sdno.model.servicemodel.vpn.VpnVo;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Vpn;
import org.openo.sdno.result.Result;
import org.openo.sdno.wanvpn.inventory.sdk.common.ErrorCode;
import org.openo.sdno.wanvpn.inventory.sdk.impl.PuerInvDAOImpl;
import org.openo.sdno.wanvpn.inventory.sdk.result.ResultRsp;
import org.openo.sdno.wanvpn.util.query.BatchQueryResult;
import org.openo.sdno.wanvpn.util.query.mss.QueryComplexParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.Assert;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring/applicationContext.xml",
                "classpath*:META-INF/spring/service.xml", "classpath*:spring/service.xml"})
public class L3VpnSvcResourceTest {

    @Mocked
    HttpServletRequest request;

    @Mocked
    HttpServletResponse response;

    @Autowired
    L3VpnSvcResource l3VpnSvcResource;

    @Before
    public void setUp() throws Exception {
        new MockInventoryDao();
        new MockRestfulProxy();
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

        Vpn rs = l3VpnSvcResource.createSingleVpn(request);
        Assert.assertNotNull(rs);
    }

    @Test
    public void testDeleteSingleVpn() throws ServiceException {

        new MockUp<Vpn>() {

            @Mock
            public VpnBasicInfo getVpnBasicInfo() {
                return new VpnBasicInfo();
            }

            @Mock
            public List<Tp> getAccessPointList() {
                List<Tp> tps = new ArrayList<>();
                tps.add(new Tp());
                return tps;
            }

        };

        new MockUp<ControllerUtils>() {

            @Mock
            public String getControllerUUID(final Vpn vpn) throws ServiceException {
                return "controllerId";
            }

        };

        Vpn rs = l3VpnSvcResource.deleteSingleVpn("uuid", request);
        Assert.assertNotNull(rs);
    }

    @Test
    public void testDeleteSingleVpnWhenGetVpnForDeleteIsNull() throws ServiceException {

        new MockUp<L3VpnSvcResource>() {

            @Mock
            Vpn getVpnForDelete(final String uuid, @Context final HttpServletRequest request) throws ServiceException {
                return null;
            }

        };

        new MockUp<Vpn>() {

            @Mock
            public VpnBasicInfo getVpnBasicInfo() {
                return new VpnBasicInfo();
            }

            @Mock
            public List<Tp> getAccessPointList() {
                List<Tp> tps = new ArrayList<>();
                tps.add(new Tp());
                return tps;
            }

        };

        new MockUp<ControllerUtils>() {

            @Mock
            public String getControllerUUID(final Vpn vpn) throws ServiceException {
                return "controllerId";
            }

        };

        Vpn rs = l3VpnSvcResource.deleteSingleVpn("uuid", request);
        Assert.assertNotNull(rs);
    }

    @Test
    public void testGetDetail() throws ServiceException, IOException {
        Vpn rs = l3VpnSvcResource.getDetail("uuid", request);
        Assert.assertNotNull(rs);
    }

    @Test
    public void testGetStatus() throws ServiceException, IOException {
        new MockUp<Vpn>() {

            @Mock
            public VpnBasicInfo getVpnBasicInfo() {
                return new VpnBasicInfo();
            }

            @Mock
            public List<Tp> getAccessPointList() {
                List<Tp> tps = new ArrayList<>();
                Tp tp = new Tp();
                tp.setNeId("neId");
                tps.add(tp);
                return tps;
            }

        };

        Vpn rs = l3VpnSvcResource.getStatus("uuid", request);
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

        new MockUp<ControllerUtils>() {

            @Mock
            public String getControllerUUID(final Vpn vpn) throws ServiceException {
                return "controllerId";
            }

        };

        new MockUp<Vpn>() {

            @Mock
            public VpnBasicInfo getVpnBasicInfo() {
                return new VpnBasicInfo();
            }

            @Mock
            public List<Tp> getAccessPointList() {
                List<Tp> tps = new ArrayList<>();
                Tp tp = new Tp();
                tp.setNeId("neId");
                tps.add(tp);
                return tps;
            }

        };

        Tp rs = l3VpnSvcResource.addTp("uuid", request);
        Assert.assertNotNull(rs);
    }

    @Test
    public void testDeleteSingleTp() throws ServiceException {

        new MockUp<Vpn>() {

            @Mock
            public VpnBasicInfo getVpnBasicInfo() {
                return new VpnBasicInfo();
            }

            @Mock
            public List<Tp> getAccessPointList() {
                List<Tp> tps = new ArrayList<>();
                Tp tp = new Tp();
                tp.setNeId("neId1");
                tp.setUuid("tpUuid1");
                tps.add(tp);

                Tp tp2 = new Tp();
                tp2.setNeId("neId2");
                tp2.setUuid("tpUuid2");
                tps.add(tp2);

                return tps;
            }

        };

        Tp rs = l3VpnSvcResource.deleteSingleTp("tpUuid1", "uuid", request);
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

        new MockUp<ControllerUtils>() {

            @Mock
            public String getControllerUUID(final Vpn vpn) throws ServiceException {
                return "controllerId";
            }

        };

        Vpn rs = l3VpnSvcResource.updateDesc("uuid", request);
        Assert.assertNotNull(rs);
    }

    @Test
    public void testGetTePath() throws ServiceException {

        new MockUp<Vpn>() {

            @Mock
            public List<Tp> getAccessPointList() {
                List<Tp> tps = new ArrayList<>();
                Tp tp = new Tp();
                tp.setNeId("neId1");
                tp.setUuid("tpUuid1");
                tps.add(tp);

                Tp tp2 = new Tp();
                tp2.setNeId("neId2");
                tp2.setUuid("tpUuid2");
                tps.add(tp2);

                return tps;
            }

        };

        BatchQueryResult<TePath> rs =
                l3VpnSvcResource.getTePath("uuid", "srcNeId", "destNeId", "tpUuid1", "tpUuid2", request);
        Assert.assertNotNull(rs);
    }

    private final class MockInventoryDao<T> extends MockUp<PuerInvDAOImpl<T>> {

        @Mock
        ResultRsp<List<String>> add(final List<T> moList, final Class<?> moType) throws ServiceException {
            return new ResultRsp(ErrorCode.UNDERLAYVPN_SUCCESS);
        }

        @Mock
        ResultRsp<String> delete(final String uuid, final Class moType) throws ServiceException {
            return new ResultRsp(ErrorCode.UNDERLAYVPN_SUCCESS);
        }

        @Mock
        ResultRsp<List<String>> delete(final List<T> moList, final Class<?> moType) throws ServiceException {
            return new ResultRsp(ErrorCode.UNDERLAYVPN_SUCCESS);
        }

        @Mock
        ResultRsp batchDelete(final List<String> uuidList, final Class<?> moType) throws ServiceException {
            return new ResultRsp(ErrorCode.UNDERLAYVPN_SUCCESS);
        }

        @Mock
        ResultRsp update(final List<T> moList, final Class<?> moType) throws ServiceException {
            return new ResultRsp(ErrorCode.UNDERLAYVPN_SUCCESS);
        }

        @Mock
        ResultRsp<List<T>> queryComplex(final Class moType, final QueryComplexParams params) throws ServiceException {
            return new ResultRsp(ErrorCode.UNDERLAYVPN_SUCCESS);
        }

        @Mock
        ResultRsp<List<T>> queryAll(final Class moType, final QueryComplexParams params)
                throws ServiceException, CloneNotSupportedException {
            return new ResultRsp(ErrorCode.UNDERLAYVPN_SUCCESS);
        }

        @Mock
        ResultRsp<T> query(final String uuid, final Class<?> moType) throws ServiceException, IOException {
            ResultRsp<T> resp = new ResultRsp(ErrorCode.UNDERLAYVPN_SUCCESS);
            final L3VpnPo po = new L3VpnPo();
            po.setVpnBasicInfoId("vpnBasicInfoId");
            po.setUuid("uuid");
            resp.setData((T)po);

            return resp;
        }
    }

    private final class MockRestfulProxy extends MockUp<RestfulProxy> {

        @Mock
        RestfulResponse get(String uri, RestfulParametes restParametes, RestfulOptions restOptions)
                throws ServiceException {
            RestfulResponse response = new RestfulResponse();

            if(uri.startsWith("/openoapi/sdnobrs/v1/managed-elements")) {
                Map<String, Object> contentMap = new HashMap<>();
                NetworkElementMO networkElementMO = new NetworkElementMO();
                networkElementMO.setNativeID("nativeID");

                List<String> contrlIds = new ArrayList<>();
                contrlIds.add("controlId1");
                networkElementMO.setControllerID(contrlIds);
                contentMap.put("managedElement", networkElementMO);

                response.setStatus(HttpStatus.SC_OK);
                response.setResponseJson(JsonUtil.toJson(contentMap));
            } else if(uri.startsWith("/openoapi/sbi-l3vpn/v1/l3vpns")) {
                Result<String> sbiRsp = new Result<>();
                sbiRsp.setResultObj(JsonUtil.toJson(new L3Vpn()));
                response.setResponseJson(JsonUtil.toJson(sbiRsp));
                response.setStatus(HttpStatus.SC_OK);
            }

            return response;
        }

        @Mock
        RestfulResponse post(String uri, RestfulParametes restParametes, RestfulOptions restOptions)
                throws ServiceException {
            RestfulResponse response = new RestfulResponse();
            if(uri.startsWith("/openoapi/sbi-l3vpn/v1/l3vpns")) {
                Result<String> sbiRsp = new Result<>();
                response.setResponseJson(JsonUtil.toJson(sbiRsp));
            }

            response.setStatus(HttpStatus.SC_OK);

            return response;
        }

        @Mock
        RestfulResponse delete(String uri, RestfulParametes restParametes, RestfulOptions restOptions)
                throws ServiceException {
            RestfulResponse response = new RestfulResponse();

            Result<String> sbiRsp = new Result<>();
            response.setResponseJson(JsonUtil.toJson(sbiRsp));
            response.setStatus(HttpStatus.SC_OK);

            return response;
        }

        @Mock
        RestfulResponse put(String uri, RestfulParametes restParametes, RestfulOptions restOptions)
                throws ServiceException {
            RestfulResponse response = new RestfulResponse();

            Result<String> sbiRsp = new Result<>();
            response.setResponseJson(JsonUtil.toJson(sbiRsp));
            response.setStatus(HttpStatus.SC_OK);

            return response;
        }

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
