package com.sample.game15.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameBoard {
	private final int rowCount;
	private final int columnCount;
	private final List<Pile> piles;
	
	public GameBoard(final int rows, final int columns, final List<Pile> piles) {

		if (rows < 2) {
			throw new IllegalArgumentException("Too small rows: " + rows);
		}
		this.rowCount = rows;
		
		if(columns < 2) {
			throw new IllegalArgumentException("Too small columns: " + columns);
		}
		this.columnCount = columns;
		
		if (piles != null && piles.size() == rows * columns) {
			this.validatePileValues(rows, columns, piles);
			this.piles = new ArrayList<>(piles);
		} else {
			throw new IllegalArgumentException("piles");
		}
		
	}

	private void validatePileValues(final int rows, final int columns, final List<Pile> pilesToValidate) {
		int[] valuesToCounts = new int[rows*columns];
		
		for(int i=0; i<pilesToValidate.size(); i++) {
			Pile p = pilesToValidate.get(i);
			int value = p.getValue();
			if (value >= 0 && value < rows*columns) {
				valuesToCounts[value]++;
			} else {
				throw new IllegalArgumentException("Pile has invalid value:"  + p);
			}
		}
		
		for (int i = 0; i < valuesToCounts.length; i++) {
			int count = valuesToCounts[i];
			if (count != 1) {
				throw new IllegalArgumentException("Count <> 1 at position:"  + i + " is: " + count + "; piles: "+ pilesToValidate );
			}
		}
	}

	public Pile getPileAt(final Coordinate coord) {
		int row = coord.getRow();
		int column = coord.getColumn();
		
		if (! (row < this.rowCount && row >=0)) {
			throw new IllegalArgumentException("requested illegal row:" + row);
		}
		if (! (column < this.columnCount && column >=0)) {
			throw new IllegalArgumentException("requested illegal column:" + column);
		}
		
		int idx = row*this.columnCount + column;
		
		Pile pile = this.piles.get(idx);
		
		return pile;
	}

	// since all Pile's values are unique we can take advantage of this in the implementation below
	public void movePile(final Pile pileToMove) throws GameException {

		Set<Pile> moves = this.eligibleMoves();
		if (moves.contains(pileToMove)) {
			
			int pileIdx = this.pileIndex(pileToMove);
			if (pileIdx == -1) {
				throw new IllegalArgumentException("Unknown pile:" + pileToMove);
			}
			
			int emptyPileIdx = this.pileIndex(Pile.EMPTY_PILE);
			
			doMove(pileIdx, emptyPileIdx);
			
		} else {
			throw new MoveNotAllowedException("Move is not allowed: " + pileToMove);
		}	
	}
	
	public boolean hasUserWon() {
		int size = this.piles.size();
		if (size > 0) {
			if (Pile.EMPTY_PILE.equals(this.piles.get(size-1))) {
				for(int i=0; i < size-1; i++) {
					if (i+1 != this.piles.get(i).getValue()) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
	
	public Set<Pile> eligibleMoves() {
		int emptyPileIdx = this.pileIndex(Pile.EMPTY_PILE);
		Coordinate emptyPileCoordinate = fromPileIndex(emptyPileIdx);
		
		Set<Pile> eligibleMoves = new HashSet<>();
		if (emptyPileCoordinate.getRow() > 0 ) {
			// UP is possible
			int pileRow = emptyPileCoordinate.getRow() - 1;
			int pileColumn = emptyPileCoordinate.getColumn();
			Pile p = getPileAt(Coordinate.of(pileRow, pileColumn));
			eligibleMoves.add(p);
		}
		
		if (emptyPileCoordinate.getColumn() > 0 ) {
			// LEFT is possible
			int pileRow = emptyPileCoordinate.getRow();
			int pileColumn = emptyPileCoordinate.getColumn() - 1;
			Pile p = getPileAt(Coordinate.of(pileRow, pileColumn));
			eligibleMoves.add(p);
		}
		
		if (emptyPileCoordinate.getRow() < this.rowCount-1 ) {
			// DOWN is possible
			int pileRow = emptyPileCoordinate.getRow() + 1;
			int pileColumn = emptyPileCoordinate.getColumn();
			Pile p = getPileAt(Coordinate.of(pileRow, pileColumn));
			eligibleMoves.add(p);
		}
		
		if (emptyPileCoordinate.getColumn() < this.columnCount-1 ) {
			// RIGHT is possible
			int pileRow = emptyPileCoordinate.getRow();
			int pileColumn = emptyPileCoordinate.getColumn() + 1;
			Pile p = getPileAt(Coordinate.of(pileRow, pileColumn));
			eligibleMoves.add(p);
		}
				
		return eligibleMoves;
	}
	
	private void doMove(final int fromIdx, final int toIdx) {
		Pile src = this.piles.get(fromIdx);
		Pile target = this.piles.get(toIdx);
		
		this.piles.set(fromIdx, target);
		this.piles.set(toIdx, src);
	}

	private Coordinate fromPileIndex(int index) {
		int row = index / this.columnCount;
		int column = index % this.columnCount;
		return Coordinate.of(row, column);
	}
	
	private int pileIndex(final Pile pile) {
		// because of the actual small size it's OK that's O(n)
		return piles.indexOf(pile);
	}
	
	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

}
