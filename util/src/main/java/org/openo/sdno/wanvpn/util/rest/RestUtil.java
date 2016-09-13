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
import org.apache.commons.lang3.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulOptions;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.rest.ResponseUtils;
import org.openo.sdno.wanvpn.util.error.CommonErrorCode;
import org.openo.sdno.wanvpn.util.error.ServiceExceptionUtil;

/**
 * Restful util class.<br/>
 *
 * @author
 * @version SDNO 0.5 2016-6-1
 */
public class RestUtil {

    private static final String CONTENT_TYPE_NAME = "Content-Type";

    private static final String CONTENT_TYPE_VALUE = "application/json;charset=UTF-8";

    private static final int DEFAULT_TIMEOUT = 60000;

    private RestUtil() {
    }

    /**
     * Send Restful GET request.<br/>
     *
     * @param request HttpServlet request
     * @param url URL
     * @return Restful response
     * @throws ServiceException when operate failed
     * @since SDNO 0.5
     */
    public static RestfulResponse get(@Context HttpServletRequest request, final String url) throws ServiceException {
        return get(request, url, null);
    }

    /**
     * Send Restful GET request.<br/>
     *
     * @param request HttpServlet request
     * @param url URL
     * @param businessParams business parameters
     * @return Restful response
     * @throws ServiceException when operate failed
     * @since SDNO 0.5
     */
    private static RestfulResponse get(@Context HttpServletRequest request, final String url,
            final Map<String, Object> businessParams) throws ServiceException {
        if(request == null) {
            throw ServiceExceptionUtil.getBadRequestServiceException(CommonErrorCode.CHECK_HTTP_CONTEXT_IS_NULL);
        }

        final RestfulParametes restfulParametes = getRestfulParametes(request, businessParams);
        return sendGetRequest(url, restfulParametes, request);
    }

    /**
     * Get Restful parameters.<br/>
     *
     * @return Restful parameters
     * @since SDNO 0.5
     */
    public static RestfulParametes getRestfulParametes() {
        return getRestfulParametes(null);
    }

    /**
     * Get Restful parameters from input raw data.<br/>
     *
     * @param rawData raw data
     * @return Restful parameters
     * @since SDNO 0.5
     */
    public static RestfulParametes getRestfulParametes(final String rawData) {
        final RestfulParametes restfulParametes = new RestfulParametes();
        restfulParametes.putHttpContextHeader(CONTENT_TYPE_NAME, CONTENT_TYPE_VALUE);
        if(StringUtils.isNotBlank(rawData)) {
            restfulParametes.setRawData(rawData);
        }
        return restfulParametes;
    }

    private static <K, V> void mExtractParameters(final RestfulParametes dest, final Map<K, V> src) {
        if(MapUtils.isEmpty(src)) {
            return;
        }
        V value = null;
        for(final K key : src.keySet()) {
            if(null != dest.getParamMap().get(String.valueOf(key))) {
                continue;
            }

            value = src.get(key);
            if(value instanceof String) {

                dest.put(String.valueOf(key), String.valueOf(value));
            } else if(value instanceof String[]) {
                final String[] values = (String[])value;
                if(ArrayUtils.isEmpty(values)) {
                    return;
                }
                dest.put(String.valueOf(key), String.valueOf(values[0]));
            }
        }
    }

    private static RestfulParametes getRestfulParametes(@Context HttpServletRequest request,
            final Map<String, Object> businessParams) {
        final RestfulParametes restfulParametes = new RestfulParametes();
        restfulParametes.putHttpContextHeader(CONTENT_TYPE_NAME, CONTENT_TYPE_VALUE);
        mExtractParameters(restfulParametes, businessParams);

        if(null != request) {
            mExtractParameters(restfulParametes, request.getParameterMap());
        }
        return restfulParametes;
    }

    /**
     * Send Restful DELETE request. <br/>
     *
     * @param url URL
     * @param restfulParametes Restful parameters
     * @param request HttpServlet request
     * @return Restful response
     * @throws ServiceException when operate failed
     * @since SDNO 0.5
     */
    public static RestfulResponse sendDeleteRequest(final String url, final RestfulParametes restfulParametes,
            @Context HttpServletRequest request) throws ServiceException {
        return sendDeleteRequest(url, restfulParametes, request, DEFAULT_TIMEOUT);
    }

