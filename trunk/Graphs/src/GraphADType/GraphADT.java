/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType;

import NodeOriented.Node;

/**
 *
 * @author nuno
 */
abstract public class GraphADT<T, Y> {

    abstract public void addVertices(int n);

    abstract public int order();

    abstract public void addArc(Node<T> n1, Node<T> n2, Y w);

    abstract public void addEdge(Node<T> n1, Node<T> n2, Y w);

    abstract public boolean isArc(Node<T> n1, Node<T> n2);

    abstract public Y getWeight(Node<T> n1, Node<T> n2);

    @Override
    abstract public String toString();
}
