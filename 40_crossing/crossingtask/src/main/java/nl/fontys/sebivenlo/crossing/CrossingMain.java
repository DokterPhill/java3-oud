package nl.fontys.sebivenlo.crossing;

import nl.fontys.sebivenlo.crossing.model.AbstractTrafficDispatcher;
import nl.fontys.sebivenlo.crossing.gui.CrossingGui;
import nl.fontys.sebivenlo.crossing.model.TrafficMatcher;
import nl.fontys.sebivenlo.crossing.model.CrossingModel;
import nl.fontys.sebivenlo.crossing.model.TrafficLight;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import static javax.swing.WindowConstants.*;
import nl.fontys.sebivenlo.crossing.matcher.TrafficMatcherImpl;

/**
 *
 * @author ode
 */
public class CrossingMain {

    public static void main( String[] args ) {
        int[] l = { 3, 1, 3, 2 };
        if ( args.length >= 4 ) {
            for ( int i = 0; i < 4; i++ ) {
                try {
                    int lc = Integer.valueOf( args[ i ] );
                    l[ i ] = lc;
                } catch ( NumberFormatException nfe ) {
                    System.out.println( "Not an integer:" + args[ i ] );
                }
            }
        }
        CrossingMain cm = new CrossingMain( l[ 0 ], l[ 1 ], l[ 2 ], l[ 3 ] );
        AbstractTrafficDispatcher dispatcher;
        dispatcher = new TrafficDispatcher(cm.crossingModel );
        cm.startGui();
        // set dispatcher last, will not return.
        cm.setDisPatcher( dispatcher );
    }
    /**
     * Application frame
     */
    private final JFrame frame;
    /**
     * The crossing widget
     */
    private final CrossingGui<TrafficLight> crossing;
    /**
     * The object for regulating the lights
     */
    private AbstractTrafficDispatcher dispatcher;
    private final CrossingModel<TrafficLight> crossingModel;

    /**
     * The complexity of hooking up things is somewhat hidden in this Object.
     *
     * @param l1 lane count 1
     * @param l2 lane count 2
     * @param l3 lane count 3
     * @param l4 lane count 4
     */
    public CrossingMain( int l1, int l2, int l3, int l4 ) {
        TrafficMatcher matcher = new TrafficMatcherImpl();
        crossingModel = new CrossingModel<TrafficLight>(
                l1, l2, l3, l4, matcher );
        crossing = new CrossingGui<TrafficLight>( crossingModel );

        frame = new JFrame( "PRO 3 StreetCorner with " );//+dispatcher.getClass().getSimpleName());

        TrafficMatcherImpl tm
                = new TrafficMatcherImpl();
        tm.organizeTrafficLights( crossingModel );
    }

    /**
     * Starts the Gui.
     *
     * @throws HeadlessException
     */
    private void startGui() throws
            HeadlessException {
        // make it smart on close by user
        Runnable hophop = new Runnable() {
            public void run() {
                frame.setDefaultCloseOperation( EXIT_ON_CLOSE );

                frame.setSize( 640, 640 );
                frame.setVisible( true );
                frame.setContentPane( crossing );
                frame.setSize( 640, 640 );
                frame.setVisible( true );
            }
        };
        SwingUtilities.invokeLater( hophop );
    }

    /**
     * Sets the dispatcher. This method calls start on the dispatcher, which
     * does not return.
     *
     * @param dispatcher to keep the crossing safe but efficient.
     */
    private void setDisPatcher( AbstractTrafficDispatcher dispatcher ) {
        crossingModel.setDispatcher( dispatcher );
        frame.setTitle( frame.getTitle() + dispatcher.getClass().
                getSimpleName() );
        dispatcher.start();
    }

}
