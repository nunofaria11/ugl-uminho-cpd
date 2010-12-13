/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType.Support;

import EdgeOriented.Edge;
import GraphADType.GraphADT;
import GraphADType.GraphMapAdj;
import GraphIO.GraphInput;
import NodeOriented.Node;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Random GraphADT generator. Focus on:
 * <ol>
 * <li>parameter interface where the random function for Y-typed values must be
 *      defined.
 * </li>
 * </ol>
 * @author nuno
 */
public class GraphGenADT<T, Y extends Comparable<Y>> {

    final Y MAX_WEIGHT;
    final Y MIN_WEIGHT;
    final YRandomizer<Y> yrand;
    final TArithmeticOperations<T> arith;
    final ArrayList<T> alphabet;
    private int nEdges;
    private double prob;

    /**
     * Constructor to define the number of nodes and automatically assign the
     * graph with the maximum number of edges.
     *
     * @param numV Number of vertices
     * @param MAX_WEIGHT Higher limit for the edge weight
     * @param MIN_WEIGHT Higher limit for the edge weight
     * @param yrand Handler for random edge weights
     * @param arith Handler for arithmetic operations on node ids, to generate the necessary ids.
     * @param alphabet The elements in this collection will be used to form an id for each node the graph needs.
     */
    public GraphGenADT(
            int numV,
            Y MAX_WEIGHT,
            Y MIN_WEIGHT,
            YRandomizer<Y> yrand,
            TArithmeticOperations<T> arith,
            ArrayList<T> alphabet) {
        this.MAX_WEIGHT = MAX_WEIGHT;
        this.MIN_WEIGHT = MIN_WEIGHT;
        this.yrand = yrand;
        this.nEdges = Constants.possibleEdgesNum(numV);
        this.prob = -1;
        this.arith = arith;
        this.alphabet = alphabet;
    }

    /**
     * Constructor to define the number of nodes and manually assign the number of edges.
     *
     * @param numV Number of vertices
     * @param numE Number of edges. If the number of edges passed here is not enough the GNM algorithm changes it to the minimum number of edges needed.
     * @param MAX_WEIGHT Higher limit for the edge weight
     * @param MIN_WEIGHT Higher limit for the edge weight
     * @param yrand Handler for random edge weights
     * @param arith Handler for arithmetic operations on node ids, to generate the necessary ids.
     * @param alphabet The elements in this collection will be used to form an id for each node the graph needs.
     */
    public GraphGenADT(
            int numV,
            int numE,
            Y MAX_WEIGHT,
            Y MIN_WEIGHT,
            YRandomizer<Y> yrand,
            TArithmeticOperations<T> arith,
            ArrayList<T> alphabet) {
        this.MAX_WEIGHT = MAX_WEIGHT;
        this.MIN_WEIGHT = MIN_WEIGHT;
        this.yrand = yrand;
        this.nEdges = numE;
        this.prob = -1;
        this.arith = arith;
        this.alphabet = alphabet;
    }

    /**
     * Constructor to define the connection probability each graph node will have
     * of connecting to another different node.
     * @param p Connection probability
     * @param MAX_WEIGHT Higher limit for the edge weight
     * @param MIN_WEIGHT Higher limit for the edge weight
     * @param yrand Handler for random edge weights
     * @param arith Handler for arithmetic operations on node ids, to generate the necessary ids.
     * @param alphabet The elements in this collection will be used to form an id for each node the graph needs.
     */
    public GraphGenADT(
            double p,
            Y MAX_WEIGHT,
            Y MIN_WEIGHT,
            YRandomizer<Y> yrand,
            TArithmeticOperations<T> arith,
            ArrayList<T> alphabet) {
        this.MAX_WEIGHT = MAX_WEIGHT;
        this.MIN_WEIGHT = MIN_WEIGHT;
        this.yrand = yrand;
        this.nEdges = -1;
        this.prob = p;
        this.arith = arith;
        this.alphabet = alphabet;
    }

    /**
     * Random graph generation function, that builds a random graph according to
     * a connection probability or the number of edges wanted. The Erdős–Rényi
     * model is followed, and the sub-models are:
     * <ol>
     *  <li>In the G(n, M) model, a graph is chosen uniformly at random from the
     *      collection of all graphs which have <b>n</b> nodes and <b>M</b> edges.
     * For example, in the G(3, 2) model, each of the three possible graphs on
     * three vertices and two edges are included with probability 1/3.</li>
     * <br>
     *  <li>In the G(n, p) model, a graph is thought to be constructed by
     *      connecting nodes randomly. Each edge is included in the graph
     *      with probability <b>p</b>, with the presence or absence of any two
     *      distinct edges in the graph being independent.</li>
     * </ol>
     * @param g GraphADT instance
     * @param N number of nodes
     * @return Random graph
     */
    public GraphADT generate(GraphADT g, int N) {
        if (nEdges != -1) // 1)
        {
            return this.gnm(g, N);
        } else // 2)
        {
            return this.gnp(g, N);
        }
    }

