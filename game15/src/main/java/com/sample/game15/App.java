package com.sample.game15;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import com.sample.game15.logic.GameBoard;
import com.sample.game15.logic.GameBoardGenerator;
import com.sample.game15.logic.GameException;
import com.sample.game15.logic.Pile;
import com.sample.game15.view.GameBoardView;

/**
 * Entry Point
 *
 */
public class App {
	
	// min values for both are 2
	private static final int ROW_COUNT = 4;
	private static final int COLUMN_COUNT = 4;
	
	public static void main(String[] args) {
		
		Long seed = null;
		
		if (args.length == 2) {
			if ("--seed".equals(args[0])) {
				try {
					seed = Long.parseLong(args[1]);
					print("Using provided seed: " + seed);
				} catch (Exception e) {
					print("Wrong seed format.");
				}
			}
		}
		print("New game started!");

		GameBoardGenerator generator = new GameBoardGenerator(ROW_COUNT, COLUMN_COUNT, seed);
		GameBoard board = generator.generate();

		GameBoardView boardView = new GameBoardView(board);

		String v = boardView.view();
		print(v);
		print("You can always start with the same board by providing arguments: --seed " + generator.getSeed());
		exitIfUserWon(board);
		
		Scanner inputScanner = null;
		try {
			inputScanner = new Scanner(System.in);
			while (true) {
				Optional<Integer> maybePile = readMove(inputScanner, board);
				if (maybePile.isPresent()) {
					int num = maybePile.get();
					print("Selected:" + num);
					try {
						board.movePile(new Pile(num));
					} catch (GameException e) {
						print(e.getMessage());
					}
					v = boardView.view();
					print(v);
					exitIfUserWon(board);
				}
			}
		} finally {
			if (inputScanner != null)
				try {
					inputScanner.close();
				} catch (Exception ignored) {
					//ignored
				}
		}
	}

	private static void exitIfUserWon(GameBoard board) {
		if(board.hasUserWon()) {
			print("Congratulations! You WON!!!");
			print("GAME OVER");
			System.exit(0);
		}
	}

	private static void print(String s) {
		System.out.println(s);
	}

	private static Optional<Integer> readMove(Scanner in, GameBoard board) {
		Set<Pile> moves = board.eligibleMoves();
		List<Integer> moveNames = moves.stream().map(Pile::getValue).collect(Collectors.toList());
		int maxMove = ROW_COUNT * COLUMN_COUNT - 1;
		print("What is your next move? Please enter a number between 1 and " + maxMove + ". Cntl+C to exit");
		print("Available options: " + moveNames);
		
		Optional<Integer> res = Optional.empty();
		try {
			res = Optional.of(in.nextInt());
		} catch ( NoSuchElementException e) {
			print("Input not valid");
			if(in.hasNextLine()) {
				in.nextLine();
			}
		}
		return res;
	}
}
