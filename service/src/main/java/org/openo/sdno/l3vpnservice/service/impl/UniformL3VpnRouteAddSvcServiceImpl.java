/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.l3vpnservice.dao.L3VpnRouteProtocolSpecDao;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnSbiApiService;
import org.openo.sdno.l3vpnservice.service.inf.L3vpnRouteAddSvcService;
import org.openo.sdno.l3vpnservice.service.util.ControllerUtils;
import org.openo.sdno.model.servicemodel.common.enumeration.RouteProtocolType;
import org.openo.sdno.model.servicemodel.routeprotocol.RouteProtocolSpec;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.uniformsbi.l3vpn.Route;
import org.openo.sdno.wanvpn.translator.common.VpnContextKeys;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtxFactory;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3VpnRouteTranslator;
import org.openo.sdno.wanvpn.util.EnumUtil;
import org.openo.sdno.wanvpn.util.constans.TpConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Uniform L3VPN route add service implement class.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-5-31
 */
@Service("uniformL3VpnRouteAddSvcService")
public class UniformL3VpnRouteAddSvcServiceImpl implements L3vpnRouteAddSvcService {

    @Autowired
    L3VpnRouteProtocolSpecDao l3VpnRouteProDao;

    @Autowired
    private L3VpnRouteTranslator l3VpnRouteTranslator;

    @Autowired
    private TranslatorCtxFactory translatorCtxFactory;

    @Autowired
    private L3VpnSbiApiService l3VpnSbiApiService;

    @Override
    public RouteProtocolSpec addRouteProtocol(Vpn vpn, String tpUuid, RouteProtocolSpec routeProtocolSpec,
            @Context HttpServletRequest request) throws ServiceException {
        // Insert protocol info to DB.
        RouteProtocolSpec routeNew = persistRoute(tpUuid, routeProtocolSpec);
        // Send to controller.
        try {
            mSendPostRequest(routeNew, vpn, tpUuid, request);
        } catch(ServiceException e) {
            // Delete info in DB when failed.
            l3VpnRouteProDao.delMos(Collections.singletonList(routeProtocolSpec));
            throw e;
        }
        return routeProtocolSpec;
    }

    private Route mSendPostRequest(RouteProtocolSpec routeNew, Vpn vpn, String tpUuid,
            @Context HttpServletRequest request) throws ServiceException {
        final TranslatorCtx translatorCtx = translatorCtxFactory.getTranslatorCtx(null);
        translatorCtx.addVal(VpnContextKeys.PROTOCOL, Collections.singletonList(routeNew));
        Route route = l3VpnRouteTranslator.translate(translatorCtx);

        if(route == null) {
            return route;
        }
        String ctrlUuid = ControllerUtils.getControllerUUID(vpn);
        String l3vpnUuid = vpn.getId();

        RouteProtocolType type = EnumUtil.valueOf(RouteProtocolType.class, routeNew.getType());
        switch(type) {
            case STATIC_ROUTING: {
                if(route.getStaticRoutes() == null || route.getStaticRoutes().getStaticRoute().isEmpty()) {
                    return route;
                }

                l3VpnSbiApiService.createStaticRoute(route.getStaticRoutes().getStaticRoute().get(0), l3vpnUuid, tpUuid,
                        ctrlUuid, request);
                break;
            }
            case BGP: {
                if(route.getBgpRoutes() == null || route.getBgpRoutes().getBgpRoute().isEmpty()) {
                    return route;
                }
                l3VpnSbiApiService.createBgpRoute(route.getBgpRoutes().getBgpRoute().get(0), l3vpnUuid, tpUuid,
                        ctrlUuid, request);
                break;

            }
            default:

        }

        return route;
    }

    private RouteProtocolSpec persistRoute(String tpUuid, RouteProtocolSpec routeProtocolSpec) throws ServiceException {
        routeProtocolSpec.setUuid(UuidUtils.createUuid());
        routeProtocolSpec.setValue4Po(TpConstants.TP_ID, tpUuid);
        l3VpnRouteProDao.addMos(Collections.singletonList(routeProtocolSpec));
        return routeProtocolSpec;
    }

    public void setL3VpnRouteProDao(L3VpnRouteProtocolSpecDao l3VpnRouteProDao) {
        this.l3VpnRouteProDao = l3VpnRouteProDao;
    }

    public void setL3VpnRouteTranslator(L3VpnRouteTranslator l3VpnRouteTranslator) {
        this.l3VpnRouteTranslator = l3VpnRouteTranslator;
    }

}
