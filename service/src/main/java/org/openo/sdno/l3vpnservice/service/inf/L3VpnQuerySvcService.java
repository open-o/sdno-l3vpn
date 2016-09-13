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

package org.openo.sdno.l3vpnservice.service.inf;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.VpnOperStatus;

/**
 * L3VPN query service interface.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-5-31
 */
public interface L3VpnQuerySvcService {

    /**
     * Query VPN status info from controller.<br>
     * 
     * @param vpn VPN info
     * @param request HttpServlet request
     * @return VPN info
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    Vpn getStatus(final Vpn vpn, @Context HttpServletRequest request) throws ServiceException;

    /**
     * Query VPN detail info from database.<br>
     * 
     * @param uuid UUID
     * @param request HttpServlet request
     * @return VPN info
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    Vpn getDetail(final String uuid, @Context HttpServletRequest request) throws ServiceException;

    /**
     * Query VPN's operation status.<br>
     * 
     * @param vpnId VPN ID
     * @param ctrluuid controller UUID
     * @param request HttpServlet request
     * @return VPN's operation status
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    VpnOperStatus getOperStatus(String vpnId, String ctrluuid, @Context HttpServletRequest request)
            throws ServiceException;

    /**
     * Query VPN's administrative status from controller.<br>
     * 
     * @param vpn VPN info
     * @param ctrluuid controller UUID
     * @param request HttpServlet request
     * @return VPN's administrative status
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    Vpn getL3vpnAdminStatus(Vpn vpn, String ctrluuid, @Context HttpServletRequest request) throws ServiceException;

}
