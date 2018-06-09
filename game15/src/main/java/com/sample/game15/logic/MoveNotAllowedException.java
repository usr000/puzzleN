package com.sample.game15.logic;

public class MoveNotAllowedException extends GameException {

	private static final long serialVersionUID = -8597073856084846500L;

	public MoveNotAllowedException(String message) {
		super(message);
	}

}
