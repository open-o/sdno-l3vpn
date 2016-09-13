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

package org.openo.sdno.wanvpn.util.query;

import static org.springframework.util.StringUtils.hasLength;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.commons.lang3.StringUtils;
import org.openo.baseservice.remoteservice.exception.ExceptionArgs;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.common.query.BatchQueryParams;
import org.openo.sdno.model.common.query.BatchQueryResult;
import org.openo.sdno.model.servicemodel.mss.QueryComplexParams;
import org.openo.sdno.wanvpn.util.constans.PageQueryConstants;
import org.openo.sdno.wanvpn.util.error.CommonErrorCode;
import org.openo.sdno.wanvpn.util.error.ServiceExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

/**
 * Batch query util class.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-1
 */
public class BatchQueryUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchQueryUtils.class);

    private static final int MAX_PAGE_SIZE = 1000;

    private static final int MIN_PAGE_SIZE = 0;

    private static final int MIN_PAGE_INDEX = 0;

    private BatchQueryUtils() {
    }

    /**
     * Get batch query parameters from HTTP context.<br/>
     * 
     * @param request HttpServlet request
     * @return batch query parameters
     * @throws ServiceException when operate failed
     * @since SDNO 0.5
     */
    public static BatchQueryParams getBatchQueryParams(@Context HttpServletRequest request) throws ServiceException {
        if(null == request) {
            throw ServiceExceptionUtil.getBadRequestServiceException(CommonErrorCode.CHECK_HTTP_CONTEXT_IS_NULL);
        }

        final Map<String, String[]> parameterMap = request.getParameterMap();
        return doGetBatchQueryParams(parameterMap);
    }

    private static BatchQueryParams doGetBatchQueryParams(final Map<String, String[]> parameterMap)
            throws ServiceException {
        final String queryType = getValue(PageQueryConstants.QUERYTYPE, parameterMap);
        final String attr = getValue(PageQueryConstants.ATTR, parameterMap);
        final String sort = getValue(PageQueryConstants.SORT, parameterMap);
        final String sortType = getValue(PageQueryConstants.SORTTYPE, parameterMap);
        final String pageNumberStr = getValue(PageQueryConstants.PAGENUMBER, parameterMap);
        final String pageSizeStr = getValue(PageQueryConstants.PAGESIZE, parameterMap);

        validatePageQueryParams(pageNumberStr, pageSizeStr);
        final BatchQueryParams rQueryParams = new BatchQueryParams(attr, sort, queryType, sortType);

        int pageNumber = MIN_PAGE_INDEX;

        if(hasLength(pageNumberStr) && StringUtils.isNumeric(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
            if(pageNumber > MIN_PAGE_INDEX) {
                rQueryParams.setPageNumber(pageNumber);
            }
        }

        int pageSize = MIN_PAGE_SIZE;
        if(hasLength(pageSizeStr) && StringUtils.isNumeric(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
            if(pageSize >= MIN_PAGE_SIZE && pageSize <= MAX_PAGE_SIZE) {
                rQueryParams.setPageSize(pageSize);
            }
        }

        return rQueryParams;
    }

    private static String getValue(final String key, final Map<String, String[]> map) {
        final String[] values = map.get(key);
        String value = null;
        if(null != values && values.length > 0) {
            value = values[0];
        }
        return value;
    }

    /**
     * When do fake paging, create result constructor according to all data queried out.<br/>
     * 
     * @param datas data queried out
     * @param batchQueryParams batchQueryParams object
     * @return Batch query result
     * @since SDNO 0.5
     */
    public static <T> BatchQueryResult<T> getFakeBatchQueryResult(final List<T> datas,
            final BatchQueryParams batchQueryParams) {
        if(CollectionUtils.isEmpty(datas)) {
            final List<T> emptyList = Collections.emptyList();
            return new BatchQueryResult<>(emptyList, emptyList.size());
        }

        final int curPage = batchQueryParams.getPageNumber();
        final int pageSize = batchQueryParams.getPageSize();
        final int totalNum = datas.size();

        final int beginIndex = curPage * pageSize;
        final int endindex = beginIndex + Math.min(pageSize - 1, totalNum - beginIndex - 1);

        if(beginIndex >= totalNum || endindex >= totalNum) {
            final List<T> emptyList = Collections.emptyList();
            return new BatchQueryResult<>(emptyList, emptyList.size());
        }

        return new BatchQueryResult<>(datas.subList(beginIndex, endindex + 1), totalNum);
    }

    /**
     * Convert BatchQueryParams to QueryComplexParams.<br/>
     * 
     * @param batchQueryParams BatchQueryParams object
     * @return QueryComplexParams object
     * @since SDNO 0.5
     */
    public static QueryComplexParams getQueryComplexParam(final BatchQueryParams batchQueryParams) {
        final QueryComplexParams complexParams = new QueryComplexParams();

        final String sort = batchQueryParams.getSort();
        if(StringUtils.isNotEmpty(sort)) {
            setSortParam(Collections.singletonList(sort), complexParams);
        }

        setPageParam(batchQueryParams, complexParams);
        setFilterParam(batchQueryParams.getFilterDesc(), batchQueryParams.getBusinessParams(), complexParams);
        final String attr = batchQueryParams.getAttr();
        if(StringUtils.isNotEmpty(attr)) {
            setFieldsParam(Collections.singletonList(attr), complexParams);
        }

        return complexParams;
    }

    private static void setFieldsParam(final List<String> fields, final QueryComplexParams complexParams) {
        complexParams.setFields(fields);
    }

    private static void setSortParam(final List<String> sortAttrs, final QueryComplexParams complexParams) {
        complexParams.setSort(sortAttrs);
    }

    private static void setFilterParam(final String filterDsc, final Map<String, Object> filterData,
            final QueryComplexParams complexParams) {
        complexParams.setFilterDsc(filterDsc);
        complexParams.setFilterData(filterData);
    }

    private static void setPageParam(final BatchQueryParams batchQueryParams, final QueryComplexParams complexParams) {
        complexParams.setPageNumber(batchQueryParams.getPageNumber());
        complexParams.setPageSize(batchQueryParams.getPageSize());
    }

    private static void validatePageQueryParams(final String pageNumberStr, final String pageCapacityStr)
            throws ServiceException {
        final String[] zeroArgs = new String[] {"pageNumber is invalid, it must be an integer more or equals to zero"};
        final ExceptionArgs args = new ExceptionArgs();

        final String[] sizeArgs =
                new String[] {"pageSize is invalid, it must be an integer less than  or equals to 1000"};

        if(pageNumberStr == null && pageCapacityStr == null) {
            return;
        }
        if(!hasLength(pageNumberStr) || !StringUtils.isNumeric(pageNumberStr)) {

            args.setDetailArgs(zeroArgs);
            throw ServiceExceptionUtil.getBadRequestServiceException(CommonErrorCode.CHECKER_PAGENUMBER_IS_INVALID,
                    args);

        }

        if(!hasLength(pageCapacityStr) || !StringUtils.isNumeric(pageCapacityStr)) {
            throw ServiceExceptionUtil.getBadRequestServiceException(CommonErrorCode.CHECKER_PAGECAPACITY_IS_INVALID);

        }

        BigInteger pageNumberInteger = new BigInteger(pageNumberStr);

        if(pageNumberInteger.compareTo(BigInteger.ZERO) < 0
                || pageNumberInteger.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0) {
            throw ServiceExceptionUtil.getBadRequestServiceException(CommonErrorCode.CHECKER_PAGENUMBER_IS_INVALID,
                    args);
        }

        BigInteger bigInteger = new BigInteger(pageCapacityStr);
        BigInteger maxSize = BigInteger.valueOf(MAX_PAGE_SIZE);
        if(bigInteger.compareTo(maxSize) > 0 || bigInteger.compareTo(BigInteger.ZERO) <= 0) {
            args.setDescArgs(sizeArgs);
            throw ServiceExceptionUtil.getBadRequestServiceException(CommonErrorCode.CHECKER_PAGESZIE_IS_INVALID, args);
        }

    }

}
