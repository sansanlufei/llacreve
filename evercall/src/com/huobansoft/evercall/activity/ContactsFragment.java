package com.huobansoft.evercall.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.huobansoft.evercall.R;
import com.huobansoft.evercall.util.ContactsHelper;

public class ContactsFragment extends Fragment {

	private static final String TAG = ContactsFragment.class.getSimpleName();
	
	private ContactsHelper contactsHelper;
	private FrameLayout mView;
	private ListView mListView;
	private String name1 = "", name2 = "";
	private EditText searchPadNameTextView;
	private LinearLayout searchPadQuick;
	private LinearLayout searchPadQuickList1;
	private LinearLayout searchPadQuickList2;
	private ImageButton searchPadUpDownButton;
	
	private Map<String, List<String>> searchpadData = new LinkedHashMap<String, List<String>>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = (FrameLayout) inflater.from(getActivity()).inflate(R.layout.contacts, container, false);
		mListView = (ListView) mView.findViewById(R.id.contacts_list);
		searchPadNameTextView = (EditText) mView.findViewById(R.id.search_pad_name_text);
		searchPadNameTextView.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				refreshContacts();
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void afterTextChanged(Editable s) {}
		});
		searchPadQuick = (LinearLayout) mView.findViewById(R.id.search_pad_quick_list);
		searchPadUpDownButton = (ImageButton) mView.findViewById(R.id.search_pad_up_down_btn);
		searchPadUpDownButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (searchPadQuick.getVisibility() == View.GONE) {
					((ImageButton)v).setImageDrawable(ContactsFragment.this.getResources().getDrawable(R.drawable.navigation_down));
					searchPadQuick.setVisibility(View.VISIBLE);
				} else {
					((ImageButton)v).setImageDrawable(ContactsFragment.this.getResources().getDrawable(R.drawable.navigation_up));
					searchPadQuick.setVisibility(View.GONE);
				}
			}
		});
		
		searchPadQuickList1 = (LinearLayout) mView.findViewById(R.id.search_pad_quick_list1);
		searchPadQuickList2 = (LinearLayout) mView.findViewById(R.id.search_pad_quick_list2);
		refreshContacts();
		return mView;
	}
	
	private void refreshContacts() {
		mListView.setAdapter(contactsHelper.getContacts(searchPadNameTextView.getText().toString().trim()));
	}
	
	private void refreshSearchPad() {
		searchpadData.clear();
		List<Person> list = contactsHelper.getAllContacts();
		for (Person p: list) {
			String name = p.getName();
			if (name.trim().length() >= 1) {
				String name1 = name.trim().substring(0, 1);
				String name2 = null;
				if (name.trim().length() >= 2) {
					name2 = name.trim().substring(1, 2);
				}
				putToSearchpadData(name1, name2);
			}
		}
		
		//refresh the search pad list view
		Iterator<Map.Entry<String, List<String>>> it = searchpadData.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, List<String>> entry = it.next();
			addSearchPadItem(searchPadQuickList1, entry.getKey(), 1);
		}
		name1 = searchpadData.keySet().toArray(new String[searchpadData.keySet().size()])[0];
		showSearchPadQuickList2(name1);
	}
	
	private void addSearchPadItem(ViewGroup group, String text, final int padIndex) {
		TextView view = (TextView) this.getActivity().getLayoutInflater().inflate(R.layout.search_pad_button, null, true);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(group.getLayoutParams().height, group.getLayoutParams().height);
		params.setMargins(2, 0, 0, 0);
		view.setLayoutParams(params);
		view.setText(text);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (1 == padIndex) {
					name1 = ((TextView)v).getText().toString();
					name2 = "";
					showSearchPadQuickList2(name1);
				} else {
					name2 = ((TextView)v).getText().toString();
				}
				
				searchPadNameTextView.setText(name1 + name2);
				searchPadNameTextView.setSelection((name1 + name2).length());
			}
		});
		
		group.addView(view);
	}
	
	private void showSearchPadQuickList2(String name1) {
		HorizontalScrollView parent = (HorizontalScrollView) searchPadQuickList2.getParent();
		parent.scrollTo(HorizontalScrollView.FOCUS_LEFT, 0);
		searchPadQuickList2.removeAllViews();
		for (String name2: searchpadData.get(name1)) {
			addSearchPadItem(searchPadQuickList2, name2, 2);
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		refreshSearchPad();
	}
	
	private void putToSearchpadData(String name1, String name2) {
		List<String> list = searchpadData.get(name1);
		if (list == null) {
			list = new ArrayList<String>();
		}
		if (name2 != null && name2.trim().length()> 0 && !list.contains(name2)) {
			list.add(name2);
		}
		searchpadData.put(name1, list);
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
