package com.sample.game15.view;

import com.sample.game15.logic.Coordinate;
import com.sample.game15.logic.GameBoard;
import com.sample.game15.logic.Pile;

public class GameBoardView {
	private final GameBoard gameBoard;

	public GameBoardView(final GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}
	
	public String view() {
		StringBuilder sb = new StringBuilder();
		
		int rows = this.gameBoard.getRowCount();
		int columns = this.gameBoard.getColumnCount();
		
		for (int row=0; row<rows; row++) {
			sb.append("(");
			for (int column=0; column<columns; column++) {
				Pile pile =  this.gameBoard.getPileAt(Coordinate.of(row, column));
				String pileView = getPileView(pile);
				if (pileView.length() < 2) {
					sb.append(' ');
				}
				sb.append(pileView);
				if (column != columns-1) {
					sb.append(' ');
					sb.append('|');
				}
			}
			sb.append(")");
			sb.append("\n");
		}

		return sb.toString();
	}
	
	private String getPileView(Pile pile) {
		if (pile == null) {
			return "<null>";
		}
		if (pile.getValue() == 0) {
			return  " ";
		}
		return String.valueOf(pile.getValue());
	} 
	
}
