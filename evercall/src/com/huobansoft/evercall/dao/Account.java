package com.huobansoft.evercall.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Account {
	
	private String phoneNumber;
	private String earningsToday = "0";
	private String earningsYestoday = "0";
	private String credits = "0";
	private Map<String, String> earningsLog;
	private List<ConsumptionLog> consumptionsLog;
	
	public Account(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		earningsLog = new LinkedHashMap<String, String>();
		consumptionsLog = new ArrayList<ConsumptionLog>();
	}
	
	public Account syncToServer() {
		
		return this;
	}
	

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEarningsToday() {
		return earningsToday;
	}

	public void setEarningsToday(String earningsToday) {
		this.earningsToday = earningsToday;
	}

	public String getEarningsYestoday() {
		return earningsYestoday;
	}

	public void setEarningsYestoday(String earningsYestoday) {
		this.earningsYestoday = earningsYestoday;
	}

	public String getCredits() {
		return credits;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}

	public Map<String, String> getEarningsLog() {
		return earningsLog;
	}

	public void setEarningsLog(Map<String, String> earningsLog) {
		this.earningsLog = earningsLog;
	}

	public List<ConsumptionLog> getConsumptionsLog() {
		return consumptionsLog;
	}

	public void setConsumptionsLog(List<ConsumptionLog> consumptionsLog) {
		this.consumptionsLog = consumptionsLog;
	}

}
