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
/**
 * 
 */

package org.openo.sdno.model.servicemodel.mss.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * User-defined JAVA annotation, MOResType.<br/>
 * <p>
 * Define MO resource name. <br/>
 * Resource name defined in MO must be correspond in im_sdnpolicy_info_model.xml: infomodel
 * name="sdnpolicy".<br/>
 * If not define MO resource name,framework will automatically consider that current MO class name
 * is resource name(include package name, '.' will be replaced as '_').
 * </p>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
@Target(value = {java.lang.annotation.ElementType.TYPE})
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Documented
public @interface MOResType {

    /**
     * Info model name.<br/>
     * 
     * @return name
     * @since SDNO 0.5
     */
    String infoModelName();
}
