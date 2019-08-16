package com.cg.bbs.exception;

public class ContactExistsException extends RuntimeException{

	public ContactExistsException(String msg) {
		super(msg);
	}
}
