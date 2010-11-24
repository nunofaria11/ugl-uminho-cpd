/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType.Algorithms;

import EdgeOriented.EdgeEO;
import GraphADType.GraphADT;
import GraphADType.GraphArraySucc;
import GraphADType.GraphMapAdj;
import GraphADType.GraphMapSucc;
import GraphADType.Support.Constants;
import GraphADType.Support.GenSaveReadADT;
import GraphADType.Support.GraphGenADT;
import GraphADType.Support.UnionFind_ADT;
import GraphADType.Support.TArithmeticOperations;
import GraphADType.Support.YRandomizer;
import NodeOriented.Node;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 *
 * @author nuno
 */
public class BoruvkaADT<T, Y extends Comparable<Y>> {

    /*
     * The wannabe and nbors arrays had to be transformed from arrays to hashmaps
     * in order to being able to associate
     */
    private ArrayList<EdgeEO<T, Y>> wannabes;
    private HashMap<Node<T>, EdgeEO<T, Y>> nbors;
    private UnionFind_ADT<Node<T>> uf;
    private GraphADT g;
    /*
     * The maximum edge had to be calculated according to the maximum edge present
     * in the graph. I couldn't create a generic maximum edge.
     */
    private final EdgeEO<T, Y> maxEdge;

    public BoruvkaADT(GraphADT g) {
        this.g = g.clone();
        this.wannabes = new ArrayList<EdgeEO<T, Y>>(g.size());
        this.nbors = new HashMap<Node<T>, EdgeEO<T, Y>>(g.order());
        this.uf = new UnionFind_ADT(this.g.getNodes());
        // create maximum edge for comparison effects
        this.maxEdge = getMaxEdge();
    }

    private EdgeEO<T, Y> getMaxEdge() {
        ArrayList<EdgeEO<T, Y>> edges = new ArrayList<EdgeEO<T, Y>>(g.size());
        edges.addAll(g.getEdges());
        if (edges.size() < 1) {
            return null;
        }
        // create a priority queue where in non-INcreasing order
        // i.e., the highest element is the head.
        PriorityQueue<EdgeEO<T, Y>> q = new PriorityQueue<EdgeEO<T, Y>>(edges.size(), new Comparator<EdgeEO<T, Y>>() {

            public int compare(EdgeEO<T, Y> o1, EdgeEO<T, Y> o2) {
                return o2.getEdge_data().compareTo(o1.getEdge_data());
            }
        });
        q.addAll(edges);
        return q.remove();
    }

    /*
     * [http://flylib.com/books/en/3.56.1.52/1/]
     * This implementation of Boruvka's MST algorithm uses a version of the
     * union-find ADT from Chapter 4 (with the single-parameter find added to
     * the interface) to associate indices with MST subtrees as they are built.
     * Each phase checks all the remaining edges; those that connect disjoint
     * subtrees are kept for the next phase. The array a has the edges not yet
     * discarded and not yet in the MST. The index N is used to store those being
     * saved for the next phase (the code resets E from N at the end of each phase)
     * and the index h is used to access the next edge to be checked. Each
     * component's nearest neighbor is kept in the array b with find component
     * numbers as indices. At the end of each phase, each component is united with
     * its nearest neighbor and the nearest-neighbor edges added to the MST.
     */
    public GraphADT getMst() {
        GraphADT mst = g.clone();
        mst.clean();
        mst.addNodes(g.order());
        // get wannabe edges in MST
        this.wannabes = new ArrayList<EdgeEO<T, Y>>(g.size());
        wannabes.addAll(g.getEdges());

        // initialize forest (nbors hashmap - each node is initially a root)
        this.nbors = new HashMap<Node<T>, EdgeEO<T, Y>>(g.order());
        ArrayList<Node<T>> allnodes = new ArrayList<Node<T>>(g.getNodes());
        for (Node<T> node : allnodes) {
            nbors.put(node, maxEdge);
        }

        int next;
        // Repeat until there is only one tree
        for (int i = g.size(); i != 0; i = next) {
            for (Node<T> node : allnodes) {
                nbors.put(node, maxEdge);
            }
            Node<T> l, m;
            next = 0;
            for (EdgeEO<T, Y> e : wannabes) {
                l = uf.find(e.getNode1());
                m = uf.find(e.getNode2());
                if (l.equals(m)) {
                    continue;
                }
                // if e.getWeight() < nbors.get(l).getWeight()
                if (e.compareTo(nbors.get(l)) == -1) {

                    this.nbors.put(l, e);
                }
                // if e.getWeight() < nbors.get(m).getWeight()
                if (e.compareTo(nbors.get(m)) == -1) {

                    this.nbors.put(m, e);
                }
                this.wannabes.set(next, e);
                next++;
            }
            // for every vertex check its nearest neighbor
            for (Node<T> n : allnodes) {
                EdgeEO<T, Y> nEdge = this.nbors.get(n);
                if (!nEdge.equals(maxEdge)) {

                    l = nEdge.getNode1();
                    m = nEdge.getNode2();
                    if (!uf.find(l, m)) {
                        uf.union(l, m);
                        mst.addEdge(nEdge.getNode1(), nEdge.getNode2(), nEdge.getEdge_data());
                    }
                }
            }
        }

        return mst;
    }

