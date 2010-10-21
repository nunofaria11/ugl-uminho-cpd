/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

/**
 *
 * @author nuno
 */
public final class Constants {

    public static final int MAX_WEIGHT = 20;
    public static final int MIN_WEIGHT = 1;
    public static final int MAX_VERTEX_NUM = 10000;
    public static final int MAX_EDGE_NUM = posibleEdgesNum(MAX_VERTEX_NUM);

    public static int posibleEdgesNum(int v) {
        return (v * (v - 1)) / 2;
    }
}
