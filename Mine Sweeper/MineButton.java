import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class MineButton extends JButton{

	private boolean Clicked=false;
	private boolean minePresent=false;
	private int surrMines = 0;
	private int row, col;

	public boolean isMinePresent() {
		return minePresent;
	}

	public int getSurrMines() {
		return surrMines;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public MineButton(int r, int c, boolean m)
	{
		row = r;
		col = c;
		minePresent = m;
		setText("");
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setCol(int col) {
		this.col = col;
	}
	public MineButton(){

	}

	public void setMine(){
		minePresent=true;
	}

public void setClicked(){

		Clicked=true;
	}

public boolean getClicked(){

	return Clicked;
}

public int getSurroundingMines(){
	return surrMines;
}

public boolean isMine()
{
	return minePresent;
}

public void incSurrMines()
{
	surrMines++;
}
	public void clickButton(Board mineBoard){
		Clicked=true;
		if(minePresent){
			setText("X");
			setBackground(Color.RED);
			}
		else
			setBackground(Color.GRAY);
}
}