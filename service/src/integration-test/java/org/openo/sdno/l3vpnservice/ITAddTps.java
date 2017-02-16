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

package org.openo.sdno.l3vpnservice;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.l3vpnservice.checker.FailChecker;
import org.openo.sdno.l3vpnservice.checker.SuccessChecker;
import org.openo.sdno.l3vpnservice.mocoserver.L3vpnSbiAdapterServer;
import org.openo.sdno.l3vpnservice.util.DriverRegisterManager;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.servicemodel.vpn.VpnVo;
import org.openo.sdno.testframework.checker.ServiceExceptionChecker;
import org.openo.sdno.testframework.http.model.HttpModelUtils;
import org.openo.sdno.testframework.http.model.HttpRequest;
import org.openo.sdno.testframework.http.model.HttpResponse;
import org.openo.sdno.testframework.http.model.HttpRquestResponse;
import org.openo.sdno.testframework.replace.PathReplace;
import org.openo.sdno.testframework.testmanager.TestManager;
import org.openo.sdno.testframework.topology.ResourceType;
import org.openo.sdno.testframework.topology.Topology;
import org.openo.sdno.wanvpn.util.error.CommonErrorCode;

public class ITAddTps extends TestManager {

    private static final String CREATE_L3VPN_TESTCASE =
            "src/integration-test/resources/testcase/createl3vpn/createl3vpn.json";

    private static final String ADD_TPS_TESTCASE = "src/integration-test/resources/testcase/addtps/addtps.json";

    private static final String ADD_INVALID_TPS_TESTCASE =
            "src/integration-test/resources/testcase/addtps/addinvalidtps.json";

    private static final String DELETE_L3VPN_TESTCASE =
            "src/integration-test/resources/testcase/deletel3vpn/deletel3vpn.json";

    private static final String DELETE_TPS_TESTCASE =
            "src/integration-test/resources/testcase/deletetps/deletetps.json";

    private static final String TOPODATA_PATH = "src/integration-test/resources/topodata";

    private static Topology topo = new Topology(TOPODATA_PATH);

    private static L3vpnSbiAdapterServer adapter = new L3vpnSbiAdapterServer();

    @BeforeClass
    public static void setup() throws ServiceException {
        topo.createInvTopology();
        DriverRegisterManager.registerDriver();
        adapter.start();
    }

    @AfterClass
    public static void tearDown() throws ServiceException {
        topo.clearInvTopology();
        DriverRegisterManager.unRegisterDriver();
        adapter.stop();
    }

