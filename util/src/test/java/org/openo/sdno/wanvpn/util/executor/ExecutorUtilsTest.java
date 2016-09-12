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

package org.openo.sdno.wanvpn.util.executor;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;

public class ExecutorUtilsTest {

    @Test(expected = ServiceException.class)
    public void testAssertUuidNull() throws ServiceException {
        ExecutorUtils.assertUUID(null);
    }

    @Test(expected = ServiceException.class)
    public void testAssertUuidEmpty() throws ServiceException {
        ExecutorUtils.assertUUID("");
    }

    @Test(expected = ServiceException.class)
    public void testAssertUuidTooLong() throws ServiceException {
        ExecutorUtils.assertUUID("1234567891234567891234567891234567890");
    }

    @Test
    public void testAssertUuid() throws ServiceException {
        ExecutorUtils.assertUUID("test-right");
        assertTrue(true);
    }

}
