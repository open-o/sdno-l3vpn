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

package org.openo.sdno.l3vpnservice;

import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.HttpCode;
import org.openo.sdno.l3vpnservice.checker.FailChecker;
import org.openo.sdno.l3vpnservice.checker.SuccessChecker;
import org.openo.sdno.testframework.checker.IChecker;
import org.openo.sdno.testframework.http.model.HttpModelUtils;
import org.openo.sdno.testframework.http.model.HttpRequest;
import org.openo.sdno.testframework.http.model.HttpResponse;
import org.openo.sdno.testframework.http.model.HttpRquestResponse;
import org.openo.sdno.testframework.replace.PathReplace;
import org.openo.sdno.testframework.testmanager.TestManager;
import org.openo.sdno.testframework.topology.ResourceType;
import org.openo.sdno.testframework.topology.Topology;

public class ITGetNePorts extends TestManager {

    private static final String GET_NEPORTS_TESTCASE =
            "src/integration-test/resources/testcase/getneports/getneports.json";

    private static final String TOPODATA_PATH = "src/integration-test/resources/topodata";

    private static Topology topo = new Topology(TOPODATA_PATH);

    @BeforeClass
    public static void setup() throws ServiceException {
        topo.createInvTopology();
    }

    @AfterClass
    public static void tearDown() throws ServiceException {
        topo.clearInvTopology();
    }

    @Test
    public void testGetNePorts() throws ServiceException {

        HttpRquestResponse httpQueryNePortsObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(GET_NEPORTS_TESTCASE);
        HttpRequest queryNePortsRequest = httpQueryNePortsObject.getRequest();

        String ne1Id = topo.getResourceUuid(ResourceType.NETWORKELEMENT, "Ne1");
        String ne2Id = topo.getResourceUuid(ResourceType.NETWORKELEMENT, "Ne2");

        String requestUri = new StringBuilder(queryNePortsRequest.getUri()).toString();

        queryNePortsRequest.setUri(PathReplace.replaceUuid("neid", requestUri, ne1Id));
        Map<String, String> queryParamMap = queryNePortsRequest.getQueries();
        queryParamMap.put("commonName", "Ltp1");

        execTestCase(queryNePortsRequest, new QuerySuccessChecker("Ltp1"));

        queryParamMap.put("commonName", "Ltp2");

        execTestCase(queryNePortsRequest, new QuerySuccessChecker("Ltp2"));

        queryNePortsRequest.setUri(PathReplace.replaceUuid("neid", requestUri, ne2Id));
        queryParamMap.put("commonName", "Ltp3");

        execTestCase(queryNePortsRequest, new QuerySuccessChecker("Ltp3"));

        queryParamMap.put("commonName", "Ltp4");

        execTestCase(queryNePortsRequest, new QuerySuccessChecker("Ltp4"));
    }

    @Test
    public void testGetNePortsNeIdNotExist() throws ServiceException {
        HttpRquestResponse httpQueryNePortsObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(GET_NEPORTS_TESTCASE);
        HttpRequest queryNePortsRequest = httpQueryNePortsObject.getRequest();

        queryNePortsRequest.setUri(PathReplace.replaceUuid("neid", queryNePortsRequest.getUri(), "NotExistId"));
        Map<String, String> queryParamMap = queryNePortsRequest.getQueries();
        queryParamMap.put("commonName", "Ltp1");

        execTestCase(queryNePortsRequest, new FailChecker());
    }

    @Test
    public void testGetNePortsNeIdFormatError() throws ServiceException {
        HttpRquestResponse httpQueryNePortsObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(GET_NEPORTS_TESTCASE);
        HttpRequest queryNePortsRequest = httpQueryNePortsObject.getRequest();

        queryNePortsRequest.setUri(PathReplace.replaceUuid("neid", queryNePortsRequest.getUri(), "67&^%#"));
        Map<String, String> queryParamMap = queryNePortsRequest.getQueries();
        queryParamMap.put("commonName", "Ltp1");

        execTestCase(queryNePortsRequest, new FailChecker());
    }

    @Test
    public void testGetNePortsPortNameNotExist() throws ServiceException {
        HttpRquestResponse httpQueryNePortsObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(GET_NEPORTS_TESTCASE);
        HttpRequest queryNePortsRequest = httpQueryNePortsObject.getRequest();

        String neId = topo.getResourceUuid(ResourceType.NETWORKELEMENT, "Ne1");

        queryNePortsRequest.setUri(PathReplace.replaceUuid("neid", queryNePortsRequest.getUri(), neId));
        Map<String, String> queryParamMap = queryNePortsRequest.getQueries();
        queryParamMap.put("commonName", "LtpNotExistName");

        execTestCase(queryNePortsRequest, new SuccessChecker());
    }

    private class QuerySuccessChecker implements IChecker {

        private String ltpName;

        public QuerySuccessChecker(String ltpName) {
            this.ltpName = ltpName;
        }

        @Override
        public boolean check(HttpResponse response) {

            if(HttpCode.isSucess(response.getStatus()) && response.getData().contains(ltpName)) {
                return true;
            }

            return false;
        }
    }

}
