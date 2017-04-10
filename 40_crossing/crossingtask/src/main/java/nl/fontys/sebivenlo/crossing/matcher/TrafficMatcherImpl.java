/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sebivenlo.crossing.matcher;

import nl.fontys.sebivenlo.crossing.model.TrafficMatcher;
import nl.fontys.sebivenlo.crossing.model.DirUtils;
import nl.fontys.sebivenlo.crossing.model.CrossingModel;
import nl.fontys.sebivenlo.crossing.model.Dir;
import nl.fontys.sebivenlo.crossing.model.StreetCornerID;
import static nl.fontys.sebivenlo.crossing.model.StreetCornerID.*;
import nl.fontys.sebivenlo.crossing.model.TrafficLight;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Matches Traffic, that is traffic that can go at the same time.
 * 
 * @param <T> TrafficLight type to use.
 * @author Pieter van den Hombergh and Ferd van Odenhoven.
 */
public final class TrafficMatcherImpl implements
        TrafficMatcher {

    public TrafficMatcherImpl() {
    }

    /**
     * When all traffic lights are present, this method adds to each traffic
     * light the safe lights for that traffic light
     * @param crossing model for this crossing.
     */
    @Override
    public void organizeTrafficLights( CrossingModel crossing ) {
        for ( StreetCornerID i : StreetCornerID.values() ) {
            TrafficLight[] tlights = crossing.getCornerLights( i );
            for ( TrafficLight tlight : tlights ) {
                tlight.addFriends( getFriends( crossing, tlight ) );
            }
        }

    }

    /**
     * This method is called only once: at the start up of the crossing. It adds
     * all traffic lights to tl, if they can cycle together. All these traffic
     * lights are 'friends' of that traffic light tl. AbstractTrafficLight holds
     * a Collection for that purpose and has a method to add these 'friends':
     * addFriend(TrafficLight tl). A TrafficLight cannot be its own friend. If
     * there is only one lane, nothing has to be added!
     * <p/>
     * @param tl traffic light to add friends to
     */
    Set<TrafficLight> getFriends( CrossingModel crossing, TrafficLight tl ) {
        // add all the friends of tl
        // friends are allowed to be green if tl is green
        Set<TrafficLight> friends = getLeftHandCornerFriends( crossing, tl );
        // add the lanes in the same street
        friends.addAll( getSiblings( crossing, tl ) );
        friends.addAll( getOppositeCornerFriends( crossing, tl ) );
        friends.addAll( getRightHandCornerFriends( crossing, tl ) );
        //tl.addFriends( friends );
        return friends;
    }

    /**
     * Find the siblings of this TrafficLight. Siblings are the traffic lights
     * for lanes that share the street (or corner) and are not equal to the
     * TrafficLight.
     * <p/>
     * @param tl TrafficLight
     * <p/>
     * @return Set of Traffic lights on the same lane as tl, not including tl.
     */
    final Set<TrafficLight> getSiblings( CrossingModel crossing, TrafficLight tl ) {
        Set<TrafficLight> resultSet = new HashSet<>();
        //Start Solution::replacewith:://TODO

        StreetCornerID corner = tl.getCorner();
        TrafficLight[] tlights = crossing.getCornerLights( corner );
        for ( TrafficLight sib : tlights ) {
            if ( tl != sib ) {
                resultSet.add( sib );
            }
        }
        return resultSet;
        //End Solution::replacewith::return resultSet;
    }

    /**
     * Find friends from left hand corner which can be green at the same time as
     * tl. If tl wants to go green, then all his friends on the lefthand corner
     * are those traffic lights that are allowed to be green when tl is green!
     * So: traffic lights are friends if they can both be green at the same
     * instant.
     * <p/>
     * @param leftHandCorner the lefthand corner of traffic light tl
     * @param tl the traffic light that wants to know its friends
     * <p/>
     * @return resultSet of left hand corner friend traffic lights
     */
    final Set<TrafficLight> getLeftHandCornerFriends( CrossingModel crossing,
            TrafficLight tl ) {
        Set<TrafficLight> resultSet = new HashSet<>();
        //Start Solution::replacewith:://TODO
        EnumSet<Dir> es = tl.getDirection();
        StreetCornerID leftHandCorner = getLeftHandCorner( tl.getCorner() );
        TrafficLight[] tlights = crossing.getCornerLights( leftHandCorner );
        for ( TrafficLight tl2 : tlights ) {
            if ( tl2.getDirection().equals( EnumSet.of( Dir.RIGHT ) )
                    || ( es.equals( EnumSet.of( Dir.RIGHT ) )
                    && DirUtils.intersection( tl2.getDirection(), EnumSet.of(
                            Dir.STRAIGHT ) ).equals( EnumSet.noneOf( Dir.class ) ) ) ) {
                resultSet.add( tl2 );
            }
        }
        return resultSet;
        //End Solution::replacewith::return resultSet;
    }

    /**
     * Find friends from opposite corner which can be green at the same time as
     * tl. If tl wants to go green, then all his friends on the opposite corner
     * are those traffic lights that are allowed to be green when tl is green!
     * So: traffic lights are friends if they can both be green at the same
     * instant.
     * <p/>
     * @param oppositeCorner the opposite corner of traffic light tl
     * @param tl the traffic light that wants to know its friends
     * <p/>
     * @return set of matching trafficlights
     */
    final Set<TrafficLight> getOppositeCornerFriends( CrossingModel crossing,
            TrafficLight tl ) {
        Set<TrafficLight> resultSet = new HashSet<>();
        //Start Solution::replacewith:://TODO
        EnumSet<Dir> es = tl.getDirection();
        StreetCornerID oppositeCorner = getOppositeCorner( tl.getCorner() );
        TrafficLight[] tlights = crossing.getCornerLights( oppositeCorner );
        for ( TrafficLight tl2 : tlights ) {
            if ( ( es.equals( EnumSet.of( Dir.LEFT ) ) && tl2.getDirection().
                    equals( EnumSet.of( Dir.LEFT ) ) )
                    || ( DirUtils.intersection( es, EnumSet.of( Dir.LEFT ) ).
                            equals( EnumSet.noneOf( Dir.class ) )
                    && DirUtils.intersection( tl2.getDirection(), EnumSet.of(
                            Dir.LEFT ) ).equals( EnumSet.noneOf( Dir.class ) ) ) ) {
                resultSet.add( tl2 );
            }
        }
        return resultSet;
        //End Solution::replacewith::return resultSet;
    }

    /**
     * Find friends from right hand corner which can be green at the same time
     * as tl. If tl wants to go green, then all his friends on the righthand
     * corner are those traffic lights that are allowed to be green when tl is
     * green! So: traffic lights are friends if they can both be green at the
     * same instant.
     * <p/>
     * @param rightHandCorner the righthand corner of traffic light tl
     * @param tl the traffic light that wants to know its friends
     * <p/>
     * @return set of matching trafficlights
     */
    final Set<TrafficLight> getRightHandCornerFriends( CrossingModel crossing,
            TrafficLight tl ) {
        Set<TrafficLight> resultSet = new HashSet<>();
        //Start Solution::replacewith:://TODO
        EnumSet<Dir> es = tl.getDirection();
        StreetCornerID rightHandCorner = getRightHandCorner( tl.getCorner() );
        TrafficLight[] tlights = crossing.getCornerLights( rightHandCorner );
        for ( TrafficLight tl2 : tlights ) {
            if ( es.equals( EnumSet.of( Dir.RIGHT ) )
                    || ( tl2.getDirection().equals( EnumSet.of( Dir.RIGHT ) )
                    && DirUtils.intersection( es, EnumSet.of( Dir.STRAIGHT ) ).
                            equals( EnumSet.noneOf( Dir.class ) ) ) ) {
                resultSet.add( tl2 );
            }
        }
        return resultSet;
        //End Solution::replacewith::return resultSet;
    }
}
