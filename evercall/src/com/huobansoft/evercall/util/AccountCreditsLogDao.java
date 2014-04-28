package com.huobansoft.evercall.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.huobansoft.evercall.vo.CreditsLog;

public class AccountCreditsLogDao extends SQLiteOpenHelper {
	private static final String TAG = "evercall";
	
	private static final int ACCOUNT_DB_VERSION = 1;
	
	public static final String TABLE_NAME = "account_credits_log";
	public static final String AMOUNT = "amount";
	public static final String SOURCE = "source";
	public static final String CTIME = "ctime";
	
	private static final String TABLE_SCHEMA = 
			"CREATE TABLE " +TABLE_NAME+ "(" +
					AMOUNT + " INTEGER NOT NULL," +
					SOURCE + " VARCHAR(128) NOT NULL," +
					CTIME + " INTEGER NOT NULL" +
					")";
	private static final String[] indexs = new String[]{
		
	};
	
	private Context mContext;
	public AccountCreditsLogDao(Context context) {
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
	
	public List<CreditsLog> getAllCreditsLog() {
		Cursor cursor = null;
		try {
			cursor = getReadableDatabase()
					.query(TABLE_NAME, null, null, null, null, null, null);
			
			Log.i(TAG, "query getAllCreditsLog");
			
			List<CreditsLog>  list = new ArrayList<CreditsLog>();
			while (cursor != null && cursor.moveToNext()) {
				int amount = cursor.getInt(cursor.getColumnIndex(AMOUNT));
				String source = cursor.getString(cursor.getColumnIndex(SOURCE));
				String ctime = cursor.getString(cursor.getColumnIndex(CTIME));
				list.add(new CreditsLog(amount, source, ctime));
			}
			return list;
			
		} finally {
			if (cursor != null) {
				cursor.close();
				cursor = null;
			}
		}
	}
}
