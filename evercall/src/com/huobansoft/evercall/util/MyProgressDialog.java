package com.huobansoft.evercall.util;

import android.app.AlertDialog;
import android.content.Context;

public class MyProgressDialog extends AlertDialog {

	public static final int MASK_ACCOUNT_SYNC_WITH_SERVER = 1;
	
	protected MyProgressDialog(Context context) {
		super(context);
	}
	
	public interface MyProgressDialogListener {
		public void onMyProgressDialogCancel(int mask);
	}
}
