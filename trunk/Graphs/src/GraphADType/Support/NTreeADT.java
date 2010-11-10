/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType.Support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Implementation of n-ary tree for auxiliary purposes.
 * @author nuno
 */
public class NTreeADT<X> {

    X data;
    NTreeADT parent;
    ArrayList<NTreeADT> childs;
    boolean visited;

    public NTreeADT(X data) {
        this.data = data;
        this.childs = new ArrayList<NTreeADT>();
        this.parent = null;
        this.visited = false;
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

    // http://www.codeproject.com/KB/java/BFSDFS.aspx
    /**
     * Returns the elements in the tree in a BFS fashion.
     * @return
     */
    public Collection<X> BFSElements() {
        NTreeADT<X> rootNode = this;
        Collection<X> ret = new ArrayList<X>();
        //BFS uses Queue data structure
        Queue<NTreeADT<X>> q = new LinkedList<NTreeADT<X>>();
        q.add(rootNode);
        ret.add(rootNode.data);
        rootNode.visited = true;
        while (!q.isEmpty()) {
            NTreeADT<X> n = q.remove();
            NTreeADT child = null;


            for(NTreeADT<X> ch : n.childs){
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
        System.out.println(root.BFSElements());
    }
}
