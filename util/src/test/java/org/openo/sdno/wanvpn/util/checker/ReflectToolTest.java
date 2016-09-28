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

package org.openo.sdno.wanvpn.util.checker;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;
import org.openo.sdno.wanvpn.util.checker.ReflectTool;

public class ReflectToolTest {

    @Test
    public void testGetAllFields() {
        List<Field> fieldtemp = new ArrayList<Field>();
        Field[] fields = ReflectTool.getAllFields(MockSubSvcModel.class);
        fieldtemp.addAll(Arrays.asList(AbstractSvcModel.class.getDeclaredFields()));
        fieldtemp.addAll(Arrays.asList(MockSubSvcModel.class.getDeclaredFields()));
        Field[] fieldArrayTemp = fieldtemp.toArray(new Field[fieldtemp.size()]);
        for(int i = 0; i < fieldArrayTemp.length; i++) {
            assertTrue(fieldArrayTemp[i].equals(fields[i]));
        }
    }

    @Test
    public void testReadVal() throws ServiceException {
        MockSubSvcModel mockSubSvcModel = new MockSubSvcModel();
        mockSubSvcModel.setNumber(0);
        long number = (long)ReflectTool.readVal(mockSubSvcModel, "number");
        assertTrue(number == 0);
    }

    @Test
    public void testReadValPdNull() throws ServiceException {
        MockSubSvcModel mockSubSvcModel = new MockSubSvcModel();
        assertTrue(ReflectTool.readVal(mockSubSvcModel, null) == null);
    }

    @Test
    public void testReadValMethodNull() {
        MockSubSvcModel mockSubSvcModel = new MockSubSvcModel();
        try {
            ReflectTool.readVal(mockSubSvcModel, "str");
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }
}
