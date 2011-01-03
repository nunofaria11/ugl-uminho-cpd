/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADT_2nd_try;

/**
 *
 * @author nuno
 */
public interface Visitable<V, E> {

    /**
     * Marks a vertex as visited
     * @param vertex
     * @return true if the vertex was marked as visited with success, if it was already visited returns false.
     */
    boolean visitVertex(V vertex);

    /**
     * Marks an edge as visited
     * @param edge
     * @return true if the edge was marked as visited with success, if it was already visited returns false.
     */
    boolean visitEdge(E edge);

    /**
     * Checks if a vertex was visited
     * @param vertex_edge
     * @return
     */
    boolean visitedVertex(V vertex);

    /**
     * Checks if an edge was visited
     * @param edge
     * @return
     */
    boolean visitedEdge(E edge);
}
