/**
 * author Prit Thakkar
 */


package PVT170000;

import rbk.Graph;
import rbk.Graph.Vertex;
import rbk.Graph.Edge;
import rbk.Graph.GraphAlgorithm;
import rbk.Graph.Factory;
import rbk.Graph.Timer;

import java.io.File;
import java.util.*;

public class DFS extends GraphAlgorithm<DFS.DFSVertex> {

    LinkedList<Vertex> finalList;
    int topNum;
    private static boolean isCyclic;
//    Boolean recStack[];
    public static class DFSVertex implements Factory {
        int cno;

        boolean seen;
        Vertex parent;
        int top;
        boolean isInRecursionStack;

        public DFSVertex(Vertex u) {
            this.seen = false;
            this.parent = null;
            this.top = 0;
            this.cno = 0;
            isInRecursionStack = false;
        }
        public DFSVertex make(Vertex u) { return new DFSVertex(u); }
    }

    public DFS(Graph g) {
        super(g, new DFSVertex(null));
    }

    public static DFS depthFirstSearch(Graph g) {
        DFS d = new DFS(g);
        d.dfs();
        return d;
    }
    // for topologicalOrder1
    public void dfs(){

        finalList = new LinkedList<Vertex>();
        topNum = g.size();
        isCyclic = false;

        for(Vertex u: g){
            get(u).seen = false;
            get(u).parent = null;
            get(u).top = 0;
            get(u).isInRecursionStack = false;
        }

        for(Vertex u : g){
            if(!get(u).seen){
                dfsVisit(u);
            }
        }

    }

    private void dfsVisit(Vertex u) {

        get(u).seen = true;
        get(u).isInRecursionStack = true;

        for(Edge e: g.incident(u)){
            Vertex v = e.otherEnd(u);

            if(!isCyclic){
                if(get(v).isInRecursionStack) isCyclic = true;
            }

            if(!get(v).seen){
                get(v).parent = u;
                dfsVisit(v);
            }
        }

        get(u).top = topNum--;
        finalList.addFirst(u);
//        recStack[u.getIndex()] = false;
        get(u).isInRecursionStack = false;
    }

    // Member function to find topological order
    public List<Vertex> topologicalOrder1() {
        if(!g.isDirected()) return null;
        this.dfs();
        return isCyclic?null : finalList;
    }

    // Find the number of connected components of the graph g by running dfs.
    // Enter the component number of each vertex u in u.cno.
    // Note that the graph g is available as a class field via GraphAlgorithm.
    public int connectedComponents() {
        return 0;
    }

    // After running the onnected components algorithm, the component no of each vertex can be queried.
    public int cno(Vertex u) {
        return get(u).cno;
    }

    // Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder1(Graph g) {
        DFS d = new DFS(g);
        return d.topologicalOrder1();
    }

    // Find topological oder of a DAG using the second algorithm. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder2(Graph g) {
        return null;
    }

    public static void main(String[] args) throws Exception {
        //String string = "7 8   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 1 7   6 7 1   7 6 1 0";
        //String string = "5 4  1 2 1   2 5 1   5 4 1   3 5 1 0";
        //String string = "9 8  1 3 1  3 7 1  3 9 1  1 9 1  9 7 1  7 4 1  2 6 1  6 8 1 0";
        String string = "10 12  1 3 1  3 2 1  2 4 1  4 7 1  1 8 1  8 5 1  8 2 1  5 4 1  6 8 1  6 10 1  5 10 1  10 9 1 0";
        Scanner in;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);

        // Read graph from input
        Graph g = Graph.readGraph(in, true);
        g.printGraph(false);

        DFS d = new DFS(g);
//        int numcc = d.connectedComponents();
//        System.out.println("Number of components: " + numcc + "\nu\tcno");
//        for(Vertex u: g) {
//            System.out.println(u + "\t" + d.cno(u));
//        }

//
        List<Vertex> ll = d.topologicalOrder1();
        //System.out.println(ll);
        //System.out.println(d.dfs());
//        for (Vertex v: g){
//            System.out.println(d.get(v).top);
//        }
        if(ll!=null){
            for(Vertex u: ll){
                System.out.print(u.getName()+" ");
            }
        }
        else{
            System.out.println(ll);
        }

    }
}
