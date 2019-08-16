package com.cg.bbs.exception;

public class UserEmailAlreadyExist extends  RuntimeException{

	public UserEmailAlreadyExist(String msg) {
		super(msg);
	}
}
