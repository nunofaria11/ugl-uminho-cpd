/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JGraphTest;

import EdgeOriented.Edge;
import GraphADType.GraphADT;
import GraphADType.Support.GenSaveReadADT;
import JungTest.EdgeJ;
import JungTest.EdgeJHandler;
import java.util.ArrayList;
import org.jgrapht.graph.WeightedMultigraph;

/**
 *
 * @author nuno
 */
public class JGraphConverter {

    private EdgeJHandler handler;

    public JGraphConverter() {
        this.handler = new EdgeJHandler();
    }

    public WeightedMultigraph ADTtoJGraph(GraphADT g) {
        WeightedMultigraph<String, EdgeJ<Integer>> jgraph = new WeightedMultigraph(EdgeJ.class);
        ArrayList<Edge<String, Integer>> alledges = new ArrayList<Edge<String, Integer>>(g.getEdges());

        for (Edge edge_adt : alledges) {
            String n1 = (String) edge_adt.getNode1();
            String n2 = (String) edge_adt.getNode2();
            EdgeJ<Integer> edge_jgraph = new EdgeJ<Integer>((Integer) edge_adt.getEdge_data(), handler);
            if (!jgraph.containsVertex(n1)) {
                jgraph.addVertex(n1);
            }
            if (!jgraph.containsVertex(n2)) {
                jgraph.addVertex(n2);
            }
            if (!jgraph.containsEdge(edge_jgraph)) {
                jgraph.addEdge(n1, n2, edge_jgraph);
            }
        }
        return jgraph;
    }

    public static void main(String[] args) {
        GraphADT g_adt = GenSaveReadADT.createSmallGraphADT();
        System.out.println(g_adt);

        JGraphConverter jconv = new JGraphConverter();
        System.out.println(jconv.ADTtoJGraph(g_adt));
    }
}
