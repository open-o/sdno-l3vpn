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

package org.openo.sdno.l3vpnservice.service.api;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.l3vpnservice.constant.L3VpnConstants;
import org.openo.sdno.l3vpnservice.constant.L3VpnSvcErrorCode;
import org.openo.sdno.l3vpnservice.service.impl.UniformL3VpnCreateSvcServiceImpl;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnSbiApiService;
import org.openo.sdno.l3vpnservice.service.util.ControllerUtils;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.uniformsbi.base.AdapterResponseInfo;
import org.openo.sdno.model.uniformsbi.l3vpn.BgpRoute;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Ac;
import org.openo.sdno.model.uniformsbi.l3vpn.L3Vpn;
import org.openo.sdno.model.uniformsbi.l3vpn.StaticRoute;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.ResponsTranslator;
import org.openo.sdno.wanvpn.util.TranslateChecker;
import org.openo.sdno.wanvpn.util.URLEncoderUtil;
import org.openo.sdno.wanvpn.util.rest.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * L3VPN SBI API class.<br>
 * 
 * @author
 * @version SDNO 0.5 Aug 2, 2016
 */
@Service
public class L3VpnSbiApi implements L3VpnSbiApiService {

    @Autowired
    private ResponsTranslator responsTranslator;

    private static final Logger LOGGER = LoggerFactory.getLogger(UniformL3VpnCreateSvcServiceImpl.class);

    @Override
    public L3Vpn createL3VPN(L3Vpn l3Vpn, String controllerUuid, @Context HttpServletRequest request)
            throws ServiceException {
        final Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("l3vpn", l3Vpn);
        String reqJson = JsonUtil.toJson(paras);
        reqJson = TranslateChecker.check(reqJson);
        final RestfulParametes restfulParametes = RestUtil.getRestfulParametes(reqJson);
        restfulParametes.putHttpContextHeader("X-Driver-Parameter",
                "extSysID=" + URLEncoderUtil.encode(controllerUuid));
        LOGGER.info(restfulParametes.getRawData());
        final RestfulResponse rsp = RestUtil.sendPostRequest(L3VpnConstants.CREATE_VPN, restfulParametes, request,
                L3VpnConstants.TIMEOUT);
        if(rsp.getStatus() / 100 != 2) {
            throw new ServiceException("Create L3VPN error with status : " + rsp.getStatus());
        }
        // final AdapterResponseInfo adapterResponseInfo =
        // responsTranslator.tranlate(rsp);
        // this.handleResponse(adapterResponseInfo,
        // L3VpnSvcErrorCode.L3VPN_CREATE_CONTROLLER_FAIL);
        return l3Vpn;
    }

    @Override
    public void deployVpnStatus(final L3Vpn l3Vpn, final String controllerUuid, @Context HttpServletRequest request)
            throws ServiceException {
        final Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("l3vpn", l3Vpn);
        String strJsonReq = JsonUtil.toJson(paras);

        strJsonReq = TranslateChecker.check(strJsonReq);
        final RestfulParametes restfulParametes = RestUtil.getRestfulParametes(strJsonReq);
        restfulParametes.putHttpContextHeader("X-Driver-Parameter",
                "extSysID=" + URLEncoderUtil.encode(controllerUuid));
        final String url = MessageFormat.format(L3VpnConstants.VPN_ACTIVE, URLEncoderUtil.encode(l3Vpn.getUuid()));
        LOGGER.info(url);
        LOGGER.info(strJsonReq);
        final RestfulResponse rsp = RestUtil.sendPutRequest(url, restfulParametes, request);
        final AdapterResponseInfo adapterResponseInfo = responsTranslator.tranlate(rsp);
        this.handleResponse(adapterResponseInfo, L3VpnSvcErrorCode.L3VPN_UPDATE_STATUS_CONTROLLER_FAIL);
    }

