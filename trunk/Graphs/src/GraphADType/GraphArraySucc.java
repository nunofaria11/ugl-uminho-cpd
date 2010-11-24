package GraphADType;

import EdgeOriented.EdgeEO;
import NodeOriented.Node;

import GraphADType.Support.Constants;
import GraphADType.Support.TArithmeticOperations;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author nuno
 */
public class GraphArraySucc<T, Y extends Comparable<Y>> extends GraphADT<T, Y> implements Serializable {

    HashMap<Node<T>, Integer> _index;
    Object[] _succs;
    Object[] _weights;
    int avail_index;
    int total_num_edges;

    private void _allocate(int n) {
        _index = new HashMap<Node<T>, Integer>(n);
        int max_num_edges = Constants.possibleEdgesNum(n) * 2 /* *2 for double edges*/;
        _succs = new Object[max_num_edges];
        _weights = new Object[max_num_edges];
        avail_index = 0;
        total_num_edges = max_num_edges;
    }

    public GraphArraySucc() {
        _allocate(0);
    }

    public GraphArraySucc(int n) {
        _allocate(n);
    }

    public GraphArraySucc(HashMap<Node<T>, Integer> _index, Object[] _succs, Object[] _weights, int avail_index) {
        this._index = (HashMap<Node<T>, Integer>) _index.clone();
        this._succs = _succs.clone();
        this._weights = _weights.clone();
        this.avail_index = avail_index;
    }

    @Override
    public boolean addNode(Node<T> node) {
        _index.put(node, -1);
        return true;
    }

    @Override
    public void addNodes(int n) {
        _allocate(n);
    }

    @Override
    public int order() {
        return _index.keySet().size();
    }

    @Override
    public boolean isNode(Node<T> node) {
        return _index.keySet().contains(node);
    }

    @Override
    public void addArc(Node<T> n1, Node<T> n2, Y w) {
        if (avail_index >= _succs.length) {
//            System.out.println("ALERT!!!: " + avail_index + " > " + _succs.length);
            return;
        }
        if (!isNode(n1)) {
            addNode(n1);
        }
        if (!isNode(n2)) {
            addNode(n2);
        }

        if (_index.get(n1) == -1) {
            // it means it is empty
            _index.put(n1, avail_index);
            _succs[avail_index] = n2;
            _weights[avail_index] = w;

        } else {
            // it is not empty and shift is needed...
            // when inserting in the middle of the arrays - check made in shift function
            int index = _index.get(n1);
            shiftNextIndices(n1);

            _succs[index] = n2;
            _weights[index] = w;
        }
        avail_index++;
        

    }

    private void shift(Object[] array, int index) {
        Object[] tmp = new Object[(array.length - index) - 1];
        for (int i = 0; i < tmp.length && i + index < array.length; i++) {
            tmp[i] = array[i + index];
        }
        System.arraycopy(tmp, 0, array, index + 1, tmp.length);
    }

    private void shiftIndexMap(Node<T> node) {
        // get list of nodes to change
        ArrayList<Node<T>> nodes = new ArrayList<Node<T>>(_index.keySet());
        ArrayList<Node<T>> nodes2change = new ArrayList<Node<T>>();
        nodes.remove(node);
        for (Node n : nodes) {
            if (_index.get(n) >= _index.get(node) && !nodes2change.contains(n)) {
                nodes2change.add(n);
            }
        }
        for (Node n : nodes2change) {
            int x = _index.get(n);
            _index.put(n, x + 1);
        }

    }

    private void shiftNextIndices(Node<T> node) {
        // shift the index hashmap
        shiftIndexMap(node);
        // when shifted the _index map right - also shift all elements from that
        // shifting index in the _succ and weight array
        int index = _index.get(node);
        shift(_succs, index);
        shift(_weights, index);
    }

    @Override
    public void addEdge(Node<T> n1, Node<T> n2, Y w) {
        addArc(n1, n2, w);
        addArc(n2, n1, w);
    }

