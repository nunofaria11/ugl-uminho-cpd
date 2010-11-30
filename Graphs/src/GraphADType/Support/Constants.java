/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType.Support;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nuno
 */
public class Constants {

    public static int possibleEdgesNum(int v) {
        return (v * (v - 1)) / 2;
    }
    public static YRandomizer<Double> randDouble = new YRandomizer<Double>() {

        public Double random(Double lower, Double upper) {
            Random r = new Random(System.currentTimeMillis());
            return r.nextDouble() * (upper - lower) + lower;
        }
    };

    public static YRandomizer<Integer> randInteger = new YRandomizer<Integer>() {

        public Integer random(Integer lower, Integer upper) {
            Random r = new Random(System.currentTimeMillis());
            return r.nextInt(upper - lower) + lower;
        }
    };

    public static YRandomizer<Integer> sleepIntRand = new YRandomizer<Integer>() {

            public Integer random(Integer lower, Integer upper) {
                Random r = new Random(System.currentTimeMillis());
                try {
                    Thread.sleep(1, 2);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GenSaveReadADT.class.getName()).log(Level.SEVERE, null, ex);
                }
                return r.nextInt(upper - lower) + lower;
            }
        };

    public static TArithmeticOperations<String> strArith = new TArithmeticOperations<String>() {

        public String Add(String a, String b) {
            return a + b;
        }

        public String Cat(String a, String b) {
            return a + b;
        }

        public String zero_element() {
            return "";
        }
    };

    public static TArithmeticOperations<Integer> intArith = new TArithmeticOperations<Integer>() {

        public Integer Add(Integer a, Integer b) {
            return a+b;
        }

        public Integer Cat(Integer a, Integer b) {
            return Integer.parseInt(a.toString()+b.toString());
        }

        public Integer zero_element() {
            return 0;
        }


    };
}
