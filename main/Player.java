package main;
import java.util.*;
/**
 * Class that represents players in Battlefield.
 * @author Mani, Karthik
 *
 */

public class Player {
	
	/**
	 * The name of the player.
	 */
	
	private String name;
	
	/**
	 * The board that represents the player's ships.
	 */
	
	private int[][] board;
	
	/**
	 * The list of choices that the player has already made.
	 */
	
	private ArrayList<Choice> choices = new ArrayList<Choice>();
	
	/**
	 * The size of the ships.
	 */
	
	public static final int SHIP_SIZE = 3;
	
	/**
	 * Constructs a player object.
	 * @param name1 - the name of the player.
	 * @param dimension - the dimension of the board.
	 */
	
	public Player(final String name1, final int dimension) {
		name = name1;
		board = createBoard(dimension);
		displayBoard(board);
	}
	
	/**
	 * Displays the board.
	 * @param arr - the board to be displayed.
	 */
	
	private static void displayBoard(int[][] arr) {
		System.out.println("Here is your current board.");
		System.out.print(" \t");
		for (int num = 0; num < arr[0].length; num++) {
			System.out.print(num + ".\t");
		}
		System.out.println();
		for (int i = 0; i < arr.length; i++) {
			System.out.print(i + ".\t");
			for (int j = 0; j < arr[0].length; j++) {
				System.out.print(arr[i][j] + "\t");
			}
			System.out.println();
			System.out.println();
		}
	}
	
	/**
	 * Creates the board for the player.
	 * @param dimension - the dimension of the board.
	 * @return - the board for the player.
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
	 * @param arr - the board for the player.
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
	 * @param arr - the board for the player.
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
	 * Asks the player to make a choice for the next target, and determines if that choice is valid.
	 * @return a Choice object that represents the player's valid choice.
	 */
	
	public Choice makeChoice() {
		Scanner scan = new Scanner(System.in);
		int row;
		int col;
		System.out.println(name + ", It is your turn to choose a target. Please enter a valid row and column, between 0 and " + (board.length - 1) + ", inclusive");
		System.out.println("Row: ");
		row = scan.nextInt();
		System.out.println("Column: ");
		col = scan.nextInt();
		boolean chooseAgain = checkTarget(row, col);
		while (!chooseAgain) {
			System.out.println("Your inputs were invalid. Please ensure that");
			System.out.println("your input row and columns were within the range of the board.");
			System.out.println("Also, you are not allowed to make repeat choices.");
			System.out.println("Row: ");
			row = scan.nextInt();
			System.out.println("Column: ");
			col = scan.nextInt();
			chooseAgain = checkTarget(row, col);
		}
		System.out.println("You have made your choice to hit at row " + row + " and column " + col + ".");
		Choice next = new Choice(row, col);
		choices.add(next);
		scan.close();
		return(next);
	}
	
	/**
	 * Determines if computer's target was correct, and alters the board of the player if necessary.
	 * @param row - the computer's target's row-coordinate.
	 * @param col - the computer's target's column-coordinate.
	 */
	
	public void getHit(int row, int col) {
		if (board[row][col] == 1) {
			System.out.println(name + ", you have been hit at row " + row + " and column "  + col + ".");
			displayBoard(board);
		}
		else {
			System.out.println("Whew, the CPU missed.");
			displayBoard(board);
		}
	}
	
	/**
	 * Checks if the player's target is valid, based on whether it is out of bounds of the board and based on whether the player
	 * has already made the choice to go for that target. 
	 * Players are not allowed to make the choice to go for a target for which they have already gone.
	 * @param row - the player's target's row-coordinate.
	 * @param col - the player's target's column-coordinate.
	 * @return - returns whether or not the player's choice for a target is valid.
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
