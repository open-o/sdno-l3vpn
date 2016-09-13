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

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.xml.ws.spi.http.HttpContext;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnActiveSvcService;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnCreateSvcService;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnDeleteSvcService;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnModifySvcService;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnQuerySvcService;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnQueryTePathService;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnSvcService;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnTpModifySvcService;
import org.openo.sdno.l3vpnservice.service.inf.L3vpnRouteAddSvcService;
import org.openo.sdno.l3vpnservice.service.inf.L3vpnRouteDeleteSvcService;
import org.openo.sdno.l3vpnservice.service.inf.L3vpnTpAddSvcService;
import org.openo.sdno.l3vpnservice.service.inf.L3vpnTpDeleteSvcService;
import org.openo.sdno.model.common.query.BatchQueryResult;
import org.openo.sdno.model.servicemodel.routeprotocol.RouteProtocolSpec;
import org.openo.sdno.model.servicemodel.tepath.TePath;
import org.openo.sdno.model.servicemodel.tepath.TePathQueryKey;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.servicemodel.vpn.VpnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Uniform L3VPN service implement class.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-5-31
 */
@Service("uniformL3VpnSvcService")
public class UniformL3VpnSvcServiceImpl implements L3VpnSvcService {

    @Autowired
    @Qualifier("uniformL3VpnCreateSvcService")
    private L3VpnCreateSvcService l3VpnCreateSvcService;

    @Autowired
    @Qualifier("uniformL3VpnDeleteSvcService")
    private L3VpnDeleteSvcService l3VpnDeleteSvcService;

    @Autowired
    @Qualifier("uniformL3VpnModifySvcService")
    private L3VpnModifySvcService l3VpnModifySvcService;

    @Autowired
    @Qualifier("uniformL3VpnTpDeleteSvcService")
    private L3vpnTpDeleteSvcService l3VpnTpDeleteSvcService;

    @Autowired
    @Qualifier("uniformL3VpnTpAddSvcService")
    private L3vpnTpAddSvcService l3vpnTpAddSvcService;

    @Autowired
    @Qualifier("uniformL3VpnRouteAddSvcService")
    private L3vpnRouteAddSvcService l3vpnRouteAddSvcService;

    @Autowired
    @Qualifier("uniformL3VpnRouteDeleteSvcService")
    private L3vpnRouteDeleteSvcService l3vpnRouteDeleteSvcService;

    @Autowired
    @Qualifier("uniformL3VpnActiveSvcService")
    private L3VpnActiveSvcService l3VpnActiveSvcService;

    @Autowired
    @Qualifier("uniformL3VpnQuerySvcService")
    private L3VpnQuerySvcService l3vpnQuerySvcService;

    @Autowired
    @Qualifier("uniformL3VpnTpModifySvcService")
    private L3VpnTpModifySvcService l3VpnTpModifySvcService;

    @Autowired
    @Qualifier("uniforml3vpnQueryTePathService")
    private L3VpnQueryTePathService l3VpnQueryTpPathService;

    /**
     * @see com.huawei.netmatrix.svc.underlayvpn.l3vpnservice.service.inf.L3VpnSvcService#create(org.openo.sdno.model.servicemodel.vpn.huawei.netmatrix.vpn.wan.servicemodel.vpn.vo.VpnVo,
     *      com.huawei.bsp.roa.common.HttpContext)
     */
    @Override
    public Vpn create(VpnVo vpnVo, @Context HttpServletRequest request) throws ServiceException {
        return l3VpnCreateSvcService.create(vpnVo, request);
    }

    /**
     * @see L3VpnSvcService#deleteTp(Vpn , HttpContext)
     */
    @Override
    public Vpn delete(Vpn vpn, @Context HttpServletRequest request) throws ServiceException {
        return l3VpnDeleteSvcService.delete(vpn, request);
    }

    /**
     * @see L3VpnSvcService#active(Vpn , HttpContext)
     */
    @Override
    public Vpn active(Vpn vpn, @Context HttpServletRequest request) throws ServiceException {
        return l3VpnActiveSvcService.active(vpn, request);
    }

    /**
     * @see L3VpnSvcService#deactive(Vpn , HttpContext)
     */
    @Override
    public Vpn deactive(Vpn vpn, @Context HttpServletRequest request) throws ServiceException {
        return l3VpnActiveSvcService.deactive(vpn, request);
    }

    /**
     * @see com.huawei.netmatrix.svc.underlayvpn.l3vpnservice.service.inf.L3VpnSvcService#modifyDesc(org.openo.sdno.model.servicemodel.vpn.Vpn,
     *      com.huawei.bsp.roa.common.HttpContext)
     */
    @Override
    public Vpn modifyDesc(Vpn vpn, @Context HttpServletRequest request) throws ServiceException {
        return l3VpnModifySvcService.modifyDesc(vpn, request);
    }

