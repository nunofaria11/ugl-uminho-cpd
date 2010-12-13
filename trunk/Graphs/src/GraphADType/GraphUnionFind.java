/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType;

import EdgeOriented.Edge;
import GraphADType.Support.UnionFindTree;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author nuno
 */
public class GraphUnionFind<T, Y extends Comparable<Y>> extends GraphADT<T, Y> implements Serializable {

    private static final long serialVersionUID = 7366196200306982820L;
    /*
     * FLAG control for NTreeADT node:
     *  0 ==> node from the original graph, not in the MST
     *  1 ==> MST node
     *  2 ==> original and MST graph node
     */
    // tree
    UnionFindTree _tree;

    @Override
    public void addNodes(int n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int order() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addNode(T node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isNode(T node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addArc(T n1, T n2, Y w) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addEdge(T n1, T n2, Y w) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isArc(T n1, T n2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Y getWeight(T n1, T n2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public T getRandom() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<Edge<T, Y>> getNeighborEdges(T node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GraphADT clone() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void clean() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<T> getNodes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
