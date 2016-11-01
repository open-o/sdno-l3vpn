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

package org.openo.sdno.wanvpn.util;

/**
 * <br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Oct 30, 2016
 */
public class TranslateChecker {

    public static String check(String original) {
        String result = original;
        // AdminStatus
        result = result.replace("ADMIN_UP", "adminUp");
        result = result.replace("ADMIN_DOWN", "adminDown");

        // OperStatus
        result = result.replace("OPERATE_UP", "operateUp");
        result = result.replace("OPERATE_DOWN", "operateDown");

        // AccessAction
        result = result.replace("KEEP", "keep");
        result = result.replace("PUSH", "push");
        result = result.replace("POP", "pop");
        result = result.replace("SWAP", "swap");

        // CtrlWordType
        result = result.replace("DISABLE", "disable");
        result = result.replace("ENABLE", "enable");

        // SignalType
        result = result.replace("STATIC", "static");
        result = result.replace("LDP", "ldp");
        result = result.replace("RSVP_TE", "rsvp-te");

        // RouteType
        result = result.replace("STATIC", "static");
        result = result.replace("BGP", "bgp");

        // TopologyType
        result = result.replace("FULL_MESH", "full-mesh");
        result = result.replace("POINT_TO_MULTIPOINT", "point_to_multipoint");
        result = result.replace("POINT_TO_POINT", "point-to-point");
        result = result.replace("SINGLEPOINT", "singlePoint");
        result = result.replace("HUB_SPOKE", "hubSpoke");

        return result;
    }

}
