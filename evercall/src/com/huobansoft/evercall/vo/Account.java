package com.huobansoft.evercall.vo;

import java.util.List;
import java.util.Map;

import com.huobansoft.evercall.dao.ConsumptionLog;

public class Account {
	
	private static final String TAG = "evercall";
	
	private String tel = "";
	private String credits = "0";
	private String earningsToday = "0";
	private String earningsYestoday = "0";
	private Map<String, String> creditsLog;
	private List<ConsumptionLog> consumptionsLog;
	
	@Override
	public boolean equals(Object o) {
		if (! (o instanceof Account)) {
			return false;
		}
		Account account = (Account) o;
		return this.tel != null && account != null && this.tel.equals(account.getTel()); 
	}
	
	public void copy(Account account) {
		this.tel = account.getTel();
		this.credits = account.getCredits();
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
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

	public Map<String, String> getCreditsLog() {
		return creditsLog;
	}

	public void setCreditsLog(Map<String, String> creditsLog) {
		this.creditsLog = creditsLog;
	}

	public List<ConsumptionLog> getConsumptionsLog() {
		return consumptionsLog;
	}

	public void setConsumptionsLog(List<ConsumptionLog> consumptionsLog) {
		this.consumptionsLog = consumptionsLog;
	}

}
