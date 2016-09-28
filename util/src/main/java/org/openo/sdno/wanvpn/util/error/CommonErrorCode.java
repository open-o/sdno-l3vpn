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

package org.openo.sdno.wanvpn.util.error;

/**
 * Common error code class.<br>
 * 
 * @author
 * @version SDNO 0.5 Aug 2, 2016
 */
public class CommonErrorCode {

    public static final String APP = "wanvpncommon";

    public static final String SRC_ENUM = "enum";

    public static final String SRC_FIELD = "field";

    public static final String SRC_STRING = "string";

    public static final String SRC_IP = "ip";

    public static final String APP_NAME = "singlevpnsvc";

    /**
     * the value of Enum is out of range.
     */
    public static final String CHECKER_ENUM_OUT_OF_RANGE = ErrorCodeUtils.getErrorCode(APP, SRC_ENUM, "out_of_range");

    /**
     * The field value should not be null.
     */
    public static final String CHECKER_FILED_IS_NULL = ErrorCodeUtils.getErrorCode(APP, SRC_FIELD, "field_is_null");

    /**
     * The value of IP is not a valid ip address without mask.
     */
    public static final String CHECKER_IP_INVALID = ErrorCodeUtils.getErrorCode(APP, SRC_IP, "ip_invalid");

    /**
     * The value of CIDR is not a valid ip address with mask.
     */
    public static final String CHECKER_CIDR_INVALID = ErrorCodeUtils.getErrorCode(APP, SRC_IP, "cidr_invalid");

    /**
     * String can not be blank.
     */
    public static final String CHECKER_STRING_IS_BLANK =
            ErrorCodeUtils.getErrorCode(APP, SRC_STRING, "string_is_blank");

    /**
     * String can not be empty.
     */
    public static final String CHECKER_STRING_IS_EMPTY =
            ErrorCodeUtils.getErrorCode(APP, SRC_STRING, "string_is_empty");

    /**
     * the value of String does not match the pattern.
     */
    public static final String CHECKER_STRING_IS_INVALID =
            ErrorCodeUtils.getErrorCode(APP, "scopechecker", "string_is_invalid");

    /**
     * the value of String's length is out of range.
     */
    public static final String CHECKER_STRING_OVER_LENGTH =
            ErrorCodeUtils.getErrorCode(APP, SRC_STRING, "string_over_length");

    /**
     * The field in Scope is unsupported.
     */
    public static final String CHECKER_UNSUPPORT_FIELD_TYPE =
            ErrorCodeUtils.getErrorCode(APP, "scopechecker", "field_type_unsupport");

    /**
     * The VLAN scope is not valid.
     */
    public static final String CHECKER_VLAN_SCOPE_INVALID =
            ErrorCodeUtils.getErrorCode(APP, "vlanscope", "vlanscope_invalid");

    /**
     * UUID is invalid.
     */
    public static final String UUID_INVALID = ErrorCodeUtils.getErrorCode(APP, "uuid", "uuid_invalid");

    /**
     * It is not a A type IP address.
     */
    public static final String CHECK_IP_NOT_A_CLASS = ErrorCodeUtils.getErrorCode(APP, SRC_IP, "ip_not_a_class");

    /**
     * It is not a B type IP address.
     */
    public static final String CHECK_IP_NOT_B_CLASS = ErrorCodeUtils.getErrorCode(APP, SRC_IP, "ip_not_b_class");

    /**
     * It is not a C type IP address.
     */
    public static final String CHECK_IP_NOT_C_CLASS = ErrorCodeUtils.getErrorCode(APP, SRC_IP, "ip_not_c_class");

    /**
     * It is not a D type IP address.
     */
    public static final String CHECK_IP_NOT_D_CLASS = ErrorCodeUtils.getErrorCode(APP, SRC_IP, "ip_not_d_class");

    /**
     * It is not a E type IP address.
     */
    public static final String CHECK_IP_NOT_E_CLASS = ErrorCodeUtils.getErrorCode(APP, SRC_IP, "ip_not_e_class");

    /**
     * It is not a ABC type IP address.
     */
    public static final String CHECK_IP_NOT_ABC_CLASS = ErrorCodeUtils.getErrorCode(APP, SRC_IP, "ip_not_abc_class");

    public static final String CHECK_HTTP_CONTEXT_IS_NULL =
            ErrorCodeUtils.getErrorCode(APP, "http_context", "http_context_is_null");

    public static final String SITE_NOT_BIND_WITH_NE = ErrorCodeUtils.getErrorCode(APP, "site", "not_bind_with_ne");

    /**
     * sort type can not be empty.
     */
    public static final String CHECKER_PAGENUMBER_IS_INVALID =
            ErrorCodeUtils.getErrorCode(APP, "pageNumber", "in_valid");

    public static final String CHECKER_PAGESZIE_IS_INVALID = ErrorCodeUtils.getErrorCode(APP, "pageSize", "in_valid");

    /**
     * page Capacity can not be empty.
     */
    public static final String CHECKER_PAGECAPACITY_IS_INVALID =
            ErrorCodeUtils.getErrorCode(APP, "pageCapacity", "is_invalid");

    public static final String JSON_FORMAT_ERROR = ErrorCodeUtils.getErrorCode(APP, "json", "format_error");

    public static final String VPN_NOT_EXIST =
            ErrorCodeUtils.getErrorCode(CommonErrorCode.APP_NAME, "vpn", "vpn_not_exist");

    private CommonErrorCode() {

    }
}
