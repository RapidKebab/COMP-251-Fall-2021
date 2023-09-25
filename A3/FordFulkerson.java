import java.util.*;

public class FordFulkerson {

	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> path = new ArrayList<Integer>();//https://stackoverflow.com/questions/27534092/finding-a-single-path-in-a-graph-using-dfs
		ArrayList<Integer> pathNodes = new ArrayList<Integer>();//this will serve as our "to evaluate"
		//https://www.geeksforgeeks.org/print-the-path-between-any-two-nodes-of-a-tree-dfs/

		//I hate this so much
		ArrayList<Integer> visited = new ArrayList<Integer>();

		pathNodes.add(source);
		/* YOUR CODE GOES HERE */
		while (!pathNodes.isEmpty()){
			int lastNode = pathNodes.get((pathNodes.size()-1));
			visited.add(lastNode);
			pathNodes.remove((pathNodes.size()-1));

			while(!path.isEmpty()){//remove invalid edges
				Edge e = graph.getEdge(path.get(path.size()-1),lastNode);
				if(e == null || e.weight <1)
					path.remove(path.size()-1);
				else
					break;
			}

			path.add(lastNode);

			for(Edge e: graph.getEdges()){
				if((!visited.contains(e.nodes[1])) && (e.nodes[0] == lastNode && e.weight == 0)){
					if(e.nodes[1] == destination){
						path.add(e.nodes[1]);
						pathNodes.clear();
					}
					else{
						pathNodes.add(e.nodes[1]);
					}
				}
			}
		}
		return path;
	}



	public static String fordfulkerson(WGraph graph){
		String answer = "";
		int maxFlow = 0;
		WGraph G_f = new WGraph(graph);        // residual Graph
		WGraph capacity = new WGraph(graph);

		ArrayList<Integer> path = pathDFS(G_f.getSource(), G_f.getDestination(), G_f);

		while (path.size()>0){//loop through the whole path
			int bottleNeck = Integer.MAX_VALUE;
			for(int i = 0;i<path.size()-1;i++){
				int c = G_f.getEdge(path.get(i), path.get(i+1)).weight;
				if(c<bottleNeck)
					bottleNeck=c;
			}
			for(int i = 0;i<path.size()-1;i++){//construct the residual
				Edge e = G_f.getEdge(i,i+1);
				int cap = capacity.getEdge(i,i+1).weight;
				if(e.weight<=cap){
					G_f.setEdge(i,i+1,cap-e.weight);
				}
				else if(e.weight>0){
					Edge backEdge = G_f.getEdge(i+1,i);
					if(backEdge== null){
						G_f.addEdge( new Edge(i+1,i,e.weight));
					}
					else{
						G_f.setEdge(i+1,i,e.weight);
					}
				}
			}
			path = pathDFS(G_f.getSource(), G_f.getDestination(), G_f);
			maxFlow +=bottleNeck;
		}

		/* YOUR CODE GOES HERE */

		answer += maxFlow + "\n" + graph.toString();	
		return answer;
	}
	

	 public static void main(String[] args){
		 String file = args[0];
		 WGraph g = new WGraph(file);
		 System.out.println(fordfulkerson(g));
	 }
}

