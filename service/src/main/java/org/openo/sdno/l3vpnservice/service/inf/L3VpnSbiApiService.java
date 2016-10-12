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
import org.openo.sdno.model.uniformsbi.l3vpn.BgpRoute;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Ac;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Vpn;
import org.openo.sdno.model.uniformsbi.l3vpn.StaticRoute;

/**
 * L3Vpn SBI interface class.<br>
 * 
 * @author
 * @version     SDNO 0.5  August 2, 2016
 */
public interface L3VpnSbiApiService {

    /**
     * 
     * Create L3VPN.<br>
     * 
     * @param l3Vpn L3VPN info
     * @param controllerUuid Controller UUID
     * @param request HttpServlet request
     * @return L3VPN info
     * @throws ServiceException when create failed
     * @since  SDNO 0.5
     */
    L3Vpn createL3VPN(L3Vpn l3Vpn, String controllerUuid, @Context HttpServletRequest request) throws ServiceException;

    /**
     * Deploy L3VPN status.<br>
     * 
     * @param l3Vpn L3VPN info
     * @param controllerUuid Controller UUID
     * @param request HttpServlet request
     * @throws ServiceException when deploy failed
     * @since  SDNO 0.5
     */
    void deployVpnStatus(final L3Vpn l3Vpn, final String controllerUuid, @Context HttpServletRequest request)
            throws ServiceException;

    /**
     * Deploy TP status.<br>
     * 
     * @param vpnId L3VPN id
     * @param l3ac L3Ac info
     * @param ctrlUuid Controller UUID
     * @param request HttpServlet request
     * @throws ServiceException when deploy failed
     * @since  SDNO 0.5
     */
    void deployTpStatus(final String vpnId, final L3Ac l3ac, String ctrlUuid, @Context HttpServletRequest request)
            throws ServiceException;

    /**
     * Delete L3VPN.<br>
     * 
     * @param vpnId L3VPN id
     * @param controllerUuid Controller UUID
     * @param request HttpServlet request
     * @throws ServiceException when delete failed
     * @since  SDNO 0.5
     */
    void deleteVpn(String vpnId, String controllerUuid, @Context HttpServletRequest request) throws ServiceException;

    /**
     * Modify L3VPN description.<br>
     * 
     * @param ctrlUuid Controller UUID
     * @param l3Vpn L3VPN
     * @param request HttpServlet request
     * @throws ServiceException when modify L3VPN failed
     * @since  SDNO 0.5
     */
    void modifyVpnDescrition(final String ctrlUuid, final L3Vpn l3Vpn, @Context HttpServletRequest request)
            throws ServiceException;

    /**
     * Get detail info of L3VPN.<br>
     * 
     * @param uuid L3VPN UUID
     * @param ctrluuid Controller UUID
     * @param request HttpServlet request
     * @return L3VPN info
     * @throws ServiceException when get L3VPN info failed
     * @since  SDNO 0.5
     */
    L3Vpn getL3vpnDetail(final String uuid, final String ctrluuid, @Context HttpServletRequest request)
            throws ServiceException;

    /**
     * Create TP.<br>
     * 
     * @param ac L3Ac info
     * @param l3VpnId L3VPN id
     * @param ctrlUuid Controller UUID
     * @param request HttpServlet request
     * @return L3Ac info
     * @throws ServiceException when create TP failed
     * @since  SDNO 0.5
     */
    L3Ac createTp(L3Ac ac, String l3VpnId, String ctrlUuid, @Context HttpServletRequest request)
            throws ServiceException;

    /**
     * Delete TP.<br>
     * 
     * @param l3vpnUuid L3VPN UUID
     * @param tpUuid TP UUID
     * @param ctrlUuid Controller UUID
     * @param request HttpServlet request
     * @throws ServiceException when delete TP failed
     * @since  SDNO 0.5
     */
    void deleteTp(String l3vpnUuid, String tpUuid, String ctrlUuid, @Context HttpServletRequest request)
            throws ServiceException;

    /**
     * Create static route.<br>
     * 
     * @param staticRoute Static route info
     * @param l3vpnUuid L3VPN UUID
     * @param tpUuid TP UUID
     * @param ctrlUuid Controller UUID
     * @param request HttpServlet request
     * @throws ServiceException when create failed
     * @since  SDNO 0.5
     */
    void createStaticRoute(StaticRoute staticRoute, String l3vpnUuid, String tpUuid, String ctrlUuid,
            @Context HttpServletRequest request) throws ServiceException;

    /**
     * Create BGP route.<br>
     * 
     * @param bgpRoute BGP route info
     * @param l3vpnUuid L3VPN UUID
     * @param tpUuid TP UUID
     * @param ctrlUuid Controller UUID
     * @param request HttpServlet request
     * @throws ServiceException when create failed
     * @since  SDNO 0.5
     */
    void createBgpRoute(BgpRoute bgpRoute, String l3vpnUuid, String tpUuid, String ctrlUuid,
            @Context HttpServletRequest request) throws ServiceException;

    /**
     * Delete static route.<br>
     * 
     * @param tempVpn VPN info
     * @param tpUuid TP UUID
     * @param urlBody URL body
     * @param request HttpServlet request
     * @throws ServiceException when delete failed
     * @since  SDNO 0.5
     */
    void deleteStaticRoute(Vpn tempVpn, String tpUuid, String urlBody, @Context HttpServletRequest request)
            throws ServiceException;

    /**
     * Delete BGP route.<br>
     * 
     * @param tempVpn VPN info
     * @param tpUuid TP UUID
     * @param urlBody URL body
     * @param request HttpServlet request
     * @throws ServiceException when delete failed
     * @since  SDNO 0.5
     */
    void deleteBgpRoute(Vpn tempVpn, String tpUuid, String urlBody, @Context HttpServletRequest request)
            throws ServiceException;

}
