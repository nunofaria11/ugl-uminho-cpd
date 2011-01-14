/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADT_2nd_try;

import GraphADType.Support.TArithmeticOperations;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author nuno
 */
public class UndirectedIndexedGraph<V extends Serializable, E extends Comparable<E>> extends BaseGraph<V, E> implements Undirected<V, E>, Serializable {

    private static final long serialVersionUID = -1841996080223673322L;
    Map<V, Pair<Integer>> indices;
    ArrayList<V> successors;
    ArrayList<E> edges;
    int avail_index;

    public UndirectedIndexedGraph() {
        indices = new HashMap<V, Pair<Integer>>();
        successors = new ArrayList<V>();
        edges = new ArrayList<E>();
        avail_index = 0;
    }

    public UndirectedIndexedGraph(BaseGraph<V, E> g) {
        indices = new HashMap<V, Pair<Integer>>();
        successors = new ArrayList<V>();
        edges = new ArrayList<E>();
        avail_index = 0;
        Collection<V> ug_verts = g.getVertices();
        Collection<E> ug_edges = g.getEdges();
        for (V v : ug_verts) {
            this.addVertex(v);
        }
        for (E e : ug_edges) {
            Pair<V> pair = g.getEndpoints(e);
            this.addEdge(e, pair.getFirst(), pair.getSecond(), EdgeType.UNDIRECTED);
        }
    }

    @Override
    public BaseGraph<V, E> create() {
        return new UndirectedIndexedGraph<V, E>();
    }

    @Override
    public boolean addEdge(E edge, V v1, V v2) {
        if (!containsVertex(v1) || !containsVertex(v2)) {
            throw new IllegalArgumentException("Both vertices must exist in graph.");
        }
        if (containsEdge(edge)) {
            throw new IllegalArgumentException("Edge already exists.");
        }
        if (indices.get(v1).first == indices.get(v1).second) {
//            System.out.println("inserting indices of " + v1 + "-(" + edge + ")->" + v2 + " [" + avail_index + "," + (avail_index + 1) + "]");
            indices.put(v1, new Pair<Integer>(avail_index, avail_index + 1));
            successors.add(v2);
            edges.add(edge);

        } else {
            // if a pair already exists...change ending index
            Pair<Integer> inds = indices.get(v1);
            inds.setSecond(inds.second + 1);
            indices.put(v1, inds);
//            System.out.println("update indices of " + v1 + "-(" + edge + ")->" + v2 + " [" + inds.first + "," + inds.second + "]");
            // shifting indices on successors and edges are needed
            // the add(index, element) operation on ArrayList already
            // performs a shift

            // also need to shift all index elements forward in the
            // indices map - UPDATE INDEX MAP
//          *******************
            for (V key : indices.keySet()) {
                Pair<Integer> pair = indices.get(key);
                if (pair.second > inds.first && pair.first != pair.second && !v1.equals(key)) {
//                    System.out.println("***** updating " + key);
                    indices.put(key, new Pair<Integer>(pair.first + 1, pair.second + 1));
                }
            }

            //
            successors.add(inds.second - 1, v2);
            edges.add(inds.second - 1, edge);
        }
        avail_index++;
//        printState();
//        System.out.println();
//        System.out.println("Added: " + v1 + "-(" + edge + ")->" + v2 + "\t[" + indices.get(v1).first + "," + indices.get(v1).second + "]");
        return true;
    }

    @Override
    public boolean addEdge(E edge, Pair<? extends V> endpoints) {
        V v1 = endpoints.first;
        V v2 = endpoints.second;
        return this.addEdge(edge, v1, v2);
    }

