/**
 * Copyright 2012 Scott Weeden-Moody
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lillicoder.lib.uiwidgets.spinner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

/**
 * {@link PopupWindow} whose content is a general-purpose {@link ListView}.
 */
public class PopupListView extends PopupWindow {

	private ListView _list;
	
	public PopupListView(Context context) {
		this(context, null);
	}
	
	public PopupListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this._list = new ListView(context);
		
		this.setContentView(this._list);
		this.setFocusable(true);
		this.setWindowLayoutMode(0, ViewGroup.LayoutParams.WRAP_CONTENT);
	}

	/**
	 * Returns the adapter currently in use in this window's ListView. 
	 * The returned adapter might not be the same adapter passed 
	 * to setAdapter(ListAdapter) but might be a WrapperListAdapter.
	 * @return The adapter currently used to display data in this window's ListView.
	 */
	public ListAdapter getAdapter() {
		return this._list.getAdapter();
	}

	/**
	 * Sets the data behind this window's ListView. 
	 * The adapter passed to this method may be wrapped by a WrapperListAdapter, 
	 * depending on the ListView features currently in use. For instance, adding 
	 * headers and/or footers will cause the adapter to be wrapped.
	 * @param adapter The {@link ListAdapter} which is responsible for maintaining the 
	 *                data backing this window's list and for producing a view to represent 
	 *                an item in that data set.
	 */
	public void setAdapter(ListAdapter adapter) {
		this._list.setAdapter(adapter);
	}
	
	/**
	 * Set the background to a given resource. 
	 * The resource should refer to a Drawable object or 0 to remove the background.
	 * @param resourceId The identifier of the resource.
	 */
	public void setBackgroundResource(int resourceId) {
		this._list.setBackgroundResource(resourceId);
		
		// Disable the cache color hint to prevent background flicker.
		this._list.setCacheColorHint(0);
		
	}
	
	/**
	 * Register a callback to be invoked when an item in this window's AdapterView has been clicked.
	 * @param listener The callback that will run.
	 */
	public void setOnItemClickListener(OnItemClickListener listener) {
		this._list.setOnItemClickListener(listener);
	}
	
}