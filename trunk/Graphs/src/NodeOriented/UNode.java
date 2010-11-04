/*
 * To be used in an undirected graph.
 */
package NodeOriented;

import EdgeOriented.EdgeEO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nuno
 */
public class UNode<T, Y extends Comparable<Y>> extends Node<T> {

    List<EdgeEO<T, Y>> edges;

    public UNode(T data) {
        super(data);
        edges = new ArrayList<EdgeEO<T, Y>>();
    }

//    public UNode(List<EdgeEO<T, Y>> edges) {
//        super();
//        this.edges = edges;
//    }
//
//    public UNode(T data, List<EdgeEO<T, Y>> edges) {
//        super(data);
//        this.edges = edges;
//    }

    /**
     * Adds edge to node - makes sure that the edge has its source or destiny in
     * the node we want to add the edge to, and ensures that it is not a loop-edge
     * (laço?).
     * @param edge
     * @return boolean successful adding or not
     */
    public boolean add(EdgeEO<T, Y> edge) {
        if ((edge.getNode1().equals(this) || edge.getNode2().equals(this))
                && !edge.getNode1().equals(edge.getNode2())) {
            return edges.add(edge);
        }
        return false;
    }

    public boolean remove(EdgeEO<T, Y> e) {
        return edges.remove(e);
    }

    public boolean has(EdgeEO<T, Y> edge) {
        return edges.contains(edge);
    }

    public boolean hasNeighbor(Node<T> node) {
        for (EdgeEO<T, Y> e : edges) {
            if (e.getNode1().equals(node) || e.getNode2().equals(node)) {
                return true;
            }
        }
        return false;
    }

    public List<EdgeEO<T, Y>> getEdges() {
        return edges;
    }


    public int degree(){
        return edges.size();
    }
}
