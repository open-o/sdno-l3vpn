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

package org.openo.sdno.l3vpnservice.constant;

import javax.ws.rs.core.MediaType;

/**
 * The constants class of L3VPN.<br/>
 * 
 * @author
 * @version SDNO 0.5 Aug 2, 2016
 */
public class L3VpnConstants {

    public static final int TIMEOUT = 150000;

    public static final String URL = "/rest/svc/sbiadp/controller/";

    public static final String MODULE_L3VPN = "l3vpn";

    public static final String TPQOS_RESOURCE = "tpQos";

    public static final String BINDTP_RESOURCE = "bindTp";

    public static final String PROTECT_GROUP = "protectGroup";

    public static final String CTRL_CONTANT_TYPE = MediaType.APPLICATION_JSON_TYPE.getSubtype();

    public static final String DEACTIVE_TYPE = "deactivate";

    public static final String ACTIVE_TYPE = "activate";

    public static final String STATICROUTE = "staticRoute";

    public static final String OSPFROUTE = "ospfRoute";

    public static final String ISIS = "isisRoute";

    public static final String BGP = "bgpRoute";

    public static final String PROTOCOL = "protocol";

    public static final String CREATE_VPN =
            "/rest/plugin_adapter_alu_nsp/v1/controllers/{0}/united-sbi-service-l3vpn:service/l3vpns";

    public static final String VPN_DELETE =
            "/rest/plugin_adapter_alu_nsp/v1/controllers/{0}/united-sbi-service-l3vpn:service/l3vpns/l3vpn/{1}";

    public static final String VPN_QUERY =
            "/rest/plugin_adapter_alu_nsp/v1/controllers/{0}/united-sbi-service-l3vpn:service/l3vpns/l3vpn/{1}";

    public static final String VPN_MODIFY =
            "/rest/plugin_adapter_alu_nsp/v1/controllers/{0}/united-sbi-service-l3vpn:service/l3vpns/l3vpn/{1}";

    public static final String ADD_TP =
            "/rest/plugin_adapter_alu_nsp/v1/controllers/{0}/united-sbi-service-l3vpn:service/l3vpns/l3vpn/{1}/acs";

    public static final String DELETE_TP =
            "/rest/plugin_adapter_alu_nsp/v1/controllers/{0}/united-sbi-service-l3vpn:service/l3vpns/l3vpn/{1}/acs/ac/{2}";

    public static final String ADD_ROUTE_STATIC =
            "/rest/plugin_adapter_alu_nsp/v1/controllers/{0}/united-sbi-service-l3vpn:service/l3vpns/l3vpn/{1}/acs/ac/{2}/l3Access/routes/route/static/staticRoutes";

    public static final String ADD_ROUTE_BGP =
            "/rest/plugin_adapter_alu_nsp/v1/controllers/{0}/united-sbi-service-l3vpn:service/l3vpns/l3vpn/{1}/acs/ac/{2}/l3Access/routes/route/bgp/bgpRoutes";

    public static final String DELETE_STATIC_ROUTE =
            "/rest/plugin_adapter_alu_nsp/v1/controllers/{0}/united-sbi-service-l3vpn:service/l3vpns/l3vpn/{1}/acs/ac/{2}/l3Access/routes/route/static/staticRoutes/staticRoute/";

    public static final String DELETE_BGP_ROUTE =
            "/rest/plugin_adapter_alu_nsp/v1/controllers/{0}/united-sbi-service-l3vpn:service/l3vpns/l3vpn/{1}/acs/ac/{2}/l3Access/routes/route/bgp/bgpRoutes/bgpRoute/";

    public static final String VPN_ACTIVE =
            "/rest/plugin_adapter_alu_nsp/v1/controllers/{0}/united-sbi-service-l3vpn:service/l3vpns/l3vpn/{1}";

    public static final String SBI_URL_SITE_ACTION =
            "/rest/plugin_adapter_alu_nsp/v1/controllers/{0}/united-sbi-service-l3vpn:service/l3vpns/l3vpn/{1}/acs/ac/{2}";

    public static final String SBI_URL_TPQOS_MODIFY =
            "/rest/plugin_adapter_alu_nsp/v1/controllers/{0}/united-sbi-service-l3vpn:service/l3vpns/l3vpn/{1}/acs/ac/{2}";

    public static final String START_COLLECT_TASK = "/rest/svc/sbiadp/collect/acwan/v1/docollect";

    public static final String QUERY_COLLECT_TASK_STATUS = "/rest/svc/sbiadp/collect/acwan/v1/docollect/{0}";

    public static final String QUERY_COLLECT_TASK_RESULT = "/rest/svc/sbiadp/collect/acwan/v1/docollect/{0}/result";

    public static final String IPV4PING = "ipv4ping";

    private L3VpnConstants() {
    }
}
