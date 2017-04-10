package nl.fontys.sebivenlo.crossing.matcher;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import nl.fontys.sebivenlo.crossing.model.CrossingModel;
import nl.fontys.sebivenlo.crossing.model.StreetCornerID;
import static nl.fontys.sebivenlo.crossing.model.StreetCornerID.*;
import nl.fontys.sebivenlo.crossing.model.TrafficLight;
import java.util.Set;
import java.util.TreeSet;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author ode
 */
public class TrafficMatcherTest {

    private CrossingModel crossing;
    private TrafficMatcherImpl matcher;

    @Before
    public void setUp() {
        matcher = new TrafficMatcherImpl();
    }

    @After
    public void tearDown() {
        crossing = null;
    }

  /**
     *
     */
    @Test
    public void testGetSiblings() {
        //Start Solution::replacewith:://TODO
        crossing = new CrossingModel( 1, 2, 3, 3, matcher );
        TrafficLight tl = crossing.getTrafficLight( NW, 0 );
        Set<TrafficLight> siblings = matcher.getSiblings( crossing, tl );
        assertEquals( "No siblings on one lane street", 0, siblings.size() );

        StreetCornerID corner = NE;
        tl = crossing.getTrafficLight( corner, 0 );
        siblings = matcher.getSiblings( crossing, tl );
        assertEquals( "One sibling on two lane street", 1, siblings.size() );
        assertTrue( "Must be on same corner, other lane", siblings.
                contains( crossing.getTrafficLight( corner, 1 ) ) );

        corner = SE;
        tl = crossing.getTrafficLight( corner, 0 );
        siblings = matcher.getSiblings( crossing,tl );
        assertEquals( "Two siblings on three lane street", 2, siblings.size() );
        assertTrue( "Must have same corner, lane 1",
                siblings.contains( crossing.getTrafficLight( corner, 1 ) ) );
        assertTrue( "Must have same corner, lane 2",
                siblings.contains( crossing.getTrafficLight( corner, 2 ) ) );
        //End Solution::replacewith::fail("test not implemented");
    }

    /**
     * Crossing(int nwLanes, int neLanes, int seLanes, int swLanes)
     * <p/>
     */
    @Test
    public void testGetLeftHandCornerFriends() {
        // new crossing, all lights red: 100% safe!
        //                      NW NE SE SW
        crossing = new CrossingModel( 1, 2, 3, 1, matcher );
        //Start Solution::replacewith:://TODO
        Set<TrafficLight> friends = null;
        // get traffic light at NW corner: has only one lane
        // left corner has lanes: LEFT and STRAIGHTRIGHT
        TrafficLight tl = crossing.getTrafficLight( NW, THE_ONLY_LANE );
        friends = matcher.getLeftHandCornerFriends( crossing, tl );
        assertFalse( friends.contains( crossing.getTrafficLight( NE,
                DUAL_STRAIGHT_RIGHT_LANE ) ) );
        assertFalse( friends.
                contains( crossing.getTrafficLight( NE, LEFT_LANE ) ) );
        // get traffic light at NE corner: has two lanes: LEFT and STRAIGHTRIGHT
        // left corner has lanes: RIGHT, STRAIGHT and LEFT
        tl = crossing.getTrafficLight( NE, LEFT_LANE );
        friends = matcher.getLeftHandCornerFriends( crossing, tl );
        assertFalse( friends.
                contains( crossing.getTrafficLight( NE, LEFT_LANE ) ) );
        assertTrue( friends.
                contains( crossing.getTrafficLight( SE, RIGHT_LANE ) ) );
        assertFalse( friends.contains( crossing.getTrafficLight( NE,
                STRAIGHT_LANE ) ) );
        tl = crossing.getTrafficLight( NE, DUAL_STRAIGHT_RIGHT_LANE );
        friends = matcher.getLeftHandCornerFriends( crossing, tl );
        assertFalse( friends.
                contains( crossing.getTrafficLight( NE, LEFT_LANE ) ) );
        assertTrue( friends.
                contains( crossing.getTrafficLight( SE, RIGHT_LANE ) ) );
        assertFalse( friends.contains( crossing.getTrafficLight( NE,
                STRAIGHT_LANE ) ) );

        //End Solution::replacewith::fail("test not implemented");
    }

    /**
     * Crossing(int nwLanes, int neLanes, int seLanes, int swLanes)
     * <p/>
     */
    @Test
    public void testGetOppositeCornerFriends() {
        //                      NW NE SE SW
        crossing = new CrossingModel( 3, 2, 3, 1, matcher );
        // first: NW-SE traffic light pair
        //Start Solution::replacewith:://TODO
        TrafficLight tl = crossing.getTrafficLight( NW, LEFT_LANE );
        Set<TrafficLight> friends = matcher.getOppositeCornerFriends( crossing,
                tl );
        assertEquals( 1, friends.size() );
        assertTrue( friends.
                contains( crossing.getTrafficLight( SE, LEFT_LANE ) ) );
        tl = crossing.getTrafficLight( NW, STRAIGHT_LANE );
        friends = matcher.getOppositeCornerFriends( crossing, tl );
        assertEquals( 2, friends.size() );
        assertTrue( friends.
                contains( crossing.getTrafficLight( SE, RIGHT_LANE ) ) );
        assertTrue( friends.contains( crossing.getTrafficLight( SE,
                STRAIGHT_LANE ) ) );
        tl = crossing.getTrafficLight( NW, RIGHT_LANE );
        friends = matcher.getOppositeCornerFriends( crossing, tl );
        assertEquals( 2, friends.size() );
        assertTrue( friends.
                contains( crossing.getTrafficLight( SE, RIGHT_LANE ) ) );
        assertTrue( friends.contains( crossing.getTrafficLight( SE,
                STRAIGHT_LANE ) ) );

        //End Solution
    }

    /**
     * Crossing(int nwLanes, int neLanes, int seLanes, int swLanes)
     * <p/>
     */
    @Test
    public void testGetRightHandCornerFriends() {
        //Start Solution::replacewith:://TODO
        //                      NW NE SE SW
        crossing = new CrossingModel( 3, 1, 3, 2, matcher );
        TrafficLight tl = crossing.getTrafficLight( NW, 0 );
        //End Solution
    }

    /**
     * Test for collides. Check that no friend collides.
     */
    @Test
    public void testOppositeCollideSetEmpty3333() {
        //                      NW NE SE SW
        crossing = new CrossingModel( 3, 3, 3, 3, matcher );
        //Start Solution
        TrafficLight tl = crossing.getTrafficLight( NW, LEFT_LANE );
        Set<TrafficLight> colliders = new TreeSet<TrafficLight>();

        colliders.add( crossing.getTrafficLight( NE, STRAIGHT_LANE ) );
        colliders.add( crossing.getTrafficLight( NE, LEFT_LANE ) );

        colliders.add( crossing.getTrafficLight( SE, STRAIGHT_LANE ) );
        colliders.add( crossing.getTrafficLight( SE, RIGHT_LANE ) );

        colliders.add( crossing.getTrafficLight( SW, STRAIGHT_LANE ) );
        colliders.add( crossing.getTrafficLight( SW, LEFT_LANE ) );

        assertEquals( 6, colliders.size() );
        System.out.println( "tl.getFriends" + tl.getFriends() );

        Set<TrafficLight> intersect = tl.getFriends();
        // compute intersection.
        intersect.retainAll( colliders );
        assertEquals( 0, intersect.size() );
        //End Solution
    }
}
