/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
import java.util.List;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.wanvpn.util.checker.IntegerChecker;

public class IntegerCheckerTest {

    @Test
    public void testCheckIntegerNull() throws ServiceException, NoSuchFieldException, SecurityException {
        MockSvcModel svcModelTemp = new MockSvcModel();
        svcModelTemp.setNumber(5);
        Field fieldTemp = svcModelTemp.getClass().getDeclaredField("str");
        IntegerChecker.checkInteger(svcModelTemp, fieldTemp);
    }

    @Test
    public void testCheckIntegerTypeException() throws NoSuchFieldException, SecurityException {
        MockSvcModel svcModelTemp = new MockSvcModel();
        svcModelTemp.setNumberStr(null);
        Field fieldTemp = svcModelTemp.getClass().getDeclaredField("numberStr");
        try {
            IntegerChecker.checkInteger(svcModelTemp, fieldTemp);
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckIntegerLongDefault() throws ServiceException, NoSuchFieldException, SecurityException {
        MockSvcModel svcModelTemp = new MockSvcModel();
        svcModelTemp.setNumber(0);
        Field fieldTemp = svcModelTemp.getClass().getDeclaredField("number");
        IntegerChecker.checkInteger(svcModelTemp, fieldTemp);
    }

    @Test
    public void testCheckIntegerLongSucess() throws ServiceException, NoSuchFieldException, SecurityException {
        MockSvcModel svcModelTemp = new MockSvcModel();
        svcModelTemp.setNumber(5);
        Field fieldTemp = svcModelTemp.getClass().getDeclaredField("number");
        IntegerChecker.checkInteger(svcModelTemp, fieldTemp);
    }

    @Test
    public void testCheckIntegerLongOut() throws NoSuchFieldException, SecurityException {
        MockSvcModel svcModelTemp = new MockSvcModel();
        svcModelTemp.setNumber(11);
        Field fieldTemp = svcModelTemp.getClass().getDeclaredField("number");
        try {
            IntegerChecker.checkInteger(svcModelTemp, fieldTemp);
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckIntegerLongLess() throws NoSuchFieldException, SecurityException {
        MockSvcModel svcModelTemp = new MockSvcModel();
        svcModelTemp.setNumber(-1);
        Field fieldTemp = svcModelTemp.getClass().getDeclaredField("number");
        try {
            IntegerChecker.checkInteger(svcModelTemp, fieldTemp);
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckIntIntegerDefault() throws ServiceException, NoSuchFieldException, SecurityException {
        MockSvcModel svcModelTemp = new MockSvcModel();
        svcModelTemp.setIntNumber(0);
        Field fieldTemp = svcModelTemp.getClass().getDeclaredField("intNumber");
        IntegerChecker.checkInteger(svcModelTemp, fieldTemp);
    }

    @Test
    public void testCheckIntegerIntegerDefault() throws ServiceException, NoSuchFieldException, SecurityException {
        MockSvcModel svcModelTemp = new MockSvcModel();
        svcModelTemp.setNumberInteger(0);
        Field fieldTemp = svcModelTemp.getClass().getDeclaredField("numberInteger");
        IntegerChecker.checkInteger(svcModelTemp, fieldTemp);
    }

    @Test
    public void testCheckIntegerIntegerSuccess() throws ServiceException, NoSuchFieldException, SecurityException {
        MockSvcModel svcModelTemp = new MockSvcModel();
        svcModelTemp.setNumberInteger(5);
        Field fieldTemp = svcModelTemp.getClass().getDeclaredField("numberInteger");
        IntegerChecker.checkInteger(svcModelTemp, fieldTemp);
    }

    @Test
    public void testCheckIntegerIntegerOut() throws NoSuchFieldException, SecurityException {
        MockSvcModel svcModelTemp = new MockSvcModel();
        svcModelTemp.setNumberInteger(11);
        Field fieldTemp = svcModelTemp.getClass().getDeclaredField("numberInteger");
        try {
            IntegerChecker.checkInteger(svcModelTemp, fieldTemp);
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckIntegerIntegerLess() throws NoSuchFieldException, SecurityException {
        MockSvcModel svcModelTemp = new MockSvcModel();
        svcModelTemp.setNumberInteger(-1);
        Field fieldTemp = svcModelTemp.getClass().getDeclaredField("numberInteger");
        try {
            IntegerChecker.checkInteger(svcModelTemp, fieldTemp);
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckIntegerList() throws NoSuchFieldException, SecurityException, ServiceException {
        MockSvcModel svcModelTemp = new MockSvcModel();
        List<Integer> numList = new ArrayList<Integer>();
        numList.add(0);
        svcModelTemp.setNumList(numList);
        Field fieldTemp = svcModelTemp.getClass().getDeclaredField("numList");
        IntegerChecker.checkInteger(svcModelTemp, fieldTemp);
    }

    @Test
    public void testCheckIntegerListException() throws NoSuchFieldException, SecurityException {
        MockSvcModel svcModelTemp = new MockSvcModel();
        List<MockSubSvcModel> numberList = new ArrayList<MockSubSvcModel>();
        MockSubSvcModel mockSubSvcModel = new MockSubSvcModel();
        mockSubSvcModel.setStr("String");
        numberList.add(mockSubSvcModel);
        svcModelTemp.setNumberList(numberList);
        Field fieldTemp = svcModelTemp.getClass().getDeclaredField("numberList");
        try {
            IntegerChecker.checkInteger(svcModelTemp, fieldTemp);
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }
}
