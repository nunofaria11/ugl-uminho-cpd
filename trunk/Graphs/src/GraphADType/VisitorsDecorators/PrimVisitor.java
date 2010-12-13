///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package GraphADType.VisitorsDecorators;
//
//import EdgeOriented.Edge;
//import GraphADType.GraphADT;
//import GraphADType.GraphMapAdj;
//import GraphADType.Support.Constants;
//import NodeOriented.Node;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.PriorityQueue;
//
///**
// * Prim MST algorithm using the visitor and decorator concept.
// * The visited nodes control will be made by coloring them.
// * @author nuno
// */
//public final class PrimVisitor<N extends Node, E extends Edge> implements Visitor<N, E> {
//
//    private GraphADT g;
//    private PriorityQueue<E> Q;
//    private ArrayList<E> nbors;
//    private ArrayList<E> edges2add;
//
//    public PrimVisitor(GraphADT g) {
//        this.g = g;
//        this.Q = new PriorityQueue<E>();
//    }
//
//    public void initializes(N n) {
//        n.addProperty(new ColorDecorator(ColorEnum.WHITE));
//    }
//
//    public void start(N n) {
//    }
//
//    public void discover(N n) {
//        n.addProperty(new ColorDecorator(ColorEnum.GREY));
//        nbors = (ArrayList<E>) g.getNeighborEdges(n);
//        edges2add = new ArrayList<E>();
//
//    }
//
//    public boolean process(E e) {
//        ColorDecorator target_color = (ColorDecorator) e.getNode2().getProperty("color");
//        // if targer was not visited yet returns true
//        boolean process_result = target_color.color.equals(ColorEnum.WHITE);
//        if(process_result){
//            edges2add.add(e);
//        }
//        return process_result;
//    }
//
//    public void finish(N n) {
//        System.out.println("edges2add: "+edges2add);
//        Q.addAll(edges2add);
//        E minEdge = Q.poll();
//        g.addEdge(minEdge.getNode1(), minEdge.getNode2(), minEdge.getEdge_data());
//    }
//
//    public static void main(String[] args){
//        GraphMapAdj<String, Integer> g = new GraphMapAdj<String, Integer>(7);
//        // create nodes...
//        Node<String> n0 = new Node<String>("A");
//        Node<String> n1 = new Node<String>("B");
//        Node<String> n2 = new Node<String>("C");
//        Node<String> n3 = new Node<String>("D");
//        Node<String> n4 = new Node<String>("E");
//        Node<String> n5 = new Node<String>("F");
//        Node<String> n6 = new Node<String>("G");
//        // add nodes to graph...
//        g.addNode(n0);
//        g.addNode(n1);
//        g.addNode(n2);
//        g.addNode(n3);
//        g.addNode(n4);
//        g.addNode(n5);
//        g.addNode(n6);
//        // link nodes together...
//        g.addEdge(n0, n1, 7);
//        g.addEdge(n0, n3, 5);
//        g.addEdge(n1, n2, 8);
//        g.addEdge(n1, n3, 9);
//        g.addEdge(n1, n4, 7);
//        g.addEdge(n2, n4, 5);
//        g.addEdge(n3, n4, 15);
//        g.addEdge(n3, n5, 6);
//        g.addEdge(n4, n5, 8);
//        g.addEdge(n4, n6, 9);
//        g.addEdge(n5, n6, 11);
//
//        PrimVisitor<Node<String>, Edge<String, Integer>> prim_visitor =
//                new PrimVisitor<Node<String>, Edge<String, Integer>>(g);
//
//        BFS<String, Integer> traversal = new BFS<String, Integer>(g, prim_visitor);
//
//        GraphADT mst = traversal.bfs_with_visitor(
//                n0,
//                new HashMap<Node<String>, Integer>(),
//                Constants.intArith,
//                new HashMap<Node<String>, Node<String>>());
//        System.out.println(mst);
//    }
//}
