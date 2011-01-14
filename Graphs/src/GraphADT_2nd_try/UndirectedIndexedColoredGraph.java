/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADT_2nd_try;

import GraphIO.GraphInput;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author nuno
 */
public class UndirectedIndexedColoredGraph<V extends Serializable, E extends Comparable<E>> extends UndirectedIndexedGraph<V, E> implements Visitable<V, E> {

    private static final long serialVersionUID = -1850848173131722321L;

    enum Color {

        VISITED,
        NOT_VISITED,
        NOT_VISITABLE // for cyclic control
    };
    // structures to mark the visited nodes and/or edges
    Map<V, Color> vis_verts;
    Map<E, Color> vis_edges;

    public UndirectedIndexedColoredGraph() {
        super();
        vis_verts = new HashMap<V, Color>();
        vis_edges = new HashMap<E, Color>();
    }

    public UndirectedIndexedColoredGraph(BaseGraph<V, E> g) {

//        if (g instanceof UndirectedGraph || g instanceof UndirectedIndexedGraph) {
//        BaseGraph<V, E> ug = g.create();
        Collection<V> ug_verts = g.getVertices();
        Collection<E> ug_edges = g.getEdges();
        vis_verts = new HashMap<V, Color>();
        vis_edges = new HashMap<E, Color>();
        for (V v : ug_verts) {
            this.addVertex(v);
//            vis_verts.put(v, Color.NOT_VISITED);
        }
        for (E e : ug_edges) {
            Pair<V> pair = g.getEndpoints(e);
            this.addEdge(e, pair.getFirst(), pair.getSecond(), EdgeType.UNDIRECTED);
//            vis_edges.put(e, Color.NOT_VISITED);
        }
//        } else {
//            throw new IllegalArgumentException("Graph instance is not Undirected");
//        }

    }

    /**
     * Constructor to build a colored undirected graph from a normal undirected
     * graph.
     * @param ug UndirectedGraph
     */
    public UndirectedIndexedColoredGraph(UndirectedGraph<V, E> ug) {
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
        indices.put(v, new Pair<Integer>(avail_index, avail_index));
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

    public static boolean implementsInterface(Object object, Class interf) {
        return interf.isInstance(object);
    }

    @Override
    public String toString() {
        return "Colored " + super.toString();
    }

    public static void main2(String[] args) {
        UndirectedIndexedGraph<String, EdgeJ<Integer>> g = new UndirectedIndexedGraph<String, EdgeJ<Integer>>();
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addVertex("D");
        g.addVertex("E");
        g.addVertex("F");
        g.addVertex("G");
        EdgeJHandler handler = new EdgeJHandler();
        g.addEdge(new EdgeJ<Integer>(7, handler), "A", "B");
        g.addEdge(new EdgeJ<Integer>(8, handler), "B", "C");
        g.addEdge(new EdgeJ<Integer>(5, handler), "A", "D");
        g.addEdge(new EdgeJ<Integer>(9, handler), "B", "D");
        g.addEdge(new EdgeJ<Integer>(5, handler), "C", "E");
        g.addEdge(new EdgeJ<Integer>(6, handler), "D", "F");
        g.addEdge(new EdgeJ<Integer>(7, handler), "B", "E");
        g.addEdge(new EdgeJ<Integer>(8, handler), "E", "F");
        g.addEdge(new EdgeJ<Integer>(15, handler), "D", "E");
        g.addEdge(new EdgeJ<Integer>(11, handler), "F", "G");
        g.addEdge(new EdgeJ<Integer>(9, handler), "E", "G");
        UndirectedIndexedColoredGraph<String, EdgeJ<Integer>> gc = new UndirectedIndexedColoredGraph<String, EdgeJ<Integer>>(g);
        System.out.println(gc);
    }

    public static void main(String[] args) {

        GraphInput gin = new GraphInput("../graph_50.g2");

        UndirectedIndexedGraph<String, EdgeJ<Integer>> g =
                new UndirectedIndexedGraph<String, EdgeJ<Integer>>(gin.readGraph2());

        System.out.println(g);
    }
}
