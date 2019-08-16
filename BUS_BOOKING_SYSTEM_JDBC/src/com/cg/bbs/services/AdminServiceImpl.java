package com.cg.bbs.services;

import java.util.HashMap;

import com.cg.bbs.beans.Admin;
import com.cg.bbs.beans.Bus;
import com.cg.bbs.dao.AdminDaoBBS;
import com.cg.bbs.dao.AdminDaoImpl;

public class AdminServiceImpl implements AdminServices{

	AdminDaoBBS daoadmin = new AdminDaoImpl();
	@Override
	public Boolean createBus(Bus bus) {
		return daoadmin.createBus(bus);
	}

	@Override
	public Boolean updateBus(Bus bus) {
		return daoadmin.updateBus(bus);
	}

	@Override
	public Bus searchBus(int busId) {
		return daoadmin.searchBus(busId);
	}

	@Override
	public Boolean deleteBus(int busId) {
		return daoadmin.deleteBus(busId);
	}

	@Override
	public HashMap<Integer, Bus> busBetween(String source, String destination) {
		return daoadmin.busBetween(source, destination);
	}


	@Override
	public Boolean adminLogin(Integer adminId, String password) {
		return daoadmin.adminLogin(adminId, password);
	}

	@Override
	public Admin searchAdmin(Integer adminId) {
		return daoadmin.searchAdmin(adminId);
	}


}
