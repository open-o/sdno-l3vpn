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

package org.openo.sdno.l3vpnservice.service.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.l3vpnservice.constant.L3VpnSvcErrorCode;
import org.openo.sdno.l3vpnservice.dao.L3VpnDao;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnCreateSvcService;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnQuerySvcService;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnSbiApiService;
import org.openo.sdno.l3vpnservice.service.util.ControllerUtils;
import org.openo.sdno.model.servicemodel.common.enumeration.OperStatus;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.servicemodel.vpn.VpnVo;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Vpn;
import org.openo.sdno.wanvpn.translator.common.OperType;
import org.openo.sdno.wanvpn.translator.common.VpnContextKeys;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtxFactory;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3VpnTranslator;
import org.openo.sdno.wanvpn.util.error.ServiceExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * Uniform L3VPN create service implement class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-5-31
 */
@Service("uniformL3VpnCreateSvcService")
public class UniformL3VpnCreateSvcServiceImpl implements L3VpnCreateSvcService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniformL3VpnCreateSvcServiceImpl.class);

    @Autowired
    private L3VpnSbiApiService l3VpnSbiApiService;

    @Autowired
    private L3VpnTranslator l3VpnTranslator;

    @Resource
    private L3VpnDao vpnDao;

    @Autowired
    @Qualifier("uniformL3VpnQuerySvcService")
    private L3VpnQuerySvcService l3VpnQuerySvcService;

    @Autowired
    private TranslatorCtxFactory translatorCtxFactory;

    @Override
    public Vpn create(VpnVo vpnVo, @Context HttpServletRequest request) throws ServiceException {
        final TranslatorCtx translatorCtx = translatorCtxFactory.getTranslatorCtx(OperType.CREATE);
        setTunnelSchemaToCtx(vpnVo, translatorCtx);
        setVPNParamToCtx(vpnVo, translatorCtx);
        final Vpn vpn = vpnVo.getVpn();
        // Check whether name is unique
        this.checkVpnNameUnique(vpn);
        this.setTpAdminStatus(vpn);

        final L3Vpn l3Vpn = l3VpnTranslator.translate(translatorCtx);
        final String controllerUuid = ControllerUtils.getControllerUUID(vpnVo);

        l3VpnSbiApiService.createL3VPN(l3Vpn, controllerUuid, request);

        this.vpnDao.addMos(Collections.singletonList(vpn));

        try {
            l3VpnQuerySvcService.getStatus(vpn, request);
        } catch(ServiceException e) {
            LOGGER.error("Query vpn failed after sucessfully creating vpn. Set Vpn Operstatus to Nop. Err:", e);
            vpn.setOperStatus(OperStatus.NOP.getCommonName());
        }

        return vpn;
    }

    private void checkVpnNameUnique(final Vpn vpn) throws ServiceException {
        if(vpnDao.isVpnNameExisted(vpn.getName())) {
            throw ServiceExceptionUtil.getServiceException(L3VpnSvcErrorCode.L3VPN_NAME_DUPLICATE);
        }
    }

    private void setTpAdminStatus(Vpn vpn) {
        final String adminStatus = vpn.getVpnBasicInfo().getAdminStatus();
        final List<Tp> tpList = vpn.getAccessPointList();
        if(!CollectionUtils.isEmpty(tpList)) {
            for(Tp tp : tpList) {
                tp.setAdminStatus(adminStatus);
            }
        }

    }

    private void setVPNParamToCtx(VpnVo vpnVo, final TranslatorCtx translatorCtx) {
        translatorCtx.addVal(VpnContextKeys.VPN, vpnVo.getVpn());
    }

    private void setTunnelSchemaToCtx(VpnVo vpnVo, final TranslatorCtx translatorCtx) {
        translatorCtx.addVal(VpnContextKeys.TUNNEL_SCHEMA, vpnVo.getTunnelSchema());
    }

    public void setL3VpnSbiApiService(L3VpnSbiApiService l3VpnSbiApiService) {
        this.l3VpnSbiApiService = l3VpnSbiApiService;
    }

    public void setTranslatorCtxFactory(TranslatorCtxFactory translatorCtxFactory) {
        this.translatorCtxFactory = translatorCtxFactory;
    }

    public void setVpnDao(L3VpnDao vpnDao) {
        this.vpnDao = vpnDao;
    }

    public void setL3VpnQuerySvcService(L3VpnQuerySvcService l3VpnQuerySvcService) {
        this.l3VpnQuerySvcService = l3VpnQuerySvcService;
    }

    public void setL3VpnTranslator(L3VpnTranslator l3VpnTranslator) {
        this.l3VpnTranslator = l3VpnTranslator;
    }

}
