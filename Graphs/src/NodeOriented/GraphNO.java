/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NodeOriented;

import EdgeOriented.EdgeEO;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Node oriented graph
 * @author nuno
 */
public class GraphNO<T>{

    List<Node<T>> nodes;

    public GraphNO(List<Node<T>> nodes) {
        this.nodes = nodes;
    }

    public GraphNO() {
        nodes = new ArrayList<Node<T>>();
    }

    public boolean add(Node<T> node) {
        return nodes.add(node);
    }

    public int order() {
        return nodes.size();
    }

    public List<Node<T>> getNodes() {
        return nodes;
    }

    public Node<T> getRandom() {
        return nodes.get(new Random().nextInt(order()));
    }

    public static void main(String[] args) {

        // Example of undirected-node oriented.

        GraphNO g = new GraphNO();
        UNode<Integer, Integer> node0 = new UNode<Integer, Integer>(0);
        UNode<Integer, Integer> node1 = new UNode<Integer, Integer>(1);
        UNode<Integer, Integer> node2 = new UNode<Integer, Integer>(2);
        UNode<Integer, Integer> node3 = new UNode<Integer, Integer>(3);
        UNode<Integer, Integer> node4 = new UNode<Integer, Integer>(4);
        UNode<Integer, Integer> node5 = new UNode<Integer, Integer>(5);
        UNode<Integer, Integer> node6 = new UNode<Integer, Integer>(6);

        node0.add(new EdgeEO<Integer, Integer>(node0, node1, 7));
        node0.add(new EdgeEO<Integer, Integer>(node0, node3, 5));

        node1.add(new EdgeEO<Integer, Integer>(node1, node0, 7));
        node1.add(new EdgeEO<Integer, Integer>(node1, node2, 8));
        node1.add(new EdgeEO<Integer, Integer>(node1, node3, 9));
        node1.add(new EdgeEO<Integer, Integer>(node1, node4, 7));

        node2.add(new EdgeEO<Integer, Integer>(node2, node1, 8));
        node2.add(new EdgeEO<Integer, Integer>(node2, node4, 5));

        node3.add(new EdgeEO<Integer, Integer>(node3, node0, 5));
        node3.add(new EdgeEO<Integer, Integer>(node3, node1, 9));
        node3.add(new EdgeEO<Integer, Integer>(node3, node4, 15));
        node3.add(new EdgeEO<Integer, Integer>(node3, node5, 6));

        node4.add(new EdgeEO<Integer, Integer>(node4, node1, 7));
        node4.add(new EdgeEO<Integer, Integer>(node4, node2, 5));
        node4.add(new EdgeEO<Integer, Integer>(node4, node3, 15));
        node4.add(new EdgeEO<Integer, Integer>(node4, node5, 8));
        node4.add(new EdgeEO<Integer, Integer>(node4, node6, 9));

        node5.add(new EdgeEO<Integer, Integer>(node5, node3, 6));
        node5.add(new EdgeEO<Integer, Integer>(node5, node4, 8));
        node5.add(new EdgeEO<Integer, Integer>(node5, node6, 11));

        node6.add(new EdgeEO<Integer, Integer>(node6, node4, 9));
        node6.add(new EdgeEO<Integer, Integer>(node6, node5, 11));

        g.add(node0);
        g.add(node1);
        g.add(node2);
        g.add(node3);
        g.add(node4);
        g.add(node5);
        g.add(node6);

        System.out.println(g.toString());
    }
}
