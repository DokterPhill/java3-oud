package nl.fontys.sebivenlo.crossing;

import nl.fontys.sebivenlo.crossing.model.TrafficLight;
import java.util.Arrays;

/**
 *
 * @author ode
 */
class HarmlessTraffic {

    private PassingTraffic[] harmlessGroup;
    private int n;

    HarmlessTraffic(PassingTraffic tl) {
        harmlessGroup = new PassingTraffic[12];
        harmlessGroup[0] = tl;
        n = 1;
    }

    void addTrafficLight(TrafficLight tl) {
        addTraffic(new PassingTraffic(tl));
    }

    void addTraffic( PassingTraffic tl ){
        if (tl != null) {
            harmlessGroup[n] = tl;
            n++;
        }
    }

    boolean canJoin( TrafficLight tl ) {
        for (int i = 0; i < n; i++) {
            if (!harmlessGroup[i].getAbstractTrafficLight().isFriend( tl )) {
                return false;
            }
        }
        return true;
    }

    PassingTraffic[] getTrafficLights() {
        return Arrays.copyOf(harmlessGroup, n);
    }

    int size() {
        return n;
    }
}
