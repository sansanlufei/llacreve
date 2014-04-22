package com.huobansoft.evercall.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.huobansoft.evercall.R;
import com.huobansoft.evercall.activity.Person;
import com.huobansoft.evercall.util.ContactsHelper;

public class CallLogListAdapter extends MyContactsListAdapter {

	public CallLogListAdapter(ContactsHelper contactsHelper, Context context,
			int layout, Cursor c, String[] from, int[] to) {
		super(contactsHelper, context, layout, c, from, to);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final View onePersonView = super.getView(position, convertView, parent);
		
		//name
		TextView nameView = (TextView) onePersonView.findViewById(R.id.contact_name);
		if (nameView.getText().toString().length() == 0) {
			nameView.setText("Î´ÖªºÅÂë");
		}
		final String name = nameView.getText().toString();
		
		//tel number
		TextView numberView = (TextView) onePersonView.findViewById(R.id.contact_number);
		final String number = numberView.getText().toString();
		//date
		SimpleDateFormat f = new SimpleDateFormat("MM-dd HH:mm");
		TextView dateView = (TextView) onePersonView.findViewById(R.id.contact_time);
		final String date = dateView.getText().toString();
		long dateLong = 0;
		//fengyi.hua add number format check
				try{
					dateLong = Long.parseLong(date);
				} catch(NumberFormatException e){
					dateLong = 0;
				}
				dateView.setText(f.format(new Date(dateLong)));
		
		TextView typeView = (TextView) onePersonView.findViewById(R.id.contact_type);
		final String type = typeView.getText().toString();
		ImageView typeImage = (ImageView) onePersonView.findViewById(R.id.contact_type_image);
		if (String.valueOf(CallLog.Calls.INCOMING_TYPE).equals(type)) {
			typeImage.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.calllog_in_ok));
		} else if (String.valueOf(CallLog.Calls.MISSED_TYPE).equals(type)) {
			typeImage.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.calllog_in_missed));
		} else if (String.valueOf(CallLog.Calls.OUTGOING_TYPE).equals(type)) {
			typeImage.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.calllog_out_ok));
		}
		typeImage.setVisibility(View.VISIBLE);
		
		
		final Person person = new Person(null, name, number, date, null);
		
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
