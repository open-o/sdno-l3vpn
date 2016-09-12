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

package org.openo.sdno.l3vpnservice.service.impl;

import javax.annotation.Resource;

import org.openo.sdno.l3vpnservice.dao.L3VpnTpDao;
import org.openo.sdno.l3vpnservice.service.inf.L3VpnTpModifySvcService;
import org.springframework.stereotype.Service;

/**
 * Uniform L3VPN TP modify service implement class.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-5-31
 */
@Service("uniformL3VpnTpModifySvcService")
public class UniformL3VpnTpModifySvcService implements L3VpnTpModifySvcService {

    @Resource
    private L3VpnTpDao tpDao;

    public void setTpDao(final L3VpnTpDao tpDao) {
        this.tpDao = tpDao;
    }
}
