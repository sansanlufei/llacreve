package com.huobansoft.evercall.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.huobansoft.evercall.R;
import com.huobansoft.evercall.util.ContactsHelper;

public class CalllogAllFragment extends Fragment {
	
	private static final String TAG = "evercall";
	
	private ContactsHelper contactsHelper;
	private ListView mListView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "onCreateView");
		LinearLayout view = (LinearLayout) inflater.from(getActivity()).inflate(R.layout.calllog_contact_all, container, false);
		mListView = (ListView) view.findViewById(R.id.calllog_contacts_list);
		mListView.setAdapter(contactsHelper.getCalllogAll());
		return view;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		contactsHelper = new ContactsHelper(getActivity());
	}
	
	@Override
	public void onResume() {
		Log.i(TAG, "onResume");
		super.onResume();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i(TAG, "onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}
	@Override
	public void onAttach(Activity activity) {
		Log.i(TAG, "onAttach");
		super.onAttach(activity);
	}
	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy");
		super.onDestroy();
	}
	@Override
	public void onDestroyView() {
		Log.i(TAG, "onDestroyView");
		super.onDestroyView();
	}
	@Override
	public void onHiddenChanged(boolean hidden) {
		Log.i(TAG, "onHiddenChanged");
		super.onHiddenChanged(hidden);
	}
	@Override
	public void onDetach() {
		Log.i(TAG, "onDetach");
		super.onDetach();
	}
	@Override
	public void onPause() {
		Log.i(TAG, "onPause");
		super.onPause();
	}
	@Override
	public void onStart() {
		Log.i(TAG, "onStart");
		super.onStart();
	}
	@Override
	public void onStop() {
		Log.i(TAG, "onStop");
		super.onStop();
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		Log.i(TAG, "onViewCreated");
		super.onViewCreated(view, savedInstanceState);
	}
}
