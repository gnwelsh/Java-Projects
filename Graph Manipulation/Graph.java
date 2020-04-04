import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Graph 
{
	static Scanner kb = new Scanner(System.in);
	
	private int[][] adjacencyMatrix;//contains edges of nodes coresponding to indexes in the 2D array
	
	Graph(String location) throws FileNotFoundException
	{
		// Open the file.
	      File graph = new File(location);//contains graph information
	      Scanner inputGraph = new Scanner(graph);//Scanner for graph file
	      
	      int numberOfNodes=inputGraph.nextInt();//total nodes in the graph

	      adjacencyMatrix = new int[numberOfNodes][numberOfNodes];
	      
	    //fill adjacency matrix with -1
	      for (int i=0; i <numberOfNodes;i++)
	    	  for (int j=0; j <numberOfNodes;j++)
	    		  adjacencyMatrix[i][j]=-1;
	      
	    //fill adjacency matrix with nodes
	      
	      for(int i=0; i<numberOfNodes;i++)
	      {
	    	  int edges = inputGraph.nextInt();
	    	  
	    	  for(int j=0;j<edges;j++)
	    	  {
	    		  int node=inputGraph.nextInt();
	    		  int weight=inputGraph.nextInt();
	    		  adjacencyMatrix[i][node]=weight;
	    	  }
	      }

	      // Close the file.
	      inputGraph.close();
	}
	
	public int[][] getMatrix()
	{
		return adjacencyMatrix;
	}
	
	/**
	 * takes the array and prints the adjacencyMatrix
	 * @param array
	 */
	public void print(int [][] array)
	{
		for(int i=0;i<array.length;i++)
		{
		for(int j=0;j<array.length;j++)
			System.out.print(array[i][j]+" ");		
		System.out.println();
		}
	}
	
	/**
	 * takes the array and prints the graph's original format
	 * @param array
	 */
	
	public void printOut(int [][] array)
	{
		System.out.println("\n"+array.length);
		int[] totalNodes = new int[array.length];
		for(int i=0;i<array.length;i++)
		{
			for(int j=0;j<array.length;j++)
				if(array[i][j]!=-1&&i!=j)
					totalNodes[i]++;		
		}
		for(int i=0;i<array.length;i++)
		{
			System.out.print(totalNodes[i]+" ");
			for(int j=0;j<array.length;j++)	
			{
				if(array[i][j]!=-1&&i!=j)
					System.out.print(j+" "+array[i][j]+" ");	
			}
			System.out.println();
		}
	}
	
	/**
	 * determine whether or not the graph is connected.
	 * useing a depth first search to treverse the graph
	 * if there are any unmarked nodes at the end the graph isn't conected
	 */
	
	public boolean isConnected()
	{
		
	    boolean[] isVisited = new boolean[adjacencyMatrix.length]; //creates a boolean to check if nodes have been visited yet

	    DFS(0,isVisited);//Depth First Search(node first starting at, boolean[] to check if nodes have been visited

		int k=0;
		for(int i=0;i<isVisited.length;i++)	//this checks to see if all nodes have been visited
			if(isVisited[i]==true)
				k++;
		
		
		
		if(k==isVisited.length)	//all nodes have been visited, return true
			return true;
		else					//all nodes have NOT been visited, return false
			return false;
		
	}
	
	/**
	 * find a minimum spanning tree for the graph
	 */
	public void minimumSpanningTree()
	   {int [][] temp= new int[adjacencyMatrix.length][adjacencyMatrix.length];	//creates a double array to create a second adjacencyMatrix with different values
		
		temp=primAlgorithm(temp);
		this.printOut(temp);
		
	   }
	/**
	 * find the shortest paths from a node to all other nodes in the graph
	 * Using Dijkstra's Algorithm on given node
	 */
	public void shortestPath()
	   {
		System.out.print("From which node would you like to find the shortest paths (0 - " +(adjacencyMatrix.length-1)+ "): ");
		int givenNode=kb.nextInt();
		
		if(givenNode<0||givenNode>adjacencyMatrix.length-1)
		{
			System.out.print("Error: No node with a value of "+givenNode+" exists in this graph.");
			return;
		}
		
		int[] temp= new int[adjacencyMatrix.length];
		System.out.println("Node: "+givenNode);
		temp=dijkstraAlgorithm(givenNode);

		for(int i=0;i<temp.length;i++)
		{
			if(temp[i]==-1)
				System.out.println(i+":  Infinity");
			else
				System.out.println(i+":  "+temp[i]);
		}

	   }
	/**
	 * determine whether or not the graph is metric
	 */
	public int isMetric()
	   {
		
		int posobilities=0;//determins outcome
		int count=0;
		int result=((adjacencyMatrix.length*adjacencyMatrix.length)-adjacencyMatrix.length);
		boolean connected=false;
		boolean triangle = false;
		
		//connected check
		for (int i=0; i <adjacencyMatrix.length;i++)
		{
			for (int j=0; j <adjacencyMatrix.length;j++)
			{
				if((adjacencyMatrix[i][j]!=-1)&&(i!=j))
					count++;
			}
		}
		
		if(count==result)
			connected=true;
		else
			connected=false;
		
		//triangle check
		for (int i=0; i <adjacencyMatrix.length;i++)
		{
			for (int j=0; j <adjacencyMatrix.length;j++)
			{
				int connection;
				int[] weight=this.dijkstraAlgorithm(i);
				connection=weight[j];
				
				if(adjacencyMatrix[i][j]<=connection)//run dijkstra on on i set connection equal to distence[j]
						triangle=true;
			}
		}
		
		//results
		if(connected&&triangle)
			posobilities=3;		//metric
		
		if(connected&&!triangle)
			posobilities=2;		//not metric dosn't obay triangle inequality
		
		if(!connected&&triangle)
			posobilities=1;		//not metric not completely connected
		
		return posobilities;
	   }
	/**
	 * make the unmetric graph metric
	 */
	public void makeMetric()
	   {
			int[][] tempMatrix = new int[adjacencyMatrix.length][adjacencyMatrix.length];
			int[] temp= new int[adjacencyMatrix.length];
			
			for(int i=0;i<adjacencyMatrix.length;i++)
			{
				temp=this.dijkstraAlgorithm(i);
				for(int j=0;j<adjacencyMatrix.length;j++)
				{
					tempMatrix[i][j]=temp[j];
					if(tempMatrix[i][j]==0)
						tempMatrix[i][j]=-1;
				}
			}
			printOut(tempMatrix);
			for(int i=0;i<adjacencyMatrix.length;i++)
				for(int j=0;j<adjacencyMatrix.length;j++)
					adjacencyMatrix[i][j]=tempMatrix[i][j];
	   }

	/**
	 *  brute force to try all possible tours starting at node 0. 
	 *  Print the total length of the shortest tour and the sequence of nodes it takes.
	 */
	public void travelingSalesmanProblem()
	   {
			
			System.out.println();//clean output purpose
			boolean[]visited=new boolean[adjacencyMatrix.length];	//marks whether or not a node has been visited.
			int[] currentPath= new int[adjacencyMatrix.length+2];	//keeps track of where you are in the current path. 2nd to last holds the weight, very last holds the starting node of value 0
			int[] bestPath= new int[adjacencyMatrix.length+2];		//holds the best path in the graph. 2nd to last holds the weight, very last holds the starting node of value 0
			bestPath[bestPath.length-1]=Integer.MAX_VALUE;			//makes the weight of the path the greatest int value			
			int start=0;	//marks the starting node with 0

			TSP(start,currentPath,bestPath,0,visited);	//starts the recursive method of Traveling Salesman Problem

			//output
			System.out.print(bestPath[bestPath.length-1]+": ");
			for(int i=0;i<bestPath.length-2;i++)		
				System.out.print(bestPath[i]+" ");
			System.out.println();
	   }
	/**
	 * give an approximate the best TSP tour
	 */
	
	public void TSP(int node, int[] currentPath,int[] bestPath, int length, boolean[]visited){
															//length keeps track of how many nodes we've been to.
															//node gives the number of what node we are on
															//visited is a check to see which nodes we have been to.

		if(length==bestPath.length-2)//if we have reached the end and all nodes have been visited
		{
			if(node==0&&bestPath[bestPath.length-1]>currentPath[currentPath.length-1])
			{
				for(int i=0;i<bestPath.length;i++)
					bestPath[i]=currentPath[i];	//Found new best Path, now it gets replaced
			}
			return;
		}
		if(visited[node])//if the node looked at has already been visited, it will return.
			return;
		
		else
		{	
			visited[node]=true;	//mark the node visited
			for(int i=0;i<adjacencyMatrix.length;i++)
			{
				if(adjacencyMatrix[node][i]!=-1)
				{
					currentPath[length+1]=i;//keep track of which node has been looked at
					currentPath[currentPath.length-1]+=adjacencyMatrix[node][i];	//weight being added
					TSP(i,currentPath,bestPath,length+1,visited);	//Recursion
					currentPath[currentPath.length-1]-=adjacencyMatrix[node][i];	//weight being deducted
				}
			}
			visited[node]=false;	//unmark the node visited
		}
	}
	/**
	 * give an approximation of the best TSP tour
	 */
	public void approximateTSP()
	   {
		
		if(this.isMetric()!=3)
		{
			System.out.println("Error: Graph is not metric.");
			return;
		}

		int total=0;
		int [][] minimum= new int[adjacencyMatrix.length][adjacencyMatrix.length];	//creates a double array to create a second adjacencyMatrix with different values
		
		minimum=primAlgorithm(minimum);

		int[] order = new int[adjacencyMatrix.length+1];
		boolean[] visited = new boolean[minimum.length+1];

		DFT(0,visited, order,minimum,0);
	
		for(int i=0;i<order.length-1;i++)
		{
			total+=adjacencyMatrix[order[i]][order[i+1]];	
		}
		
		System.out.print(total+": ");
		
		for(int i=0;i<order.length-1;i++)
		{
			System.out.print(order[i]+" ");
		}

	   }

	private void DFS(int node, boolean[] visited)//node represents the first node looked at
	{											 //visited[] checks to see whether or not the node we are currently looking at has been checked yet																									
		if(visited[node]==true)//if the node currently being looked at has been visited, it will return
			return;

		visited[node]=true;
		boolean [] array=new boolean[adjacencyMatrix[node].length];// create a new array to hold how many connections the node has

		for(int i=0;i<adjacencyMatrix[node].length;i++)//	Goes through the adjacencyMatrix, looking to see if the current node has any connecting nodes
			if(adjacencyMatrix[node][i]!=-1)
				array[i]=true;
		for(int i=0;i<adjacencyMatrix[node].length;i++)//DFS each connecting node
			if(array[i]==true)
				DFS(i,visited);		
	}

	private void DFT(int node, boolean[] visited, int[] order, int[][] minimum, int length )//node represents the first node looked at
	{											 //visited[] checks to see whether or not the node we are currently looking at has been checked yet																									
		if(visited[node]==true)//if the node currently being looked at has been visited, it will return
			return;

		visited[node]=true;
		order[length]=node;
		length+=1;
		boolean [] array=new boolean[minimum.length];// create a new array to hold how many connections the node has

		for(int i=0;i<minimum.length;i++)//	Goes through the adjacencyMatrix, looking to see if the current node has any connecting nodes
			if(adjacencyMatrix[node][i]!=-1)
				array[i]=true;
		for(int i=0;i<minimum.length;i++)//DFS each connecting node
			if(array[i]==true)
				DFT(i,visited, order, minimum, length);		
	}

	/**
	 * takes a given node and calculates the shortest weight between it and all other nodes in the graph
	 * @param firstNode
	 * @return
	 */
	private int [] dijkstraAlgorithm(int firstNode)
	{
		boolean[] visited = new boolean[adjacencyMatrix.length];
		int[] distances= new int[adjacencyMatrix.length];	

		for(int i=0;i<distances.length;i++)
			distances[i]=-1;

		distances[firstNode]=0;
		visited[firstNode]=true;

		int count=1;//count is the number of passes through Dijkstra's
		int u=firstNode;	//u is the closest node
		while(count!=adjacencyMatrix.length)
		{
			for(int i=0;i<adjacencyMatrix.length;i++)	//fills the distanceNodes with possible weighted paths
			{
				if(adjacencyMatrix[u][i]!=-1&& !visited[i])
				{
					if(distances[i]==-1|| distances[i]>distances[u]+adjacencyMatrix[u][i])
						distances[i]=distances[u]+adjacencyMatrix[u][i];
				}
			}

			int temp = -1;
			for (int i=0;i<adjacencyMatrix.length;i++)	//this finds the lowest weighted path connected to the first node
			{
				if(!visited[i] && distances[i]!=-1 && (temp==-1|| distances[i]<distances[temp]))
					temp=i;
			}

			if(temp==-1)
				visited[u]=true;
			else
			{
				u =temp;		//makes the focused node the new node with the shortest path
				visited[u]=true;
			}

			count++;
		}
		return distances;
	}

	/**
	 * takes a graph and generates an instance of a minimum spanning tree
	 * @param temp
	 * @return
	 */
	private int[][] primAlgorithm(int[][] temp)
	{
		int node =0; //starting node
		int distance=Integer.MAX_VALUE;
		int connectedNode=0;
	
		for(int i=0;i<adjacencyMatrix.length;i++)
			for(int j=0;j<adjacencyMatrix.length;j++)
				temp[i][j]=-1;
		
		boolean[] visited = new boolean[this.adjacencyMatrix.length]; //true means node has been visited, false means it has not
		visited[node]=true;
		
		int check = 1;
		
		while(check!=adjacencyMatrix.length)
		{
			for(int i=0;i<adjacencyMatrix.length;i++)
			{
				if(adjacencyMatrix[i][node]!=-1&&distance>adjacencyMatrix[i][node])
				{
					distance=adjacencyMatrix[i][node];
					connectedNode=i;
				}
		}

			if(node!=connectedNode)
				temp[node][connectedNode]=distance;

			temp[connectedNode][node]=distance;

			visited[node]=true;

			node=check;
			check++;		
		}

		return temp;
	}
}