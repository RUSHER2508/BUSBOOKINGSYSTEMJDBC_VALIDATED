package com.cg.bbs.dao;

import java.util.HashMap;

import com.cg.bbs.beans.Admin;
import com.cg.bbs.beans.Bus;

public interface AdminDaoBBS {

	// Bus manipulations
	public Boolean createBus(Bus bus); // For create operation of bus

	public Boolean updateBus(Bus bus); // For update operation of bus

	public Bus searchBus(int busId); // For search operation using bus id

	public Boolean deleteBus(int busId); // For delete operation

	// For searching bus between source and destination
	public HashMap<Integer, Bus> busBetween(String source, String destination);

	// Admin
	public Boolean adminLogin(Integer adminId, String password); // For admin login

	public Admin searchAdmin(Integer adminId); // For search operation of admin

}
