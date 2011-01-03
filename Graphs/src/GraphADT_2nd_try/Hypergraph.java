/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADT_2nd_try;

import java.util.Collection;

/**
 * A Hypergraph can have many nodes in an Edge.
 * @author nuno
 */
public interface Hypergraph<V, E> {

    Collection<E> getEdges();

    Collection<V> getVertices();

    boolean containsVertex(V v);

    boolean containsEdge(E e);

    int getSize();

    int getOrder();

    Collection<V> getNeighbors(V v);

    Collection<E> getIncidentEdges(V v);

    E findEdge(V a, V b);

    /**
     * For multi-graphs: that allow more than one edge connecting <i>a</i> and <i>b</i>.
     * @param a source vertex
     * @param b target vertex
     * @return Set of edges that connect <i>a</i> to <i>b</i>.
     */
    Collection<E> findEdgeSet(V a, V b);

    boolean addVertex(V v);

    boolean addEdge(E e, Pair<? extends V> p);

    boolean addEdge(E e, Collection<? extends V> vertices);

    boolean addEdge(E e, Collection<? extends V> vertices, EdgeType edge_type);

    boolean removeVertex(V v);

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
}
