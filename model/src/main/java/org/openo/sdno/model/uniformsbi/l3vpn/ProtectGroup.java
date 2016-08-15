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

package org.openo.sdno.model.uniformsbi.l3vpn;

/**
 * Protect group class.<br/>
 * 
 * @author
 * @version SDNO 0.5 Jul 22, 2016
 */
public class ProtectGroup {

    private String masterAc;

    private String backupAc;

    private Vrrp vrrp;

    public String getMasterAc() {
        return masterAc;
    }

    public void setMasterAc(String masterAc) {
        this.masterAc = masterAc;
    }

    public String getBackupAc() {
        return backupAc;
    }

    public void setBackupAc(String backupAc) {
        this.backupAc = backupAc;
    }

    public Vrrp getVrrp() {
        return vrrp;
    }

    public void setVrrp(Vrrp vrrp) {
        this.vrrp = vrrp;
    }

}
