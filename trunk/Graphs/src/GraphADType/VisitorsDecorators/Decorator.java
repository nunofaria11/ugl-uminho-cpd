/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType.VisitorsDecorators;

/**
 *
 * @author nuno
 */
public abstract class Decorator {

    String dec_id;

    public String getDec_id() {
        return dec_id;
    }

    public void setDec_id(String dec_id) {
        this.dec_id = dec_id;
    }
}
