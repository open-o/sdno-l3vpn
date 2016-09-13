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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulOptions;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.rest.ResponseUtils;

/**
 * Inventory SDK restful util class.<br>
 *
 * @author
 * @version SDNO 0.5 2016-6-1
 */
public class InventorySDKRestUtil {

    private static final String CONTENT_TYPE_NAME = "Content-Type";

    private static final String CONTENT_TYPE_VALUE = "application/json;charset=UTF-8";

    private static final String MSS_REQUEST_OWNER = "x-mss-request-owner";

    private static final int DEFAULT_TIMEOUT = 60000;

    private InventorySDKRestUtil() {
    }

    /**
     * Send restful GET request.<br>
     *
     * @param url URL
     * @param restfulParametes restful Parameters
     * @param request HttpServlet request
     * @param owner owner
     * @return Restful Response
     * @throws ServiceException when send failed
     * @since SDNO 0.5
     */
    public static RestfulResponse sendGetRequest(final String url, final RestfulParametes restfulParametes,
            @Context HttpServletRequest request, final String owner) throws ServiceException {
        restfulParametes.putHttpContextHeader(CONTENT_TYPE_NAME, CONTENT_TYPE_VALUE);
        if(null != owner) {
            restfulParametes.putHttpContextHeader(MSS_REQUEST_OWNER, owner);
        }
        final RestfulResponse restfulResponse = RestfulProxy.get(url, restfulParametes);
        ResponseUtils.checkResonseAndThrowException(restfulResponse);
        return restfulResponse;
    }

    /**
     * Send restful POST request.<br>
     *
     * @param url URL
     * @param restfulParametes restful Parameters
     * @param request HttpServlet request
     * @param owner owner
     * @return Restful Response
     * @throws ServiceException when send failed
     * @since SDNO 0.5
     */
    public static RestfulResponse sendPostRequest(final String url, final RestfulParametes restfulParametes,
            @Context HttpServletRequest request, final String owner) throws ServiceException {
        restfulParametes.putHttpContextHeader(CONTENT_TYPE_NAME, CONTENT_TYPE_VALUE);
        if(null != owner) {
            restfulParametes.putHttpContextHeader(MSS_REQUEST_OWNER, owner);
        }
        if(null != request) {
            mExtractParameters(restfulParametes, request.getParameterMap());
            InventorySDKRestUtil.mExtractParameters(restfulParametes, request.getParameterMap());
        }

        final RestfulOptions restfulOptions = new RestfulOptions();
        restfulOptions.setRestTimeout(DEFAULT_TIMEOUT);

        final RestfulResponse restfulResponse = RestfulProxy.post(url, restfulParametes, restfulOptions);
        ResponseUtils.checkResonseAndThrowException(restfulResponse);
        return restfulResponse;
    }

    /**
     * Send restful DELETE request.<br>
     *
     * @param url URL
     * @param restfulParametes restful Parameters
     * @param request HttpServlet request
     * @param owner owner
     * @return Restful Response
     * @throws ServiceException when send failed
     * @since SDNO 0.5
     */
    public static RestfulResponse sendDeleteRequest(final String url, final RestfulParametes restfulParametes,
            @Context HttpServletRequest request, final String owner) throws ServiceException {
        if(null != owner) {
            restfulParametes.putHttpContextHeader(MSS_REQUEST_OWNER, owner);
        }
        if(null != request) {
            restfulParametes.putHttpContextHeader(CONTENT_TYPE_NAME, CONTENT_TYPE_VALUE);
            mExtractParameters(restfulParametes, request.getParameterMap());
        }

        final RestfulResponse restfulResponse = RestfulProxy.delete(url, restfulParametes);
        ResponseUtils.checkResonseAndThrowException(restfulResponse);
        return restfulResponse;
    }

    /**
     * Send restful PUT request.<br>
     *
     * @param url URL
     * @param restfulParametes restful Parameters
     * @param request HttpServlet request
     * @param owner owner
     * @return Restful Response
     * @throws ServiceException when send failed
     * @since SDNO 0.5
     */
    public static RestfulResponse sendPutRequest(final String url, final RestfulParametes restfulParametes,
            @Context HttpServletRequest request, final String owner) throws ServiceException {
        if(null != owner) {
            restfulParametes.putHttpContextHeader(MSS_REQUEST_OWNER, owner);
        }
        if(null != request) {
            restfulParametes.putHttpContextHeader(CONTENT_TYPE_NAME, CONTENT_TYPE_VALUE);
            mExtractParameters(restfulParametes, request.getParameterMap());
        }

        final RestfulResponse restfulResponse = RestfulProxy.put(url, restfulParametes);
        ResponseUtils.checkResonseAndThrowException(restfulResponse);
        return restfulResponse;
    }

    private static <K, V> void mExtractParameters(final RestfulParametes dest, final Map<K, V> src) {
        if(MapUtils.isEmpty(src)) {
            return;
        }
        V v = null;
        for(final K key : src.keySet()) {
            if(null != dest.getParamMap().get(String.valueOf(key))) {
                continue;
            }

            v = src.get(key);
            if(v instanceof String) {

                dest.put(String.valueOf(key), String.valueOf(v));
            } else if(v instanceof String[]) {
                final String[] vs = (String[])v;
                if(ArrayUtils.isEmpty(vs)) {
                    return;
                }
                dest.put(String.valueOf(key), String.valueOf(vs[0]));
            }
        }
    }
}
