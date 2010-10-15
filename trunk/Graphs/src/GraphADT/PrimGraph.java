///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package GraphADT;
//
//import GraphADT.AdjLists.GraphAdjLists;
//import GraphADT.GraphInterfaces.PrimInterface;
//import java.util.ArrayList;
//import java.util.ArrayList;
//
///**
// *
// * @author nuno
// */
//public class PrimGraph<N extends GraphADT> implements PrimInterface {
//
//    /**
//     * auxiliary array for visited nodes
//     */
//    private ArrayList<Integer> visited;
//    /**
//     * auxiliary array for active nodes
//     */
//    private ArrayList<Integer> active;
//
//    private N mstG;
//
//    public PrimGraph(PrimGraph G) {
//        setActive(G.getActive());
//        setVisited(G.getVisited());
//        mstG = (N)G.getMstG();
//    }
//
//    public N getMstG() {
//        return mstG;
//    }
//
//    public void setMstG(N mst) {
//        this.mstG = mst;
//    }
//
//    public PrimGraph() {
//        visited = new ArrayList<Integer>();
//        active = new ArrayList<Integer>(2);
//        active.add(-1);
//        active.add(-1);
//    }
//
//    public void addVisited(int v) {
//        visited.add(v);
//    }
//
//    public void changeActive(int index, int node) {
//        active.set(index, node);
//    }
//
//    public ArrayList<Integer> getActive() {
//        return active;
//    }
//
//    public ArrayList<Integer> getVisited() {
//        return visited;
//    }
//
//    public void reset() {
//        visited = new ArrayList<Integer>();
//        active = new ArrayList<Integer>(2);
//        active.add(-1);
//        active.add(-1);
//    }
//
//    public void setActive(ArrayList<Integer> active) {
//        this.active = (ArrayList<Integer>) active.clone();
//    }
//
//    public void setVisited(ArrayList<Integer> visited) {
//        this.visited = (ArrayList<Integer>) visited.clone();
//    }
//
//    public GraphADT MST_Prim() {
//        reset();
//        N mst = new N();
//        mst.addVertices(order());
//        // choose random start node
//        int startNode = getRandomNode();
//        changeActive(0, startNode);
//        changeActive(1, startNode);
//        addVisited(active.get(0));
//        int activeIndex = 0;
//        while (visited.size() != order()) {
//            System.out.println("Actives: " + active);
//            System.out.println("Visited: " + visited + "\n");
//            int minimumDest = -1;
//            int minEdgeDest = (Integer) findMinEdge(active.get(0), visited);
//            int minEdgeDest2 = (Integer) findMinEdge(active.get(1), visited);
////            if (minEdge.getWeight() < minEdge2.getWeight()) {
//            if (getWeight(active.get(0), minEdgeDest) < getWeight(active.get(1), minEdgeDest2)) {
//                activeIndex = 0;
//                minimumDest = minEdgeDest;
//            } else {
//                activeIndex = 1;
//                minimumDest = minEdgeDest2;
//            }
//            mst.addEdge(active.get(activeIndex), minimumDest, getWeight(active.get(activeIndex), minimumDest));
//            changeActive(activeIndex, minimumDest);
//            addVisited(minimumDest);
//        }
//        return mst;
//    }
//}
