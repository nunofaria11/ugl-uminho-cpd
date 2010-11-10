/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphIO;

import GraphAD.GraphAD;
import GraphADType.GraphADT;
import PerfTest.GenSaveRead;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nuno
 */
public class GraphInput {

    private String file_name;
    private ObjectInputStream in;

    public GraphInput(String file_name) {
        this.file_name = file_name;
        try {
            in = new ObjectInputStream(new FileInputStream(file_name));
        } catch (IOException ioe) {
            System.out.println("Erro opening file '" + file_name + "' for read: " + ioe.toString());
        }
    }

    public void openFile() {
        try {
            in = new ObjectInputStream(new FileInputStream(file_name));
        } catch (IOException ioe) {
            System.out.println("Erro opening file '" + file_name + "' for read: " + ioe.toString());
        }
    }

    public void closeFile() {
        try {
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(GraphInput.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error closing reading file '" + file_name + "': " + ex.toString());
        }
    }

    public GraphAD readGraph() {
        try {
            return (GraphAD) in.readObject();
        } catch (IOException ex) {
            Logger.getLogger(GraphInput.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error reading file '" + file_name + "': " + ex.toString());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GraphInput.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Class doesn't exist in file '" + file_name + "': " + ex.toString());
        }
        return null;
    }

    public GraphADT readGraphADT() {
        try {
            return (GraphADT) in.readObject();
        } catch (IOException ex) {
            Logger.getLogger(GraphInput.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error reading file '" + file_name + "': " + ex.toString());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GraphInput.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Class doesn't exist in file '" + file_name + "': " + ex.toString());
        }
        return null;
    }

    public static void main(String[] args) {
        GraphInput gin = new GraphInput(/*GenSaveRead.file_name*/"file1_ADT.ser");
//        GraphAdjMatrix g = (GraphAdjMatrix) gin.readGraph();
        GraphADT g = gin.readGraphADT();
        System.out.println(g.toString());
    }
}
