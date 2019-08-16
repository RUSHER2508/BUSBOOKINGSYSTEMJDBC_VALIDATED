package com.cg.bbs.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cg.bbs.beans.Available;
import com.cg.bbs.beans.Booking;
import com.cg.bbs.beans.Bus;
import com.cg.bbs.beans.Ticket;
import com.cg.bbs.beans.User;

public interface UserServices {
	// user manipulation
	public Boolean createUser(User user);

	Boolean updateUser(User user);

	public Boolean loginUser(Integer userId, String password);

	public User searchUser(Integer userId);

	public Boolean deleteUser(Integer userId, String password);

	// ticket booking
	public Ticket bookTicket(Ticket ticket);

	public Boolean cancelTicket(Integer bookingId, Integer userId);

	Ticket getTicket(Integer bookingId, Integer userId);

	public Integer checkAvailability(Integer busId, java.sql.Date availdate);

	public List<Bus> checkAvailability(String source, String destination, java.sql.Date date);

	String checkemail(String email);

	Long checkContact(String contact);

	Integer idCheck(String id);

	boolean nameCheck(String name);

	Long regexcontact(String contact);
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

	public String checkDate(String date);
	Ticket getAllTicket( Integer userId); // For getting ticket



}
