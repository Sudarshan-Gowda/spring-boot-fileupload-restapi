package com.star.sud.exception;

public class FileStorageException extends RuntimeException {

	private static final long serialVersionUID = -4245846472490133078L;

	public FileStorageException(String message) {
		super(message);
	}

	public FileStorageException(String message, Throwable cause) {
		super(message, cause);
	}
}