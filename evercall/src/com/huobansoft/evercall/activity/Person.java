package com.huobansoft.evercall.activity;

import java.util.HashMap;

public class Person extends HashMap<String, String> {
	
	private static final long serialVersionUID = 1L;

	public Person(String contactId, String name, String number, String date, String timesConnected) {
		this.put("contactId", contactId);
		this.put("name", name);
		this.put("number", number);
		this.put("date", date);
		this.put("timesConnected", timesConnected);
	}
	
	public String getContactId() {
		return this.get("contactId");
	}
	public String getName() {
		return this.get("name");
	}
	public String getNumber() {
		return this.get("number");
	}
	public String getDate() {
		return this.get("date");
	}
	
	public String getTimesConnected() {
		return this.get("timesConnected");
	}
	
}
