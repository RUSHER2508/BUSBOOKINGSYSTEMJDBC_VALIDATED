package com.cg.bbs.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.cg.bbs.beans.Admin;
import com.cg.bbs.beans.Bus;
import com.cg.bbs.beans.User;

public class AdminDaoImpl implements AdminDaoBBS {

	// Database URL for connection here database name is busbookingsystem_db
	// username and password is root
	String url = "jdbc:mysql://localhost:3306/busbookingsystem_db?user=root&password=root";
	Connection conn;
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs = null;
	Bus bus = new Bus();

	@Override
	public Boolean createBus(Bus bus) {
		try {

			// Loading the driver
			java.sql.Driver div = new com.mysql.jdbc.Driver();

			// Registering the driver
			DriverManager.registerDriver(div);

			// Setting up the connection
			conn = DriverManager.getConnection(url);

			// Issuing the query
			String sql = "INSERT INTO BUS_INFO VALUES(?,?,?,?,?,?,?)";

			// Processing the results
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bus.getBusId());
			pstmt.setString(2, bus.getBusName());
			pstmt.setString(3, bus.getSource());
			pstmt.setString(4, bus.getDestination());
			pstmt.setString(5, bus.getBusType());
			pstmt.setInt(6, bus.getTotalSeats());
			pstmt.setDouble(7, bus.getPrice());
			int i = pstmt.executeUpdate();
			if (i > 0) {
				System.out.println("Done");
				return true;
			} else {
				System.out.println("error");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Finally block for closing resources
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

		return false;
	}

	@Override
	public Boolean updateBus(Bus bus) {

		// Issuing query
		String query = "UPDATE bus_info SET busname = ?,source = ?,destination = ?,bus_type = ?"
				+ "total_seats = ? , price = ? WHERE bus_id = ?";

		// Setting up connections
		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			// Processing results
			pstmt.setInt(7, bus.getBusId());
			pstmt.setString(1, bus.getBusName());
			pstmt.setString(2, bus.getSource());
			pstmt.setString(3, bus.getDestination());
			pstmt.setString(4, bus.getBusType());
			pstmt.setInt(5, bus.getTotalSeats());
			pstmt.setDouble(6, bus.getPrice());
			int i = pstmt.executeUpdate();
			if (i >= 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Bus searchBus(int busId) {

		// Issuing query
		String query = "SELECT * FROM BUS_INFO WHERE BUS_ID =?";
		ResultSet rs = null;
		Bus bus = null;

		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, busId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bus = new Bus();
				bus.setBusId(rs.getInt("bus_id"));


			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return bus;
	}

	@Override
	public Boolean deleteBus(int busId) {
		try {
			// load Driver
			java.sql.Driver div = new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(div);
			// GetConnection
			conn = DriverManager.getConnection(url);
			// Issue SQL quERY
			String q2 = "delete from bus_info where bus_id=?";
			pstmt = conn.prepareStatement(q2);
			pstmt.setInt(1, busId);

			int k = pstmt.executeUpdate();
			if (k > 0) {
				return true;
			} else {
				System.out.println("Something went wrong");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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

		return false;
	}

	@Override
	public HashMap<Integer, Bus> busBetween(String source, String destination) {
		Bus bus = new Bus();
		HashMap<Integer, Bus> map = new HashMap<>();

		try {
			java.sql.Driver div = new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(div);
			// GetConnection
			conn = DriverManager.getConnection(url);
			// IssueSql query
			String query = "Select * from bus_info where source=? and destination=? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, source);
			pstmt.setString(2, destination);
			rs = pstmt.executeQuery();
			int i = 1;
			while (rs.next()) {
				int busId = rs.getInt("bus_id");
				String busName = rs.getString("busname");
				String busSource = rs.getString("source");
				String busDestination = rs.getString("destination");
				String busType = rs.getString("bus_type");
				Double price = rs.getDouble("price");
				bus.setBusId(busId);
				bus.setBusName(busName);
				bus.setSource(busSource);
				bus.setDestination(busDestination);
				bus.setBusType(busType);
				bus.setPrice(price);
				map.put(i, bus);
				i++;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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

		return map;
	}

	@Override
	public Boolean adminLogin(Integer adminId, String password) {

		// Issuing query
		String query = "SELECT * FROM admin_info where admin_id=" + adminId + " and password='" + password + "'";

		// Setting up Connection
		try (Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();

				// Processing the result
				ResultSet rs = stmt.executeQuery(query)) {

			if (rs.next()) {
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
	public Admin searchAdmin(Integer adminId) {
		String query = "SELECT * FROM ADMIN_INFO WHERE ADMIN_ID =?";
		ResultSet rs = null;
		Admin admin = null;

		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, adminId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				admin = new Admin();

			admin.setAdminId(rs.getInt("admin_id"));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return admin;
	}
}
