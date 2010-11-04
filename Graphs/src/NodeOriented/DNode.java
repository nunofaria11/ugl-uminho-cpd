/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NodeOriented;

import EdgeOriented.EdgeEO;
import java.util.ArrayList;
import java.util.List;

/**
 * Directed Node.
 * @author nuno
 */
public class DNode<T, Y extends Comparable<Y>> extends Node<T> {

    List<EdgeEO<T, Y>> in;
    List<EdgeEO<T, Y>> out;

    public DNode(T data) {
        super(data);
        in = new ArrayList<EdgeEO<T, Y>>();
        out = new ArrayList<EdgeEO<T, Y>>();
    }

    public boolean addIn(EdgeEO<T, Y> edge) {
        return in.add(edge);

    }

    public boolean addOut(EdgeEO<T, Y> edge) {
        return out.add(edge);

    }

    public boolean removeIn(EdgeEO<T, Y> edge) {
        return in.remove(edge);
    }

    public boolean removeOut(EdgeEO<T, Y> edge) {
        return out.remove(edge);
    }

    public List<EdgeEO<T, Y>> getInEdges() {
        return in;
    }

    public List<EdgeEO<T, Y>> getOutEdges() {
        return out;
    }

    /**
     * Checks if has IN arc 'edge'.
     * @param edge
     * @return boolean
     */
    public boolean hasIn(EdgeEO<T, Y> edge) {
        return in.contains(edge);
    }

    /**
     * Checks if has OUT arc 'edge'.
     * @param edge
     * @return boolean
     */
    public boolean hasOut(EdgeEO<T, Y> edge) {
        return out.contains(edge);
    }

// hasInNeighbour
    public boolean hasInNeighbor(Node<T> node) {
        for (EdgeEO edge : in) {
            if (edge.contains(node)) {
                return true;
            }
        }
        return false;
    }
// hasOutNeighbor

    public boolean hasOutNeighbor(Node<T> node) {
        for (EdgeEO edge : out) {
            if (edge.contains(node)) {
                return true;
            }
        }
        return false;
    }

    public int inDegree() {
        return in.size();
    }

    public int outDegree() {
        return out.size();
    }

    public int degree(){
        return inDegree()+outDegree();
    }
}
