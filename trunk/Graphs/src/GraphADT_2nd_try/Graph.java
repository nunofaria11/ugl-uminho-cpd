/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADT_2nd_try;

import java.io.Serializable;
import java.util.Collection;

/**
 * A <i>Graph</i> has exactly two nodes in each edge.
 *
 * SOME METHODS MIGHT STILL BE ADDED
 *
 * @author nuno
 */
public interface Graph<V extends Serializable, E> extends Hypergraph<V, E> {

    Collection<E> getEdges();

    Collection<V> getVertices();

    boolean containsVertex(V v);

    boolean containsEdge(E e);

    int getSize();

    int getOrder();

    Collection<V> getNeighbors(V v);

    Collection<E> getIncidentEdges(V v);

    E findEdge(V a, V b);

    Collection<E> findEdgeSet(V a, V b);

    boolean addVertex(V v);

    boolean addEdge(E e, Pair<? extends V> p);

    boolean addEdge(E e, V v1, V v2);

    boolean addEdge(E e, V v1, V v2, EdgeType edge_type);

//    boolean removeVertex(V v);

    boolean removeEdge(E v);

    boolean isNeighbor(V a, V b);

    boolean isIncident(V v, E e);

    /**
     * Number of edges incident to <i>vertex</i>
     * @param v
     * @return
     */
    int degree(V vertex);

    /**
     * Number of vertices adjacent to <i>vertex</i>, i.e., getNeighbors(vertex).size()
     * @param vertex
     * @return
     */
    int getNeighborCount(V vertex);

    /**
     * Number of vertices incident to <i>edge</i>.
     * (2 for normal edges; 1 for self-loops)
     * @param edge
     * @return
     */
    int getIncidentCount(E edge);

    EdgeType getEdgeType(E edge);

    EdgeType getDefaultEdgeType();

    int getEdgeCount(EdgeType edge_type);

    Collection<E> getInEdges(V v);

    Collection<E> getOutEdges(V v);

    int inDegree(V v);

    int outDegree(V v);

    Collection<V> getPredecessors(V v);

    Collection<V> getSuccessors(V v);

    V getSource(E directed_edge);

    V getTarget(E directed_edge);

    boolean isSource(V v, E edge);

    boolean isDest(V v, E edge);

    Pair<V> getEndpoints(E e);

    V getRandomNode();
}
