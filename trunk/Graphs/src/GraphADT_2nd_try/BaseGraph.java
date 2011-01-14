/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADT_2nd_try;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author nuno
 */
public abstract class BaseGraph<V extends Serializable, E> implements Graph<V, E> {


    /**
     * Returns a new empty instance with the type of the original graph
     * @return
     */
    abstract public BaseGraph<V,E> create();

    public boolean addEdge(E edge, Collection<? extends V> verts, EdgeType edge_type) {
        if (verts.size() == 2) {
            return this.addEdge(edge, (verts instanceof Pair) ? ((Pair<V>) verts) : (new Pair<V>(verts)), edge_type);
        } else if (verts.size() == 1) {
            // self-loop
            V vertex = verts.iterator().next();
            return this.addEdge(edge, new Pair<V>(vertex, vertex), edge_type);
        } else {
            throw new IllegalArgumentException("Graphs connect 1 or 2 vertices; it has " + verts.size());
        }
    }

    abstract public boolean addEdge(E edge, V v1, V v2);
//    {
//        return this.addEdge(edge, v1, v2, this.getDefaultEdgeType());
//    }

    abstract public boolean addEdge(E edge, Pair<? extends V> endpoints);
//    {
//        return this.addEdge(edge, endpoints, this.getDefaultEdgeType());
//    }

    abstract public boolean addEdge(E edge, Collection<? extends V> verts);
//    {
//        return this.addEdge(edge, verts, this.getDefaultEdgeType());
//    }

    protected Pair<V> getValidatedEndpoints(E edge, Pair<? extends V> endpoints) {

        if (edge == null) {
            throw new IllegalArgumentException("Edge cannot be null.");
        }
        if (endpoints == null) {
            throw new IllegalArgumentException("Endpoints cannot be null.");
        }

        Pair<V> new_endpoints = new Pair<V>(endpoints.first, endpoints.second);

        if (containsEdge(edge)) {
            Pair<V> existing_endpoints = this.getEndpoints(edge);
            if (!existing_endpoints.equals(new_endpoints)) {
                throw new IllegalArgumentException("Endpoints for this edge (" + edge.toString() + ") laready exist (" + existing_endpoints.toString() + ")");
            } else {
                return null;
            }
        }
        return new_endpoints;
    }

    public int inDegree(V vertex) {
        return this.getInEdges(vertex).size();
    }

    public int outDegree(V vertex) {
        return this.getOutEdges(vertex).size();
    }

    public boolean isNeighbor(V v1, V v2) {
        if (!containsVertex(v1) || !containsVertex(v2)) {
            throw new IllegalArgumentException("Both vertices must exist in graph");
        }
        return this.getNeighbors(v1).contains(v2);
    }

    public int getNeighborCount(V v) {
        if (!containsVertex(v)) {
            throw new IllegalArgumentException("Vertex must exist in graph");
        }
        return this.getNeighbors(v).size();
    }

    public int degree(V v) {
        if (!containsVertex(v)) {
            throw new IllegalArgumentException("Vertex must exist in graph");
        }
        return this.getIncidentEdges(v).size();
    }

    public V getOpposite(V vertex, E edge) {
        Pair<V> pair = this.getEndpoints(edge);
        V f = pair.first;
        V s = pair.second;
        if (f.equals(vertex)) {
            return s;
        }
        if (s.equals(vertex)) {
            return f;
        } else {
            throw new IllegalArgumentException(vertex + " is not incident on " + edge);
        }
    }

    public E findEdge(V v1, V v2) {
        for (E edge : getOutEdges(v1)) {
            if (getOpposite(v1, edge).equals(v2)) {
                return edge;
            }
        }
        for (E edge : getOutEdges(v2)) {
            if (getOpposite(v2, edge).equals(v1)) {       
                return edge;
            }
        }
        return null;
    }

    public Collection<E> findEdgeSet(V v1, V v2){

        if(!containsVertex(v1) || !containsVertex(v2))
            throw new IllegalArgumentException("Both vertices must exist");

        Collection<E> edges = new ArrayList<E>();

        for(E e : getOutEdges(v1)){
            if(getOpposite(v1, e).equals(v2))
                edges.add(e);
        }
        return Collections.unmodifiableCollection(edges);
    }

    abstract public V getRandomNode();

    abstract public boolean connected();

    
}
