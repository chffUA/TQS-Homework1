/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tqs_hw1;

import java.util.List;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Asus
 */
public class ConvBeanTest {
    
    private CurrencyLayerCaller caller;
    private ConvBean bean;
    
    private CurrencyLayerCaller caller2;
    private ConvBean bean2;
    
    @Before
    public void setUp() {
        caller = EasyMock.createMock(CurrencyLayerCaller.class);
        EasyMock.expect(caller.fetch()).andReturn("{"+
            "\"quotes\":{"+
                "\"USDEUR\":0.807602,"+
                "\"USDAUD\":1.286804"+
                "}"+
            "}");
        EasyMock.replay(caller);
        bean = new ConvBean(caller);
        
        caller2 = new CurrencyLayerCaller();
        bean2 = new ConvBean();
    }
    
    /**
     * Test of calculate method, of class ConvBean.
     */
    @Test
    public void testCalculateNoAPI() {
        System.out.println("calculate no API");
        assertEquals(1.615204, bean.calculate("USD","EUR",2), 0.001);
        assertEquals(2, bean.calculate("USD","USD",2), 0.001);
        assertEquals(2.476467, bean.calculate("EUR","USD",2), 0.001);
        assertEquals(3.186728, bean.calculate("EUR","AUD",2), 0.001);
    }
    
    @Test
    public void testListHasOptions() {
        System.out.println("test list");
        assertTrue(bean2.getCurrencies().size()>0);
    }
    
    @Test
    public void testFetch() {
        System.out.println("fetch");
        assertNotNull(caller2.fetch());
        //System.out.println(caller2.fetch());
    }
    
    public ConvBeanTest() {
    }
        
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    
    @After
    public void tearDown() {
    }
    
}
