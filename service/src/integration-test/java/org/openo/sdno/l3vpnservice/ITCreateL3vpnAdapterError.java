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
import org.openo.sdno.l3vpnservice.mocoserver.CreateL3vpnFailSbiAdapterlServer;
import org.openo.sdno.l3vpnservice.util.DriverRegisterManager;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.VpnVo;
import org.openo.sdno.testframework.http.model.HttpModelUtils;
import org.openo.sdno.testframework.http.model.HttpRequest;
import org.openo.sdno.testframework.http.model.HttpRquestResponse;
import org.openo.sdno.testframework.testmanager.TestManager;
import org.openo.sdno.testframework.topology.ResourceType;
import org.openo.sdno.testframework.topology.Topology;

public class ITCreateL3vpnAdapterError extends TestManager {

    private static final String CREATE_L3VPN_TESTCASE2 =
            "src/integration-test/resources/testcase/createl3vpn/createl3vpn2.json";

    private static final String TOPODATA_PATH = "src/integration-test/resources/topodata";

    private static Topology topo = new Topology(TOPODATA_PATH);

    private static CreateL3vpnFailSbiAdapterlServer adapterServer = new CreateL3vpnFailSbiAdapterlServer();

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
    public void TestL3vpnCreateFailed() throws ServiceException, InterruptedException {
        HttpRquestResponse httpCreateObject = HttpModelUtils.praseHttpRquestResponseFromFile(CREATE_L3VPN_TESTCASE2);
        HttpRequest createRequest = httpCreateObject.getRequest();
        VpnVo vpnData = JsonUtil.fromJson(createRequest.getData(), VpnVo.class);
        List<Tp> tpList = vpnData.getVpn().getAccessPointList();
        tpList.get(0).setNeId("NEError");
        tpList.get(1).setNeId(topo.getResourceUuid(ResourceType.NETWORKELEMENT, "Ne2"));
        createRequest.setData(JsonUtil.toJson(vpnData));
        execTestCase(createRequest, new FailChecker());
    }

}
