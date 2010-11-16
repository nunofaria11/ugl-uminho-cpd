/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NodeOriented;

import GraphADType.VisitorsDecorators.Decorator;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author nuno
 */
public class Node<T> implements Serializable {

    T data;
    boolean mark;
    HashMap<String,Decorator> properties = new HashMap<String, Decorator>();

    public Node() {
    }

    public Node(T data) {
        this.data = data;
        this.mark = false;
        this.properties = new HashMap<String, Decorator>();
    }

    public Node(T data, HashMap<String,Decorator> properties) {
        this.data = data;
        this.properties = properties;
    }

    public Node(Object o, boolean control) {
        Node<T> n = (Node<T>) o;
        this.data = (T) n.getData();
        this.mark = n.mark;
        this.properties = new HashMap<String, Decorator>();
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

        return data.toString().equals(obj.toString());
    }

    @Override
    public String toString() {
        return data.toString();
    }

    public boolean addProperty(Decorator d) {
        // checking if a property if this type already exists
        for (String dec_id : properties.keySet()) {
            if (dec_id.equals(d.getDec_id())) {
                // overwrite the existing color
                properties.remove(dec_id);
            }
        }
        properties.put(d.getDec_id(), d);
        return true;
    }

    public Decorator getProperty(String id){
        return properties.get(id);
    }

}
