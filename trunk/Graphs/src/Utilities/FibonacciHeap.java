/*
 * Copyright: JGraphT.
 */
package Utilities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nuno
 */
public class FibonacciHeap<T> {

    private static final double oneOverLogPhi = 1.0 / Math.log((1.0 + Math.sqrt(5.0)) / 2.0);
    FibonacciHeapNode<T> minNode;
    private int nNodes;

    public boolean isEmpty() {
        return minNode == null;
    }

    public void clear() {
        minNode = null;
        nNodes = 0;
    }

    public void insert(T data, int key) {
        FibonacciHeapNode<T> node = new FibonacciHeapNode<T>(data, key);
        this.insert(node, key);
    }

    public void insert(FibonacciHeapNode<T> node, int key) {
        node.key = key;
        if (minNode == null) {
            minNode = node;
        } else {
            //here we are creating a cycle
            node.left = minNode;
            node.right = minNode.right;
            minNode.right = node;
            node.right.left = node;
            if (key < minNode.key) {
                minNode = node;
            }
        }
        nNodes++;
    }

    public FibonacciHeapNode<T> min() {
        return minNode;
    }

    public FibonacciHeapNode<T> removeMin() {
        FibonacciHeapNode<T> z = minNode;
        if (z != null) {
            int numChilds = z.degree;
            FibonacciHeapNode<T> ch = z.child;
            FibonacciHeapNode<T> tmpRight;
            while (numChilds > 0) {
                tmpRight = ch.right;
                // remove x from child list
                ch.left.right = ch.right;
                ch.right.left = ch.left;
                // add x to root list of heap
                ch.left = minNode;
                ch.right = minNode.right;
                minNode.right = ch;
                ch.right.left = ch;
                // set parent[x] to null
                ch.parent = null;
                ch = tmpRight;
                numChilds--;
            }
            z.right.left = z.left;
            z.left.right = z.right;
            if (z == z.right) {
                minNode = null;
            } else {
                minNode = z.right;
                consolidate();
            }
        }
        return z;
    }

    protected void consolidate() {

        int arraySize = ((int) Math.floor(Math.log(nNodes) * oneOverLogPhi)) + 1;
        List<FibonacciHeapNode<T>> array = new ArrayList<FibonacciHeapNode<T>>(arraySize);

        //initialize degree array
        for (int i = 0; i < arraySize; i++) {
            array.add(null);
        }
        //find number of root nodes
        int numRoots = 0;
        FibonacciHeapNode<T> x = minNode;
        if (x != null) {
            numRoots++;
            x = x.right;
            while (x != minNode) {
                numRoots++;
                x = x.right;
            }
        }
        //for each root node check if there is another root node with the same degree
        while (numRoots > 0) {
            // Access this node's degree..
            int d = x.degree;
            FibonacciHeapNode<T> next = x.right;
            // ..and see if there's another of the same degree.
            for (;;) {
                FibonacciHeapNode<T> y = array.get(d);
                if (y == null) {
                    // Nope.
                    break;
                }
                // There is, make one of the nodes a child of the other.
                // Do this based on the key value.
                if (x.key > y.key) {
                    FibonacciHeapNode<T> temp = y;
                    y = x;
                    x = temp;
                }
                // FibonacciHeapNode<T> y disappears from root list.
                link(y, x);
                // We've handled this degree, go to next one.
                array.set(d, null);
                d++;
            }
            // Save this node for later when we might encounter another
            // of the same degree.
            array.set(d, x);
            // Move forward through list.
            x = next;
            numRoots--;
        }
        // Set min to null (effectively losing the root list) and
        // reconstruct the root list from the array entries in array[].
        minNode = null;

        for (int i = 0; i < arraySize; i++) {
            FibonacciHeapNode<T> y = array.get(i);
            if (y == null) {
                continue;
            }

            // We've got a live one, add it to root list.
            if (minNode != null) {
                // First remove node from root list.
                y.left.right = y.right;
                y.right.left = y.left;

                // Now add to root list, again.
                y.left = minNode;
                y.right = minNode.right;
                minNode.right = y;
                y.right.left = y;

                // Check if this is a new min.
                if (y.key < minNode.key) {
                    minNode = y;
                }
            } else {
                minNode = y;
            }
        }

    }

    /**
     * Make node y child of node x.
     * 
     * @param y node to become a child.
     * @param x node to become a parent.
     */
    protected void link(FibonacciHeapNode<T> y, FibonacciHeapNode<T> x) {
        //remove y from root list of heap
        y.left.right = y.right;
        y.right.left = y.left;
        //make y a child of x
        y.parent = x;
        if (x.child == null) {
            x.child = y;
            y.right = y;
            y.left = y;
        } else {
            y.left = x.child;
            y.right = x.child.right;
            x.child.right = y;
            y.right.left = y;
        }
        //increase degree[x]
        x.degree++;
        //set mark[y] = false
        y.mark = false;
    }

    /**
     * Removes node x from child list of y, adding it to root list.
     *
     * @param x child to be removed from child list of y and added to root list.
     * @param y parent of x about to lose child x.
     */
    public void cut(FibonacciHeapNode<T> x, FibonacciHeapNode<T> y) {
        // remove x from childlist of y and decrement degree[y]
        x.left.right = x.right;
        x.right.left = x.left;
        y.degree--;
        // reset y.child if necessary
        if (y.child == x) {
            y.child = x.right;
        }
        if (y.degree == 0) {
            y.child = null;
        }
        // add x to root list of heap
        x.left = minNode;
        x.right = minNode.right;
        minNode.right = x;
        x.right.left = x;
        // set parent[x] to nil
        x.parent = null;
        // set mark[x] to false
        x.mark = false;
    }

    public void decreaseKey(FibonacciHeapNode<T> x, int k) {
        if (k > x.key) {
            throw new IllegalArgumentException(
                    "decreaseKey() got larger key value");
        }
        x.key = k;

        FibonacciHeapNode<T> y = x.parent;
        if ((y != null) && (x.key < y.key)) {
            cut(x, y);
            cascadingCut(y);
        }

        if (x.key < minNode.key) {
            minNode = x;
        }

    }

    protected void cascadingCut(FibonacciHeapNode<T> y) {
        FibonacciHeapNode<T> z = y.parent;

        // if there's a parent...
        if (z != null) {
            // if y is unmarked, set it marked
            if (!y.mark) {
                y.mark = true;
            } else {
                // it's marked, cut it from parent
                cut(y, z);

                // cut its parent as well
                cascadingCut(z);
            }
        }
    }

    /**
     * Deletes a node from the heap given the reference to the node. The trees
     * in the heap will be consolidated, if necessary. This operation may fail
     * to remove the correct element if there are nodes with key value
     * -Infinity.
     *
     * <p>Running time: O(log n) amortized</p>
     *
     * @param x node to remove from heap
     */
    public void delete(FibonacciHeapNode<T> x) {
        // make x as small as possible
        decreaseKey(x, Integer.MIN_VALUE);

        // remove the smallest, which decreases n also
        removeMin();
    }

    public void delete(T d, int k) {
        FibonacciHeapNode x = new FibonacciHeapNode(d, k);
        // make x as small as possible
        decreaseKey(x, Integer.MIN_VALUE);

        // remove the smallest, which decreases n also
        removeMin();
    }

    /**
     * 
     * @return all <T> data elements in tree.
     */
    public ArrayList<T> getAllElements() {
        ArrayList<T> els = new ArrayList<T>();
        FibonacciHeapNode<T> x = minNode;
        while (x != minNode) {
            els.add(x.getData());
        }
        return els;
    }
}
