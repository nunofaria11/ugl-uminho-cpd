/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADT_2nd_try;

/**
 *
 * @author nuno
 */
public class EdgeJHandler {

    private int count;

    public EdgeJHandler() {
        this.count = 0;
    }

    public int getNewId(){
        return count++;
    }
}