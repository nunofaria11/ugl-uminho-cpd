/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType;

import EdgeOriented.EdgeEO;
import GraphADType.Algorithms.BoruvkaADT;
import NodeOriented.Node;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author nuno
 */
public class GraphMatrix<T, Y extends Comparable<Y>> extends GraphADT<T, Y> implements Serializable {

    Object[][] _matrix;
    // auxilary structures to map the node with the index of rows and columns in the matrix
    HashMap<Node<T>, Integer> _rows;
    HashMap<Node<T>, Integer> _cols;
    // auxiliary field for aiding in the graph construction
    // this value can't be higher than numNodes
    int _avail_index;

    private void _allocate(int N) {
        _matrix = new Object[N][N];
        _rows = new HashMap<Node<T>, Integer>(N);
        _cols = new HashMap<Node<T>, Integer>(N);
        _avail_index = 0;
    }

    public GraphMatrix(Object[][] _matrix, HashMap<Node<T>, Integer> _rows, HashMap<Node<T>, Integer> _cols, int _avail_index) {
        this._matrix = _matrix.clone();
        this._rows = (HashMap<Node<T>, Integer>) _rows.clone();
        this._cols = (HashMap<Node<T>, Integer>) _cols.clone();
        this._avail_index = new Integer(_avail_index);
    }

    public GraphMatrix() {
    }

    public GraphMatrix(int numNodes) {
        _allocate(numNodes);
    }

    @Override
    public void addNodes(int n) {
        _allocate(n);
    }

    @Override
    public int order() {
        return _avail_index;
    }

    @Override
    public boolean addNode(Node node) {
        if (isNode(node)) {
            return false;
        }
        _rows.put(node, new Integer(_avail_index));
        _cols.put(node, new Integer(_avail_index));
        _avail_index++;
        return true;
    }

    @Override
    public boolean isNode(Node node) {
        if (_rows.containsKey(node) && _cols.containsKey(node)) {
            return true;
        }
        return false;
    }

    @Override
    public void addArc(Node n1, Node n2, Comparable w) {
        if (!isNode(n1)) {
            addNode(n1);
        }
        if (!isNode(n2)) {
            addNode(n2);
        }
        int i1 = _rows.get(n1);
        int i2 = _cols.get(n2);
        _matrix[i1][i2] = w;
    }

    @Override
    public void addEdge(Node n1, Node n2, Comparable w) {
        addArc(n1, n2, w);
        addArc(n2, n1, w);
    }

    @Override
    public boolean isArc(Node n1, Node n2) {
        if (!isNode(n1)) {
            return false;
        }
        if (!isNode(n2)) {
            return false;
        }
        int i1 = _rows.get(n1);
        int i2 = _cols.get(n2);
        return (_matrix[i1][i2] != null);
    }

    @Override
    public Y getWeight(Node n1, Node n2) {
        if (!isNode(n1)) {
            return null;
        }
        if (!isNode(n2)) {
            return null;
        }
        int i1 = _rows.get(n1);
        int i2 = _cols.get(n2);
        return (Y) _matrix[i1][i2];
    }

    @Override
    public Node getRandom() {
        return (Node<T>) _cols.keySet().toArray()[new Random().nextInt(order())];
    }

    @Override
    public Collection getNeighborEdges(Node node) {
        ArrayList<EdgeEO<T, Y>> nbors = new ArrayList<EdgeEO<T, Y>>();

        int index = _rows.get(node);
        ArrayList<Node<T>> allOtherNodes = (ArrayList<Node<T>>) getNodes();
        allOtherNodes.remove(node);
        for (Node node2 : allOtherNodes) {
            int j = _cols.get(node2);
            if (index != j && _matrix[index][j] != null) {
                EdgeEO<T, Y> edge = new EdgeEO<T, Y>(node, node2, _matrix[index][j]);
                nbors.add(edge);
            }
        }
        return nbors;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("GraphMatrix:\n");
        ArrayList<Node<T>> allNodes = (ArrayList<Node<T>>) getNodes();
        for (Node i : allNodes) {
            s.append(i).append(" ::: ");
            s.append(getNeighborEdges(i)).append("\n");
        }
        return s.toString();
    }

    @Override
    public GraphADT clone() {
        return new GraphMatrix(_matrix, _rows, _cols, _avail_index);
    }

    @Override
    public void clean() {
        _allocate(0);
    }

    @Override
    public Collection<Node<T>> getNodes() {
        return new ArrayList<Node<T>>(_rows.keySet());
    }

    public static void main(String[] args) {
        GraphMatrix<String, Integer> g = new GraphMatrix<String, Integer>(7);

        Node<String> n0 = new Node<String>("A");
        Node<String> n1 = new Node<String>("B");
        Node<String> n2 = new Node<String>("C");
        Node<String> n3 = new Node<String>("D");
        Node<String> n4 = new Node<String>("E");
        Node<String> n5 = new Node<String>("F");
        Node<String> n6 = new Node<String>("G");

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

        System.out.println(g.toString());


        BoruvkaADT bor = new BoruvkaADT(g);
        GraphADT mst = bor.getMst();
        System.out.println("=============\n" + mst);
        System.out.println(mst.toGraphMapSucc());

    }
}
