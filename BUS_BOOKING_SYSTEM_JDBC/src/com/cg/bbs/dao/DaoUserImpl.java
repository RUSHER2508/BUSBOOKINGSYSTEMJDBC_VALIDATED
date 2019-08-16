package com.cg.bbs.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.cg.bbs.beans.Available;
import com.cg.bbs.beans.Booking;
import com.cg.bbs.beans.Bus;
import com.cg.bbs.beans.Ticket;
import com.cg.bbs.beans.User;

public class DaoUserImpl implements DAOUser {

	// Loading the Driver
	public DaoUserImpl() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Database URL for connection here database name is busbookingsystem_db
	// username and password is root
	String url = "jdbc:mysql://localhost:3306/busbookingsystem_db" + "?user=root&password=root";

	@Override
	public Boolean createUser(User user) {
		// Issue SQL Queries via Connection
		String query = "INSERT INTO USERS_INFO VALUES(?,?,?,?,?) ";

		// Registering the drivers and Setting up connections inside try with resources
		// block
		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			// Process the results returned by SQL Queries
			pstmt.setInt(1, user.getUserId());
			pstmt.setString(2, user.getUsername());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getPassword());
			pstmt.setLong(5, user.getContact());
			int i = pstmt.executeUpdate();
			if (i > 0) {
				System.out.println("Inserted Successfully");
				return true;
			} else {
				System.out.println("Something went wrong");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public Boolean updateUser(User user) {

		// Issue SQL Queries via Connection
		String query = "UPDATE USERS_INFO SET username = ?,email = ?,contact = ?"
				+ " WHERE user_id = ? AND password = ?";

		// Registering the drivers and Setting up connections inside try with resources
		// block
		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			// Process the results returned by SQL Queries
			pstmt.setInt(4, user.getUserId());
			pstmt.setLong(3, user.getContact());
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(5, user.getPassword());

			int i = pstmt.executeUpdate();
			if (i >= 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Boolean deleteUser(Integer userId, String password) {

		// First search if user is present or not
		User user = searchUser(userId);
		// Issue SQL Queries via Connection
		String query = "DELETE FROM USERS_INFO WHERE USER_ID=? AND PASSWORD=?";

		// Registering the drivers and Setting up connections inside try with resources
		// block
		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			// Process the results returned by SQL Queries
			pstmt.setInt(1, user.getUserId());
			pstmt.setString(2, user.getPassword());
			int i = pstmt.executeUpdate();
			if (i > 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public Boolean loginUser(Integer userId, String password) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// Loading the Driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// GetConnection
			conn = DriverManager.getConnection(url);

			// Issue SQL Query
			String query = "SELECT * FROM USERS_INFO WHERE USER_ID=? AND PASSWORD=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, userId);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();

			if (rs.next()) {

				return true;
			}
		}

		catch (Exception e) {

		}
		return false;
	}

	@Override
	public User searchUser(Integer userId) {
		String query = "SELECT * FROM USERS_INFO WHERE USER_ID =?";
		User user = null;
		ResultSet rs = null;

		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			while (rs.next()) {

				user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setContact(rs.getLong("contact"));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	@Override
	public Ticket bookTicket(Ticket ticket) {
		String query = " INSERT INTO booking_info (bus_id,user_id,journey_date,numofseats,booking_datetime)"
				+ " VALUES(?,?,?,?,?)";
		String passquery = "UPDATE availability SET avail_seats=? WHERE avail_date=? AND bus_id=?";
		int totalseats = checkAvailability(ticket.getBusId(),(java.sql.Date)ticket.getJourneyDate());
		try(Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
				PreparedStatement bookpstmt = conn.prepareStatement(passquery)){
			if(ticket.getNumofSeats()<=totalseats) {
			pstmt.setInt(1,ticket.getBusId());
			pstmt.setInt(2, ticket.getUserId());
			pstmt.setDate(3,(java.sql.Date) ticket.getJourneyDate() );
			pstmt.setInt(4, ticket.getNumofSeats());
			pstmt.setTimestamp(5, new java.sql.Timestamp(new java.util.Date().getTime()));
			
			
			int tick = pstmt.executeUpdate();
			 
			if(tick > -1){
				bookpstmt.setInt(1, totalseats - ticket.getNumofSeats());
				bookpstmt.setDate(2, (java.sql.Date)ticket.getJourneyDate());
				bookpstmt.setInt(3, ticket.getBusId());
				bookpstmt.executeUpdate();
				
				ResultSet rs = pstmt.getGeneratedKeys();
				if(rs.next()) {
				ticket.setBookingId(rs.getInt(1));}
				return ticket;
				}
		
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean cancelTicket(Integer bookingId, Integer userId) {
		Boolean state=false;
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		Scanner sc = new Scanner(System.in);
		
		try {
			java.sql.Driver div = new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(div);
			//GetConnection
			conn = DriverManager.getConnection(url);
			//Issue Query
			Ticket ticket=getTicket(bookingId, userId);
			int num=ticket.getNumofSeats();
			int busId=ticket.getBusId();
			System.out.println(ticket);
			{
				String query="Delete from booking_info where booking_id=?";
				pstmt=conn.prepareStatement(query);
				pstmt.setInt(1,bookingId);
				int update=pstmt.executeUpdate();
				int available;
				if(update>0)
				{
					String q1="Select * from availability where bus_id=?";
					pstmt=conn.prepareStatement(q1);
					pstmt.setInt(1, busId);
					rs=pstmt.executeQuery();
					if(rs.next())
					{
						available=rs.getInt("avail_seats");
						int availableNew=available+num;
						String q2="Update availability set avail_seats=? where bus_id=?";
						pstmt=conn.prepareStatement(q2);
						pstmt.setInt(2, busId);
						pstmt.setInt(1, availableNew);
						int inc= pstmt.executeUpdate();
						if(inc>0)
						{
							state=true;
						}
					}
				}
			}
		
} catch (SQLException e) {
	e.printStackTrace();
}



		return state;
			
	}

	@Override
	public Ticket getAllTicket(Integer userId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// Setting up Connection
			conn = DriverManager.getConnection(url);

			// Issuing the query
			String q2 = "select * from booking_info inner join bus_info on booking_info.bus_id = bus_info.bus_id where user_id=? ";

			// Processing the results
			pstmt = conn.prepareStatement(q2);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			while (rs.next()) {

				Integer bookingId1 = rs.getInt("booking_id");
				Integer busId = rs.getInt("bus_id");
				Integer userId1 = rs.getInt("user_id");
				Date date = rs.getDate("journey_date");
				Integer seats = rs.getInt("numofseats");
				String source = rs.getString("source");
				String destination = rs.getString("destination");
				Ticket ticket = new Ticket();

				Bus bus = new Bus();
				ticket.setBookingId(bookingId1);
				ticket.setBusId(busId);
				ticket.setUserId(userId1);
				ticket.setJourneyDate(date);
				ticket.setNumofSeats(rs.getInt("numofseats"));
				ticket.setSource(source);
				ticket.setDestination(destination);
				return ticket;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		// finally block to close resources
		finally {
			if (conn != null) {
				try {
					conn.close();
					if (pstmt != null) {
						pstmt.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public Integer checkAvailability(Integer busId, Date date) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// Loading the driver
			java.sql.Driver div = new com.mysql.jdbc.Driver();

			// Registering the driver
			DriverManager.registerDriver(div);

			// Setting up Connection
			conn = DriverManager.getConnection(url);

			// Issuing query
			String q1 = "Select * from availability where bus_id=? and avail_date=?";

			// Processing results
			pstmt = conn.prepareStatement(q1);
			pstmt.setInt(1, busId);
			pstmt.setDate(2, date);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int available = rs.getInt("avail_seats");
				return available;
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		// finally block to close resources
		finally {
			if (conn != null) {
				try {
					conn.close();
					if (pstmt != null) {
						pstmt.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;

	}

	@Override
	public List<Bus> checkAvailability(String source, String destination, Date date) {

		// List for storing and retreving data in form of List
		List<Bus> list = new ArrayList<Bus>();
		Bus bus = null;
		ResultSet rs = null;

		// Issuing query
		String query = "select * from bus_info INNER JOIN availability"
				+ "  ON bus_info.bus_id=availability.bus_id where source=? AND destination=?" + " AND avail_date=?";

		// Setting up connections
		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, source);
			pstmt.setString(2, destination);
			pstmt.setDate(3, date);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bus = new Bus();
				bus.setBusId(rs.getInt("bus_id"));
				bus.setBusName(rs.getString("busname"));
				bus.setSource(rs.getString("source"));
				bus.setDestination(rs.getString("destination"));
				bus.setBusType(rs.getString("bus_type"));
				bus.setTotalSeats(rs.getInt("total_seats"));
				bus.setPrice(rs.getDouble("price"));

				list.add(bus);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public User searchUser(String username) {
		String query = "SELECT USERNAME FROM USERS_INFO WHERE USERNAME =?";
		User user = null;
		ResultSet rs = null;

		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			while (rs.next()) {

				user = new User();
				user.setUsername(rs.getString("username"));
				return user;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public User searchUserEmail(String email) {
		String query = "SELECT EMAIL FROM USERS_INFO WHERE EMAIL =?";
		User user = null;
		ResultSet rs = null;

		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			while (rs.next()) {

				user = new User();
				user.setEmail(rs.getString("email"));
				return user;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public User searchContact(Long contact) {
		String query = "SELECT CONTACT FROM USERS_INFO WHERE CONTACT =?";
		User user = null;
		ResultSet rs = null;

		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setLong(1, contact);
			rs = pstmt.executeQuery();
			while (rs.next()) {

				user = new User();
				user.setContact(rs.getLong("contact"));
				return user;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public List<Bus> showBusesFromSource(String source) {
		List<Bus> list = new ArrayList<Bus>();
		String query = "SELECT * FROM BUS_INFO WHERE SOURCE =?";
		User user = null;
		ResultSet rs = null;

		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, source);
			rs = pstmt.executeQuery();
			while (rs.next()) {

				Bus bus = new Bus();
				bus.setBusId(rs.getInt("bus_id"));
				bus.setBusName(rs.getString("busname"));
				bus.setSource(rs.getString("source"));
				bus.setDestination(rs.getString("destination"));
				bus.setBusType(rs.getString("bus_type"));
				bus.setTotalSeats(rs.getInt("total_seats"));
				bus.setPrice(rs.getDouble("price"));
				list.add(bus);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	@Override
	public List<Bus> showBusesByDestination(String destination) {
		List<Bus> list = new ArrayList<Bus>();
		String query = "SELECT * FROM BUS_INFO WHERE DESTINATION =?";
		User user = null;
		ResultSet rs = null;

		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, destination);
			rs = pstmt.executeQuery();
			while (rs.next()) {

				Bus bus = new Bus();
				bus.setBusId(rs.getInt("bus_id"));
				bus.setBusName(rs.getString("busname"));
				bus.setSource(rs.getString("source"));
				bus.setDestination(rs.getString("destination"));
				bus.setBusType(rs.getString("bus_type"));
				bus.setTotalSeats(rs.getInt("total_seats"));
				bus.setPrice(rs.getDouble("price"));
				list.add(bus);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	@Override
	public List<String> showSources() {
		List<String> list = new ArrayList<String>();
		String query = "SELECT SOURCE FROM BUS_INFO";
		ResultSet rs = null;

		try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
			rs = stmt.executeQuery(query);

			while (rs.next()) {

				String source = rs.getString("source");
				list.add(source);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	@Override
	public List<String> showDestination(String source) {
		List<String> list = new ArrayList<String>();
		String query = "SELECT DESTINATION FROM BUS_INFO WHERE  SOURCE=?";
		User user = null;
		ResultSet rs = null;

		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, source);
			rs = pstmt.executeQuery();
			while (rs.next()) {

				String destination = rs.getString("destination");
				list.add(destination);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	@Override
	public Available searchAvailableBus(int busId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> date(String source, String destination) {
		List<String> list = new ArrayList<String>();

		String query1 = "SELECT AVAIL_DATE FROM AVAILABILITY  INNER JOIN BUS_INFO ON AVAILABILITY.BUS_ID=BUS_INFO.BUS_ID WHERE SOURCE=? AND DESTINATION=?";
		User user = null;
		ResultSet rs = null;

		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query1)) {

			pstmt.setString(1, source);
			pstmt.setString(2, destination);
			rs = pstmt.executeQuery();
			while (rs.next()) {

				String date = rs.getString("avail_date");
				list.add(date);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Bus> searchBus(int busId) {
		String query = "SELECT * FROM BUS_INFO WHERE BUS_ID =?";
		List<Bus> list = new ArrayList<Bus>();

		ResultSet rs = null;

		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, busId);
			rs = pstmt.executeQuery();
			while (rs.next()) {

				Bus bus = new Bus();
				bus.setBusId(rs.getInt("bus_id"));
				list.add(bus);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

	@Override
	public Booking searchBookingId(int bookingId) {

		Booking booking = null;
		String query = "SELECT * FROM BOOKING_INFO WHERE BOOKING_ID =?";
		ResultSet rs = null;

		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, bookingId);
			rs = pstmt.executeQuery();
			while (rs.next()) {

				booking = new Booking();
				booking.setBookingId(rs.getInt("booking_id"));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return booking;

	}

	@Override
	public List<Integer> showBuses(String source, String destination, Date date) {
		List<Integer> list = new ArrayList<Integer>();

		String query = "select * from bus_info INNER JOIN availability"
				+ "  ON bus_info.bus_id=availability.bus_id where source=? AND destination=?" + " AND avail_date=?";
		ResultSet rs = null;

		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, source);
			pstmt.setString(2, destination);
			pstmt.setDate(3, date);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				
				int busId = rs.getInt("bus_id");
				list.add(busId);


			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Ticket getTicket(Integer bookingId, Integer userId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn=DriverManager.getConnection(url);
			
			String q2="select * from booking_info inner join bus_info on booking_info.bus_id = bus_info.bus_id where booking_id=? and user_id=? ";
			
			pstmt=conn.prepareStatement(q2);
			pstmt.setInt(1,bookingId);
			pstmt.setInt(2,userId);
			rs = pstmt.executeQuery();
			while(rs.next()) {

				Integer bookingId1 = rs.getInt("booking_id");
				Integer busId = rs.getInt("bus_id");
				Integer userId1 = rs.getInt("user_id");
				Date date = rs.getDate("journey_date");
				Integer seats = rs.getInt("numofseats");
				String source = rs.getString("source");
				String destination = rs.getString("destination");
				Ticket ticket =new Ticket();

				ticket.setBookingId(bookingId1);
				ticket.setBusId(busId);
				ticket.setUserId(userId1);
				ticket.setJourneyDate(date);
				ticket.setNumofSeats(rs.getInt("numofseats"));
				ticket.setSource(source);
				ticket.setDestination(destination);
				return ticket;
			}

		}
		catch(Exception e) {
			e.printStackTrace();

		}	
		return null;
	}


}
