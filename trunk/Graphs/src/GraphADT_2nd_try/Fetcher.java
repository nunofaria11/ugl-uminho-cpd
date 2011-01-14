/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphADT_2nd_try;

/**
 *
 * @author nuno
 */
public class Fetcher implements Runnable {

    UndirectedGraph ug;

    public Fetcher(UndirectedGraph ug) {
        this.ug = ug;
        new Thread(this).start();
    }

    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
