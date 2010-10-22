/*
 * Auxiliary class to use in performance testing for matrix MST algorithms
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
public class Matrix_Perf {

    private PrimHeapQueue prim;
    private Kruskal kruskal;
    private Boruvka boruvka;

    public Matrix_Perf(GraphAdjMatrix read_graph_matrix) {
        prim = new PrimHeapQueue(read_graph_matrix);
        kruskal = new Kruskal(read_graph_matrix);
        boruvka = new Boruvka(read_graph_matrix);
    }

    public Matrix_Perf(GraphAD g) {
        GraphAdjMatrix gm;

        if (g instanceof GraphAdjMatrix) {
            gm = (GraphAdjMatrix) g;
        } else {
            gm = ((GraphAdjLists) g).toGraphAdjMatrix();
        }
        
        prim = new PrimHeapQueue(gm);
        kruskal = new Kruskal(gm);
        boruvka = new Boruvka(gm);
    }

    public void runMatrixMSTs() {
        GraphAD boruvka_mst = boruvka.MST_Boruvka_UnionFind();
        GraphAD prim_mst = prim.MST_PrimHeap();
        GraphAD kruskal_mst = kruskal.MST_Kruskal_UnionFind();

        System.out.println("LIST:");
        System.out.println("\tBoruvka MST weight: " + boruvka_mst.MST_TotalWeight());
        System.out.println("\tPrim MST weight: " + prim_mst.MST_TotalWeight());
        System.out.println("\tKruskal MST weight: " + kruskal_mst.MST_TotalWeight());
    }

    public static void main(String[] args){
        GraphAD read_graph = GenSaveRead.read();

        Matrix_Perf mperf = new Matrix_Perf(read_graph);

        mperf.runMatrixMSTs();
    }
}
