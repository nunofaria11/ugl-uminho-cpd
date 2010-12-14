package GraphADType;

import EdgeOriented.Edge;
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

    private static final long serialVersionUID = 6990005528953386502L;
    HashMap<T, Integer> _index;
    Object[] _succs;
    Object[] _weights;
    int avail_index;
    int total_num_edges;

    private void _allocate(int n) {
        _index = new HashMap<T, Integer>(n);
        int max_num_edges = Constants.possibleEdgesNum(n);/* *2 for double edges*/
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

    public GraphArraySucc(HashMap<T, Integer> _index, Object[] _succs, Object[] _weights, int avail_index) {
        this._index = (HashMap<T, Integer>) _index.clone();
        this._succs = _succs.clone();
        this._weights = _weights.clone();
        this.avail_index = avail_index;
    }

    @Override
    public boolean addNode(T node) {
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
    public int size() {
        return avail_index;
    }

    @Override
    public boolean isNode(T node) {
        return _index.keySet().contains(node);
    }

    @Override
    public void addArc(T n1, T n2, Y w) {
        if (avail_index >= _succs.length) {
            return;
        }
        // *** if the reverse arc already exists, dont add
        if (isArc(n2, n1)) {
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

    private void shiftIndexMap(T node) {
        // get list of nodes to change
        ArrayList<T> nodes = new ArrayList<T>(_index.keySet());
        ArrayList<T> nodes2change = new ArrayList<T>();
        nodes.remove(node);
        for (T n : nodes) {
            if (_index.get(n) >= _index.get(node) && !nodes2change.contains(n)) {
                nodes2change.add(n);
            }
        }
        for (T n : nodes2change) {
            int x = _index.get(n);
            _index.put(n, x + 1);
        }

    }

    private void shiftNextIndices(T node) {
        // shift the index hashmap
        shiftIndexMap(node);
        // when shifted the _index map right - also shift all elements from that
        // shifting index in the _succ and weight array
        int index = _index.get(node);
        shift(_succs, index);
        shift(_weights, index);
    }

    @Override
    public void addEdge(T n1, T n2, Y w) {
        addArc(n1, n2, w);
//        addArc(n2, n1, w);
    }

    @Override
    public boolean isArc(T n1, T n2) {
        if (!_index.containsKey(n1) || !_index.containsKey(n2)) {
            return false;
        }
        if (_index.get(n1) == -1) {
            return false;
        }

        int index = _index.get(n1);
        Collection<Integer> indices = new ArrayList<Integer>(_index.values());
        indices.remove(index);// indices has beginning indices of every other node
        for (int i = index; i < avail_index && !indices.contains(i); i++) {
            if (n2.equals((T) _succs[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Y getWeight(T n1, T n2) {
        if (!isArc(n1, n2)) {
            T tmp = n1;
            n1 = n2;
            n2 = tmp;
        }
        int index = _index.get(n1);
        // need to create a new collection or the item would be removed from the graph
        Collection<Integer> indices = new ArrayList<Integer>(_index.values());
        indices.remove(index);// indices has beginning indices of every other node
        for (int i = index; i < avail_index && !indices.contains(i); i++) {
            if (n2.equals((T) _succs[i])) {
                return (Y) _weights[i];
            }
        }
        return null;
    }

    @Override
    public Collection<T> getNodes() {
        return new ArrayList<T>(_index.keySet());
    }

    public T getRandom() {
        return (T) _index.keySet().toArray()[new Random().nextInt(order())];
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

    /**
     * Returns incoming edges of node
     * @param node
     * @return
     */
    private Collection<Edge<T, Y>> incomingEdges(T node) {
        ArrayList<Edge<T, Y>> edges = new ArrayList<Edge<T, Y>>();
        //reverse edge search in _succs
        for (T n : _index.keySet()) {
            int index = _index.get(n);
            if (index != -1) {
                for (int i = index; i < getNextHigherIndex(index); i++) {
                    T target = (T) _succs[i];
                    if (target.equals(node)) {
                        Edge e = new Edge(n, node, _weights[i]);
                        edges.add(e);
                    }
                }
            }
        }
        return edges;
    }

    /**
     * Returns outgoing edges of node
     * @param node
     * @return
     */
    private Collection<Edge<T, Y>> outgoingEdges(T node) {
        ArrayList<Edge<T, Y>> edges = new ArrayList<Edge<T, Y>>();
        //outgoing edges of node
        int index = _index.get(node);
        for (int i = index; i < getNextHigherIndex(index); i++) {
            if (i == -1) {
                continue;
            }
            if (_succs[i] != null) {
                Edge<T, Y> e = new Edge<T, Y>(node, (T) _succs[i], getWeight(node, (T) _succs[i]));
                if (e.getNode2() != null && e.getEdge_data() != null) {
                    edges.add(e);
                }
            }
        }
        return edges;
    }

    @Override
    public Collection<Edge<T, Y>> getNeighborEdges(T node) {
        ArrayList<Edge<T, Y>> edges = new ArrayList<Edge<T, Y>>();
        edges.addAll(incomingEdges(node));
        edges.addAll(outgoingEdges(node));
        return edges;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("ArraySucc:\n");
        for (T node : _index.keySet()) {
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
                s.append(((T) o).toString()).append(", ");
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

    public static void main(String[] args) {
        GraphArraySucc<String, Integer> g = new GraphArraySucc<String, Integer>(7);

        String n0 = "A";
        String n1 = "B";
        String n2 = "C";
        String n3 = "D";
        String n4 = "E";
        String n5 = "F";
        String n6 = "G";

        g.addNode(n0);
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        g.addNode(n4);
        g.addNode(n5);
        g.addNode(n6);
        System.out.println(g._index);

        System.out.println(g.stateString());

        g.addEdge(n0, n1, 7);
        g.addEdge(n0, n3, 5);
        g.addEdge(n1, n2, 8);
        g.addEdge(n1, n3, 9);
        g.addEdge(n1, n4, 7);
        g.addEdge(n2, n4, 5);
        g.addEdge(n3, n4, 15);
        g.addEdge(n3, n5, 6);
        g.addEdge(n4, n5, 8);
        g.addEdge(n4, n6, 9);
        g.addEdge(n5, n6, 11);


        System.out.println(g.isArc("B", "A"));

        System.out.println(g.getWeight("B", "A"));

        System.out.println(g.stateString());
        System.out.println(g.getNeighborEdges("B"));


//        double total3 = 0.0;
/*        TArithmeticOperations<Double> arith = new TArithmeticOperations<Double>() {

        public Double Add(Double a, Double b) {
        return a + b;
        }

        public Double Cat(Double a, Double b) {
        throw new UnsupportedOperationException("Not supported yet.");
        }

        public Double zero_element() {
        throw new UnsupportedOperationException("Not supported yet.");
        }
        };*/
//        System.out.println(g.getMstWeight(arith, total3));

//        System.out.println(g.stateString());
//        System.out.println("Max Index: " + g.getMaxIndex());
//        System.out.println("Next Higher Index: " + g.getNextHigherIndex(g.getMaxIndex()));

//        System.out.println(g.getNeighborEdges(n6));
    }
}
