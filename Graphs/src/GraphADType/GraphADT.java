/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType;

import GraphADType.Support.TArithmeticOperations;
import EdgeOriented.EdgeEO;
import GraphADType.Support.UnionFind_ADT;
import NodeOriented.Node;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Graph Abstract Data-Type
 * @author nuno
 */
abstract public class GraphADT<T, Y extends Comparable<Y>> {

    public UnionFind_ADT<Node<T>> _union_find;

    public GraphADT() {
    }

    public void initUnionFind(){
        this._union_find = new UnionFind_ADT<Node<T>>(this.getNodes());
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

    public void addAllEdges(Collection<EdgeEO<T,Y>> edges){
        for(EdgeEO e : edges){
            addEdge(e.getNode1(), e.getNode2(), (Y) e.getEdge_data());
        }
    }

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
                if (!visited.contains(node2)) {
                    if (getWeight(node1, node2) != null) {
                        total = arithmetic.Add(total, getWeight(node1, node2));
                    }
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

    public Collection<EdgeEO<T, Y>> getUnduplicatedEdges() {
        ArrayList<EdgeEO<T, Y>> edges = new ArrayList<EdgeEO<T, Y>>();
        ArrayList<EdgeEO<T, Y>> edges2rem = new ArrayList<EdgeEO<T, Y>>();
        for (EdgeEO<T, Y> edge : new ArrayList<EdgeEO<T, Y>>(getEdges())) {
            EdgeEO<T, Y> rev_edge = new EdgeEO<T, Y>(edge.getNode2(), edge.getNode1(), edge.getEdge_data());
            edges.add(edge);
            if (!edges2rem.contains(edge)) {
                edges2rem.add(rev_edge);
            }
        }

        edges.removeAll(edges2rem);
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

    public GraphMapAdj<T, Y> toGraphMapAdj() {
        GraphMapAdj<T, Y> graph = new GraphMapAdj<T, Y>(order());

        for (Node node : this.getNodes()) {
            graph.addNode(node);
        }
        for (EdgeEO edge : this.getUnduplicatedEdges()) {
            graph.addEdge(edge.getNode1(), edge.getNode2(), (Y) edge.getEdge_data());
        }
        return graph;
    }

    public GraphArraySucc<T, Y> toGraphArraySucc() {
        GraphArraySucc<T, Y> graph = new GraphArraySucc<T, Y>(order());

        for (Node node : this.getNodes()) {
            graph.addNode(node);
        }
        for (EdgeEO edge : this.getUnduplicatedEdges()) {
            graph.addEdge(edge.getNode1(), edge.getNode2(), (Y) edge.getEdge_data());
        }
        return graph;
    }

    public GraphMatrix<T, Y> toGraphMatrix() {
        GraphMatrix<T, Y> graph = new GraphMatrix<T, Y>(order());
        for (Node node : this.getNodes()) {
            graph.addNode(node);
        }
        for (EdgeEO edge : this.getUnduplicatedEdges()) {
            graph.addEdge(edge.getNode1(), edge.getNode2(), (Y) edge.getEdge_data());
        }
        return graph;
    }

    public GraphMapSucc<T, Y> toGraphMapSucc() {
        GraphMapSucc<T, Y> graph = new GraphMapSucc<T, Y>(order());
        for (Node<T> node : this.getNodes()) {
            graph.addNode(node);
        }
        for (EdgeEO edge : this.getUnduplicatedEdges()) {
            graph.addEdge(edge.getNode1(), edge.getNode2(), (Y) edge.getEdge_data());
        }
        return graph;
    }
}
