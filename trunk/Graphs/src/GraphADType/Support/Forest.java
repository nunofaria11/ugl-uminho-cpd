/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType.Support;

import java.util.Collection;

/**
 *
 * @author nuno
 */
public interface Forest<X> {

    /**
     * Fill the forest with a collection
     * @param col Collection to insert in the UnionFind structure
     */
    void fill(Collection<X> col);

    /**
     * Finds a root of node <i>x</i>.
     * @param x
     * @return root of <i>x</i>
     */
    X find(X x);

    /**
     * Checks if the nodes <i>p</i> and <i>q</i> are in the same subtree.
     * @param p
     * @param q
     * @return true if nodes are in the same tree.
     */
    boolean find(X p, X q);

    /**
     * Unites the subtree of <i>p</i> and <i>q</i>.
     * @param p
     * @param q
     */
    void union(X p, X q);

    /**
     * Checks if the UnionFind structure has already found an MST -
     * the element with more children is the MST candidate
     * @return NTreeADT that has the MST, if it is not <b>null</b>
     */
    NTreeADT getMST();
}
