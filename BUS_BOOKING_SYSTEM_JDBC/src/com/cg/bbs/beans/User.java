package com.cg.bbs.beans;

public class User {

/*	private Integer userId;
	private String username;
	private String email;
	private long contact;
	private String password;
*/

	private Integer userId;
	private String username;
	private String email;
	private long contact;
	private String password;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getContact() {
		return contact;
	}
	public void setContact(long contact) {
		this.contact = contact;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", email=" + email + ", contact=" + contact
				+ ", password=" + password + "]";
	}
	
}