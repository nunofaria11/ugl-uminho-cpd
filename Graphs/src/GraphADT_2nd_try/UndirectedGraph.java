/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADT_2nd_try;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author nuno
 */
public class UndirectedGraph<V, E extends Comparable<E>> extends BaseGraph<V, E> implements Undirected<V, E> {

    // Set so it supports parallel edges
    Map<V, Set<E>> verts;
    // Instead of having the nodes info inside the edges, the nodes are now mapped to an edge
    Map<E, Pair<V>> edges;

    public UndirectedGraph() {
        verts = new HashMap<V, Set<E>>();
        edges = new HashMap<E, Pair<V>>();
    }

    public Collection<E> getEdges() {
        return this.edges.keySet();
    }

    public Collection<V> getVertices() {
        return this.verts.keySet();
    }

    public boolean containsVertex(V v) {
        return this.verts.containsKey(v);
    }

    public boolean containsEdge(E e) {
        return this.edges.containsKey(e);
    }

    public int getSize() {
        return this.edges.keySet().size();
    }

    public int getOrder() {
        return this.verts.keySet().size();
    }

    public Collection<V> getNeighbors(V v) {
        Collection<E> incident_edges = getIncidentEdges(v);
        Collection<V> neighbors = new ArrayList<V>();
        for (E edge : incident_edges) {
            neighbors.add(getOpposite(v, edge));
        }
        return neighbors;
    }

    public Collection<E> getIncidentEdges(V v) {
        return this.verts.get(v);
    }

//    public Object findEdge(Object a, Object b) {
//        return this.findEdge(a, b);
//    }
//    public Collection findEdgeSet(Object a, Object b) {
//        return this.findEdgeSet(a, b);
//    }
    public boolean addVertex(V v) {
        if (containsVertex(v)) {
            return false;
        }
        verts.put(v, new HashSet<E>());
        return true;
    }

//    public boolean addEdge(Object e, Pair p) {
//        return this.addEdge(e, p);
//    }
//    public boolean addEdge(Object e, Object v1, Object v2) {
//        return this.addEdge(e, v1, v2);
//    }
    public boolean addEdge(E e, V v1, V v2, EdgeType edge_type) {

        if (containsEdge(e)) {
            throw new IllegalArgumentException("Edge already exists in graph.");
        }

        if (!containsVertex(v1) || !containsVertex(v2)) {
            System.out.println(v1+"+"+v2);
            throw new IllegalArgumentException("Both vertices must exist in graph.");
        }

        Set<E> edges_of_1 = verts.get(v1);
        edges_of_1.add(e);
        verts.put(v1, edges_of_1);

        Set<E> edges_of_2 = verts.get(v2);
        edges_of_2.add(e);
        verts.put(v2, edges_of_2);

        edges.put(e, new Pair<V>(v1, v2)); 

        return true;
    }

    public boolean removeVertex(Object v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isNeighbor(V a, V b) {
        return super.isNeighbor(a, b);
    }

    public boolean isIncident(V v, E e) {
        if (!containsVertex(v)) {
            throw new IllegalArgumentException("Vertex must exist in graph");
        }
        if (!containsEdge(e)) {
            throw new IllegalArgumentException("Edge must exist in graph");
        }

        Pair<V> endpoints = edges.get(e);
        return v == endpoints.first || v == endpoints.second || v.equals(endpoints.first) || v.equals(endpoints.second);
    }

    @Override
    public int degree(V vertex) {
        return degree(vertex);
    }

    @Override
    public int getNeighborCount(V vertex) {
        return getNeighborCount(vertex);
    }

    public int getIncidentCount(E edge) {
        return getIncidentCount(edge);
    }

    public EdgeType getEdgeType(E edge) {
        return EdgeType.UNDIRECTED;
    }

    public EdgeType getDefaultEdgeType() {
        return EdgeType.UNDIRECTED;
    }

    public int getEdgeCount(EdgeType edge_type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection getInEdges(V v) {
        return getIncidentEdges(v);
    }

    public Collection getOutEdges(V v) {
        return getIncidentEdges(v);
    }

    @Override
    public int inDegree(V v) {
        return getInEdges(v).size();
    }

    @Override
    public int outDegree(V v) {
        return getOutEdges(v).size();
    }

    public Collection getPredecessors(V v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection getSuccessors(V v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public V getSource(E directed_edge) {
        if (!containsEdge(directed_edge)) {
            throw new IllegalArgumentException("Edge must exist in graph");
        }
        return edges.get(directed_edge).first;
    }

    public V getTarget(E directed_edge) {
        if (!containsEdge(directed_edge)) {
            throw new IllegalArgumentException("Edge must exist in graph");
        }
        return edges.get(directed_edge).second;
    }

    public boolean isSource(V v, E edge) {
        if (!containsVertex(v)) {
            throw new IllegalArgumentException("Vertex must exist in graph");
        }
        if (!containsEdge(edge)) {
            throw new IllegalArgumentException("Edge must exist in graph");
        }
        V s = getSource(edge);
        return s == v || s.equals(v);
    }

    public boolean isDest(V v, E edge) {
        if (!containsVertex(v)) {
            throw new IllegalArgumentException("Vertex must exist in graph");
        }
        if (!containsEdge(edge)) {
            throw new IllegalArgumentException("Edge must exist in graph");
        }
        V t = getTarget(edge);
        return t == v || t.equals(v);
    }

    public Pair getEndpoints(E e) {
        if (!containsEdge(e)) {
            throw new IllegalArgumentException("Edge must exist in graph");
        }
        return edges.get(e);
    }

    @Override
    public V getRandomNode() {
        return (V) verts.keySet().toArray()[new Random().nextInt(getOrder())];
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Undirected Graph\n");
        for (V v : getVertices()) {
            s.append(v).append(" : ").append(getIncidentEdges(v)).append("\n");
        }
        return s.toString();
    }

    public boolean removeEdge(E v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    @Override
    public boolean addEdge(E edge, V v1, V v2) {
        return this.addEdge(edge, v1, v2, getDefaultEdgeType());
    }

    @Override
    public boolean addEdge(E edge, Pair<? extends V> endpoints) {
        return this.addEdge(edge, endpoints.first, endpoints.second, getDefaultEdgeType());
    }

    @Override
    public boolean addEdge(E edge, Collection<? extends V> verts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
