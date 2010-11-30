/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JungTest;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.UndirectedGraph;

/**
 *
 * @author nuno
 */
public class test1 {

    int edge_count = 0;

    public class myNode {

        int id;

        public myNode(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "{" + "id=" + id + '}';
        }
    }

    public class myLink {

        int id;
        int weight;

        public myLink(int weight) {
            this.id = edge_count++;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "{" + "[" + id + "] weight=" + weight + '}';
        }
    }

    public void create_test() {
        SparseMultigraph<String, myLink> g = new SparseMultigraph<String, myLink>();

        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addVertex("D");

        g.addEdge(new myLink(3), "A", "C");
        g.addEdge(new myLink(3), "A", "B");
        g.addEdge(new myLink(5), "B", "D");
        g.addEdge(new myLink(1), "B", "C");

        System.out.println(g.toString());

    }
    
    public static void main(String[] args){
        test1 t = new test1();
        t.create_test();
    }


}
