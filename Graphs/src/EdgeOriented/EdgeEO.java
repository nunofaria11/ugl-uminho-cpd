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
public class EdgeEO<T, Y> {

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
        s.append(node1.toString()).append(" -(").append(edge_data.toString()).append(")-> ").append(node2.toString()).append(", ");
        return s.toString();
    }



    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof EdgeEO)) {
            return false;
        }
        EdgeEO edge = (EdgeEO) obj;
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
}
