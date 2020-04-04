import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Board extends JFrame{
	private JLabel label = new JLabel("Minesweeper");
	private int mineCount=0;
	private JLabel mines;
	private JPanel mainPanel = new JPanel();
	private JPanel radioPanel = new JPanel();
	private final int boardSize = 10;
	MineButton [][] board = new MineButton[boardSize+2][boardSize+2];
	int r,c;

	//Create the radio buttons.

	JRadioButton checkMine = new JRadioButton("Check Mine", true);
	JRadioButton markMine = new JRadioButton("Mark Mine", false);

public Board(){
		MineButton button;
		setSize(boardSize*50,boardSize*50);
		setTitle("Minesweeper Game");
		setLayout(new GridLayout(boardSize,boardSize));
		setLocation(500,200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainPanel.setLayout(new GridLayout(boardSize,boardSize));

		for (int row=0; row<boardSize+2; row++){
			for (int col=0; col<boardSize+2; col++){
				button = new MineButton(row,col,false);
				if((row != 0 && col!= 0 && row!= boardSize+1 && col != boardSize +1))//don't display border squares
				{
					mainPanel.add(button);
				}
				else
					button.setClicked();
				board[row][col] = button;
				button.addActionListener(new ButtonListener(button));
			}
		}

		for (r=1; r<=boardSize; r++)		//place mines
			for (c=1; c<=boardSize; c++){
				if (Math.random()*100 <= 15){
					(board[r][c]).setMine();
					//(board[r][c]).setText("x");
					mineCount++;
				}
		}

		for (r=1; r<=boardSize; r++)
			for (c=1; c<=boardSize; c++)
			{
				if (!board[r][c].isMine()) 
					for (int i=r-1; i<=r+1; i++)
					for (int j=c-1; j<=c+1; j++)
					if (board[i][j].isMine())
					board[r][c].incSurrMines();
			}

		setLayout(new BorderLayout());
		add(label,BorderLayout.NORTH);
		add(mainPanel,BorderLayout.CENTER);

		 //Group the radio buttons.
		ButtonGroup Radiogroup = new ButtonGroup();
		Radiogroup.add(checkMine);
		Radiogroup.add(markMine);
		mines= new JLabel("Mines Left: "+ mineCount);
		radioPanel.add(mines);
		radioPanel.add(checkMine);
		radioPanel.add(markMine);
		add(radioPanel,BorderLayout.SOUTH);
		setVisible(true);
	}

	private class ButtonListener implements ActionListener
	{
		private MineButton button;
		public ButtonListener(MineButton b)
		{
			button=b;
		}
		private void checkAction()
		{
			if(button.getClicked()|| button.getText()=="*")
				return;
			button.clickButton(Board.this);
		
			if (button.isMine())			//show all mines on the board
			{
				for (int i=1; i<=boardSize; i++)
					for (int j=1; j<=boardSize; j++)
					{
						if (board[i][j].isMine())
						{
							board[i][j].clickButton(Board.this);
						}
					}
				endGame("You Lose");
			}
			int n=button.getSurroundingMines();
			if(n==0)
				button.setText("");
			else
				button.setText(""+n);
			//Surrounding mines loop
			int numberRow=button.getRow();
			int numberCol=button.getCol();
			if (button.getSurroundingMines()==0){

				for (int i=numberRow-1; i<=numberRow+1; i++)
					for(int j=numberCol-1; j<=numberCol+1; j++)
					{
						board[i][j].doClick();
					}
			}
			checkBoard();
		}

		private void markAction(){

			if(button.getText()=="*"){
				button.setText("");
				mineCount++;
			}
			else{
				button.setText("*");
				mineCount--;
				}
			mines.setText("Mines Left: "+ mineCount);
			checkBoard();
		}

		public void actionPerformed(ActionEvent e){//radio button actions
			if(checkMine.isSelected())
				checkAction();
			else
				markAction();
		}
	}

	private void checkBoard(){

		boolean gameWon=true;
		for (int i=1; i<=boardSize; i++)
			for (int j=1; j<=boardSize; j++)
			{
				if (!board[i][j].isMine() && !board[i][j].getClicked())
				{
					gameWon=false;
				}
			}
		if(gameWon){
			endGame("You Win!!");
		}
	}

	private void endGame(String result){
		int answer = JOptionPane.showConfirmDialog(mainPanel, "New Game?", result, JOptionPane.YES_NO_OPTION);
		if (answer==JOptionPane.YES_OPTION){
			new Board();
		}
		(Board.this).dispose();
	}

	public static void main (String[] args){
			new Board();
	}
}