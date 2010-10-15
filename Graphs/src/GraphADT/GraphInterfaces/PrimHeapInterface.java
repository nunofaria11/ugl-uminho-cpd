/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphADT.GraphInterfaces;

import GraphADT.Edge;
import java.util.ArrayList;

/**
 *
 * @author nuno
 */
public interface PrimHeapInterface {

    boolean QisEmpty();

    void addVisited(int v);

    /**
     * Extracts minimum value in queue and returns it
     * @return minimum value in queue
     */
    Edge extractMin();

    ArrayList<Edge> getQ();

    boolean isInQ(Edge x);

    void setQ(ArrayList<Edge> Q);

    void addToQ(ArrayList x);

}
