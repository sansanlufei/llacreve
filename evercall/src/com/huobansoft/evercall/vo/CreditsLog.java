package com.huobansoft.evercall.vo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class CreditsLog {
	private int amount;
	private String source;
	private String ctime;
	
	public CreditsLog(Map<String, Object> map) {
		this((Integer) map.get("amount"), (String)map.get("source"), null);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ctime = sf.format(new Date(Long.parseLong((String)map.get("ctime"))));
		this.ctime = ctime;
	}
	
	public CreditsLog(int amount, String source, String ctime) {
		this.amount = amount;
		this.source = source;
		this.ctime = ctime;
	}

	public int getAmount() {
		return amount;
	}
	public String getSource() {
		return source;
	}
	public String getCtime() {
		return ctime;
	}
}
