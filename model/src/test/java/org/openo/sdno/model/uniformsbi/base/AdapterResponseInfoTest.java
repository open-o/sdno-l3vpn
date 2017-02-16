/*
 * Copyright 2016-2017 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.model.uniformsbi.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.model.JsonFileUtil;
import org.openo.sdno.model.uniformsbi.error.Error;
import org.openo.sdno.model.uniformsbi.error.Errors;

public class AdapterResponseInfoTest {

    @Test
    public void testControllerSuccess() {
        AdapterResponseInfo adapterResponseInfo = new AdapterResponseInfo();
        adapterResponseInfo.setRet(200);
        assertTrue(adapterResponseInfo.isControllerSuccess());
    }

    @Test
    public void testControllerFalse() {
        AdapterResponseInfo adapterResponseInfo = new AdapterResponseInfo();
        adapterResponseInfo.setRet(500);
        assertFalse(adapterResponseInfo.isControllerSuccess());
    }

    @Test
    public void testControllerFound() {
        AdapterResponseInfo adapterResponseInfo = new AdapterResponseInfo();
        adapterResponseInfo.setRet(200);
        assertFalse(adapterResponseInfo.isController404());
    }

    @Test
    public void testControllerNotFound() {
        AdapterResponseInfo adapterResponseInfo = new AdapterResponseInfo();
        adapterResponseInfo.setRet(404);
        assertTrue(adapterResponseInfo.isController404());
    }

    @Test
    public void testAdapterResponseInfo() throws IOException {
        AdapterResponseInfo adapterResponseInfo1 = new AdapterResponseInfo("test");
        adapterResponseInfo1.setRet(200);
        adapterResponseInfo1.setMsg("new message");
        adapterResponseInfo1.setFormat("format");
        Map<String, String> respHeaders = new HashMap<>();
        respHeaders.put("head1", "111");
        respHeaders.put("head2", "222");
        adapterResponseInfo1.setRespHeaders(respHeaders);

        String filePath = new File("src/test/resources/error.json").getCanonicalPath();
        Error error = JsonUtil.fromJson(JsonFileUtil.getJsonString(filePath), Error.class);
        assertNotNull(error);
        assertNotNull(JsonUtil.toJson(error));

        Errors errors = new Errors();
        List<Error> errorList = new ArrayList<>();
        errorList.add(error);
        errors.setError(errorList);
        adapterResponseInfo1.setErrors(errors);
        adapterResponseInfo1.setSuccess(true);
        assertEquals(adapterResponseInfo1.getRet(), 200);
        assertEquals(adapterResponseInfo1.getMsg(), "new message");
        assertEquals(adapterResponseInfo1.getFormat(), "format");
        assertEquals(adapterResponseInfo1.getRespHeaders().get("head1"), "111");
        assertNotNull(adapterResponseInfo1.getErrors().getError().get(0).getErrorInfo().getErrorParas().getErrorPara());
        assertTrue(adapterResponseInfo1.isSuccess());

        ServiceException serviceException = adapterResponseInfo1.getServiceException("500");
        assertEquals(serviceException.getId(), "500");
        assertTrue(adapterResponseInfo1.toString().contains("200"));

        AdapterResponseInfo adapterResponseInfo2 = new AdapterResponseInfo(300, "test");
        assertEquals(adapterResponseInfo2.getRet(), 300);
        assertEquals(adapterResponseInfo2.getMsg(), "test");

        AdapterResponseInfo adapterResponseInfo3 = new AdapterResponseInfo(100, "format", "msg");
        assertEquals(adapterResponseInfo3.getRet(), 100);
        assertEquals(adapterResponseInfo3.getFormat(), "format");
        assertEquals(adapterResponseInfo3.getMsg(), "msg");

        AdapterResponseInfo adapterResponseInfo4 = new AdapterResponseInfo(100, "format", "msg", respHeaders);
        assertEquals(adapterResponseInfo4.getRet(), 100);
        assertEquals(adapterResponseInfo4.getFormat(), "format");
        assertEquals(adapterResponseInfo4.getMsg(), "msg");
        assertNotNull(adapterResponseInfo4.getRespHeaders());
    }

}
