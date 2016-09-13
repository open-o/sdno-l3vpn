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
import org.openo.sdno.framework.container.service.IService;
import org.openo.sdno.model.common.query.BatchQueryResult;
import org.openo.sdno.model.servicemodel.routeprotocol.RouteProtocolSpec;
import org.openo.sdno.model.servicemodel.tepath.TePath;
import org.openo.sdno.model.servicemodel.tepath.TePathQueryKey;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.servicemodel.vpn.VpnVo;

/**
 * L3VPN service interface.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-5-31
 */
public interface L3VpnSvcService extends IService {

    /**
     * Create L3VPN.<br>
     * 
     * @param vpnVo VPN info
     * @param request HttpServlet request
     * @return VPN info
     * @throws ServiceException when create failed
     * @since SDNO 0.5
     */
    Vpn create(final VpnVo vpnVo, @Context HttpServletRequest request) throws ServiceException;

    /**
     * Delete L3VPN.<br>
     * 
     * @param vpn VPN info
     * @param request HttpServlet request
     * @return VPN info
     * @throws ServiceException when delete failed
     * @since SDNO 0.5
     */
    Vpn delete(final Vpn vpn, @Context HttpServletRequest request) throws ServiceException;

    /**
     * Activate L3VPN.<br>
     * 
     * @param vpn VPN info
     * @param request HttpServlet request
     * @return VPN info
     * @throws ServiceException when active failed
     * @since SDNO 0.5
     */
    Vpn active(Vpn vpn, @Context HttpServletRequest request) throws ServiceException;

    /**
     * Deactivate L3VPN.<br>
     * 
     * @param vpn VPN info
     * @param request HttpServlet request
     * @return VPN info
     * @throws ServiceException when deactivate failed
     * @since SDNO 0.5
     */
    Vpn deactive(Vpn vpn, @Context HttpServletRequest request) throws ServiceException;

    /**
     * Update VPN description info in database.<br>
     * 
     * @param vpn VPN info
     * @param request HttpServlet request
     * @return VPN info
     * @throws ServiceException when modify failed
     * @since SDNO 0.5
     */
    Vpn modifyDesc(final Vpn vpn, @Context HttpServletRequest request) throws ServiceException;

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
     * Query VPN status info from controller.<br>
     * 
     * @param vpn VPN info
     * @param request HttpServlet request
     * @return VPN info
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    Vpn getStatus(Vpn vpn, @Context HttpServletRequest request) throws ServiceException;

    /**
     * Add TP.<br>
     * 
     * @param vpn VPN info
     * @param tp TP info
     * @param request HttpServlet request
     * @return TP info
     * @throws ServiceException when add failed
     * @since SDNO 0.5
     */
    Tp addTp(Vpn vpn, Tp tp, @Context HttpServletRequest request) throws ServiceException;

    /**
     * Delete TP.<br>
     * 
     * @param vpn VPN
     * @param tpId TP UUID
     * @param request HttpServlet request
     * @return TP info
     * @throws ServiceException when delete failed
     * @since SDNO 0.5
     */
    Tp deleteTp(Vpn vpn, String tpId, @Context HttpServletRequest request) throws ServiceException;

    /**
     * Add route protocol.<br>
     * 
     * @param vpn VPN info
     * @param tpUuid TP UUID
     * @param routeProtocolSpec route protocol info
     * @param request HttpServlet request
     * @return route protocol info
     * @throws ServiceException when add failed
     * @since SDNO 0.5
     */
    RouteProtocolSpec addRoute(Vpn vpn, String tpUuid, RouteProtocolSpec routeProtocolSpec,
            @Context HttpServletRequest request) throws ServiceException;

    /**
     * Delete route protocol.<br>
     * 
     * @param vpn VPN
     * @param tpUuid TP UUID
     * @param routeId route ID
     * @param request HttpServlet request
     * @return route protocol info
     * @throws ServiceException when delete failed
     * @since SDNO 0.5
     */
    RouteProtocolSpec deleteRoute(Vpn vpn, String tpUuid, String routeId, @Context HttpServletRequest request)
            throws ServiceException;

    /**
     * Activate site.<br>
     * 
     * @param vpn VPN info
     * @param tpId TP id
     * @param request HttpServlet request
     * @return TP info
     * @throws ServiceException when active failed
     * @since SDNO 0.5
     */
    Tp activeSite(Vpn vpn, String tpId, @Context HttpServletRequest request) throws ServiceException;

    /**
     * Deactivate site.<br>
     * 
     * @param vpn VPN info
     * @param tpId TP id
     * @param request HttpServlet request
     * @return TP info
     * @throws ServiceException when deactivate failed
     * @since SDNO 0.5
     */
    Tp deactiveSite(Vpn vpn, String tpId, @Context HttpServletRequest request) throws ServiceException;

    /**
     * Query TE path.<br>
     * 
     * @param vpn VPN info
     * @param queryKey query key
     * @param request HttpServlet request
     * @return TE path
     * @throws ServiceException when query TE path failed
     * @since SDNO 0.5
     */
    BatchQueryResult<TePath> getTePath(Vpn vpn, final TePathQueryKey queryKey, @Context HttpServletRequest request)
            throws ServiceException;

}
