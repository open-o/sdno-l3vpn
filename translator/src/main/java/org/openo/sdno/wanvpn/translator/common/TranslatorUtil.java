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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openo.sdno.model.servicemodel.common.enumeration.AdminStatus;
import org.springframework.util.StringUtils;

/**
 * The tool class of translator.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-2
 */
public final class TranslatorUtil {

    private TranslatorUtil() {

    }

    /**
     * Get the ip except the mask.<br/>
     * 
     * @param ipWithMask The ip with mask
     * @return The ip except the mask
     * @since SDNO 0.5
     */
    public static String dropIPMask(String ipWithMask) {
        String ip = ipWithMask;
        if(ipWithMask != null && !ipWithMask.isEmpty()) {
            final int index = ipWithMask.indexOf('/');
            ip = index > 0 ? ipWithMask.substring(0, index) : ipWithMask;
        }
        return ip;
    }

    /**
     * Translate the list of vlan from string to integer.<br/>
     * 
     * @param strVlan The list of string vlan
     * @return The list of integer vlan
     * @since SDNO 0.5
     */
    public static List<Integer> stringVlan2IntVlanlst(String strVlan) {
        if(!StringUtils.hasLength(strVlan)) {
            return null;
        }
        String[] vlanScops = strVlan.split(",");
        List<Integer> vlanlst = new ArrayList<Integer>();
        for(String vlanScop : vlanScops) {
            try {
                vlanlst.addAll(TranslatorUtil.vlanScope2Vlanlst(vlanScop));
            } catch(NumberFormatException e) {
                throw new IllegalArgumentException("Vlan Scop:" + vlanScop + " is invalid", e);
            }
        }
        Collections.sort(vlanlst);
        return vlanlst;
    }

    private static List<Integer> vlanScope2Vlanlst(String strVlanScop) {
        List<Integer> vlans = new ArrayList<Integer>();
        if(!StringUtils.hasLength(strVlanScop.trim())) {
            return vlans;
        }
        if(strVlanScop.indexOf('-') < 0) {
            vlans.add(Integer.parseInt(strVlanScop.trim()));
            return vlans;
        }
        String[] strVlans = strVlanScop.split("-");
        if(strVlans.length != 2) {
            throw new IllegalArgumentException("Vlan Scop:" + strVlanScop + " is invalid");
        }
        Integer vlanBegin = Integer.parseInt(strVlans[0].trim());
        Integer vlanEnd = Integer.parseInt(strVlans[1].trim());
        if(vlanBegin >= vlanEnd) {
            throw new IllegalArgumentException("Vlan Scop:" + strVlanScop + " is invalid");
        }
        for(int vlan = vlanBegin; vlan <= vlanEnd; vlan++) {
            vlans.add(vlan);
        }
        return vlans;
    }

    /**
     * Get the admin status.<br/>
     * 
     * @param strAdminStatus The boolean object of admin status
     * @return ACTIVE's common name when strAdminStatus is true
     *         INACTIVE's common name when strAdminStatus is false
     * @since SDNO 0.5
     */
    public static String n2sAdminStatus(boolean strAdminStatus) {

        if(strAdminStatus) {
            return AdminStatus.ACTIVE.getCommonName();
        } else {
            return AdminStatus.INACTIVE.getCommonName();
        }
    }

}
