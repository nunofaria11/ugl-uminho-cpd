/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType.Support;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Union find structure with abstract data types.
 * @author nuno
 */
public class UnionFind_ADT<X> {

    private Map<X, Integer> sz;
    private Map<X, X> id;

    public UnionFind_ADT(Collection<X> xs) {
        id = new HashMap<X, X>(xs.size());
        sz = new HashMap<X, Integer>(xs.size());
        for (X x : xs) {
            id.put(x, x);
            sz.put(x, 1);
        }
    }

    public X find(X x) {
        while (!x.equals(this.id.get(x))) {
            x = this.id.get(x);
        }
        return x;
    }

    public boolean find(X p, X q) {
        return (find(p).equals(find(q)));
    }

    public void union(X p, X q) {
        X i = find(p);
        X j = find(q);
        if (i.equals(j)) {
            return;
        }
        if (sz.get(i) < sz.get(j)) {
            id.put(i, j);
            sz.put(j, sz.get(j) + sz.get(i));
        } else {
            id.put(j, i);
            sz.put(i, sz.get(i) + sz.get(j));
        }
    }

    @Override
    public String toString() {
        return "UnionFind_ADT{" + "sz=" + sz + "id=" + id + '}';
    }

    

}
