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

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.model.uniformsbi.base.AdapterResponseInfo;
import org.openo.sdno.wanvpn.JsonFileUtil;
import org.openo.sdno.wanvpn.translator.uniformsbi.impl.ResponsTranslatorImpl;

public class ResponsTranslatorImplTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testTranlate() throws IOException, ServiceException {
        ResponsTranslatorImpl transService = new ResponsTranslatorImpl();

        String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                + File.separator + "resources" + File.separator + "response.json";
        RestfulResponse response = new RestfulResponse();
        response.setResponseJson(JsonFileUtil.getJsonString(filePath));
        response.setStatus(200);
        AdapterResponseInfo rs = transService.tranlate(response);
        Assert.assertNotNull(rs);
    }

}
