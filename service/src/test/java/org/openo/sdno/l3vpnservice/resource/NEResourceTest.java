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

package org.openo.sdno.l3vpnservice.resource;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.l3vpnservice.JsonFileUtil;
import org.openo.sdno.l3vpnservice.service.impl.NEServiceImpl;
import org.openo.sdno.model.servicemodel.brs.LtpMO;
import org.openo.sdno.result.Result;
import org.openo.sdno.wanvpn.inventory.sdk.impl.LtpInvDao;

import junit.framework.Assert;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;

public class NEResourceTest {

    private final NEResource neResource = new NEResource();

    @Mocked
    private HttpServletRequest httpServletRequest;

    @Before
    public void setUp() throws Exception {
        new MockUp<LtpInvDao>() {

            @Mock
            public Result<List<LtpMO>> queryMOByParam(final Map<String, String> paramMap)
                    throws ServiceException, IOException {
                Result<List<LtpMO>> ltpResult = new Result<List<LtpMO>>();
                ltpResult.setResultObj(getLtpJson());
                return ltpResult;
            }
        };
    }

    private List<LtpMO> getLtpJson() throws IOException {
        final String filePath = new File("src/test/resources/ltp.json").getCanonicalPath();
        final Map<String, Object> contentMap = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Map.class);
        final String neData = JsonUtil.toJson(contentMap.get("logicalTernminationPoint"));
        final LtpMO[] ltpMOList = JsonUtil.fromJson(neData, LtpMO[].class);
        Assert.assertNotNull(ltpMOList);
        final List<LtpMO> moArrayList = Arrays.asList(ltpMOList);
        return moArrayList;
    }

    @Test
    public void testGetResUri() {
        Assert.assertNotNull(neResource.getResUri());
    }

    @Test
    public void testGetNePorts() throws ServiceException {
        NEServiceImpl neService = new NEServiceImpl();
        neService.setInvTpService(new LtpInvDao());
        neResource.setService(neService);
        try {
            neResource.getNePorts(UuidUtils.createUuid(), "name", httpServletRequest);
        } catch(ServiceException e) {
            e.printStackTrace();
        }
    }
}
