/*
 * The Kruskal algorithm consists in implementing a list with all the edges
 * ordered by non-decreasing order where each minimim edge is extracted in
 * order to find the MST.
 *
 * For this implementation the Union concepts were applied
 * (see class "UnionFind").
 */

package Support;

import GraphADT.Edge;
import GraphADT.GraphADT;
import java.util.ArrayList;

/**
 *
 * @author nuno
 */
public interface KruskalInterface {

    void addToQ(ArrayList<Edge> x);

    Edge extractMin();

    void initQ();

    GraphADT MST_Kruskal_UnionFind();

}
