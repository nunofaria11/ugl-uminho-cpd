/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphIO;

import EdgeOriented.Edge;
import GraphAD.GraphAD;
import GraphADType.GraphADT;
import GraphADType.GraphMapAdj;
import NodeOriented.Node;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

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

    public void GraphtoDot(GraphADT g) throws IOException {

        FileWriter file_w = new FileWriter(file_name);
        PrintWriter print_w = new PrintWriter(file_w);

        print_w.write("graph g{\n");


        // write each node and edge as a dot format
        ArrayList<Edge> edges = (ArrayList<Edge>) g.getEdges();
        for (Edge edge : edges) {
            print_w.write("\t" + edge.getNode1().toString() + " -- " + edge.getNode2().toString());
            print_w.write(" [label=\"" + edge.getEdge_data() + "\", len=" + edge.getEdge_data() + "];\n");
        }
        //
        print_w.write("}\n");
        file_w.close();
    }

    public static void main(String[] args) throws IOException {
        GraphMapAdj<String, Integer> g = new GraphMapAdj<String, Integer>();
        // create nodes...
        //        Node<String> n0 = new Node<String>("A");
//        Node<String> n1 = new Node<String>("B");
//        Node<String> n2 = new Node<String>("C");
//        Node<String> n3 = new Node<String>("D");
//        Node<String> n4 = new Node<String>("E");
//        Node<String> n5 = new Node<String>("F");
//        Node<String> n6 = new Node<String>("G");
        String n0 = "A";
        String n1 = "B";
        String n2 = "C";
        String n3 = "D";
        String n4 = "E";
        String n5 = "F";
        String n6 = "G";
        // add nodes to graph...
        g.addNode(n0);
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        g.addNode(n4);
        g.addNode(n5);
        g.addNode(n6);
        // link nodes together...
        g.addEdge(n0, n1, 7);
        g.addEdge(n0, n3, 5);
        g.addEdge(n1, n2, 8);
        g.addEdge(n1, n3, 9);
        g.addEdge(n1, n4, 7);
        g.addEdge(n2, n4, 5);
        g.addEdge(n3, n4, 15);
        g.addEdge(n3, n5, 6);
        g.addEdge(n4, n5, 8);
        g.addEdge(n4, n6, 9);
        g.addEdge(n5, n6, 11);

        GraphOutput gdot = new GraphOutput("g.dot");
        gdot.GraphtoDot(g);

//        System.out.println(g.getUnduplicatedEdges());


    }
}
