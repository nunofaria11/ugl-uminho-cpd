/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType.Support;

/**
 *
 * @author nuno
 */
public interface TArithmeticOperations<T> {
    T Add(T a, T b); // ...

    T Cat(T a, T b);
    /**
     * Returns the empty element for type T.
     * eg.:
     *  Integer:    -1
     *  String:     ""
     * 
     * @return
     */
    T zero_element();
}

