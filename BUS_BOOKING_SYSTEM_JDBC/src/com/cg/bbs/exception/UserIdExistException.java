package com.cg.bbs.exception;

public class UserIdExistException extends RuntimeException {

	public UserIdExistException(String msg) {
		
		super(msg);
	}
}
