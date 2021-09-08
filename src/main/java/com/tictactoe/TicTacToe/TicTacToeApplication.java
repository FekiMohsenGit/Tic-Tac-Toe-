package com.tictactoe.TicTacToe;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

@SpringBootApplication
public class TicTacToeApplication {

	private static int gameModeChoice = 0;
	private static String playerOneName = "Player1";
	private static String playerTwoName = "Player2";
	private static String[] board = new String[9];
	private static String winner = null;
	private static int turn = 1;
	private final static String computerName = "Computer";

	public static void main(String[] args) {
		SpringApplication.run(TicTacToeApplication.class, args);

		WelcomeMessage();
		boolean playAgain = true;
		try (Scanner in = new Scanner(System.in)) {
			GameModeChoice(in);
			while (playAgain) {

				if (gameModeChoice == 2) {
					EnteringNames(in, 2);
					InitializeBoard();
					printBoard();
					System.out.println(playerOneName + "'s will play first. Enter a slot number to place A in:");
					startTheGame(in);
				} else {
					EnteringNames(in, 1);
					InitializeBoard();
					printBoard();
					System.out.println(playerOneName + "'s will play first. Enter a slot number to place A in:");
					startTheGameVsComputer(in);
				}
				System.out.println("Wanna Replay? y/n");
				String answer = in.next();
				while("n".equalsIgnoreCase(answer) && "y".equalsIgnoreCase(answer) ) {
					answer = in.next();
				}
				if("n".equalsIgnoreCase(answer)) {
					playAgain = false;
				}
				winner = null;
			}
		}
	}

	private static void startTheGameVsComputer(Scanner in) {

		int turnPlayed = 0;
		turn = 1;
		while (winner == null) {
			int numInput;
			try {
				if (turn == 1) {
					numInput = Integer.parseInt(in.next());
					if (!(numInput > 0 && numInput <= 9)) {
						System.out.println("Invalid Slot Selection; re-enter slot number:");
						continue;
					}
				} else {
					numInput = numImakeaMoveForMeComputer(turnPlayed);
					System.out.println(numInput);
				}

			} catch (Exception e) {
				System.out.println("Invalid Slot Selection; re-enter slot number:");
				continue;
			}

			if (board[numInput - 1].equals(String.valueOf(numInput))) {

				if (turn == 1) {
					board[numInput - 1] = "X";
					turn = 2;
				} else {
					board[numInput - 1] = "O";
					turn = 1;
				}
				turnPlayed++;
				printBoard();
				if (turnPlayed > 4)
					winner = checkWinner();
			} else {
				System.out.println("Slot already taken; re-enter slot number:");
				continue;
			}
			if (winner == null) {
				if (turn == 1) {
					System.out.println(playerOneName + "'s turn; enter a slot number to place " + turn + " in:");
				} else {
					System.out.println(playerTwoName + "'s turn; enter a slot number to place " + turn + " in:");
				}

			} else if (winner.equalsIgnoreCase("draw")) {
				System.out.println("It's a draw! Thanks for playing.");
			} else {
				System.out.println("Congratulations! " + winner + "'s have won! Thanks for playing.");
			}

		}

	}

	private static int numImakeaMoveForMeComputer(int turnPlayed) {
		if (turnPlayed == 1) {
			return ((int) (Math.random() * (9 - 1))) + 1;
		} else {
			int x = checkIfICanWin();
			if (x == 0) {
				x = checkIfOpponentIsWinning();
				if (x == 0) {
					x = checkIfIHaveAnOpportunity();
					if (x == 0) {
						x = getAnyEmptySlot();
					}
				}
				return x;
			} else {
				return x;
			}
		}
	}

	private static int getAnyEmptySlot() {
		for (int i = 0; i < board.length; i++) {
			if (board[i] != "O" && board[i] != "X") {
				return i + 1;
			}
		}
		return 0;
	}

