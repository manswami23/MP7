package main;

public class Choice {
	private int row;
	private int col;
	public Choice (final int row1, final int col1) {
		row = row1;
		col = col1;
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public boolean equals(Choice other) {
		if (other.getRow() == row && other.getCol() == col) {
			return true;
		}
		else {
			return false;
		}
	}
}
