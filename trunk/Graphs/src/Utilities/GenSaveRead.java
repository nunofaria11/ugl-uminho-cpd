package Utilities;


import Algorithms.Boruvka;
import GraphADT.AdjLists.GraphAdjLists;
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

    static double connection_probability = 0.8;
    static String file_name = "graph_adj_2.ser";

    /*
     * Create and write a graph.
     */
    public static void write() throws IOException {
        GraphGen gen = new GraphGen(GenSaveRead.connection_probability);
        GraphOutput gout = new GraphOutput(GenSaveRead.file_name);

        GraphAdjLists g_example = new GraphAdjLists();
        g_example.addVertices(100);

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

//        write();

        Boruvka g_mst = new Boruvka(GenSaveRead.read());

        

        System.out.println("MST total weight: " + g_mst.MST_Boruvka_UnionFind().MST_TotalWeight());

    }
}