    @Override
    public void deployTpStatus(final String vpnId, final L3Ac l3ac, String ctrlUuid,
            @Context HttpServletRequest request) throws ServiceException {
        final Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("ac", l3ac);
        final String strJsonReq = JsonUtil.toJson(paras);
        final RestfulParametes restfulParametes = RestUtil.getRestfulParametes(strJsonReq);
        restfulParametes.putHttpContextHeader("X-Driver-Parameter", "extSysID=" + URLEncoderUtil.encode(ctrlUuid));
        final String url = MessageFormat.format(L3VpnConstants.SBI_URL_SITE_ACTION, URLEncoderUtil.encode(vpnId),
                URLEncoderUtil.encode(l3ac.getUuid()));
        LOGGER.info("deployTpStatus, jsonReq:" + url);
        LOGGER.info("deployTpStatus, jsonReq:" + strJsonReq);
        final RestfulResponse rsp = RestUtil.sendPutRequest(url, restfulParametes, request);
        final AdapterResponseInfo adapterResponseInfo = responsTranslator.tranlate(rsp);
        this.handleResponse(adapterResponseInfo, L3VpnSvcErrorCode.L3VPN_UPDATE_TP_STATUS_CONTROLLER_FAIL);
    }

    @Override
    public void deleteVpn(String vpnId, String controllerUuid, @Context HttpServletRequest request)
            throws ServiceException {
        final RestfulParametes restParametes = new RestfulParametes();
        restParametes.putHttpContextHeader("Content-Type", "application/json");
        restParametes.putHttpContextHeader("X-Driver-Parameter", "extSysID=" + URLEncoderUtil.encode(controllerUuid));
        restParametes.put("resource", L3VpnConstants.MODULE_L3VPN);
        restParametes.put("isDryrun", "");
        restParametes.put("uuid", vpnId);

        final String url = MessageFormat.format(L3VpnConstants.VPN_DELETE, URLEncoderUtil.encode(vpnId));
        LOGGER.info("delete vpn, jsonReq:" + url);

        final RestfulResponse rsp = RestUtil.sendDeleteRequest(url, restParametes, request);
        final AdapterResponseInfo adapterResponseInfo = responsTranslator.tranlate(rsp);
        this.handleResponse(adapterResponseInfo, L3VpnSvcErrorCode.L3VPN_DELETE_CONTROLLER_FAIL);

    }

    @Override
    public void modifyVpnDescrition(final String ctrlUuid, final L3Vpn l3Vpn, @Context HttpServletRequest request)
            throws ServiceException {
        final Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("l3vpn", l3Vpn);
        String strJsonReq = JsonUtil.toJson(paras);
        strJsonReq = TranslateChecker.check(strJsonReq);
        final RestfulParametes restfulParametes = RestUtil.getRestfulParametes(strJsonReq);
        restfulParametes.putHttpContextHeader("X-Driver-Parameter", "extSysID=" + URLEncoderUtil.encode(ctrlUuid));
        final String url = MessageFormat.format(L3VpnConstants.VPN_MODIFY, URLEncoderUtil.encode(l3Vpn.getUuid()));
        LOGGER.info("L3VPN update description url:" + url);
        LOGGER.info(strJsonReq);

        final RestfulResponse rsp = RestUtil.sendPutRequest(url, restfulParametes, request);
        final AdapterResponseInfo adapterResponseInfo = responsTranslator.tranlate(rsp);
        this.handleResponse(adapterResponseInfo, L3VpnSvcErrorCode.L3VPN_UPDATEDESC_CONTROLLER_FAIL);
    }

    @Override
    public L3Vpn getL3vpnDetail(final String uuid, final String ctrluuid, @Context HttpServletRequest request)
            throws ServiceException {
        final RestfulParametes restfulParamL3 = new RestfulParametes();
        restfulParamL3.put("uuid", uuid);
        restfulParamL3.putHttpContextHeader("X-Driver-Parameter", "extSysID=" + URLEncoderUtil.encode(ctrluuid));
        final String urlL3vpn = MessageFormat.format(L3VpnConstants.VPN_QUERY, URLEncoderUtil.encode(uuid));
        LOGGER.info("L3VPN query url:" + urlL3vpn);
        final RestfulResponse responseL3vpn = RestUtil.sendGetRequest(urlL3vpn, restfulParamL3, request);
        final AdapterResponseInfo adapterResponseInfo = responsTranslator.tranlate(responseL3vpn);
        return this.handleResponse(adapterResponseInfo, L3VpnSvcErrorCode.L3VPN_GET_CONTROLLER_FAIL, L3Vpn.class);
    }

