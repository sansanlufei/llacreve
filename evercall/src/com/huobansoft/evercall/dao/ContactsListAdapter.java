package com.huobansoft.evercall.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.huobansoft.evercall.R;
import com.huobansoft.evercall.util.ContactsHelper;
import com.huobansoft.evercall.vo.Person;

public class ContactsListAdapter extends MyContactsListAdapter {

	public ContactsListAdapter(ContactsHelper contactsHelper, Context context,
			int layout, Cursor c, String[] from, int[] to) {
		super(contactsHelper, context, layout, c, from, to);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final View onePersonView = super.getView(position, convertView, parent);
		
		//contactId
		TextView contactIdView = (TextView) onePersonView.findViewById(R.id.contact_id);
		final String contactId = contactIdView.getText().toString();
		
		//name
		TextView nameView = (TextView) onePersonView.findViewById(R.id.contact_name);
		if (nameView.getText().toString().length() == 0) {
			nameView.setText("Î´ÖªºÅÂë");
		}
		final String name = nameView.getText().toString();
		
		//tel number
		TextView numberView = (TextView) onePersonView.findViewById(R.id.contact_number);
		final String number = contactsHelper.getNumber(contactId);
		numberView.setText(number);
		
		//date
		TextView dateView = (TextView) onePersonView.findViewById(R.id.contact_time);
		SimpleDateFormat f = new SimpleDateFormat("MM-dd HH:mm");
		dateView.setText(f.format(new Date(Long.parseLong(dateView.getText().toString()))));
		final String date = dateView.getText().toString();
		
		//connected times
		TextView connectedTimesView = (TextView) onePersonView.findViewById(R.id.contact_times_connected);
		final String connectedTimes = connectedTimesView.getText().toString();
		
		final Person person = new Person(contactId, name, number, date, connectedTimes);
		
		onePersonView.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
//				Toast.makeText(mContext, ((TextView) onePersonView.findViewById(R.id.contact_id)).getText().toString(), 3000).show();
				onePersonView.setBackgroundColor(mContext.getResources().getColor(R.color.contacts_list_item_background_selected));
				showPersonOperationsDialog(onePersonView, person);
				return true;
			}
		});
		
		//call button and isms button
		Button callButton = (Button) onePersonView.findViewById(R.id.contact_to_call);
		callButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (number != null && number.trim().length() > 0) {
					Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+number));
					mContext.startActivity(intent);
				}
			}
		});
		Button ismsButton = (Button) onePersonView.findViewById(R.id.contact_to_isms_chat);
		ismsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if (number != null && number.trim().length() > 0) {
					Intent intent = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+number));
					mContext.startActivity(intent);
				}
			}
		});
		
		return onePersonView;
	}
	
}
