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


package org.openo.sdno.model.servicemodel.brs;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TenantMOTest {

    @Test
    public void testEqualsObjectNull() {
        TenantMO a = new TenantMO();
        assertFalse(a.equals(null));
    }

    @Test
    public void testEqualsObjectSame() {
        TenantMO a = new TenantMO();
        TenantMO b = a;
        assertTrue(a.equals(b));
    }

    @Test
    public void testEqualsObjectDifClass() {
        TenantMO a = new TenantMO();
        SiteMOTest b = new SiteMOTest();
        assertTrue(!a.equals(b));
    }

    @Test
    public void testEqualsObjectNullId() {
        TenantMO a = new TenantMO();
        TenantMO b = new TenantMO();
        b.setId("b1");
        assertTrue(!a.equals(b));
    }

    @Test
    public void testEqualsObjectDifId() {
        TenantMO a = new TenantMO();
        a.setId("a1");
        TenantMO b = new TenantMO();
        b.setId("b1");
        assertTrue(!a.equals(b));
    }

    @Test
    public void testEqualsObjectSameId() {
        TenantMO a = new TenantMO();
        a.setId("a1");
        TenantMO b = new TenantMO();
        b.setId("a1");
        assertTrue(a.equals(b));
    }

}
