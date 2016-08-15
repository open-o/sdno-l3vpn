
package org.openo.sdno.model.db;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openo.sdno.exception.InnerErrorServiceException;

public class FieldConvertUtilTest {

    @Test
    public void testSetA2BANull() {
        FieldConvertUtil.setA2B(null, new Object());
        FieldConvertUtil.setA2B(new Object(), null);
        assertTrue(true);

    }

    @Test
    public void testGetUUIDNormal() throws InnerErrorServiceException {
        TestMoUuid demo = new TestMoUuid();
        demo.setUuid("uuid");
        String uuid = FieldConvertUtil.getUUID(demo, demo.getClass());
        assertTrue(uuid.equals("uuid"));
    }

    @Test
    public void testGetUUIDAnnotation() throws InnerErrorServiceException {
        TestMoAnnotation demo = new TestMoAnnotation();
        demo.setId("uuid");
        String uuid = FieldConvertUtil.getUUID(demo, demo.getClass());
        assertTrue(uuid.equals("uuid"));
    }

    @Test
    public void testGetUUIDNothing() throws InnerErrorServiceException {
        TestMoNothing demo = new TestMoNothing();
        assertNull(FieldConvertUtil.getUUID(demo, demo.getClass()));
    }

    @Test
    public void testGetRestType() {
        assertTrue(FieldConvertUtil.getRestType(TestMoAnnotation.class).equals("infomodel"));
        assertTrue(FieldConvertUtil.getRestType(TestMoNothing.class).equals("org_openo_sdno_model_db_testmonothing"));

    }

    @Test
    public void testSetA2BNormal() {
        ObjectA objecA = new ObjectA();
        ObjectB objectB = new ObjectB();
        objecA.setValue4Po("aNotHave", "value");
        objecA.setValue4Po("ahave", "value1");
        objecA.setValue4Po("onlyB2", "value1");
        FieldConvertUtil.setA2B(objecA, objectB);

    }

    @Test
    public void testSetA2BNoMap() {
        ObjectANoMap objecA = new ObjectANoMap();
        ObjectB objectB = new ObjectB();
        FieldConvertUtil.setA2B(objecA, objectB);

    }

    @Test
    public void testSetA2BNoGet() {
        ObjectANoGet objecA = new ObjectANoGet();
        ObjectB objectB = new ObjectB();
        FieldConvertUtil.setA2B(objecA, objectB);

    }
}
