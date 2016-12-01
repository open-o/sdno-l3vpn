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

/**
 * Enumeration in data model should have an "alias", a name used in serialization. <br>
 * <p>
 * All data model enumeration should override toString() method to return alias. Alias could be a
 * more user-friendly name. This is designed primarily for use in specialized situations where
 * correctness depends on getting the exact name, which will not vary from release to release.
 * </p>
 *
 * @author zhaozhongchao@huawei.com
 * @version SDNO 0.5 Dec 1, 2016
 */
public interface ModelEnum {

    String getAlias();

}
