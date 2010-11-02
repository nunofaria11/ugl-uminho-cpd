/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphAD;

/**
 *
 * @author nuno
 */
public class Node<T> {

    T data;

    public Node(T data) {
        this.data = data;
    }

    public Node() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object obj) {
        return data.toString().equals(obj.toString());
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
