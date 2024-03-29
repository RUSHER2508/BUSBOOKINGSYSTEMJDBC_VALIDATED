package com.cg.bbs.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import com.cg.bbs.beans.Admin;
import com.cg.bbs.beans.Available;
import com.cg.bbs.beans.Booking;
import com.cg.bbs.beans.Bus;
import com.cg.bbs.beans.Ticket;
import com.cg.bbs.beans.User;
import com.cg.bbs.exception.AdminIdNotFoundException;
import com.cg.bbs.exception.BookingIdNotFoundException;
import com.cg.bbs.exception.BusCreateFailException;
import com.cg.bbs.exception.BusDeleteFailException;
import com.cg.bbs.exception.BusIdAlreadyExistsException;
import com.cg.bbs.exception.BusIdNotFoundException;
import com.cg.bbs.exception.BusNotAvailableException;
import com.cg.bbs.exception.BusNotFoundException;
import com.cg.bbs.exception.ContactExistsException;
import com.cg.bbs.exception.ContactNumberFormatException;
import com.cg.bbs.exception.DateNotMatchedException;
import com.cg.bbs.exception.DeleteUserException;
import com.cg.bbs.exception.EmailFormatException;
import com.cg.bbs.exception.LoginException;
import com.cg.bbs.exception.NotValidDateFormatException;
import com.cg.bbs.exception.NotValidDestinationException;
import com.cg.bbs.exception.NotValidSourceException;
import com.cg.bbs.exception.RegisterException;
import com.cg.bbs.exception.SourceNotFoundException;
import com.cg.bbs.exception.TicketBookingException;
import com.cg.bbs.exception.TicketCancelException;
import com.cg.bbs.exception.TicketNotAvailableException;
import com.cg.bbs.exception.UpdateException;
import com.cg.bbs.exception.UserEmailAlreadyExist;
import com.cg.bbs.exception.UserIdExistException;
import com.cg.bbs.exception.UserIdNotExistException;
import com.cg.bbs.exception.UserNameException;
import com.cg.bbs.services.AdminServiceImpl;
import com.cg.bbs.services.AdminServices;
import com.cg.bbs.services.UserServices;
import com.cg.bbs.services.UserServicesImpl;

