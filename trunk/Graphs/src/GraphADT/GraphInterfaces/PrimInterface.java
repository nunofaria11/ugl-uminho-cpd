/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADT.GraphInterfaces;

import GraphADT.GraphADT;
import java.util.ArrayList;
import java.util.Stack;
import java.util.ArrayList;

/**
 *
 * @author nuno
 */
public interface PrimInterface {

    GraphADT MST_Prim();

    void addVisited(int v);

    void changeActive(int index, int node);

    ArrayList<Integer> getActive();

    ArrayList<Integer> getVisited();

    void reset();

    void setActive(ArrayList<Integer> active);

    void setVisited(ArrayList<Integer> visited);
}
