package com.sample.game15.logic;

public final class Coordinate {
	private final int row;
	private final int column;
	
	private Coordinate(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public static Coordinate of(int row, int column) {
		return new Coordinate(row, column);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Coordinate [row=" + row + ", column=" + column + "]";
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
	
}
