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

package org.openo.sdno.wanvpn.dao;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.openo.sdno.wanvpn.dao.FilterUtil.SimpleWhereStatement;

public class FilterUtilTest {

    @Test
    public void testGetInstance() throws Exception {
        Assert.assertNotNull(FilterUtil.getInstance());
    }

    @Test
    public void testGetFilter() throws Exception {
        final String filter = "uuid in (:uuid) and name=:name and description=:description and tenantName=:tenantName"
                + " and businessTypeName like :businessTypeName and vpnBasicInfoId in (:vpnBasicInfoId) and "
                + "operStatus=:operStatus and startTime=:startTime and endTime=:endTime";

        final String s = FilterUtil.getInstance().getFilter(filter, new ArrayList<String>() {

            {
                add("uuid");
                add("name");
                add("businessTypeName");
            }
        });
        System.out.println(s);
        Assert.assertTrue(s.contains("uuid in (:uuid)"));
        Assert.assertTrue(s.contains("name=:name"));
        Assert.assertTrue(s.contains("businessTypeName like :businessTypeName"));
    }

    @Test
    public void testSimpleWhereStatement() {
        SimpleWhereStatement sim = new SimpleWhereStatement("attr", "statement");
        Assert.assertEquals(sim.toString(), "statement");

        Assert.assertNull(SimpleWhereStatement.fromRawStatement("lsls"));
    }
}
