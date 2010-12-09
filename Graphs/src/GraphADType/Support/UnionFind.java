/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphADType.Support;

/**
 *
 * @author nuno
 */
public interface UnionFind<X> {

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

}
