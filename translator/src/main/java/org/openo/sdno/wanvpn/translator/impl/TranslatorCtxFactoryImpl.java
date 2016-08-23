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

package org.openo.sdno.wanvpn.translator.impl;

import org.openo.sdno.wanvpn.translator.common.OperType;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtxFactory;
import org.springframework.stereotype.Component;

/**
 * The implement class of the translator Ctx factory.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-2
 */
@Component("translatorCtxFactory")
public class TranslatorCtxFactoryImpl implements TranslatorCtxFactory {

    @Override
    public TranslatorCtx getTranslatorCtx(final OperType operType) {
        final TranslatorCtx ctx = new TranslatorCtxImpl();
        ctx.setOperType(operType);
        return ctx;
    }
}
