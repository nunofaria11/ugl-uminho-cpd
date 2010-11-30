/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphADType;

import GraphADType.Support.TArithmeticOperations;

/**
 *
 * @author nuno
 */

public class ArithmeticExample<T extends Number> {

    public T Add(TArithmeticOperations<T> ops, T a, T b) {
        return ops.Add(a, b);
    }

    public static void main(String[] args){
        ArithmeticExample<Double> a = new ArithmeticExample<Double>();

        TArithmeticOperations<Double> ar = new TArithmeticOperations<Double>() {
            public Double Add(Double a, Double b) {
                return a+b;
            }

            public Double Cat(Double a, Double b) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public Double zero_element() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };

        System.out.println(a.Add(ar, 1.2, 2.1));
    }

}