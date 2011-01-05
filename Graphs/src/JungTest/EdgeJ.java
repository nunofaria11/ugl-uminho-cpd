/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JungTest;

import java.io.Serializable;

/**
 *
 * @author nuno
 */
public class EdgeJ<Y extends Comparable<Y>> implements Comparable<EdgeJ<Y>>, Serializable {

    private static final long serialVersionUID = 797963301960447861L;
    public Y data;
    int id;

    public EdgeJ(Y data, EdgeJHandler handler) {
        this.data = data;
        this.id = handler.getNewId();
    }

    @Override
    public String toString() {
        return data.toString();
    }

    public Y getData() {
        return data;
    }

    public int compareTo(EdgeJ<Y> that) {
        if (this.data == null && that.data == null) {
            return 0;
        }
        if (this.data == null) {
            return -1;
        }
        if (that.data == null) {
            return 1;
        }
        return this.data.compareTo(that.data);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EdgeJ<Y> other = (EdgeJ<Y>) obj;
        if (this.data != other.data && (this.data == null || !this.data.equals(other.data))) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.data != null ? this.data.hashCode() : 0);
        hash = 97 * hash + this.id;
        return hash;
    }
}
