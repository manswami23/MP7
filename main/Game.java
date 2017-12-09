package main;
import java.util.*;

/**
 * The Game class implements the game in the console.
 * @author Mani, Karthik
 *
 */
public class Game {
	/**
	 * The main method runs the game in the console.
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String name;

		System.out.println("Hello player. Welcome to Battlefield. Enter your name.");
		System.out.println("Name: ");
		name = scan.nextLine();
		
		Player player = new Player(name);
		CPU cpu = new CPU();
		while (!checkGameOver(player.board, cpu.board)) {
			Choice temp = player.makeChoice();
			cpu.getHit(temp.getRow(), temp.getCol());
			Choice temp1 = cpu.makeChoice();
			player.getHit(temp1.getRow(), temp1.getCol());	
		}
	}
	
	
	/**
	 * chechGameOver checks the player's and CPU's board to check if the game is over.
	 * Returns false if playerCount or cpuCount is greater than zero.
	 * Returns true if playerCount or cpuCount is zero and prints output accordingly.
	 * @param playerB - Player's board
	 * @param computerB - Computer's board
	 * @return true or false.
	 */
	private static boolean checkGameOver(final int[][] playerB, final int[][] computerB) {
		boolean playerL = true;
		boolean computerL = true;
		int playerCount = 0;
		int cpuCount = 0;
		for (int i = 0; i < playerB.length; i++) {
			for (int j = 0; j < playerB[0].length; j++) {
				playerCount += playerB[i][j];
			}
		}
		for (int i = 0; i < computerB.length; i++) {
			for (int j = 0; j < computerB[0].length; j++) {
				cpuCount += computerB[i][j];
			}
		}
	
		if (playerCount > 0 && cpuCount > 0) {
			return false;
		} else if (playerCount == 0) {
			System.out.println("The player has lost. Game over.");
			return true;
		} else {
			System.out.println("The computer has lost. The player has won. Good job.");
			return true;
		}
		
		/**for (int i = 0; i < playerB.length; i++) {
			for (int j = 0; j > playerB[0].length; j++) {
				if (playerB[i][j] == 1) {
					 playerL = false;
					 break;
				}
			}
			if (!playerL) {
				break;
			}
		}
		if (playerL) {
			System.out.println("The player has lost. Game over.");
			return true;
		}
		for (int i = 0; i < computerB.length; i++) {
			for (int j = 0; j < computerB[0].length; j++) {
				if (computerB[i][j] == 1) {
					computerL =  false;
					break;
				}
			}
			if (!computerL) {
				break;
			}
		}
		if (computerL) {
			System.out.println("The computer has lost. The player has won. Good job.");
			return true;
		}
		return false;*/
	}
}	
