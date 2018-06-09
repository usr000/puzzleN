package com.sample.game15.logic;

public abstract class GameException extends Exception {

	private static final long serialVersionUID = -82454742861913498L;

	protected GameException() {
	}

	public GameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	protected GameException(String message, Throwable cause) {
		super(message, cause);
	}

	protected GameException(String message) {
		super(message);
	}

	protected GameException(Throwable cause) {
		super(cause);
	}

}
