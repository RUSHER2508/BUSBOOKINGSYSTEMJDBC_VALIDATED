package com.cg.bbs.beans;

public class Admin {

	private int adminId;
	private String password;
	public int adminId() {
		return adminId;
	}
	
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Admin [adminId=" + adminId + ", password=" + password + "]";
	}
	
	
}
