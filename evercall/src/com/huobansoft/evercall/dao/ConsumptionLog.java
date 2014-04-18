package com.huobansoft.evercall.dao;

public class ConsumptionLog {
	
	private long time;
	private int amount;
	private boolean serverSuccess;
	private boolean clientSuccess;
	
	private boolean noticeServerSuccess;
	
	public ConsumptionLog(long time, int amount, 
			boolean serverSuccess, boolean clientSuccess,
			boolean noticeServerSuccess) {
		this.time = time;
		this.amount = amount;
		this.serverSuccess = serverSuccess;
		this.clientSuccess = clientSuccess;
		this.noticeServerSuccess = noticeServerSuccess;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public boolean isServerSuccess() {
		return serverSuccess;
	}

	public void setServerSuccess(boolean serverSuccess) {
		this.serverSuccess = serverSuccess;
	}

	public boolean isClientSuccess() {
		return clientSuccess;
	}

	public void setClientSuccess(boolean clientSuccess) {
		this.clientSuccess = clientSuccess;
	}

	public boolean isNoticeServerSuccess() {
		return noticeServerSuccess;
	}

	public void setNoticeServerSuccess(boolean noticeServerSuccess) {
		this.noticeServerSuccess = noticeServerSuccess;
	}
	
}