    /**
     * @see com.huawei.netmatrix.svc.underlayvpn.l3vpnservice.service.inf.L3VpnSvcService#getDetail(java.lang.String,
     *      com.huawei.bsp.roa.common.HttpContext)
     */
    @Override
    public Vpn getDetail(String uuid, @Context HttpServletRequest request) throws ServiceException {
        return l3vpnQuerySvcService.getDetail(uuid, request);
    }

    /**
     * @see L3VpnSvcService#getStatus(Vpn , HttpContext)
     */
    @Override
    public Vpn getStatus(Vpn vpn, @Context HttpServletRequest request) throws ServiceException {
        return l3vpnQuerySvcService.getStatus(vpn, request);
    }

    @Override
    public Tp addTp(Vpn vpn, Tp tp, @Context HttpServletRequest request) throws ServiceException {
        return l3vpnTpAddSvcService.addTp(vpn, tp, request);
    }

    @Override
    public Tp deleteTp(Vpn vpn, String tpId, @Context HttpServletRequest request) throws ServiceException {
        return l3VpnTpDeleteSvcService.deleteTp(vpn, tpId, request);
    }

    @Override
    public RouteProtocolSpec addRoute(Vpn vpn, String tpUuid, RouteProtocolSpec routeProtocolSpec,
            @Context HttpServletRequest request) throws ServiceException {
        return l3vpnRouteAddSvcService.addRouteProtocol(vpn, tpUuid, routeProtocolSpec, request);
    }

    @Override
    public RouteProtocolSpec deleteRoute(Vpn vpn, String tpUuid, String routeId, @Context HttpServletRequest request)
            throws ServiceException {
        return l3vpnRouteDeleteSvcService.deleteRoute(vpn, tpUuid, routeId, request);
    }

    @Override
    public Tp activeSite(Vpn vpn, String tpId, @Context HttpServletRequest request) throws ServiceException {
        return l3VpnActiveSvcService.activeSite(vpn, tpId, request);
    }

    @Override
    public Tp deactiveSite(Vpn vpn, String tpId, @Context HttpServletRequest request) throws ServiceException {
        return l3VpnActiveSvcService.deactiveSite(vpn, tpId, request);
    }

    @Override
    public BatchQueryResult<TePath> getTePath(Vpn vpn, TePathQueryKey queryKey, @Context HttpServletRequest request)
            throws ServiceException {
        return l3VpnQueryTpPathService.getTePath(vpn, queryKey, request);
    }

    public void setL3VpnCreateSvcService(final L3VpnCreateSvcService l3VpnCreateSvcService) {
        this.l3VpnCreateSvcService = l3VpnCreateSvcService;
    }

    public void setL3VpnDeleteSvcService(final L3VpnDeleteSvcService l3VpnDeleteSvcService) {
        this.l3VpnDeleteSvcService = l3VpnDeleteSvcService;
    }

    public void setL3VpnModifySvcService(final L3VpnModifySvcService l3VpnModifySvcService) {
        this.l3VpnModifySvcService = l3VpnModifySvcService;
    }

    public void setL3VpnQuerySvcService(final L3VpnQuerySvcService l3VpnQuerySvcService) {
        l3vpnQuerySvcService = l3VpnQuerySvcService;
    }

    public void setL3VpnActiveSvcService(final L3VpnActiveSvcService l3VpnActiveSvcService) {
        this.l3VpnActiveSvcService = l3VpnActiveSvcService;
    }

    public void setL3VpnTpModifySvcService(final L3VpnTpModifySvcService l3VpnTpModifySvcService) {
        this.l3VpnTpModifySvcService = l3VpnTpModifySvcService;
    }

    public void setL3vpnTpAddSvcService(final L3vpnTpAddSvcService l3vpnTpAddSvcService) {
        this.l3vpnTpAddSvcService = l3vpnTpAddSvcService;
    }

    public void setL3VpnTpDeleteSvcService(final L3vpnTpDeleteSvcService l3VpnTpDeleteSvcService) {
        this.l3VpnTpDeleteSvcService = l3VpnTpDeleteSvcService;
    }

    public void setL3VpnQueryTePathService(final L3VpnQueryTePathService l3VpnQueryTePathService) {
        l3VpnQueryTpPathService = l3VpnQueryTePathService;
    }

    public void setL3vpnRouteAddSvcService(final L3vpnRouteAddSvcService l3vpnRouteAddSvcService) {
        this.l3vpnRouteAddSvcService = l3vpnRouteAddSvcService;
    }

    public void setL3vpnRouteDeleteSvcService(final L3vpnRouteDeleteSvcService l3vpnRouteDeleteSvcService) {
        this.l3vpnRouteDeleteSvcService = l3vpnRouteDeleteSvcService;
    }
}
