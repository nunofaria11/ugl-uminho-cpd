/*
 * Graph Abstract Data
 * http://www.cs.auckland.ac.nz/~ute/220ft/graphalg/node4.html
 *
 * CHANGED: need to add a weight parameter.
 */
package GraphADT;

import java.util.Random;
import java.util.ArrayList;

/**
 *
 * @author nuno
 */
public abstract class GraphADT {

    abstract public void addVertices(int i);        // Add some vertices

    abstract public void removeVertex(int i);       // Remove vertex.

    abstract public void addArc(int i, int j, int w);      // Add directed edge.

    public void addEdge(int i, int j, int w) {              // Add undirected edge.
        addArc(i, j, w);
        addArc(j, i, w);
    }

    abstract public void removeArc(int i, int j);   // Remove directed edge.

    public void removeEdge(int i, int j) {           // Remove undirected edge.
        removeArc(i, j);
        removeArc(j, i);
    }

    abstract public boolean isArc(int i, int j);    // Check for directed edges.

    public boolean isEdge(int i, int j) {            // Check for undirected edges.
        return isArc(i, j) && isArc(j, i);
    }

    abstract public int inDegree(int i);            // Number of incoming arcs.

    abstract public int outDegree(int i);           // Number of outgoing arcs.

    public int degree(int i) {                       // Number of neighbours.
        return outDegree(i);
    }

    abstract public ArrayList neighbors(int i);        // List of (out-) neighbours.

    abstract public int order();                    // Number of vertices.

    /**
     *
     * @return number of edges
     */
    abstract public int size();                     // Number of edges.

    abstract public String toString();

    public int getRandomNode() {
        return new Random().nextInt(order());
    }

    abstract public int MST_TotalWeight();

    abstract public Object findMinEdge(int v, ArrayList<Integer> visited);

    abstract public int getWeight(int i, int j);

    public ArrayList getNeighbors(int x) {
        ArrayList<Integer> nbors = new ArrayList<Integer>();
        for (int i = 0; i < order(); i++) {
            if (isArc(x, i)) {
                nbors.add(new Integer(i));
            }
        }
        return nbors;
    }

    /**
     * Checks if all the nodes in the graph are connected
     * @return boolean
     */
    abstract public boolean connected();

    abstract public boolean saveToFile(String fileName);

    abstract public GraphADT loadFromFile(String fileName);
    
}
