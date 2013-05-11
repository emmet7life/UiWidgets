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
 * Copyright 2013 Scott Weeden-Moody
 *
 * This file has been modified from the FragmentTabs.java sample provided in the framework.
 * This class has had its formatting and style changed to match other code in this library.
 */

package com.lillicoder.lib.uiwidgets.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Utility class that holds the tab information needed by 
 * {@link FragmentTabsAdapter} to add and remove fragments.  
 */
public class TabInfo {
	
	private Bundle _arguments;
	private Class<?> _class;
	private Fragment _fragment;
	private String _tag;
	
	/**
	 * Instantiates a new instance of this class.
	 * @param tag The tag to use for this tab.
	 * @param aClass The class of the {@link Fragment} that will be the content of this tab.
	 * @param arguments Arguments {@link Bundle} for use when instantiating this tab's content fragment.
	 */
	public TabInfo(String tag, Class<?> aClass, Bundle arguments) {
		this._tag = tag;
		this._class = aClass;
		this._arguments = arguments;
	}

	/**
	 * Gets the {@link Bundle} containing arguments used in instantiating this
	 * tab's fragment.
	 * @return Arguments {@link Bundle}.
	 */
	public Bundle getArguments() {
		return this._arguments;
	}
	
	/**
	 * Gets the {@link Fragment} that is the content of this tab.
	 * @return Tab content {@link Fragment}.
	 */
	public Fragment getFragment() {
		return this._fragment;
	}
	
	/**
	 * Gets the class name of this tab's content {@link Fragment}. This
	 * name can be used when instantiating a new instance of the content fragment.
	 * @return The tab content fragment class name.
	 */
	public String getFragmentClassName() {
		return this._class.getName();
	}
	
	/**
	 * Gets this tab's tag.
	 * @return The tag.
	 */
	public String getTag() {
		return this._tag;
	}
	
	/**
	 * Sets this tab's content {@link Fragment}.
	 * @param fragment {@link Fragment} to use as this tab's content.
	 */
	public void setFragment(Fragment fragment) {
		this._fragment = fragment;
	}
	
}
