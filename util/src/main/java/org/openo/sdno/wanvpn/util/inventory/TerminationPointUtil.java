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

package org.openo.sdno.wanvpn.util.inventory;

import org.openo.sdno.model.servicemodel.brs.LtpMO;

/**
 * Termination point util class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-1
 */
public class TerminationPointUtil {

    private TerminationPointUtil() {
    }

    /**
     * Check input LTP object is sub ETH interface or not.<br>
     * 
     * @param ltpMO LTP module object
     * @return true when ltpMO's logical type equals ETH and the ltpMO's name matches regular
     *         expression,otherwise is false
     * @since SDNO 0.5
     */
    public static boolean isSubEthInterface(final LtpMO ltpMO) {
        if(null == ltpMO) {
            return false;
        }
        return "ETH".equals(ltpMO.getLogicalType()) && ltpMO.getName().matches(".*?\\.\\d+");
    }

    /**
     * Check input LTP object is loop back interface or not.<br>
     * 
     * @param ltpMO LTP module object
     * @return true when ltpMO's logical type equals Loopback,otherwise is false
     * @since SDNO 0.5
     */
    public static boolean isLoopbackInterface(final LtpMO ltpMO) {
        if(null == ltpMO) {
            return false;
        }
        return "Loopback".equals(ltpMO.getLogicalType());
    }
}
