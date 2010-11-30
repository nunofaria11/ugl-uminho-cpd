/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType.Support;

import GraphADType.GraphADT;
import GraphADType.GraphMapAdj;
import GraphIO.GraphInput;
import GraphIO.GraphOutput;
import NodeOriented.Node;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author nuno
 */
public class GenSaveReadADT {

    public static int NUM_NODES = 50;
    public static double connection_probability = 0.5;
    public static String file_name = "graphADT_" + NUM_NODES + "nodes.ser";

    public static void write() {
        TArithmeticOperations<String> strArith = Constants.strArith;

        YRandomizer<Integer> iRand = Constants.randInteger;

        ArrayList<String> alpha = new ArrayList<String>();
        alpha.add("A");
        alpha.add("B");
        alpha.add("C");
        alpha.add("D");
        alpha.add("E");
        alpha.add("F");

        GraphGenADT<String, Integer> ggen = new GraphGenADT<String, Integer>(
                GenSaveReadADT.connection_probability,
                90, // maximum
                5, // minimum
                iRand, // interface for random Y-weight values
                strArith, // interface for node ids creation operations
                alpha);     // alphabet to consider in node-ids

        GraphMapAdj<String, Integer> g = new GraphMapAdj<String, Integer>();
        g = (GraphMapAdj<String, Integer>) ggen.generate(g, NUM_NODES);

        GraphOutput gout = new GraphOutput(GenSaveReadADT.file_name);
        try {
            gout.saveGraphADT(g);
            System.out.println("GraphADT written with success !!");
        } catch (IOException ioe) {
            System.out.println("Error writing GraphADT: " + ioe.toString());
        }
    }


    /*
     * writes a small example graph
     */
    public static void write_small() {

        GraphMapAdj<String, Double> g = new GraphMapAdj<String, Double>(7);
        // create nodes...
        Node<String> n0 = new Node<String>("A");
        Node<String> n1 = new Node<String>("B");
        Node<String> n2 = new Node<String>("C");
        Node<String> n3 = new Node<String>("D");
        Node<String> n4 = new Node<String>("E");
        Node<String> n5 = new Node<String>("F");
        Node<String> n6 = new Node<String>("G");
        // add nodes to graph...
        g.addNode(n0);
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        g.addNode(n4);
        g.addNode(n5);
        g.addNode(n6);
        // link nodes together...
        g.addEdge(n0, n1, 7.1);
        g.addEdge(n0, n3, 5.1);
        g.addEdge(n1, n2, 8.1);
        g.addEdge(n1, n3, 9.1);
        g.addEdge(n1, n4, 7.1);
        g.addEdge(n2, n4, 5.1);
        g.addEdge(n3, n4, 15.1);
        g.addEdge(n3, n5, 6.1);
        g.addEdge(n4, n5, 8.1);
        g.addEdge(n4, n6, 9.1);
        g.addEdge(n5, n6, 11.1);

        GraphOutput gout = new GraphOutput("small_example_ADT.ser");
        try {
            gout.saveGraphADT(g);
        } catch (IOException ioe) {
            System.out.println("Error writing GraphADT: " + ioe.toString());
        }
    }

    public static GraphADT read() {
        GraphInput gin = new GraphInput(GenSaveReadADT.file_name);
        GraphADT g_adt_read = gin.readGraphADT();
        System.out.println("Done reading GraphADT !!");
        return g_adt_read;
    }

    public static GraphADT readTestBenchGraph(int size) {
        GraphInput gin = new GraphInput("bench" + size + "_test.ser");
        return gin.readGraphADT();
    }

    public static void GenerateAndWriteTestBenchGraph(int size) throws IOException {
        TArithmeticOperations<String> strArith = Constants.strArith;
        YRandomizer<Integer> iRand = Constants.randInteger;
        ArrayList<String> alpha = new ArrayList<String>();
        alpha.add("A");
        alpha.add("B");
        alpha.add("C");
        alpha.add("D");
        alpha.add("E");
        alpha.add("F");
        GraphGenADT<String, Integer> ggen = new GraphGenADT<String, Integer>(
                0.5,
                90, // maximum
                5, // minimum
                iRand, // interface for random Y-weight values
                strArith, // interface for node ids creation operations
                alpha);     // alphabet to consider in node-ids
        GraphMapAdj<String, Integer> g = new GraphMapAdj<String, Integer>();
        g = (GraphMapAdj<String, Integer>) ggen.generate(g, size);
        GraphOutput gout = new GraphOutput("bench" + size + "_test_matrix.ser");
        gout.saveGraphADT(g);
    }

    public static void main(String[] args) throws IOException {
//        GenSaveReadADT.write_small();
////        GraphADT read_g = GenSaveReadADT.read();
//        GraphInput gin = new GraphInput("small_example_ADT.ser");
//        GraphADT read_g = gin.readGraphADT();
//
////        GraphOutput gdot = new GraphOutput("g" + NUM_NODES + ".dot");
//        GraphOutput gdot = new GraphOutput("small.dot");
//        gdot.GraphtoDot(read_g);
//
//        System.out.println(read_g.toString());

        for (int s = 250; s <= 1200; s += 50) {
            GenSaveReadADT.GenerateAndWriteTestBenchGraph(s);
        }



//        GraphInput gin = new GraphInput("bench100_test_wrange.ser");
//        GraphADT g = gin.readGraphADT();//GenSaveReadADT.readTestBenchGraph(50);
//        System.out.println(g.toString());
//        GraphOutput gout = new GraphOutput("bench50_test_wrange.dot");
//        gout.GraphtoDot(g);


    }
}
