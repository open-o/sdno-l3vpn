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

import org.apache.commons.lang3.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.AdminStatus;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Ac;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3AcTranslator;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3VpnL2AccessTranslator;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3VpnL3AccessTranslator;
import org.openo.sdno.wanvpn.translator.uniformsbi.util.TranslatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The implement class of the L3 AC translator.<br>
 * 
 * @author
 * @version SDNO 0.5 Aug 1, 2016
 */
@Service("uniformL3AcTranslatorImpl")
public class L3AcTranslatorImpl implements L3AcTranslator {

    @Autowired
    private L3VpnL3AccessTranslator l3VpnL3AccessTranslator;

    @Autowired
    private L3VpnL2AccessTranslator l3VpnL2AccessTranslator;

    private static final Logger LOGGER = LoggerFactory.getLogger(L3AcTranslatorImpl.class);

    @Override
    public L3Ac translate(TranslatorCtx ctx) throws ServiceException {

        final Object srcVal = ctx.getVal("srcTp");
        if(srcVal instanceof Tp) {
            return translateL3Ac(ctx, (Tp)srcVal);
        }
        LOGGER.error("invalid data type of key \"TpPara\"");
        return null;

    }

    private L3Ac translateL3Ac(TranslatorCtx ctx, Tp tp) throws ServiceException {
        L3Ac l3Ac = new L3Ac();
        this.translateId(tp, l3Ac);
        this.translateAdminStatus(tp, l3Ac);
        this.translateDesc(tp, l3Ac);
        this.translateName(tp, l3Ac);
        this.translateNeId(tp, l3Ac);
        this.translateLtpId(tp, l3Ac);
        l3Ac.setL3Access(l3VpnL3AccessTranslator.translate(ctx));
        l3Ac.setL2Access(l3VpnL2AccessTranslator.translate(ctx));
        this.translateQosPolicyId(tp, l3Ac);

        return l3Ac;
    }

    private void translateLtpId(Tp tp, L3Ac l3Ac) {
        l3Ac.setLtpId(tp.getContainedMainTP());
    }

    private void translateId(final Tp tp, final L3Ac l3Ac) {
        String uuid = tp.getUuid();
        if(StringUtils.isBlank(uuid)) {
            uuid = UuidUtils.createUuid();
        }

        l3Ac.setUuid(uuid);
    }

    private void translateAdminStatus(final Tp tp, final L3Ac l3Ac) {
        final AdminStatus adminStatus = TranslatorUtil.s2nAdminStatus(tp.getAdminStatus());
        l3Ac.setAdminStatus(adminStatus);
    }

    private void translateDesc(final Tp tp, final L3Ac l3Ac) {
        l3Ac.setDescription(tp.getDescription());
    }

    private void translateName(final Tp tp, final L3Ac l3Ac) {
        l3Ac.setName(tp.getName());
    }

    private void translateNeId(final Tp tp, final L3Ac l3Ac) {
        l3Ac.setNeId(tp.getNeId());
    }

    private void translateQosPolicyId(final Tp tp, final L3Ac l3Ac) {
        String inboundQosPolicyId = tp.getInboundQosPolicyId();
        String outboundQosPolicyId = tp.getOutboundQosPolicyId();
        l3Ac.setInboundQosPolicyId(inboundQosPolicyId);
        l3Ac.setOutboundQosPolicyId(outboundQosPolicyId);
    }
}
