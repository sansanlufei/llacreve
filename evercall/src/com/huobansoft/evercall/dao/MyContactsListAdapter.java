package com.huobansoft.evercall.dao;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.Toast;

import com.huobansoft.evercall.R;
import com.huobansoft.evercall.util.ContactsHelper;
import com.huobansoft.evercall.vo.Person;

public class MyContactsListAdapter extends SimpleCursorAdapter {

	
	protected ContactsHelper contactsHelper;
	
	public MyContactsListAdapter(ContactsHelper contactsHelper, Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, layout, c, from, to);
		this.contactsHelper = contactsHelper;
	}
	
	/**
	 * Show one dialog contains all operations menus for one contact person
	 * @param person
	 */
	protected void showPersonOperationsDialog(final View onePersonView, final Person person) {
		final String[] operations = new String[] {"修改", "详细信息"};
		AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
	    builder.setTitle(person.getName())
	           .setItems(operations, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int which) {
	            	   onePersonView.setBackgroundColor(mContext.getResources().getColor(R.color.contacts_list_item_background));
	            	   String contactId = person.getContactId();
	            	   if (contactId == null) {
	            		   contactId = contactsHelper.getContactsId(person.getNumber());
	            	   }
	            	   if (contactId == null) {
	            		   Toast.makeText(mContext, "无此联系人", Toast.LENGTH_LONG).show();
	            		   return;
	            	   }
	            	   Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(contactId));
	            	   Intent intent = null;
	            	   switch (which) {
		            	   case 0:
			           		   intent = new Intent(Intent.ACTION_EDIT, uri);
			           		   mContext.startActivity(intent);
				           	   break;
		            	   case 1:
		            		   intent = new Intent(Intent.ACTION_EDIT, uri);
		            		   mContext.startActivity(intent);
		            		   break;
	            	   }
	               }
	           });
	    builder.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				onePersonView.setBackgroundColor(mContext.getResources().getColor(R.color.contacts_list_item_background));
			}
		});
	    
	    builder.create().show();
	}
	
}
