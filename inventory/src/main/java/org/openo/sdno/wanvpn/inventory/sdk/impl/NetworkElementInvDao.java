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

package org.openo.sdno.wanvpn.inventory.sdk.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.exception.HttpCode;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.model.servicemodel.brs.NetworkElementMO;
import org.openo.sdno.result.Result;
import org.openo.sdno.wanvpn.inventory.sdk.common.OwnerInfoThreadLocal;
import org.openo.sdno.wanvpn.inventory.sdk.inf.IBaseInvDao;
import org.openo.sdno.wanvpn.inventory.sdk.result.ResultRsp;
import org.openo.sdno.wanvpn.util.rest.InventorySDKRestUtil;
import org.openo.sdno.wanvpn.util.rest.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Network element inventory data access object class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-3
 */
public class NetworkElementInvDao implements IBaseInvDao<NetworkElementMO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkElementInvDao.class);

    private static final String MEURI = "/openoapi/sdnobrs/v1/managed-elements";

    private static final String MESKEY = "managedElements";

    @Override
    public Result<NetworkElementMO> queryMOById(final String id) throws ServiceException {
        if(null == id || "".equals(id)) {
            LOGGER.error("uuid of query operation is null or empty!");
            return null;
        }
        final RestfulResponse response = RestUtil.get(OwnerInfoThreadLocal.getHttpServletRequest(), MEURI + "/" + id);

        final Result<NetworkElementMO> moResult = new Result<NetworkElementMO>();

        if(!HttpCode.isSucess(response.getStatus())) {
            LOGGER.error("queryMOById failed.");
            return null;
        }
        @SuppressWarnings("unchecked")
        final Map<String, Object> contentMap = JsonUtil.fromJson(response.getResponseContent(), Map.class);
        final String neData = JsonUtil.toJson(contentMap.get("managedElement"));
        final NetworkElementMO networkElementMO = JsonUtil.fromJson(neData, NetworkElementMO.class);
        moResult.setResultObj(networkElementMO);
        return moResult;

    }

    @Override
    public Result<List<NetworkElementMO>> queryMOByParam(final Map<String, String> paramMap) throws ServiceException {
        final RestfulParametes restfulParametes = new RestfulParametes();
        if(null != paramMap) {
            for(final Entry<String, String> entry : paramMap.entrySet()) {
                restfulParametes.put(entry.getKey(), entry.getValue());
            }
        }
        final RestfulResponse response = InventorySDKRestUtil.sendGetRequest(MEURI, restfulParametes,
                OwnerInfoThreadLocal.getHttpServletRequest(), null);

        final Result<List<NetworkElementMO>> moResult = new Result<List<NetworkElementMO>>();

        if(!HttpCode.isSucess(response.getStatus())) {
            LOGGER.error("queryMOByParam Failed!");
            return null;
        }
        @SuppressWarnings("unchecked")
        final Map<String, Object> contentMap = JsonUtil.fromJson(response.getResponseContent(), Map.class);
        final String neData = JsonUtil.toJson(contentMap.get(MESKEY));
        final NetworkElementMO[] networkElementMOList = JsonUtil.fromJson(neData, NetworkElementMO[].class);
        final List<NetworkElementMO> moArrayList = Arrays.asList(networkElementMOList);
        moResult.setResultObj(moArrayList);

        return moResult;
    }

    @Override
    public Result<List<NetworkElementMO>> queryAllMOs() throws ServiceException {
        // Unimplemented
        return null;
    }

    @Override
    public ResultRsp<List<NetworkElementMO>> queryPageMOByParam(Map<String, String> paramMap) throws ServiceException {
        // Unimplemented
        return null;
    }

}
