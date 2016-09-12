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
import org.openo.sdno.wanvpn.util.checker.StringChecker;

public class StringCheckerTest {

    @Test
    public void testCheckLengthNull() throws ServiceException {
        StringChecker.checkLength(1, 10, null, "testName");
    }

    @Test
    public void testCheckLengthException1() {
        try {
            StringChecker.checkLength(1, 10, "", "testName");
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckLengthException2() {
        try {
            StringChecker.checkLength(1, 10, "testNameOutRange", "testName");
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckLength() throws ServiceException {
        StringChecker.checkLength(1, 10, "test", "testName");
    }

    @Test
    public void testCheckNotBlankNull() {
        try {
            StringChecker.checkNotBlank(null, "testName");
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckNotBlankIsBlank() {
        try {
            StringChecker.checkNotBlank("  ", "testName");
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckNotBlankEmpty() {
        try {
            StringChecker.checkNotBlank("", "testName");
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckNotBlank() throws ServiceException {
        StringChecker.checkNotBlank("test", "testName");
    }

    @Test
    public void testCheckNotEmptyNull() {
        try {
            StringChecker.checkNotEmpty(null, "testName");
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckNotEmptyIsEmpty() {
        try {
            StringChecker.checkNotEmpty("", "testName");
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckNotEmpty() throws ServiceException {
        StringChecker.checkNotEmpty("test", "testName");
    }

    @Test
    public void testCheckPatternValueNull() throws ServiceException {
        StringChecker.checkPattern("[A-Z][A-Za-z]*", null, "testName");
    }

    @Test
    public void testCheckPatternBlank() throws ServiceException {
        StringChecker.checkPattern("", "test", "testName");
    }

    @Test
    public void testCheckPatternException() {
        try {
            StringChecker.checkPattern("[A-Z][A-Za-z]*", "test", "testName");
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckPattern() throws ServiceException {
        StringChecker.checkPattern("[A-Z][A-Za-z]*", "Test", "testName");
    }

    @Test
    public void testCheckStirng() throws ServiceException, NoSuchFieldException, SecurityException {
        MockSvcModel svcModelMO = new MockSvcModel();
        svcModelMO.setStr("TestString");
        Field fieldTemp = svcModelMO.getClass().getDeclaredField("str");
        StringChecker.checkStirng(svcModelMO, fieldTemp);
    }

    @Test
    public void testCheckStirngNull() throws ServiceException, NoSuchFieldException, SecurityException {
        MockSvcModel svcModelMO = new MockSvcModel();
        Field fieldTemp = svcModelMO.getClass().getDeclaredField("number");
        StringChecker.checkStirng(svcModelMO, fieldTemp);
    }

    @Test
    public void testCheckStirngListException() throws NoSuchFieldException, SecurityException {
        MockSvcModel svcModelMO = new MockSvcModel();
        List<MockSubSvcModel> strList = new ArrayList<MockSubSvcModel>();
        MockSubSvcModel mockSubSvcModel = new MockSubSvcModel();
        mockSubSvcModel.setStr("String");
        strList.add(mockSubSvcModel);
        svcModelMO.setStrList(strList);
        Field fieldTemp = svcModelMO.getClass().getDeclaredField("strList");
        try {
            StringChecker.checkStirng(svcModelMO, fieldTemp);
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckStirngNumberException() throws NoSuchFieldException, SecurityException {
        MockSvcModel svcModelMO = new MockSvcModel();
        Field fieldTemp = svcModelMO.getClass().getDeclaredField("strNumber");
        try {
            StringChecker.checkStirng(svcModelMO, fieldTemp);
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckStirngList() throws NoSuchFieldException, SecurityException, ServiceException {
        MockSvcModel svcModelMO = new MockSvcModel();
        List<String> strList = new ArrayList<String>();
        strList.add("String");
        svcModelMO.setStrListStr(strList);
        Field fieldTemp = svcModelMO.getClass().getDeclaredField("strListStr");
        StringChecker.checkStirng(svcModelMO, fieldTemp);
    }

    @Test
    public void testCheckStirngListNull() throws NoSuchFieldException, SecurityException, ServiceException {
        MockSvcModel svcModelMO = new MockSvcModel();
        Field fieldTemp = svcModelMO.getClass().getDeclaredField("strListStr");
        StringChecker.checkStirng(svcModelMO, fieldTemp);
    }

}
