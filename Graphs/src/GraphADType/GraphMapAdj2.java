/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType;

import EdgeOriented.EdgeEO;
import NodeOriented.Node;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author nuno
 */
public class GraphMapAdj2<T, Y extends Comparable<Y>> extends GraphADT<T, Y> implements Serializable {

    private static final long serialVersionUID = -4330765585850038580L;
    // The edges
    HashMap<Node<T>, Queue<EdgeEO<T, Y>>> _adj_map;

    private HashMap<Node<T>, Queue<EdgeEO<T, Y>>> _allocate(int n) {
        HashMap<Node<T>, Queue<EdgeEO<T, Y>>> map = new HashMap<Node<T>, Queue<EdgeEO<T, Y>>>(n);
        map.clear();
        for (Node<T> node : map.keySet()) {
            map.put(node, new PriorityQueue<EdgeEO<T, Y>>());
        }
        return map;
    }

    @Override
    public void addNodes(int n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int order() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addNode(Node<T> node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isNode(Node<T> node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addArc(Node<T> n1, Node<T> n2, Y w) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addEdge(Node<T> n1, Node<T> n2, Y w) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isArc(Node<T> n1, Node<T> n2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Y getWeight(Node<T> n1, Node<T> n2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Node<T> getRandom() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<EdgeEO<T, Y>> getNeighborEdges(Node<T> node) {
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
    public Collection<Node<T>> getNodes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
