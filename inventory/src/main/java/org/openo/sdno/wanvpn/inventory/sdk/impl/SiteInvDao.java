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

package org.openo.sdno.wanvpn.inventory.sdk.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.exception.HttpCode;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.model.servicemodel.brs.SiteMO;
import org.openo.sdno.result.Result;
import org.openo.sdno.wanvpn.inventory.sdk.common.OwnerInfoThreadLocal;
import org.openo.sdno.wanvpn.inventory.sdk.inf.IBaseInvDao;
import org.openo.sdno.wanvpn.inventory.sdk.result.ResultRsp;
import org.openo.sdno.wanvpn.util.rest.InventorySDKRestUtil;
import org.openo.sdno.wanvpn.util.rest.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Site inventory data access object class.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-3
 */
public class SiteInvDao implements IBaseInvDao<SiteMO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SiteInvDao.class);

    private static final String SITEURI = "/openoapi/sdnobrs/v1/sites";

    private static final String SITESKEY = "sites";

    private static final String SITEKEY = "site";

    private static final String TOTAL_NUM = "totalNum";

    @Override
    public Result<SiteMO> queryMOById(final String id) throws ServiceException {
        if(null == id || "".equals(id)) {
            LOGGER.error("uuid of query operation is null or empty!");
            return null;
        }
        final RestfulResponse response = RestUtil.get(OwnerInfoThreadLocal.getHttpServletRequest(), SITEURI + "/" + id);

        final Result<SiteMO> moResult = new Result<SiteMO>();

        if(!HttpCode.isSucess(response.getStatus())) {
            LOGGER.error("queryMOById failed.");
        } else {
            @SuppressWarnings("unchecked")
            final Map<String, Object> contentMap = JsonUtil.fromJson(response.getResponseContent(), Map.class);
            final String neData = JsonUtil.toJson(contentMap.get(SITEKEY));
            final SiteMO siteMO = JsonUtil.fromJson(neData, SiteMO.class);
            moResult.setResultObj(siteMO);
            return moResult;
        }

        return null;
    }

    // Query ManagedElement table
    @Override
    public Result<List<SiteMO>> queryMOByParam(final Map<String, String> paramMap) throws ServiceException {
        ResultRsp<List<SiteMO>> rsp = queryPageMOByParam(paramMap);
        return new Result<List<SiteMO>>(-1, rsp.getData());
    }

    @Override
    public Result<List<SiteMO>> queryAllMOs() throws ServiceException {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("fields", "all");
        return queryMOByParam(paramMap);
    }

    @Override
    public ResultRsp<List<SiteMO>> queryPageMOByParam(Map<String, String> paramMap) throws ServiceException {
        final RestfulParametes restfulParametes = new RestfulParametes();
        if(null != paramMap) {
            String fields = paramMap.get("fields");
            if(fields == null) {
                restfulParametes.put("fields", "all");
            }
            for(final Entry<String, String> entry : paramMap.entrySet()) {
                restfulParametes.put(entry.getKey(), entry.getValue());
            }
        }
        final RestfulResponse response = InventorySDKRestUtil.sendGetRequest(SITEURI, restfulParametes,
                OwnerInfoThreadLocal.getHttpServletRequest(), null);

        final ResultRsp<List<SiteMO>> moResult = new ResultRsp<List<SiteMO>>();

        if(!HttpCode.isSucess(response.getStatus())) {
            LOGGER.error("queryMOByParam Failed!");
        } else {
            @SuppressWarnings("unchecked")
            final Map<String, Object> contentMap = JsonUtil.fromJson(response.getResponseContent(), Map.class);
            final String neData = JsonUtil.toJson(contentMap.get(SITESKEY));
            final SiteMO[] siteMOList = JsonUtil.fromJson(neData, SiteMO[].class);
            final List<SiteMO> moArrayList = Arrays.asList(siteMOList);
            moResult.setData(moArrayList);

            final int total = ((Integer)contentMap.get(TOTAL_NUM)).intValue();
            moResult.setTotal(total);
        }

        return moResult;
    }

}
