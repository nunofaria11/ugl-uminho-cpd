/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Support;

/**
 *
 * @author nuno
 */
public class UnionFind {

    private int[] id, sz;

    public int find(int x) {
        while (x != this.id[x]) {
            x = this.id[x];
        }

        return x;
    }

    public UnionFind(int N) {
        id = new int[N];
        sz = new int[N];

        for (int i = 0; i < N; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    public boolean find(int p, int q) {
        return (find(p) == find(q));
    }

    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j) {
            return;
        }
        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
        } else {
            id[j] = i;
            sz[i] += sz[j];
        }
    }
}
