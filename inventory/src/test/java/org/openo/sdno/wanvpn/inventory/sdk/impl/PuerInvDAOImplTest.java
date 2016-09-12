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

package org.openo.sdno.wanvpn.inventory.sdk.impl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.wanvpn.inventory.sdk.impl.PuerInvDAOImpl;
import org.openo.sdno.wanvpn.inventory.sdk.impl.nbi.PuerInvServiceNbiBean;
import org.openo.sdno.wanvpn.inventory.sdk.result.ResultRsp;

import mockit.Mock;
import mockit.MockUp;

public class PuerInvDAOImplTest {

    @Test
    public void testAdd() throws ServiceException {
        PuerInvDAOImpl<TestMo> demo = new PuerInvDAOImpl<TestMo>();
        new MockUp<PuerInvServiceNbiBean>() {

            @Mock
            public List<Map<String, Object>> add(final String resType, final List listMapValue) {
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("id", "value1");
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("id", "");
                Map<String, Object> map3 = new HashMap<String, Object>();
                map3.put("id", null);
                List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
                result.add(map1);
                result.add(map2);
                result.add(map3);
                return result;
            }

        };
        List<TestMo> moList = new ArrayList<TestMo>();
        TestMo mo1 = new TestMo();
        mo1.setId("uuid1");
        TestMo mo2 = new TestMo();
        mo2.setId("uuid2");
        TestMo mo3 = new TestMo();
        mo3.setId("uuid3");
        moList.add(mo1);
        moList.add(mo2);
        moList.add(mo3);
        demo.setPuerObjInvService(new PuerInvServiceNbiBean());
        ResultRsp<List<String>> result = demo.add(moList, TestMo.class);

        assertTrue(result.getData().get(0).equals("value1"));

    }

    @Test
    public void testAddEmpty() throws ServiceException {
        PuerInvDAOImpl<TestMo> demo = new PuerInvDAOImpl<TestMo>();
        new MockUp<PuerInvServiceNbiBean>() {

            @Mock
            public List<Map<String, Object>> add(final String resType, final List listMapValue) {
                List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
                return result;
            }

        };
        List<TestMo> moList = new ArrayList<TestMo>();
        TestMo mo1 = new TestMo();
        mo1.setId("uuid1");
        TestMo mo2 = new TestMo();
        mo2.setId("uuid2");
        TestMo mo3 = new TestMo();
        mo3.setId("uuid3");
        moList.add(mo1);
        moList.add(mo2);
        moList.add(mo3);
        demo.setPuerObjInvService(new PuerInvServiceNbiBean());
        ResultRsp<List<String>> result = demo.add(moList, TestMo.class);

        assertTrue(result.getData().isEmpty());

    }

    @Test(expected = InnerErrorServiceException.class)
    public void testDeleteUuidNull() throws ServiceException {
        PuerInvDAOImpl<TestMoFather> demo = new PuerInvDAOImpl<TestMoFather>();
        List<TestMoFather> moList = new ArrayList<TestMoFather>();
        TestMoFather mo1 = new TestMoFather();
        TestMoFather mo2 = new TestMoFather();
        TestMoFather mo3 = new TestMoFather();
        moList.add(mo1);
        moList.add(mo2);
        moList.add(mo3);
        ResultRsp<List<String>> result = demo.delete(moList, TestMoFather.class);
    }

    @Test
    public void testDeleteNormal() throws ServiceException {
        new MockUp<PuerInvServiceNbiBean>() {

            @Mock
            public void deleteOne(final String resType, final String uuid) {
                return;
            }

        };
        PuerInvDAOImpl<TestMoFather> demo = new PuerInvDAOImpl<TestMoFather>();
        List<TestMoFather> moList = new ArrayList<TestMoFather>();
        TestMo mo1 = new TestMo();
        mo1.setId("uuid1");
        TestMoWithUuid mo2 = new TestMoWithUuid();
        mo2.setId("uuid2");
        TestMo mo3 = new TestMo();
        mo3.setId("uuid3");
        moList.add(mo1);
        moList.add(mo2);
        moList.add(mo3);
        demo.setPuerObjInvService(new PuerInvServiceNbiBean());
        ResultRsp<List<String>> result = demo.delete(moList, TestMoFather.class);
        assertTrue(result.isSuccess());
    }

}
