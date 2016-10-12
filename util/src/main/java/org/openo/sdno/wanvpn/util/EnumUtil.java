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

import org.openo.sdno.model.common.CommonName;

/**
 * The tool class to deal with enumeration.<br>
 * 
 * @author
 * @version SDNO 0.5 August 1, 2016
 */
public final class EnumUtil {

    private EnumUtil() {
    }

    /**
     * Get the value of enumeration.<br>
     * 
     * @param clazz The object class
     * @param commonName The common name of the field to get
     * @return The value of the enumeration
     * @since SDNO 0.5
     */
    public static <T extends Enum<T>> T valueOf(Class<T> clazz, String commonName) {
        if(commonName == null) {
            return null;
        }
        if(CommonName.class.isAssignableFrom(clazz)) {
            return valueOf(clazz.getEnumConstants(), commonName);
        } else {
            return Enum.valueOf(clazz, commonName);
        }

    }

    private static <T extends Enum<T>> T valueOf(T[] ts, String commonName) {
        if(null == ts || ts.length == 0) {
            return null;
        }

        for(T t : ts) {
            if(commonName.equals(((CommonName)t).getCommonName())) {
                return t;
            }
        }
        return null;
    }

}
