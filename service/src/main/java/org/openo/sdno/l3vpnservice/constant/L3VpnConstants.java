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

package org.openo.sdno.l3vpnservice.constant;

/**
 * The constants class of L3VPN.<br>
 * 
 * @author
 * @version SDNO 0.5 Aug 2, 2016
 */
public class L3VpnConstants {

    public static final int TIMEOUT = 150000;

    public static final String MODULE_L3VPN = "l3vpn";

    public static final String CREATE_VPN = "/openoapi/sbi-l3vpn/v1/l3vpns";

    public static final String VPN_DELETE = "/openoapi/sbi-l3vpn/v1/l3vpns/{0}";

    public static final String VPN_QUERY = "/openoapi/sbi-l3vpn/v1/l3vpns/{0}";

    public static final String VPN_MODIFY = "/openoapi/sbi-l3vpn/v1/l3vpns/{0}";

    public static final String ADD_TP = "/openoapi/sbi-l3vpn/v1/l3vpns/{0}/acs";

    public static final String DELETE_TP = "/openoapi/sbi-l3vpn/v1/l3vpns/{0}/acs/{1}";

    public static final String ADD_ROUTE_STATIC = "/openoapi/sbi-l3vpn/v1/l3vpns/{0}/acs/{1}/static_routes";

    public static final String ADD_ROUTE_BGP = "/openoapi/sbi-l3vpn/v1/l3vpns/{0}/acs/{1}/bgp_routes";

    public static final String DELETE_STATIC_ROUTE = "/openoapi/sbi-l3vpn/v1/l3vpns/{0}/acs/{1}/static_routes";

    public static final String DELETE_BGP_ROUTE = "/openoapi/sbi-l3vpn/v1/l3vpns/{0}/acs/{1}/bgp_routes";

    public static final String VPN_ACTIVE = "/openoapi/sbi-l3vpn/v1/l3vpns/{0}";

    public static final String SBI_URL_SITE_ACTION = "/openoapi/sbi-l3vpn/v1/l3vpns/{0}/acs/{1}";

    private L3VpnConstants() {
    }
}
