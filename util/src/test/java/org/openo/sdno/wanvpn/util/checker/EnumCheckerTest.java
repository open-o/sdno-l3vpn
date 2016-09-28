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

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.wanvpn.util.checker.EnumChecker;

/**
 * EnumChecker test class.<br>
 *
 * @author
 * @version SDNO 0.5 2016-6-7
 */
public class EnumCheckerTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testCheckEnum1() throws NoSuchFieldException, SecurityException {
        Vpn vpn = new Vpn();
        vpn.setOperStatus("UP");
        Field field = vpn.getClass().getDeclaredField("operStatus");
        try {
            EnumChecker.checkEnum(vpn, field);
        } catch(ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCheckEnum2() throws NoSuchFieldException, SecurityException {
        Vpn vpn = new Vpn();
        vpn.setOperStatus("ERR");
        Field field = vpn.getClass().getDeclaredField("operStatus");
        try {
            EnumChecker.checkEnum(vpn, field);
        } catch(ServiceException e) {
            assertEquals("wanvpncommon.enum.out_of_range", e.getId());
        }
    }

}
