/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sebivenlo.crossing;

import nl.fontys.sebivenlo.crossing.model.CrossingModel;
import nl.fontys.sebivenlo.crossing.model.Dir;
import static nl.fontys.sebivenlo.crossing.model.Dir.*;
import static nl.fontys.sebivenlo.crossing.model.StreetCornerID.*;
import nl.fontys.sebivenlo.crossing.model.TrafficLight;
import java.util.EnumSet;
import nl.fontys.sebivenlo.crossing.matcher.TrafficMatcherImpl;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author ode
 */
public class TrafficLightTest {

    private TrafficLight tL1, tL2;
    private CrossingModel<TrafficLight> cm;

    @Before
    public void setUp() {
        cm = new CrossingModel<TrafficLight>( 2, 2, 2, 2, new TrafficMatcherImpl() );
        TrafficMatcherImpl tm =
                new TrafficMatcherImpl(  );
        tm.organizeTrafficLights(cm);
        tL1 = cm.getTrafficLight( NW, 0 );
        tL1.setDirection( EnumSet.of( RIGHT, Dir.STRAIGHT ) );
    }

    @After
    public void tearDown() {
        tL1 = null;
        cm = null;
    }


    @Test
    public void testTrafficLight() {
        tL1 = cm.getTrafficLight( NW, 1 );
        assertEquals( "NW", tL1.getDescription() );
        assertEquals( NW, tL1.getCorner() );
        assertEquals( 1, tL1.getLane() );
        assertEquals( EnumSet.of( Dir.STRAIGHT, Dir.RIGHT ), tL1.getDirection() );
    }

    @Test
    public void testTrafficLightsFriends() {
        tL1 = cm.getTrafficLight( NW, 1 );
        // tL2 from tL1's left goes left
        tL2 = cm.getTrafficLight( NE, 0 );
        assertFalse( tL1.isFriend( tL2 ) );
        assertFalse( tL2.isFriend( tL1 ) );
        // tL2 from tL1's left goes right+straight
        tL2 = cm.getTrafficLight( NE, 1 );
        assertFalse( tL1.isFriend( tL2 ) );
        assertFalse( tL2.isFriend( tL1 ) );
        // tL1 goes left
        tL1 = cm.getTrafficLight( NW, 0 );
        assertFalse( tL1.isFriend( tL2 ) );
        assertFalse( tL2.isFriend( tL1 ) );
        // tL2 from tL1's opposite goes left
        tL2 = cm.getTrafficLight( SE, 0 );
        assertTrue( tL1.isFriend( tL2 ) );
        assertTrue( tL2.isFriend( tL1 ) );
    }
}