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
import org.openo.sdno.testframework.http.model.HttpModelUtils;
import org.openo.sdno.testframework.http.model.HttpRequest;
import org.openo.sdno.testframework.http.model.HttpResponse;
import org.openo.sdno.testframework.http.model.HttpRquestResponse;
import org.openo.sdno.testframework.replace.PathReplace;
import org.openo.sdno.testframework.testmanager.TestManager;
import org.openo.sdno.testframework.topology.ResourceType;
import org.openo.sdno.testframework.topology.Topology;

public class ITQueryTePath extends TestManager {

    private static final String CREATE_L3VPN_TESTCASE =
            "src/integration-test/resources/testcase/createl3vpn/createl3vpn.json";

    private static final String DELETE_L3VPN_TESTCASE =
            "src/integration-test/resources/testcase/deletel3vpn/deletel3vpn.json";

    private static final String QUERY_TEPATH_TESTCASE =
            "src/integration-test/resources/testcase/querytepath/querytepath.json";

    private static final String TOPODATA_PATH = "src/integration-test/resources/topodata";

    private static Topology topo = new Topology(TOPODATA_PATH);

    private static L3vpnSbiAdapterServer adapterServer = new L3vpnSbiAdapterServer();

    @BeforeClass
    public static void setup() throws ServiceException {
        topo.createInvTopology();
        DriverRegisterManager.registerDriver();
        adapterServer.start();
    }

    @AfterClass
    public static void tearDown() throws ServiceException {
        topo.clearInvTopology();
        DriverRegisterManager.unRegisterDriver();
        adapterServer.stop();
    }

    @Test
    public void testQueryTePath() throws ServiceException {
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

        // Query Tepath
        HttpRquestResponse httpQueryTepathObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(QUERY_TEPATH_TESTCASE);
        HttpRequest queryTepathRequest = httpQueryTepathObject.getRequest();
        queryTepathRequest.setUri(PathReplace.replaceUuid("l3vpnid", queryTepathRequest.getUri(), createdVpnUUId));
        execTestCase(queryTepathRequest, new SuccessChecker());

        // Delete L3vpn
        HttpRquestResponse httpDeleteObject = HttpModelUtils.praseHttpRquestResponseFromFile(DELETE_L3VPN_TESTCASE);
        HttpRequest deleteRequest = httpDeleteObject.getRequest();
        deleteRequest.setUri(PathReplace.replaceUuid("l3vpnid", deleteRequest.getUri(), createdVpnUUId));
        execTestCase(deleteRequest, new SuccessChecker());
    }

    @Test
    public void testQueryTepathIdNotExist() throws ServiceException {
        HttpRquestResponse httpQueryTepathObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(QUERY_TEPATH_TESTCASE);
        HttpRequest queryTepathRequest = httpQueryTepathObject.getRequest();

        queryTepathRequest.setUri(PathReplace.replaceUuid("l3vpnid", queryTepathRequest.getUri(), "NotExistId"));

        execTestCase(queryTepathRequest, new FailChecker());
    }

    @Test
    public void testQueryTepathIdFormatError() throws ServiceException {
        HttpRquestResponse httpQueryTepathObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(QUERY_TEPATH_TESTCASE);
        HttpRequest queryTepathRequest = httpQueryTepathObject.getRequest();

        queryTepathRequest.setUri(PathReplace.replaceUuid("l3vpnid", queryTepathRequest.getUri(), "%3333&#"));

        execTestCase(queryTepathRequest, new FailChecker());
    }

}