    @Override
    public L3Ac createTp(L3Ac ac, String l3VpnId, String ctrlUuid, @Context HttpServletRequest request)
            throws ServiceException {
        final Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("ac", ac);
        final String strJsonReq = JsonUtil.toJson(paras);
        final RestfulParametes restfulParametes = RestUtil.getRestfulParametes(strJsonReq);
        restfulParametes.putHttpContextHeader("X-Driver-Parameter", "extSysID=" + URLEncoderUtil.encode(ctrlUuid));
        final String url = MessageFormat.format(L3VpnConstants.ADD_TP, URLEncoderUtil.encode(l3VpnId));
        LOGGER.info(url);
        LOGGER.info("Create tp:" + strJsonReq);

        final RestfulResponse rsp = RestUtil.sendPostRequest(url, restfulParametes, request, L3VpnConstants.TIMEOUT);
        final AdapterResponseInfo adapterResponseInfo = responsTranslator.tranlate(rsp);
        this.handleResponse(adapterResponseInfo, L3VpnSvcErrorCode.L3VPN_CREATE_TP_CONTROLLER_FAIL);
        return ac;
    }

    @Override
    public void deleteTp(String l3vpnUuid, String tpUuid, String ctrlUuid, @Context HttpServletRequest request)
            throws ServiceException {
        final RestfulParametes restParametes = new RestfulParametes();
        restParametes.putHttpContextHeader("X-Driver-Parameter", "extSysID=" + URLEncoderUtil.encode(ctrlUuid));
        final String url = MessageFormat.format(L3VpnConstants.DELETE_TP, URLEncoderUtil.encode(l3vpnUuid),
                URLEncoderUtil.encode(tpUuid));
        LOGGER.info("Delete tp:" + url);

        final RestfulResponse rsp = RestUtil.sendDeleteRequest(url, restParametes, request);
        final AdapterResponseInfo adapterResponseInfo = responsTranslator.tranlate(rsp);
        this.handleResponse(adapterResponseInfo, L3VpnSvcErrorCode.L3VPN_DELETE_TP_CONTROLLER_FAIL);
    }

    @Override
    public void createStaticRoute(StaticRoute staticRoute, String l3vpnUuid, String tpUuid, String ctrlUuid,
            @Context HttpServletRequest request) throws ServiceException {
        final Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("staticRoute", staticRoute);
        String strJsonReq = JsonUtil.toJson(paras);
        LOGGER.info(strJsonReq);
        RestfulParametes restfulParametes = RestUtil.getRestfulParametes(strJsonReq);
        restfulParametes.putHttpContextHeader("X-Driver-Parameter", "extSysID=" + URLEncoderUtil.encode(ctrlUuid));
        String url = MessageFormat.format(L3VpnConstants.ADD_ROUTE_STATIC, URLEncoderUtil.encode(l3vpnUuid),
                URLEncoderUtil.encode(tpUuid));
        final RestfulResponse rsp = RestUtil.sendPostRequest(url, restfulParametes, request, L3VpnConstants.TIMEOUT);
        final AdapterResponseInfo adapterResponseInfo = responsTranslator.tranlate(rsp);
        this.handleResponse(adapterResponseInfo, L3VpnSvcErrorCode.L3VPN_CREATE_STATICROUTE_CONTROLLER_FAIL);
    }

