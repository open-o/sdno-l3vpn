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

package org.openo.sdno.wanvpn.util.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulOptions;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.wanvpn.util.rest.RestUtil;

import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;

public class RestUtilTest {

    @Mocked
    private HttpServletRequest servletRequest;

    @Mocked
    private HttpServletResponse servletResponse;

    @Before
    public void setUp() {

        new MockUp<ServletRequestWrapper>() {

            @Mock
            public Map<String, String[]> getParameterMap() {
                final HashMap<String, String[]> map = new HashMap<>();
                map.put("name", new String[] {"Jack"});
                return map;
            }
        };
    }

    @Test
    public void get() throws Exception {
        new MockUp<RestfulProxy>() {

            @Mock
            public RestfulResponse get(String uri, RestfulParametes restParametes, RestfulOptions restOptions)
                    throws ServiceException {
                final RestfulResponse response = new RestfulResponse();
                response.setStatus(200);
                response.setResponseJson(restParametes.getRawData());
                return response;
            }
        };
        RestfulResponse response = RestUtil.get(servletRequest, "");
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), 200);

        response = RestUtil.get(servletRequest, "");
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), 200);
    }

    @Test(expected = ServiceException.class)
    public void get_error() throws Exception {
        final RestfulResponse response = RestUtil.get(null, "");
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), 200);
    }

    @Test
    public void getRestfulParametes() throws Exception {
        final RestfulParametes parametes = RestUtil.getRestfulParametes();
        Assert.assertNotNull(parametes);
    }

    @Test
    public void sendDeleteRequest() throws Exception {
        new MockUp<RestfulProxy>() {

            @Mock
            public RestfulResponse delete(String uri, RestfulParametes restParametes, RestfulOptions restOptions)
                    throws ServiceException {
                final RestfulResponse response = new RestfulResponse();
                response.setStatus(200);
                response.setResponseJson(restParametes.getRawData());
                return response;
            }
        };
        RestfulResponse response = RestUtil.sendDeleteRequest("", RestUtil.getRestfulParametes(), servletRequest);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), 200);
    }

    @Test
    public void sendGetRequest() throws Exception {
        new MockUp<RestfulProxy>() {

            @Mock
            public RestfulResponse get(String uri, RestfulParametes restParametes, RestfulOptions restOptions)
                    throws ServiceException {
                final RestfulResponse response = new RestfulResponse();
                response.setStatus(200);
                response.setResponseJson(restParametes.getRawData());
                return response;
            }
        };
        RestfulResponse response = RestUtil.sendGetRequest("", RestUtil.getRestfulParametes(), servletRequest);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), 200);
    }

    @Test
    public void sendPostRequest() throws Exception {
        new MockUp<RestfulProxy>() {

            @Mock
            public RestfulResponse post(String uri, RestfulParametes restParametes, RestfulOptions restOptions)
                    throws ServiceException {
                final RestfulResponse response = new RestfulResponse();
                response.setStatus(200);
                response.setResponseJson(restParametes.getRawData());
                return response;
            }
        };
        RestfulResponse response = RestUtil.sendPostRequest("", RestUtil.getRestfulParametes(), servletRequest, 3000);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), 200);
    }

    @Test
    public void sendPutRequest() throws Exception {
        new MockUp<RestfulProxy>() {

            @Mock
            public RestfulResponse put(String uri, RestfulParametes restParametes, RestfulOptions restOptions)
                    throws ServiceException {
                final RestfulResponse response = new RestfulResponse();
                response.setStatus(200);
                response.setResponseJson(restParametes.getRawData());
                return response;
            }
        };
        RestfulResponse response = RestUtil.sendPutRequest("", RestUtil.getRestfulParametes(), servletRequest);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), 200);
    }

}
