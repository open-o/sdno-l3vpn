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
 * User-defined JAVA annotation, StringDesc.<br>
 * <p>
 * Describe the length and matched pattern of String type fields.
 * </p>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StringDesc {

    /**
     * String minimum length.<br>
     * 
     * @return minimum length
     * @since SDNO 0.5
     */
    int minLen() default 0;

    /**
     * String maximum length.<br>
     * 
     * @return maximum length
     * @since SDNO 0.5
     */
    int maxLen() default 255;

    /**
     * Can be empty or not.<br>
     * 
     * @return boolean
     * @since SDNO 0.5
     */
    boolean notEmpty() default false;

    /**
     * Can be blank or not.<br>
     * 
     * @return boolean
     * @since SDNO 0.5
     */
    boolean notBlank() default false;

    /**
     * Matched pattern.<br>
     * 
     * @return pattern
     * @since SDNO 0.5
     */
    String pattern() default "";
}
