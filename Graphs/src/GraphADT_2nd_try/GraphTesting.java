/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADT_2nd_try;

import GraphADType.Support.TArithmeticOperations;
import GraphIO.GraphInput;
import GraphIO.GraphOutput;
import JungTest.EdgeJ;
import JungTest.EdgeJHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author nuno
 */
public class GraphTesting {

    /*
     * CONSTANT INTERFACES
     */
    public static EdgeRandomizer<EdgeJ<Integer>> edge_randomizer = new EdgeRandomizer<EdgeJ<Integer>>() {

        private EdgeJHandler handler = new EdgeJHandler();

        public EdgeJ<Integer> random() {
            Random r = new Random(System.currentTimeMillis());
            Integer intValue = r.nextInt(upperLimit().data - lowerLimit().data) + lowerLimit().data;
            return new EdgeJ<Integer>(intValue, handler);
        }

        public EdgeJ<Integer> upperLimit() {
            return new EdgeJ<Integer>(90, handler);
        }

        public EdgeJ<Integer> lowerLimit() {
            return new EdgeJ<Integer>(5, handler);
        }
    };
    public static TArithmeticOperations<String> strArith = new TArithmeticOperations<String>() {

        public String Add(String a, String b) {
            return a + b;
        }

        public String Cat(String a, String b) {
            return a + b;
        }

        public String zero_element() {
            return "";
        }
    };

    /**
     * Creates a graph with <i>#nodes=size</i>
     * @param size #nodes
     */
    public BaseGraph createGraphSize(int size) {

        ArrayList<String> alphabet = new ArrayList<String>();
        alphabet.add("A");
        alphabet.add("B");
        alphabet.add("C");
        alphabet.add("D");
        alphabet.add("E");
        alphabet.add("F");
        alphabet.add("G");

        GraphGenerator<String, EdgeJ<Integer>> graphgen = new GraphGenerator<String, EdgeJ<Integer>>(
                0.6, // connection probability
                //                                8, // #edges
                edge_randomizer, // edge randomizer
                strArith, // vertex arithmetic
                alphabet);          // vertex alphabet

        BaseGraph<String, EdgeJ<Integer>> g = graphgen.generate(new UndirectedGraph<String, EdgeJ<Integer>>(), size);
        return g;

    }

    public void createAndWrite(int minSize, int maxSize, int interval) {
        for (int size = minSize; size <= maxSize; size += interval) {
            BaseGraph<String, EdgeJ<Integer>> g = createGraphSize(size);
            GraphOutput output = new GraphOutput("graph_"+size+".g2");
            try {
                output.saveGraph2(g);
            } catch (IOException ex) {
                System.out.println("Unnable to write: graph_"+size+".g2 \t("+ex.toString()+")");
            }
        }

    }

    public BaseGraph readGraphSize(int size){
        GraphInput input = new GraphInput("graph_"+size+".g2");
        BaseGraph g = input.readGraph2();
        return g;
    }

    public void testReads(int minSize, int maxSize, int interval){

        for (int size = minSize; size <= maxSize; size += interval) {
            BaseGraph g = readGraphSize(size);
            System.out.println("File: graph_"+size+".g2\tOrder:" + g.getOrder()+"\tSize: " + g.getSize());
        }

    }

    public static void main(String[] args) {

        GraphTesting t = new GraphTesting();
        t.createAndWrite(250, 1000, 50);

        t.testReads(50, 1000, 50);

    }
}
