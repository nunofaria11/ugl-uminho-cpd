/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType.Support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Implementation of n-ary tree for auxiliary purposes.
 * @author nuno
 */
public class NTreeADT<X> {

    public X data;
    public NTreeADT parent;
    public Set<NTreeADT<X>> childs;
    public boolean visited;

    public NTreeADT(X data) {
        this.data = data;
        this.childs = new HashSet<NTreeADT<X>>();
        this.parent = null;
        this.visited = false;
    }

    public Set<NTreeADT<X>> getChilds() {
        return childs;
    }

    public X getData() {
        return data;
    }

    public NTreeADT getParent() {
        return parent;
    }

    public boolean isVisited() {
        return visited;
    }

    public boolean hasChildren() {
        return !childs.isEmpty();
    }

    public boolean addChild(NTreeADT<X> child) {
        child.parent = this;
        return childs.add(child);
    }

    public boolean addChild(X child_data) {
        NTreeADT child = new NTreeADT(child_data);
        child.parent = this;
        return addChild(child);
    }

    public boolean addChilds(Collection<X> col) {
        boolean ret = true;
        for (X ch : col) {
            ret &= addChild(ch);
        }
        return ret;
    }

    // http://www.codeproject.com/KB/java/BFSDFS.aspx
    /**
     * Returns the elements in the tree in a BFS fashion.
     * @return
     */
    public Collection<X> BFSTreeElements() {
        NTreeADT<X> rootNode = this;
        Collection<X> ret = new ArrayList<X>();
        //BFS uses Queue data structure
        Queue<NTreeADT<X>> q = new LinkedList<NTreeADT<X>>();
        q.add(rootNode);
        ret.add(rootNode.data);
        rootNode.visited = true;
        while (!q.isEmpty()) {
            NTreeADT<X> n = q.remove();
            for (NTreeADT<X> ch : n.childs) {
                ch.visited = true;
                ret.add(ch.data);
                q.add(ch);
            }
        }
        //Clear visited property of nodes
        return ret;
    }
//

    /**
     * Returns the number of nodes
     * @return size Number of tree nodes (including the empty tree root node)
     */
    public int size() {

        if (childs.isEmpty()) {
            return 1;
        }
        int t = 1;
        for (NTreeADT ch : childs) {
            t += ch.size();
        }
        return t;

    }

    private String toStringChilds() {
        StringBuilder s = new StringBuilder();
        for (NTreeADT ch : childs) {
            s.append(ch.data);
            s.append(", ");
        }
        return s.toString();
    }

    @Override
    public String toString() {
        return "{" + "data=" + data + ", childs=" + toStringChilds() + ", parent=" + ((parent != null) ? (parent.data) : ("null")) + "}";
    }

    public static void main(String[] args) {
        NTreeADT<String> root = new NTreeADT<String>("");
        NTreeADT<String> A = new NTreeADT<String>("A");

        A.addChild("AA");
        A.addChild("AB");
        A.addChild("AC");

        root.addChild(A);
        root.addChild("B");
        root.addChild("C");

        System.out.println(root.size());
        System.out.println(root.BFSTreeElements());
    }
}
