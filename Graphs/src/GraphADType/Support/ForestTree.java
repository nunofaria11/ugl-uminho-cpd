/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType.Support;

import NodeOriented.Node;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.apache.commons.collections15.map.FastHashMap;

/**
 * NOTE: The UnionFind structure is not yet prepared to support edge info.
 *      But when building the MST graph from the NTreeADT instance we could also
 *      pass the original graph so that we could access the edge info
 * @author nuno
 */
public class ForestTree<X> implements Forest<X> {

    private HashMap<X, NTreeADT<X>> _tree;

    public ForestTree() {
        this._tree = new HashMap<X, NTreeADT<X>>();
    }

    public ForestTree(Collection<X> elements) {
        this._tree = new HashMap<X, NTreeADT<X>>(elements.size());
        // at first it adds an empty tree (with no childs) to the structure
        for (X el : elements) {
            _tree.put(el, new NTreeADT<X>(el));
        }
    }

    public X find(X x) {
        NTreeADT t = _tree.get(x);
        while (t.parent != null) {
            t = t.parent;
        }
        return (X) t.data;
    }

    public boolean find(X p, X q) {
        return find(p).equals(find(q));
    }

    public void union(X p, X q) {
        X i = find(p); // get root of i
        X j = find(q); // get root of j
        if (i.equals(j)) {
            return;
        }
        NTreeADT ii = _tree.get(i);
        NTreeADT jj = _tree.get(j);
        // if ii has more
        if (jj.childs.size() <= ii.childs.size()) {
            jj.parent = ii;
            ii.addChild(jj);
            _tree.put(j, jj);
            Collection<X> childs_of_jj = jj.childs;
            ii.addChilds(childs_of_jj);
        } else if (ii.childs.size() < jj.childs.size()) {
            ii.parent = jj;
            jj.addChild(ii);
            _tree.put(i, ii);
            Collection<X> childs_of_ii = ii.childs;
            jj.addChilds(childs_of_ii);
        }
    }

    public void fill(Collection<X> col) {
        this._tree = new FastHashMap<X, NTreeADT<X>>(col.size());
        // at first it adds an empty tree (with no childs) to the structure
        for (X el : col) {
            _tree.put(el, new NTreeADT<X>(el));
        }
    }


    public NTreeADT getMST() {

        NTreeADT mst_tree = null;
        for (X key : _tree.keySet()) {
            if (mst_tree == null) {
                mst_tree = _tree.get(key);
            }
            if (_tree.get(key).childs.size() > mst_tree.childs.size()) {
                // it meants it is still empty
                mst_tree = _tree.get(key);
            }

        }
        return mst_tree;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (X key : _tree.keySet()) {
            s.append(key.toString()).append("::");
            s.append("childs: ").append(_tree.get(key).childs);
            s.append("; ");
        }
        return s.toString();
    }

    public static void main(String[] args) {

        ArrayList<Node<String>> nodes = new ArrayList<Node<String>>();
        Node<String> A = new Node("A");
        Node<String> B = new Node("B");
        Node<String> C = new Node("C");
        Node<String> D = new Node("D");
        nodes.add(A);
        nodes.add(B);
        nodes.add(C);
        nodes.add(D);

        ForestInteger_ADT<Node<String>> uf_adt = new ForestInteger_ADT<Node<String>>(nodes);
        System.out.println(uf_adt.toString());
        uf_adt.union(A, B);
        System.out.println(uf_adt.toString());
        uf_adt.union(A, C);
        System.out.println(uf_adt.toString());

        System.out.println();

        ForestTree<Node<String>> uf_tree = new ForestTree<Node<String>>(nodes);
        System.out.println(uf_tree.toString());
        uf_tree.union(B, D);
        System.out.println(uf_tree.toString());
        uf_tree.union(A, C);
        System.out.println(uf_tree.toString());
        uf_tree.union(A, D);
        System.out.println(uf_tree.toString());


        System.out.println();

        for (Node<String> node : uf_tree._tree.keySet()) {
            System.out.println(node + ":::" + uf_tree._tree.get(node).childs.size());
        }

        System.out.println();
        System.out.println(uf_tree.getMST());

        System.out.println();
        System.out.println(uf_tree.getMST().BFSTreeElements());


    }
}