    /**
     * Send Restful DELETE request.<br/>
     *
     * @param url URL
     * @param restfulParametes Restful parameters
     * @param request HttpServlet request
     * @param timeout Timeout length
     * @return Restful response
     * @throws ServiceException when operate failed
     * @since SDNO 0.5
     */
    private static RestfulResponse sendDeleteRequest(final String url, final RestfulParametes restfulParametes,
            @Context HttpServletRequest request, final int timeout) throws ServiceException {
        if(null != request) {
            restfulParametes.putHttpContextHeader(CONTENT_TYPE_NAME, CONTENT_TYPE_VALUE);
            mExtractParameters(restfulParametes, request.getParameterMap());
        }
        final RestfulOptions restfulOptions = new RestfulOptions();
        restfulOptions.setRestTimeout(timeout);
        final RestfulResponse restfulResponse = RestfulProxy.delete(url, restfulParametes, restfulOptions);
        ResponseUtils.checkResonseAndThrowException(restfulResponse);
        return restfulResponse;
    }

    /**
     * Send Restful GET request.<br/>
     *
     * @param url URL
     * @param restfulParametes Restful parameters
     * @param request HttpServlet request
     * @return Restful response
     * @throws ServiceException when operate failed
     * @since SDNO 0.5
     */
    public static RestfulResponse sendGetRequest(final String url, final RestfulParametes restfulParametes,
            @Context HttpServletRequest request) throws ServiceException {
        if(null != request) {
            restfulParametes.putHttpContextHeader(CONTENT_TYPE_NAME, CONTENT_TYPE_VALUE);
            mExtractParameters(restfulParametes, request.getParameterMap());
        }

        final RestfulOptions restfulOptions = new RestfulOptions();
        restfulOptions.setRestTimeout(DEFAULT_TIMEOUT);
        final RestfulResponse restfulResponse = RestfulProxy.get(url, restfulParametes, restfulOptions);
        ResponseUtils.checkResonseAndThrowException(restfulResponse);
        return restfulResponse;
    }

    /**
     * Send Restful POST request.<br/>
     * 
     * @param url URL
     * @param restfulParametes Restful parameters
     * @return Restful response
     * @throws ServiceException when operate failed
     * @since SDNO 0.5
     */
    public static RestfulResponse sendPostRequest(final String url, final RestfulParametes restfulParametes)
            throws ServiceException {
        return sendPostRequest(url, restfulParametes, null);
    }

    /**
     * Send Restful POST request.<br/>
     * 
     * @param url URL
     * @param restfulParametes Restful parameters
     * @param request HttpServlet request
     * @return Restful response
     * @throws ServiceException when operate failed
     * @since SDNO 0.5
     */
    public static RestfulResponse sendPostRequest(final String url, final RestfulParametes restfulParametes,
            @Context HttpServletRequest request) throws ServiceException {
        return sendPostRequest(url, restfulParametes, request, DEFAULT_TIMEOUT);
    }

    /**
     * Send Restful POST request.<br/>
     * 
     * @param url URL
     * @param restfulParametes Restful parameters
     * @param request HttpServlet request
     * @param timeout Timeout length
     * @return Restful response
     * @throws ServiceException when operate failed
     * @since SDNO 0.5
     */
    public static RestfulResponse sendPostRequest(final String url, final RestfulParametes restfulParametes,
            @Context HttpServletRequest request, final int timeout) throws ServiceException {
        if(null != request) {
            restfulParametes.putHttpContextHeader(CONTENT_TYPE_NAME, CONTENT_TYPE_VALUE);
            mExtractParameters(restfulParametes, request.getParameterMap());
        }
        final RestfulOptions restfulOptions = new RestfulOptions();
        restfulOptions.setRestTimeout(timeout);

        final RestfulResponse restfulResponse = RestfulProxy.post(url, restfulParametes, restfulOptions);
        ResponseUtils.checkResonseAndThrowException(restfulResponse);
        return restfulResponse;
    }

    /**
     * Send Restful PUT request.<br/>
     *
     * @param url URL
     * @param restfulParametes Restful parameters
     * @param request HttpServlet request
     * @return Restful response
     * @throws ServiceException when operate failed
     * @since SDNO 0.5
     */
    public static RestfulResponse sendPutRequest(final String url, final RestfulParametes restfulParametes,
            @Context HttpServletRequest request) throws ServiceException {
        if(null != request) {
            restfulParametes.putHttpContextHeader(CONTENT_TYPE_NAME, CONTENT_TYPE_VALUE);
            mExtractParameters(restfulParametes, request.getParameterMap());
        }
        final RestfulOptions restfulOptions = new RestfulOptions();
        restfulOptions.setRestTimeout(DEFAULT_TIMEOUT);
        final RestfulResponse restfulResponse = RestfulProxy.put(url, restfulParametes, restfulOptions);
        ResponseUtils.checkResonseAndThrowException(restfulResponse);
        return restfulResponse;
    }
}