    @Override
    public void createBgpRoute(BgpRoute bgpRoute, String l3vpnUuid, String tpUuid, String ctrlUuid,
            @Context HttpServletRequest request) throws ServiceException {
        final Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("bgpRoute", bgpRoute);
        String strJsonReq = JsonUtil.toJson(paras);
        LOGGER.info(strJsonReq);
        RestfulParametes restfulParametes = RestUtil.getRestfulParametes(strJsonReq);
        restfulParametes.putHttpContextHeader("X-Driver-Parameter", "extSysID=" + URLEncoderUtil.encode(ctrlUuid));
        String url = MessageFormat.format(L3VpnConstants.ADD_ROUTE_BGP, URLEncoderUtil.encode(l3vpnUuid),
                URLEncoderUtil.encode(tpUuid));
        final RestfulResponse rsp = RestUtil.sendPostRequest(url, restfulParametes, request, L3VpnConstants.TIMEOUT);
        final AdapterResponseInfo adapterResponseInfo = responsTranslator.tranlate(rsp);
        this.handleResponse(adapterResponseInfo, L3VpnSvcErrorCode.L3VPN_CREATE_BGPROUTE_CONTROLLER_FAIL);
    }

    @Override
    public void deleteStaticRoute(Vpn tempVpn, String tpUuid, String urlBody, @Context HttpServletRequest request)
            throws ServiceException {
        String ctrlUuid = ControllerUtils.getControllerUUID(tempVpn);
        String l3vpnUuid = tempVpn.getId();
        final RestfulParametes restParametes = new RestfulParametes();
        restParametes.putHttpContextHeader("X-Driver-Parameter", "extSysID=" + URLEncoderUtil.encode(ctrlUuid));
        String url = MessageFormat.format(L3VpnConstants.DELETE_STATIC_ROUTE, URLEncoderUtil.encode(l3vpnUuid),
                URLEncoderUtil.encode(tpUuid));
        url = url + urlBody;
        final RestfulResponse rsp = RestUtil.sendDeleteRequest(url, restParametes, request);
        final AdapterResponseInfo adapterResponseInfo = responsTranslator.tranlate(rsp);
        this.handleResponse(adapterResponseInfo, L3VpnSvcErrorCode.L3VPN_DELETE_STATICROUTE_CONTROLLER_FAIL);
    }

    @Override
    public void deleteBgpRoute(Vpn tempVpn, String tpUuid, String urlBody, @Context HttpServletRequest request)
            throws ServiceException {
        String ctrlUuid = ControllerUtils.getControllerUUID(tempVpn);
        String l3vpnUuid = tempVpn.getId();
        final RestfulParametes restParametes = new RestfulParametes();
        restParametes.putHttpContextHeader("X-Driver-Parameter", "extSysID=" + URLEncoderUtil.encode(ctrlUuid));
        String url = MessageFormat.format(L3VpnConstants.DELETE_BGP_ROUTE, URLEncoderUtil.encode(l3vpnUuid),
                URLEncoderUtil.encode(tpUuid));
        url = url + urlBody;
        final RestfulResponse rsp = RestUtil.sendDeleteRequest(url, restParametes, request);
        final AdapterResponseInfo adapterResponseInfo = responsTranslator.tranlate(rsp);
        this.handleResponse(adapterResponseInfo, L3VpnSvcErrorCode.L3VPN_DELETE_BGPROUTE_CONTROLLER_FAIL);
    }

    private void handleResponse(AdapterResponseInfo adapterResponseInfo, String controllerFailCode)
            throws ServiceException {
        if(adapterResponseInfo.getRet() / 100 != 2) {

            throw adapterResponseInfo.getServiceException(controllerFailCode);
        }
    }

    private <T> T handleResponse(AdapterResponseInfo adapterResponseInfo, String controllerFailCode, Class<T> object)
            throws ServiceException {
        if(adapterResponseInfo.getRet() / 100 != 2) {
            throw adapterResponseInfo.getServiceException(controllerFailCode);
        } else {
            return JsonUtil.fromJson(adapterResponseInfo.getMsg(), object);
        }
    }

    public void setResponsTranslator(ResponsTranslator responsTranslator) {
        this.responsTranslator = responsTranslator;
    }

}
