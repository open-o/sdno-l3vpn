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

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.servicemodel.routeprotocol.StaticRouteTable;
import org.openo.sdno.model.uniformsbi.l3vpn.StaticRoute;
import org.openo.sdno.wanvpn.translator.common.VpnContextKeys;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3VpnStaticRouteTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * The implement class of the L3VPN static route translator.<br/>
 * 
 * @author
 * @version SDNO 0.5 Aug 1, 2016
 */
@Component("uniformL3VpnStaticRouteTranslatorImpl")
public class L3VpnStaticRouteTranslatorImpl implements L3VpnStaticRouteTranslator {

    private static final Logger LOGGER = LoggerFactory.getLogger(L3VpnStaticRouteTranslatorImpl.class);

    @Override
    public StaticRoute translate(TranslatorCtx ctx) throws ServiceException {
        final Object val = ctx.getVal(VpnContextKeys.STARICROUTE);
        if(val instanceof StaticRouteTable) {
            return translateStaticRoute(ctx, (StaticRouteTable)val);
        }
        LOGGER.error("invalid data type of key \"StaticRouteTable\"");
        return null;

    }

    private StaticRoute translateStaticRoute(TranslatorCtx ctx, StaticRouteTable staticRouteTable) {
        StaticRoute staticRoute = new StaticRoute();
        staticRoute.setIpPrefix(staticRouteTable.getDestinationCidr());
        staticRoute.setNextHop(staticRouteTable.getNextHopIp());
        return staticRoute;
    }

}
