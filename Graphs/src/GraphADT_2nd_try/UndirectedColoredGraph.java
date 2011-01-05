/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADT_2nd_try;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Undirected graph implementation with colors to check 
 * for visited VERTICES only. It is directed at eliminating
 * the use of auxiliary structures such as visited vertex
 * or edge arrays.
 *
 * For this implementation check only visitable vertices.
 *
 * However, the edges will also be visited to control the
 * cycles that may happen, i.e., to mark as NOT_VISITABLE
 * the target vertices the edges point to (see Prim algorithm).
 *
 * @author nuno
 */
public class UndirectedColoredGraph<V extends Serializable, E extends Comparable<E>> extends UndirectedGraph<V, E> implements Visitable<V, E> {

    private static final long serialVersionUID = -1850848173131722321L;

    enum Color {

        VISITED,
        NOT_VISITED,
        NOT_VISITABLE // for cyclic control
    };
    // structures to mark the visited nodes and/or edges
    Map<V, Color> vis_verts;
    Map<E, Color> vis_edges;

    public UndirectedColoredGraph() {
        super();
        vis_verts = new HashMap<V, Color>();
        vis_edges = new HashMap<E, Color>();
    }

    public UndirectedColoredGraph(BaseGraph<V, E> g) {

        if (g instanceof UndirectedGraph) {
            UndirectedGraph<V, E> ug = (UndirectedGraph<V, E>) g;
            Collection<V> ug_verts = ug.getVertices();
            Collection<E> ug_edges = ug.getEdges();
            vis_verts = new HashMap<V, Color>();
            vis_edges = new HashMap<E, Color>();
            for (V v : ug_verts) {
                this.addVertex(v);
//            vis_verts.put(v, Color.NOT_VISITED);
            }
            for (E e : ug_edges) {
                Pair<V> pair = ug.getEndpoints(e);
                this.addEdge(e, pair.getFirst(), pair.getSecond(), EdgeType.UNDIRECTED);
//            vis_edges.put(e, Color.NOT_VISITED);
            }
        } else {
            throw new IllegalArgumentException("Graph instance is not Undirected");
        }

    }

    /**
     * Constructor to build a colored undirected graph from a normal undirected
     * graph.
     * @param ug UndirectedGraph
     */
    public UndirectedColoredGraph(UndirectedGraph<V, E> ug) {
        Collection<V> ug_verts = ug.getVertices();
        Collection<E> ug_edges = ug.getEdges();
        vis_verts = new HashMap<V, Color>();
        vis_edges = new HashMap<E, Color>();
        for (V v : ug_verts) {
            this.addVertex(v);
//            vis_verts.put(v, Color.NOT_VISITED);
        }
        for (E e : ug_edges) {
            Pair<V> pair = ug.getEndpoints(e);
            this.addEdge(e, pair.getFirst(), pair.getSecond(), EdgeType.UNDIRECTED);
//            vis_edges.put(e, Color.NOT_VISITED);
        }
    }

    public boolean visitVertex(V vertex) {
        if (!containsVertex(vertex)) {
            throw new IllegalArgumentException("Vertex must exist in graph.");
        }

        if (vis_verts.get(vertex).equals(Color.VISITED) || vis_verts.get(vertex).equals(Color.NOT_VISITABLE)) {
            return false;
        }
        vis_verts.put(vertex, Color.VISITED);
        return true;
    }

    public boolean visitedVertex(V vertex) {
        return vis_verts.get(vertex).equals(Color.VISITED) || vis_verts.get(vertex).equals(Color.NOT_VISITABLE);
    }

    public boolean visitEdge(E edge) {
        if (!containsEdge(edge)) {
            throw new IllegalArgumentException("Edge must exist in graph.");
        }

        vis_edges.put(edge, Color.VISITED);
        // now mark the target vertices of the edge as NOT_VISITABLE if they are not already marked as VISITED
//        Pair<V> pair = getEndpoints(edge);
//        V first = pair.first;
//        V second = pair.second;
//
//        if (!visitedVertex(first)) {
//            vis_verts.put(first, Color.NOT_VISITABLE);
//            System.out.println("making "+first+" NOT_VISITABLE");
//        }
//        if (!visitedVertex(second)) {
//            vis_verts.put(second, Color.NOT_VISITABLE);
//            System.out.println("making "+second+" NOT_VISITABLE");
//        }
        return true;
    }

    public boolean visitedEdge(E edge) {
        return vis_edges.get(edge).equals(Color.VISITED) || vis_edges.get(edge).equals(Color.NOT_VISITABLE);
    }

    /*
     * NOW OVERRIDE THE addVertex AND addEdge methods in order to use the
     * colored control structures
     */
    @Override
    public boolean addVertex(V v) {
        if (containsVertex(v)) {
            return false;
        }
        verts.put(v, new HashSet<E>());
        vis_verts.put(v, Color.NOT_VISITED);
        return true;
    }

    @Override
    public boolean addEdge(E e, V v1, V v2, EdgeType edge_type) {
        if (super.addEdge(e, v1, v2, edge_type)) {
            vis_edges.put((E) e, Color.NOT_VISITED);
            return true;
        } else {
            return false;
        }
    }
}