	private static int checkIfIHaveAnOpportunity() {
		for (int a = 0; a < 8; a++) {
			String line = null;
			switch (a) {
			case 0:
				line = board[0] + board[1] + board[2];
				break;
			case 1:
				line = board[3] + board[4] + board[5];
				break;
			case 2:
				line = board[6] + board[7] + board[8];
				break;
			case 3:
				line = board[0] + board[3] + board[6];
				break;
			case 4:
				line = board[1] + board[4] + board[7];
				break;
			case 5:
				line = board[2] + board[5] + board[8];
				break;
			case 6:
				line = board[0] + board[4] + board[8];
				break;
			case 7:
				line = board[2] + board[4] + board[6];
				break;
			}

			long count = line.chars().filter(ch -> ch == 'O').count();
			long count2 = line.chars().filter(ch -> ch == 'X').count();
			if (count == 1 && count2 == 0) {
				String ch = (line.replace("O", "")).charAt(0) + "";
				return Integer.parseInt(ch);
			}
		}
		return 0;
	}

	private static int checkIfICanWin() {
		for (int a = 0; a < 8; a++) {
			String line = null;
			switch (a) {
			case 0:
				line = board[0] + board[1] + board[2];
				break;
			case 1:
				line = board[3] + board[4] + board[5];
				break;
			case 2:
				line = board[6] + board[7] + board[8];
				break;
			case 3:
				line = board[0] + board[3] + board[6];
				break;
			case 4:
				line = board[1] + board[4] + board[7];
				break;
			case 5:
				line = board[2] + board[5] + board[8];
				break;
			case 6:
				line = board[0] + board[4] + board[8];
				break;
			case 7:
				line = board[2] + board[4] + board[6];
				break;
			}

			long count = line.chars().filter(ch -> ch == 'O').count();
			long count2 = line.chars().filter(ch -> ch == 'X').count();
			if (count == 2 && count2 == 0) {
				String ch = (line.replace("O", "")).charAt(0) + "";
				return Integer.parseInt(ch);
			}
		}
		return 0;
	}

	private static int checkIfOpponentIsWinning() {
		for (int a = 0; a < 8; a++) {
			String line = null;
			switch (a) {
			case 0:
				line = board[0] + board[1] + board[2];
				break;
			case 1:
				line = board[3] + board[4] + board[5];
				break;
			case 2:
				line = board[6] + board[7] + board[8];
				break;
			case 3:
				line = board[0] + board[3] + board[6];
				break;
			case 4:
				line = board[1] + board[4] + board[7];
				break;
			case 5:
				line = board[2] + board[5] + board[8];
				break;
			case 6:
				line = board[0] + board[4] + board[8];
				break;
			case 7:
				line = board[2] + board[4] + board[6];
				break;
			}

			long count = line.chars().filter(ch -> ch == 'O').count();
			long count2 = line.chars().filter(ch -> ch == 'X').count();
			if (count == 2 && count2 == 0) {
				return Integer.parseInt(line.replace("O", ""));
			} else if (count2 == 2 && count == 0) {
				return Integer.parseInt(line.replace("X", ""));
			}
		}
		return 0;

	}

	private static void startTheGame(Scanner in) {
		int turnPlayed = 0;
		while (winner == null) {
			int numInput;
			try {
				numInput = Integer.parseInt(in.next());
				if (!(numInput > 0 && numInput <= 9)) {
					System.out.println("Invalid Slot Selection; re-enter slot number:");
					continue;
				}
			} catch (Exception e) {
				System.out.println("Invalid Slot Selection; re-enter slot number:");
				continue;
			}

			if (board[numInput - 1].equals(String.valueOf(numInput))) {

				if (turn == 1) {
					board[numInput - 1] = "X";
					turn = 2;
				} else {
					board[numInput - 1] = "O";
					turn = 1;
				}
				turnPlayed++;
				printBoard();
				if (turnPlayed > 4)
					winner = checkWinner();
			} else {
				System.out.println("Slot already taken; re-enter slot number:");
				continue;
			}
			if (winner == null) {
				if (turn == 1) {
					System.out.println(playerOneName + "'s turn; enter a slot number to place " + turn + " in:");
				} else {
					System.out.println(playerTwoName + "'s turn; enter a slot number to place " + turn + " in:");
				}

			} else if (winner.equalsIgnoreCase("draw")) {
				System.out.println("It's a draw! Thanks for playing.");
			} else {
				System.out.println("Congratulations! " + winner + "'s have won! Thanks for playing.");
			}

		}
	}

