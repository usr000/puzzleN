package com.sample.game15.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameBoardGenerator {
	
	private final int rowCount;
	private final int columnCount;
	private final long seed; 
		
	public GameBoardGenerator(int rows, int columns, Long seed) {
		this.rowCount = rows;
		this.columnCount = columns;
		if (seed != null) {
			this.seed = seed;
		} else {
			this.seed = generateSeed();
		}
	}

	private Long generateSeed() {
		return new Random().nextLong();
	}

	public GameBoard generate() {
		
		int maxPileValue = (this.rowCount * this.columnCount) - 1;
		if (maxPileValue > 1) {
			List<Integer> pileValues = new ArrayList<>(maxPileValue + 1);
			
			for(int i=1; i<= maxPileValue; i++) {
				pileValues.add(i);
			}
			
			List<Pile> piles = new ArrayList<>(pileValues.size());
			for(Integer pileValue : pileValues) {
				piles.add(new Pile(pileValue));
			}
			piles.add(Pile.EMPTY_PILE);
			
			Random rndWithSeed = new Random(this.seed);
			Collections.shuffle(piles, rndWithSeed);
			
			GameBoard gb = new GameBoard(this.rowCount, this.columnCount, piles);
			return gb;
		} else {
			throw new IllegalStateException("maxPileValue is too small:" + maxPileValue);
		}
		

	}

	public Long getSeed() {
		return seed;
	}
	
}
