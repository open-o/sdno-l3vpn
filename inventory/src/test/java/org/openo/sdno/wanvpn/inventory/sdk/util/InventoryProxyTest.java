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

package org.openo.sdno.wanvpn.inventory.sdk.util;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.model.servicemodel.brs.NetworkElementMO;
import org.openo.sdno.result.Result;
import org.openo.sdno.wanvpn.util.rest.RestUtil;

import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;

public class InventoryProxyTest {

    @Mocked
    HttpServletRequest serve;

    @Test
    public void testQueryNeByReturnEmpty() throws ServiceException {
        new MockUp<RestUtil>() {

            @Mock
            public RestfulResponse get(@Context HttpServletRequest request, final String url) {
                RestfulResponse returning = new RestfulResponse();
                returning.setStatus(404);
                return returning;
            }

        };

        assertNull(InventoryProxy.queryNeById("testNotOk"));

    }

    @Test
    public void testQueryNeByIdNormal() throws ServiceException {
        new MockUp<RestUtil>() {

            @Mock
            public RestfulResponse get(@Context HttpServletRequest request, final String url) {
                RestfulResponse returning = new RestfulResponse();
                returning.setStatus(200);
                Map<String, Object> contrl = new HashMap<String, Object>();
                NetworkElementMO demo = new NetworkElementMO();
                demo.setId("123456");
                contrl.put("managedElement", demo);
                String responseString = JsonUtil.toJson(contrl);
                returning.setResponseJson(responseString);
                return returning;
            }

        };

        assertTrue(InventoryProxy.queryNeById("testNotOk").getResultObj().getId().equals("123456"));
    }

    @Test
    public void testQueryNeByIdEmpty() throws ServiceException {
        assertNull(InventoryProxy.queryNeById(""));
    }

    @Test
    public void testQueryNeByIdEmpty1() throws ServiceException {
        assertNull(InventoryProxy.queryNeById(null));
    }

    @Test
    public void testQueryControllerByIDNull() throws ServiceException {
        new MockUp<RestfulProxy>() {

            @Mock
            public RestfulResponse get(String uri, RestfulParametes restParametes) throws ServiceException {
                RestfulResponse response = new RestfulResponse();
                response.setStatus(404);
                return response;
            }
        };
        assertNull(InventoryProxy.queryController(""));
    }

    @Test
    public void testQueryControllerByIDNull1() throws ServiceException {
        new MockUp<RestfulProxy>() {

            @Mock
            public RestfulResponse get(String uri, RestfulParametes restParametes) throws ServiceException {
                RestfulResponse response = new RestfulResponse();
                response.setStatus(404);
                return response;
            }
        };
        new MockUp<InventoryProxy>() {

            @Mock
            public Result<NetworkElementMO> queryNeById(final String id) throws ServiceException {
                Result<NetworkElementMO> moResult = new Result<NetworkElementMO>();
                NetworkElementMO MO = new NetworkElementMO();
                List<String> idlist = new ArrayList<String>();
                idlist.add("controller");
                MO.setControllerID(idlist);
                moResult.setResultObj(MO);
                moResult.setErrcode(1);
                return moResult;
            }

        };
        assertNull(InventoryProxy.queryController(""));
    }

    @Test
    public void testQueryControllerByIDControllerIdNull() throws ServiceException {
        new MockUp<InventoryProxy>() {

            @Mock
            public Result<NetworkElementMO> queryNeById(final String id) throws ServiceException {
                Result<NetworkElementMO> moResult = new Result<NetworkElementMO>();
                NetworkElementMO MO = new NetworkElementMO();
                List<String> idlist = new ArrayList<String>();
                MO.setControllerID(idlist);
                moResult.setResultObj(MO);
                moResult.setErrcode(0);
                return moResult;
            }

        };
        assertNull(InventoryProxy.queryController(""));
    }

    @Test
    public void testQueryControllerByIDNormalNull() throws ServiceException {
        new MockUp<RestfulProxy>() {

            @Mock
            public RestfulResponse get(String uri, RestfulParametes restParametes) throws ServiceException {
                RestfulResponse response = new RestfulResponse();
                response.setStatus(404);
                return response;
            }
        };
        new MockUp<InventoryProxy>() {

            @Mock
            public Result<NetworkElementMO> queryNeById(final String id) throws ServiceException {
                Result<NetworkElementMO> moResult = new Result<NetworkElementMO>();
                NetworkElementMO MO = new NetworkElementMO();
                List<String> idlist = new ArrayList<String>();
                MO.setControllerID(idlist);
                moResult.setResultObj(MO);
                moResult.setErrcode(0);
                return moResult;
            }

        };
        assertNull(InventoryProxy.queryController(""));
    }

    @Test
    public void testQueryControllerByIDNormal() throws ServiceException {
        new MockUp<InventoryProxy>() {

            @Mock
            public Result<NetworkElementMO> queryNeById(final String id) throws ServiceException {
                Result<NetworkElementMO> moResult = new Result<NetworkElementMO>();
                NetworkElementMO MO = new NetworkElementMO();
                List<String> idlist = new ArrayList<String>();
                idlist.add("controller");
                MO.setControllerID(idlist);
                moResult.setResultObj(MO);
                moResult.setErrcode(0);
                return moResult;
            }

        };
        assertTrue(InventoryProxy.queryController("test").getId().equals("controller"));
    }
}
