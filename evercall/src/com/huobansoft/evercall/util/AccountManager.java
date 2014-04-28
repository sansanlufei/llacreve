package com.huobansoft.evercall.util;

import java.util.Random;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.huobansoft.evercall.R;
import com.huobansoft.evercall.vo.Account;

public class AccountManager {
	
	private static final int ACCOUNT_CHANGED = 1;
	private static final int ACCOUNT_NOT_CHANGED = 0;
	
	public interface AccountManagerListener {
		public void onSyncWithServerFinished();
	}
	
	private Context mContext;
	private AccountDao accountDao;
	private DialogHelper dialogHelper;
	public AccountManager(Context context) {
		this.mContext = context;
		this.accountDao = new AccountDao(mContext);
		this.dialogHelper = new DialogHelper(mContext);
	}
	
	public Account signin(String tel) {
		Account account = new Account();
		account.setTel(tel);
		//1. signin to server
		
		//2. save to db
		account = accountDao.createAccount(account);
		
		return account;
	}
	
	public Account getAccount() {
		//query account info
		Account account = accountDao.getAccount();
		if (account == null) {
			return new Account();
		}
		
		//query account credits logs 
		
		return account;
	}
	
	private Account getAccountFromServer(String tel) {
		Random random = new Random();
		Account account = new Account();
		account.setTel(tel);
		account.setCredits(random.nextInt(100000) + "");
		return account;
	}
	
	public void syncWithServer(final AccountManagerListener listener) {
		//sync from server
		
		final Account accountdb = getAccount();
		if (accountdb == null || accountdb.getTel().trim().length() == 0) {
			dialogHelper.getCommonAlertDialog(mContext.getResources().getString(R.string.evercall_alert), 
					mContext.getResources().getString(R.string.hint_when_account_empty)).show();
			listener.onSyncWithServerFinished();
			return;
		}
		
		
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == ACCOUNT_NOT_CHANGED) {
					dialogHelper.getCommonAlertDialog(mContext.getResources().getString(R.string.evercall_alert), 
						mContext.getResources().getString(R.string.hint_when_account_not_changed)).show();
				}
				listener.onSyncWithServerFinished();
			};
		};
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (Exception e) {}
				Account accountServer = getAccountFromServer(accountdb.getTel());
//				accountServer = accountdb;
				if (accountdb.equals(accountServer) && 
						accountdb.getCredits().equals(accountServer.getCredits())) {
					handler.sendEmptyMessage(ACCOUNT_NOT_CHANGED);
				} else {
					//update to server
					accountDao.updateAccount(accountServer);
					handler.sendEmptyMessage(ACCOUNT_CHANGED);
				}
			}
		}).start();
	}
}
