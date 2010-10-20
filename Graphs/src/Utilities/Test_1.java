package Utilities;


import Algorithms.Boruvka;
import Algorithms.Kruskal;
import Algorithms.PrimHeapQueue;
import GraphADT.GraphADT;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nuno
 */
public class Test_1 {

    public static void main(String[] args){
        GraphADT read_graph = GenSaveRead.read();

        PrimHeapQueue prim = new PrimHeapQueue(read_graph);
        Kruskal kruskal = new Kruskal(read_graph);
        Boruvka boruvka = new Boruvka(read_graph);

        GraphADT boruvka_mst = boruvka.MST_Boruvka_UnionFind();
        GraphADT prim_mst = prim.MST_PrimHeap();
        GraphADT kruskal_mst = kruskal.MST_Kruskal_UnionFind();
        
        System.out.println("Boruvka MST weight: " + boruvka_mst.MST_TotalWeight());
        System.out.println("Prim MST weight: " + prim_mst.MST_TotalWeight());
        System.out.println("Kruskal MST weight: " + kruskal_mst.MST_TotalWeight());
        

    }

}
