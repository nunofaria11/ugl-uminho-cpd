/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType.VisitorsDecorators;

/**
 *
 * @author nuno
 */
public class DistanceDecorator<X extends Number> extends Decorator {

    X distance;

    public DistanceDecorator() {
        super.dec_id = "distance";
    }

    public DistanceDecorator(X distance) {
        super.dec_id = "distance";
        this.distance = distance;
    }
}
