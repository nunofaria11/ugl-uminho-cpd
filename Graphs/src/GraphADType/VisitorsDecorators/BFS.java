/*

 /*
  * NOTE: when the usage of the Node class was removed from the
  *       algorithms, BFS was deprecated.
  * Redefine traversal tools to use just T-typed nodes and if necessary
  * use Node class internally
  */


///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package GraphADType.VisitorsDecorators;
//
//import EdgeOriented.Edge;
//import GraphADType.GraphADT;
//import GraphADType.GraphMapAdj;
//import GraphADType.Support.TArithmeticOperations;
//import NodeOriented.Node;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.PriorityQueue;
//
///**
// *
// * @author nuno
// */
//public class BFS<T, Y extends Comparable<Y>> {
//
//    GraphADT<T, Y> g;
//    Visitor<Node<T>, Edge<T, Y>> visitor;
//
//    public BFS(GraphADT<T, Y> g, Visitor<Node<T>, Edge<T, Y>> visitor) {
//        this.g = g;
//        this.visitor = visitor;
//    }
//
//    public Collection<Node<T>> bfs(
//            T s,
//            HashMap<Node<T>, Y> dist,
//            TArithmeticOperations<Y> arith,
//            HashMap<Node<T>, Node<T>> pred) {
//
//        ArrayList<Node<T>> node_list = new ArrayList<Node<T>>();
//
//        ColorDecorator black_decorator = new ColorDecorator(ColorEnum.BLACK);
//        ColorDecorator grey_decorator = new ColorDecorator(ColorEnum.GREY);
//        ColorDecorator white_decorator = new ColorDecorator(ColorEnum.WHITE);
//        //initialization
//        for (Node<T> n : g.getNodes()) {
//            // set initial color of each node to WHITE
//            n.addProperty(white_decorator);
//            dist.put(n, arith.zero_element());
//            pred.put(n, null);
//        }
//        // queue to order nodes by color
//        PriorityQueue<Node<T>> Q = new PriorityQueue<Node<T>>(g.order(), new Comparator<Node<T>>() {
//
//            public int compare(Node<T> o1, Node<T> o2) {
//                ColorDecorator cd1 = (ColorDecorator) o1.getProperty("color");
//                ColorDecorator cd2 = (ColorDecorator) o2.getProperty("color");
//                return cd1.compareTo(cd2);
//            }
//        });
//        s.addProperty(grey_decorator);
//        Q.add(s);
//        while (!Q.isEmpty()) {
//            Node<T> node = Q.poll();
//            if (((ColorDecorator) node.getProperty("color")).color == ColorEnum.GREY) {
//                node_list.add(node);
//            }
//            for (Edge<T, Y> ei : g.getNeighborEdges(node)) {
//                Node<T> target = ei.getNode2();
//                ColorEnum target_color = ((ColorDecorator) target.getProperty("color")).color;
//                if (target_color == ColorEnum.WHITE) {
//                    target.addProperty(grey_decorator);
//                    Q.add(target); // enqueue white neighbors (now grey neighbors) of current node
//                    dist.put(
//                            node,
//                            arith.Add(dist.get(node), ei.getEdge_data()));
//                    pred.put(target, node);
//                }
//            }
//            node.addProperty(black_decorator);
//        }
//        return node_list;
//    }
//
//    public GraphADT<T, Y> bfs_with_visitor(
//            Node<T> start_node,
//            HashMap<Node<T>, Y> dist,
//            TArithmeticOperations<Y> arith,
//            HashMap<Node<T>, Node<T>> pred) {
//
//        GraphADT bfs_g = g.clone();
//        bfs_g.clean();
////        ArrayList<Node<T>> node_list = new ArrayList<Node<T>>();
//        //initialization
//        for (Node n : g.getNodes()) {
//            // initialize with visitor according to the BFS initialization "contract"
//            visitor.initializes(n);
//            dist.put(n, arith.zero_element());
//            pred.put(n, null);
//        }
//        PriorityQueue<Node<T>> Q = new PriorityQueue<Node<T>>(g.order(), new Comparator<Node<T>>() {
//
//            public int compare(Node<T> o1, Node<T> o2) {
//                ColorDecorator cd1 = (ColorDecorator) o1.getProperty("color");
//                ColorDecorator cd2 = (ColorDecorator) o2.getProperty("color");
//                return cd1.compareTo(cd2);
//            }
//        });
//        visitor.start(start_node);
//        Q.add(start_node);
//        while (!Q.isEmpty()) {
//            Node<T> node = Q.poll();
//            visitor.discover(node);
//            if (((ColorDecorator) node.getProperty("color")).color == ColorEnum.GREY) {
////                node_list.add(node);
//                bfs_g.addNode(node);
//            }
//            for (Edge<T, Y> ei : g.getNeighborEdges(node)) {
//                if (visitor.process(ei)) {
//                    Node<T> target = ei.getNode2();
////                    visitor.discover(target);
//
//                    target.addProperty(new ColorDecorator(ColorEnum.GREY));
//                    Q.add(target); // enqueue white neighbors (now grey neighbors) of current node
//                    dist.put(
//                            node,
//                            arith.Add(dist.get(node), ei.getEdge_data()));
//                    pred.put(target, node);
//                    bfs_g.addEdge(node, target, (Y) ei.getEdge_data());
//                }
//            }
//            visitor.finish(node);
//        }
//
//        return bfs_g;
//
//    }
//
//    public static void main(String[] args) {
//
//
//        GraphMapAdj<String, Double> g = new GraphMapAdj<String, Double>(7);
//        // create nodes...
////        Node<String> n0 = new Node<String>("A");
////        Node<String> n1 = new Node<String>("B");
////        Node<String> n2 = new Node<String>("C");
////        Node<String> n3 = new Node<String>("D");
////        Node<String> n4 = new Node<String>("E");
////        Node<String> n5 = new Node<String>("F");
////        Node<String> n6 = new Node<String>("G");
//        String n0 = "A";
//        String n1 = "B";
//        String n2 = "C";
//        String n3 = "D";
//        String n4 = "E";
//        String n5 = "F";
//        String n6 = "G";
//        // add nodes to graph...
//        g.addNode(n0);
//        g.addNode(n1);
//        g.addNode(n2);
//        g.addNode(n3);
//        g.addNode(n4);
//        g.addNode(n5);
//        g.addNode(n6);
//        // link nodes together...
//        g.addEdge(n0, n1, 7.1);
//        g.addEdge(n0, n3, 5.1);
//        g.addEdge(n1, n2, 8.1);
//        g.addEdge(n1, n3, 9.1);
//        g.addEdge(n1, n4, 7.1);
//        g.addEdge(n2, n4, 5.1);
//        g.addEdge(n3, n4, 15.1);
//        g.addEdge(n3, n5, 6.1);
//        g.addEdge(n4, n5, 8.1);
//        g.addEdge(n4, n6, 9.1);
//        g.addEdge(n5, n6, 11.1);
//
//        System.out.println(g);
//
//        // visitor
//        BFS_Visitor<Node<String>, Edge<String, Double>> bfs_visiter = new BFS_Visitor<Node<String>, Edge<String, Double>>();
//
//        BFS<String, Double> bfs = new BFS<String, Double>(g, bfs_visiter);
//
//
//        HashMap<Node<String>, Node<String>> predecessors = new HashMap<Node<String>, Node<String>>();
//        HashMap<Node<String>, Double> distances = new HashMap<Node<String>, Double>();
//
//        TArithmeticOperations<Double> arith = new TArithmeticOperations<Double>() {
//
//            public Double Add(Double a, Double b) {
//                return a + b;
//            }
//
//            public Double Cat(Double a, Double b) {
//                throw new UnsupportedOperationException("Not supported yet.");
//            }
//
//            public Double zero_element() {
//                return 0.0;
//            }
//        };
//
//        System.out.println(bfs.bfs(n0, distances, arith, predecessors));
//
//        System.out.println("Start: " + n0);
//        System.out.println("Dists: " + distances);
//        System.out.println("Preds: " + predecessors);
//
//        System.out.println("===========================");
//
//        predecessors = new HashMap<Node<String>, Node<String>>();
//        distances = new HashMap<Node<String>, Double>();
//        System.out.println(bfs.bfs_with_visitor(n0, distances, arith, predecessors));
//
//        System.out.println("Start: " + n0);
//        System.out.println("Dists: " + distances);
//        System.out.println("Preds: " + predecessors);
//
//        // **** maybe the colors arent being well initialized at WHITE.
//        // implement setNode? or setNodeProperties? to encapsulate the prop in the graph
//        // instead of the Node that is extracted from getNodes?
//
//
//    }
//}

