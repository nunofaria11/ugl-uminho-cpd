/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType;

import GraphADType.Support.TArithmeticOperations;
import EdgeOriented.Edge;
import GraphADType.Support.NTreeADT;
import GraphADType.Support.UnionFindTree;
import GraphADType.Support.UnionFind_ADT;
import NodeOriented.Node;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Graph Abstract Data-Type
 * @author nuno
 */
abstract public class GraphADT<T, Y extends Comparable<Y>> {

    // Forest implementation - see this better, I think this could be a more large concept
    public UnionFind_ADT<T> _union_find;
    // Abstract implementation of a worklist
    public AbstractCollection<Edge<T, Y>> _worklist;

    public GraphADT() {
    }

    private void addToWorklist(Collection<Edge<T, Y>> col) {
        _worklist.addAll(col);
    }

    public void initWorklist(Class<? extends AbstractCollection> worklist_class) {
        try {
            _worklist = worklist_class.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(GraphADT.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GraphADT.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return Worklist with all edges in graph
     */
    public AbstractCollection<Edge<T, Y>> fillWorklist() {
        addToWorklist(getUnduplicatedEdges());
        return _worklist;
    }

    /**
     * Creates a worklist from the neighbor edges of a node
     * @param n Node
     * @return Worklist with neighbor edges of <b>n</b>.
     */
    public AbstractCollection<Edge<T, Y>> fillWorklist(T n) {
        addToWorklist(getNeighborEdges(n));
        return _worklist;
    }

    public AbstractCollection<Edge<T, Y>> getWorklist() {
        return _worklist;
    }

    public void setWorklist(AbstractCollection<Edge<T, Y>> _worklist) {
        this._worklist = _worklist;
    }

    public void buildGraph(UnionFindTree<T> uf) {

        NTreeADT<T> mst_tree_start = uf.getMST();
        clean();
        addNodes(mst_tree_start.BFSTreeElements().size());
        // queue for child source nodes
        Queue<NTreeADT> q = new LinkedList<NTreeADT>();

        q.add(mst_tree_start);
        while (!q.isEmpty()) {
            NTreeADT<T> source = q.poll();
            if (source.hasChildren()) {
                q.addAll(source.getChilds());
            }

            T sourceNode = source.getData();

            if (!isNode(sourceNode)) {
                addNode(sourceNode);
            }
            for (NTreeADT<T> target : source.getChilds()) {
                T targetNode = target.getData();
                if (!isNode(targetNode)) {
                    addNode(targetNode);
                }
            }
        }

    }

    public void initUnionFind() {
        this._union_find = new UnionFind_ADT<T>(this.getNodes());
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
        for (T n : getNodes()) {
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
    abstract public boolean addNode(T node);

    /**
     * Checks if node exists.
     *
     * @param node
     * @return
     */
    abstract public boolean isNode(T node);

    abstract public void addArc(T n1, T n2, Y w);

    abstract public void addEdge(T n1, T n2, Y w);

    public void addAllEdges(Collection<Edge<T, Y>> edges) {
        for (Edge e : edges) {
            addEdge((T) e.getNode1(), (T) e.getNode2(), (Y) e.getEdge_data());
        }
    }

    abstract public boolean isArc(T n1, T n2);

    abstract public Y getWeight(T n1, T n2);

    abstract public T getRandom();

    abstract public Collection<Edge<T, Y>> getNeighborEdges(T node);

    @Override
    abstract public String toString();

    @Override
    abstract public GraphADT clone();

    abstract public void clean();

    abstract public Collection<T> getNodes();

    public Y getMstWeight(TArithmeticOperations<Y> arithmetic, Y total) {
        Collection<T> allnodes = getNodes();
        ArrayList<T> visited = new ArrayList<T>();
        for (T node1 : allnodes) {
            visited.add(node1);
            for (T node2 : allnodes) {
                if (!visited.contains(node2)) {
                    if (getWeight(node1, node2) != null) {
                        total = arithmetic.Add(total, getWeight(node1, node2));
                    }
                }
            }
        }
        return total;
    }

    public Collection<Edge<T, Y>> getEdges() {
        Collection<Edge<T, Y>> edges = new ArrayList<Edge<T, Y>>();
        for (T node : getNodes()) {
            edges.addAll(getNeighborEdges(node));
        }
        return edges;
    }

    public Collection<Edge<T, Y>> getUnduplicatedEdges() {
        ArrayList<Edge<T, Y>> edges = new ArrayList<Edge<T, Y>>();
        ArrayList<Edge<T, Y>> edges2rem = new ArrayList<Edge<T, Y>>();
        for (Edge<T, Y> edge : new ArrayList<Edge<T, Y>>(getEdges())) {
            Edge<T, Y> rev_edge = new Edge<T, Y>(edge.getNode2(), edge.getNode1(), edge.getEdge_data());
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
        ArrayList<T> allnodes = (ArrayList<T>) getNodes();
        // visited array
        ArrayList<T> visited = new ArrayList<T>();
        ArrayList<Edge<T, Y>> alledges = (ArrayList<Edge<T, Y>>) getEdges();
        for (Edge edge : alledges) {
            visited.add((T) edge.getNode2());
        }
        return visited.containsAll(allnodes);
    }

    public Collection<T> insertAllNodes(Collection<T> datalist) {
        ArrayList<T> nodes = new ArrayList<T>();
        for (T data : datalist) {
            addNode(data);
            nodes.add(data);
        }
        return nodes;
    }

    public GraphMapAdj<T, Y> toGraphMapAdj() {
        GraphMapAdj<T, Y> graph = new GraphMapAdj<T, Y>(order());

        for (T node : this.getNodes()) {
            graph.addNode(node);
        }
        for (Edge edge : this.getUnduplicatedEdges()) {
            graph.addEdge((T) edge.getNode1(), (T) edge.getNode2(), (Y) edge.getEdge_data());
        }
        return graph;
    }

    public GraphArraySucc<T, Y> toGraphArraySucc() {
        GraphArraySucc<T, Y> graph = new GraphArraySucc<T, Y>(order());

        for (T node : this.getNodes()) {
            graph.addNode(node);
        }
        for (Edge edge : this.getUnduplicatedEdges()) {
            graph.addEdge((T) edge.getNode1(), (T) edge.getNode2(), (Y) edge.getEdge_data());
        }
        return graph;
    }

    public GraphMatrix<T, Y> toGraphMatrix() {
        GraphMatrix<T, Y> graph = new GraphMatrix<T, Y>(order());
        for (T node : this.getNodes()) {
            graph.addNode(node);
        }
        for (Edge edge : this.getUnduplicatedEdges()) {
            graph.addEdge((T) edge.getNode1(), (T) edge.getNode2(), (Y) edge.getEdge_data());
        }
        return graph;
    }

    public GraphMapSucc<T, Y> toGraphMapSucc() {
        GraphMapSucc<T, Y> graph = new GraphMapSucc<T, Y>(order());
        for (T node : this.getNodes()) {
            graph.addNode(node);
        }
        for (Edge edge : this.getUnduplicatedEdges()) {
            graph.addEdge((T) edge.getNode1(), (T) edge.getNode2(), (Y) edge.getEdge_data());
        }
        return graph;
    }
}
