package main;
import java.util.*;
public class Game {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String name;
		int dimension;
		System.out.println("Hello player. Welcome to Battlefield. Enter your name and desired board dimension. We recommend 8.");
		System.out.println("Anything above 8 might be too slow.");
		System.out.println("Name: ");
		name = scan.nextLine();
		System.out.println("Dimension (Enter only one number): "); 
		dimension = scan.nextInt();
		Player player = new Player(name, dimension);
		
	}
	private boolean checkGameOver(final int[][] playerB, final int[][] computerB) {
		boolean playerL = true;
		boolean computerL = true;
		for (int i = 0; i < playerB.length; i++) {
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
		return false;
	}
}	
