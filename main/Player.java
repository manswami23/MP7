package main;
import java.util.*;
/**
 * Class that represents players in Battlefield.
 * @author Mani, Karthik
 *
 */

public class Player {
	private Scanner scan = new Scanner(System.in);
	
	/**
	 * The name of the player.
	 */
	private String name;
	
	/**
	 * The board that represents the player's ships.
	 */
	protected int[][] board;
	
	/**
	 * The board represents the players choices.
	 */
	protected char[][] choicesBoard;
	
	
	/**
	 * Fills in the board with '.''s
	 */
	public static char[][] initialBoard(final int[][] arr, final int dimension) {
		char[][] choices = new char[dimension][dimension];
		
		for (int i = 0; i < choices.length; i++) {
			for (int j = 0; j < choices[i].length; j++) {
				if (arr[i][j] == 1) {
					choices[i][j] = 'S';
				} else if (arr[i][j] == 0) {
					choices[i][j] = '.';
				}
			}
		}
		return choices;
	}
	
	
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
	public Player(final String name1) {
		name = name1;
		board = createBoard(10);
		choicesBoard = initialBoard(board, 10);
		displayBoard(choicesBoard);
	}
	
	
	/**
	 * Displays the board.
	 * @param arr - the board to be displayed.
	 */
	/**private static void displayBoard(int[][] arr) {
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
	} */

	
	 /** Displays the board.
	 * @param arr - the board to be displayed.
	 */
	private static void displayBoard(char[][] arr) {
		System.out.print(" \t");
		for (int num = 0; num < arr[0].length; num++) {
			System.out.print("\t");
		}
		System.out.println();
		for (int i = 0; i < arr.length; i++) {
			System.out.print("\t");
			for (int j = 0; j < arr[0].length; j++) {
				System.out.print(arr[i][j] + "\t");
			}
			System.out.println();
			System.out.println();
		}
		System.out.println("Here is your current board.");
	}
	
	/**
	 * Creates the board for the player.
	 * @param dimension - the dimension of the board.
	 * @return - the board for the player.
	 */
	private static int[][] createBoard(final int dimension) {
		int[][] result = new int[dimension][dimension];
		//4 ships, two horizontal, two vertical.
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
		
		int row;
		int col;
		System.out.println(name + ", It is your turn to choose a target. Please enter a valid row and column, between 0 and " + (board.length - 1) + ", inclusive");
		System.out.println("Row: ");
		
		row = Integer.parseInt(scan.nextLine());
				
		
		System.out.println("Column: ");
		
		col = Integer.parseInt(scan.nextLine());
		boolean chooseAgain = checkTarget(row, col);
		while (!chooseAgain) {
			System.out.println("Your inputs were invalid. Please ensure that");
			System.out.println("your input row and columns were within the range of the board.");
			System.out.println("Also, you are not allowed to make repeat choices.");
			System.out.println("Row: ");
			
			row = Integer.parseInt(scan.nextLine());
					
			
			System.out.println("Column: ");
			
			col = Integer.parseInt(scan.nextLine());
			chooseAgain = checkTarget(row, col);
		}
		System.out.println("You have made your choice to hit at row " + row + " and column " + col + ".");
		Choice next = new Choice(row, col);
		choices.add(next);
		
		return(next);
	}
	
	/**
	 * Determines if computer's target was correct, and alters the board of the player if necessary.
	 * @param row - the computer's target's row-coordinate.
	 * @param col - the computer's target's column-coordinate.
	 */
	public void getHit(int row, int col) {
		if (board[row][col] == 1) {
			board[row][col] = 0;
			choicesBoard[row][col] = 'X';
			displayBoard(choicesBoard);
			System.out.println(name + ", you have been hit at row " + row + " and column "  + col + "!");
		} else {
			System.out.println("Whew, the CPU missed.");
			choicesBoard[row][col] = 'O';
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
