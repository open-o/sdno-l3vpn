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

import org.openo.sdno.model.servicemodel.routeprotocol.RouteProtocolSpec;
import org.openo.sdno.model.servicemodel.vpn.Vpn;

/**
 * L3VPN route add service interface.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-5-31
 */
public interface L3vpnRouteAddSvcService {

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
    RouteProtocolSpec addRouteProtocol(Vpn vpn, String tpUuid, RouteProtocolSpec routeProtocolSpec,
            @Context HttpServletRequest request) throws ServiceException;

}
