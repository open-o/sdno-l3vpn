/*
 * Copyright 2017 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.wanvpn.inventory.sdk.impl.nbi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.model.servicemodel.mss.BatchAddOrModifyResponse;
import org.openo.sdno.model.servicemodel.mss.BatchQueryResponse;
import org.openo.sdno.model.servicemodel.mss.QueryParams;
import org.openo.sdno.wanvpn.util.rest.InventorySDKRestUtil;

import mockit.Mock;
import mockit.MockUp;

public class PuerInvServiceNbiBeanTest {

    PuerInvServiceNbiBean puerInvServiceNbiBean = new PuerInvServiceNbiBean();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testAdd() throws ServiceException {
        new MockUp<InventorySDKRestUtil>() {

            @Mock
            public RestfulResponse sendPostRequest(final String url, final RestfulParametes restfulParametes,
                    @Context HttpServletRequest request, final String owner) throws ServiceException {
                RestfulResponse restfulResponse = new RestfulResponse();
                BatchAddOrModifyResponse batchAddOrModifyResponse = new BatchAddOrModifyResponse();
                List<Map<String, Object>> objects = new ArrayList<>();
                Map<String, Object> map = new HashMap<>();
                map.put("name", "value");
                objects.add(map);
                batchAddOrModifyResponse.setObjects(objects);
                restfulResponse.setResponseJson(JsonUtil.toJson(batchAddOrModifyResponse));

                return restfulResponse;
            }

        };

        List listMapValue = new ArrayList<>();
        List<Map<String, Object>> rspList = puerInvServiceNbiBean.add("ipsec", listMapValue);
        assertEquals(rspList.get(0).get("name"), "value");
    }

    @Test(expected = ServiceException.class)
    public void testAddException() throws ServiceException {
        new MockUp<InventorySDKRestUtil>() {

            @Mock
            public RestfulResponse sendPostRequest(final String url, final RestfulParametes restfulParametes,
                    @Context HttpServletRequest request, final String owner) throws ServiceException {
                throw new ServiceException();
            }

        };

        List listMapValue = new ArrayList<>();
        puerInvServiceNbiBean.add("ipsec", listMapValue);
    }

    @Test
    public void testUpdate() throws ServiceException {
        new MockUp<InventorySDKRestUtil>() {

            @Mock
            public RestfulResponse sendPutRequest(final String url, final RestfulParametes restfulParametes,
                    @Context HttpServletRequest request, final String owner) throws ServiceException {
                RestfulResponse restfulResponse = new RestfulResponse();
                BatchAddOrModifyResponse batchAddOrModifyResponse = new BatchAddOrModifyResponse();
                List<Map<String, Object>> objects = new ArrayList<>();
                Map<String, Object> map = new HashMap<>();
                map.put("name", "value");
                objects.add(map);
                batchAddOrModifyResponse.setObjects(objects);
                restfulResponse.setResponseJson(JsonUtil.toJson(batchAddOrModifyResponse));

                return restfulResponse;
            }

        };

        List listMapValue = new ArrayList<>();
        List<Map<String, Object>> rspList = puerInvServiceNbiBean.update("ipsec", listMapValue);
        assertEquals(rspList.get(0).get("name"), "value");
    }

    @Test(expected = ServiceException.class)
    public void testUpdateException() throws ServiceException {
        new MockUp<InventorySDKRestUtil>() {

            @Mock
            public RestfulResponse sendPutRequest(final String url, final RestfulParametes restfulParametes,
                    @Context HttpServletRequest request, final String owner) throws ServiceException {
                throw new ServiceException();
            }

        };

        List listMapValue = new ArrayList<>();
        List<Map<String, Object>> rspList = puerInvServiceNbiBean.update("ipsec", listMapValue);
    }

    @Test
    public void testGet() throws ServiceException {
        new MockUp<InventorySDKRestUtil>() {

            @Mock
            public RestfulResponse sendGetRequest(final String url, final RestfulParametes restfulParametes,
                    @Context HttpServletRequest request, final String owner) throws ServiceException {
                RestfulResponse restfulResponse = new RestfulResponse();

                Map<String, Object> maps = new HashMap<>();
                Map<String, Object> map = new HashMap<>();
                map.put("name", "value");
                maps.put("object", map);
                restfulResponse.setResponseJson(JsonUtil.toJson(maps));

                return restfulResponse;
            }

        };

        Map<String, Object> rspMap = puerInvServiceNbiBean.get("uuid", PuerInvServiceNbiBean.class, "name");
        assertEquals(rspMap.get("name"), "value");
    }

    @Test(expected = ServiceException.class)
    public void testGetException() throws ServiceException {
        new MockUp<InventorySDKRestUtil>() {

            @Mock
            public RestfulResponse sendGetRequest(final String url, final RestfulParametes restfulParametes,
                    @Context HttpServletRequest request, final String owner) throws ServiceException {
                throw new ServiceException();
            }

        };

        puerInvServiceNbiBean.get("uuid", PuerInvServiceNbiBean.class, "name");
    }

    @Test
    public void testqueryAll() throws ServiceException {
        new MockUp<InventorySDKRestUtil>() {

            @Mock
            public RestfulResponse sendGetRequest(final String url, final RestfulParametes restfulParametes,
                    @Context HttpServletRequest request, final String owner) throws ServiceException {
                RestfulResponse restfulResponse = new RestfulResponse();
                BatchQueryResponse batchQueryResponse = new BatchQueryResponse();
                batchQueryResponse.setTotalPageNum(5);
                batchQueryResponse.setPageSize(2);
                batchQueryResponse.setTotal(10);
                batchQueryResponse.setCurrentPage(0);
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                Map<String, Object> map = new HashMap<>();
                list.add(map);
                map.put("key", "object");
                batchQueryResponse.setObjects(list);

                restfulResponse.setResponseJson(JsonUtil.toJson(batchQueryResponse));

                return restfulResponse;
            }
        };

        Map<String, Object> filter = new HashMap<>();
        List list1 = new ArrayList<>();
        list1.add("111");
        list1.add("222");
        filter.put("name", list1);
        filter.put("id", null);
        QueryParams queryParams = new QueryParams(JsonUtil.toJson(filter), "name, id", "name", "sortType");
        List<Map<String, Object>> rspList = puerInvServiceNbiBean.queryAll("ipsec", queryParams);
        assertEquals(rspList.get(0).get("key"), "object");
    }

    @Test(expected = ServiceException.class)
    public void testqueryAllException() throws ServiceException {
        new MockUp<InventorySDKRestUtil>() {

            @Mock
            public RestfulResponse sendGetRequest(final String url, final RestfulParametes restfulParametes,
                    @Context HttpServletRequest request, final String owner) throws ServiceException {
                throw new ServiceException();
            }
        };

        QueryParams queryParams = new QueryParams(null, "name, id", "name", "sortType");
        puerInvServiceNbiBean.queryAll("ipsec", queryParams);
    }

    @Test
    public void testDeleteOne() throws ServiceException {
        new MockUp<InventorySDKRestUtil>() {

            @Mock
            public RestfulResponse sendDeleteRequest(final String url, final RestfulParametes restfulParametes,
                    @Context HttpServletRequest request, final String owner) throws ServiceException {
                RestfulResponse restfulResponse = new RestfulResponse();

                return restfulResponse;
            }

        };

        try {
            puerInvServiceNbiBean.deleteOne("ipsec", "uuid");
            assertTrue(true);
        } catch(ServiceException e) {
            assertTrue(false);
        }
    }

    @Test(expected = ServiceException.class)
    public void testDeleteOneException() throws ServiceException {
        new MockUp<InventorySDKRestUtil>() {

            @Mock
            public RestfulResponse sendDeleteRequest(final String url, final RestfulParametes restfulParametes,
                    @Context HttpServletRequest request, final String owner) throws ServiceException {
                throw new ServiceException();
            }

        };

        puerInvServiceNbiBean.deleteOne("ipsec", "uuid");
    }

    @Test
    public void testQueryTotalNumber() throws ServiceException {
        new MockUp<InventorySDKRestUtil>() {

            @Mock
            public RestfulResponse sendGetRequest(final String url, final RestfulParametes restfulParametes,
                    @Context HttpServletRequest request, final String owner) throws ServiceException {
                RestfulResponse restfulResponse = new RestfulResponse();

                restfulResponse.setResponseJson("6");

                return restfulResponse;
            }

        };

        Map<String, Object> filter = new HashMap<>();
        List list1 = new ArrayList<>();
        list1.add("111");
        list1.add("222");
        filter.put("name", list1);
        filter.put("id", null);
        long num = puerInvServiceNbiBean.queryTotalNumber("ipsec", JsonUtil.toJson(filter));
        assertEquals(num, 6);
    }

    @Test(expected = ServiceException.class)
    public void testQueryTotalNumberException() throws ServiceException {
        new MockUp<InventorySDKRestUtil>() {

            @Mock
            public RestfulResponse sendGetRequest(final String url, final RestfulParametes restfulParametes,
                    @Context HttpServletRequest request, final String owner) throws ServiceException {
                throw new ServiceException();
            }

        };

        Map<String, Object> filter = new HashMap<>();
        List list1 = new ArrayList<>();
        list1.add("111");
        list1.add("222");
        filter.put("name", list1);
        filter.put("id", null);
        puerInvServiceNbiBean.queryTotalNumber("ipsec", JsonUtil.toJson(filter));
    }

    @Test
    public void testBatchDelete() throws ServiceException {
        new MockUp<InventorySDKRestUtil>() {

            @Mock
            public RestfulResponse sendDeleteRequest(final String url, final RestfulParametes restfulParametes,
                    @Context HttpServletRequest request, final String owner) throws ServiceException {
                RestfulResponse restfulResponse = new RestfulResponse();

                return restfulResponse;
            }

        };

        Map<String, Object> filter = new HashMap<>();
        List list = new ArrayList<>();
        list.add("1");
        list.add("2");

        try {
            puerInvServiceNbiBean.batchDelete("ipsec", list);
            assertTrue(true);
        } catch(ServiceException e) {
            assertTrue(false);
        }
    }

    @Test(expected = ServiceException.class)
    public void testBatchDeleteException() throws ServiceException {
        new MockUp<InventorySDKRestUtil>() {

            @Mock
            public RestfulResponse sendDeleteRequest(final String url, final RestfulParametes restfulParametes,
                    @Context HttpServletRequest request, final String owner) throws ServiceException {
                throw new ServiceException();
            }

        };

        puerInvServiceNbiBean.batchDelete("ipsec", new ArrayList<String>());
    }

    @Test(expected = ServiceException.class)
    public void testQueryOtherPageException() throws ServiceException {
        new MockUp<InventorySDKRestUtil>() {

            @Mock
            public RestfulResponse sendGetRequest(final String url, final RestfulParametes restfulParametes,
                    @Context HttpServletRequest request, final String owner) throws ServiceException {
                throw new ServiceException();
            }

        };

        puerInvServiceNbiBean.queryOtherPage("ipsec", new RestfulParametes(), "0");
    }
}
