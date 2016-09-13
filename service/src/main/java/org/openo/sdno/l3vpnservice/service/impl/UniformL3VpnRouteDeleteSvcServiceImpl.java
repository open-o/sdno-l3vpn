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

package org.openo.sdno.l3vpnservice.service.impl;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.l3vpnservice.constant.L3VpnSvcErrorCode;
import org.openo.sdno.l3vpnservice.dao.L3VpnRouteProtocolSpecDao;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnSbiApiService;
import org.openo.sdno.l3vpnservice.service.inf.L3vpnRouteDeleteSvcService;
import org.openo.sdno.l3vpnservice.service.util.L3VpnServiceUtils;
import org.openo.sdno.model.servicemodel.common.enumeration.RouteProtocolType;
import org.openo.sdno.model.servicemodel.routeprotocol.RouteProtocolSpec;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.wanvpn.util.EnumUtil;
import org.openo.sdno.wanvpn.util.URLEncoderUtil;
import org.openo.sdno.wanvpn.util.error.ServiceExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Uniform L3VPN route delete service implement class.<br/>
 *
 * @author
 * @version SDNO 0.5 2016-5-31
 */
@Service("uniformL3VpnRouteDeleteSvcService")
public class UniformL3VpnRouteDeleteSvcServiceImpl implements L3vpnRouteDeleteSvcService {

    @Autowired
    private L3VpnRouteProtocolSpecDao routeDao;

    @Autowired
    private L3VpnSbiApiService l3VpnSbiApiService;

    @Override
    public RouteProtocolSpec deleteRoute(Vpn vpn, String tpUuid, String routeId, @Context HttpServletRequest request)
            throws ServiceException {

        if(null == vpn) {
            throw ServiceExceptionUtil.getServiceException(L3VpnSvcErrorCode.L3VPN_DELETE_TPNOTEXIST);
        }
        final Tp tempTp = L3VpnServiceUtils.getTpFromVpn(vpn, tpUuid);
        if(null == tempTp) {
            throw ServiceExceptionUtil.getServiceException(L3VpnSvcErrorCode.L3VPN_DELETE_TPNOTEXIST);
        }

        RouteProtocolSpec routeProtocolSpec = L3VpnServiceUtils.getRouteFromTp(tempTp, routeId);
        if(null == routeProtocolSpec) {
            // Up layer need ID to judge which operation failed.
            routeProtocolSpec = new RouteProtocolSpec();
            routeProtocolSpec.setUuid(routeId);
            return routeProtocolSpec;
        }
        mSendDeleteRequest(vpn, tpUuid, routeProtocolSpec, request);

        routeDao.delMos(Collections.singletonList(routeProtocolSpec));
        return routeProtocolSpec;

    }

    private void mSendDeleteRequest(Vpn tempVpn, String tpUuid, RouteProtocolSpec routeProtocolSpec,
            @Context HttpServletRequest request) throws ServiceException {
        RouteProtocolType type = EnumUtil.valueOf(RouteProtocolType.class, routeProtocolSpec.getType());
        String urlBody = null;
        switch(type) {
            case STATIC_ROUTING: {
                urlBody = URLEncoderUtil.encode(routeProtocolSpec.getStaticRoute().getDestinationCidr()) + ",";
                urlBody = urlBody + URLEncoderUtil.encode(routeProtocolSpec.getStaticRoute().getNextHopIp());
                l3VpnSbiApiService.deleteStaticRoute(tempVpn, tpUuid, urlBody, request);
                break;

            }
            default:

        }

    }

    public void setRouteDao(L3VpnRouteProtocolSpecDao routeDao) {
        this.routeDao = routeDao;
    }

}
