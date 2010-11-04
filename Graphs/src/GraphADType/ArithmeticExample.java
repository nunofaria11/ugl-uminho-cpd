/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphADType;

/**
 *
 * @author nuno
 */

public class ArithmeticExample<T extends Number> {

    public T Add(YArithmeticOperations<T> ops, T a, T b) {
        return ops.Add(a, b);
    }

    public static void main(String[] args){
        ArithmeticExample<Double> a = new ArithmeticExample<Double>();

        YArithmeticOperations<Double> ar = new YArithmeticOperations<Double>() {
            public Double Add(Double a, Double b) {
                return a+b;
            }
        };

        System.out.println(a.Add(ar, 1.2, 2.1));
    }

}