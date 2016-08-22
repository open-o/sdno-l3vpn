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

package org.openo.sdno.wanvpn.util.executor.resource.svc;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.wanvpn.util.executor.ExecutorResultContainer;
import org.openo.sdno.wanvpn.util.executor.ExecutorUtils;

/**
 * Service resource single query executor abstract class.<br/>
 * 
 * @param <R> Generic paradigm
 * @author
 * @version SDNO 0.5 2016-6-1
 */
public abstract class SvcResourceQuerySingleExecutor<R> {

    private final String uuid;

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     * @param uuid UUID
     * @throws ServiceException when failed
     */
    public SvcResourceQuerySingleExecutor(final String uuid) throws ServiceException {
        this.uuid = uuid;
    }

    protected abstract R implementQuery(String uuid) throws ServiceException;

    /**
     * Execute query.<br/>
     * 
     * @return Executor result
     * @throws ServiceException when execute failed
     * @since SDNO 0.5
     */
    public ExecutorResultContainer<R> execute() throws ServiceException {
        ExecutorUtils.assertUUID(uuid);
        return new ExecutorResultContainer<>(implementQuery(uuid));
    }

}
