package com.huobansoft.evercall.util;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.huobansoft.evercall.vo.Account;

public class AccountDao extends SQLiteOpenHelper {
	private static final String TAG = "evercall";
	
	private static final int ACCOUNT_DB_VERSION = 1;
	
	private static final String TABLE_NAME = "account";
	private static final String TEL = "tel";
	private static final String CREDITS = "credits";
	private static final String CTIME = "ctime";
	private static final String UTIME = "utime";
	
	private static final String TABLE_SCHEMA = 
			"CREATE TABLE " +TABLE_NAME+ "(" +
					TEL + " VARCHAR(32) NOT NULL," +
					CREDITS + " INTEGER NOT NULL," +
					CTIME + " INTEGER NOT NULL," +
					UTIME + " INTEGER NOT NULL" +
					")";
	private static final String[] indexs = new String[]{
		"CREATE UNIQUE INDEX tel_index ON " + TABLE_NAME + " ( " + TEL + " )"
	};
	
	private Context mContext;
	public AccountDao(Context context) {
		super(context, "evercall", null, ACCOUNT_DB_VERSION);
		this.mContext = context;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_SCHEMA);
		for (String s: indexs) {
			db.execSQL(s);
		}
		Log.i(TAG, "create table " + TABLE_NAME);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(TAG, "update table " + TABLE_NAME + " " + oldVersion + " " + newVersion);
	}
	
	public Account getAccount() {
		Cursor cursor = null;
		try {
			cursor = getReadableDatabase()
					.query(TABLE_NAME, null, null, null, null, null, null);
			
			Log.i(TAG, "query account");
			
			if (cursor != null && cursor.moveToNext()) {
				String tel = cursor.getString(cursor.getColumnIndex(TEL));
				String credits = cursor.getString(cursor.getColumnIndex(CREDITS));
				Account account = new Account();
				account.setTel(tel);
				account.setCredits(credits);
				return account;
			}
			return null;
		} finally {
			if (cursor != null) {
				cursor.close();
				cursor = null;
			}
		}
	}
	
	public synchronized Account createAccount(Account account) {
		//first to clear all account info if the account change
		Account accountdb = getAccount();
		if (accountdb != null && ! accountdb.equals(account)) {
			clearAccount();
		}
		
		ContentValues values = new ContentValues();
		values.put(TEL, account.getTel());
		values.put(CREDITS, account.getCredits());
		values.put(CTIME, new Date().getTime());
		values.put(UTIME, new Date().getTime());
		getWritableDatabase().insert(TABLE_NAME, null, values);
		Log.i(TAG, "create account tel=" + account.getTel());
		return account;
	}
	
	public void clearAccount() {
		getWritableDatabase().delete(TABLE_NAME, null, null);
		Log.i(TAG, "clear account");
	}
	
	public Account updateAccount(Account account) {
		ContentValues values = new ContentValues();
		values.put(CREDITS, account.getCredits());
		values.put(UTIME, new Date().getTime());
		getWritableDatabase().update(TABLE_NAME, values, TEL+"=?", new String[]{account.getTel()});
		Log.i(TAG, "update account tel=" + account.getTel() + ", credits=" + account.getCredits());
		return account;
	}
}
