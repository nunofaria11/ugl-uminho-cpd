/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphAD.Representations;

import GraphAD.Representations.GraphAdjLists;
import GraphAD.Edge;
import GraphAD.GraphAD;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author nuno
 */
public class GraphAdjMatrix extends GraphAD implements Serializable {

    /**
     * Current space allocated.
     */
    private int _space;
    /**
     * Number of vertices.
     */
    private int _order;
    /**
     * Adjacency matrix.
     */
    private int _adj[][];

    private static int[][] _allocate(int n) {
        int matrix[][] = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = -1;
            }
        }
        return matrix;
    }

    public GraphAdjMatrix() {
        _space = _order = 0;
        _allocate(0);
    }

    public GraphAdjMatrix(GraphAdjMatrix G) {
        int n = G.order();
        if (n > 0) {
            _adj = _allocate(n);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                _adj[i][j] = G._adj[i][j];
            }
        }
    }

    @Override
    public void addVertices(int n) {
        if (n > _space - _order) {  // if its bigger add to matrix
            int matrix[][] = _allocate(_order + n);
            for (int i = 0; i < _order; i++) {
                for (int j = 0; j < _order; j++) {
                    matrix[i][j] = _adj[i][j];
                }
            }
            _adj = matrix;
            _space = n + _order;
            _order += n;
        } else {                  // expand and reclean
            for (int i = 0; i < _order + n; i++) {
                for (int j = 0; j < _order + n; j++) {
                    _adj[i][j] = _adj[j][i] = -1;
                }
            }
            _order += n;
        }
    }

    @Override
    public void removeVertex(int v) {
        _order--;
        int i;
        for (i = 0; i < v; i++) {
            for (int j = v; j < _order; j++) {
                _adj[i][j] = _adj[i][j + 1];
            }
        }

        for (i = v; i < _order; i++) {
            int j;
            for (j = 0; j < v; j++) {
                _adj[i][j] = _adj[i + 1][j];
            }
            for (j = v; j < _order; j++) {
                _adj[i][j] = _adj[i + 1][j + 1];
            }
        }
    }

    @Override
    public void addArc(int i, int j, int w) {
        if (!isArc(i, j)) {
            _adj[i][j] = w;
        }
    }

    @Override
    public void removeArc(int i, int j) {
        _adj[i][j] = -1;
    }

    @Override
    public boolean isArc(int i, int j) {
        return (_adj[i][j] != -1);
    }

    @Override
    public int inDegree(int i) {
        int inTotal = 0;
        for (int j = 0; j < order(); j++) {
            if (_adj[j][i] != -1) {
                inTotal++;
            }
        }
        return inTotal;
    }

    @Override
    public int outDegree(int i) {
        int outTotal = 0;
        for (int j = 0; j < order(); j++) {
            if (_adj[i][j] != -1) {
                outTotal++;
            }
        }
        return outTotal;
    }

    @Override
    public ArrayList neighbors(int v) {
        ArrayList<Integer> nbors = new ArrayList<Integer>();
        for (int i = 0; i < order(); i++) {
            if (_adj[v][i] != -1) {
                nbors.add(new Integer(i));
            }
        }
        return nbors;
    }

    @Override
    public ArrayList<Edge> getEdgeNeighbors(int x) {
        ArrayList<Edge> nbors = new ArrayList<Edge>();
        for (int i = 0; i < order(); i++) {
            if (isEdge(x, i)) {
                Edge e = new Edge(x, i, getWeight(x, i));
                nbors.add(e);
            }
        }
        return nbors;
    }

    @Override
    public int order() {
        return _order;
    }

    @Override
    public int size() {
        //number of edges - counts edges twice since we consider it to be undirected
        int size = 0;
        for (int i = 0; i < order(); i++) {
            for (int j = 0; j < order(); j++) {
                if (_adj[i][j] != -1) {
                    size++;
                }
            }
        }
        return size;
    }

    @Override
    public int getWeight(int i, int j) {
        return _adj[i][j];
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Matrix:\n  ");
        for (int i = 0; i < order(); i++) {
            s.append(i).append("\t");
        }
        s.append("\n");

        for (int i = 0; i < order(); i++) {
            s.append(i).append(" | ");
            for (int j = 0; j < order(); j++) {
                if (isArc(i, j)) {
                    s.append(_adj[i][j]).append("\t");
                } else {
                    s.append("X\t");
                }
            }
            s.append("\n");
        }

        return s.toString();
    }

    @Override
    public int MST_TotalWeight() {
        int total = 0;
        ArrayList<Integer> visited = new ArrayList<Integer>();
        for (int i = 0; i < order(); i++) {
            visited.add(i);
            for (int j = 0; j < order(); j++) {

                if (!visited.contains(j) && getWeight(i, j) != -1) {
                    total += getWeight(i, j);
                }
            }
        }
        return total;
    }

    @Override
    public Object findMinEdge(int v, ArrayList<Integer> visited) {
        int minEdge = -1;
        int minWeight = new Integer(Integer.MAX_VALUE);
        int tempWeight;
        for (int j = 0; j < order(); j++) {
            tempWeight = getWeight(v, j);
            if (tempWeight < minWeight) {
                minEdge = j;
                minWeight = tempWeight;
            }
        }
        return minEdge;
    }

    @Override
    public boolean connected() {
        boolean[] visited = new boolean[order()];
        for (int i = 0; i < order(); i++) {
            visited[i] = false;
        }
        for (int i = 0; i < order(); i++) {
            for (int j = 0; j < order(); j++) {
                if (_adj[i][j] != -1) {
                    visited[j] = true;
                }
            }
        }
        boolean c = true;
        for (boolean existsPath : visited) {
            c &= existsPath;
        }
        return c;
    }

    @Override
    public boolean saveToFile(String fileName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GraphAD loadFromFile(String fileName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Edge> getAllEdges() {
        ArrayList<Edge> all = new ArrayList<Edge>();
        for (int v = 0; v < order(); v++) {
            for (int i = 0; i < order(); i++) {
                if (_adj[v][i] != -1) {
                    all.add(new Edge(v, i, getWeight(v, i)));
                }
            }
        }
        return all;
    }

    @Override
    public void clean() {
        _allocate(0);
    }

    @Override
    public GraphAdjMatrix clone() {
        return new GraphAdjMatrix(this);
    }

    /**
     * Converts this adjacency matrix graph to adjacency list.
     * 
     * @return glists Conversion of the adjacency matrix to adjacency lists.
     */
    public GraphAdjLists toGraphAdjLists(){
        GraphAdjLists glists = new GraphAdjLists();
        glists.addVertices(order());
        for(int i=0;i<order();i++){
            for(int j=0;j<order();j++){
                if(i!=j && isEdge(i, j)){
                    glists.addEdge(i, j, getWeight(i, j));
                }
            }
        }
        return glists;
    }
}
