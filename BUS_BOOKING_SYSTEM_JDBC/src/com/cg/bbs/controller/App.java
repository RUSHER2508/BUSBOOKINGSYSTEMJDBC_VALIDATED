package com.cg.bbs.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.cg.bbs.beans.Available;
import com.cg.bbs.beans.Bus;
import com.cg.bbs.beans.Ticket;
import com.cg.bbs.beans.User;
import com.cg.bbs.exception.BusCreateFailException;
import com.cg.bbs.exception.BusDeleteFailException;
import com.cg.bbs.exception.BusNotFoundException;
import com.cg.bbs.exception.DeleteUserException;
import com.cg.bbs.exception.LoginException;
import com.cg.bbs.exception.NotIntegerFormatException;
import com.cg.bbs.exception.RegisterException;
import com.cg.bbs.exception.TicketBookingException;
import com.cg.bbs.exception.UpdateException;
import com.cg.bbs.services.AdminServiceImpl;
import com.cg.bbs.services.AdminServices;
import com.cg.bbs.services.UserServices;
import com.cg.bbs.services.UserServicesImpl;

public class App {
	// Declaring variables and methods as static
	static Integer userId;
	static Integer adminId;
	static String password;
	static Integer bookingId;
	static UserServices services = new UserServicesImpl();
	static AdminServices adminservices = new AdminServiceImpl();
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		Boolean loop = true;
		while (loop) {

			// the MAIN MENU//
			System.out.println("*********************************************");
			System.out.println("****      RUSHER BUS BOOKING SYSTEM      ****");
			System.out.println("*********************************************");
			System.out.println("** [1] Login                               **");
			System.out.println("** [2] Create Profile                      **");
			System.out.println("** [3] Admin Login                         **");
			System.out.println("** [4] Exit                                **");
			System.out.println("*********************************************");
			System.out.println("*********************************************");
			System.out.println("Enter your choice");
			int firstChoice = sc.nextInt();
			Boolean login = false;
			if (firstChoice == 1) {

				try {
					login = loginUser();
				} catch (LoginException e) {
					e.printStackTrace();
				}
				if (login) {
					System.out.println("Login Successful");
					boolean loop1 = true;
					while (loop1) {
						System.out.println("** [1] Search Profile               **");
						System.out.println("** [2] Update Profile               **");
						System.out.println("** [3] Delete Profile               **");
						System.out.println("** [4] Book Ticket                  **");
						System.out.println("** [5] Cancel Ticket                **");
						System.out.println("** [6] Get Ticket                   **");
						System.out.println("** [7] Check availability           **");
						System.out.println("** [8] Exit                         **");

						System.out.println("***************************************");
						System.out.println("***************************************\n");
						{
							System.out.print("ENTER CHOICE: ");
							Integer choice = sc.nextInt();

							if (choice == 1) {
								try {
									searchUser();
								} catch (Exception e) {
									e.printStackTrace();
								}
							} else if (choice == 2) {
								try {
									updateUser();
								} catch (UpdateException e) {
									e.printStackTrace();
								}
							} else if (choice == 3) {
								try {
									deleteUser();
								} catch (DeleteUserException e) {
									e.printStackTrace();
								}
							}

							else if (choice == 4) {
								try {
									bookTicket();
								} catch (TicketBookingException e) {
									e.printStackTrace();
								}
							} else if (choice == 5) {
								cancelTicket();
							} else if (choice == 6) {
								getTicket();
							}

							else if (choice == 7) {
								checkAvailability();
							} else if (choice == 8) {
								System.out.println("Thank you for visiting");
								loop1 = false;
							} else {
								System.out.println("Login Failed");
								loop1 = false;
							}

						}

					}

				}
			} else if (firstChoice == 2) {
				try {
					createUser();
				} catch (RegisterException e) {
					e.printStackTrace();
				}

			} else if (firstChoice == 3) {
				Boolean admin = false;
				try {
					admin = adminLogin();
				} catch (LoginException e) {
					e.printStackTrace();
				}
				if (admin) {
					System.out.println("Login Succesful");
					boolean loop2 = true;
					while (loop2) {
						System.out.println("** [1] Create bus               **");
						System.out.println("** [2] Update bus               **");
						System.out.println("** [3] Search bus               **");
						System.out.println("** [4] Delete bus               **");
						System.out.println("** [5] Search between source and destination        **");
						System.out.println("** [6] Exit                                **");

						System.out.println("***************************************");
						System.out.println("***************************************\n");

						System.out.print("ENTER CHOICE: ");
						Integer choice = sc.nextInt();
						if (choice == 1) {
							try {
								createBus();
							} catch (BusCreateFailException e) {
								e.printStackTrace();
							}
						} else if (choice == 2) {
							try {
								updateBus();
							} catch (UpdateException e) {
								e.printStackTrace();
							}
						} else if (choice == 3) {
							try {
								searchBus();
							} catch (BusNotFoundException e) {
								e.printStackTrace();
							}
						} else if (choice == 4) {
							try {
								deleteBus();
							} catch (BusDeleteFailException e) {
								e.printStackTrace();
							}
						} else if (choice == 5) {
							searchBusBetween();
						} else if (choice == 6) {
							System.out.println("Thank you for visiting");
							loop2 = false;
						} else {
							System.out.println("Unsuccessfull");
						}

					}

				}

			} else if (firstChoice == 4) {
				loop = false;
				System.err.println("Thank you for visiting");

			}

		}

	}

	// For updating user
	private static void updateUser() throws UpdateException {
		User user = new User();
		user.setUserId(userId); // Setting up userId
		user.setPassword(password); // Setting up password
		System.out.println("Enter New Username");
		user.setUsername(sc.next());
		System.out.println("Enter New Email");
		user.setEmail(sc.next());
		System.out.println("Enter New Contact");
		user.setContact(sc.nextLong());

		boolean b = services.updateUser(user); // Invoking the updateUser()
		if (b) {
			// if true
			System.out.println("SuccessFully Updated");
		} else {
			// if false
			// Customized exception
			throw new UpdateException("Updation Fail Exception");
		}

	}

	// For Login of user
	private static boolean loginUser() throws LoginException {

		boolean checkLogin = true;
		while (checkLogin) {
			System.out.println("Enter userid:");
			// Checking if input is in Integer format or not by idCheck()
			Integer tempId = services.idCheck(sc.next());
			if (tempId != null) {
				userId = tempId;
				checkLogin = false;
			} else {
				System.err.print("User id should be number !");
				throw new NotIntegerFormatException(password);

			}
		}
		System.out.println("Enter password:");
		password = sc.next();
		Boolean login = services.loginUser(userId, password); // Invoking the loginUser()
		if (login) {
			// if true
			return true;
		} else {
			// if false
			throw new LoginException("Login Failed Exception");
		}

	}

	// For Profile delete of user
	private static void deleteUser() throws DeleteUserException {

		if (services.deleteUser(userId, password)) // Invoking deleteUser()
		{
			// if true
			System.out.println("Profile sucessfully Deleted");
		} else {

			// if false throw customised exception
			throw new DeleteUserException("User Profile deletion Failed");
		}
	}

	// For searching bus
	private static void searchBus() throws BusNotFoundException {
		boolean busCheck = true;
		Integer busId = 0;
		while (busCheck) {
			System.out.println("Enter BusId");

			// Checking if input is in Integer format or not
			Integer tempId = services.idCheck(sc.next());
			if (tempId != null) {
				busId = tempId;
				busCheck = false;
			} else {
				System.err.println("User id should be number !");
				throw new NotIntegerFormatException(password);

			}
			Bus b = adminservices.searchBus(busId); // Calling searchBus()
			System.out.println(b);

		}

	}

	// For searching of user
	private static void searchUser() {

		// Calling searchUser()
		User search = services.searchUser(userId);
		System.out.println(search); // For printing of the search result

	}

	// For checking the availability of Bus
	private static void checkAvailability() {

		Available available = new Available();
		System.out.println("Enter Source point");
		String source = sc.next();
		System.out.println("Enter Destination point");
		String destination = sc.next();
		System.out.println("Enter Date (YYYY-MM-DD)");
		String tempDate = sc.next();
		Date date = Date.valueOf(tempDate); // Converting java.sql date to java.util date
		available.setAvailableDate(date); // Setting up the date
		// Calling the checkAvailability() and storing in List format
		List<Bus> list = services.checkAvailability(source, destination, date);

		// Iterator for retreiving values
		for (Bus bs : list) {
			System.out.println(bs); // Printing the buses
			// Calling the checkAvailabilty()
			int avail = services.checkAvailability(bs.getBusId(), date);
			System.out.println("Available Seats:" + avail); // Printing the available Seats
		}

	}

	// For Booking the ticket
	private static void bookTicket() throws TicketBookingException {
		Ticket ticket = new Ticket();
		Bus bus = new Bus();

		System.out.println("Enter source point");
		String checksource = sc.next();
		System.out.println("Enter Destination point");
		String checkdestination = sc.next();
		System.out.println("Enter date of journey(yyyy-mm-dd)");
		String tempDate = sc.next();
		Date date = Date.valueOf(tempDate); // Converting java.sql Date to java.util Date
		ticket.setJourneyDate(date); // Setting the journeyDate

		// Calling the checkAvailability() and storing in List format
		List<Bus> list = services.checkAvailability(checksource, checkdestination, date);

		// Iterator for retrieving the values
		for (Bus bs : list) {

			System.out.println(bs);
			int avail = services.checkAvailability(bs.getBusId(), date);
			System.out.println("Available Seats:" + avail);
		}

		System.out.println("Enter the bus id");
		int busId = sc.nextInt();
		ticket.setBusId(busId);

		ticket.setUserId(userId);

		// Calling the checkAvailabilty()
		Integer availSeats = services.checkAvailability(busId, date);
		if (availSeats != null) {
			System.out.println("Total available seats are: " + availSeats);
		}

		System.out.println("Enter number of seats to book");
		ticket.setNumofSeats(sc.nextInt());
		for (Bus bs : list) {
		 bus.setPrice(bs.getPrice());
		 

		}


		Ticket bookTicket = services.bookTicket(ticket);
		if (bookTicket != null) {
			System.out.println("Ticket sucessfully Booked");
			System.out.println(services.getTicket(ticket.getBookingId(), userId));
		} else {
			System.err.println("Failed to book Ticket");
			throw new TicketBookingException("Ticket Booking Fail Exception");
		}
	}

	// For getting the ticket info
	private static void getTicket() {

		boolean busCheck = true;
		Integer bookingId = 0;
		while (busCheck) {
			System.out.println("Enter BookingId");

			// Checking if input is in Integer format or not
			Integer tempId = services.idCheck(sc.next());
			if (tempId != null) {
				bookingId = tempId;
				busCheck = false;
			} else {
				System.err.println("Booking id should be number !");
				throw new NotIntegerFormatException(password);

			}

			Ticket ticket = services.getTicket(bookingId, userId);
			if (ticket != null) {
				System.out.println(ticket);
			} else {
				System.out.println("No Tickets Found");
			}
		}
	}

	// For cancelling the ticket
	private static void cancelTicket() {
		System.out.println("Enter BookingId");
		bookingId = sc.nextInt();
		// Calling the getTicket()
		Ticket ticket = services.getTicket(bookingId, userId);
		System.out.println(ticket);
		System.err.println("To confirm delete press yes");
		String decision = sc.next();
		if (decision.equalsIgnoreCase("yes")) {
			// Calling the cancelTicket()
			Boolean b = services.cancelTicket(bookingId, userId);

			if (b) {
				System.out.println("Ticket Successfully Cancelled");
			} else {
				System.err.println("No Tickets Found");
			}
		}

	}

	// For creating the user
	private static void createUser() throws RegisterException {
		User user = new User();
		boolean checkLogin = true;
		while (checkLogin) {
			System.out.println("Enter userid:");
			// Checking if input is in Integer format or not
			Integer tempId = services.idCheck(sc.next());
			if (tempId != null) {
				userId = tempId;
				user.setUserId(userId);
				checkLogin = false;
			} else {

				System.err.println("User id should be number !");
				throw new NotIntegerFormatException(password);

			}
		}
		System.out.println("Enter Username:");
		user.setUsername(sc.next());
		boolean checkEmail = true;
		while (checkEmail) {
			System.out.println("Enter Email:");
			// For checking the format of email
			String temp = services.checkemail(sc.next());
			if (temp != null) {
				user.setEmail(temp);
				checkEmail = false;
			} else {
				System.err.println("Wrong Email Format!! e.g(example@email.com)");
			}
		}

		boolean checkContact = true;
		while (checkContact) {
			System.out.println("Enter Contact No.:");
			// For checking the format of contact
			Long temp = services.regexcontact(sc.next());
			if (temp != null) {
				user.setContact(temp);
				checkContact = false;
			} else {
				System.err.println("Contact should be of 10 digits!!");
			}
		}
		System.out.println("Enter Password:");
		user.setPassword(sc.next());
		// calling createUser()
		boolean registration = services.createUser(user); // Calling createUser()
		if (registration) {
			System.out.println("Registration Successful");
		} else {
			throw new RegisterException("Registration Fail Exception");
		}

	}

	private static Boolean adminLogin() throws LoginException {

		System.out.println("Enter Admin id:");
		int adminid = Integer.parseInt(sc.next());
		System.out.println("Enter Admin password:");
		String password = sc.next();

		// Calling the adminLogin()
		if (adminservices.adminLogin(adminid, password)) {
			return true;
		} else {
			throw new LoginException("Admin Login Fail Exception");
		}
	}

	// For creating the bus
	private static void createBus() throws BusCreateFailException {
		Bus bus = new Bus();
		System.out.println("Enter Bus Id");
		bus.setBusId(Integer.parseInt(sc.next()));
		System.out.println("Enter BusName");
		bus.setBusName(sc.next());
		System.out.println("Enter Bus type");
		bus.setBusType(sc.next());
		System.out.println("Enter Source");
		bus.setSource(sc.next());
		System.out.println("Enter Destination");
		bus.setDestination(sc.next());
		System.out.println("Enter Total Seats");
		bus.setTotalSeats(Integer.parseInt(sc.next()));
		System.out.println("Enter Price");
		bus.setPrice(Double.parseDouble(sc.next()));

		// Calling createBus()
		boolean create = adminservices.createBus(bus);
		if (create) {
			// if true
			System.out.println("Bus created");
		} else {
			// if false then throw customised exception
			throw new BusCreateFailException("Fail to Create Bus Exception");
		}

	}

	// For updating bus info
	private static void updateBus() throws UpdateException {
		Bus bus = new Bus();
		System.out.println("Enter Bus Id");
		bus.setBusId(Integer.parseInt(sc.next()));
		System.out.println("Enter New BusName");
		bus.setBusName(sc.next());
		System.out.println("Enter New Source");
		bus.setSource(sc.next());
		System.out.println("Enter New Destination");
		bus.setDestination(sc.next());
		System.out.println("Enter New Bus type");
		bus.setBusType(sc.next());
		System.out.println("Enter New Total Seats");
		bus.setTotalSeats(Integer.parseInt(sc.next()));
		System.out.println("Enter New Price");
		bus.setPrice(Double.parseDouble(sc.next()));

		// calling the updateBus()
		boolean updateBus = adminservices.updateBus(bus);
		if (updateBus) {
			// if true
			System.out.println("Bus Successfully Updated");
		} else {
			// if false throw customized exception
			throw new UpdateException("Fail to Update Bus Exception");
		}
	}

	// For deleting bus
	private static void deleteBus() throws BusDeleteFailException {
		System.out.println("Enter Bus Id");
		int busId = Integer.parseInt(sc.next());
		// Calling deleteBus()
		boolean deleteBus = adminservices.deleteBus(busId);
		if (deleteBus) {
			// if true
			System.out.println("Bus Successfully Deleted");
		} else {
			// if false throw customised exception
			throw new BusDeleteFailException("Fail to Delete Bus Exception");
		}
	}

	// For searching bus
	private static void searchBusBetween() {
		System.out.println("Enter source");
		String source = sc.next();
		System.out.println("Enter the destination");
		String destination = sc.next();

		// Calling busBetween()
		HashMap<Integer, Bus> b = adminservices.busBetween(source, destination);

		// Printing the result
		System.out.println(b);

	}

}
