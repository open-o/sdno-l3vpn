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

package org.openo.sdno.wanvpn.inventory.sdk.impl;

import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.exception.HttpCode;
import org.openo.sdno.wanvpn.inventory.sdk.common.OwnerInfoThreadLocal;
import org.openo.sdno.wanvpn.inventory.sdk.impl.NetworkElementInvDaoImpl;
import org.openo.sdno.wanvpn.util.rest.InventorySDKRestUtil;
import org.openo.sdno.wanvpn.util.rest.RestUtil;

import mockit.Mock;
import mockit.MockUp;

/**
 * NetworkElementInvDaoTest class.<br>
 * 
 * @author
 * @version SDNO 0.5 Aug 4, 2016
 */
public class NetworkElementInvDaoTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testQueryMOByIdWithNullInput() throws ServiceException {
        NetworkElementInvDaoImpl dao = new NetworkElementInvDaoImpl();
        assertNull(dao.queryMOById(null));
    }

    @Test
    public void testQueryMOByIdWithEmptyInput() throws ServiceException {
        NetworkElementInvDaoImpl dao = new NetworkElementInvDaoImpl();
        assertNull(dao.queryMOById(""));
    }

    @Test
    public void testQueryMOByIdReturnFail() throws ServiceException {
        MockUp<OwnerInfoThreadLocal> mock = new MockUp<OwnerInfoThreadLocal>() {

            @Mock
            public HttpServletRequest getHttpServletRequest() {

                return null;
            }
        };

        MockUp<RestUtil> mock2 = new MockUp<RestUtil>() {

            @Mock
            public RestfulResponse get(@Context HttpServletRequest request, String url) throws ServiceException {
                RestfulResponse rsp = new RestfulResponse();
                rsp.setStatus(HttpCode.ERR_FAILED);
                return rsp;
            }
        };

        NetworkElementInvDaoImpl dao = new NetworkElementInvDaoImpl();
        assertNull(dao.queryMOById("123"));
        mock.tearDown();
        mock2.tearDown();
    }

    @Test
    public void testQueryMOByParamWithNullInput() throws ServiceException {
        MockUp<OwnerInfoThreadLocal> mock = new MockUp<OwnerInfoThreadLocal>() {

            @Mock
            public HttpServletRequest getHttpServletRequest() {

                return null;
            }
        };

        MockUp<InventorySDKRestUtil> mock2 = new MockUp<InventorySDKRestUtil>() {

            @Mock
            public RestfulResponse sendGetRequest(String url, RestfulParametes restfulParametes,
                    @Context HttpServletRequest request, String owner) throws ServiceException {
                RestfulResponse rsp = new RestfulResponse();
                rsp.setStatus(HttpCode.ERR_FAILED);
                return rsp;
            }
        };

        NetworkElementInvDaoImpl dao = new NetworkElementInvDaoImpl();
        assertNull(dao.queryMOByParam(null));
        mock.tearDown();
        mock2.tearDown();
    }

    @Test
    public void testQueryMOByParamReturnFail() throws ServiceException {
        MockUp<OwnerInfoThreadLocal> mock = new MockUp<OwnerInfoThreadLocal>() {

            @Mock
            public HttpServletRequest getHttpServletRequest() {

                return null;
            }
        };

        MockUp<InventorySDKRestUtil> mock2 = new MockUp<InventorySDKRestUtil>() {

            @Mock
            public RestfulResponse sendGetRequest(String url, RestfulParametes restfulParametes,
                    @Context HttpServletRequest request, String owner) throws ServiceException {
                RestfulResponse rsp = new RestfulResponse();
                rsp.setStatus(HttpCode.ERR_FAILED);
                return rsp;
            }
        };

        NetworkElementInvDaoImpl dao = new NetworkElementInvDaoImpl();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("1", "v1");
        assertNull(dao.queryMOByParam(paramMap));
        mock.tearDown();
        mock2.tearDown();
    }
}
