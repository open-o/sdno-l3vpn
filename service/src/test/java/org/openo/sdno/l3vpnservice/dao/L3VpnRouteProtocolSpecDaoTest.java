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
package org.openo.sdno.l3vpnservice.dao;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openo.sdno.model.db.l3vpn.L3VpnRouteProtocolSpecPo;

public class L3VpnRouteProtocolSpecDaoTest
{
    private final L3VpnRouteProtocolSpecDao dao = new L3VpnRouteProtocolSpecDao();

    @Before
    public void setUp() throws Exception
    {
    }

    @Test
    public void testGetPoClass()
    {
        Assert.assertEquals(dao.getPoClass(), L3VpnRouteProtocolSpecPo.class);
    }
}
