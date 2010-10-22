/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphIO;

import GraphAD.Representations.GraphAdjLists;
import GraphAD.GraphAD;
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
        GraphAdjLists g = new GraphAdjLists();
        g.addVertices(7);
        g.addEdge(0, 1, 7);
        g.addEdge(0, 3, 5);
        g.addEdge(1, 2, 8);
        g.addEdge(1, 3, 9);
        g.addEdge(1, 4, 7);
        g.addEdge(2, 4, 5);
        g.addEdge(3, 4, 15);
        g.addEdge(3, 5, 6);
        g.addEdge(4, 5, 8);
        g.addEdge(4, 6, 9);
        g.addEdge(5, 6, 11);

        GraphOutput gout = new GraphOutput("file2.ser");
        gout.saveGraph(g);
        gout.closeFile();

    }

}
