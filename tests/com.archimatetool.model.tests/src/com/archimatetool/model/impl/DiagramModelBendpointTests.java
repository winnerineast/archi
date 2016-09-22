/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.model.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.archimatetool.model.IArchimateFactory;
import com.archimatetool.model.IDiagramModelBendpoint;


public class DiagramModelBendpointTests {
    
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DiagramModelBendpointTests.class);
    }
    
    private IDiagramModelBendpoint bp;
    

    @Before
    public void runBeforeEachDiagramModelArchimateObjectTest() {
        bp = IArchimateFactory.eINSTANCE.createDiagramModelBendpoint();
    }


    @Test
    public void testDefaultValues() {
        assertNotNull(bp);
        assertEquals(0, bp.getX());
        assertEquals(0, bp.getY());
    }

    @Test
    public void testSetValues() {
        assertNotNull(bp);
        
        bp.setX(1);
        bp.setY(2);
        
        assertEquals(1, bp.getX());
        assertEquals(2, bp.getY());
    }

}
