package PerfTest;

import Algorithms.Boruvka;
import GraphAD.Representations.GraphAdjLists;
import GraphAD.Representations.GraphAdjMatrix;
import GraphAD.GraphAD;
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

    public static double connection_probability = 0.5;
//    static String file_name = "graph_adj_500nodes.ser";
    public static String file_name = "graph_mat_500nodes.ser";
//    public static String file_name = "small_example_7nodes_matrix.ser";

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

    public static void write_small() throws IOException {

        GraphOutput gout = new GraphOutput(GenSaveRead.file_name);
        GraphAdjLists g = new GraphAdjLists();
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


        gout.saveGraph(g);

        gout.closeFile();
    }

    /*
     * Read the written graph
     */
    public static GraphAD read() {
        GraphInput gin = new GraphInput(GenSaveRead.file_name);
        GraphAD g_adt_read = gin.readGraph();

//        System.out.println(g_adt_read.toString());
        return g_adt_read;
    }

    public static void main(String[] args) throws IOException {

        GenSaveRead.write();

        Boruvka g_mst = new Boruvka(GenSaveRead.read());

        System.out.println("MST total weight: " + g_mst.MST_Boruvka_UnionFind().MST_TotalWeight());

    }
}
