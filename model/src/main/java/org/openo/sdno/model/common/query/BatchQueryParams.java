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

package org.openo.sdno.model.common.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Batch query parameters class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-3
 */
public class BatchQueryParams {

    private static final int DEFAULT_PAGE_SIZE = 20;

    private static final int FIRST_PAGE_INDEX = 0;

    private Map<String, Object> businessParams;

    /**
     * The description of filter.
     */
    private String filterDesc;

    /**
     * The attr of resource.
     */
    private String attr;

    private String sort;

    private String sortType;

    private int pageNumber = BatchQueryParams.FIRST_PAGE_INDEX;

    private int pageSize = BatchQueryParams.DEFAULT_PAGE_SIZE;

    private String queryType;

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     * @param attr Attr to set
     * @param sort sort to set
     * @param queryType queryType to set
     * @param sortType sortType to set
     */
    public BatchQueryParams(final String attr, final String sort, final String queryType, final String sortType) {
        this();
        this.attr = attr;
        this.sort = sort;
        this.queryType = queryType;
        this.sortType = sortType;
    }

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     */
    public BatchQueryParams() {
        businessParams = new HashMap<>();
    }

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     * @param filterDesc filterDesc to set
     * @param businessParams businessParams to set
     * @param pageNumber pageNumber to set
     * @param pageSize pageSize to set
     */
    public BatchQueryParams(final String filterDesc, final Map<String, Object> businessParams, final int pageNumber,
            final int pageSize) {
        this.filterDesc = filterDesc;
        this.businessParams = businessParams;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     * @param cond cond to set
     * @param value value to set
     */
    public BatchQueryParams(final String cond, final Object value) {
        final Map<String, Object> para = new HashMap<>(1);
        para.put(cond, value);
        businessParams = para;
        pageSize = BatchQueryParams.DEFAULT_PAGE_SIZE;
        pageNumber = BatchQueryParams.FIRST_PAGE_INDEX;
    }

    /**
     * Add new data to businessParams.<br>
     * 
     * @param key The key
     * @param value The value
     * @since SDNO 0.5
     */
    public void addBusinessParam(final String key, final Object value) {
        businessParams.put(key, value);
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(final String attr) {
        this.attr = attr;
    }

    /**
     * Get the businessParam through the key.<br>
     * 
     * @param key The key
     * @return The businessParam as string
     * @since SDNO 0.5
     */
    public String getStringBusinessParam(final String key) {
        return getBusinessParam(key, String.class);
    }

    /**
     * Get the businessParam through the key.<br>
     * 
     * @param key The key
     * @return The businessParam as integer
     * @since SDNO 0.5
     */
    public Integer getIntBusinessParam(final String key) {
        return getBusinessParam(key, Integer.class);
    }

    /**
     * Get the businessParam through the key.<br>
     * 
     * @param key The key
     * @return The businessParam as long
     * @since SDNO 0.5
     */
    public Long getLongBusinessParam(final String key) {
        return getBusinessParam(key, Long.class);
    }

    /**
     * Get the list of businessParam through the key.<br>
     * 
     * @param key The key
     * @return The list of businessParam as string
     * @since SDNO 0.5
     */
    public List<String> getStrListBusinessParam(final String key) {
        return getListBusinessParam(key, String.class);
    }

    /**
     * Get the list of businessParam through the key.<br>
     * 
     * @param key The key
     * @return The list of businessParam as integer
     * @since SDNO 0.5
     */
    public List<Integer> getIntListBusinessParam(final String key) {
        return getListBusinessParam(key, Integer.class);
    }

    /**
     * Get the list of businessParam through the key.<br>
     * 
     * @param key The key
     * @return The list of businessParam as long
     * @since SDNO 0.5
     */
    public List<Long> getLongListBusinessParam(final String key) {
        return getListBusinessParam(key, Long.class);
    }

    public Map<String, Object> getBusinessParams() {
        return businessParams;
    }

    public void setBusinessParams(final Map<String, Object> businessParams) {
        this.businessParams = businessParams;
    }

    public String getFilterDesc() {
        return filterDesc;
    }

    public void setFilterDesc(final String filterDesc) {
        this.filterDesc = filterDesc;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pagesize) {
        pageSize = pagesize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(final int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(final String queryType) {
        this.queryType = queryType;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(final String sort) {
        this.sort = sort;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(final String sortType) {
        this.sortType = sortType;
    }

    /**
     * Get the list of businessParam through the key.<br>
     * 
     * @param key The key
     * @param clazz The class type
     * @return The list of businessParam as the class type
     * @since SDNO 0.5
     */
    private <T> List<T> getListBusinessParam(final String key, final Class<T> clazz) {
        final Object o = businessParams.get(key);
        if(null != o) {
            return (List<T>)o;
        }
        return null;
    }

    /**
     * Get the businessParam through the key.<br>
     * 
     * @param key The key
     * @param clazz The class type
     * @return The businessParam as the class type
     * @since SDNO 0.5
     */
    private <T> T getBusinessParam(final String key, final Class<T> clazz) {
        final Object o = businessParams.get(key);
        if(null != o && clazz.isAssignableFrom(o.getClass())) {
            return clazz.cast(o);
        }
        return null;
    }
}
