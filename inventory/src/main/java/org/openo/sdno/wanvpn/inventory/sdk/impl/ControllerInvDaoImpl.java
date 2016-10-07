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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.exception.HttpCode;
import org.openo.sdno.rest.ResponseUtils;
import org.openo.sdno.result.Result;
import org.openo.sdno.wanvpn.inventory.sdk.inf.BaseInvDao;
import org.openo.sdno.wanvpn.inventory.sdk.result.ResultRsp;
import org.openo.sdno.model.servicemodel.brs.ControllerMO;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;

/**
 * Controller inventory data access object class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-3
 */
public class ControllerInvDaoImpl implements BaseInvDao<ControllerMO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerInvDaoImpl.class);

    private static final String GETLISTPATH = "/rest/accessobjectmgmt/v1/access-objects?category=Controller";

    private static final String GETCONTROLLER = "/rest/accessobjectmgmt/v1/access-objects/";

    private static final String ACCESS_OBJECTS_KEY = "accessObjects";

    private static final String ACCESS_OBJECT_KEY = "accessObject";

    @Override
    public Result<List<ControllerMO>> queryAllMOs() throws ServiceException {
        final Result<List<ControllerMO>> moResult = new Result<List<ControllerMO>>();

        final RestfulParametes restfulParametes = new RestfulParametes();
        final RestfulResponse response = RestfulProxy.get(GETLISTPATH, restfulParametes);
        if(HttpCode.isSucess(response.getStatus())) {
            @SuppressWarnings("unchecked")
            final Map<String, Object> responseValue = ResponseUtils.transferResponse(response, Map.class);
            final String resultJson = JsonUtil.toJson(responseValue.get(ACCESS_OBJECTS_KEY));
            final ControllerMO[] controllerArray = JsonUtil.fromJson(resultJson, ControllerMO[].class);
            if(null != controllerArray) {
                moResult.setResultObj(Arrays.asList(controllerArray));
            }
            return moResult;
        } else {
            LOGGER.error("getController get controllers failed.");
        }

        moResult.setResultObj(new ArrayList<ControllerMO>());

        return moResult;
    }

    @Override
    public Result<ControllerMO> queryMOById(final String id) throws ServiceException {
 
        final Result<ControllerMO> moResult = new Result<ControllerMO>();

        final RestfulParametes restfulParametes = new RestfulParametes();
        final RestfulResponse response = RestfulProxy.get(GETCONTROLLER + id, restfulParametes);
        if(HttpCode.isSucess(response.getStatus())) {
            @SuppressWarnings("unchecked")
            final Map<String, Object> responseValue = ResponseUtils.transferResponse(response, Map.class);
            final String resultJson = JsonUtil.toJson(responseValue.get(ACCESS_OBJECT_KEY));
            final ControllerMO controller = JsonUtil.fromJson(resultJson, ControllerMO.class);
            moResult.setResultObj(controller);
            return moResult;
        } else {
            LOGGER.error("getController get controller failed.");
        }

        return moResult;
    }

    @Override
    public Result<List<ControllerMO>> queryMOByParam(final Map<String, String> paramMap) throws ServiceException {
        // Unimplemented
        return null;
    }

    @Override
    public ResultRsp<List<ControllerMO>> queryPageMOByParam(Map<String, String> paramMap) throws ServiceException {
        // Unimplemented
        return null;
    }

}
