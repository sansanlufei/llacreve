package com.huobansoft.evercall.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.huobansoft.evercall.R;
import com.huobansoft.evercall.util.ContactsHelper;

public class CalllogAllFragment extends Fragment {

	private ContactsHelper contactsHelper;
	private ListView mListView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LinearLayout view = (LinearLayout) inflater.from(getActivity()).inflate(R.layout.calllog_contact_all, container, false);
		mListView = (ListView) view.findViewById(R.id.calllog_contacts_list);
		mListView.setAdapter(contactsHelper.getCalllogAll());
		return view;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		contactsHelper = new ContactsHelper(getActivity());
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
