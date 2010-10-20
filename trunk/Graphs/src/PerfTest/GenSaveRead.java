package PerfTest;

import Algorithms.Boruvka;
import GraphADT.AdjMatrix.GraphAdjMatrix;
import GraphADT.GraphADT;
import GraphIO.GraphInput;
import GraphIO.GraphOutput;
import Utilities.GraphGen;
import java.io.IOException;

/*
 * This class is aimed to manage auxiliary graph files to use in performance
 * and correctness testing
 */
/**
 *
 * @author nuno
 */
public class GenSaveRead {

    static double connection_probability = 0.5;
//    static String file_name = "graph_adj_500nodes.ser";
    static String file_name = "graph_mat_500nodes.ser";

    /*
     * Create and write a graph.
     */
    public static void write() throws IOException {
        GraphGen gen = new GraphGen(GenSaveRead.connection_probability);
        GraphOutput gout = new GraphOutput(GenSaveRead.file_name);

        GraphAdjMatrix g_example = new GraphAdjMatrix();
        g_example.addVertices(500);

        gen.generate(g_example);

        gout.saveGraph(g_example);

        gout.closeFile();
    }

    /*
     * Read the written graph
     */
    public static GraphADT read() {
        GraphInput gin = new GraphInput(GenSaveRead.file_name);
        GraphADT g_adt_read = gin.readGraph();

//        System.out.println(g_adt_read.toString());
        return g_adt_read;
    }

    public static void main(String[] args) throws IOException {

        write();

        Boruvka g_mst = new Boruvka(GenSaveRead.read());


        System.out.println("MST total weight: " + g_mst.MST_Boruvka_UnionFind().MST_TotalWeight());

    }
}
