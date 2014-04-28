package com.huobansoft.evercall.activity;

import java.util.ArrayList;

import net.youmi.android.AdManager;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.huobansoft.evercall.R;

public class MainActivity extends FragmentActivity {
	
	private static final int[] tabIcons = new int[]{
		R.drawable.device_access_time,
		R.drawable.social_person,
		R.drawable.collections_view_as_grid
	};
	private static final Class<?>[] tabClasses = {
		CalllogFragment.class, 
		ContactsFragment.class,
		CollectionsFragment.class
	};

	private ViewPager pagers;
	private TabsAdapter tabsAdapter;
	private Button searchButton;
	private Button toDialButton;
	private Button toSmsButton;
	private Button toContactAddButton;
	private Button ohtersButton;
	private View bottomPad;
	private View bottomPadOthers;
	private ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//must before setContentView
//		this.requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);  
		setContentView(R.layout.activity_main);
		//youmi
		AdManager.getInstance(this).init("50d9e69db0c03228", "c5ebc4cc4feb3bc9", false);
		
		
		pagers = (ViewPager) this.findViewById(R.id.pagers);
        tabsAdapter = new TabsAdapter(this, pagers);
        
        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//	    actionBar.setDisplayOptions(1, ActionBar.DISPLAY_SHOW_TITLE);
//        actionBar.setTitle(R.string.app_name);
//	    actionBar.setLogo(R.drawable.ic_launcher); //api 14
        //display the action bar's icon and title
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        //display the app back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        for (int i=0; i<tabIcons.length; i++) {
        	ActionBar.Tab tab = actionBar.newTab().setIcon(tabIcons[i]);
        	tabsAdapter.addTab(tab, tabClasses[i], null);
        }
        
        if (savedInstanceState != null) {
        	actionBar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }
        
        initBottomPad();
        
	}
	
	private void initBottomPad() {
		//chenge to center contacts list when search button is clicked
        bottomPad = this.findViewById(R.id.bottom_pad);
        bottomPadOthers = this.findViewById(R.id.bottom_pad_others);
        searchButton = (Button) this.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				actionBar.setSelectedNavigationItem(1);
				bottomPadOthers.setVisibility(View.GONE);
			}
		});
        toDialButton = (Button) this.findViewById(R.id.to_dial_pad);
        toDialButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"));
				startActivity(intent);
				bottomPadOthers.setVisibility(View.GONE);
			}
		});
        toSmsButton = (Button) this.findViewById(R.id.to_sms);
        toSmsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"));
				startActivity(intent);
				bottomPadOthers.setVisibility(View.GONE);
			}
		});
        toContactAddButton = (Button) this.findViewById(R.id.to_contact_add);
        toContactAddButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent =  new Intent(Intent.ACTION_INSERT);
				intent.setType("vnd.android.cursor.dir/person");
				intent.setType("vnd.android.cursor.dir/contact");
				intent.setType("vnd.android.cursor.dir/raw_contact");
				startActivity(intent);
				bottomPadOthers.setVisibility(View.GONE);
			}
		});
        ohtersButton = (Button) this.findViewById(R.id.others_button);
        ohtersButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (View.VISIBLE == bottomPadOthers.getVisibility()) {
					bottomPadOthers.setVisibility(View.GONE);
				} else {
					bottomPadOthers.setVisibility(View.VISIBLE);
				}
			}
		});
	}
	
	
	public class TabsAdapter extends FragmentPagerAdapter implements 
		ActionBar.TabListener, ViewPager.OnPageChangeListener {
	
		private final Context mContext;
	    private final ActionBar mActionBar;
	    private final ViewPager mViewPager;
	    private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
	    
		final class TabInfo {
	        private final Class<?> clss;
	        private final Bundle args;
	
	        TabInfo(Class<?> _class, Bundle _args) {
	            clss = _class;
	            args = _args;
	        }
	    }
		
		public TabsAdapter(FragmentActivity activity, ViewPager pager) {
			super(activity.getSupportFragmentManager());
			mContext = activity;
	        mActionBar = activity.getActionBar();
	        mViewPager = pager;
	        mViewPager.setAdapter(this);
	        mViewPager.setOnPageChangeListener(this);
		}
		
		public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args) {
	        TabInfo info = new TabInfo(clss, args);
	        tab.setTag(info);
	        tab.setTabListener(this);
	        mTabs.add(info);
	        mActionBar.addTab(tab);
	        notifyDataSetChanged();
	    }
		@Override
	    public Fragment getItem(int position) {
	        TabInfo info = mTabs.get(position);
	        return Fragment.instantiate(mContext, info.clss.getName(), info.args);
	    }
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mTabs.size();
		}
		@Override
		public void onPageScrollStateChanged(int arg0) {}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {}
		
		@Override
		public void onPageSelected(int arg0) {
			mActionBar.setSelectedNavigationItem(arg0);
			if (arg0 == 1) {
				bottomPad.setVisibility(View.GONE);
				bottomPadOthers.setVisibility(View.GONE);
			} else {
				bottomPad.setVisibility(View.VISIBLE);
			}
		}
	
		@Override
		public void onTabReselected(Tab arg0, FragmentTransaction arg1) {}
		
		@Override
		public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
			 Object tag = arg0.getTag();
	            for (int i=0; i<mTabs.size(); i++) {
	                if (mTabs.get(i) == tag) {
	                    mViewPager.setCurrentItem(i);
	                }
	            }
		}
		
		@Override
		public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {}
	}
	
}
