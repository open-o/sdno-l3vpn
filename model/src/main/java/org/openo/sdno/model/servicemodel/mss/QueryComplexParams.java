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

package org.openo.sdno.model.servicemodel.mss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.sdno.framework.container.util.JsonUtil;

/**
 * Data structure for batch query. <br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public class QueryComplexParams implements Cloneable {

    /**
     * Maximum capacity in one page.
     */
    private static final int PAGE_CAPACITY_MAX = 1000;

    // Attribute name
    private static final String FILTER = "filter";

    private static final String FILTER_DESC = "filterDsc";

    private static final String FILTER_DATA = "filterData";

    private static final String SORT = "sort";

    private static final String FIELDS = "fields";

    private static final String PAGE_NUM = "pagenum";

    private static final String PAGE_SIZE = "pagesize";

    private static final String ALL_ATTR = "all";

    /**
     * Attribute names for sort.Start with '_' means Descending.
     */
    private List<String> sort = new ArrayList<>();

    /**
     * fields:
     * null-Query all the attributes.
     * ext-query all the external attributes.
     * all--Query all the attributes.
     * attribute names: separate with comma.
     */
    private List<String> fields = new ArrayList<>();

    /**
     * Current request page start from 0.
     */
    private int pageNumber = -1;

    /**
     * Capacity for one page.Default is 1000, can not bigger than 1000.
     */
    private int pageSize = -1;

    private String filterDsc = "";

    private Map<String, Object> filterData;

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     */
    public QueryComplexParams() {
        super();
    }

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     * @param filterDsc Filter description.
     * @param filterData Filter data.
     */
    public QueryComplexParams(final String filterDsc, final Map<String, Object> filterData) {
        super();
        this.filterData = filterData;
        this.filterDsc = filterDsc;
    }

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     * @param sort The sort to set
     * @param fields The fields to set
     * @param pageNumber The pageNumber to set
     * @param pageSize The pageSize to set
     * @param filterDsc Filter description.
     * @param filterData Filter data.
     */
    public QueryComplexParams(final List<String> sort, final List<String> fields, final int pageNumber,
            final int pageSize, final String filterDsc, final Map<String, Object> filterData) {
        super();
        this.sort = sort;
        this.fields = fields;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.filterDsc = filterDsc;
        this.filterData = filterData;
    }

    private static List<String> filterEmptyElement(final List<String> origin) {
        final List<String> result = new ArrayList<>(origin.size());
        if(CollectionUtils.isEmpty(origin)) {
            return result;
        }

        for(final String s : origin) {
            if(!StringUtils.isEmpty(s)) {
                result.add(s);
            }
        }
        return origin;
    }

    @Override
    public QueryComplexParams clone() throws CloneNotSupportedException {
        QueryComplexParams clone = null;
        clone = (QueryComplexParams)super.clone();

        clone.setFilterDsc(filterDsc);
        clone.setFilterData(filterData);
        clone.setPageNumber(pageNumber);
        clone.setPageSize(pageSize);
        final List<String> newFields = new ArrayList<String>(fields.size());
        newFields.addAll(fields);
        clone.setFields(newFields);

        final List<String> newSorts = new ArrayList<String>(sort.size());
        newSorts.addAll(sort);
        clone.setFields(newSorts);

        return clone;
    }

    public List<String> getSort() {
        return sort;
    }

    public void setSort(final List<String> sort) {
        this.sort = sort;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(final List<String> fields) {
        this.fields = fields;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(final int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getFilterDsc() {
        return filterDsc;
    }

    public void setFilterDsc(final String filterDsc) {
        this.filterDsc = filterDsc;
    }

    public Map<String, Object> getFilterData() {
        return filterData;
    }

    public void setFilterData(final Map<String, Object> filterData) {
        this.filterData = filterData;
    }

    public int getPageSize() {

        return pageSize;
    }

    public void setPageSize(final int pagesize) {
        pageSize = pagesize;
    }

    /**
     * Reset page parameters.<br/>
     * 
     * @since SDNO 0.5
     */
    public void resetPagePara() {
        pageNumber = -1;
        pageSize = -1;
    }

    /**
     * Convert the parameters to the restful parameters.<br/>
     * 
     * @return The restful parameters
     * @since SDNO 0.5
     */
    public RestfulParametes convertToRestfulParametes() {
        final RestfulParametes restParametes = new RestfulParametes();

        if(StringUtils.isNotEmpty(filterDsc) && MapUtils.isNotEmpty(filterData)) {
            final Map<String, Object> filter = new HashMap<>();
            filter.put(FILTER_DESC, filterDsc);
            filter.put(FILTER_DATA, JsonUtil.toJson(filterData));
            restParametes.put(FILTER, JsonUtil.toJson(filter));
        }

        final List<String> sortAttrs = filterEmptyElement(sort);
        if(CollectionUtils.isNotEmpty(sortAttrs)) {
            restParametes.put(SORT, JsonUtil.toJson(sortAttrs));
        }

        final List<String> attrs = filterEmptyElement(fields);
        if(CollectionUtils.isNotEmpty(attrs)) {
            restParametes.put(FIELDS, StringUtils.join(attrs, ","));
        } else {
            restParametes.put(FIELDS, ALL_ATTR);
        }

        restParametes.put(PAGE_NUM, String.valueOf((pageNumber < 0) ? 0 : pageNumber));
        restParametes.put(PAGE_SIZE,
                String.valueOf(((pageSize < 0) || (pageSize > PAGE_CAPACITY_MAX)) ? PAGE_CAPACITY_MAX : pageSize));

        return restParametes;
    }
}