	private static void EnteringNames(Scanner in, int playerNumber) {
		String name = "";
		playerOneName = NameInputSetting(in, name, "1");
		if (playerNumber == 2)
			playerTwoName = NameInputSetting(in, name, "2");
		;
	}

	private static String NameInputSetting(Scanner in, String name, String playerNumber) {
		System.out.println("Please Enter player " + playerNumber + " name (must not be Empty)");
		while (!StringUtils.hasText(name)) {
			name = in.next();
		}
		return name;
	}

	private static void WelcomeMessage() {
		System.out.println("\n\n\n \n\n\n\n\n\n\n\n\n\n");
		System.out.println(
				" __        __   _                            _____       _____ _        _____            _____          \n"
						+ " \\ \\      / /__| | ___ ___  _ __ ___   ___  |_   _|__   |_   _(_) ___  |_   _|_ _  ___  |_   _|__   ___ \n"
						+ "  \\ \\ /\\ / / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\   | |/ _ \\    | | | |/ __|   | |/ _` |/ __|   | |/ _ \\ / _ \\\n"
						+ "   \\ V  V /  __/ | (_| (_) | | | | | |  __/   | | (_) |   | | | | (__    | | (_| | (__    | | (_) |  __/\n"
						+ "    \\_/\\_/ \\___|_|\\___\\___/|_| |_| |_|\\___|   |_|\\___/    |_| |_|\\___|   |_|\\__,_|\\___|   |_|\\___/ \\___|\n"
						+ "                                                                                                        ");
		System.out.println("\n\n\n \n\n");
	}

	private static void GameModeChoice(Scanner in) {
		while (gameModeChoice != 1 && gameModeChoice != 2) {
			System.out.println("Please Choose:");
			System.out.println("1- PLAYER Vs COMPUTER");
			System.out.println("2- 2 PLAYER");
			try {
				gameModeChoice = Integer.parseInt(in.next());
				if (gameModeChoice != 1 && gameModeChoice != 2) {
					throw new Exception();
				}

			} catch (Exception e) {

				System.out.println("Invalid input; Choose Between the proposed Choices:");
				continue;

			}
		}

	}

	private static void InitializeBoard() {
		for (int a = 0; a < 9; a++) {
			board[a] = String.valueOf(a + 1);
		}
	}

	private static void printBoard() {
		String createBoard = "|---|---|---| \n" + "| " + board[0] + " | " + board[1] + " | " + board[2] + " | \n"
				+ "|-----------| \n" + "| " + board[3] + " | " + board[4] + " | " + board[5] + " | \n"
				+ "|-----------| \n" + "| " + board[6] + " | " + board[7] + " | " + board[8] + " | \n"
				+ "|---|---|---| \n";
		System.out.println(createBoard);
		System.out.println();
	}

	static String checkWinner() {
		for (int a = 0; a < 8; a++) {
			String line = null;
			switch (a) {
			case 0:
				line = board[0] + board[1] + board[2];
				break;
			case 1:
				line = board[3] + board[4] + board[5];
				break;
			case 2:
				line = board[6] + board[7] + board[8];
				break;
			case 3:
				line = board[0] + board[3] + board[6];
				break;
			case 4:
				line = board[1] + board[4] + board[7];
				break;
			case 5:
				line = board[2] + board[5] + board[8];
				break;
			case 6:
				line = board[0] + board[4] + board[8];
				break;
			case 7:
				line = board[2] + board[4] + board[6];
				break;
			}
			if (line.equals("OOO")) {
				if (gameModeChoice == 2) {
					return playerTwoName;
				} else {
					return computerName;
				}

			} else if (line.equals("XXX")) {
				return playerOneName;
			}
		}

		for (int a = 0; a < 9; a++) {
			if (Arrays.asList(board).contains(String.valueOf(a + 1))) {
				break;
			} else if (a == 8)
				return "draw";
		}

		return null;
	}
}
