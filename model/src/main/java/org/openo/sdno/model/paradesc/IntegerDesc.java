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

package org.openo.sdno.model.paradesc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User-defined JAVA annotation, EnumDesc.<br/>
 * <p>
 * Describe the value range of Integer,Long fields.
 * </p>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IntegerDesc {

    /**
     * Default value.<br/>
     * 
     * @return default value
     * @since SDNO 0.5
     */
    long defaultVal() default 0L;

    /**
     * Has default value or not.<br/>
     * 
     * @return boolean
     * @since SDNO 0.5
     */
    boolean hasDefault() default false;

    /**
     * Maximum value.<br/>
     * 
     * @return maximum value
     * @since SDNO 0.5
     */
    long maxVal() default Long.MAX_VALUE;

    /**
     * Minimum value.<br/>
     * 
     * @return minimum value
     * @since SDNO 0.5
     */
    long minVal() default Long.MIN_VALUE;
}
