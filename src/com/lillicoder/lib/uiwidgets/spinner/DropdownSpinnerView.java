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

import junit.framework.Assert;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.lillicoder.lib.uiwidgets.R;

/**
 * {@link Spinner} whose list shows as a {@link PopupListView} anchored to the
 * bottom of this view.
 */
public class DropdownSpinnerView extends FrameLayout {

	private PopupListView _listWindow;
	private TextView _title; // TODO Determine how the main view/title displays general purpose data
	
	public DropdownSpinnerView(Context context) {
		this(context, null);
	}
	
	public DropdownSpinnerView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public DropdownSpinnerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.view_dropdown_spinner, this);
		
		this.initializeViewReferences();
		
		this.setClickable(true);
		this.setFocusable(true);
		
		// TODO Determine what background should be used to provide color state
	}
	
	/**
	 * Dismisses the navigation drop-down. If the drop-down is not
	 * showing this has no effect.
	 */
	public void dismissDropdown() {
		this._listWindow.dismiss();
	}
	
	/**
	 * Initializes child view references for this view.
	 */
	private void initializeViewReferences() {
		this._title = (TextView) this.findViewById(R.id.DropdownSpinnerView_title);
		this._listWindow = new PopupListView(this.getContext());
	}
	
	/**
	 * Determines if this view's drop-down list is currently being displayed.
	 * @return <code>true</code> if this view's drop-down list is being displayed,
	 * 		   <code>false</code> otherwise.
	 */
	public boolean isDropdownShowing() {
		return this._listWindow.isShowing();
	}
	
	/**
	 * Sets the data that backs this view's popup navigation list. The
	 * first item's title in the backing data will be displayed in 
	 * this view's title text.
	 * @param adapter {@link BaseAdapter} to back this view's popup navigation list.
	 */
	public void setAdapter(BaseAdapter adapter) {
		this._listWindow.setAdapter(adapter);
	}
	
	/**
	 * Register a callback to be invoked when an item in this view's dropdown list has been clicked.
	 * @param listener The callback that will run.
	 */
	public void setOnItemClickListener(OnItemClickListener listener) {
		this._listWindow.setOnItemClickListener(listener);
	}
	
	/**
	 * Shows the navigation drop-down list using this view
	 * as the anchor for the drop-down. Alignment
	 * defaults to placing the top-left corner of the drop-down
	 * at the bottom-left corner of this view.
	 */ 
	public void showDropdown() {
		this.showDropdown(this);
	}
	
	/**
	 * Shows the navigation drop-down list using the given
	 * {@link View} as the anchor for the drop-down. 
	 * @param anchor {@link View} to serve as anchor for the navigation
	 * 				 drop-down list.
	 */
	private void showDropdown(View anchor) {
		Assert.assertTrue(anchor != null);
		
		this._listWindow.showAsDropDown(anchor);
	}
	
}