/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType;

import EdgeOriented.EdgeEO;
import NodeOriented.Node;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 *
 * @author nuno
 */
public class GraphMapAdj<T, Y extends Comparable<Y>> extends GraphADT<T, Y> implements Serializable {

    HashMap<Node<T>, List<EdgeEO<T, Y>>> _adj_map;

    private HashMap<Node<T>, List<EdgeEO<T, Y>>> _allocate(int n) {
        HashMap<Node<T>, List<EdgeEO<T, Y>>> map = new HashMap<Node<T>, List<EdgeEO<T, Y>>>(n);
        map.clear();
        for (Node<T> node : map.keySet()) {
            map.put(node, new ArrayList<EdgeEO<T, Y>>());
        }
        return map;
    }

    public GraphMapAdj(int n) {
        _adj_map = _allocate(n);
    }

    public GraphMapAdj() {
        _adj_map = _allocate(0);
    }

    public GraphMapAdj(HashMap<Node<T>, List<EdgeEO<T, Y>>> _adj_map) {
        this._adj_map = (HashMap<Node<T>, List<EdgeEO<T, Y>>>) _adj_map.clone();
    }

    @Override
    public boolean addNode(Node<T> node) {
        if (isNode(node)) { // is already exists do not add vertex
            return false;
        }
        _adj_map.put(node, new ArrayList<EdgeEO<T, Y>>());
        return true;
    }

    @Override
    public boolean isNode(Node<T> node) {
        return _adj_map.keySet().contains(node);
    }

    @Override
    public void addNodes(int n) {
        _adj_map = _allocate(n);
    }

    @Override
    public int order() {
        return _adj_map.keySet().size();
    }

    @Override
    public void addArc(Node<T> n1, Node<T> n2, Y w) {
        List<EdgeEO<T, Y>> nbors = _adj_map.get(n1);
        if (nbors == null) {
            nbors = new ArrayList<EdgeEO<T, Y>>();
        }
        EdgeEO<T, Y> edge = new EdgeEO<T, Y>(n1, n2, (Y) w);
        nbors.add(edge);
        _adj_map.put(n1, nbors);
    }

    @Override
    public void addEdge(Node<T> n1, Node<T> n2, Y w) {
        addArc(n1, n2, w);
        addArc(n2, n1, w);
    }

    @Override
    public boolean isArc(Node n1, Node n2) {
        List<EdgeEO<T, Y>> nbors = _adj_map.get(n1);
        for (EdgeEO<T, Y> edge : nbors) {
            if (edge.getNode2().equals(n2)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Y getWeight(Node n1, Node n2) {
        List<EdgeEO<T, Y>> nbors = _adj_map.get(n1);
        for (EdgeEO edge : nbors) {
            if (edge.getNode2().equals(n2)) {
                return (Y) edge.getEdge_data();
            }
        }
        return null;
    }

    @Override
    public Collection<Node<T>> getNodes() {
        return new ArrayList<Node<T>>(_adj_map.keySet());
    }

    public HashMap<Node<T>, List<EdgeEO<T, Y>>> getAdj_map() {
        return _adj_map;
    }

    public Node<T> getRandom() {
        return (Node<T>) _adj_map.keySet().toArray()[new Random().nextInt(order())];
    }

    @Override
    public GraphADT clone() {
        return new GraphMapAdj(_adj_map);
    }

    @Override
    public void clean() {
        _adj_map = _allocate(0);
    }

    @Override
    public Collection<EdgeEO<T, Y>> getNeighborEdges(Node<T> node) {
        return _adj_map.get(node);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("MapAdj:\n");
        for (Node<T> node : _adj_map.keySet()) {
            s.append(node.toString());
            s.append(": ");
            s.append(_adj_map.get(node).toString());
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        GraphMapAdj<String, Double> g = new GraphMapAdj<String, Double>();
        g.addNodes(7);

//        Node<Integer> n0 = new Node<Integer>(0);
//        Node<Integer> n1 = new Node<Integer>(1);
//        Node<Integer> n2 = new Node<Integer>(2);
//        Node<Integer> n3 = new Node<Integer>(3);
//        Node<Integer> n4 = new Node<Integer>(4);
//        Node<Integer> n5 = new Node<Integer>(5);
//        Node<Integer> n6 = new Node<Integer>(6);
        Node<String> n0 = new Node<String>("A");
        Node<String> n1 = new Node<String>("B");
        Node<String> n2 = new Node<String>("C");
        Node<String> n3 = new Node<String>("D");
        Node<String> n4 = new Node<String>("E");
        Node<String> n5 = new Node<String>("F");
        Node<String> n6 = new Node<String>("G");


        g.addEdge(n0, n1, 7.1);
        g.addEdge(n0, n3, 5.2);
        g.addEdge(n1, n2, 8.3);
        g.addEdge(n1, n3, 9.4);
        g.addEdge(n1, n4, 7.5);
        g.addEdge(n2, n4, 5.6);
        g.addEdge(n3, n4, 15.7);
        g.addEdge(n3, n5, 6.8);
        g.addEdge(n4, n5, 8.9);
        g.addEdge(n4, n6, 9.10);
        g.addEdge(n5, n6, 11.11);

        System.out.println(g.toString());
    }
}
