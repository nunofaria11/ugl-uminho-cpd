/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NodeOriented;

/**
 *
 * @author nuno
 */
public class Node<T> {

    T data;
    boolean mark;

    public Node(T data) {
        this.data = data;
        this.mark = false;
    }

    public Node() {
    }

    public Node(Object o, boolean control) {
        Node<T> n = (Node<T>) o;
        this.data = (T)n.getData();
        this.mark = n.mark;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
//        Node<T> n = (Node<T>) obj;
//        if (n.data == null && this.data == null) {
//            return true;
//        }
//        if (this.data == null) {
//            return false;
//        }
//        if (n.data == null) {
//            return false;
//        }
        return data.toString().equals(obj.toString());
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
