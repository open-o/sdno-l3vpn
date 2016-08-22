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

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.wanvpn.util.executor.ExecutorResultContainer;

/**
 * Service resource update executor abstract class.<br/>
 * 
 * @param <P> Generic paradigm
 * @param <R> Generic paradigm
 * @author
 * @version SDNO 0.5 2016-6-1
 */
public abstract class SvcResourceUpdateExecutor<P, R> {

    private final P param;

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     * @param request HttpServlet request
     * @throws ServiceException when failed
     */
    public SvcResourceUpdateExecutor(@Context HttpServletRequest request) throws ServiceException {
        this.param = extractParam(request);
    }

    /**
     * Execute update.<br/>
     * 
     * @return Executor result
     * @throws ServiceException when execute failed
     * @since SDNO 0.5
     */
    public ExecutorResultContainer<R> execute() throws ServiceException {
        assertParam(param);
        return new ExecutorResultContainer<>(implementUpdate(param));
    }

    protected abstract void assertParam(P param) throws ServiceException;

    protected abstract R implementUpdate(P param) throws ServiceException;

    protected abstract P extractParam(@Context HttpServletRequest request) throws ServiceException;

}
