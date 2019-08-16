package com.cg.bbs.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cg.bbs.beans.Available;
import com.cg.bbs.beans.Booking;
import com.cg.bbs.beans.Bus;
import com.cg.bbs.beans.Ticket;
import com.cg.bbs.beans.User;

public interface DAOUser {

	// User manipulation Methods
	public Boolean createUser(User user); // For create user operation

	Boolean updateUser(User user); // For update user operation

	public Boolean loginUser(Integer userId, String password); // For login of user

	public User searchUser(Integer userId); // For searching of user using user id

	public Boolean deleteUser(Integer userId, String password); // For delete operation of user

	// Ticket booking methods

	public Ticket bookTicket(Ticket ticket); // For booking tickets

	public Boolean cancelTicket(Integer bookingId, Integer userId); // For cancelling ticket

	Ticket getAllTicket( Integer userId); // For getting ticket
	

	// For checking availability from bus id and date
	public Integer checkAvailability(Integer busId, java.sql.Date availdate); // For checking availabillity

	// For checking availability from source,destination and date
	public List<Bus> checkAvailability(String source, String destination, java.sql.Date date);

	
	public User searchUser(String username); //For searching user by username
	
	public User searchUserEmail(String email); //For searching user by email
	
	public User searchContact(Long contact);
	

	public List<Bus> showBusesFromSource(String source);

	public List<Bus> showBusesByDestination(String destination);
	
	public List<String> showSources();
	
	public List<String> showDestination(String source);
	
	
	public Available searchAvailableBus(int busId);

	
	public List<String> date(String source,String destination);
	
	public List<Bus> searchBus(int busId);
	
	public Booking searchBookingId(int bookingId);

	public List<Integer> showBuses(String source, String destination, java.sql.Date date);

	Ticket getTicket(Integer bookingId, Integer userId);

	
}
