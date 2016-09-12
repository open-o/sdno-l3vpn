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

package org.openo.sdno.wanvpn.translator.common;

import org.junit.Test;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.wanvpn.translator.common.OperType;
import org.openo.sdno.wanvpn.translator.impl.TranslatorCtxImpl;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;

import junit.framework.Assert;

public class TranslatorCtxTest {

    @Test
    public void test() {
        TranslatorCtx ctx = new TranslatorCtxImpl();
        Vpn vpn = new Vpn();
        String key = "vpn";
        String name = "name";
        vpn.setName(name);
        ctx.addVal(key, vpn);

        Assert.assertEquals(((Vpn)ctx.getVal(key)).getName(), name);

        ctx.setOperType(OperType.CREATE);
        Assert.assertEquals(ctx.getOperType(), OperType.CREATE);
    }
}
