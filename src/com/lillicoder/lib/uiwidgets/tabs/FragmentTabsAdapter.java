/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Copyright 2012 Scott Weeden-Moody
 *
 * This file has been modified from the FragmentTabs.java sample provided in the framework.
 * This class was made stand alone and was modified to work across Honeycomb versions.
 */

package com.lillicoder.lib.uiwidgets.tabs;

import java.util.LinkedHashMap; 
import java.util.Map;
import java.util.Map.Entry;

import junit.framework.Assert;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;

/**
 * Class that handles manipulation of a {@link TabHost} and a {@link ViewPager} in
 * conjunction to provide page-able tabs. This code is taken directly from a Google API
 * sample from v4 of the support library.
 */
public class FragmentTabsAdapter extends FragmentPagerAdapter implements OnTabChangeListener, OnPageChangeListener {

	private final Context _context;
	private final Map<String, TabInfo> _tabs;
	private final TabHost _tabHost;
	private final ViewPager _viewPager;
	
	/**
	 * Class that creates dummy tabs for a {@link TabHost}.
	 */
	private final class DummyTabFactory implements TabContentFactory {

		private final Context _context;
		
		public DummyTabFactory(Context context) {
			this._context = context;
		}
		
		@Override
		public View createTabContent(String tag) {
			View view = new View(this._context);
			view.setMinimumWidth(0);
			view.setMinimumHeight(0);
			return view;
		}
		
	}
	
	/**
	 * Creates a new {@link FragmentTabsAdapter} instance.
	 * @param activity {@link FragmentActivity} containing the tabs and pager.
	 * @param tabHost {@link TabHost} to display the tabs.
	 * @param pager {@link ViewPager} that provides the paging ability.
	 */
	public FragmentTabsAdapter(FragmentActivity activity, TabHost tabHost, ViewPager pager) {
		super(activity.getSupportFragmentManager());
		
		this._context = activity;
		this._tabHost = tabHost;
		this._viewPager = pager;
		
		this._tabs = new LinkedHashMap<String, TabInfo>();
		
		this._tabHost.setOnTabChangedListener(this);
		this._viewPager.setAdapter(this);
		this._viewPager.setOnPageChangeListener(this);
	}
	
	/**
	 * Adds a tab to the tab host with the given parameters.
	 * @param tabSpec {@link TabSpec} to add as a tab.
	 * @param aClass Class to serve as the fragment content for this tab.
	 * @param args {@link Bundle} arguments.
	 */
	public void addTab(TabSpec tabSpec, Class<?> aClass, Bundle args) {
		tabSpec.setContent(new DummyTabFactory(this._context));
		String tag = tabSpec.getTag();
		
		TabInfo info = new TabInfo(tag, aClass, args);
		this._tabs.put(tag, info);
		this._tabHost.addTab(tabSpec);
		
		this.notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return this._tabs.size();
	}
	
	@Override
	public Fragment getItem(int position) {
		TabInfo info = this.getTabInfoByPosition(position);
		Fragment fragment = 
			Fragment.instantiate(this._context, info.getFragmentClassName(), info.getArguments());
		return fragment;
	}
	
	/**
	 * Gets the {@link TabInfo} at the given position.
	 * @param position Position of the {@link TabInfo} to fetch.
	 * @return {@link TabInfo} at the given position,
	 * 		   <code>null</code> if no value was found at that position.
	 */
	private TabInfo getTabInfoByPosition(int position) {
		Assert.assertTrue(position >= 0);
		
		int index = position;
		for (Entry<String, TabInfo> entry : this._tabs.entrySet()) {
			if (index == 0)
				return (TabInfo) entry.getValue();
			else
				index -= 1;
		}
		
		return null;
	}

	@Override
	public void onPageScrollStateChanged(int state) {}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

	@Override
	public void onPageSelected(int position) {
		// According to the sample comments, the TabHost
		// will automatically put focus on the current tab
		// when the tab changes, thus taking focus from the
		// ViewPager. This code functions as a workaround to
		// prevent this behavior by getting the current focus
		// state, disabling focus change for descendant views,
		// then restoring the old focus state.
		TabWidget widget = this._tabHost.getTabWidget();
		int oldFocusability = widget.getDescendantFocusability();
		widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
		
		this._tabHost.setCurrentTab(position);
		
		widget.setDescendantFocusability(oldFocusability);
	}

	@Override
	public void onTabChanged(String tabId) {
		int position = this._tabHost.getCurrentTab();
		this._viewPager.setCurrentItem(position);
	}

}