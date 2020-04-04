import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainMenu {
	
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner kb = new Scanner(System.in);//keyboard input
		
		 // Get the file name.
		
	      System.out.print("Enter the name of a graph file: ");
	      String graphName = kb.nextLine();

	      Graph original = new Graph(graphName);
		
		int choice;

		do{
			menu();
			choice=kb.nextInt();

			if(choice<1||choice>8){
				System.out.println("Input Error. End Program");
			break;
			}
			
			switch (choice){

            case 1:  choice = 1;
            
            	if(original.isConnected())  	
            		System.out.println("Graph is connected.");
            	else
            		System.out.println("Error: Graph is not connected.");
      
            	break;

            case 2:  choice = 2;
            	if(original.isConnected())  	
            		original.minimumSpanningTree();     		
            	else
            		System.out.println("Error: Graph is not connected.");
          
                break;
            
            case 3:  choice = 3;
            	original.shortestPath();
                     break;

            case 4:  choice = 4;   
            	int result=original.isMetric();            
            	if(result==3)
            		System.out.println("Graph is metric.");            
            	else if(result==2)
            		System.out.println("Graph is not metric: Edges do not obey the triangle inequality.");            
            	else if(result==1)
            		System.out.println("Graph is not metric: Graph is not completely connected.");           
            	
                     break;

            case 5:  choice = 5;
            if(original.isConnected())  	
        		original.makeMetric();     		
        	else
        		System.out.println("Error: Graph is not connected.");
                     break;
            
            case 6:  choice = 6;
            	original.travelingSalesmanProblem();
                     break;
            
            case 7:  choice = 7;
            	original.approximateTSP();
                     break;
            
            case 8: choice =8;
            	original.printOut(original.getMatrix());
             
            default: choice = 9;
            	
                     break;
        }
		}while(choice!=8);
		System.out.println("Done");
	}
	
	/**
	 * prints out a menu of ways to manipulate the given graph
	 */
	public static void menu()
	   {
		System.out.print("\n"+
				"1. Is Connected\n" +
				"2. Minimum Spanning Tree\n" +
				"3. Shortest Path\n" +
				"4. Is Metric\n" +
				"5. Make Metric\n" +
				"6. Traveling Salesman Problem\n" +
				"7. Approximate TSP\n" +
				"8. Quit\n\n"+
				"Make your choice (1 - 8):  ");
	   }
}