    @Test
    public void testAddTps() throws ServiceException {

        // Create L3vpn
        HttpRquestResponse httpCreateObject = HttpModelUtils.praseHttpRquestResponseFromFile(CREATE_L3VPN_TESTCASE);
        HttpRequest createRequest = httpCreateObject.getRequest();
        VpnVo vpnData = JsonUtil.fromJson(createRequest.getData(), VpnVo.class);
        List<Tp> tpList = vpnData.getVpn().getAccessPointList();
        tpList.get(0).setNeId(topo.getResourceUuid(ResourceType.NETWORKELEMENT, "Ne1"));
        tpList.get(1).setNeId(topo.getResourceUuid(ResourceType.NETWORKELEMENT, "Ne2"));
        createRequest.setData(JsonUtil.toJson(vpnData));
        HttpResponse createResponse = execTestCase(createRequest, new SuccessChecker());
        Vpn createdVpn = JsonUtil.fromJson(createResponse.getData(), Vpn.class);
        String createdVpnUUId = createdVpn.getUuid();

        // Add Tps
        HttpRquestResponse httpAddTpsObject = HttpModelUtils.praseHttpRquestResponseFromFile(ADD_TPS_TESTCASE);
        HttpRequest addTpsRequest = httpAddTpsObject.getRequest();
        Tp tpObject = JsonUtil.fromJson(addTpsRequest.getData(), Tp.class);
        tpObject.setNeId(topo.getResourceUuid(ResourceType.NETWORKELEMENT, "Ne1"));
        addTpsRequest.setData(JsonUtil.toJson(tpObject));
        addTpsRequest.setUri(PathReplace.replaceUuid("l3vpnid", addTpsRequest.getUri(), createdVpnUUId));
        HttpResponse addTpResponse = execTestCase(addTpsRequest, new SuccessChecker());
        Tp addedTp = JsonUtil.fromJson(addTpResponse.getData(), Tp.class);

        // Delete Tps
        HttpRquestResponse httpDeleteTpsObject = HttpModelUtils.praseHttpRquestResponseFromFile(DELETE_TPS_TESTCASE);
        HttpRequest deleteTpsRequest = httpDeleteTpsObject.getRequest();
        deleteTpsRequest.setUri(PathReplace.replaceUuid("l3vpnid", deleteTpsRequest.getUri(), createdVpnUUId));
        deleteTpsRequest.setUri(PathReplace.replaceUuid("tpid", deleteTpsRequest.getUri(), addedTp.getId()));
        execTestCase(deleteTpsRequest, new SuccessChecker());

        // Delete L3vpn
        HttpRquestResponse httpDeleteObject = HttpModelUtils.praseHttpRquestResponseFromFile(DELETE_L3VPN_TESTCASE);
        HttpRequest deleteRequest = httpDeleteObject.getRequest();
        deleteRequest.setUri(PathReplace.replaceUuid("l3vpnid", deleteRequest.getUri(), createdVpnUUId));
        execTestCase(deleteRequest, new SuccessChecker());
    }

    @Test
    public void testAddTpsIdNotExist() throws ServiceException {
        HttpRquestResponse httpAddTpsObject = HttpModelUtils.praseHttpRquestResponseFromFile(ADD_TPS_TESTCASE);
        HttpRequest addTpsRequest = httpAddTpsObject.getRequest();
        addTpsRequest.setUri(PathReplace.replaceUuid("l3vpnid", addTpsRequest.getUri(), "NotExistId"));
        execTestCase(addTpsRequest, new FailChecker());
    }

    @Test
    public void testAddTpsIdFormatError() throws ServiceException {
        HttpRquestResponse httpAddTpsObject = HttpModelUtils.praseHttpRquestResponseFromFile(ADD_TPS_TESTCASE);
        HttpRequest addTpsRequest = httpAddTpsObject.getRequest();
        addTpsRequest.setUri(PathReplace.replaceUuid("l3vpnid", addTpsRequest.getUri(), "^7744&&**"));
        execTestCase(addTpsRequest, new FailChecker());
    }

    @Test
    public void testAddInvalidTps() throws ServiceException {

        // Create L3vpn
        HttpRquestResponse httpCreateObject = HttpModelUtils.praseHttpRquestResponseFromFile(CREATE_L3VPN_TESTCASE);
        HttpRequest createRequest = httpCreateObject.getRequest();
        VpnVo vpnData = JsonUtil.fromJson(createRequest.getData(), VpnVo.class);
        List<Tp> tpList = vpnData.getVpn().getAccessPointList();
        tpList.get(0).setNeId(topo.getResourceUuid(ResourceType.NETWORKELEMENT, "Ne1"));
        tpList.get(1).setNeId(topo.getResourceUuid(ResourceType.NETWORKELEMENT, "Ne2"));
        createRequest.setData(JsonUtil.toJson(vpnData));
        HttpResponse createResponse = execTestCase(createRequest, new SuccessChecker());
        Vpn createdVpn = JsonUtil.fromJson(createResponse.getData(), Vpn.class);
        String createdVpnUUId = createdVpn.getUuid();

        // Add Tps
        HttpRquestResponse httpAddTpsObject = HttpModelUtils.praseHttpRquestResponseFromFile(ADD_INVALID_TPS_TESTCASE);
        HttpRequest addTpsRequest = httpAddTpsObject.getRequest();
        Tp tpObject = JsonUtil.fromJson(addTpsRequest.getData(), Tp.class);
        tpObject.setNeId(topo.getResourceUuid(ResourceType.NETWORKELEMENT, "Ne1"));
        addTpsRequest.setData(JsonUtil.toJson(tpObject));
        addTpsRequest.setUri(PathReplace.replaceUuid("l3vpnid", addTpsRequest.getUri(), createdVpnUUId));
        execTestCase(addTpsRequest, new ServiceExceptionChecker(CommonErrorCode.CHECKER_IP_INVALID));

        // Delete L3vpn
        HttpRquestResponse httpDeleteObject = HttpModelUtils.praseHttpRquestResponseFromFile(DELETE_L3VPN_TESTCASE);
        HttpRequest deleteRequest = httpDeleteObject.getRequest();
        deleteRequest.setUri(PathReplace.replaceUuid("l3vpnid", deleteRequest.getUri(), createdVpnUUId));
        execTestCase(deleteRequest, new SuccessChecker());
    }

