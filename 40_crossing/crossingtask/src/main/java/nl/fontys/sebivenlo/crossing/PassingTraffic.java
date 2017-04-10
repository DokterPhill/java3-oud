package nl.fontys.sebivenlo.crossing;

import nl.fontys.sebivenlo.crossing.model.TrafficLight;
import java.util.concurrent.Callable;

/**
 * The result carrying value object in pro3_crossing.
 *
 * @author hom
 */
final class PassingTraffic implements Callable<PassingTraffic> {

    private final TrafficLight atl;
    private static int trafficCounter = 0;
    private final int trafficNr = ++trafficCounter;
    private final long waitSince = System.nanoTime();
    private long waitEnded;

    public PassingTraffic( TrafficLight abstractTrafficLight ) {
        this.atl = abstractTrafficLight;
    }

    /**
     * The method to be invoked by the executor threads.
     *
     * @return this
     *
     * @throws Exception
     */
    public PassingTraffic call() throws Exception {
        atl.cycleGreen();
        waitEnded = System.nanoTime();
        return this;
    }

    /**
     * Get the light id.
     *
     * @return the id
     */
    public int getTrafficNr() {
        return trafficNr;
    }

    /**
     * Get the associated traffic light.
     *
     * @return the trafficlight
     */
    public TrafficLight getAbstractTrafficLight() {
        return atl;
    }

    /**
     * Get the end of wait time.
     *
     * @return the time in nano seconds.
     */
    public long getWaitEnded() {
        return waitEnded;
    }

    /**
     * Get the waiting time in milliseconds.
     *
     * @return the time span this Passing traffic was waiting for service.
     */
    public final long getWaitingInterval() {
        return ( getWaitEnded() - waitSince ) / 1000000L;
    }

    @Override
    public String toString() {
        return String.format( "Passing Traffic [%3d] waiting for light %s "
                + " for %5d milli seconds", trafficNr, atl.toString(),
                + getWaitingInterval() );
    }
}