    private GraphADT gnm(GraphADT g, int N) {
//        Random random = new Random(System.currentTimeMillis());

//        ArrayList<Edge> existingEdges = new ArrayList<Edge>();

        // choose the minimum edges needed so that the graph may be ?connected?
        int nE = Math.min(
                this.nEdges,
                (int) (N * (N - 1) / 2));
        
        // The idea here is that if the source and target we
        // initially chose has already been created, then create
        // the next closest edge according to our enumeration. ---> USING TArithmeticOperations
        // Randomly selecting a new edge is computationally
        // prohibitive when the number of edges approaches the maximum
        // (due to performance issues).
        Queue<T> nodeQ = new LinkedList<T>();
        // create all node ids
        ArrayList<T> t_node_ids = (ArrayList<T>) createNodeIds(alphabet, arith, N);
        ArrayList<T> allnodes = (ArrayList<T>) g.insertAllNodes(t_node_ids);
        // add all node ids to node queue
        nodeQ.addAll(allnodes);

        for (int i = 0; i < nE; i++) {
            ArrayList<T> removed = new ArrayList<T>();
//            System.out.println("Queue: "+nodeQ);
            T source = nodeQ.poll(); // removes source node from queue
            T target = nodeQ.peek(); // DOESN'T remove the target node from queue
            removed.add(source);

//            System.out.println("Source:"+source+"\tTarget:"+target);
            // if "edge:source->target" exists in graph, so does "edge:target->source"
            boolean check = (g.getWeight(source, target) != null) ? (false) : (true);
            // if check is false it means the edge already exists and we must
            // find another target node
            while (!check) {
                target = nodeQ.poll();
                removed.add(target);
                check = (g.getWeight(source, target) == null) ? (false) : (true);
            }

            // when check is finally true, it means the found edge doesnt exist yet - we can add it to the graph
            Y random_w = yrand.random(MIN_WEIGHT, MAX_WEIGHT);
            g.addEdge(source, target, random_w);
            nodeQ.addAll(removed);
        }

        return g;
    }

    private GraphADT gnp(GraphADT g, int N) {
        Random random = new Random(System.currentTimeMillis());
        // create nodes ids and insert nodes in graph
        ArrayList<T> t_node_ids = (ArrayList<T>) createNodeIds(alphabet, arith, N);
        ArrayList<T> allnodes = (ArrayList<T>) g.insertAllNodes(t_node_ids);
        ArrayList<T> visited = new ArrayList<T>();
        // create random egdes while the graph is not connected
        while (!g.connected()) {
            // For each node
            for (T i : allnodes) {
                visited.add(i);
                // For every other node
                ArrayList<T> available = new ArrayList<T>(allnodes);
                available.removeAll(visited);
                for (T j : available) {
                    // If random indicates this edge exists
                    if (random.nextDouble() <= this.prob) {
                        // Create and edge between node i and node j
                        Y random_weight = this.yrand.random(MIN_WEIGHT, MAX_WEIGHT);
                        g.addEdge(i, j, random_weight);
                    }
                }
            }
        }
        return g;
    }

    /**
     * Auxiliary function to be called by node-id generator function.
     * @param alpha Alphabet to consider
     * @param root Structure to store nodes
     * @param N Number of nodes wanted
     * @param level Level in tree in which this recursive function is
     * @param arith Operation-handler for type <i>T</i>
     * @return Combinations of the alphabet in an N-ary tree.
     */
    private NTreeADT<T> recursiveT(
            ArrayList<T> alpha,
            NTreeADT<T> root,
            int N,
            int level,
            TArithmeticOperations<T> arith) {
        //
        if (Math.pow(alpha.size(), level - 1) > N) {
            return null;
        }

        for (int i = 0; i < alpha.size(); i++) {
            NTreeADT child = new NTreeADT(arith.Cat(root.data, alpha.get(i)));
            recursiveT(alpha, child, N - root.size(), level + 1, arith);
            root.addChild(child);
        }
        return root;
    }

    /**
     * Returns a collection of T-typed node-ids ready to use, without repetitions.
     * @param alpha Alphabet to consider in creating the id collection (accepts less elements than the number of nodes wanted).
     * @param arith Arithmetic interface in order to perform operations on the type T.
     * @param N Number of nodes wanted.
     * @return Collection of T's which will serve has node-ids.
     */
    public Collection<T> createNodeIds(
            Collection<T> alpha,
            TArithmeticOperations<T> arith,
            int N) {
        Collection<T> nodeIds = new ArrayList<T>();
        // add null element of alphabet to root for prefix operations
        NTreeADT<T> root = new NTreeADT<T>(arith.zero_element());
        // build tree recursively
        root = recursiveT(new ArrayList<T>(alpha), root, N, 0, arith);
        // get array of tree with all elements
        ArrayList<T> aux = (ArrayList<T>) root.BFSTreeElements();
        // removing null element in root
        aux.remove(0);
        // now use only N elements, since the recursive function may return more than needed
        for (int i = 0; i < N; i++) {
            nodeIds.add(aux.get(i));
        }
        return nodeIds;
    }

