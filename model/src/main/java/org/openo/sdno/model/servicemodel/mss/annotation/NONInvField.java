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
/**
 * 
 */

package org.openo.sdno.model.servicemodel.mss.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * User-defined JAVA annotation, NONInvField.<br>
 * <p>
 * Indicate that a field isn't inventory store field.<br>
 * Then inventory process class won't send this field to data source to store.<br>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
@Target(value = {java.lang.annotation.ElementType.FIELD})
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Documented
public @interface NONInvField {

}
