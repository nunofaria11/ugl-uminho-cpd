/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphADType.Support;

import NodeOriented.Node;
import java.util.Collection;

/**
 * Interface used to define the way the user wants to generate random Y values.
 * @author nuno
 */
public interface TYRandomizer<T,Y extends Comparable<Y>> {

    /**
     * Random function to generate random Y values in the interval [lower, upper].
     * @param lower Lower limit.
     * @param upper Upper limit.
     * @return Random Y value.
     */
    Y random(Y lower, Y upper);


    /**
     * Randomly generates N T-typed nodes.
     * @param n Number of nodes to identify.
     * @return Collection with T-typed N nodes.
     */
    Collection<Node<T>> randomizeNodes(int n, Collection<T> alphabet);

}
