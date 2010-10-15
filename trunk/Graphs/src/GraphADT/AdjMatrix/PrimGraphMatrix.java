/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADT.AdjMatrix;

import GraphADT.GraphInterfaces.PrimInterface;
import java.util.ArrayList;
import java.util.ArrayList;

/**
 *
 * @author nuno
 */
public class PrimGraphMatrix extends GraphAdjMatrix implements PrimInterface {

    /**
     * auxiliary array for visited nodes
     */
    private ArrayList<Integer> visited;
    /**
     * auxiliary array for active nodes
     */
    private ArrayList<Integer> active;

    public PrimGraphMatrix(PrimGraphMatrix G) {
        super(G);
        setActive(G.getActive());
        setVisited(G.getVisited());
    }

    public PrimGraphMatrix() {
        visited = new ArrayList<Integer>();
        active = new ArrayList<Integer>(2);
        active.add(-1);
        active.add(-1);
    }

    public void addVisited(int v) {
        visited.add(v);
    }

    public void changeActive(int index, int node) {
        active.set(index, node);
    }

    public ArrayList<Integer> getActive() {
        return active;
    }

    public ArrayList<Integer> getVisited() {
        return visited;
    }

    public void reset() {
        visited = new ArrayList<Integer>();
        active = new ArrayList<Integer>(2);
        active.add(-1);
        active.add(-1);
    }

    public void setActive(ArrayList<Integer> active) {
        this.active = (ArrayList<Integer>) active.clone();
    }

    public void setVisited(ArrayList<Integer> visited) {
        this.visited = (ArrayList<Integer>) visited.clone();
    }

    public PrimGraphMatrix MST_Prim() {
        reset();
        PrimGraphMatrix mst = new PrimGraphMatrix();
        mst.addVertices(order());
        // choose random start node
        int startNode = getRandomNode();
        changeActive(0, startNode);
        changeActive(1, startNode);
        addVisited(active.get(0));
        int activeIndex = 0;
        while (visited.size() != order()) {
            System.out.println("Actives: " + active);
            System.out.println("Visited: " + visited + "\n");
            int minimumDest = -1;
            int minEdgeDest = (Integer) findMinEdge(active.get(0), visited);
            int minEdgeDest2 = (Integer) findMinEdge(active.get(1), visited);
//            if (minEdge.getWeight() < minEdge2.getWeight()) {
            if (getWeight(active.get(0), minEdgeDest) < getWeight(active.get(1), minEdgeDest2)) {
                activeIndex = 0;
                minimumDest = minEdgeDest;
            } else {
                activeIndex = 1;
                minimumDest = minEdgeDest2;
            }
            mst.addEdge(active.get(activeIndex), minimumDest, getWeight(active.get(activeIndex), minimumDest));
            changeActive(activeIndex, minimumDest);
            addVisited(minimumDest);
        }
        return mst;
    }

    public static void main(String[] args) {
        PrimGraphMatrix g = new PrimGraphMatrix();
        g.addVertices(7);
        g.addEdge(0, 1, 7);
        g.addEdge(0, 3, 5);
        g.addEdge(1, 2, 8);
        g.addEdge(1, 3, 9);
        g.addEdge(1, 4, 7);
        g.addEdge(2, 4, 5);
        g.addEdge(3, 4, 15);
        g.addEdge(3, 5, 6);
        g.addEdge(4, 5, 8);
        g.addEdge(4, 6, 9);
        g.addEdge(5, 6, 11);
        System.out.println(g.toString());
        PrimGraphMatrix mst = new PrimGraphMatrix(g.MST_Prim());
        System.out.println(mst.toString());
        System.out.println("Total weight: " + mst.MST_TotalWeight());
    }
}
