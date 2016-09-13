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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.db.l3vpn.L3VpnPo;
import org.openo.sdno.model.servicemodel.vpn.Vpn;

import junit.framework.Assert;
import mockit.Mock;
import mockit.MockUp;

public class DaoUtilTest {

    @Test
    public void batchMoConvert() throws Exception {
        final Vpn vpn = new Vpn();
        final List<L3VpnPo> pos = DaoUtil.batchMoConvert(Collections.singletonList(vpn), L3VpnPo.class);
        Assert.assertTrue(CollectionUtils.isNotEmpty(pos));
    }

    @Test()
    public void batchMoConvertWithException1() {
        MockUp<Class> mock = new MockUp<Class>() {

            @Mock
            public T newInstance() throws InstantiationException, IllegalAccessException {
                throw new InstantiationException();

            }
        };

        final Vpn vpn = new Vpn();

        assertNull(DaoUtil.batchMoConvert(Collections.singletonList(vpn), L3VpnPo.class));
        mock.tearDown();
    }

    @Test()
    public void batchMoConvertWithException2() {
        MockUp<Class> mock = new MockUp<Class>() {

            @Mock
            public T newInstance() throws InstantiationException, IllegalAccessException {
                throw new IllegalAccessException();

            }
        };

        final Vpn vpn = new Vpn();
        assertNull(DaoUtil.batchMoConvert(Collections.singletonList(vpn), L3VpnPo.class));
        mock.tearDown();
    }

    @Test
    public void batchMoConvert3() throws Exception {
        new Vpn();
        final List<L3VpnPo> pos = DaoUtil.batchMoConvert(null, L3VpnPo.class);
        Assert.assertTrue(CollectionUtils.isEmpty(pos));
    }

    @Test
    public void batchPoConvert() throws Exception {
        final List<Vpn> pos = DaoUtil.batchPoConvert(null, Vpn.class);
        Assert.assertTrue(CollectionUtils.isEmpty(pos));
    }

    @Test
    public void getUuids() throws Exception {
        final Vpn vpn = new Vpn();
        vpn.setUuid("123");
        final List<Vpn> list1 = new ArrayList<>();
        list1.add(vpn);
        assertEquals("123", DaoUtil.getUuids(list1).get(0));
    }

    @Test
    public void getUuidsWithEmptyInput() throws Exception {

        final List<Vpn> list1 = new ArrayList<>();

        assertTrue(DaoUtil.getUuids(list1).isEmpty());
    }

    @Test
    public void getPoModelUuids() throws Exception {
        L3VpnPo vpn = new L3VpnPo();
        vpn.setUuid("123");
        final List<L3VpnPo> list1 = new ArrayList<>();
        list1.add(vpn);
        assertEquals("123", DaoUtil.getPoModelUuids(list1).get(0));

    }

    @Test
    public void getPoModelUuidsWithEmptyInput() throws Exception {

        final List<L3VpnPo> list1 = new ArrayList<>();

        assertTrue(DaoUtil.getPoModelUuids(list1).isEmpty());

    }

    @Test
    public void resetUuidWithNullInput() throws Exception {
        assertNull(DaoUtil.resetUuid(null));
    }

    @Test
    public void resetUuid() throws Exception {
        final Vpn vpn = new Vpn();
        vpn.setUuid("123");
        String rst = DaoUtil.resetUuid(vpn);

        assertFalse("123".equals(rst));
    }

    @Test
    public void setUuidIfEmptyWithNullInput() throws Exception {
        assertTrue(DaoUtil.setUuidIfEmpty(null).isEmpty());
    }

    @Test
    public void setUuidIfEmpty() throws Exception {
        final Vpn vpn = new Vpn();
        final Vpn vpn2 = new Vpn();
        vpn.setUuid("123");
        final List<Vpn> list = new ArrayList<>();
        list.add(vpn);
        list.add(vpn2);
        assertEquals("123", DaoUtil.setUuidIfEmpty(list).get(0));
    }

}