    @Override
    public boolean addEdge(E edge, Collection<? extends V> verts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public V getRandomNode() {
        return (V) indices.keySet().toArray()[new Random().nextInt(getOrder())];
    }

    @Override
    public boolean connected() {
        // just check if all nodes are some other nodes target
        Collection<V> allnodes = getVertices();
        // visited array
        ArrayList<V> visited = new ArrayList<V>();
        ArrayList<E> alledges = new ArrayList<E>(getEdges());
        for (E edge : alledges) {
            Pair<V> endpoints = getEndpoints(edge);
            visited.add(endpoints.first);
            // needs to visit both vertices because it is an Undirected graph
            // if it is directed only add one vertice
            visited.add(endpoints.second);
        }
        return visited.containsAll(allnodes);
    }

    public Collection<E> getEdges() {
        return edges;
    }

    public Collection<V> getVertices() {
        return indices.keySet();
    }

    public boolean containsVertex(V v) {
        return indices.containsKey(v);
    }

    public boolean containsEdge(E e) {
        return edges.contains(e);
    }

    public int getSize() {
        return avail_index;
    }

    public int getOrder() {
        return indices.keySet().size();
    }

    public Collection<V> getNeighbors(V v) {
        if (!containsVertex(v)) {
            throw new IllegalArgumentException("Vertex must exist in graph");
        }
        // the same than getIncidentEdges but on the successors array
        Pair<Integer> ipair = indices.get(v);
        return successors.subList(ipair.first, ipair.second);
    }

    public Collection<E> getIncidentEdges(V v) {
        // the same than getIncidentEdges but on the successors array
        if (!containsVertex(v)) {
            throw new IllegalArgumentException("Vertex must exist in graph");
        }
        Pair<Integer> ipair = indices.get(v);
        List<E> incident = new ArrayList<E>();
        if (ipair.first != ipair.second) {
            for (int i = ipair.first; i < ipair.second; i++) {
                incident.add(edges.get(i));
            }
        }
        for (V key : indices.keySet()) {
            if (!v.equals(key)) {
                Pair<Integer> ind_pair = indices.get(key);
                for (int i = ind_pair.first; i < ind_pair.second; i++) {
                    if (successors.get(i).equals(v)) {
                        incident.add(edges.get(i));
                    }
                }
            }
        }
        return incident;
    }

    public boolean addVertex(V v) {
        if (containsVertex(v)) {
//            throw new IllegalArgumentException("Vertex already exists.");
            return false;
        }
        indices.put(v, new Pair<Integer>(avail_index, avail_index));
        return true;
    }

    public boolean addEdge(E e, V v1, V v2, EdgeType edge_type) {
        if (edge_type == EdgeType.DIRECTED) {
            throw new IllegalArgumentException("Edges in this graph must be undirected.");
        }
        return addEdge(e, v1, v2);
    }

    public boolean removeEdge(E v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isIncident(V v, E e) {
        if (!containsEdge(e)) {
            throw new IllegalArgumentException("Edge must exist in graph");
        }
        return getIncidentEdges(v).contains(e);
    }

    /**
     * Returns number of vertices that are incident on edge
     * @param edge
     * @return
     */
    public int getIncidentCount(E edge) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public EdgeType getEdgeType(E edge) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public EdgeType getDefaultEdgeType() {
        return EdgeType.UNDIRECTED;
    }

    public int getEdgeCount(EdgeType edge_type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection<E> getInEdges(V v) {
        return getIncidentEdges(v);
    }

    public Collection<E> getOutEdges(V v) {
        return getIncidentEdges(v);
    }

    public Collection<V> getPredecessors(V v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection<V> getSuccessors(V v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public V getSource(E directed_edge) {
        if (!containsEdge(directed_edge)) {
            throw new IllegalArgumentException("Edge must exist in graph");
        }
        int index = edges.indexOf(directed_edge);
        for (V key : indices.keySet()) {
            Pair<Integer> pair = indices.get(key);
            if (index >= pair.first && index < pair.second) {
                return key;
            }
        }
        throw new Error("Error in mapping edges: this edge does not have a source vertex");
    }

    public V getTarget(E directed_edge) {
        int index = edges.indexOf(directed_edge);
        return successors.get(index);
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

    public Pair<V> getEndpoints(E e) {
        V first = getSource(e);
        V second = getTarget(e);
        return new Pair<V>(first, second);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Undirected Indexed Array Graph\n");
        for (V v : getVertices()) {
            s.append(v).append(" : ").append(getIncidentEdges(v)).append("\n");
        }
        return s.toString();
    }

    public void printState() {
        System.out.println("avail_index:\t" + avail_index);
        System.out.print("Indices:\t");
        for (V k : indices.keySet()) {
            System.out.print(k + "= [" + indices.get(k).first + ", " + indices.get(k).second + "],");
        }
        System.out.println();
        System.out.println("Successors:\t" + successors);
        System.out.println("Edges:\t" + edges);
    }

    public static void main(String[] args) {

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

//        g.printState();

        System.out.println(g);

//        Collection<EdgeJ<Integer>> inc_edges = g.getIncidentEdges("E");
//        for(EdgeJ e : inc_edges){
//            System.out.println("Endpoints: "+ e+"\t"+g.getEndpoints(e));
//        }

        Kruskal2<String, EdgeJ<Integer>> kruskal = new Kruskal2<String, EdgeJ<Integer>>();
        UndirectedIndexedGraph<String, EdgeJ<Integer>> mst = (UndirectedIndexedGraph<String, EdgeJ<Integer>>) kruskal.getMst(g);
        System.out.println(mst);
        /*
         * MST weight
         */
        TArithmeticOperations<EdgeJ<Integer>> arith = new TArithmeticOperations<EdgeJ<Integer>>() {

            EdgeJHandler handler = new EdgeJHandler();

            public EdgeJ<Integer> Add(EdgeJ<Integer> a, EdgeJ<Integer> b) {
                return new EdgeJ<Integer>(a.data + b.data, handler);
            }

            public EdgeJ<Integer> Cat(EdgeJ<Integer> a, EdgeJ<Integer> b) {
                return Add(a, b);
            }

            public EdgeJ<Integer> zero_element() {
                return new EdgeJ<Integer>(0, handler);
            }
        };

        System.out.println("MST weight: " + kruskal.getMstWeight(mst, arith).data);



    }
}
