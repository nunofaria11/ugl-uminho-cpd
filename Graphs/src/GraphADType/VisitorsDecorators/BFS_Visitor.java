/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType.VisitorsDecorators;

import EdgeOriented.EdgeEO;
import NodeOriented.Node;

/**
 *
 * @author nuno
 */
public final class BFS_Visitor<N extends Node, E extends EdgeEO> implements Visitor<N, E> {

    public void initializes(N n) {
        n.addProperty(new ColorDecorator());
    }

    public void start(N n) {
        n.addProperty(new ColorDecorator(ColorEnum.GREY));
    }

    public void discover(N n) {
        n.addProperty(new ColorDecorator(ColorEnum.GREY));
    }

    /**
     * Checks if the destination of the edge is "followable" in the BFS algorithm
     * @param e Edge to process
     * @return
     */
    public boolean process(E e) {
        Node target = e.getNode2();
        ColorEnum target_color = ((ColorDecorator) target.getProperty("color")).color;
        if (target_color == ColorEnum.WHITE) {
            return true;
        }
        return false;
    }

    /**
     * When the BFS algorithm finishes visiting a node
     * @param n
     */
    public void finish(N n) {
        n.addProperty(new ColorDecorator(ColorEnum.BLACK));
    }
}
