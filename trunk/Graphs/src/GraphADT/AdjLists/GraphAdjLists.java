/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADT.AdjLists;

import GraphADT.Edge;
import GraphADT.GraphADT;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author nuno
 */
public class GraphAdjLists extends GraphADT implements Serializable {

    ArrayList _adj;

    private void _allocate(int n) {
        _adj = new ArrayList();
        for (int i = 0; i < n; i++) {
            _adj.add(new ArrayList<Edge>());
        }
    }

    public GraphAdjLists() {
        _allocate(0);
    }

    public GraphAdjLists(GraphAdjLists g) {
        int n = g.order();
        _adj = new ArrayList<Edge>();
        for (int i = 0; i < n; i++) {
            _adj.add(g.neighbors(i));
        }
    }
    // Create later a buffered reader constructor

    @Override
    public void addVertices(int n) {
        if (n > 0) {
            for (int i = 0; i < n; i++) {
                _adj.add(new ArrayList<Edge>());
            }
        }
    }

    public Edge getEdge(int i, int j) {
        ArrayList<Edge> edges = ((ArrayList<Edge>) _adj.get(i));
        for (Edge e : edges) {
            if (e.getDest() == j) {
                return e;
            }
        }
        return null;
    }

    @Override
    public void removeVertex(int i) {
        _adj.remove(i);        // simply remove vertex...
        // ...now we have to remove all references to the removed vertex.
        for (int u = 0; u < order(); u++) {
            Edge remEdge = getEdge(u, i);
            ArrayList<Edge> uVec = (ArrayList<Edge>) _adj.get(u);
            uVec.remove(remEdge);              // remove i from adjacency lists

            for (int v = 0; v < uVec.size(); v++) {   // relabel larger indexed nodes
                Edge nbr = ((Edge) uVec.get(v));
                if (nbr.getDest() > i) {
                    uVec.set(v, new Edge(v, nbr.getDest() - 1, nbr.getWeight()));
                }
            }
        }
    }

    @Override
    public void addArc(int i, int j, int w) {
        // if arc already exists do nothing
        if (isArc(i, j)) {
            return;
        }
        ((ArrayList) _adj.get(i)).add(new Edge(i, j, w));
        // new Integer because of reference pointer issues
    }

    @Override
    public void removeArc(int i, int j) {
        ArrayList<Edge> edges = ((ArrayList<Edge>) _adj.get(i));
        for (Edge e : edges) {
            if (e.getDest() == j) {
                ((ArrayList<Edge>) _adj.get(i)).remove(e);
                return;
            }
        }
    }

    @Override
    public boolean isArc(int i, int j) {
        return ((ArrayList) _adj.get(i)).contains(new Integer(j));
    }

    @Override
    public int inDegree(int i) {
        int sz = 0;
        for (int j = 0; j < order(); j++) {
            if (isArc(j, i)) {
                sz++;
            }
        }
        return sz;
    }

    @Override
    public int outDegree(int i) {
        return ((ArrayList) _adj.get(i)).size();
    }

    @Override
    public ArrayList<Edge> neighbors(int i) {
        return (ArrayList<Edge>) ((ArrayList<Edge>) _adj.get(i)).clone();
    }

    @Override
    public int order() {
        // number of vertices
        return _adj.size();
    }

    @Override
    public int size() {
        //number of edges - counts edges twice since we consider it to be undirected
        int total = 0;
        for (int i = 0; i < order(); i++) {
            total += ((ArrayList<Edge>) _adj.get(i)).size();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < order(); i++) {
            s.append("\n" + i + " ::: ");
            for (Edge e : (ArrayList<Edge>) _adj.get(i)) {
                s.append(e.toString());
            }
        }
        return s.toString();
    }

    /*
     * Finds a minimum edge that goes out of a node,
     * considering a list of visited nodes.
     */
    @Override
    public Object findMinEdge(int i, ArrayList<Integer> visited) {
        Edge minEdge = new Edge(-1, -1, new Integer(Integer.MAX_VALUE));
        ArrayList<Edge> edges = (ArrayList<Edge>) _adj.get(i);
        for (Edge edge : edges) {
            if (edge.getWeight() < minEdge.getWeight() && !visited.contains(edge.getDest())) {
                minEdge = edge;
            }
        }
        return minEdge;
    }

    /*
     * Returns the total weight of a graph considering the graph is in MST from.
     *
     * PS: Try to find a  way to ensure that the graph is an MST.
     */
    @Override
    public int MST_TotalWeight() {
        int total = 0;
        ArrayList<Integer> visited = new ArrayList<Integer>();
        for (int i = 0; i < order(); i++) {
            visited.add(i);
            for (Edge e : ((ArrayList<Edge>) _adj.get(i))) {
                if (!visited.contains(e.getDest())) {
                    total += e.getWeight();
                }
            }
        }
        return total;
    }

    @Override
    public int getWeight(int i, int j) {
        return ((Edge) ((ArrayList<Edge>) _adj.get(i)).get(j)).getWeight();
    }

    @Override
    public boolean connected() {
        boolean[] visited = new boolean[order()];
        for (int i = 0; i < order(); i++) {
            visited[i] = false;
        }
        for (int i = 0; i < order(); i++) {
//            visited[i] = true;
            for (Edge e : (ArrayList<Edge>) _adj.get(i)) {
                visited[e.getDest()] = true;
            }
        }
        boolean c = true;
        for (boolean existsPath : visited) {
            c &= existsPath;
        }
        return c;
    }

    @Override
    public boolean saveToFile(String fileName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GraphADT loadFromFile(String fileName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addEdge(Edge e) {
        addEdge(e.getFrom(), e.getDest(), e.getWeight());
    }
}
