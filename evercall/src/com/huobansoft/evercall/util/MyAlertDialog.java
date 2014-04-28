package com.huobansoft.evercall.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

public class MyAlertDialog extends AlertDialog {

	private View mView;
	protected MyAlertDialog(Context context, View view) {
		super(context);
		this.mView = view;
	}
	
	public View getView() {
		return mView;
	}
	
	public interface MyAlertDialogListener {
		public void onDialogPositiveClick(DialogInterface dialog, View view);
        public void onDialogNegativeClick(DialogInterface dialog, View view);
	}
}