    /*
     * Test function for strings
     */
    private static NTreeADT recursiveStr(
            ArrayList<String> alpha,
            NTreeADT<String> root,
            int N,
            int level) {
        if (Math.pow(alpha.size(), level) > N) {
            return null;
        }

        for (int i = 0; i < alpha.size(); i++) {
            NTreeADT child = new NTreeADT(root.data + alpha.get(i));
            recursiveStr(alpha, child, N - root.size(), level + 1);
            root.addChild(child);
        }
        return root;
    }

    /**
     * Generates <b>N</b> graphs (<i>N = n_areas</i>) and then joins them in
     * order to generate a graph with <b>n_areas</b> dense areas.
     * @param g Dummy graph to denote the instance type of the resulting graph
     * @param n_areas Number of density areas
     * @param total_edges Number of total edges in the cluster
     * @param total_nodes Number of total nodes in the cluster
     * @return Clustered graph
     */
    public GraphADT createCluster(
            GraphADT<T, Y> g,
            int areas,
            int total_edges,
            int total_nodes) {
        GraphADT cluster = g;
        //

        //
        return cluster;
    }

    /**
     * generate graph with the #edges according to the parameter 'opt'
     * @param numNodes
     * @param opt
     */
    public static GraphADT genGNumEdge(int numNodes, int opt) {
        // opt = 0 -> minEdges
        // opt = 1 -> normal #edges
        // opt = 2 -> maxEdges
        int numEdges;
        switch (opt) {
            case 0:
                numEdges = numNodes - 1;
                break;
            case 1:
                numEdges = (numNodes * (numNodes - 1)) / 2;
                break;
            case 2:
                numEdges = numNodes * (numNodes - 1);
                break;
            default:
                numEdges = (numNodes * (numNodes - 1)) / 2;
        }
        
        TArithmeticOperations<String> strArith = Constants.strArith;
        YRandomizer<Integer> iRand = Constants.randInteger;

        ArrayList<String> alpha = new ArrayList<String>();
        alpha.add("A");
        alpha.add("B");
        alpha.add("C");
        alpha.add("D");
        alpha.add("E");
        alpha.add("F");
        // following the GNM model
        GraphGenADT<String, Integer> ggen = new GraphGenADT<String, Integer>(
                numNodes, // numV
                numEdges, // numE
                90,// max_weight
                5, // min_weight
                iRand,
                strArith,
                alpha);
        GraphMapAdj<String, Integer> g = new GraphMapAdj<String, Integer>(numNodes);
        g = (GraphMapAdj<String, Integer>) ggen.generate(g, numNodes);
        return g;
    }

    public static void main2(String[] args) throws InterruptedException {

        TArithmeticOperations<String> strArith = Constants.strArith;

        YRandomizer<Double> dRand = Constants.randDouble;
        YRandomizer<Integer> iRand = Constants.randInteger;

        ArrayList<String> alpha = new ArrayList<String>();
        alpha.add("A");
        alpha.add("B");
        alpha.add("C");
        alpha.add("D");
        alpha.add("E");
        alpha.add("F");

//        GraphGenADT<String, Integer> ggen = new GraphGenADT<String, Integer>(
//                0.60,
//                90,
//                5,
//                iRand,
//                strArith,
//                alpha);
        GraphGenADT<String, Integer> ggen = new GraphGenADT<String, Integer>(
                10, // numV
                9, // numE
                90,// max_weight
                5, // min_weight
                iRand,
                strArith,
                alpha);

        GraphMapAdj<String, Integer> g = new GraphMapAdj<String, Integer>(10);

        g = (GraphMapAdj<String, Integer>) ggen.generate(g, 10);
        System.out.println(g.toString());

//        PrimADT bor = new PrimADT(g);
//        long begin = System.currentTimeMillis();
//        GraphADT mst = bor.getMst();
//        long end = System.currentTimeMillis();
//        System.out.println(mst);
//        System.out.println("Time: " + (end - begin) + " ms");
    }

    public static void main(String[] args) throws IOException {

//        int opt = Integer.parseInt(args[0]);
//        int min = Integer.parseInt(args[1]);
//        int max = Integer.parseInt(args[2]);
//        for (int size = min; size <= max; size += 50) {
//            GraphOutput gout = new GraphOutput("bench" + size + "_test_edgevar" + opt + ".ser");
//            GraphADT g = genGNumEdge(size, opt);
//            gout.saveGraphADT(g);
//        }

        GraphInput gin = new GraphInput("bench50_test_edgevar2.ser");
        GraphADT g = gin.readGraphADT();
        System.out.println(g);
        System.out.println(g.connected());
    }
}
