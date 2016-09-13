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

import org.openo.baseservice.remoteservice.exception.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;

/**
 * L3Vpn activate service interface.<br/>
 * 
 * @author
 * @version     SDNO 0.5  Aug 2, 2016
 */
public interface L3VpnActiveSvcService {

    /**
     * Activate L3VPN.<br/>
     * 
     * @param vpn VPN info
     * @param request HttpServlet request
     * @return VPN info
     * @throws ServiceException when active failed
     * @since  SDNO 0.5
     */
    Vpn active(Vpn vpn, @Context HttpServletRequest request) throws ServiceException;

    /**
     * Deactivate L3VPN.<br/>
     * 
     * @param vpn VPN info
     * @param request HttpServlet request
     * @return VPN info
     * @throws ServiceException when deactivate failed
     * @since  SDNO 0.5
     */
    Vpn deactive(Vpn vpn, @Context HttpServletRequest request) throws ServiceException;

    /**
     * Activate site.<br/>
     * 
     * @param vpn  VPN info
     * @param tpId TP id
     * @param request HttpServlet request
     * @return TP info
     * @throws ServiceException when active failed
     * @since  SDNO 0.5
     */
    Tp activeSite(Vpn vpn, String tpId, @Context HttpServletRequest request) throws ServiceException;

    /**
     * Deactivate site.<br/>
     * 
     * @param vpn VPN info
     * @param tpId TP id
     * @param request HttpServlet request
     * @return TP info
     * @throws ServiceException when deactivate failed
     * @since  SDNO 0.5
     */
    Tp deactiveSite(Vpn vpn, String tpId, @Context HttpServletRequest request) throws ServiceException;
}
