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
import org.openo.sdno.model.servicemodel.routeprotocol.BgpProtocolItem;
import org.openo.sdno.model.uniformsbi.l3vpn.BgpRoute;
import org.openo.sdno.wanvpn.translator.common.VpnContextKeys;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3VpnBgpRouteTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("uniformL3VpnBgpRouteTranslatorImpl")
public class L3VpnBgpRouteTranslatorImpl implements L3VpnBgpRouteTranslator {

    protected static final Logger LOGGER = LoggerFactory.getLogger(L3VpnBgpRouteTranslatorImpl.class);

    @Override
    public BgpRoute translate(TranslatorCtx ctx) throws ServiceException {

        final Object val = ctx.getVal(VpnContextKeys.BGPROUTE);
        if(val instanceof BgpProtocolItem) {
            return translateBgpRoute(ctx, ((BgpProtocolItem)val));
        }
        LOGGER.error("invalid data type of key \"BgpProtocolPara\"");
        return null;

    }

    private BgpRoute translateBgpRoute(TranslatorCtx ctx, BgpProtocolItem bgpProtocolItem) {
        BgpRoute bgpRoute = new BgpRoute();
        bgpRoute.setPeerIp(bgpProtocolItem.getPeerIp());
        bgpRoute.setRemoteAs("" + bgpProtocolItem.getPeerAsNumber());
        bgpRoute.setPassword(bgpProtocolItem.getPassword());
        return bgpRoute;
    }

}
