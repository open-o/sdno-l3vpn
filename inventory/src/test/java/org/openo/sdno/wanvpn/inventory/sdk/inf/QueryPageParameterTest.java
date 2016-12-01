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

package org.openo.sdno.wanvpn.inventory.sdk.inf;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.sdno.wanvpn.util.query.mss.QueryComplexParams;
import org.openo.sdno.wanvpn.inventory.sdk.inf.QueryPageParameter;

public class QueryPageParameterTest {

    @Test
    public void testConvertRestfulParametesAttrNull1() throws ServiceException {
        List<String> attr = new ArrayList<String>();
        attr.add("test");
        QueryPageParameter demo = new QueryPageParameter(null, attr, null, new QueryComplexParams());
        RestfulParametes result = demo.convertRestfulParametes();
        assertTrue(result.get("attr").equals("[\"test\"]"));
    }

    @Test
    public void testConvertRestfulParametesAttrNull2() throws ServiceException {
        List<String> attr = new ArrayList<String>();
        attr.add("test");
        QueryPageParameter demo = new QueryPageParameter(null, null, null, new QueryComplexParams());
        RestfulParametes result = demo.convertRestfulParametes();
        assertNull(result.get("attr"));
    }

    @Test
    public void testConvertRestfulParametesAttrNull3() throws ServiceException {
        List<String> attr = new ArrayList<String>();
        QueryPageParameter demo = new QueryPageParameter(null, attr, null, new QueryComplexParams());
        RestfulParametes result = demo.convertRestfulParametes();
        assertNull(result.get("attr"));
    }

}
