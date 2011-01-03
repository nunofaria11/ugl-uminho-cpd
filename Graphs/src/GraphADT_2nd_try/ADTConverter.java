/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADT_2nd_try;

import EdgeOriented.Edge;
import GraphADType.GraphADT;
import GraphADType.GraphMapAdj;
import JungTest.EdgeJ;
import JungTest.EdgeJHandler;
import java.util.ArrayList;

/**
 *
 * @author nuno
 */
public class ADTConverter {

    public UndirectedGraph<String, EdgeJ<Integer>> ADT2New(GraphADT adt) {
        EdgeJHandler handler = new EdgeJHandler();
        ArrayList<Edge<String, Integer>> edges = new ArrayList<Edge<String, Integer>>(adt.getEdges());
        UndirectedGraph<String, EdgeJ<Integer>> g = new UndirectedGraph<String, EdgeJ<Integer>>();
        for (Edge edge : edges) {
            String n1 = (String) edge.getNode1();
            String n2 = (String) edge.getNode2();
            Integer data = (Integer) edge.getEdge_data();
            EdgeJ<Integer> newEdge = new EdgeJ<Integer>(data, handler);
            // check if nodes in this edge have already been added
            String node1_data = n1;
            String node2_data = n2;
            if (!g.containsVertex(node1_data)) {
                g.addVertex(node1_data);
            }
            if (!g.containsVertex(node2_data)) {
                g.addVertex(node2_data);
            }
            if (!g.containsEdge(newEdge)) {
                g.addEdge(newEdge, node1_data, node2_data);
            }
        }
        return g;
    }

    public static GraphADT createSmallGraphADT() {
        GraphMapAdj<String, Integer> adt_g = new GraphMapAdj<String, Integer>();
        adt_g.addNodes(7);
        String n0 = "A";
        String n1 = "B";
        String n2 = "C";
        String n3 = "D";
        String n4 = "E";
        String n5 = "F";
        String n6 = "G";
        adt_g.addEdge(n0, n1, 7);
        adt_g.addEdge(n0, n3, 5);
        adt_g.addEdge(n1, n2, 8);
        adt_g.addEdge(n1, n3, 9);
        adt_g.addEdge(n1, n4, 7);
        adt_g.addEdge(n2, n4, 5);
        adt_g.addEdge(n3, n4, 15);
        adt_g.addEdge(n3, n5, 6);
        adt_g.addEdge(n4, n5, 8);
        adt_g.addEdge(n4, n6, 9);
        adt_g.addEdge(n5, n6, 11);
//        System.out.println(adt_g);
        return adt_g;
    }

    public static void main(String[] args){
        GraphADT adt = ADTConverter.createSmallGraphADT();
        System.out.println(adt.toString());

        ADTConverter conv = new ADTConverter();
        System.out.println(conv.ADT2New(adt));

    }
}
