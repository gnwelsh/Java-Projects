import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.*;

public class Node extends JFrame{

	JButton[][] display = new JButton[8][8];
	public Node next=null;

	public Node(int solutionNumber, boolean[][] board){
		int r,c;
		setLayout(new GridLayout(8,8));

		for (r=0; r<8; r++)
			for (c=0; c<8; c++)
			{
				display[r][c] = new JButton();
				if (board[r][c]) 
					display[r][c].setText("Q");

				add(display[r][c]);

				if((r+c)%2==0)
					display[r][c].setBackground(Color.red); 
				else
					display[r][c].setBackground(Color.white);
			}

		setSize(400,400);
		setTitle("Solution Number: "+solutionNumber);
		setLocation(solutionNumber*8,10);
		setVisible(false);
	}

	public void setNext(Node n)
	{
		next = n;
	}
	
	public Node getNext()
	{
		return next;
	}
}