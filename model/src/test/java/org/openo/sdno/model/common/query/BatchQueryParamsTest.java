
package org.openo.sdno.model.common.query;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class BatchQueryParamsTest {

    @Test
    public void testGetIntListBusinessParam() {
        BatchQueryParams batchQueryParams = new BatchQueryParams();
        List<Integer> intList = new ArrayList<Integer>();
        intList.add(1);
        batchQueryParams.addBusinessParam("intNumber", intList);
        List<Integer> integerList = batchQueryParams.getIntListBusinessParam("intNumber");
        assertEquals(Integer.valueOf(1), integerList.get(0));
    }

    @Test
    public void testGetStrListBusinessParamNull() {
        BatchQueryParams batchQueryParams = new BatchQueryParams();
        List<String> strList = batchQueryParams.getStrListBusinessParam("strNumber");
        assertEquals(null, strList);
    }

    @Test
    public void testGetIntBusinessParam() {
        BatchQueryParams batchQueryParams = new BatchQueryParams();
        batchQueryParams.addBusinessParam("intNumber", 1);
        Integer integerNumber = batchQueryParams.getIntBusinessParam("intNumber");
        assertEquals(Integer.valueOf(1), integerNumber);
    }

    @Test
    public void testGetIntBusinessParamNull() {
        BatchQueryParams batchQueryParams = new BatchQueryParams();
        batchQueryParams.addBusinessParam("intNumber", 1);
        Integer integerNumber = batchQueryParams.getIntBusinessParam("number");
        assertEquals(null, integerNumber);
    }

    @Test
    public void testGetIntBusinessParamNotAssignable() {
        BatchQueryParams batchQueryParams = new BatchQueryParams();
        batchQueryParams.addBusinessParam("strNumber", "test");
        Integer integerNumber = batchQueryParams.getIntBusinessParam("strNumber");
        assertEquals(null, integerNumber);
    }

}