public class App2{
	static int userId;
	static int adminId;
	static String password;
	static UserServices services = new UserServicesImpl();
	static AdminServices adminservices = new AdminServiceImpl();
	static Scanner sc = new Scanner(System.in);
	static Available available = new Available();
	static User user = new User();

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
				} catch (Exception e) {
					System.err.println(e.getMessage());

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
						System.out.println("** [8] Search Bus by source         **");
						System.out.println("** [9]Search Bus by destination    **");
						System.out.println("** [10] Logout                       **");

						System.out.println("***************************************");
						System.out.println("***************************************\n");
						{
							System.out.print("ENTER CHOICE: ");
							Integer choice = sc.nextInt();

							if (choice == 1) {
								try {
									searchUser();
								} catch (Exception exception) {
									System.err.println(exception.getMessage());
								}
							} else if (choice == 2) {
								try {
									updateUser();
								} catch (Exception exception) {
									System.err.println(exception.getMessage());
								}
							} else if (choice == 3) {
								try {
									deleteUser();
									loop1 = false;
								} catch (Exception exception) {
									System.err.println(exception.getMessage());
								}
							}

							else if (choice == 4) {
								try {
									bookTicket();
								} catch (Exception exception) {
									System.err.println(exception.getMessage());
								}
							} else if (choice == 5) {
								try {
									cancelTicket();

								} catch (Exception exception) {
									System.err.println(exception.getMessage());
								}
							} else if (choice == 6) {
								getAllTicket();
							}

							else if (choice == 7) {
								try {
									checkAvailability();
								} catch (Exception exception) {
									System.err.println(exception.getMessage());
								}
							}

							else if (choice == 8) {
								showBusesFromSource();
							} else if (choice == 9) {
								showBusesFromDestination();
							}

							else if (choice == 10) {
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
				} catch (Exception exception) {
					System.err.println(exception.getMessage());
				}

			} else if (firstChoice == 3) {
				Boolean admin = false;
				try {
					admin = adminLogin();
				} catch (Exception exception) {
					System.err.println(exception.getMessage());
				}
				if (admin) {
					System.out.println("Login Succesful");
					boolean loop2 = true;
					while (loop2) {
						System.out.println("** [1] Create bus               **");
						System.out.println("** [2] Update bus               **");
						System.out.println("** [3] Search bus               **");
						System.out.println("** [4] Delete bus               **");
						System.out.println("** [5] Exit                                **");

						System.out.println("***************************************");
						System.out.println("***************************************\n");

						System.out.print("ENTER CHOICE: ");
						Integer choice = sc.nextInt();
						if (choice == 1) {
							try {
								createBus();
							} catch (Exception exception) {
								System.err.println(exception.getMessage());
							}

						} else if (choice == 2) {
							try {
								updateBus();
							} catch (Exception exception) {
								System.err.println(exception.getMessage());
							}
						} else if (choice == 3) {
							try {
								searchBusById();
							} catch (Exception exception) {
								System.err.println(exception.getMessage());
							}
						} else if (choice == 4) {
							try {
								deleteBus();
							} catch (Exception exception) {
								System.err.println(exception.getMessage());
							}
						} else if (choice == 5) {
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

	private static List<Bus> showBusesFromSource() {
		System.out.println("Enter source");
		String source = sc.next();
		List<Bus> bus = services.showBusesFromSource(source);
		System.out.print(bus);
		return bus;

	}

	private static List<Bus> showBusesFromDestination() {
		System.out.println("Enter destination to see source");
		String destination = sc.next();
		List<Bus> buses = services.showBusesByDestination(destination);
		System.out.println(buses);
		return buses;

	}

	private static void updateUser() throws UpdateException {
		User user = new User();
		user.setUserId(userId); // Setting up userId
		user.setPassword(password); // Setting up password
		System.out.println("Enter New Username");
		user.setUsername(sc.next());
		System.out.println("Enter New Email");
		String email = services.checkemail(sc.next());
		if (email != null) {
			user.setEmail(email);
		} else {
			throw new EmailFormatException("EmailFormatException:Wrong Email Format!! e.g(example@email.com)");
		}

		System.out.println("Enter New Contact");
		user.setContact(sc.nextLong());

		 // Invoking the updateUser()
		  		boolean update = services.updateUser(user);
		if (update) {
			System.out.println("SuccessFully Updated");
		} else {
			throw new UpdateException("Updation Fail Exception");

		}

	}

	private static boolean loginUser() throws LoginException {

		boolean checkLogin = true;
		while (checkLogin) {
			System.out.println("Enter userid:");
			Integer tempId = services.idCheck(sc.next());
			if (tempId != null) {
				userId = tempId;
				checkLogin = false;
			} else {
				throw new NumberFormatException("NumberFormatException:UserId should be number !");

			}
		}
		if (services.searchUser(userId) == null) {
			throw new UserIdNotExistException("UserIdNotExistException:UserId not exists");
		} else {
			System.out.println("Enter password:");
			password = sc.next();
			Boolean login = services.loginUser(userId, password);
			if (login) {
				return true;
			} else {
				throw new LoginException("LoginException:Login Failed Exception");
			}
		}

	}

	private static void deleteUser() throws DeleteUserException {

		if (services.deleteUser(userId, password)) {
			System.out.println("Profile sucessfully Deleted");
		} else {
			throw new DeleteUserException("User Profile deletion Failed");
		}
	}

	private static void searchBusById() throws BusNotFoundException {
		boolean busCheck = true;
		Integer busId = 0;
		while (busCheck) {
			System.out.println("Enter BusId");
			Integer tempId = services.idCheck(sc.next());
			if (tempId != null) {
				busId = tempId;
				busCheck = false;
			} else {
				throw new NumberFormatException("NumberFormatException:BusId should be number !");

			}
			if (services.searchBus(busId) != null) {
				Bus bus = adminservices.searchBus(busId);
				System.out.println(bus);
			} else {
				throw new BusIdNotFoundException("BusIdNotFoundException:No buses on this busId");
			}
		}

	}

	private static void searchUser() {
		User search = services.searchUser(userId);
		System.out.println(search);

	}

	private static void checkAvailability() throws ParseException {
		Integer busId = 0;
		System.out.println("Enter BusId");
		Integer tempId = services.idCheck(sc.next());
		if (tempId != null) {
			busId = tempId;
		} else {
			throw new NumberFormatException("NumberFormatException:BusId should be number !");

		}
		if (services.searchBus(busId) != null) {
			Bus bus = adminservices.searchBus(busId);
		} else {
			throw new BusIdNotFoundException("BusIdNotFoundException:No buses on this busId");
		}
		System.out.println("Enter available  in yyyy-mm-dd");
		String enterDate = sc.next();
		if (enterDate == null) {
			throw new NotValidDateFormatException("NotValidDateException:Enter a valid date format");
		}
		java.util.Date availableDateEntered = new SimpleDateFormat("yyyy-MM-dd").parse(enterDate);
		java.sql.Date availabeDate = new Date(availableDateEntered.getTime());
		int available = services.checkAvailability(busId, availabeDate);
		System.out.println(available);

	}

	private static void bookTicket() throws TicketBookingException, ParseException {

		Ticket ticket = new Ticket();

		System.out.println("Enter the sources from provided");
		System.out.println(services.showSources());
		List<String> list = services.showSources();
		String source = sc.next();
		String upSource = source.toUpperCase();
		if (list.contains(upSource)) {
			ticket.setSource(source);
		} else {
			throw new NotValidSourceException("NotValidSourceException:Enter source provided");

		}
		System.out.println("Enter the destination");
		System.out.println(services.showDestination(source));
		String destination = sc.next();
		String upDestination = destination.toUpperCase();
		if (services.showDestination(source).contains(upDestination)) {
			ticket.setDestination(destination);
		} else {
			throw new NotValidDestinationException("NotValidDestinationException:Enter destination provided");
		}
		System.out.println("Enter date of journey(yyyy-mm-dd)");
		System.out.println(services.date(source, destination));
		String tempDate = services.checkDate(sc.next());
		if (tempDate == null) {
			throw new NotValidDateFormatException("NotValidDateException:Enter a valid date format");
		}
		Date date=Date.valueOf(tempDate);
		ticket.setJourneyDate(date);
		List<Bus> list1 = 
				services.checkAvailability(source,destination,date);
		boolean dateMatched = list1.equals(date);
		try {
			if (list1.get(0) != null) {
				for (Bus busavailable : list1) {
				}
			}
		} catch (Exception exception) {
			throw new BusNotAvailableException("BusNotAvailableException:Bus not available enter proper date provided");

		}


		for(Bus bus:list1)
		{	

			System.out.println(bus);
			int avail = services.checkAvailability(bus.getBusId(), date);
			System.out.println("Available Seats:"+avail);
		}

		System.out.println("Enter the busId");
		int busId = 0;
		Integer tempId1 = services.idCheck(sc.next());
		if (tempId1 != null) {
			busId = tempId1;
		} else {
			throw new NumberFormatException("NumberFormatException:BusId should be number !");

		}
		ticket.setBusId(busId);
		ticket.setUserId(userId);

		Integer availSeats=services.checkAvailability(busId, date);
		if(availSeats!=null){
			System.out.println("Total available seats are: "+availSeats);
		}

		System.out.println("Enter number of seats to book");
		ticket.setNumofSeats(sc.nextInt());
		Ticket bookTicket=services.bookTicket(ticket);
		if(bookTicket!=null){
			System.out.println("Ticket sucessfully Booked");
			System.out.println(services.getTicket(ticket.getBookingId(), userId));
		}else{
			throw new TicketBookingException("Ticket Booking Fail Exception");
		}	
	}

	

	private static void getAllTicket() {
		Ticket booking = services.getAllTicket(userId);
		System.out.println(booking);
	}
	
	private static void getTicket() {
		System.out.println("Enter BookingId");
		Ticket ticket = services.getTicket(sc.nextInt(), userId);
		if(ticket != null) {
			System.out.println(ticket);
		}else {
			System.out.println("No Tickets Found");
		}
		}	

	private static void cancelTicket() throws TicketCancelException {
		System.out.println("Enter BookingId");
		Integer tempId = services.idCheck(sc.next());
		Integer bookingId=0;
		if (tempId != null) {
			bookingId = tempId;

		} else {
			throw new NumberFormatException("NumberFormatException:BookingId should be number !");

		}
		if (services.searchBookingId(bookingId) != null) {
			Booking booking = services.searchBookingId(bookingId);
		} else {
			throw new BookingIdNotFoundException("BookingIdNotFoundException buses on this busId");
		}
		Ticket ticket = services.getTicket(bookingId, userId);
		System.out.println(ticket);
		System.err.println("Press  yes to confirm ");
		String decision = sc.next();
		if (decision.equalsIgnoreCase("yes")) {
			Boolean cancelTicket = services.cancelTicket(bookingId, userId);
			if (cancelTicket) {
				System.out.println("Ticket Successfully Cancelled");
			} else {
				throw new TicketNotAvailableException("TicketNotAvailableException:No Tickets Found");
			}
		} else {
			throw new TicketCancelException("TicketCancelException:Ticket cancel exception occured");
		}
	}

	private static void createUser() throws RegisterException {
		User user = new User();
		boolean create = true;
		while (create) {
			System.out.println("Enter userid:");
			Integer tempId = services.idCheck(sc.next());
			if (tempId != null) {
				userId = tempId;
				user.setUserId(userId);
				create = false;
			} else {
				throw new NumberFormatException("NumberFormatException:UserId should be number !");
			}
		}
		if (services.searchUser(userId) != null) {
			throw new UserIdExistException("UserIdExistException:UserId already exists");
		} else {
			System.out.println("Enter Username:");
			String username = sc.next();
			user.setUsername(username);
			if (services.searchUser(username) != null) {
				throw new UserNameException("UserNameException:Username already exists");
			} else {
				boolean checkEmail = true;
				System.out.println("Enter Email:");
				String email = services.checkemail(sc.next());
				if (email != null) {
					user.setEmail(email);
					checkEmail = false;
				} else {
					throw new EmailFormatException("EmailFormatException:Wrong Email Format!! e.g(example@email.com)");
				}
				if (services.searchUserEmail(email) != null) {
					throw new UserEmailAlreadyExist("UserEmailAlreadyExist:Email already exists");
				} else {

					boolean checkContact = true;
					System.out.println("Enter Contact No.:");
					Long temp = services.checkContact(sc.next());
					if (temp != null) {
						user.setContact(temp);
						checkContact = false;
					} else {
						throw new ContactNumberFormatException(
								"ContactNumberFormatException:Contact should be of 10 digits!!");
					}
					if (services.searchContact(temp) != null) {
						throw new ContactExistsException("ContactExistsException:Contact Already Exits");
					} else {
						System.out.println("Enter Password:");
						user.setPassword(sc.next());
						boolean registration = services.createUser(user);
						if (registration) {
							System.out.println("Registration Successful");
						} else {
							throw new RegisterException("RegisterException:Registration Fail Exception");
						}
					}
				}
			}
		}
	}

	private static Boolean adminLogin() throws LoginException {

		int adminId = 0;
		System.out.println("Enter AdminId");
		Integer tempId = services.idCheck(sc.next());
		if (tempId != null) {
			adminId = tempId;

		} else {
			throw new NumberFormatException("NumberFormatException:BusId should be number !");

		}
		if (adminservices.searchAdmin(adminId) != null) {
			Admin login = adminservices.searchAdmin(adminId);
		} else {
			throw new AdminIdNotFoundException("AdminIdNotFoundException buses on this busId");
		}

		System.out.println("Enter Admin password:");
		String password = sc.next();
		if (adminservices.adminLogin(adminId, password)) {
			return true;
		} else {
			throw new LoginException("LoginException:Admin Login Fail Exception");
		}
	}

	private static void createBus() throws BusCreateFailException {
		Bus bus = new Bus();
		Integer busId = 0;
		System.out.println("Enter BusId");
		Integer tempId = services.idCheck(sc.next());
		if (tempId != null) {
			busId = tempId;
		} else {
			throw new NumberFormatException("NumberFormatException:BusId should be number !");

		}
		if (adminservices.searchBus(busId) == null) {
			Bus searchBus = adminservices.searchBus(busId);
		} else {
			throw new BusIdAlreadyExistsException("BusIdAlreadyExistsException: BusId already exists");
		}
		bus.setBusId(busId);

		System.out.println("Enter BusName");
		bus.setBusName(sc.next());
		System.out.println("Enter Bus type");
		bus.setBusType(sc.next());
		System.out.println("Enter Source");
		bus.setSource(sc.next());
		System.out.println("Enter Destination");
		bus.setDestination(sc.next());
		System.out.println("Enter Total Seats");
		Integer seats = services.idCheck(sc.next());
		int seat = 0;
		if (seats != null) {
			seat = seats;
			bus.setTotalSeats(seat);
		} else {
			throw new NumberFormatException("NumberFormatException:Seats should be number !");
		}

		System.out.println("Enter Price");
		Double price = sc.nextDouble();
		bus.setPrice(price);
		boolean create = adminservices.createBus(bus);
		if (create) {
			System.out.println("Bus created");
		} else {
			throw new BusCreateFailException("Failed to Create Bus Exception");
		}

	}

	private static void updateBus() throws UpdateException {
		System.out.println("Enter the source");
		String source = sc.next();
		System.out.println("Enter the destination");
		String destination = sc.next();
		System.out.println("Enter the price");
		double price = sc.nextDouble();
		Integer busId = 0;
		System.out.println("Enter BusId");
		Integer tempId = services.idCheck(sc.next());
		if (tempId != null) {
			busId = tempId;
		} else {
			throw new NumberFormatException("NumberFormatException:BusId should be a number !");

		}
		if (services.searchBus(busId) != null) {
			Bus bus = adminservices.searchBus(busId);
		} else {
			throw new BusIdNotFoundException("BusIdNotFoundException:No buses on this busId");
		}

		System.out.println(adminservices.searchBus(busId));

		Bus bus = new Bus();

		boolean updatebus = adminservices.updateBus(bus);
		if (updatebus) {
			System.out.println("Bus Successfully Updated");
		} else {
			throw new UpdateException("UpdateException:Fail to Update Bus Exception");
		}
	}

	private static void deleteBus() throws BusDeleteFailException {
		Integer busId = 0;
		System.out.println("Enter BusId");
		Integer tempId = services.idCheck(sc.next());
		if (tempId != null) {
			busId = tempId;
		} else {
			throw new NumberFormatException("NumberFormatException:BusId should be number !");

		}
		if (adminservices.searchBus(busId) != null) {
			Bus b = adminservices.searchBus(busId);
		} else {
			throw new BusIdNotFoundException("BusIdNotFoundException:No buses on this busId");
		}
		boolean deleteBus = adminservices.deleteBus(busId);
		if (deleteBus) {
			System.out.println("Bus Successfully Deleted");
		} else {
			throw new BusDeleteFailException("BusDeleteFailException:Failed to Delete Bus Exception");
		}
	}

}