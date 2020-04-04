import javax.swing.*;
	import java.awt.*;
	import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JFrame{

		private JLabel solutionLabel = new JLabel("Total Solutions: "+ EightQueens.getSolutionCount());
		private JButton firstSolution= new JButton("First Solution");
		private JButton lastSolution= new JButton("Last Solution");
		private JButton nextSolution= new JButton("Next Solution");

		public ControlPanel()
		{
			setLocation(600,300);
			setSize(300,200);
			setTitle("8 Queens Solutions");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setLayout(new GridLayout(2,4));
			add(solutionLabel);
			add(firstSolution);
			add(lastSolution);
			add(nextSolution);

			firstSolution.addActionListener(new FirstListener());
			lastSolution.addActionListener(new LastListener());
			nextSolution.addActionListener(new NextListener());
			setVisible(true);
		}

		private class FirstListener implements ActionListener
		{

			public void actionPerformed(ActionEvent e) 
			{
				EightQueens.firstSolution();
			}
		}

		private class LastListener implements ActionListener 
		{

			public void actionPerformed(ActionEvent e) 
			{
				EightQueens.lastSolution();
			}
		}

		private class NextListener implements ActionListener 
		{

			public void actionPerformed(ActionEvent e) 
			{
				EightQueens.nextSolution();
			}
		}

		public void firstNode(Node firstNode)
		{
			EightQueens.firstSolution();
		}

		public static void main(String[] args)
		{
			EightQueens.placeQueen(0);
			new ControlPanel();
		}
}