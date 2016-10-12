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

package org.openo.sdno.wanvpn.translator.uniformsbi.impl;

import java.util.ArrayList;
import java.util.List;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.servicemodel.common.enumeration.RouteProtocolType;
import org.openo.sdno.model.servicemodel.routeprotocol.RouteProtocolSpec;
import org.openo.sdno.model.uniformsbi.l3vpn.Route;
import org.openo.sdno.model.uniformsbi.l3vpn.StaticRoute;
import org.openo.sdno.model.uniformsbi.l3vpn.StaticRoutes;
import org.openo.sdno.wanvpn.translator.common.VpnContextKeys;
import org.openo.sdno.wanvpn.translator.impl.TranslatorCtxImpl;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3VpnRouteTranslator;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3VpnStaticRouteTranslator;
import org.openo.sdno.wanvpn.util.EnumUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The implement class of the L3VPN route translator.<br>
 * 
 * @author
 * @version SDNO 0.5 August 1, 2016
 */
@Component("uniformL3VpnRouteTranslatorImpl")
public class L3VpnRouteTranslatorImpl implements L3VpnRouteTranslator {

    private static final Logger LOGGER = LoggerFactory.getLogger(L3VpnRouteTranslatorImpl.class);

    @Autowired
    private L3VpnStaticRouteTranslator l3VpnStaticRouteTranslator;

    @Override
    public Route translate(TranslatorCtx ctx) throws ServiceException {
        List<RouteProtocolSpec> routeProtocolSpecs = (List<RouteProtocolSpec>)ctx.getVal(VpnContextKeys.PROTOCOL);

        if(null == routeProtocolSpecs) {
            return null;
        }

        return translateRouteProtocol(ctx, routeProtocolSpecs);
    }

    private Route translateRouteProtocol(TranslatorCtx ctx, List<RouteProtocolSpec> routeProtocolSpecs)
            throws ServiceException {
        RouteProtocolType routeType = EnumUtil.valueOf(RouteProtocolType.class, routeProtocolSpecs.get(0).getType());
        switch(routeType) {
            case STATIC_ROUTING: {
                return new Route(translateStaticRoutes(routeProtocolSpecs));
            }
            default:

        }
        return new Route();
    }

    private StaticRoutes translateStaticRoutes(List<RouteProtocolSpec> routeProtocolSpecs) throws ServiceException {
        StaticRoutes staticRoutes = new StaticRoutes();
        List<StaticRoute> staticRouteLst = new ArrayList<StaticRoute>();
        staticRoutes.setStaticRoute(staticRouteLst);
        for(RouteProtocolSpec routeProtocolSpec : routeProtocolSpecs) {
            TranslatorCtx ctx = new TranslatorCtxImpl();
            ctx.addVal(VpnContextKeys.STARICROUTE, routeProtocolSpec.getStaticRoute());
            staticRouteLst.add(l3VpnStaticRouteTranslator.translate(ctx));
        }
        return staticRoutes;
    }

    public void setL3VpnStaticRouteTranslator(L3VpnStaticRouteTranslator l3VpnStaticRouteTranslator) {
        this.l3VpnStaticRouteTranslator = l3VpnStaticRouteTranslator;
    }
}
