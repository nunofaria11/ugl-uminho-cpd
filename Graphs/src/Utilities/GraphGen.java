/*
 * Utility to produce random graphs according to a connection probability
 * that determines the connection rate between every edge
 */
package Utilities;

import Algorithms.Boruvka;
import GraphADT.AdjLists.GraphAdjLists;
import GraphADT.Edge;
import GraphADT.GraphADT;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author nuno
 */
public final class GraphGen {

    /**
     * Probability - connection ratio
     */
    private final double p;
    private final int nEdges;

    /**
     * Uses the Erdos-Renyi G(N,p) random graph generator model.
     * @param p Connection probability for each node (the number of nodes is defined outside).
     */
    public GraphGen(double p) {
        this.p = p;
        this.nEdges = -1;
    }

    /**
     * Uses the Erdos-Renyi G(N,p) random graph generator model (p is assigned randomly).
     */
    public GraphGen() {
        this.p = getRandomProbability();
        this.nEdges = -1;
    }

    /**
     * Uses the Erdos-Renyi G(N,M) random graph generator model (M = numEdges).
     * @param nEdges Number of edges to use in the graph (no probabilities)
     */
    public GraphGen(int nEdges) {
        this.nEdges = nEdges;
        this.p = -1;
    }

    /**
     * Returns the connection probability (uses the G(N,p) model).
     * @return p Connection probability
     */
    public double getP() {
        return p;
    }

    /**
     * Returns the number of edges to add to the graph (uses the G(N,M) model).
     * @return nEdges Number of edges
     */
    public int getnEdges() {
        return nEdges;
    }

    /**
     * Generate a random number between ]0,1].
     * It cant be zero because one of the (initial) temporary constraints
     * ensure that the graph must be connected (MST constraint).
     *
     * (constraints system must be later added)
     *
     * @return double: connection ratio
     */
    public double getRandomProbability() {
        Random r = new Random(System.currentTimeMillis());
        double prob = r.nextDouble(); // this generates a value in [0,1[, we want ]0,1]
        while (prob == 0) {
            prob = r.nextDouble();
            if (prob == 1) {
                return prob;
            }
        }
        return prob;
    }

    /*
     * Erdős–Rényi random graph generator models:
     * 
     *  1)  In the G(n, M) model, a graph is chosen uniformly at random from the
     *      collection of all graphs which have n nodes and M edges. For example,
     *      in the G(3, 2) model, each of the three possible graphs on three
     *      vertices and two edges are included with probability 1/3.
     *
     *  2)  In the G(n, p) model, a graph is thought to be constructed by
     *      connecting nodes randomly. Each edge is included in the graph
     *      with probability p, with the presence or absence of any two
     *      distinct edges in the graph being independent.
     */
    public GraphADT generate(GraphADT g) {

        if (nEdges != -1) // 1)
        {
            return this.gnm(g);
        } else // 2)
        {
            return this.gnp(g);
        }
    }