    @Override
    public boolean isArc(Node<T> n1, Node<T> n2) {
        if (!_index.containsKey(n1)) {
            return false;
        }
        if (_index.get(n1) == -1) {
            return false;
        }
        int index = _index.get(n1);
        Collection<Integer> indices = _index.values();
        indices.remove(index);// indices has beginning indices of every other node
        for (int i = index; i < avail_index && !indices.contains(i); i++) {
            if (n2.equals((Node<T>) _succs[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Y getWeight(Node<T> n1, Node<T> n2) {
//        if (!_index.containsKey(n1)) {
//            return null;
//        }

        if (_index.get(n1) == -1) {
            return null;
        }
        int index = _index.get(n1);
        // need to create a new collection or the item would be removed from the graph
        Collection<Integer> indices = new ArrayList<Integer>(_index.values());
        indices.remove(index);// indices has beginning indices of every other node
        for (int i = index; i < avail_index && !indices.contains(i); i++) {
            if (n2.equals((Node<T>) _succs[i])) {
                return (Y) _weights[i];
            }
        }
        return null;
    }

    @Override
    public Collection<Node<T>> getNodes() {
        return new ArrayList<Node<T>>(_index.keySet());
    }

    public Node<T> getRandom() {
        return (Node<T>) _index.keySet().toArray()[new Random().nextInt(order())];
    }

    private int getMaxIndex() {
        ArrayList<Integer> indices = new ArrayList<Integer>(_index.values());
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < indices.size(); i++) {
            if (max < indices.get(i)) {
                max = indices.get(i);
            }
        }
        return max;
    }

    private int getNextHigherIndex(int curr_index) {
        ArrayList<Integer> indices = new ArrayList<Integer>(_index.values());
        // get higher index in _succ array
        Integer next = getMaxIndex();
        // try to find another index higher 'current' but lower than the maximum
        for (int i = 0; i < indices.size(); i++) {
            if (indices.get(i) != i && indices.get(i) < next && indices.get(i) > curr_index) {
                next = indices.get(i);
            }
        }
        // check if it is last element (20==20 but it may have more the 20 edges)
        if (curr_index == next) {
            int x;
            for (x = next; x < _succs.length && _succs[x] != null && x < avail_index; x++) {
            }
            next = x;
        }
        return next;
    }

    @Override
    public Collection<EdgeEO<T, Y>> getNeighborEdges(Node<T> node) {
        ArrayList<EdgeEO<T, Y>> edges = new ArrayList<EdgeEO<T, Y>>();
        int index = _index.get(node);
        int next_higher_index = getNextHigherIndex(index);
//        System.out.println("Next Higher Index: " + next_higher_index);
//        System.out.println("Avail index: " + avail_index + "\n");
        for (int i = index; i < next_higher_index; i++) {
            if (_succs[i] != null) {
//                System.out.println("Succ: " + _succs[i]);
                EdgeEO<T, Y> e = new EdgeEO<T, Y>(node, (Node<T>) _succs[i], (Y) _weights[i]);
                if (e.getNode2() != null && e.getEdge_data() != null) {
                    edges.add(e);
                }
            }
        }
        return edges;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("ArraySucc:\n");
        for (Node<T> node : _index.keySet()) {
            s.append(node.toString()).append(" ::: ");
            s.append(getNeighborEdges(node));
            s.append("\n");
        }
        return s.toString();
    }

    @Override
    public GraphADT clone() {
        return new GraphArraySucc(_index, _succs, _weights, avail_index);
    }
    /*
     * Debuging Methods
     */

    public String toStringSuccs() {
        StringBuilder s = new StringBuilder();
        for (Object o : _succs) {
            if (o != null) {
                s.append(((Node<T>) o).toString()).append(", ");
            }
        }
        return s.toString();
    }

    public String toStringWeights() {
        StringBuilder s = new StringBuilder();
        for (Object o : _weights) {
            if (o != null) {
                s.append(((Y) o).toString()).append(", ");
            }
        }
        return s.toString();
    }

    public String stateString() {
        StringBuilder s = new StringBuilder();
        s.append("Index: ").append(this._index);
        s.append("\n");
        s.append("Succs: ").append(this.toStringSuccs());
        s.append("\n");
        s.append("Weights: ").append(this.toStringWeights());
        s.append("\n");
        s.append("avail_index: ").append(this.avail_index);
        s.append("\n");
        return s.toString();
    }

    @Override
    public void clean() {
        _allocate(order()); // need to re-allocate another N nodes - if it were 0 it would cause errors
    }

    public GraphMapSucc<T, Y> toGraphMapSucc() {
        GraphMapSucc<T, Y> graph = new GraphMapSucc<T, Y>(order());

        for (Node node : this.getNodes()) {
            graph.addNode(node);
        }
        for (EdgeEO edge : this.getEdges()) {
            graph.addEdge(edge.getNode1(), edge.getNode2(), (Y) edge.getEdge_data());
        }
        return graph;
    }

    public GraphMapAdj<T, Y> toGraphMapAdj() {
        GraphMapAdj<T, Y> graph = new GraphMapAdj<T, Y>(order());

        for (Node node : this.getNodes()) {
            graph.addNode(node);
        }
        for (EdgeEO edge : this.getEdges()) {
            graph.addEdge(edge.getNode1(), edge.getNode2(), (Y) edge.getEdge_data());
        }
        return graph;
    }

    public static void main(String[] args) {
        GraphArraySucc<String, Double> g = new GraphArraySucc<String, Double>(7);

        Node<String> n0 = new Node<String>("A");
        Node<String> n1 = new Node<String>("B");
        Node<String> n2 = new Node<String>("C");
        Node<String> n3 = new Node<String>("D");
        Node<String> n4 = new Node<String>("E");
        Node<String> n5 = new Node<String>("F");
        Node<String> n6 = new Node<String>("G");

        g.addNode(n0);
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        g.addNode(n4);
        g.addNode(n5);
        g.addNode(n6);

//        System.out.println(g.stateString());

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

//        System.out.println();
//        System.out.println(g.stateString());

//        System.out.println(g.getNeighborEdges(n0)+"\n");
//        System.out.println(g.getNeighborEdges(n1)+"\n");
//        System.out.println(g.getNeighborEdges(n2)+"\n");
        System.out.println(g.toString());
        double total3 = 0.0;
        TArithmeticOperations<Double> arith = new TArithmeticOperations<Double>() {

            public Double Add(Double a, Double b) {
                return a + b;
            }

            public Double Cat(Double a, Double b) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public Double null_element() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        System.out.println(g.getMstWeight(arith, total3));

        System.out.println(g.stateString());
        System.out.println("Max Index: " + g.getMaxIndex());
        System.out.println("Next Higher Index: " + g.getNextHigherIndex(g.getMaxIndex()));

        System.out.println(g.getNeighborEdges(n6));
    }
}
