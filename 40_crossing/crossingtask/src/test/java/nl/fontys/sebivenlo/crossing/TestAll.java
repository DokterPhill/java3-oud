/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.fontys.sebivenlo.crossing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author ode
 */
@RunWith(value=Suite.class)
@Suite.SuiteClasses(
    value = {
        TrafficLightTest.class,
        HarmlessTrafficTest.class
    }
)
public class TestAll {

    public TestAll() {
    }
}