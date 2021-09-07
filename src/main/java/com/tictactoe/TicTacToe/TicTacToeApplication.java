package com.tictactoe.TicTacToe;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

@SpringBootApplication
public class TicTacToeApplication {

	private static char gameModeChoice = 0 ;
	public static void main(String[] args) {
		SpringApplication.run(TicTacToeApplication.class, args);

		System.out.println("Welcome to Tic Tac Toe.");
		System.out.println("--------------------------------");
		try (Scanner in = new Scanner(System.in)) {
			GameModeChoice(gameModeChoice, in);
		}
	}

	private static void GameModeChoice(char gameModeChoice, Scanner in) {
		while (gameModeChoice != '1' && gameModeChoice != '2') {
			System.out.println("Please Choose:");
			System.out.println("1- PLAYER Vs COMPUTER");
			System.out.println("2- 2 PLAYER");
			try {
				String input = in.next();
				if(StringUtils.hasText(input) && (input.charAt(0) == '1' || input.charAt(0) == '2')) {
						gameModeChoice = input.charAt(0);
				}else {
					throw new Exception();
				}
				
			} catch (Exception e) {
				
				System.out.println("Invalid input; Choose Between the proposed Choices:");
		        continue;

			}
		}
		
	}

	

}
