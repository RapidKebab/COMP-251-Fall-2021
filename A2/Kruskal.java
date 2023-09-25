import java.util.*;

public class Kruskal{

    public static WGraph kruskal(WGraph g){
        WGraph MST = new WGraph();
        ArrayList<Edge> edgesSorted = g.listOfEdgesSorted();
        DisjointSets ds = new DisjointSets(g.getNbNodes());

        for (Edge e : edgesSorted)
        {
            if(IsSafe(ds,e))
            {
                MST.addEdge(e);
                ds.union(e.nodes[0],e.nodes[1]);
            }
        }
        return MST;
    }

    public static Boolean IsSafe(DisjointSets p, Edge e){
        if (p.find(e.nodes[0]) != p.find(e.nodes[1])) {
            return true;
        }
        else{return false;}
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        System.out.println(t);

   } 
}
