/*
 * T: node datatype
 * Y: edge datatype
 * 
 */
package EdgeOriented;

import NodeOriented.Node;

/**
 * Edge of node-oriented.
 * @author nuno
 */
public class EdgeEO<T, Y extends Comparable<Y>> implements Comparable<EdgeEO<T, Y>> {

    Node<T> node1;
    Node<T> node2;
    Y edge_data;

    public EdgeEO(Node<T> node1, Node<T> node2, Y edge_data) {
        this.node1 = node1;
        this.node2 = node2;
        this.edge_data = edge_data;
    }

    public EdgeEO(Object o) {
        if (o instanceof EdgeEO) {
            EdgeEO e = (EdgeEO) o;
            node1 = e.getNode1();
            node2 = e.getNode2();
            edge_data = (Y) e.getEdge_data();
        }
    }

    public EdgeEO() {
        this.node1 = new Node<T>();
        this.node2 = new Node<T>();
        this.edge_data = null;
    }

    public EdgeEO(Node<T> node1, Node<T> node2, Object edge_data) {
        this.node1 = node1;
        this.node2 = node2;
        this.edge_data = (Y) edge_data;
    }

    public Y getEdge_data() {
        return edge_data;
    }

    public void setEdge_data(Y edge_data) {
        this.edge_data = edge_data;
    }

    public Node<T> getNode1() {
        return node1;
    }

    public void setNode1(Node<T> node1) {
        this.node1 = node1;
    }

    public Node<T> getNode2() {
        return node2;
    }

    public void setNode2(Node<T> node2) {
        this.node2 = node2;
    }

    public boolean contains(Node<T> node) {
        return node.equals(getNode1()) || node.equals(getNode2());
    }

    public Node<T> getRandom() {
        return Math.random() < 0.5 ? getNode1() : getNode2();
    }



    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        String w = edge_data.toString();
        s.append(node1.toString()).append(" -(").append(w).append(")-> ").append(node2.toString()).append(", ");
        return s.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof EdgeEO)) {
            return false;
        }
        EdgeEO edge = (EdgeEO) obj;

        if (edge.node1 == null || edge.node2 == null || edge.edge_data == null) {
            return false;
        }
        if (!edge.getNode1().equals(node1)) {
            return false;
        }
        if (!edge.getNode2().equals(node2)) {
            return false;
        }
        if (!edge.getEdge_data().equals(edge_data)) {
            return false;
        }
        return true;
    }

    public int compareTo(EdgeEO<T, Y> that) {
        if (that == null) {
            return 1;
        }

        if (that.node1 == null || that.node2 == null) {
            return 1;
        }
        if (this.edge_data == null && that.edge_data == null) {
            return 0;
        }
        if (this.edge_data == null) {
            return -1;
        }
        if (that.edge_data == null) {
            return 1;
        }
        return this.edge_data.compareTo(that.edge_data);
    }

    public static void main(String[] args) {
        Node<String> n0 = new Node<String>("A");
        Node<String> n1 = new Node<String>("B");
        Node<String> n2 = new Node<String>("C");

        EdgeEO<String, Double> e1 = new EdgeEO<String, Double>(n0, n1, 10);
        EdgeEO<String, Double> e2 = null;//new EdgeEO<String, Double>(n0, n2, 20);
        System.out.println(e1.compareTo(e2));
    }
}
