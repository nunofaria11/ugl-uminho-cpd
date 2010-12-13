/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JungTest;

import EdgeOriented.Edge;
import GraphADType.GraphADT;
import GraphADType.GraphMapAdj;
import GraphADType.Support.GenSaveReadADT;
import GraphIO.GraphInput;
import NodeOriented.Node;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import java.util.ArrayList;

/**
 * Converter class to receive a graph created by GraphADT library and
 * convert it to a JUNG graph.
 *
 * NOTE:    In order to add edges that may have the same value/weight in JUNG
 *          a handler class representing the links/edges is needed in order
 *          be able to repeat those values - each edge will then have an ID
 *          in order to differentiate the edges with the same weight.
 *
 * @author nuno
 */
public class JungConverter {

    public int edges_count = 0;
    public EdgeJHandler handler = new EdgeJHandler();

    public UndirectedSparseGraph<String, EdgeJ<Integer>> ADTtoJung(GraphADT adt) {
        ArrayList<Edge<String, Integer>> edges = (ArrayList<Edge<String, Integer>>) adt.getUnduplicatedEdges();
        UndirectedSparseGraph<String, EdgeJ<Integer>> jung_g = new UndirectedSparseGraph<String, EdgeJ<Integer>>();
        for (Edge edge : edges) {
            String n1 = (String) edge.getNode1();
            String n2 = (String) edge.getNode2();
            Integer data = (Integer) edge.getEdge_data();
            EdgeJ<Integer> edge_jung = new EdgeJ<Integer>(data, handler);
            // check if nodes in this edge have already been added
            String node1_data = n1;
            String node2_data = n2;
            if (!jung_g.containsVertex(node1_data)) {
                jung_g.addVertex(node1_data);
            }
            if (!jung_g.containsVertex(node2_data)) {
                jung_g.addVertex(node2_data);
            }
            if (!jung_g.containsEdge(edge_jung)) {
                jung_g.addEdge(edge_jung, node1_data, node2_data);
            }
        }
        return jung_g;

    }


    public static GraphADT createSmallGraphADT(){
        GraphMapAdj<String, Integer> adt_g = new GraphMapAdj<String, Integer>();
        adt_g.addNodes(7);
//        Node<String> n0 = new Node<String>("A");
//        Node<String> n1 = new Node<String>("B");
//        Node<String> n2 = new Node<String>("C");
//        Node<String> n3 = new Node<String>("D");
//        Node<String> n4 = new Node<String>("E");
//        Node<String> n5 = new Node<String>("F");
//        Node<String> n6 = new Node<String>("G");
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
        System.out.println(adt_g);
        return adt_g;

    }

    public static void main(String[] args) {
        /*
         * GraphADT graph
         */

        GenSaveReadADT.write();
        GraphADT adt_g;
        GraphInput gin = new GraphInput(GenSaveReadADT.file_name);
        adt_g = gin.readGraphADT();
//        adt_g = createSmallGraphADT();
        System.out.println(adt_g);


        /*
         * Convert to jung graph
         */
        JungConverter jconv = new JungConverter();
        System.out.println(jconv.ADTtoJung(adt_g));

    }
}
