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

package org.openo.sdno.wanvpn.inventory.sdk.inf;

import java.util.List;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.servicemodel.brs.ControllerMO;

/**
 * Controller inventory data access object interface.<br>
 * 
 * @param <T> BaseMO
 * @author
 * @version SDNO 0.5 2016-6-3
 */
public interface IControllerInvDao<T> {

    /**
     * Query all MOs.<br>
     * 
     * @return query result
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    List<T> queryAllMOs() throws ServiceException;

    /**
     * Query MO info by ID.<br>
     * 
     * @param uuid UUID
     * @return query result
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    T queryMOById(String uuid) throws ServiceException;

    /**
     * Query controller info. <br>
     * 
     * @param neId NE ID
     * @return query result
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    ControllerMO queryController(String neId) throws ServiceException;
}
