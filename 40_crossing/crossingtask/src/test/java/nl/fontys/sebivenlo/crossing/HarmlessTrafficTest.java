/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sebivenlo.crossing;

import nl.fontys.sebivenlo.crossing.matcher.TrafficMatcherImpl;
import nl.fontys.sebivenlo.crossing.model.TrafficLight;
import nl.fontys.sebivenlo.crossing.model.CrossingModel;
import static nl.fontys.sebivenlo.crossing.model.StreetCornerID.*;
import nl.fontys.sebivenlo.crossing.model.TrafficLight;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author ode
 */
public class HarmlessTrafficTest {

    private HarmlessTraffic harmlessGroup;
    private TrafficLight tL1, tL2;
    private CrossingModel<TrafficLight> cm;

    @Before
    public void setUp() {
        cm = new CrossingModel<TrafficLight>( 2, 2, 2, 2, new TrafficMatcherImpl() );
        TrafficMatcherImpl tm =
                new TrafficMatcherImpl( );
        tm.organizeTrafficLights(cm);
    }

    @After
    public void tearDown() {
        tL1 = null;
        harmlessGroup = null;
        cm = null;
    }

    @Test
    public void testGroup1() {
        harmlessGroup = new HarmlessTraffic(
                new PassingTraffic( cm.getTrafficLight( NW, 0 ) ) );
        TrafficLight tl = (TrafficLight) cm.getTrafficLight( NE,
                0 );
        tryJoin( harmlessGroup, tl );
        assertEquals( 1, harmlessGroup.size() );

        tl = (TrafficLight) cm.getTrafficLight( SE, 0 );
        tryJoin( harmlessGroup, tl );
        assertEquals( 2, harmlessGroup.size() );

        tl = (TrafficLight) cm.getTrafficLight( NE, 1 );
        tryJoin( harmlessGroup, tl );
        assertEquals( 2, harmlessGroup.size() );

        tl = (TrafficLight) cm.getTrafficLight( SW, 0 );
        tryJoin( harmlessGroup, tl );
        assertEquals( 2, harmlessGroup.size() );

        tl = (TrafficLight) cm.getTrafficLight( SW, 1 );
        tryJoin( harmlessGroup, tl );
        assertEquals( 2, harmlessGroup.size() );

        tl = (TrafficLight) cm.getTrafficLight( NW, 1 );
        tryJoin( harmlessGroup, tl );
        assertEquals( 2, harmlessGroup.size() );
    }

    @Test
    public void testGroup2() {
        harmlessGroup = new HarmlessTraffic(
                new PassingTraffic( cm.getTrafficLight( NW, 1 ) ) );
        TrafficLight tl = (TrafficLight) cm.getTrafficLight( NE,
                0 );
        tryJoin( harmlessGroup, tl );
        assertEquals( 1, harmlessGroup.size() );

        tl = (TrafficLight) cm.getTrafficLight( NE, 1 );
        tryJoin( harmlessGroup, tl );
        assertEquals( 1, harmlessGroup.size() );

        tl = (TrafficLight) cm.getTrafficLight( SE, 1 );
        tryJoin( harmlessGroup, tl );
        assertEquals( 2, harmlessGroup.size() );

        tl = (TrafficLight) cm.getTrafficLight( SE, 0 );
        tryJoin( harmlessGroup, tl );
        assertEquals( 2, harmlessGroup.size() );

        tl = (TrafficLight) cm.getTrafficLight( SW, 0 );
        tryJoin( harmlessGroup, tl );
        assertEquals( 2, harmlessGroup.size() );

        tl = (TrafficLight) cm.getTrafficLight( SW, 1 );
        tryJoin( harmlessGroup, tl );
        assertEquals( 2, harmlessGroup.size() );
    }

    boolean tryJoin( HarmlessTraffic group, TrafficLight tl ) {
        if ( harmlessGroup.canJoin( tl ) ) {
            harmlessGroup.addTrafficLight( tl );
            return true;
        }
        return false;
    }
}