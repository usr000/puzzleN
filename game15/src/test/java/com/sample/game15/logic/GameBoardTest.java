package com.sample.game15.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.Test;
import org.junit.Assert;

public class GameBoardTest {
	
	@Test
	public void testHasUserWon() {
		int rows = 4;
		int columns = 4;
		
		List<Pile> piles = buildWinningPiles(rows, columns);
		
		GameBoard gameBoard = new GameBoard(rows, columns, piles);
		boolean hasWon = gameBoard.hasUserWon();
		
		Assert.assertTrue(hasWon);
	}
	
	@Test
	public void testHasUserWon_false() {
		int rows = 4;
		int columns = 4;
		
		List<Pile> piles = buildWinningPiles(rows, columns);
		Random r = new Random();
		int maxExcusiveBound = piles.size();
		
		int firstIdx = r.nextInt(maxExcusiveBound);
		int secondIdx;
		do {
			secondIdx=r.nextInt(maxExcusiveBound);
		} while (firstIdx == secondIdx);
			
		Assert.assertFalse(firstIdx == secondIdx);
		
		Pile p1 = piles.get(firstIdx);
		Pile p2 = piles.get(secondIdx);
		piles.set(firstIdx, p2);
		piles.set(secondIdx, p1);
		
		GameBoard gameBoard = new GameBoard(rows, columns, piles);
		boolean hasWon = gameBoard.hasUserWon();
		Assert.assertFalse("Failed for:" + piles, hasWon);
	}
	
	@Test
	public void testFailWhenNotValidPilesSupplied() {
		int rows = 3;
		int columns = 3;
		
		List<Pile> pile1 = buildFromArray(new int[] {
				1, 2, 3,
				4, 5, 6,
				7, 8, 
		});
		
		try {
			new GameBoard(rows, columns, pile1);
			Assert.fail("Expectine exception");
		} catch (IllegalArgumentException expected) {
			Assert.assertEquals("piles", expected.getMessage());
		}
		
		List<Pile> pile2 = buildFromArray(new int[] {
				1, 2, 0,
				4, 5, 6,
				7, 8, 0
		});
		
		try {
			new GameBoard(rows, columns, pile2);
			Assert.fail("Expectine exception");
		} catch (IllegalArgumentException expected) {
			Assert.assertEquals("Count <> 1 at position:0 is: 2; piles: [Pile [value=1], Pile [value=2], Pile [value=0], Pile [value=4], Pile [value=5], Pile [value=6], Pile [value=7], Pile [value=8], Pile [value=0]]", expected.getMessage());
		}
		
		List<Pile> pile3 = buildFromArray(new int[] {
				1, 2, 3,
				4, 5, 6,
				7, 8, 11
		});
		
		try {
			new GameBoard(rows, columns, pile3);
			Assert.fail("Expectine exception");
		} catch (IllegalArgumentException expected) {
			Assert.assertEquals("Pile has invalid value:Pile [value=11]", expected.getMessage());
		}
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testEligibleMoves() {
		int rows = 3;
		int columns = 3;
		
		List<Pile> pile1 = buildFromArray(new int[] {
				1, 2, 3,
				4, 5, 6,
				7, 8, 0
		});
		List<Pile> pile2 = buildFromArray(new int[] {
				1, 2, 3,
				4, 5, 0,
				7, 8, 6
		});
		List<Pile> pile3 = buildFromArray(new int[] {
				1, 2, 0,
				4, 5, 3,
				7, 8, 6
		});
		List<Pile> pile4 = buildFromArray(new int[] {
				1, 2, 5,
				4, 0, 3,
				7, 8, 6
		});
		List<Pile> pile5 = buildFromArray(new int[] {
				1, 0, 5,
				4, 2, 3,
				7, 8, 6
		});
		List<Pile> pile6 = buildFromArray(new int[] {
				0, 1, 5,
				4, 2, 3,
				7, 8, 6
		});
		List<Pile> pile7 = buildFromArray(new int[] {
				4, 1, 5,
				0, 2, 3,
				7, 8, 6
		});
		List<Pile> pile8 = buildFromArray(new int[] {
				4, 1, 5,
				7, 2, 3,
				0, 8, 6
		});
		List<Pile> pile9 = buildFromArray(new int[] {
				4, 1, 5,
				7, 2, 3,
				8, 0, 6
		});
		
		List<?>[] inputPiles = {
				pile1,
				pile2,
				pile3,
				pile4,
				pile5,
				pile6,
				pile7,
				pile8,
				pile9
		};
		
		Set<Pile> move1 = new HashSet<>();
		move1.add(new Pile(8));
		move1.add(new Pile(6));
		
		Set<Pile> move2 = new HashSet<>();
		move2.add(new Pile(3));
		move2.add(new Pile(5));
		move2.add(new Pile(6));
		
		Set<Pile> move3 = new HashSet<>();
		move3.add(new Pile(3));
		move3.add(new Pile(2));

		Set<Pile> move4 = new HashSet<>();
		move4.add(new Pile(2));
		move4.add(new Pile(4));
		move4.add(new Pile(8));
		move4.add(new Pile(3));
		
		Set<Pile> move5 = new HashSet<>();
		move5.add(new Pile(1));
		move5.add(new Pile(2));
		move5.add(new Pile(5));
	
		Set<Pile> move6 = new HashSet<>();
		move6.add(new Pile(1));
		move6.add(new Pile(4));

		Set<Pile> move7 = new HashSet<>();
		move7.add(new Pile(4));
		move7.add(new Pile(2));
		move7.add(new Pile(7));
		
		Set<Pile> move8 = new HashSet<>();
		move8.add(new Pile(8));
		move8.add(new Pile(7));
		
		Set<Pile> move9 = new HashSet<>();
		move9.add(new Pile(8));
		move9.add(new Pile(2));
		move9.add(new Pile(6));
		
		Set<?>[] expectedMoves = {
				move1,
				move2,
				move3,
				move4,
				move5,
				move6,
				move7,
				move8,
				move9	
		};
		
		Assert.assertEquals(inputPiles.length, expectedMoves.length);
		
		for(int i=0; i< inputPiles.length; i++) {
			
			GameBoard gameBoard = new GameBoard(rows, columns, (List<Pile>)inputPiles[i]);
			Set<Pile> actualEligibleMoves = gameBoard.eligibleMoves();
			Set<Pile> expectedMove = (Set<Pile>) expectedMoves[i];
			Assert.assertEquals("failed at:"+i, expectedMove, actualEligibleMoves);
		}
		
	}
		
	private List<Pile> buildFromArray(int[] values){
		List<Pile> piles = new ArrayList<>(values.length);
		for(int i = 0; i < values.length; i++) {
			piles.add(new Pile(values[i]));	
		}
		return piles;
	}
	
	private List<Pile> buildWinningPiles(int rows, int columns) {
		List<Pile> piles = new ArrayList<>();
		for(int i=1; i<rows*columns; i++) {
			piles.add(new Pile(i));
		}
		piles.add(Pile.EMPTY_PILE);
		
		Assert.assertEquals(rows*columns, piles.size());
		return piles;
	}
}
