package PerfTest;

import GraphAD.GraphAD;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author nuno
 */
public class Test_1 {

    public static void main(String[] args) {
        GraphAD read_graph = GenSaveRead.read();

        List_Perf lperf = new List_Perf(read_graph);
        Matrix_Perf mperf = new Matrix_Perf(read_graph);

        lperf.runListMSTs();
        mperf.runMatrixMSTs();

    }
}
