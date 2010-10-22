/*
 * Edge class:  to use referencing ohter nodes when the graph has a weight 
 *              parameter. If it doesn't have the weight parameter we can
 *              simply use Integer in adjacency list elements.
 *
 * Ideas:       Make an abstract type implementation in adjancy list,
 *              GraphAdjLists<TYPE>, eg.:
 *                  GraphAdjLists<Integer>
 *                  GraphAdjLists<String>
 *                  (etc.)
 * 
 *              If I do it this way, the classes must have very good control
 *              over the methods that might or might not use specific parameters
 *              like the 'weight' parameter in Edge, parameters we dont need in
 *              unweighted graphs.
 * 
 */
package GraphAD;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author nuno
 */
public class Edge implements Comparable<Edge>, Serializable {

    private int from;
    private int dest;
    private int weight;

    public Edge(int from, int dest, int weight) {
        this.from = from;
        this.dest = dest;
        this.weight = weight;
    }

    public void setWeight(int w) {
        this.weight = w;
    }

    public void setDest(int dest) {
        this.dest = dest;
    }

    public int getWeight() {
        return weight;
    }

    public int getDest() {
        return dest;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public boolean isValid() {
        return (from != -1) && (dest != -1) && (weight != -1);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(from).append(" -(").append(weight).append(")-> ").append(dest).append(", ");
        return s.toString();
    }

    public boolean equals(Edge e) {
        return weight == e.getWeight() && dest == e.getDest() && from == e.getFrom();
    }

    public int compareTo(Edge o) {
        return weight - o.getWeight();
    }

    public static void main(String[] args) {
        Edge a = new Edge(1, 5, 100);
        Edge b = new Edge(1, 2, 20);
        Edge c = new Edge(1, 3, 150);
        Edge d = new Edge(2, 1, 1200);
        Edge e = new Edge(2, 0, 90);
        ArrayList<Edge> edges = new ArrayList<Edge>();
        edges.add(a);
        edges.add(b);
        edges.add(c);
        edges.add(d);
        edges.add(e);
        System.out.println(edges);
        Collections.sort(edges);
        System.out.println(edges);
    }
}
