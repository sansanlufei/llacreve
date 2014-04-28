package com.huobansoft.evercall.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.huobansoft.evercall.R;
import com.huobansoft.evercall.vo.Account;

public class DialogHelper {
	
	private static final String TAG = "evercall";
	
	private Context mContext;
	public DialogHelper(Context context) {
		this.mContext = context;
	}
	
	public AlertDialog getAccountInfoBindDialog(Account account, final MyAlertDialog.MyAlertDialogListener listener) {
		MyAlertDialog.Builder builder = new MyAlertDialog.Builder(this.mContext);
		LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
		final View view = inflater.inflate(R.layout.account_bind, null);
		EditText phonenumberView = ((EditText) view.findViewById(R.id.account_phonenumber));
		phonenumberView.setText(account.getTel());
		builder.setView(view)
				.setPositiveButton(R.string.button_ok, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						listener.onDialogPositiveClick(dialog, view);
					}
				})
				.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
	    return builder.create();
	}
	
	public AlertDialog getCommonAlertDialog(String title, String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
		builder.setTitle(title).setMessage(msg);
		return builder.create();
	}
	
	
	public void showToast(String text) {
		Toast.makeText(mContext, text, Toast.LENGTH_LONG);
	}
}
