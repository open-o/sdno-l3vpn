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

import static org.openo.sdno.wanvpn.translator.uniformsbi.util.TranslatorUtil.s2nAdminStatus;
import static org.openo.sdno.wanvpn.translator.uniformsbi.util.TranslatorUtil.s2nTopologyType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.servicemodel.common.enumeration.TpType;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Ac;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Acs;
import org.openo.sdno.model.uniformsbi.l3vpn.L3LoopbackIf;
import org.openo.sdno.model.uniformsbi.l3vpn.L3LoopbackIfs;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Vpn;
import org.openo.sdno.wanvpn.translator.common.OperType;
import org.openo.sdno.wanvpn.translator.common.VpnContextKeys;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3AcTranslator;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3LoopbackIfTranslator;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3VpnTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The implement class of the uniformsbi L3Vpn translator.<br/>
 * 
 * @author
 * @version SDNO 0.5 Aug 1, 2016
 */
@Service("uniformL3VpnTranslatorImpl")
public class L3VpnTranslatorImpl implements L3VpnTranslator {

    private static final Logger LOGGER = LoggerFactory.getLogger(L3VpnTranslatorImpl.class);

    @Autowired
    private L3AcTranslator l3AcTranslator;

    @Autowired
    private L3LoopbackIfTranslator l3LoopbackIfTranslator;

    /**
     * @see org.openo.sdno.wanvpn.translator.uniformsbi.inf.ModelTranslator#translate(org.openo.sdno.cbb.wanvpn.translator.common.TranslatorCtx)
     */
    @Override
    public L3Vpn translate(TranslatorCtx ctx) throws ServiceException {
        if(Objects.equals(OperType.CREATE, ctx.getOperType())) {
            final Object val = ctx.getVal(VpnContextKeys.VPN);
            if(val instanceof Vpn) {
                return translateVpn(ctx, (Vpn)val);
            }
            LOGGER.error("invalid data type of key \"VPN\"");
        }
        return null;
    }

    private L3Vpn translateVpn(TranslatorCtx ctx, Vpn vpn) throws ServiceException {
        L3Vpn l3Vpn = new L3Vpn();
        translateId(vpn, l3Vpn);
        translateName(vpn, l3Vpn);
        translateTenantId(vpn, l3Vpn);
        translateDesc(vpn, l3Vpn);
        translateAdminStatus(vpn, l3Vpn);
        translateMtu(vpn, l3Vpn);
        translateTopology(vpn, l3Vpn);
        translateAcs(ctx, vpn, l3Vpn);
        translateLoopbackifs(ctx, vpn, l3Vpn);
        return l3Vpn;
    }

    private void translateLoopbackifs(TranslatorCtx ctx, Vpn vpn, L3Vpn l3Vpn) throws ServiceException {
        boolean existLoopbackIf = false;
        final List<Tp> tpList = vpn.getAccessPointList();
        if(CollectionUtils.isEmpty(tpList)) {
            return;
        }
        L3LoopbackIfs l3Loopbackifs = l3Vpn.getL3Loopbackifs();
        if(null == l3Loopbackifs) {
            l3Loopbackifs = new L3LoopbackIfs();
        }

        List<L3LoopbackIf> l3LoopbackList = l3Loopbackifs.getL3LoopbackIf();
        if(l3LoopbackList == null) {
            l3LoopbackList = new ArrayList<>();
            l3Loopbackifs.setL3LoopbackIf(l3LoopbackList);
        }

        for(final Tp tp : tpList) {
            ctx.addVal(VpnContextKeys.TP, tp);
            if(TpType.LOOPBACK.getCommonName().equals(tp.getType())) {
                final L3LoopbackIf loopbackIf = l3LoopbackIfTranslator.translate(ctx);
                l3LoopbackList.add(loopbackIf);
                existLoopbackIf = true;
            }
        }
        if(existLoopbackIf) {
            l3Vpn.setL3Loopbackifs(l3Loopbackifs);
        }
    }

    private void translateAcs(TranslatorCtx ctx, Vpn vpn, L3Vpn l3Vpn) throws ServiceException {
        final List<Tp> tpList = vpn.getAccessPointList();
        if(CollectionUtils.isEmpty(tpList)) {
            return;
        }
        L3Acs acs = new L3Acs();
        l3Vpn.setAcs(acs);
        List<L3Ac> l3AcList = l3Vpn.getAcs().getL3Ac();
        if(l3AcList == null) {
            l3AcList = new ArrayList<>();
            L3Acs l3Acs = new L3Acs();
            l3Acs.setL3Ac(l3AcList);
            l3Vpn.setAcs(l3Acs);
        }

        for(final Tp tp : tpList) {
            ctx.addVal(VpnContextKeys.TP, tp);
            if(!TpType.LOOPBACK.getCommonName().equals(tp.getType())) {
                final L3Ac ac = l3AcTranslator.translate(ctx);
                l3AcList.add(ac);
            }
        }

    }

    private void translateTopology(Vpn vpn, L3Vpn l3Vpn) {
        l3Vpn.setTopology(s2nTopologyType(vpn.getVpnBasicInfo().getTopology()).getCommonName());
    }

    private void translateMtu(final Vpn vpn, final L3Vpn l3Vpn) {
        l3Vpn.setIpMtu(vpn.getVpnBasicInfo().getIpMtu());
    }

    private void translateTenantId(Vpn vpn, L3Vpn l3Vpn) {
        l3Vpn.setTenantId(null);
    }

    private void translateAdminStatus(Vpn vpn, L3Vpn l3Vpn) {
        l3Vpn.setAdminStatus(s2nAdminStatus(vpn.getVpnBasicInfo().getAdminStatus()));
    }

    private void translateDesc(Vpn vpn, L3Vpn l3Vpn) {
        l3Vpn.setDescription(vpn.getDescription());
    }

    private void translateName(Vpn vpn, L3Vpn l3Vpn) {
        l3Vpn.setName(vpn.getName());
    }

    private void translateId(Vpn vpn, L3Vpn l3Vpn) {
        l3Vpn.setUuid(vpn.getId());
    }

    public void setL3AcTranslator(L3AcTranslator l3AcTranslator) {
        this.l3AcTranslator = l3AcTranslator;
    }
}
