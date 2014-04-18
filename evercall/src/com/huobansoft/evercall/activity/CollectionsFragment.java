package com.huobansoft.evercall.activity;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.huobansoft.evercall.R;

public class CollectionsFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		RelativeLayout view = (RelativeLayout) inflater.from(getActivity()).inflate(R.layout.collections, container, false);
		initAds(view);
		return view;
	}
	
	private void initAds(ViewGroup view) {
		//youmi
//		boolean bannerAdOK = net.youmi.android.smart.SmartBannerManager.checkSmartBannerAdConfig(this.getActivity());
//		Toast.makeText(this.getActivity(), "bannerAdOK: " + bannerAdOK, Toast.LENGTH_LONG).show();
		AdView adView = new AdView(this.getActivity(), AdSize.FIT_SCREEN);
		LinearLayout adLayout=(LinearLayout)view.findViewById(R.id.ad_youmi_banner);
		adLayout.addView(adView);
		
		//flurry
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
