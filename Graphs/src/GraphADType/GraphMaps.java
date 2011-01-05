/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType;

import GraphADT_2nd_try.Pair;
import EdgeOriented.Edge;
import JungTest.EdgeJ;
import JungTest.EdgeJHandler;
import java.io.Serializable;
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
public class GraphMaps<T extends Serializable, Y extends Comparable<Y>> extends GraphADT<T, Y> implements Serializable {

    private static final long serialVersionUID = 671373748115885087L;
    //
    Map<T, Set<EdgeJ<Y>>> verts;
    Map<EdgeJ<Y>, Pair<T>> edges;
    private EdgeJHandler edge_handler;

    public GraphMaps() {
        this.edge_handler = new EdgeJHandler();
    }

    @Override
    public void addNodes(int n) {
        verts = new HashMap<T, Set<EdgeJ<Y>>>(n);
        edges = new HashMap<EdgeJ<Y>, Pair<T>>();
    }

    @Override
    public int order() {
        return verts.keySet().size();
    }

    @Override
    public int size() {
        return edges.keySet().size();
    }

    @Override
    public boolean addNode(T node) {
        if (verts.containsKey(node)) {
            return false;
        }
        Set<EdgeJ<Y>> edge_list = new HashSet<EdgeJ<Y>>();
        verts.put(node, edge_list);
        return true;
    }

    @Override
    public boolean isNode(T node) {
        return verts.containsKey(node);
    }

    @Override
    public void addArc(T n1, T n2, Y w) {
        EdgeJ<Y> edge = new EdgeJ<Y>(w, edge_handler);
        edges.put(edge, new Pair<T>(n1, n2));
    }

    @Override
    public void addEdge(T n1, T n2, Y w) {
        addArc(n1, n2, w);
    }

    @Override
    public boolean isArc(T n1, T n2) {
        if (!verts.containsKey(n1) || !verts.containsKey(n2)) {
            return false;
        }
        Set<EdgeJ<Y>> node_edges = verts.get(n1);
        node_edges.addAll(verts.get(n2));
        for (EdgeJ<Y> edge : node_edges) {
            T f = edges.get(edge).getFirst();
            T s = edges.get(edge).getSecond();
            if (n1.equals(f) && n2.equals(s) || (n1.equals(s) && n2.equals(f))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Y getWeight(T n1, T n2) {
        if (!verts.containsKey(n1) || !verts.containsKey(n2)) {
            return null;
        }
        Set<EdgeJ<Y>> node_edges = verts.get(n1);
        node_edges.addAll(verts.get(n2));
        for (EdgeJ<Y> edge : node_edges) {
            T f = edges.get(edge).getFirst();
            T s = edges.get(edge).getSecond();
            if (n1.equals(f) && n2.equals(s) || (n1.equals(s) && n2.equals(f))) {
                return edge.data;
            }
        }
        return null;
    }

    @Override
    public T getRandom() {
        return (T) verts.keySet().toArray()[new Random().nextInt(order())];
    }

    @Override
    public Collection<Edge<T, Y>> getNeighborEdges(T node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GraphADT clone() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void clean() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<T> getNodes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
