/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphADType;

import EdgeOriented.Edge;
import java.util.Collection;

/**
 *
 * @author nuno
 */
public interface DirectedGraph<T, Y extends Comparable<Y>> {

    public int degreeOf(T node);

    public Collection<Edge> incomingEdgesOf(T node);

}
