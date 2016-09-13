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

package org.openo.sdno.wanvpn.inventory.sdk.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.exception.HttpCode;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.model.servicemodel.brs.ControllerMO;
import org.openo.sdno.model.servicemodel.brs.Device;
import org.openo.sdno.model.servicemodel.brs.NetworkElementMO;
import org.openo.sdno.rest.ResponseUtils;
import org.openo.sdno.result.Result;
import org.openo.sdno.wanvpn.inventory.sdk.common.OwnerInfoThreadLocal;
import org.openo.sdno.wanvpn.util.rest.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The class of inventory proxy.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-3
 */
public class InventoryProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryProxy.class);

    private static final String NEURI = "/openoapi/sdnobrs/v1/managed-elements";

    private static final String GETCONTROLLER = "/openoapi/sdnobrs/v1/controller/";

    private static final String GETCOMMPARAM = "/openoapi/sdnobrs/v1/commparammgmt/access-objects/{0}/commparams";

    private InventoryProxy() {

    }

    /**
     * Get the controller device data through the controller uuid.<br>
     * 
     * @param ctrId The controller uuid
     * @return The controller device data
     * @since SDNO 0.5
     */
    public static Device getControllerDevice(final String ctrId) throws ServiceException {
        Device device = new Device();
        try {

            final RestfulParametes restfulParametes = new RestfulParametes();
            final String url = GETCOMMPARAM.replace("{0}", ctrId);
            final RestfulResponse response = RestfulProxy.get(url, restfulParametes);
            ResponseUtils.checkResonseAndThrowException(response);

            final Result<List<Device>> moResult = new Result<List<Device>>();
            moResult.setResultObj(new ArrayList<Device>());

            @SuppressWarnings("unchecked")
            final Map<String, Object> contentMap = JsonUtil.fromJson(response.getResponseContent(), Map.class);
            final String deviceData = JsonUtil.toJson(contentMap.get("commParamsList"));
            final Device[] devices = JsonUtil.fromJson(deviceData, Device[].class);
            final List<Device> deviceList = Arrays.asList(devices);
            if(null == deviceList || deviceList.isEmpty()) {
                LOGGER.error("paramList is null or empty!!");
                throw new ServiceException("paramList is null or empty!!");
            }
            device = deviceList.get(0);
            device.setIp(queryControllerByID(ctrId).getHostName());
            device.setUser(device.getAuthInfo().getUserName());
            device.setPwd(device.getAuthInfo().getPassword());
        } catch(final ServiceException e) {
            LOGGER.warn("getCltrByCltrId failed:{}", e);
            return null;
        }
        LOGGER.warn("getCltrByCltrId success.");
        return device;
    }

    /**
     * Query the controller object.<br>
     * 
     * @param neId The NE uuid
     * @return The controller object
     * @since SDNO 0.5
     */
    public static ControllerMO queryController(final String neId) throws ServiceException {

        final Result<NetworkElementMO> rsp = queryNeById(neId);
        if(null == rsp || rsp.isFailed() || null == rsp.getResultObj()) {
            LOGGER.error("queryNEById return is null.");
            return null;
        }

        final List<String> contrlIds = rsp.getResultObj().getControllerID();
        if(CollectionUtils.isEmpty(contrlIds)) {
            LOGGER.error("getController from ne is null.");
            return null;
        }
        return queryControllerByID(contrlIds.get(0));

    }

    private static ControllerMO queryControllerByID(final String ctrID) throws ServiceException {
        final RestfulParametes restfulParametes = new RestfulParametes();
        restfulParametes.putHttpContextHeader("Content-Type", MediaType.APPLICATION_JSON);
        final RestfulResponse response = RestfulProxy.get(GETCONTROLLER + ctrID, restfulParametes);
        if(!HttpCode.isSucess(response.getStatus())) {
            LOGGER.error("getController get controller failed.");
            return null;
        }
        return JsonUtil.fromJson(response.getResponseContent(), ControllerMO.class);
    }

    /**
     * Query the NE object through the uuid.<br>
     * 
     * @param id The uuid
     * @return The NE object
     * @since SDNO 0.5
     */
    public static Result<NetworkElementMO> queryNeById(final String id) throws ServiceException {
        if(null == id || "".equals(id)) {
            LOGGER.error("uuid of query operation is null or empty!");
            return null;
        }
        final RestfulResponse response = RestUtil.get(OwnerInfoThreadLocal.getHttpServletRequest(), NEURI + "/" + id);

        final Result<NetworkElementMO> moResult = new Result<NetworkElementMO>();

        if(!HttpCode.isSucess(response.getStatus())) {
            LOGGER.error("queryMOById failed.");
            return null;
        } else {
            @SuppressWarnings("unchecked")
            final Map<String, Object> contentMap = JsonUtil.fromJson(response.getResponseContent(), Map.class);
            final String neData = JsonUtil.toJson(contentMap.get("managedElement"));
            final NetworkElementMO networkElementMO = JsonUtil.fromJson(neData, NetworkElementMO.class);
            moResult.setResultObj(networkElementMO);
            return moResult;
        }
    }
}
