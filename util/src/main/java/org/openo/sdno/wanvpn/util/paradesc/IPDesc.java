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

package org.openo.sdno.wanvpn.util.paradesc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User-defined JAVA annotation, IPDesc.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IPDesc {

    /**
     * The IP address has mask or not.<br>
     * 
     * @return boolean
     * @since SDNO 0.5
     */
    boolean hasMask() default true;

    /**
     * IP type.<br>
     * 
     * @return IP type
     * @since SDNO 0.5
     */
    IPType ipType() default IPType.IPV4;

    /**
     * IP type enumeration.<br>
     * 
     * @author
     * @version SDNO 0.5 2016-6-6
     */
    public static enum IPType {
        IPV4, IPV4_A, IPV4_B, IPV4_C, IPV4_D, IPV4_E, IPV4_ABC
    }
}
