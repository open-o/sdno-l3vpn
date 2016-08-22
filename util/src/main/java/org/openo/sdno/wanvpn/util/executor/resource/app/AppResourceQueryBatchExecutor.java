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

package org.openo.sdno.wanvpn.util.executor.resource.app;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.common.query.BatchQueryParams;
import org.openo.sdno.model.common.query.BatchQueryResult;
import org.openo.sdno.wanvpn.util.executor.ExecutorResultContainer;
import org.openo.sdno.wanvpn.util.query.BatchQueryUtils;

/**
 * APP resource batch query executor abstract class.<br/>
 * 
 * @param <R> Generic paradigm
 * @author
 * @version SDNO 0.5 2016-6-1
 */
public abstract class AppResourceQueryBatchExecutor<R> {

    private final BatchQueryParams batchQueryParams;

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     * @param request HttpServlet request
     * @throws ServiceException when failed
     */
    public AppResourceQueryBatchExecutor(@Context HttpServletRequest request) throws ServiceException {
        this.batchQueryParams = BatchQueryUtils.getBatchQueryParams(request);
    }

    protected void assembleParams(BatchQueryParams batchQueryParams) throws ServiceException {
        // Implement for assemble more parameters by subclass override this.
    }

    protected void assertParams(BatchQueryParams batchQueryParams) throws ServiceException {
        // Implement for assert parameters by subclass override this.
    }

    /**
     * Execute query.<br/>
     * 
     * @return Executor result
     * @throws ServiceException when execute failed
     * @since SDNO 0.5
     */
    public ExecutorResultContainer<BatchQueryResult<R>> execute() throws ServiceException {
        assembleParams(batchQueryParams);
        assertParams(batchQueryParams);
        ExecutorResultContainer<BatchQueryResult<R>> resultContainer =
                new ExecutorResultContainer<>(implementQuery(batchQueryParams));
        executeSuccessOperationLog(resultContainer.getResult(), Locale.ENGLISH);
        return resultContainer;
    }

    protected void executeSuccessOperationLog(BatchQueryResult<R> batchQueryResult, Locale locale)
            throws ServiceException {
    }

    protected abstract BatchQueryResult<R> implementQuery(BatchQueryParams batchQueryParams) throws ServiceException;

}
