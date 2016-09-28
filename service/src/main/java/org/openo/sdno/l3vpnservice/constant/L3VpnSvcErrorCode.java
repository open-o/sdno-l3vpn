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
 * The constants of L3VPN service error code.<br>
 * 
 * @author
 * @version SDNO 0.5 Aug 2, 2016
 */
public class L3VpnSvcErrorCode {

    public static final String APP_NAME = "l3vpnsvc";

    public static final String VPN_NOT_EXIST = ErrorCodeUtils.getErrorCode(APP_NAME, "vpn", "vpn_not_exist");

    public static final String TP_NOT_EXIST = ErrorCodeUtils.getErrorCode(APP_NAME, "tp", "tp_not_exist");

    public static final String L3VPN_NAME_DUPLICATE =
            ErrorCodeUtils.getErrorCode(APP_NAME, "l3vpn", "create_name_duplicate");

    public static final String L3VPN_DELETE_ACTIVEVPN =
            ErrorCodeUtils.getErrorCode(APP_NAME, "l3vpn", "delete_active_vpn");

    public static final String L3VPN_DELETE_ACTIVETP =
            ErrorCodeUtils.getErrorCode(APP_NAME, "l3vpn", "delete_active_tp");

    public static final String L3VPN_DELETE_VPNNOTEXIST =
            ErrorCodeUtils.getErrorCode(APP_NAME, "l3vpn", "delete_vpn_not_exist");

    // Tp to be deleted not exist in db, or its bind vpn not exist.
    public static final String L3VPN_DELETE_TPNOTEXIST =
            ErrorCodeUtils.getErrorCode(APP_NAME, "l3vpn", "delete_tp_not_exist");

    public static final String L3VPN_DELETE_ONLYONETPBIND =
            ErrorCodeUtils.getErrorCode(APP_NAME, "l3vpn", "delete_only_one_tp_bind");

    public static final String L3VPN_CREATE_CONTROLLER_FAIL =
            ErrorCodeUtils.getErrorCode(APP_NAME, "l3vpn", "create_vpn_controller_fail");

    public static final String L3VPN_CREATE_TP_CONTROLLER_FAIL =
            ErrorCodeUtils.getErrorCode(APP_NAME, "l3vpn", "create_vpn_tp_controller_fail");

    public static final String L3VPN_CREATE_STATICROUTE_CONTROLLER_FAIL =
            ErrorCodeUtils.getErrorCode(APP_NAME, "l3vpn", "create_vpn_staticroute_controller_fail");

    public static final String L3VPN_DELETE_CONTROLLER_FAIL =
            ErrorCodeUtils.getErrorCode(APP_NAME, "l3vpn", "delete_vpn_controller_fail");

    public static final String L3VPN_DELETE_TP_CONTROLLER_FAIL =
            ErrorCodeUtils.getErrorCode(APP_NAME, "l3vpn", "delete_vpn_tp_controller_fail");

    public static final String L3VPN_UPDATE_STATUS_CONTROLLER_FAIL =
            ErrorCodeUtils.getErrorCode(APP_NAME, "l3vpn", "update_vpn_status_controller_fail");

    public static final String L3VPN_UPDATE_TP_STATUS_CONTROLLER_FAIL =
            ErrorCodeUtils.getErrorCode(APP_NAME, "l3vpn", "update_vpn_tp_status_controller_fail");

    public static final String L3VPN_UPDATEDESC_CONTROLLER_FAIL =
            ErrorCodeUtils.getErrorCode(APP_NAME, "l3vpn", "update_vpn_desc_controller_fail");

    public static final String L3VPN_GET_CONTROLLER_FAIL =
            ErrorCodeUtils.getErrorCode(APP_NAME, "l3vpn", "get_vpn_controller_fail");

    public static final String L3VPN_UPDATE_TP_QOS_CONTROLLER_FAIL =
            ErrorCodeUtils.getErrorCode(APP_NAME, "l3vpn", "update_tp_qos_controller_fail");

    public static final String GET_TE_CONTROLLER_FAIL =
            ErrorCodeUtils.getErrorCode(APP_NAME, "te", "get_te_controller_return_fail");

    public static final String L3VPN_CREATE_BGPROUTE_CONTROLLER_FAIL =
            ErrorCodeUtils.getErrorCode(APP_NAME, "l3vpn", "create_vpn_bgproute_controller_fail");

    public static final String L3VPN_DELETE_BGPROUTE_CONTROLLER_FAIL =
            ErrorCodeUtils.getErrorCode(APP_NAME, "l3vpn", "delete_vpn_bgproute_controller_fail");

    public static final String L3VPN_DELETE_STATICROUTE_CONTROLLER_FAIL =
            ErrorCodeUtils.getErrorCode(APP_NAME, "l3vpn", "delete_vpn_staticroute_controller_fail");

    public static final String CAR_PARA_ERROR = ErrorCodeUtils.getErrorCode(APP_NAME, "tp", "modify_qos_para_error");

    public static final String L3VPN_TPTOADD_EXIST = ErrorCodeUtils.getErrorCode(APP_NAME, "l3vpn", "tp_toadd_exist");

    public static final String L3VPN_TPID_DUPLICATED =
            ErrorCodeUtils.getErrorCode(APP_NAME, "l3vpn", "tp_toadd_duplicated");

    public static final String L3VPN_TP_NOT_SPECIFIED =
            ErrorCodeUtils.getErrorCode(APP_NAME, "l3vpn", "tp_not_specified");

    public static final String L3VPN_TP_PGS_CONFLICT =
            ErrorCodeUtils.getErrorCode(APP_NAME, "l3vpn", "tp_pgs_conflict");

    public static final String L3VPN_TP_ID_INVALID = ErrorCodeUtils.getErrorCode(APP_NAME, "l3vpn", "tp_id_invalid");

    private L3VpnSvcErrorCode() {
    }
}