    @Test
    public void testDeleteTpsVpnIdNotExist() throws ServiceException {
        HttpRquestResponse httpDeleteTpsObject = HttpModelUtils.praseHttpRquestResponseFromFile(DELETE_TPS_TESTCASE);
        HttpRequest deleteTpsRequest = httpDeleteTpsObject.getRequest();
        deleteTpsRequest.setUri(PathReplace.replaceUuid("l3vpnid", deleteTpsRequest.getUri(), "NotExistId"));
        deleteTpsRequest.setUri(PathReplace.replaceUuid("tpid", deleteTpsRequest.getUri(), "testtpid"));
        execTestCase(deleteTpsRequest, new FailChecker());
    }

    @Test
    public void testDeleteTpsTpIdNotExist() throws ServiceException {
        // Create L3vpn
        HttpRquestResponse httpCreateObject = HttpModelUtils.praseHttpRquestResponseFromFile(CREATE_L3VPN_TESTCASE);
        HttpRequest createRequest = httpCreateObject.getRequest();
        VpnVo vpnData = JsonUtil.fromJson(createRequest.getData(), VpnVo.class);
        List<Tp> tpList = vpnData.getVpn().getAccessPointList();
        tpList.get(0).setNeId(topo.getResourceUuid(ResourceType.NETWORKELEMENT, "Ne1"));
        tpList.get(1).setNeId(topo.getResourceUuid(ResourceType.NETWORKELEMENT, "Ne2"));
        createRequest.setData(JsonUtil.toJson(vpnData));
        HttpResponse createResponse = execTestCase(createRequest, new SuccessChecker());
        Vpn createdVpn = JsonUtil.fromJson(createResponse.getData(), Vpn.class);
        String createdVpnUUId = createdVpn.getUuid();

        // Delete Tps
        HttpRquestResponse httpDeleteTpsObject = HttpModelUtils.praseHttpRquestResponseFromFile(DELETE_TPS_TESTCASE);
        HttpRequest deleteTpsRequest = httpDeleteTpsObject.getRequest();
        deleteTpsRequest.setUri(PathReplace.replaceUuid("l3vpnid", deleteTpsRequest.getUri(), createdVpnUUId));
        deleteTpsRequest.setUri(PathReplace.replaceUuid("tpid", deleteTpsRequest.getUri(), "NotExtist"));
        execTestCase(deleteTpsRequest, new FailChecker());

        // Delete L3vpn
        HttpRquestResponse httpDeleteObject = HttpModelUtils.praseHttpRquestResponseFromFile(DELETE_L3VPN_TESTCASE);
        HttpRequest deleteRequest = httpDeleteObject.getRequest();
        deleteRequest.setUri(PathReplace.replaceUuid("l3vpnid", deleteRequest.getUri(), createdVpnUUId));
        execTestCase(deleteRequest, new SuccessChecker());
    }

}
