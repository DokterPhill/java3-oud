package nl.fontys.sebivenlo.crossing;

import nl.fontys.sebivenlo.crossing.model.AbstractTrafficDispatcher;
import nl.fontys.sebivenlo.crossing.model.CrossingModel;
import nl.fontys.sebivenlo.crossing.model.TrafficLight;
import nl.fontys.sebivenlo.crossing.model.StreetCornerID;

/**
 * Accepts requests and allows groups of safe lights to combined green.
 * This implementation should dispatch lazily.
 *
 * @author ode
 */
public class TrafficDispatcher implements AbstractTrafficDispatcher {

    private final CrossingModel<TrafficLight> crossingModel;

    /**
     * The Dispatcher works with a crossing model.
     *
     * @param cm crossingModel
     */
    TrafficDispatcher( final CrossingModel<TrafficLight> cm ) {
        this.crossingModel = cm;
    }

    /**
     * Adds a request for a greencycle for corner,lane.
     *
     * @param corner corner
     * @param laneNr lane
     */
    @Override
    public void addRequest( StreetCornerID corner, int laneNr ) {
        //TODO
    }

    /**
     * The dispatcher should be started.
     */
    @Override
    public void start() {
        //TODO
    }
}
