/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType;

import GraphADType.Support.TArithmeticOperations;
import EdgeOriented.EdgeEO;
import NodeOriented.Node;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Graph Abstract Data-Type
 * @author nuno
 */
abstract public class GraphADT<T, Y extends Comparable<Y>> {

    public GraphADT() {
    }

    /**
     * Prepares the graph to receive <b>n</b> nodes.
     *
     * @param n Number of vertices to add
     */
    abstract public void addNodes(int n);

    abstract public int order();

    public int size() {
        int total = 0;
        for (Node<T> n : getNodes()) {
            total += getNeighborEdges(n).size();
        }
        return total;
    }

    /**
     * Adds single node to graph.
     * 
     * @param node Node to add
     * @return boolean Success of node insertion
     */
    abstract public boolean addNode(Node<T> node);

    /**
     * Checks if node exists.
     *
     * @param node
     * @return 
     */
    abstract public boolean isNode(Node<T> node);

    abstract public void addArc(Node<T> n1, Node<T> n2, Y w);

    abstract public void addEdge(Node<T> n1, Node<T> n2, Y w);

    abstract public boolean isArc(Node<T> n1, Node<T> n2);

    abstract public Y getWeight(Node<T> n1, Node<T> n2);

    abstract public Node<T> getRandom();

    abstract public Collection<EdgeEO<T, Y>> getNeighborEdges(Node<T> node);

    @Override
    abstract public String toString();

    @Override
    abstract public GraphADT clone();

    abstract public void clean();

    abstract public Collection<Node<T>> getNodes();

    public Y getMstWeight(TArithmeticOperations<Y> arithmetic, Y total) {
        Collection<Node<T>> allnodes = getNodes();
        ArrayList<Node<T>> visited = new ArrayList<Node<T>>();
        for (Node<T> node1 : allnodes) {
            visited.add(node1);
            for (Node<T> node2 : allnodes) {
                if (getWeight(node1, node2) != null && !visited.contains(node2)) {
                    total = arithmetic.Add(total, getWeight(node1, node2));
                }
            }
        }
        return total;
    }

    public Collection<EdgeEO<T, Y>> getEdges() {
        Collection<EdgeEO<T, Y>> edges = new ArrayList<EdgeEO<T, Y>>();
        for (Node<T> node : getNodes()) {
            edges.addAll(getNeighborEdges(node));
        }
        return edges;
    }

    public boolean connected() {
        // just check if all nodes are some other nodes target
        ArrayList<Node<T>> allnodes = (ArrayList<Node<T>>) getNodes();
        // visited array
        ArrayList<Node<T>> visited = new ArrayList<Node<T>>();
        ArrayList<EdgeEO<T, Y>> alledges = (ArrayList<EdgeEO<T, Y>>) getEdges();
        for (EdgeEO edge : alledges) {
            visited.add(edge.getNode2());
        }
        return visited.containsAll(allnodes);
    }

    public Collection<Node<T>> insertAllNodes(Collection<T> datalist) {
        ArrayList<Node<T>> nodes = new ArrayList<Node<T>>();
        for (T data : datalist) {
            Node<T> new_node = new Node<T>(data);
            addNode(new_node);
            nodes.add(new_node);
        }
        return nodes;
    }
}
