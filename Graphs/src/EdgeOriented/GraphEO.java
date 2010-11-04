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

    List<EdgeEO<T, Y>> edges;

    public GraphEO() {
        edges = new ArrayList<EdgeEO<T, Y>>();
    }

    public boolean add(EdgeEO<T, Y> edge) {
        return edges.add(edge);
    }

    public boolean contains(Node<T> node) {
        for (EdgeEO e : edges) {
            if (e.contains(node)) {
                return true;
            }
        }
        return false;
    }

    public EdgeEO<T, Y> getRandom() {
        return edges.get((int) (Math.random() * edges.size()));
    }

    /**
     * It is important to do this with a key map so the nodes won't be duplicated
     * in the returned collection - since several edges may point to the same
     * node.
     * 
     * @return nodes Collection with all the nodes in the graph.
     */
    public Collection<Node<T>> getNodes() {
        Map<T, Node<T>> nodes = new HashMap<T, Node<T>>();
        for (EdgeEO edge : edges) {
            Node<T> n1 = edge.getNode1();
            Node<T> n2 = edge.getNode2();

            nodes.put(n1.getData(), n1);
            nodes.put(n2.getData(), n2);
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
