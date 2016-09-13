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

package org.openo.sdno.wanvpn.util.error;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;

public class ServiceExceptionUtilTest {

    @Test
    public void testMGetServiceExceptionNull() {
        ServiceException resultException = ServiceExceptionUtil.getBadRequestServiceException("0");
        ServiceException expcetException = new ServiceException(String.valueOf("0"), "");
        assertEquals(resultException.getId(), expcetException.getId());
        assertEquals(resultException.getExceptionArgs(), expcetException.getExceptionArgs());
    }

    @Test
    public void testMGetServiceException() {
        ServiceException resultException = ServiceExceptionUtil.getServiceExceptionWithCommonArgs("0", null);
        assertEquals(resultException.getId(), "0");
        assertFalse(resultException.getExceptionArgs() == null);
    }
}
