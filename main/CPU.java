package main;

import java.util.ArrayList;


public class CPU {

	protected  int[][] board;
	
	/**
	 * The list of choices that the player has already made.
	 */
	
	private ArrayList<Choice> choices = new ArrayList<Choice>();
	
	/**
	 * The size of the ships.
	 */
	
	public static final int SHIP_SIZE = 3;
	
	public CPU() {
		board = createBoard(10);

	}
		
	
	
	


	/**
	 * Creates the board for the cpu.
	 * @param dimension - the dimension of the board.
	 * @return - the board for the cpu.
	 */
	
	private static int[][] createBoard(final int dimension) {
		int[][] result = new int[dimension][dimension];
		//6 ships, three horizontal, three vertical.
		createShipV(result);
		createShipV(result);
		createShipV(result);
		createShipH(result);
		createShipH(result);
		createShipH(result);
		return (result);
	}
	
	/**
	 * Creates ships in the vertical direction on the board.
	 * @param arr - the board for the cpu.
	 */
	
	private static void createShipV(int[][] arr) {
		int startR = createC(arr.length);
		int startC = createC(arr.length);
		
		boolean errorStart = false;
		while (!errorStart) {
			for (int i = startR; i < startR + SHIP_SIZE; i++) {
				
					if (arr[i][startC] == 1) {
						errorStart = true;
						break;
					}
			}
			if (errorStart) {
				startR = createC(arr.length);
				startC = createC(arr.length);
				errorStart = false; //basically, if there is another ship in the way, we get new coordinates
									// and start again at the top of the while loop to check the new coordinates' validity.
			}
			else if(!errorStart) {
				errorStart = true;//this is done in the case where the coordinates are valid, and we must exit the while loop.
			}
		}
		for (int i = startR; i < startR + SHIP_SIZE; i++) {
			arr[i][startC] = 1;
		}
	}
	
	/**
	 * Creates ships in the horizontal direction on the board.
	 * @param arr - the board for the cpu.
	 */
	
	private static void createShipH(int[][] arr) {
		int startR = createC(arr.length);
		int startC = createC(arr.length);
		
		boolean errorStart = false;
		while (!errorStart) {
			for (int i = startC; i < startC + SHIP_SIZE; i++) {
				
					if (arr[startR][i] == 1) {
						errorStart = true;
						break;
					}
			}
			if (errorStart) {
				startR = createC(arr.length);
				startC = createC(arr.length);
				errorStart = false; //basically, if there is another ship in the way, we get new coordinates
									// and start again at the top of the while loop to check the new coordinates validity.
			}
			else if(!errorStart) {
				errorStart = true;//this is done in the case where the coordinates are cleared, and we must exit the while loop.
			}
		}
		for (int i = startC; i < startC + SHIP_SIZE; i++) {
			arr[startR][i] = 1;
		}
	}
	
	/**
	 * Creates either the row-coordinate or the column-coordinate for the ships to be placed on the board.
	 * @param dimension - the dimension of the board.
	 * @return either the row-coordinate or the column-coordinate.
	 */
	
	private static int createC(final int dimension) {
		int result = (int) (Math.random() * dimension);
		while ((result + SHIP_SIZE) > dimension - 1) {
			result = (int) (Math.random() * dimension);
		}
		return (result);
	}
	
	/**
	 * CPU makes a choice for the next target, and determines if that choice is valid.
	 * @return a Choice object that represents the CPU's valid choice.
	 */
	
	public Choice makeChoice() {
		
		int row;
		int col;
		row = (int) (Math.random() * board.length);
		
		col = (int) (Math.random() * board[0].length);
		boolean chooseAgain = checkTarget(row, col);
		while (!chooseAgain) {
			row = (int) (Math.random() * board.length);
			
			col = (int) (Math.random() * board[0].length);
			chooseAgain = checkTarget(row, col);
		}
		
		Choice next = new Choice(row, col);
		choices.add(next);
		
		return(next);
	}
	
	public void getHit(int row, int col) {
		if (board[row][col] == 1) {
			System.out.println("You have made a right hit.");
			board[row][col] = 0;
			
		}
		else {
			System.out.println("Oh, you have missed.");
			
		}
	}
	
	/**
	 * Checks if the cpu's target is valid, based on whether it is out of bounds of the board and based on whether the cpu
	 * has already made the choice to go for that target. 
	 * cpu is not allowed to make the choice to go for a target for which they have already gone.
	 * @param row - the cpu's target's row-coordinate.
	 * @param col - the cpu's target's column-coordinate.
	 * @return - returns whether or not the cpu's choice for a target is valid.
	 */
	
	public boolean checkTarget(int row, int col) {
		if (row < 0 || row > board.length - 1 || col < 0 || col > board[0].length - 1) {
			return false;
		}
		if (choices.size() > 0) {
			Choice target = new Choice(row, col);
			for (Choice item: choices) {
				if (item.equals(target)) {
					return false;
				}
			}
		}	
		return true;
	}	
}
