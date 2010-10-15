/*
 * MST Prim algorithm class with adjancy lists and auxiliary structures.
 */
package GraphADT.AdjLists;

import GraphADT.Edge;
import GraphADT.GraphInterfaces.PrimInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ArrayList;

/**
 *
 * @author nuno
 */
public class PrimGraphLists extends GraphAdjLists implements PrimInterface {

    /**
     * auxiliary array for visited nodes
     */
    private ArrayList<Integer> visited;
    /**
     * auxiliary array for active nodes
     */
    private ArrayList<Integer> active;

    public PrimGraphLists() {
        super();
        reset();
    }

    public PrimGraphLists(PrimGraphLists G) {
        super(G);
        setActive(G.getActive());
        setVisited(G.getVisited());
    }

    public void reset() {
        visited = new ArrayList<Integer>();
        active = new ArrayList<Integer>(2);
        active.add(-1);
        active.add(-1);
        // no active nodes
//        changeActive(0, -1);
//        changeActive(1, -1);
    }

    public ArrayList<Integer> getActive() {
        return active;
    }

    public void setActive(ArrayList<Integer> active) {
        this.active = (ArrayList<Integer>) active.clone();
    }

    public ArrayList<Integer> getVisited() {
        return visited;
    }

    public void setVisited(ArrayList<Integer> visited) {
        this.visited = (ArrayList<Integer>) visited.clone();
    }

    public void addVisited(int v) {
        visited.add(v);
    }

    public void changeActive(int index, int node) {
        active.set(index, node);
    }

    /**
     * First implementation with a two-active-node search. Doesn't output the minimum span tree.
     * @return
     */
    public PrimGraphLists MST_Prim() {
        reset();
        PrimGraphLists mst = new PrimGraphLists();
        mst.addVertices(order());
        // choose random start node
        int startNode = getRandomNode();
        changeActive(0, startNode);
        changeActive(1, startNode);
        addVisited(active.get(0));
        int activeIndex = 0;
        while (visited.size() < order()) {
            System.out.println("Actives: " + active);
            System.out.println("Visited: " + visited + "\n");
            Edge minimum = new Edge(-1, -1, Integer.MAX_VALUE);
            Edge minEdge = (Edge) findMinEdge(active.get(0), visited);
            Edge minEdge2 = (Edge) findMinEdge(active.get(1), visited);
            if (minEdge.getWeight() < minEdge2.getWeight()) {
                activeIndex = 0;
                minimum = minEdge;
            } else {
                activeIndex = 1;
                minimum = minEdge2;
            }
            mst.addEdge(active.get(activeIndex), minimum.getDest(), minimum.getWeight());
            changeActive(activeIndex, minimum.getDest());
            addVisited(minimum.getDest());
        }
        return mst;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("\nVisited: ");
        for (int i = 0; i < visited.size(); i++) {
            s.append(visited.get(i) + ", ");
        }
        s.append(super.toString());
        return s.toString();
    }

    public static void main(String[] args) {
        PrimGraphLists g = new PrimGraphLists();
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
        PrimGraphLists mst = new PrimGraphLists(g.MST_Prim());
        System.out.println(mst.toString());
        System.out.println("Total weight: " + mst.MST_TotalWeight());

//        System.out.println("\n-----------------\n");
//        g.MST_Prim_2();
//        System.out.println("\n-----------------\n");
//        System.out.println(g.toString());

    }
}
