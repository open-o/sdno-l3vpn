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

package org.openo.sdno.wanvpn.util.checker;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.paradesc.IPDesc;
import org.openo.sdno.model.paradesc.IPDesc.IPType;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;
import org.openo.sdno.wanvpn.util.checker.IPChecker;

public class IPCheckerTest {

    @Test
    public void testCheckIPAnnotationNull() throws ServiceException, NoSuchFieldException, SecurityException {
        MockSvcModel svcModelTemp = new MockSvcModel();
        Field fieldTemp = svcModelTemp.getClass().getDeclaredField("numberStr");
        IPChecker.checkIP(svcModelTemp, fieldTemp);
    }

    @Test
    public void testCheckIPV4_ANull() throws ServiceException, NoSuchFieldException, SecurityException {
        IpSvcModel ipSvcModelTemp = new IpSvcModel();
        ipSvcModelTemp.setIpVA(null);
        Field fieldTemp = ipSvcModelTemp.getClass().getDeclaredField("ipVA");
        IPChecker.checkIP(ipSvcModelTemp, fieldTemp);
    }

    @Test
    public void testCheckIPV4_AIsValidCidrException() throws NoSuchFieldException, SecurityException {
        IpSvcModel ipSvcModelTemp = new IpSvcModel();
        ipSvcModelTemp.setIpVA("1.2.3.4/a");
        Field fieldTemp = ipSvcModelTemp.getClass().getDeclaredField("ipVA");
        try {
            IPChecker.checkIP(ipSvcModelTemp, fieldTemp);
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckIPV4_AIsValidAddressException() throws NoSuchFieldException, SecurityException {
        IpSvcModel ipSvcModelTemp = new IpSvcModel();
        ipSvcModelTemp.setIpVA1("1.2.3.400");
        Field fieldTemp = ipSvcModelTemp.getClass().getDeclaredField("ipVA1");
        try {
            IPChecker.checkIP(ipSvcModelTemp, fieldTemp);
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckIPV4_A() throws ServiceException, NoSuchFieldException, SecurityException {
        IpSvcModel ipSvcModelTemp = new IpSvcModel();
        ipSvcModelTemp.setIpVA("1.2.3.4/5");
        Field fieldTemp = ipSvcModelTemp.getClass().getDeclaredField("ipVA");
        IPChecker.checkIP(ipSvcModelTemp, fieldTemp);
    }

    @Test
    public void testCheckIPV4_AException() throws NoSuchFieldException, SecurityException {
        IpSvcModel ipSvcModelTemp = new IpSvcModel();
        ipSvcModelTemp.setIpVA("128.2.3.4/5");
        Field fieldTemp = ipSvcModelTemp.getClass().getDeclaredField("ipVA");
        try {
            IPChecker.checkIP(ipSvcModelTemp, fieldTemp);
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckIPV4_B() throws ServiceException, NoSuchFieldException, SecurityException {
        IpSvcModel ipSvcModelTemp = new IpSvcModel();
        ipSvcModelTemp.setIpVB("130.2.3.4/5");
        Field fieldTemp = ipSvcModelTemp.getClass().getDeclaredField("ipVB");
        IPChecker.checkIP(ipSvcModelTemp, fieldTemp);
    }

    @Test
    public void testCheckIPV4_BLessException() throws NoSuchFieldException, SecurityException {
        IpSvcModel ipSvcModelTemp = new IpSvcModel();
        ipSvcModelTemp.setIpVB("1.2.3.4/5");
        Field fieldTemp = ipSvcModelTemp.getClass().getDeclaredField("ipVB");
        try {
            IPChecker.checkIP(ipSvcModelTemp, fieldTemp);
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckIPV4_BOutException() throws NoSuchFieldException, SecurityException {
        IpSvcModel ipSvcModelTemp = new IpSvcModel();
        ipSvcModelTemp.setIpVB("192.2.3.4/5");
        Field fieldTemp = ipSvcModelTemp.getClass().getDeclaredField("ipVB");
        try {
            IPChecker.checkIP(ipSvcModelTemp, fieldTemp);
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckIPV4_C() throws ServiceException, NoSuchFieldException, SecurityException {
        IpSvcModel ipSvcModelTemp = new IpSvcModel();
        ipSvcModelTemp.setIpVC("200.2.3.4/5");
        Field fieldTemp = ipSvcModelTemp.getClass().getDeclaredField("ipVC");
        IPChecker.checkIP(ipSvcModelTemp, fieldTemp);
    }

    @Test
    public void testCheckIPV4_CLessException() throws NoSuchFieldException, SecurityException {
        IpSvcModel ipSvcModelTemp = new IpSvcModel();
        ipSvcModelTemp.setIpVC("1.2.3.4/5");
        Field fieldTemp = ipSvcModelTemp.getClass().getDeclaredField("ipVC");
        try {
            IPChecker.checkIP(ipSvcModelTemp, fieldTemp);
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckIPV4_COutException() throws NoSuchFieldException, SecurityException {
        IpSvcModel ipSvcModelTemp = new IpSvcModel();
        ipSvcModelTemp.setIpVC("224.2.3.4/5");
        Field fieldTemp = ipSvcModelTemp.getClass().getDeclaredField("ipVC");
        try {
            IPChecker.checkIP(ipSvcModelTemp, fieldTemp);
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckIPV4_D() throws ServiceException, NoSuchFieldException, SecurityException {
        IpSvcModel ipSvcModelTemp = new IpSvcModel();
        ipSvcModelTemp.setIpVD("230.2.3.4/5");
        Field fieldTemp = ipSvcModelTemp.getClass().getDeclaredField("ipVD");
        IPChecker.checkIP(ipSvcModelTemp, fieldTemp);
    }

    @Test
    public void testCheckIPV4_DLessException() throws NoSuchFieldException, SecurityException {
        IpSvcModel ipSvcModelTemp = new IpSvcModel();
        ipSvcModelTemp.setIpVD("1.2.3.4/5");
        Field fieldTemp = ipSvcModelTemp.getClass().getDeclaredField("ipVD");
        try {
            IPChecker.checkIP(ipSvcModelTemp, fieldTemp);
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckIPV4_DOutException() throws NoSuchFieldException, SecurityException {
        IpSvcModel ipSvcModelTemp = new IpSvcModel();
        ipSvcModelTemp.setIpVD("240.2.3.4/5");
        Field fieldTemp = ipSvcModelTemp.getClass().getDeclaredField("ipVD");
        try {
            IPChecker.checkIP(ipSvcModelTemp, fieldTemp);
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckIPV4_E() throws ServiceException, NoSuchFieldException, SecurityException {
        IpSvcModel ipSvcModelTemp = new IpSvcModel();
        ipSvcModelTemp.setIpVE("245.2.3.4/5");
        Field fieldTemp = ipSvcModelTemp.getClass().getDeclaredField("ipVE");
        IPChecker.checkIP(ipSvcModelTemp, fieldTemp);
    }

    @Test
    public void testCheckIPV4_ELessException() throws NoSuchFieldException, SecurityException {
        IpSvcModel ipSvcModelTemp = new IpSvcModel();
        ipSvcModelTemp.setIpVE("1.2.3.4/5");
        Field fieldTemp = ipSvcModelTemp.getClass().getDeclaredField("ipVE");
        try {
            IPChecker.checkIP(ipSvcModelTemp, fieldTemp);
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckIPV4_EOutException() throws NoSuchFieldException, SecurityException {
        IpSvcModel ipSvcModelTemp = new IpSvcModel();
        ipSvcModelTemp.setIpVE("248.2.3.4/5");
        Field fieldTemp = ipSvcModelTemp.getClass().getDeclaredField("ipVE");
        try {
            IPChecker.checkIP(ipSvcModelTemp, fieldTemp);
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckIPV4_ABC() throws ServiceException, NoSuchFieldException, SecurityException {
        IpSvcModel ipSvcModelTemp = new IpSvcModel();
        ipSvcModelTemp.setIpVABC("1.2.3.4/5");
        Field fieldTemp = ipSvcModelTemp.getClass().getDeclaredField("ipVABC");
        IPChecker.checkIP(ipSvcModelTemp, fieldTemp);
    }

    @Test
    public void testCheckIPV4_ABCException() throws NoSuchFieldException, SecurityException {
        IpSvcModel ipSvcModelTemp = new IpSvcModel();
        ipSvcModelTemp.setIpVABC("240.2.3.4/5");
        Field fieldTemp = ipSvcModelTemp.getClass().getDeclaredField("ipVABC");
        try {
            IPChecker.checkIP(ipSvcModelTemp, fieldTemp);
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckIPListNull() throws NoSuchFieldException, SecurityException, ServiceException {
        IpSvcModel ipSvcModelTemp = new IpSvcModel();
        List<String> ipList = new ArrayList<String>();
        ipList.add(null);
        ipSvcModelTemp.setIpList(ipList);
        Field fieldTemp = ipSvcModelTemp.getClass().getDeclaredField("ipList");
        IPChecker.checkIP(ipSvcModelTemp, fieldTemp);
    }

    @Test
    public void testCheckIPList() throws NoSuchFieldException, SecurityException, ServiceException {
        IpSvcModel ipSvcModelTemp = new IpSvcModel();
        List<String> ipList = new ArrayList<String>();
        ipList.add("1.2.3.4/5");
        ipSvcModelTemp.setIpList(ipList);
        Field fieldTemp = ipSvcModelTemp.getClass().getDeclaredField("ipList");
        IPChecker.checkIP(ipSvcModelTemp, fieldTemp);
    }

    @Test
    public void testCheckIPIntegerListException() throws NoSuchFieldException, SecurityException {
        IpSvcModel ipSvcModelTemp = new IpSvcModel();
        List<Integer> ipIntegerList = new ArrayList<Integer>();
        ipIntegerList.add(0);
        ipSvcModelTemp.setIpIntegerList(ipIntegerList);
        Field fieldTemp = ipSvcModelTemp.getClass().getDeclaredField("ipIntegerList");
        try {
            IPChecker.checkIP(ipSvcModelTemp, fieldTemp);
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }

    @Test
    public void testCheckIPIntegerException() throws NoSuchFieldException, SecurityException {
        IpSvcModel ipSvcModelTemp = new IpSvcModel();
        Field fieldTemp = ipSvcModelTemp.getClass().getDeclaredField("ipInteger");
        try {
            IPChecker.checkIP(ipSvcModelTemp, fieldTemp);
        } catch(ServiceException e) {
            assertTrue(e.getClass().equals(ServiceException.class));
        }
    }
}

class IpSvcModel extends AbstractSvcModel {

    @IPDesc(hasMask = true, ipType = IPType.IPV4_A)
    private String ipVA;

    @IPDesc(hasMask = false, ipType = IPType.IPV4_A)
    private String ipVA1;

    @IPDesc(hasMask = true, ipType = IPType.IPV4_B)
    private String ipVB;

    @IPDesc(hasMask = true, ipType = IPType.IPV4_C)
    private String ipVC;

    @IPDesc(hasMask = true, ipType = IPType.IPV4_D)
    private String ipVD;

    @IPDesc(hasMask = true, ipType = IPType.IPV4_E)
    private String ipVE;

    @IPDesc(hasMask = true, ipType = IPType.IPV4_ABC)
    private String ipVABC;

    @IPDesc(hasMask = true, ipType = IPType.IPV4_A)
    private List<String> ipList;

    @IPDesc(hasMask = true, ipType = IPType.IPV4_A)
    private List<Integer> ipIntegerList;

    @IPDesc(hasMask = true, ipType = IPType.IPV4_A)
    private Integer ipInteger;

    public String getIpVA() {
        return ipVA;
    }

    public void setIpVA(String ipVA) {
        this.ipVA = ipVA;
    }

    public String getIpVA1() {
        return ipVA1;
    }

    public void setIpVA1(String ipVA1) {
        this.ipVA1 = ipVA1;
    }

    public String getIpVB() {
        return ipVB;
    }

    public void setIpVB(String ipVB) {
        this.ipVB = ipVB;
    }

    public String getIpVC() {
        return ipVC;
    }

    public void setIpVC(String ipVC) {
        this.ipVC = ipVC;
    }

    public String getIpVD() {
        return ipVD;
    }

    public void setIpVD(String ipVD) {
        this.ipVD = ipVD;
    }

    public String getIpVE() {
        return ipVE;
    }

    public void setIpVE(String ipVE) {
        this.ipVE = ipVE;
    }

    public String getIpVABC() {
        return ipVABC;
    }

    public void setIpVABC(String ipVABC) {
        this.ipVABC = ipVABC;
    }

    public List<String> getIpList() {
        return ipList;
    }

    public void setIpList(List<String> ipList) {
        this.ipList = ipList;
    }

    public List<Integer> getIpIntegerList() {
        return ipIntegerList;
    }

    public void setIpIntegerList(List<Integer> ipIntegerList) {
        this.ipIntegerList = ipIntegerList;
    }

    @Override
    public String getUuid() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setUuid(String uuid) {
        // TODO Auto-generated method stub

    }

}