    /**
     *
     * STILL HAS A PROBLEM: doesn't always generate connected graphs !<br><br>
     *
     * In the GNM model we don't have to worry about the graph being connected
     * because that is implicitly done by defining the minimum number of edges
     * we want to add to the graph. The minimum number of edges for a connected
     * graph is: (N*(N-1))/2 <br>
     * N = #Vertex
     *
     * @param g Graph pointer to change.
     * @return g Randomly generated graph.
     */
    private GraphADT gnm(GraphADT g) {
        Random random = new Random(System.currentTimeMillis());

        // Auxiliar strcuture used to control the edges that have already been added.
        // We identify each edge using the source and target: source*N + target.
        ArrayList<Integer> existingEdges = new ArrayList<Integer>();

        // ensure the the number of edges is less than the maximum defined.
        int nE = Math.min(
                nEdges,
                (int) (g.order() * (g.order() - 1) / 2));

        // create each edge
        for (int i = 0; i < nE; i++) {
            int source = Math.abs(random.nextInt()) % g.order();
            int target = Math.abs(random.nextInt()) % g.order();

            boolean check = existingEdges.contains(source * g.order() + target);

            int higher = source * g.order() + target + 1;
            int lower = source * g.order() + target - 1;

            // http://chianti.ucsd.edu/svn/csplugins/trunk/soc/pjmcswee/src/cytoscape/randomnetwork/ErdosRenyiModel.java
            // The idea here is that if the source and target we
            // initially chose has already been created, then create
            // the next closest edge according to our enumeration.
            // Randomly selecting a new edge is computationally
            // prohibitive when the number of edges approaches the maximum
            // (due to performance issues).

            while (check || source == target) { // if the edge already exists, enter here and choose another source/target
                //Check to make sure that lower is within bounds
                if (lower < 0) {
                    lower = (g.order() * g.order() - 1);
                }
                //Chck to make sure that higher is within bounds
                if (higher == g.order() * g.order()) {
                    higher = 0;
                }

                //Get the source and target from the lower number
                int source_lo = lower / g.order();
                int target_lo = lower % g.order();

                //Get the source and target from the higher number
                int source_hi = higher / g.order();
                int target_hi = higher % g.order();

                if (source_lo != target_lo) {
                    //Try to get this edge
                    check = existingEdges.contains(source_lo * g.order() + target_lo);
                    //adjacency[source_lo][target_lo];
                    //If this edge does not exist, choose this edge
                    if (!check) {
                        source = source_lo;
                        target = target_lo;
                        break;
                    }
                }

                if (source_hi != target_hi) {
                    //try to get the higher edge
                    check = existingEdges.contains(source_hi * g.order() + target_hi); //adjacency[source_hi][target_hi];
                    //If the edge does not exist choose this edge
                    if (!check) {
                        source = source_hi;
                        target = target_hi;
                        break;
                    }
                }
                higher++;
                lower--;
            }
            existingEdges.add(new Integer(source * g.order() + target));
            // add the next edge to the aux. array because for now we only
            // consider the graph to be undirectioned.
            existingEdges.add(new Integer(target * g.order() + source)); // add double-edge

            int random_weight = random.nextInt(Constants.MAX_WEIGHT) + 5;
            g.addEdge(new Edge(source, target, random_weight));
        }

        return g;

    }

    /**
     *
     * In the GNP model we only need the number of vertices (implicit in the <i>order</i>
     * of the graph and a probability p. In this model each edge has independent probability of existing: p.
     * Therefore the expected number of edges is: p * n * (n - 1)/2.
     * 
     * @param g 
     * @return g Randomly generated graph with connection ratio <i>p</i>
     */
    private GraphADT gnp(GraphADT g) {
        Random random = new Random(System.currentTimeMillis());
        while (!g.connected()) {
            // For each node
            for (int i = 0; i < g.order(); i++) {
                //start defines valid targets for the source node i
                int start = i + 1;
                // For every other node
                for (int j = start; j < g.order(); j++) {
                    //If this i,j represents a reflexive edge, and we
                    //do not allow reflexive edges, ignore it.
                    if (i == j) {
                        continue;
                    }
                    // If random indicates this edge exists
                    if (random.nextDouble() <= this.p) {
                        // Create and edge between node i and node j
                        int random_weight = random.nextInt(Constants.MAX_WEIGHT - Constants.MIN_WEIGHT) + Constants.MIN_WEIGHT;

                        g.addEdge(new Edge(i, j, random_weight));
                    }
                }
            }
        }
        return g;
    }

    public static double avg_weight(GraphADT g) {
        int total = 0;
        int count = 0;
        ArrayList<Edge> allEdges = g.getAllEdges();
        ArrayList<Integer> visited = new ArrayList<Integer>();
        for (Edge e : allEdges) {
            int source = e.getFrom();
            int target = e.getDest();
            int index = source * g.order() + target;
            int index_reverse = target * g.order() + source;
            if (!visited.contains(index) && !visited.contains(index_reverse)) {
                total += e.getWeight();
                count++;
                visited.add(index);
            }
        }
        return total / count;
    }

    public static void main(String[] args) {

        GraphGen ggen = new GraphGen(0.5);

        GraphAdjLists random_graph = new GraphAdjLists();
        random_graph.addVertices(250);

        ggen.generate(random_graph);

//        System.out.println(random_graph);

        Boruvka b = new Boruvka(random_graph);
        GraphADT a = b.MST_Boruvka_UnionFind();
        System.out.println("MST weight: " + a.MST_TotalWeight());
        System.out.println("AVG weight: " + GraphGen.avg_weight(a));

    }
}
