package com.huobansoft.evercall.activity;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huobansoft.evercall.R;
import com.huobansoft.evercall.util.AccountManager;
import com.huobansoft.evercall.util.AccountManager.AccountManagerListener;
import com.huobansoft.evercall.util.DialogHelper;
import com.huobansoft.evercall.util.MyAlertDialog;
import com.huobansoft.evercall.vo.Account;

public class CollectionsFragment extends Fragment implements MyAlertDialog.MyAlertDialogListener, AccountManagerListener {
	
	private static final String TAG = "evercall";

	private DialogHelper dialogHelper;
	private Button accountInfoBindButton;
	private Button creditsLogsButton;
	private Button creditsRefreshButton;
	private ProgressBar creditsRefreshProgress;
	private RelativeLayout mView;
	private AccountManager accountManager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.dialogHelper = new DialogHelper(this.getActivity());
		accountManager = new AccountManager(getActivity());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = (RelativeLayout) inflater.from(getActivity()).inflate(R.layout.collections, container, false);
		accountInfoBindButton = (Button) mView.findViewById(R.id.account_info_bind);
		accountInfoBindButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogHelper.getAccountInfoBindDialog(accountManager.getAccount(), CollectionsFragment.this).show();
			}
		});
		creditsLogsButton = (Button) mView.findViewById(R.id.account_credits_logs_btn);
		creditsLogsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		creditsRefreshButton = (Button) mView.findViewById(R.id.account_credits_refresh_btn);
		creditsRefreshProgress = (ProgressBar) mView.findViewById(R.id.account_credits_refresh_progress);
		creditsRefreshButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (creditsRefreshButton.getVisibility() == View.VISIBLE) {
					creditsRefreshButton.setVisibility(View.GONE);
				}
				if (creditsRefreshProgress.getVisibility() == View.GONE) {
					creditsRefreshProgress.setVisibility(View.VISIBLE);
				}
				accountManager.syncWithServer(CollectionsFragment.this);
			}
		});
		
		return mView;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		refreshAccountInfo();
		initAds(mView);
	}
	
	private void refreshAccountInfo() {
		TextView phonenumberView = ((TextView) mView.findViewById(R.id.account_phonenumber));
		TextView accountCreditsview = (TextView) mView.findViewById(R.id.account_credits);
		TextView accountCreditsEarningsToday = (TextView) mView.findViewById(R.id.account_earnings_today);
		TextView accountCreditsEarningsYestoday = (TextView) mView.findViewById(R.id.account_earnings_yestoday);
		Account account = accountManager.getAccount();
		phonenumberView.setText(account.getTel());
		accountCreditsview.setText(account.getCredits());
		accountCreditsEarningsToday.setText(account.getEarningsToday());
		accountCreditsEarningsYestoday.setText(account.getEarningsYestoday());
		Log.i(TAG, "account refresh: " + phonenumberView.getText().toString());
	}
	
	private void initAds(ViewGroup view) {
		//youmi
		AdView adView = new AdView(this.getActivity(), AdSize.FIT_SCREEN);
		LinearLayout adLayout=(LinearLayout)view.findViewById(R.id.ad_youmi_banner);
		adLayout.addView(adView);
		
		//flurry
	}

	@Override
	public void onDialogPositiveClick(DialogInterface dialog, View view) {
		String phoneNumber = ((EditText) view.findViewById(R.id.account_phonenumber)).getText().toString();
		if (phoneNumber != null && phoneNumber.trim().length() > 0) {
			accountManager.signin(phoneNumber);
			refreshAccountInfo();
		}
		dialog.dismiss();
	}

	@Override
	public void onDialogNegativeClick(DialogInterface dialog, View view) {
		
	}
	
	@Override
	public void onSyncWithServerFinished() {
		Log.i(TAG, "onSyncWithServerFinished");
		
		if (creditsRefreshButton.getVisibility() == View.GONE) {
			creditsRefreshButton.setVisibility(View.VISIBLE);
		}
		if (creditsRefreshProgress.getVisibility() == View.VISIBLE) {
			creditsRefreshProgress.setVisibility(View.GONE);
		}
		refreshAccountInfo();
	}

}
