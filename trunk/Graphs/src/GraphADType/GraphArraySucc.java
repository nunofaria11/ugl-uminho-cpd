package GraphADType;

import NodeOriented.Node;
import Utilities.Constants;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author nuno
 */
public class GraphArraySucc<T, Y> extends GraphADT<T, Y> {

    HashMap<Node<T>, Integer> _index;
    Object[] _succs;
    Object[] _weights;
    int avail_index;

    private void _allocate(int n) {
        _index = new HashMap<Node<T>, Integer>(n);
        int max_num_edges = Constants.posibleEdgesNum(n) * 2 /* *2 for double edges*/;
        _succs = new Object[max_num_edges];
        _weights = new Object[max_num_edges];
        avail_index = 0;
    }

    public GraphArraySucc() {
        _allocate(0);
    }

    public GraphArraySucc(int n) {
        _allocate(n);
    }

    public void addVertex(Node<T> node) {
        _index.put(node, -1);
    }

    @Override
    public void addVertices(int n) {
        _allocate(n);
    }

    @Override
    public int order() {
        return _index.keySet().size();
    }

    private boolean isVertex(Node<T> node) {
        return _index.keySet().contains(node);
    }

    @Override
    public void addArc(Node<T> n1, Node<T> n2, Y w) {
        if (!isVertex(n1)) {
            addVertex(n1);
        }
        if (!isVertex(n2)) {
            addVertex(n2);
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

    private void shiftNextIndices(Node<T> node) {
        for (Node n : _index.keySet()) {
            if (_index.get(n) >= _index.get(node) && !node.equals(n) && _index.get(n) != -1) {
                _index.put(n, _index.get(n) + 1);
            }
        }
        // when shifted the _index map right - also shift all elements from that
        // shifting index in the _succ and weight array
        int index = _index.get(node);
        System.arraycopy(_succs, index, _succs, index + 1, avail_index - index);
        System.arraycopy(_weights, index, _weights, index + 1, avail_index - index);

    }

    @Override
    public void addEdge(Node<T> n1, Node<T> n2, Y w) {
        addArc(n1, n2, w);
        addArc(n2, n1, w);
    }

    @Override
    public boolean isArc(Node<T> n1, Node<T> n2) {
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
        if (_index.get(n1) == -1) {
            return null;
        }
        int index = _index.get(n1);
        Collection<Integer> indices = _index.values();
        indices.remove(index);// indices has beginning indices of every other node
        for (int i = index; i < avail_index && !indices.contains(i); i++) {
            if (n2.equals((Node<T>) _succs[i])) {
                return (Y) _weights[i];
            }
        }
        return null;
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet.");
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

    public static void main(String[] args) {
        GraphArraySucc<String, Double> g = new GraphArraySucc<String, Double>(7);

        Node<String> n0 = new Node<String>("0");
        Node<String> n1 = new Node<String>("1");
        Node<String> n2 = new Node<String>("2");
        Node<String> n3 = new Node<String>("3");
        Node<String> n4 = new Node<String>("4");
        Node<String> n5 = new Node<String>("5");
        Node<String> n6 = new Node<String>("6");

        g.addVertex(n0);
        g.addVertex(n1);
        g.addVertex(n2);
        g.addVertex(n3);
        g.addVertex(n4);
        g.addVertex(n5);
        g.addVertex(n6);

        System.out.println(g.stateString());

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

        System.out.println();
        System.out.println(g.stateString());

    }
}
