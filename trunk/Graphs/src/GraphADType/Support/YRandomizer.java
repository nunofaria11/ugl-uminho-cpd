/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphADType.Support;

/**
 * Interface used to define the way the user wants to generate random Y values.
 * @author nuno
 */
public interface YRandomizer<Y extends Comparable<Y>> {

    /**
     * Random function to generate random Y values in the interval [lower, upper].
     * @param lower Lower limit.
     * @param upper Upper limit.
     * @return Random Y value.
     */
    Y random(Y lower, Y upper);


}
