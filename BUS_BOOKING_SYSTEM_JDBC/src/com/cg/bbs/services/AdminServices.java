package com.cg.bbs.services;

import java.util.HashMap;

import com.cg.bbs.beans.Admin;
import com.cg.bbs.beans.Bus;

public interface AdminServices {
	// bus manipulations
	public Boolean createBus(Bus bus);

	public Boolean updateBus(Bus bus);

	public Bus searchBus(int busId);

	public Boolean deleteBus(int busId);

	public HashMap<Integer, Bus> busBetween(String source, String destination);

	// admin
	public Boolean adminLogin(Integer adminId, String password);
	public Admin searchAdmin(Integer adminId); // For search operation of admin

}
