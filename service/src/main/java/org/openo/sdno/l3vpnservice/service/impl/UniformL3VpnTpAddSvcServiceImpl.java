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

import org.apache.commons.lang.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.l3vpnservice.dao.L3VpnTpDao;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnQuerySvcService;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnSbiApiService;
import org.openo.sdno.l3vpnservice.service.inf.L3vpnTpAddSvcService;
import org.openo.sdno.l3vpnservice.service.util.ControllerUtils;
import org.openo.sdno.model.servicemodel.common.enumeration.AdminStatus;
import org.openo.sdno.model.servicemodel.common.enumeration.OperStatus;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Ac;
import org.openo.sdno.wanvpn.translator.common.VpnContextKeys;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtxFactory;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3AcTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Uniform L3VPN TP add service implement class.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-5-31
 */
@Service("uniformL3VpnTpAddSvcService")
public class UniformL3VpnTpAddSvcServiceImpl implements L3vpnTpAddSvcService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniformL3VpnTpAddSvcServiceImpl.class);

    @Autowired
    private L3VpnTpDao tpDao;

    @Autowired
    private L3AcTranslator l3AcTranslator;

    @Autowired
    @Qualifier("uniformL3VpnQuerySvcService")
    private L3VpnQuerySvcService l3VpnQuerySvcService;

    @Autowired
    private TranslatorCtxFactory translatorCtxFactory;

    @Autowired
    private L3VpnSbiApiService l3VpnSbiApiService;

    @Override
    public Tp addTp(Vpn vpn, Tp tp, @Context HttpServletRequest request) throws ServiceException {

        Tp tpNew = persistTp(vpn.getUuid(), tp);
        try {
            final TranslatorCtx translatorCtx = translatorCtxFactory.getTranslatorCtx(null);
            translatorCtx.addVal(VpnContextKeys.TP, tpNew);
            L3Ac ac = l3AcTranslator.translate(translatorCtx);
            String controllerUuid = ControllerUtils.getControllerUUID(vpn);

            l3VpnSbiApiService.createTp(ac, vpn.getUuid(), controllerUuid, request);
        } catch(ServiceException e) {
            tpDao.delMos(Collections.singletonList(tpNew));
            throw e;
        }

        try {
            upDateTpStatusAndName(vpn, request);
        } catch(ServiceException e) {
            LOGGER.error("error:", e);
        }

        return tpNew;
    }

    private Vpn upDateTpStatusAndName(Vpn vpn, @Context HttpServletRequest request) throws ServiceException {
        return l3VpnQuerySvcService.getStatus(vpn, request);
    }

    /**
     * Insert TP info to DB, and bind vpn info.<br/>
     */
    private Tp persistTp(String vpnId, Tp tp) throws ServiceException {

        if(StringUtils.isEmpty(tp.getId())) {
            tp.setId(UuidUtils.createUuid());
        }

        tp.setAdminStatus(AdminStatus.NOP.getCommonName());
        tp.setOperStatus(OperStatus.NOP.getCommonName());
        tp.setValue4Po("vpnId", vpnId);
        tpDao.addMos(Collections.singletonList(tp));
        return tp;

    }

    public void setTpDao(final L3VpnTpDao tpDao) {
        this.tpDao = tpDao;
    }

    public void setL3VpnQuerySvcService(L3VpnQuerySvcService l3VpnQuerySvcService) {
        this.l3VpnQuerySvcService = l3VpnQuerySvcService;
    }

    public void setL3AcTranslator(L3AcTranslator l3AcTranslator) {
        this.l3AcTranslator = l3AcTranslator;
    }

}
