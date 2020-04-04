import javax.swing.JOptionPane;

public class EightQueens {

	static boolean[][] board = new boolean[8][8];
	static int solutionCount = 0;
	static Node firstNode= new Node(solutionCount, board);
	static Node nextNode= firstNode;
    static Node currentNode=firstNode;
    static Node visibleNode=null;

	static void foundSolution()
	{
		 solutionCount++;
         Node n = new Node(solutionCount,board);
         currentNode.setNext(n);
         currentNode = n;
	}

	static boolean attacked(int row, int col)
	{
		for (int i=1; i<=row; i++)
		{
			if (board[row-i][col])  return true;
			if (col-i>=0 && board[row-i][col-i]) return true;
			if (col+i<8 && board[row-i][col+i]) return true;
		}
		return false;
	}

	static void placeQueen(int row)
	{
		int col;
		if (row==8)
		{
			foundSolution();
			return;
		}
		for (col=0; col<8; col++)
		{
			if (!attacked(row,col))
			{
				board[row][col] = true;	
				placeQueen(row+1);
				board[row][col] = false;
			}
		}
	}

	public static void firstSolution()
	{
		if(visibleNode!=null)
		{
			visibleNode.setVisible(false);
		}
		
		Node n=firstNode.getNext();
		n.setVisible(true);
		nextNode=n;
		visibleNode=n;
	}

	public static void nextSolution()
	{
		if(visibleNode!=null)
		{
			visibleNode.setVisible(false);
		}
		Node n=nextNode.getNext();
		if(n==null){
			JOptionPane.showMessageDialog(null, "Found Final Solution");
			return;
			}
			n.setVisible(true);
			nextNode=n;
			visibleNode=n;
		}

	public static void lastSolution()
	{
		if(visibleNode!=null)
		{
			visibleNode.setVisible(false);
		}

		currentNode.setVisible(true);
		visibleNode=currentNode;
		nextNode=currentNode;
		
	}

	public static int getSolutionCount()
	{
		return solutionCount;
	}
}