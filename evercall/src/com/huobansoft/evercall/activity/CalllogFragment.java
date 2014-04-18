package com.huobansoft.evercall.activity;

import java.util.List;
import java.util.Vector;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.huobansoft.evercall.R;

public class CalllogFragment extends Fragment implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

	private TabHost mTabHost = null;
	private ViewPager mViewPager = null;
	private MyPagerAdapter mPagerAdapter = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.from(getActivity()).inflate(R.layout.calllog_contact, container, false);
		
		mTabHost = (TabHost) view.findViewById(R.id.tabhost);
		mTabHost.setup();
		mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("全部")
				.setContent(android.R.id.tabcontent));
		mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("来电")
				.setContent(android.R.id.tabcontent));
		mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator("去电")
				.setContent(android.R.id.tabcontent));
		mTabHost.addTab(mTabHost.newTabSpec("tab4").setIndicator("未接")
				.setContent(android.R.id.tabcontent));
		mTabHost.setOnTabChangedListener(this);
		
		mViewPager = (ViewPager) view.findViewById(R.id.calllog_viewpager) ;
		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(new CalllogAllFragment());
		fragments.add(new CalllogInFragment());
		fragments.add(new CalllogOutFragment());
		fragments.add(new CalllogMissedFragment());
		mPagerAdapter = new MyPagerAdapter(getChildFragmentManager(), fragments);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(this);
		
		return view;
	}
	
	
	public class MyPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;
        /**
         * @param fm
         * @param fragments
         */
        public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }
        /* (non-Javadoc)
         * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
         */
        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        /* (non-Javadoc)
         * @see android.support.v4.view.PagerAdapter#getCount()
         */
        @Override
        public int getCount() {
            return this.fragments.size();
        }
    }

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		mTabHost.setCurrentTab(arg0);
	}

	@Override
	public void onTabChanged(String arg0) {
		int pos = this.mTabHost.getCurrentTab();
        this.mViewPager.setCurrentItem(pos);
	}
}
