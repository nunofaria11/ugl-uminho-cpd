/*
 * Adjacency array representation. Since this isn't a very good representation of
 * dynamic arrays - arrays which may increase in vertex and edge size through its
 * construction and, f. ex., MST construction - it isn't easy to implement.
 *
 * If later implemented do a edge-left-and-rightshifting _adj and _weight arrays.
 *
 * http://www.mpi-inf.mpg.de/~mehlhorn/ftp/Toolbox/GraphRep.pdf
 * 
 */
package GraphAD.Representations;

import GraphAD.Edge;
import GraphAD.GraphAD;
import Utilities.Constants;
import java.util.ArrayList;

/**
 *
 * @author nuno
 */
public class GraphAdjArray extends GraphAD {

    private int[] _index_array;
    private int[] _adj_array;
    private int[] _weight_array;

    //auxiliar vars

    private int avail_index;

    private void _allocate(int n) {
        _index_array = new int[n];
        // allocate possible number of edges
        _adj_array = new int[Constants.posibleEdgesNum(n)];
        avail_index = 0;
    }

    public GraphAdjArray() {
        _allocate(0);
        avail_index = 0;
    }

    public GraphAdjArray(GraphAdjArray g) {
       
    }

    @Override
    public void clean() {
        _allocate(0);
        avail_index = 0;
    }

    @Override
    public void addVertices(int i) {
        _allocate(i);
        for(int x = 0;x<order();x++){
            _index_array[i] = -1;
        }
    }

    @Override
    public void removeVertex(int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addArc(int i, int j, int w) {

        if(_index_array[i] == -1){

        }

        _adj_array[i] = j;
        _weight_array[i] = w;
    }

    @Override
    public void removeArc(int i, int j) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isArc(int i, int j) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int inDegree(int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int outDegree(int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList neighbors(int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Edge> getAllEdges() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int order() {
        return _index_array.length;
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int MST_TotalWeight() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object findMinEdge(int v, ArrayList<Integer> visited) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getWeight(int i, int j) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Edge> getEdgeNeighbors(int x) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean connected() {
        throw new UnsupportedOperationException("Not supported yet.");
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
    public Object clone() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
