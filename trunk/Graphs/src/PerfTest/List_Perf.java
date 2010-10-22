/*
 * Auxiliary class to use in performance testing for list MST algorithms
 * 
 */
package PerfTest;

import Algorithms.Boruvka;
import Algorithms.Kruskal;
import Algorithms.PrimHeapQueue;
import GraphAD.Representations.GraphAdjLists;
import GraphAD.Representations.GraphAdjMatrix;
import GraphAD.GraphAD;

/**
 *
 * @author nuno
 */
public class List_Perf {

    private PrimHeapQueue prim;
    private Kruskal kruskal;
    private Boruvka boruvka;

    public List_Perf(GraphAdjLists read_graph_list) {
        prim = new PrimHeapQueue(read_graph_list);
        kruskal = new Kruskal(read_graph_list);
        boruvka = new Boruvka(read_graph_list);
    }

    public List_Perf(GraphAD g) {
        GraphAdjLists gl;

        if (g instanceof GraphAdjLists) {
            gl = (GraphAdjLists) g;
        } else {
            gl = ((GraphAdjMatrix) g).toGraphAdjLists();
        }

        prim = new PrimHeapQueue(gl);
        kruskal = new Kruskal(gl);
        boruvka = new Boruvka(gl);
    }

    public void runListMSTs() {
        GraphAD boruvka_mst = boruvka.MST_Boruvka_UnionFind();
        GraphAD prim_mst = prim.MST_PrimHeap();
        GraphAD kruskal_mst = kruskal.MST_Kruskal_UnionFind();

        System.out.println("LIST:");
        System.out.println("\tBoruvka MST weight: " + boruvka_mst.MST_TotalWeight());
        System.out.println("\tPrim MST weight: " + prim_mst.MST_TotalWeight());
        System.out.println("\tKruskal MST weight: " + kruskal_mst.MST_TotalWeight());
    }

    public static void main(String[] args) {
        GraphAD read_graph = GenSaveRead.read();

        List_Perf lperf = new List_Perf(read_graph);

        lperf.runListMSTs();
    }
}
