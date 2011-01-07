/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JGraphTest;

import GraphADT_2nd_try.EdgeJ;
import GraphADT_2nd_try.EdgeJHandler;
import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.WeightedMultigraph;
import org.jgrapht.graph.WeightedPseudograph;

/**
 *
 * @author nuno
 */
public class test1 {

    public static void main(String[] args) {

        UndirectedGraph<String, Integer> graph = new WeightedMultigraph<String, Integer>(Integer.class);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");

        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 1); //
        graph.addEdge("C", "D", 3);
        System.out.println(graph);
        // in order to use edges that may have the same value, we will use
        //the same handler use the same handler than in JUNG

        // ===============
        WeightedMultigraph<String, EdgeJ<Integer>> graph2 = new WeightedMultigraph(EdgeJ.class);
        EdgeJHandler handler = new EdgeJHandler();
        EdgeJ<Integer> e1 = new EdgeJ<Integer>(1, handler);
        EdgeJ<Integer> e2 = new EdgeJ<Integer>(1, handler);
        EdgeJ<Integer> e3 = new EdgeJ<Integer>(3, handler);

        graph2.addVertex("A");
        graph2.addVertex("B");
        graph2.addVertex("C");
        graph2.addVertex("D");

        graph2.addEdge("A", "B", e1);
        graph2.addEdge("B", "C", e2); //
        graph2.addEdge("C", "D", e3);
        System.out.println(graph2);

        // ===============
        WeightedMultigraph<String, Integer> graph3 = new WeightedMultigraph(Integer.class);
        Graph g = graph3;

        graph3.addVertex("A");
        graph3.addVertex("B");
        graph3.addVertex("C");
        graph3.addVertex("D");

        graph3.addEdge("A", "B", 1);
        graph3.addEdge("B", "C", 2); //
        graph3.addEdge("C", "D", 3);

//        graph3.setEdgeWeight(graph3.getEdge("A", "B"), 10.0);
//        graph3.setEdgeWeight(graph3.getEdge("B", "C"), 6.0);
//        graph3.setEdgeWeight(graph3.getEdge("C", "D"), 2.0);
        System.out.println(graph3);



    }
}
