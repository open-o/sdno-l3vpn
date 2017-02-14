/*
 * Copyright 2017 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.wanvpn.translator.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openo.sdno.model.servicemodel.common.enumeration.AdminStatus;

public class TranslatorUtilTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testDropIPMask() {
        assertEquals(TranslatorUtil.dropIPMask("10.0.0.1/24"), "10.0.0.1");
        assertEquals(TranslatorUtil.dropIPMask("10.0.0.1"), "10.0.0.1");

        List<Integer> Vlans = TranslatorUtil.stringVlan2IntVlanlst(null);
        assertNull(Vlans);

        try {
            TranslatorUtil.stringVlan2IntVlanlst("1-5-8");
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }

        try {
            TranslatorUtil.stringVlan2IntVlanlst("9-8");
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }

        try {
            TranslatorUtil.stringVlan2IntVlanlst("a");
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }

        Vlans = TranslatorUtil.stringVlan2IntVlanlst("1,23,3,5-8,,4");
        assertEquals(Vlans.get(0).toString(), "1");
        assertEquals(Vlans.get(1).toString(), "3");
        assertEquals(Vlans.get(2).toString(), "4");
        assertEquals(Vlans.get(3).toString(), "5");
        assertEquals(Vlans.get(4).toString(), "6");
        assertEquals(Vlans.get(5).toString(), "7");
        assertEquals(Vlans.get(6).toString(), "8");
        assertEquals(Vlans.get(7).toString(), "23");

        assertEquals(TranslatorUtil.n2sAdminStatus(true), AdminStatus.ACTIVE.getAlias());
        assertEquals(TranslatorUtil.n2sAdminStatus(false), AdminStatus.INACTIVE.toString());
    }

}
