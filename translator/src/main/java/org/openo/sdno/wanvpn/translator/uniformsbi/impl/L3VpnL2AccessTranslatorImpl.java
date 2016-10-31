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

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.servicemodel.tp.EthernetTpSpec;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.tp.TpTypeSpec;
import org.openo.sdno.model.uniformsbi.l3vpn.L2Access;
import org.openo.sdno.wanvpn.translator.common.VpnContextKeys;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3VpnL2AccessTranslator;
import org.openo.sdno.wanvpn.translator.uniformsbi.util.TranslatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * The implement class of the L2 access translator.<br>
 * 
 * @author
 * @version SDNO 0.5 August 1, 2016
 */
@Service("uniformL3VpnL2AccessTranslatorImpl")
public class L3VpnL2AccessTranslatorImpl implements L3VpnL2AccessTranslator {

    private static final Logger LOGGER = LoggerFactory.getLogger(L3VpnL2AccessTranslatorImpl.class);

    /**
     * @see org.openo.sdno.wanvpn.translator.uniformsbi.inf.ModelTranslator#translate(TranslatorCtx)
     */
    @Override
    public L2Access translate(TranslatorCtx ctx) throws ServiceException {
        final Object tp = ctx.getVal(VpnContextKeys.TP);
        if(tp instanceof Tp) {
            return translateL2Access(ctx, (Tp) tp);
        }
        LOGGER.error("invalid data type of key \"Tp\"");
        return null;
    }

    private L2Access translateL2Access(TranslatorCtx ctx, Tp tp) {
        L2Access l2Access = new L2Access();
        List<TpTypeSpec> typeSpecLst = tp.getTypeSpecList();
        if(!CollectionUtils.isEmpty(typeSpecLst)) {
            TpTypeSpec tpTypeSpec = typeSpecLst.get(0);
            EthernetTpSpec ethernetTpSpec = tpTypeSpec.getEthernetTpSpec();
            if(null != ethernetTpSpec) {
                l2Access.setDot1qVlanBitmap(Integer.parseInt(ethernetTpSpec.getDot1qVlanList()));
                l2Access.setQinqCvlanBitmap(ethernetTpSpec.getQinqCvlanList());
                l2Access.setQinqSvlanBitmap(ethernetTpSpec.getQinqSvlanList());
                l2Access.setL2AccessType(TranslatorUtil.s2nL2AccessType(ethernetTpSpec.getAccessType()).getName());
            }
        }
        return l2Access;
    }

}
