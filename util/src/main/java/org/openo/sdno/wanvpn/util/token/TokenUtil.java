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

package org.openo.sdno.wanvpn.util.token;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;

/**
 * The tools class of token.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-1
 */
public class TokenUtil {

    public static final String X_AUTH_TOKEN = "X-Auth-Token";

    private static final String TENANT_HEAD_NAME = "X-Tenant-Id";

    /**
     * The domain name.
     */
    private static final String X_DOMAIN_NAME = "X-Domain-Name";

    private TokenUtil() {
    }

    /**
     * Get token.<br/>
     * 
     * @param request HttpServlet request
     * @return The token
     * @since SDNO 0.5
     */
    public static String getToken(@Context HttpServletRequest request) {
        if(request == null) {
            return null;
        }

        String token = request.getHeader(TokenUtil.X_AUTH_TOKEN);
        if(StringUtils.isEmpty(token)) {
            return null;
        }
        return token;
    }

    /**
     * Fill token.<br/>
     * 
     * @param input Restful parameters
     * @param request HttpServlet request
     * @return Restful parameters that filled token
     * @since SDNO 0.5
     */
    public static RestfulParametes fillToken(final RestfulParametes input, @Context HttpServletRequest request) {
        if(null != request) {
            input.putHttpContextHeader(TokenUtil.X_AUTH_TOKEN, TokenUtil.getToken(request));
            input.putHttpContextHeader(TokenUtil.TENANT_HEAD_NAME, TokenUtil.getTenantId(request));
        }
        return input;
    }

    /**
     * Get tenant id.<br/>
     * 
     * @param request HttpServlet request
     * @return The tenant id
     * @since SDNO 0.5
     */
    public static String getTenantId(@Context HttpServletRequest request) {
        if(request == null) {
            return null;
        }

        String tenantID = request.getHeader(TokenUtil.TENANT_HEAD_NAME);

        if(StringUtils.isEmpty(tenantID)) {
            return null;
        }

        return tenantID;
    }

    /**
     * Get tenant name.<br/>
     * 
     * @param request HttpServlet request
     * @return The tenant name
     * @since SDNO 0.5
     */
    public static String getTenantName(@Context HttpServletRequest request) {
        if(request == null) {
            return null;
        }

        String tenantName = request.getHeader(TokenUtil.X_DOMAIN_NAME);

        if(StringUtils.isEmpty(tenantName)) {
            return null;
        }

        return tenantName;
    }

    /**
     * Check whether the tenant is ISP tenant.<br/>
     * 
     * @param tenantId The tenant id
     * @return true when the tenant is ISP tenant
     *         false when the tenant is not ISP tenant
     * @since SDNO 0.5
     */
    public static boolean isISPTenant(final String tenantId) throws ServiceException {
        return true;
    }
}
