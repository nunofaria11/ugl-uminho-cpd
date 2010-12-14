/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType.Algorithms;

import EdgeOriented.Edge;
import GraphADType.GraphADT;
import GraphADType.GraphMapAdj;
import GraphADType.Support.Constants;
import GraphADType.Support.TArithmeticOperations;
import GraphADType.Support.UnionFindTree;
import GraphIO.GraphInput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;

/**
 *
 * @author nuno
 */
public class KruskalADT<T, Y extends Comparable<Y>> {

    public PriorityQueue<Edge<T, Y>> initQ(GraphADT g) {
        ArrayList<T> visited = new ArrayList<T>();
        PriorityQueue<Edge<T, Y>> Q = new PriorityQueue<Edge<T, Y>>();
        for (T i : new ArrayList<T>(g.getNodes())) {
            visited.add(i);
            Collection<Edge<T, Y>> edges = g.getNeighborEdges(i);
            ArrayList<Edge<T, Y>> edgesToAdd = new ArrayList<Edge<T, Y>>();
            for (Edge<T, Y> e : edges) {
                if (!visited.contains(e.getNode2())) {
                    edgesToAdd.add(e);
                }
            }
            Q.addAll(edgesToAdd);
        }
        return Q;
    }

    public GraphADT getMst(GraphADT g) {
        PriorityQueue<Edge<T, Y>> Q = initQ(g);
        UnionFindTree<T> _union_find = new UnionFindTree<T>(g.getNodes());
        //http://penguin.ewu.edu/cscd327/Topic/Graph/Kruskal/Set_Union_Find.html
        GraphADT mst = g.clone();
        mst.clean();
        mst.addNodes(g.order());
        //
        int edges_processed = 0;
        int edges_added = 0;
        while (edges_processed < g.size() && edges_added < g.order() - 1) {
            Edge minEdge = Q.poll();
            T ck1 = _union_find.find((T) minEdge.getNode1());
            T ck2 = _union_find.find((T) minEdge.getNode2());
            if (!ck1.equals(ck2)) { // if roots are different it means it doesnt have a cycle
//                mst.addEdge(minEdge.getNode1(), minEdge.getNode2(), minEdge.getEdge_data());
                // A U {(u,v)}
                _union_find.union(ck1, ck2);
                edges_added++;
            }
            edges_processed++;
        }
        //
        mst.buildGraph(_union_find);
        return mst;
    }

    public static void main2(String[] args) {
        GraphMapAdj<String, Double> g = new GraphMapAdj<String, Double>(7);
        // create nodes...
//        Node<String> n0 = new Node<String>("A");
//        Node<String> n1 = new Node<String>("B");
//        Node<String> n2 = new Node<String>("C");
//        Node<String> n3 = new Node<String>("D");
//        Node<String> n4 = new Node<String>("E");
//        Node<String> n5 = new Node<String>("F");
//        Node<String> n6 = new Node<String>("G");
        String n0 = "A";
        String n1 = "B";
        String n2 = "C";
        String n3 = "D";
        String n4 = "E";
        String n5 = "F";
        String n6 = "G";
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
        // create Kruskal instance...
        KruskalADT kruskal = new KruskalADT();
        GraphADT mst = kruskal.getMst(g);
        System.out.println(mst.toString());
        // define arithmetic operations to calculate the total weight of type Y
        TArithmeticOperations<Double> arith = new TArithmeticOperations<Double>() {

            public Double Add(Double a, Double b) {
                return a + b;
            }

            public Double Cat(Double a, Double b) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public Double zero_element() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        Double total = 0.0;
        total = (Double) mst.getMstWeight(arith, total);
        System.out.println("Total Mst Weight: " + total);
    }

    public static void main(String[] args) {
        GraphInput gin = new GraphInput("graph_650.ser");
        GraphADT g = gin.readGraphADT();

        KruskalADT<String, Integer> kruskal = new KruskalADT<String, Integer>();
        GraphADT mst = kruskal.getMst(g);
        int t = 0;
        System.out.println(g.getMstWeight(Constants.intArith, t));
    }
}
