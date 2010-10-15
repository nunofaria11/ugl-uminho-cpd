/*
 * Tries to find the best edge for each vertex
 */
package GraphADT.AdjLists;

import java.util.ArrayList;

/**
 * Here I needed to added another class to the project in order to identify
 * each node. Since Boruvka's algorithm processes each node of the original
 * graph independently we need an identifier for each node so the process of
 * merging the various trees is eased.
 *
 * History: class 'Node' added (Friday, October 15 2010, 10:53 AM)
 *
 * @author nuno
 */
public class BoruvkaGraph_Lists extends GraphAdjLists {

    private ArrayList<GraphAdjLists> T;

    public BoruvkaGraph_Lists(GraphAdjLists g) {
        super(g);
    }

    public BoruvkaGraph_Lists() {
        super();
    }

    public GraphAdjLists MST_Boruvka() {
        GraphAdjLists mst = new GraphAdjLists();
        mst.addVertices(order());
        //
        T = new ArrayList<GraphAdjLists>(order());
        for (int i = 0; i < order(); i++) {
            GraphAdjLists tree = new GraphAdjLists();
            tree.addVertices(1);
            T.add(i, tree);
        }

        System.out.println(T);
        //
        return mst;
    }

    public ArrayList<GraphAdjLists> getT() {
        return T;
    }

    public void setT(ArrayList<GraphAdjLists> T) {
        this.T = T;
    }

    public static void main(String[] args) {

        BoruvkaGraph_Lists g = new BoruvkaGraph_Lists();
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
        GraphAdjLists mst = new GraphAdjLists(g.MST_Boruvka());
        System.out.println(g.toString());
        System.out.println(mst.toString());
        System.out.println("Total weight: " + mst.MST_TotalWeight());

    }
}
