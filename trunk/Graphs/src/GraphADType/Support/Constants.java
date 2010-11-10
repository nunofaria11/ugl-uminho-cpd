/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType.Support;

import NodeOriented.Node;
import java.util.Collection;
import java.util.Random;

/**
 *
 * @author nuno
 */
public class Constants {

    public static TYRandomizer<Integer, Double> randTYIntDouble = new TYRandomizer<Integer, Double>() {

        public Double random(Double lower, Double upper) {
            Random r = new Random(System.currentTimeMillis());
            return r.nextDouble() * (upper - lower) + lower;
        }

        public Collection<Node<Integer>> randomizeNodes(int n, Collection<Integer> alphabet) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };
}
