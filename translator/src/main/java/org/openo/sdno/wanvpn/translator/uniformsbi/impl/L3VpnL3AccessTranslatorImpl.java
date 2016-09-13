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
import org.openo.sdno.model.servicemodel.common.enumeration.LayerRate;
import org.openo.sdno.model.servicemodel.common.enumeration.RouteProtocolType;
import org.openo.sdno.model.servicemodel.routeprotocol.RouteProtocolSpec;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.tp.TpTypeSpec;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Access;
import org.openo.sdno.model.uniformsbi.l3vpn.Route;
import org.openo.sdno.model.uniformsbi.l3vpn.Routes;
import org.openo.sdno.wanvpn.translator.common.VpnContextKeys;
import org.openo.sdno.wanvpn.translator.impl.TranslatorCtxImpl;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3VpnL3AccessTranslator;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3VpnRouteTranslator;
import org.openo.sdno.wanvpn.util.EnumUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * The implement class of the L3 access translator.<br/>
 * 
 * @author
 * @version SDNO 0.5 Aug 1, 2016
 */
@Component("uniformL3VpnL3AccessTranslatorImpl")
public class L3VpnL3AccessTranslatorImpl implements L3VpnL3AccessTranslator {

    private static final Logger LOGGER = LoggerFactory.getLogger(L3VpnL3AccessTranslatorImpl.class);

    @Autowired
    private L3VpnRouteTranslator l3VpnRouteTranslator;

    @Override
    public L3Access translate(TranslatorCtx ctx) throws ServiceException {

        final Object tp = ctx.getVal(VpnContextKeys.TP);
        if(tp instanceof Tp) {
            return translateL3Access(ctx, (Tp)tp);
        }
        LOGGER.error("invalid data type of key \"Tp\"");
        return null;
    }

    private L3Access translateL3Access(TranslatorCtx ctx, Tp tp) throws ServiceException {
        L3Access l3Access = new L3Access();
        Routes routes = new Routes();
        List<Route> routeLst = new ArrayList<Route>();
        routes.setRoute(routeLst);
        l3Access.setRoutes(routes);

        List<TpTypeSpec> typeSpecLst = tp.getTypeSpecList();
        for(TpTypeSpec typeSpec : typeSpecLst) {
            if(null != typeSpec.getLayerRate() && typeSpec.getLayerRate().equals(LayerRate.LR_IP.getCommonName())
                    && null != typeSpec.getIpTpSpec()) {
                l3Access.setIpv4Address(typeSpec.getIpTpSpec().getMasterIp());
            }
        }

        List<RouteProtocolSpec> routeProtocolLst = tp.getRouteProtocolSpecs();
        if(CollectionUtils.isEmpty(routeProtocolLst)) {
            return null;
        }

        List<RouteProtocolSpec> staticRouteLst = new ArrayList<RouteProtocolSpec>();
        List<RouteProtocolSpec> bgpRouteLst = new ArrayList<RouteProtocolSpec>();
        for(RouteProtocolSpec routeProtocol : routeProtocolLst) {
            RouteProtocolType routeType = EnumUtil.valueOf(RouteProtocolType.class, routeProtocol.getType());
            switch(routeType) {
                case STATIC_ROUTING: {
                    staticRouteLst.add(routeProtocol);
                    break;

                }
                case BGP: {
                    bgpRouteLst.add(routeProtocol);
                    break;
                }
                default:
            }
        }

        if(!staticRouteLst.isEmpty()) {
            TranslatorCtx ctxRoute = new TranslatorCtxImpl();
            ctxRoute.addVal(VpnContextKeys.PROTOCOL, staticRouteLst);
            routeLst.add(l3VpnRouteTranslator.translate(ctxRoute));
        }
        if(!bgpRouteLst.isEmpty()) {
            TranslatorCtx ctxRoute = new TranslatorCtxImpl();
            ctxRoute.addVal(VpnContextKeys.PROTOCOL, bgpRouteLst);
            routeLst.add(l3VpnRouteTranslator.translate(ctxRoute));
        }

        return l3Access;
    }

    public void setL3VpnRouteTranslator(L3VpnRouteTranslator l3VpnRouteTranslator) {
        this.l3VpnRouteTranslator = l3VpnRouteTranslator;
    }

}
