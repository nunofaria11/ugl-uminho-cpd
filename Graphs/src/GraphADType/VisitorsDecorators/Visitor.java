/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType.VisitorsDecorators;

import EdgeOriented.EdgeEO;
import NodeOriented.Node;

/**
 *
 * @author nuno
 */
public interface Visitor<N extends Node, E extends EdgeEO> {

    /**
     * This is invoked on every vertex in the graph before the start
     * of the graph algorithm
     * @param n Start node
     */
    void initializes(N n);

    /**
     * Initializes the <i>starting</i> node in some special way.
     * @param n Node
     */
    void start(N n);

    /**
     * This is called when for a node <b>n</b> the first time the algorithm encounters
     * the node.
     * @param n
     */
    void discover(N n);

    /**
     * Invoked when the algorithm explores the edge. For example, it will be invoked
     * when Breadth First Search visits an adjacent vertex of a vertex.
     * @param e Edge
     * @param n Node
     */
    boolean process(E e);

    /**
     * This is called for vertex <b>n</b> after the algorithm has finished
     * exploring all of the out edges of vertex <b>n</b>.
     * @param n Node
     */
    void finish(N n);
}
