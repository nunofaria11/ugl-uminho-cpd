/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADT_2nd_try;

import GraphADType.Support.Constants;
import GraphADType.Support.NTreeADT;
import GraphADType.Support.TArithmeticOperations;
import JungTest.EdgeJ;
import JungTest.EdgeJHandler;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 *
 * @author nuno
 */
public class GraphGenerator<V extends Serializable, E extends Comparable<E>> {

    final EdgeRandomizer<E> erand;
    final TArithmeticOperations<V> arith;
    final ArrayList<V> alphabet;
    private int nEdges;
    private double prob;

    /**
     * Constructor to define the number of nodes and automatically assign the
     * graph with the maximum number of edges.
     *
     * @param numV Number of vertices
     * @param yrand Handler for random edge weights
     * @param arith Handler for arithmetic operations on node ids, to generate the necessary ids.
     * @param alphabet The elements in this collection will be used to form an id for each node the graph needs.
     */
    public GraphGenerator(
            int numV,
            EdgeRandomizer<E> erand,
            TArithmeticOperations<V> arith,
            ArrayList<V> alphabet) {
        this.erand = erand;
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
     * @param yrand Handler for random edge weights
     * @param arith Handler for arithmetic operations on node ids, to generate the necessary ids.
     * @param alphabet The elements in this collection will be used to form an id for each node the graph needs.
     */
    public GraphGenerator(
            int numV,
            int numE,
            EdgeRandomizer<E> erand,
            TArithmeticOperations<V> arith,
            ArrayList<V> alphabet) {
        this.erand = erand;
        this.nEdges = numE;
        this.prob = -1;
        this.arith = arith;
        this.alphabet = alphabet;
    }

    /**
     * Constructor to define the connection probability each graph node will have
     * of connecting to another different node.
     * @param p Connection probability
     * @param yrand Handler for random edge weights
     * @param arith Handler for arithmetic operations on node ids, to generate the necessary ids.
     * @param alphabet The elements in this collection will be used to form an id for each node the graph needs.
     */
    public GraphGenerator(
            double p,
            EdgeRandomizer<E> erand,
            TArithmeticOperations<V> arith,
            ArrayList<V> alphabet) {
        this.erand = erand;
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
     * @param g Graph instance
     * @param N number of nodes
     * @return Random graph
     */
    public BaseGraph<V, E> generate(BaseGraph<V, E> g, int N) {

        if (nEdges != -1) // case 1
        {
            return this.gnm(g, N);
        } else // case 2
        {
            return this.gnp(g, N);
        }
    }

    private BaseGraph<V, E> gnm(BaseGraph<V, E> g, int N) {
        int nE = Math.min(
                this.nEdges,
                (int) (N * (N - 1) / 2));

        Queue<V> nodeQ = new LinkedList<V>();
        // ======== create node ids ========
        ArrayList<V> allnodes = (ArrayList<V>) createNodeIds(alphabet, arith, N);
        // =================================
        for (V v : allnodes) {
            g.addVertex(v);
        }
        nodeQ.addAll(allnodes);

        for (int i = 0; i < nE; i++) {
            ArrayList<V> removed = new ArrayList<V>();

            V source = nodeQ.poll(); // removes source node from queue
            V target = nodeQ.peek(); // DOESN'T remove the target node from queue

            removed.add(source);
            boolean check = (g.findEdge(source, target) == null) ? (true) : (false);
            // if check is false it means the edge already exists and we must
            // find another target node
            while (!check) {
                target = nodeQ.poll();
                removed.add(target);
                check = (g.findEdge(source, target) == null) ? (true) : (false);
            }

            // when check is finally true, it means the found edge doesnt exist yet - we can add it to the graph
            E random_w = erand.random();
            g.addEdge((E) random_w, (V) source, (V) target);
            nodeQ.addAll(removed);
        }
        return g;
    }

    private BaseGraph<V, E> gnp(BaseGraph<V, E> g, int N) {
        Random random = new Random(System.currentTimeMillis());
        // create nodes ids and insert nodes in graph
        ArrayList<V> allnodes = (ArrayList<V>) createNodeIds(alphabet, arith, N);
        for (V v : allnodes) {
            g.addVertex(v);
        }
        ArrayList<V> visited = new ArrayList<V>();
        // create random egdes while the graph is not connected
        while (!g.connected()) {
//            System.out.println(g);
            // For each node
            for (V i : allnodes) {
                visited.add(i);
                // For every other node
                ArrayList<V> available = new ArrayList<V>(allnodes);
                available.removeAll(visited);
                for (V j : available) {
                    // If random indicates this edge exists
                    if (random.nextDouble() <= this.prob) {
                        // Create a random weighted edge between node i and node j
                        E random_weight = this.erand.random();
                        g.addEdge(random_weight, i, j);
                    }
                }
            }
//            System.out.println();
        }
        return g;
    }

    /**
     * Auxiliary function to be called by node-id generator function.
     * @par/am alpha Alphabet to consider
     * @param root Structure to store nodes
     * @param N Number of nodes wanted
     * @param level Level in tree in which this recursive function is
     * @param arith Operation-handler for vertex type <i>V</i>
     * @return Combinations of the alphabet in an N-ary tree.
     */
    private NTreeADT<V> recursiveT(
            ArrayList<V> alpha,
            NTreeADT<V> root,
            int N,
            int level,
            TArithmeticOperations<V> arith) {
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
     * Returns a collection of V-typed node-ids ready to use, without repetitions.
     * @param alpha Alphabet to consider in creating the id collection (accepts less elements than the number of nodes wanted).
     * @param arith Arithmetic interface in order to perform operations on the type V.
     * @param N Number of nodes wanted.
     * @return Collection of V's which will serve has node-ids.
     */
    public Collection<V> createNodeIds(
            Collection<V> alpha,
            TArithmeticOperations<V> arith,
            int N) {
        Collection<V> nodeIds = new ArrayList<V>();
        // add null element of alphabet to root for prefix operations
        NTreeADT<V> root = new NTreeADT<V>(arith.zero_element());
        // build tree recursively
        root = recursiveT(new ArrayList<V>(alpha), root, N, 0, arith);
        // get array of tree with all elements
        ArrayList<V> aux = (ArrayList<V>) root.BFSTreeElements();
        // removing null element in root
        aux.remove(0);
        // now use only N elements, since the recursive function may return more than needed
        for (int i = 0; i < N; i++) {
            nodeIds.add(aux.get(i));
        }
        return nodeIds;
    }

    /**
     * generate graph with the #edges according to the parameter 'opt'
     * @param numNodes
     * @param opt
     */
    public static Graph genGNumEdge(int numNodes, int opt) {
        throw new UnsupportedOperationException();
    }

    public static void main(String args[]) {
        /*
         * Edge randomizer
         */
        EdgeRandomizer<EdgeJ<Integer>> edge_randomizer = new EdgeRandomizer<EdgeJ<Integer>>() {

            private EdgeJHandler handler = new EdgeJHandler();

            public EdgeJ<Integer> random() {
                Random r = new Random(System.currentTimeMillis());
                Integer intValue = r.nextInt(upperLimit().data - lowerLimit().data) + lowerLimit().data;
                return new EdgeJ<Integer>(intValue, handler);
            }

            public EdgeJ<Integer> upperLimit() {
                return new EdgeJ<Integer>(90, handler);
            }

            public EdgeJ<Integer> lowerLimit() {
                return new EdgeJ<Integer>(5, handler);
            }
        };
        /*
         * Vertex alphabet
         */
        ArrayList<String> alphabet = new ArrayList<String>();
        alphabet.add("A");
        alphabet.add("B");
        alphabet.add("C");
        alphabet.add("D");
        alphabet.add("E");
        alphabet.add("F");
        alphabet.add("G");
        /*
         * Generator instance
         */
        GraphGenerator<String, EdgeJ<Integer>> graphgen = new GraphGenerator<String, EdgeJ<Integer>>(
                0.6, // connection probability
//                                8, // #edges
                edge_randomizer, // edge randomizer
                Constants.strArith, // vertex arithmetic
                alphabet);          // vertex alphabet

        BaseGraph<String, EdgeJ<Integer>> g = graphgen.generate(new UndirectedGraph<String, EdgeJ<Integer>>(), 100);

        System.out.println(g.toString());

    }
}
