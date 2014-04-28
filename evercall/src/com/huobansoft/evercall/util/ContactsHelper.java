package com.huobansoft.evercall.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;

import com.huobansoft.evercall.R;
import com.huobansoft.evercall.dao.CallLogListAdapter;
import com.huobansoft.evercall.dao.ContactsListAdapter;
import com.huobansoft.evercall.vo.Person;

public class ContactsHelper {
	
	private Context mContext;
	public ContactsHelper(Context context) {
		this.mContext = context;
	}
	
	private CallLogListAdapter calllogAllAdapter;
	private CallLogListAdapter calllogInAdapter;
	private CallLogListAdapter calllogOutAdapter;
	private CallLogListAdapter calllogMissedAdapter;
	
	private ContactsListAdapter contactsListAdapter;
	
	private static final String[] CONTACTS_FROMS = new String[] {
		ContactsContract.Contacts._ID,
		ContactsContract.Contacts.DISPLAY_NAME,
		ContactsContract.Contacts.LAST_TIME_CONTACTED,
		ContactsContract.Contacts.TIMES_CONTACTED
	};
	private static final int[] CONTACTS_TOS = new int[] {
		R.id.contact_id,
		R.id.contact_name,
		R.id.contact_time,
		R.id.contact_times_connected
	};
	
	public ContactsListAdapter getContacts(String name) {
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		Cursor cursor = mContext.getContentResolver().query(uri, 
				null, 
//				null,null,
				ContactsContract.Contacts.DISPLAY_NAME+" like ?", new String[]{"%" + name + "%"}, 
				ContactsContract.Contacts.TIMES_CONTACTED + " DESC");
		((Activity) mContext).startManagingCursor(cursor);
		
		contactsListAdapter = new ContactsListAdapter(this, mContext, R.layout.calllog_contact_item, cursor,
				CONTACTS_FROMS, CONTACTS_TOS);
		
		registerObserver(uri);
		
		return contactsListAdapter;
	}
	
	public List<Person> getAllContacts() {
		List<Person> list = new ArrayList<Person>();
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		Cursor cursor = null;
		try {
			cursor = mContext.getContentResolver().query(uri, 
					null, null, null, ContactsContract.Contacts.TIMES_CONTACTED + " DESC");
			while (cursor != null && cursor.moveToNext()) {
				String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
				String name =  cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				String date =  cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LAST_TIME_CONTACTED));
				String connectedTimes =  cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.TIMES_CONTACTED));
				
				Person person = new Person(contactId, name, null, date, connectedTimes);
				list.add(person);
			}
			
		} finally {
			if (cursor != null) {cursor.close();}
		}
		
		return list;
	}
  	
	private static final String[] CALLLOG_FROMS = new String[] {
		CallLog.Calls.CACHED_NAME,
		CallLog.Calls.NUMBER,
		CallLog.Calls.DATE,
		CallLog.Calls.TYPE
	};
	private static final int[] CALLLOG_TOS = new int[] {
		R.id.contact_name,
		R.id.contact_number,
		R.id.contact_time,
		R.id.contact_type
	};
	public CallLogListAdapter getCalllogAll() {
		if (calllogAllAdapter == null) {
			Uri uri = CallLog.Calls.CONTENT_URI;
			Cursor cursor = mContext.getContentResolver().query(uri, 
					null, null, null, CallLog.Calls.DATE + " DESC");
			((Activity) mContext).startManagingCursor(cursor);
			
			calllogAllAdapter = new CallLogListAdapter(this, mContext, R.layout.calllog_contact_item, cursor,
					CALLLOG_FROMS, CALLLOG_TOS);
			
			registerObserver(uri);
			
		}
		return calllogAllAdapter;
	}
	
	public CallLogListAdapter getCalllogIn() {
		if (calllogInAdapter == null) {
			Uri uri = CallLog.Calls.CONTENT_URI;
			Cursor cursor = mContext.getContentResolver().query(uri, 
					null, "type=?", new String[]{CallLog.Calls.INCOMING_TYPE+""}, CallLog.Calls.DATE + " DESC");
			((Activity) mContext).startManagingCursor(cursor);
			
			calllogInAdapter = new CallLogListAdapter(this, mContext, R.layout.calllog_contact_item, cursor,
					CALLLOG_FROMS, CALLLOG_TOS);
			
			registerObserver(uri);
		}
		
		return calllogInAdapter;
	}
	
	public CallLogListAdapter getCalllogOut() {
		if (calllogOutAdapter == null) {
			Uri uri = CallLog.Calls.CONTENT_URI;
			Cursor cursor = mContext.getContentResolver().query(uri, 
					null, "type=?", new String[]{CallLog.Calls.OUTGOING_TYPE+""}, CallLog.Calls.DATE + " DESC");
			((Activity) mContext).startManagingCursor(cursor);
			
			calllogOutAdapter = new CallLogListAdapter(this, mContext, R.layout.calllog_contact_item, cursor,
					CALLLOG_FROMS, CALLLOG_TOS);
			
			registerObserver(uri);
		}
		
		return calllogOutAdapter;
	}
	
	public CallLogListAdapter getCalllogMissed() {
		if (calllogMissedAdapter == null) {
			Uri uri = CallLog.Calls.CONTENT_URI;
			Cursor cursor = mContext.getContentResolver().query(uri, 
					null, "type=?", new String[]{CallLog.Calls.MISSED_TYPE+""}, CallLog.Calls.DATE + " DESC");
			((Activity) mContext).startManagingCursor(cursor);
			
			calllogMissedAdapter = new CallLogListAdapter(this, mContext, R.layout.calllog_contact_item, cursor,
					CALLLOG_FROMS, CALLLOG_TOS);
			
			registerObserver(uri);
		}
		
		return calllogMissedAdapter;
	}
	
	public String getContactsId(String number) {
		Cursor cursor = null;
		try {
			Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
			cursor = mContext.getContentResolver().query(uri, null, ContactsContract.CommonDataKinds.Phone.NUMBER+"=?", new String[]{number}, null);
			while (cursor != null && cursor.moveToNext()) {
				String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
				if (contactId != null) {
					return contactId;
				}
			}
			return null;
			
		} finally {if (cursor != null) {cursor.close();}}
	}
	
	public String getNumber(String contactId) {
		Cursor cursor = null;
		try {
			Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
			cursor = mContext.getContentResolver().query(uri, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"=?", new String[]{contactId}, null);
			while (cursor != null && cursor.moveToNext()) {
				String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				if (number != null) {
					return number;
				}
			}
			return null;
			
		} finally {if (cursor != null) {cursor.close();}}
	}
	
	public void registerObserver(Uri uri) {
		
	}

}