    public static void test_implementations(int size) {
        TArithmeticOperations<String> strArith = Constants.strArith;

        YRandomizer<Integer> iRand = Constants.randInteger;

        ArrayList<String> alpha = new ArrayList<String>();
        alpha.add("A");
        alpha.add("B");
        alpha.add("C");
        alpha.add("D");
        alpha.add("E");
        alpha.add("F");

        // copy random data to three different implementations
        GraphMapAdj<String, Integer> g_map_adj = new GraphMapAdj<String, Integer>(size);
        GraphArraySucc<String, Integer> g_array_succ = new GraphArraySucc<String, Integer>(size);
        GraphMapSucc<String, Integer> g_map_succ = new GraphMapSucc<String, Integer>(size);
        System.out.println("generating...");

        g_map_adj = (GraphMapAdj<String, Integer>) GenSaveReadADT.readTestBenchGraph(size);
        System.out.println("converting...");
        g_map_succ = g_map_adj.toGraphMapSucc();
        System.out.println("converting...");
        g_array_succ = g_map_adj.toGraphArraySucc();

        // test boruvka for each implementation
        BoruvkaADT b1 = new BoruvkaADT(g_map_adj);
        BoruvkaADT b2 = new BoruvkaADT(g_map_succ);
        BoruvkaADT b3 = new BoruvkaADT(g_array_succ);

        System.out.println("1:");
        GraphADT mst1 = b1.getMst();
        System.out.println("2:");
        GraphADT mst2 = b2.getMst();
        System.out.println("3:");
        GraphADT mst3 = b3.getMst();

        int total1 = 0;
        int total2 = 0;
        int total3 = 0;
        System.out.println(mst1.getMstWeight(Constants.intArith, total1));
        System.out.println(mst2.getMstWeight(Constants.intArith, total2));
        System.out.println(mst3.getMstWeight(Constants.intArith, total3));

    }

    public static void testArraySucc(int size) {
        TArithmeticOperations<String> strArith = Constants.strArith;

        YRandomizer<Integer> iRand = Constants.randInteger;

        ArrayList<String> alpha = new ArrayList<String>();
        alpha.add("A");
        alpha.add("B");
        alpha.add("C");
        alpha.add("D");
        alpha.add("E");
        alpha.add("F");

        // copy random data to three different implementations
//        GraphMapAdj<String, Integer> g_map_adj = new GraphMapAdj<String, Integer>();
        GraphArraySucc<String, Integer> g_array_succ = new GraphArraySucc<String, Integer>(size);
        System.out.println("generating...");
        GraphGenADT<String, Integer> ggen = new GraphGenADT<String, Integer>(
                0.5,
                90, // maximum
                5, // minimum
                iRand, // interface for random Y-weight values
                strArith, // interface for node ids creation operations
                alpha);     // alphabet to consider in node-ids

//        g_array_succ = (GraphArraySucc<String, Integer>) ggen.generate(g_array_succ, size);
        GraphMapAdj<String, Integer> g_map_adj = new GraphMapAdj<String, Integer>(size);
        g_map_adj = (GraphMapAdj<String, Integer>) ggen.generate(g_map_adj, size);
//        GraphMapAdj<String, Integer> g_map_adj = (GraphMapAdj<String, Integer>) GenSaveReadADT.readTestBenchGraph();
        g_array_succ = g_map_adj.toGraphArraySucc();
//        System.out.println(g_array_succ);

        BoruvkaADT b3 = new BoruvkaADT(g_array_succ);
        GraphADT mst = b3.getMst();
        int total = 0;
        System.out.println(mst.getMstWeight(Constants.intArith, total));

    }

    public static void main2(String[] args) {
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

        System.out.println("Connected: " + g.connected());
        // create Boruvka instance...
        BoruvkaADT bor = new BoruvkaADT(g);
        GraphADT mst = bor.getMst();
        System.out.println(mst.toString());
        // define arithmetic operations to calculate the total weight of type Y - in this case Y=Double
        TArithmeticOperations<Double> arith = new TArithmeticOperations<Double>() {

            public Double Add(Double a, Double b) {
                return a + b;
            }

            public Double Cat(Double a, Double b) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public Double null_element() {
                return -1.0;
            }
        };
        Double total = 0.0;
        total = (Double) mst.getMstWeight(arith, total);
        System.out.println("Total Mst Weight: " + total);
    }

    public static void main(String[] args) {
        BoruvkaADT.test_implementations(400);
//        BoruvkaADT.testArraySucc(400);
    }
}
