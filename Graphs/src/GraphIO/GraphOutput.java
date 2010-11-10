/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphIO;

import GraphAD.GraphAD;
import GraphADType.GraphADT;
import GraphADType.GraphArraySucc;
import GraphADType.GraphMapAdj;
import NodeOriented.Node;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 * @author nuno
 */
public class GraphOutput {

    private String file_name;
    private ObjectOutputStream out;

    public GraphOutput(String file_name, ObjectOutputStream out) {
        this.file_name = file_name;
        this.out = out;
    }

    public GraphOutput(String file_name) {
        this.file_name = file_name;
        try {
            this.out = new ObjectOutputStream(new FileOutputStream(this.file_name));
        } catch (IOException ioe) {
            System.out.println("Error creating file '" + this.file_name + "': " + ioe.toString());
        }
    }

    public void saveGraph(GraphAD g) throws IOException {
        out.writeObject(g);
    }

    public void saveGraphADT(GraphADT g) throws IOException {
        out.writeObject(g);
    }

    public void closeFile() throws IOException {
        out.close();
    }

    public void openFile() {
        try {
            this.out = new ObjectOutputStream(new FileOutputStream(this.file_name));
        } catch (IOException ioe) {
            System.out.println("Error opening file '" + this.file_name + "': " + ioe.toString());
        }
    }

    public static void main(String[] args) throws IOException{
//        GraphAdjMatrix g = new GraphAdjMatrix();
//        GraphAdjLists g = new GraphAdjLists();
//        g.addVertices(7);
//        g.addEdge(0, 1, 7);
//        g.addEdge(0, 3, 5);
//        g.addEdge(1, 2, 8);
//        g.addEdge(1, 3, 9);
//        g.addEdge(1, 4, 7);
//        g.addEdge(2, 4, 5);
//        g.addEdge(3, 4, 15);
//        g.addEdge(3, 5, 6);
//        g.addEdge(4, 5, 8);
//        g.addEdge(4, 6, 9);
//        g.addEdge(5, 6, 11);

        GraphArraySucc<String, Double> g = new GraphArraySucc<String, Double>();
        g.addNodes(7);

//        Node<Integer> n0 = new Node<Integer>(0);
//        Node<Integer> n1 = new Node<Integer>(1);
//        Node<Integer> n2 = new Node<Integer>(2);
//        Node<Integer> n3 = new Node<Integer>(3);
//        Node<Integer> n4 = new Node<Integer>(4);
//        Node<Integer> n5 = new Node<Integer>(5);
//        Node<Integer> n6 = new Node<Integer>(6);
        Node<String> n0 = new Node<String>("A");
        Node<String> n1 = new Node<String>("B");
        Node<String> n2 = new Node<String>("C");
        Node<String> n3 = new Node<String>("D");
        Node<String> n4 = new Node<String>("E");
        Node<String> n5 = new Node<String>("F");
        Node<String> n6 = new Node<String>("G");


        g.addEdge(n0, n1, 7.1);
        g.addEdge(n0, n3, 5.2);
        g.addEdge(n1, n2, 8.3);
        g.addEdge(n1, n3, 9.4);
        g.addEdge(n1, n4, 7.5);
        g.addEdge(n2, n4, 5.6);
        g.addEdge(n3, n4, 15.7);
        g.addEdge(n3, n5, 6.8);
        g.addEdge(n4, n5, 8.9);
        g.addEdge(n4, n6, 9.10);
        g.addEdge(n5, n6, 11.11);


        GraphOutput gout = new GraphOutput("file1_ADT.ser");
        gout.saveGraphADT(g);
        gout.closeFile();

    }

}
