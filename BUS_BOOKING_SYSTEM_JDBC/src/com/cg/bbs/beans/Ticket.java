package com.cg.bbs.beans;

import java.sql.Date;

public class Ticket {

	private Integer bookingId;
	private Date journeyDate;
	private int numofSeats;
	private Date bookingDatetime;
	private String availId;
	private Date availDate;
	private String availSeats;
	private Integer userId;
	private Integer busId;
	private String source;
	private String destination;
	private Double price;

	
	
	public Integer getBookingId() {
		return bookingId;
	}



	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}



	public Date getJourneyDate() {
		return journeyDate;
	}



	public void setJourneyDate(Date journeyDate) {
		this.journeyDate = journeyDate;
	}



	public int getNumofSeats() {
		return numofSeats;
	}



	public void setNumofSeats(int numofSeats) {
		this.numofSeats = numofSeats;
	}



	public Date getBookingDatetime() {
		return bookingDatetime;
	}



	public void setBookingDatetime(Date bookingDatetime) {
		this.bookingDatetime = bookingDatetime;
	}



	public String getAvailId() {
		return availId;
	}



	public void setAvailId(String availId) {
		this.availId = availId;
	}



	public Date getAvailDate() {
		return availDate;
	}



	public void setAvailDate(Date availDate) {
		this.availDate = availDate;
	}



	public String getAvailSeats() {
		return availSeats;
	}



	public void setAvailSeats(String availSeats) {
		this.availSeats = availSeats;
	}



	public Integer getUserId() {
		return userId;
	}



	public void setUserId(Integer userId) {
		this.userId = userId;
	}



	public int getBusId() {
		return busId;
	}



	public void setBusId(Integer busId) {
		this.busId = busId;
	
		
		
	}



	public String getSource() {
		return source;
	}



	public void setSource(String source) {
		this.source = source;
	}



	public String getDestination() {
		return destination;
	}



	public void setDestination(String destination) {
		this.destination = destination;
		
	}



	public Double getPrice() {
		return price;
	}



	public void setPrice(Double price) {
		this.price = price;
	}



	@Override
	public String toString() {
		return "Ticket [bookingId=" + bookingId + ", journeyDate=" + journeyDate + ", numofSeats=" + numofSeats
				+ ", bookingDatetime=" + bookingDatetime + ", availId=" + availId + ", availDate=" + availDate
				+ ", availSeats=" + availSeats + ", userId=" + userId + ", busId=" + busId + ", source=" + source
				+ ", destination=" + destination + ", price=" + price + "]";
	}



	



	}


	

