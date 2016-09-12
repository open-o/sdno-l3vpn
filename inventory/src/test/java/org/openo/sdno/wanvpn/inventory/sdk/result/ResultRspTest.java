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

package org.openo.sdno.wanvpn.inventory.sdk.result;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openo.sdno.model.servicemodel.brs.NetworkElementMO;
import org.openo.sdno.wanvpn.inventory.sdk.result.ResultRsp;

public class ResultRspTest {

    @Test
    public void testResultRsp() {
        ResultRsp demo = new ResultRsp();
        demo.setErrorCode("123");
        demo.setDescArg("123");
        demo.setReasonArg("123");
        demo.setDetailArg("123");
        demo.setAdviceArg("123");
        ResultRsp result = new ResultRsp(demo);
        assertTrue(result.getErrorCode().equals("123"));
    }

    @Test
    public void testIsSuccess() {
        ResultRsp demo = new ResultRsp();
        demo.setErrorCode("underlayvpn.operation.success");
        assertTrue(demo.isSuccess());
    }

    @Test
    public void testIsValid() {
        ResultRsp demo = new ResultRsp();
        demo.setErrorCode("underlayvpn.operation.success");
        NetworkElementMO data = new NetworkElementMO();
        data.setId("123456");
        demo.setData(data);
        assertTrue(demo.isValid());
    }

    @Test
    public void testIsValid1() {
        ResultRsp demo = new ResultRsp();
        demo.setErrorCode("underlayvpn.operation.success");
        assertFalse(demo.isValid());
    }

    @Test
    public void testIsValid2() {
        ResultRsp demo = new ResultRsp();
        demo.setErrorCode("n.success");
        NetworkElementMO data = new NetworkElementMO();
        data.setId("123456");
        demo.setData(data);
        assertFalse(demo.isValid());
    }

    @Test
    public void testIsValid3() {
        ResultRsp demo = new ResultRsp();
        demo.setErrorCode("underlayvpn.opeddddration.success");
        assertFalse(demo.isValid());
    }

}
