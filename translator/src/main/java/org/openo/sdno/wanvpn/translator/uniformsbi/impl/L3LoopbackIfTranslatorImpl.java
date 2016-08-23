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

package org.openo.sdno.wanvpn.translator.uniformsbi.impl;

import java.util.List;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.tp.TpTypeSpec;
import org.openo.sdno.model.uniformsbi.l3vpn.L3LoopbackIf;
import org.openo.sdno.wanvpn.translator.common.VpnContextKeys;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3LoopbackIfTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * The implement class of the L3 loopback if translator.<br/>
 * 
 * @author
 * @version SDNO 0.5 Aug 1, 2016
 */
@Service("uniformL3LoopbackIfTranslatorImpl")
public class L3LoopbackIfTranslatorImpl implements L3LoopbackIfTranslator {

    private static final Logger LOGGER = LoggerFactory.getLogger(L3LoopbackIfTranslatorImpl.class);

    @Override
    public L3LoopbackIf translate(TranslatorCtx ctx) throws ServiceException {
        final Object val = ctx.getVal(VpnContextKeys.TP);
        if(val instanceof Tp) {
            return translateL3Loopback(ctx, (Tp)val);
        }
        LOGGER.error("invalid data type of key \"TpPara\"");
        return null;
    }

    private L3LoopbackIf translateL3Loopback(TranslatorCtx ctx, Tp tp) {
        L3LoopbackIf l3LoopbackIf = new L3LoopbackIf();
        translateId(tp, l3LoopbackIf);
        translateAddress(tp, l3LoopbackIf);
        translateDesc(tp, l3LoopbackIf);
        return l3LoopbackIf;
    }

    private void translateDesc(Tp tp, L3LoopbackIf l3LoopbackIf) {
        l3LoopbackIf.setDescription(tp.getDescription());
    }

    private void translateAddress(Tp tp, L3LoopbackIf l3LoopbackIf) {
        List<TpTypeSpec> typeSpecLst = tp.getTypeSpecList();
        for(TpTypeSpec typeSpec : typeSpecLst) {
            if(null != typeSpec.getIpTpSpec()) {
                l3LoopbackIf.setIpv4Address(typeSpec.getIpTpSpec().getMasterIp());
                break;
            }
        }
    }

    private void translateId(Tp tp, L3LoopbackIf l3LoopbackIf) {
        l3LoopbackIf.setUuid(tp.getUuid());
        l3LoopbackIf.setNeId(tp.getNeId());
    }

}
