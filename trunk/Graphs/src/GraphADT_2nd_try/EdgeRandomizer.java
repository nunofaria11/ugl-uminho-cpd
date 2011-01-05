/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADT_2nd_try;

/**
 *
 * @author nuno
 */
public interface EdgeRandomizer<E extends Comparable<E>> {

    /**
     * Random function to generate random E values in the interval [lower, upper].
     * The <i>upper</i> and <i>lower</i> edge values must be provided as an interface
     * method.
     * @return Random E value.
     */
    E random();

    /**
     * Returns upper limit edge value
     * @return upper limit edge value
     */
    E upperLimit();

    /**
     * Returns lower limit edge value
     * @return lower limit edge value
     */
    E lowerLimit();
}
