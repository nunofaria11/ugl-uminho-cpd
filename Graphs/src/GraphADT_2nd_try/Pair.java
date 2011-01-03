/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADT_2nd_try;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author nuno
 */
public class Pair<X> implements Collection<X> {

    X first;
    X second;

    public Pair(X first, X second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Pair values cannot be null.");
        }
        this.first = first;
        this.second = second;
    }

    public Pair(Collection<? extends X> col) {
        if (col == null) {
            throw new IllegalArgumentException("Collection cannot be null");
        }
        if (col.size() == 2) {
            if (col.contains(null)) {
                throw new IllegalArgumentException("Pair values cannot be null.");
            }
            Iterator<? extends X> it = col.iterator();
            this.first = it.next();
            this.second = it.next();
        }
    }

    public X getFirst() {
        return first;
    }

    public void setFirst(X first) {
        this.first = first;
    }

    public X getSecond() {
        return second;
    }

    public void setSecond(X second) {
        this.second = second;
    }

    public int size() {
        return 2;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean contains(Object o) {
        // compare first with REFERENCES and then with ASSERTIVE equals
        return (first == o || first.equals(o) || second == o || second.equals(o));
    }

    private class PairIterator implements Iterator<X> {

        private int position;

        public PairIterator() {
            position = 0;
        }

        public boolean hasNext() {
            return position < 2;
        }

        public X next() {
            position++;
            if (position == 1) {
                return first;
            } else if (position == 2) {
                return second;
            } else {
                return null;
            }
        }

        public void remove() {
            throw new UnsupportedOperationException("A pair cannot be altered.");
        }
    }

    public Iterator<X> iterator() {
        return new PairIterator();
    }

    public Object[] toArray() {
        ArrayList<X> al = new ArrayList<X>();
        al.add(first);
        al.add(second);
        return al.toArray();
    }

    public <T> T[] toArray(T[] a) {
        ArrayList<X> al = new ArrayList<X>();
        al.add(first);
        al.add(second);
        return al.toArray(a);
    }

    public boolean add(X e) {
        throw new UnsupportedOperationException("A pair cannot be altered.");
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException("A pair cannot be altered.");
    }

    public boolean containsAll(Collection<?> c) {
        if (c.size() > 2) {
            return false;
        }
        Iterator<?> i = c.iterator();
        Object f = i.next();
        Object s = i.next();
        return this.contains(f) && this.contains(s);
    }

    public boolean addAll(Collection<? extends X> c) {
        throw new UnsupportedOperationException("A pair cannot be altered.");
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("A pair cannot be altered.");
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("A pair cannot be altered.");
    }

    public void clear() {
        throw new UnsupportedOperationException("A pair cannot be altered.");
    }
}
