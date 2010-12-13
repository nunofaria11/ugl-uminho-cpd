/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package EdgeOriented;

import NodeOriented.Node;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Edge oriented graph.
 * T: datatype of node.
 * Y: datatype of edge.
 * @author nuno
 */
public class GraphEO<T, Y extends Comparable<Y>>{

    List<Edge<T, Y>> edges;

    public GraphEO() {
        edges = new ArrayList<Edge<T, Y>>();
    }

    public boolean add(Edge<T, Y> edge) {
        return edges.add(edge);
    }

    public boolean contains(T node) {
        for (Edge e : edges) {
            if (e.contains(node)) {
                return true;
            }
        }
        return false;
    }

    public Edge<T, Y> getRandom() {
        return edges.get((int) (Math.random() * edges.size()));
    }

    /**
     * It is important to do this with a key map so the nodes won't be duplicated
     * in the returned collection - since several edges may point to the same
     * node.
     * 
     * @return nodes Collection with all the nodes in the graph.
     */
    public Collection<T> getNodes() {
        Map<T, T> nodes = new HashMap<T, T>();
        for (Edge edge : edges) {
            T n1 = (T) edge.getNode1();
            T n2 = (T) edge.getNode2();

            nodes.put(n1, n1);
            nodes.put(n2, n2);
        }
        return nodes.values();
    }

    /**
     *
     * @return order Number of nodes.
     */
    public int order() {
        return getNodes().size();
    }

    /**
     *
     * @return size Number of edges.
     */
    public int size() {
        return edges.size();
    }
}
