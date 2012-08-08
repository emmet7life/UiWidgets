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

package com.lillicoder.lib.uiwidgets.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lillicoder.lib.uiwidgets.R;

/**
 * Base class for custom {@link DialogFragment}. This class contains additional utility code
 * designed to make working with dialogs easier. This class removes all default UI framing,
 * so content is controlled by what is returned from {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
 * while providing some basic dialog theming.
 */
public abstract class BaseDialogFragment extends DialogFragment {

	private static final String EXCEPTION_COULD_NOT_FIND_BASE_COMPONENT = 
		"Could not find base dialog UI component. Are you sure you called setContentView()?";
	
	private ViewGroup _contentContainer;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Remove all dialog framing, we will completely style this dialog.
		this.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.UiWidgets_Theme_Dialog);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.dialog_base, null);
		
		this._contentContainer = 
			(ViewGroup) root.findViewById(R.id.BaseDialogFragment_contentContainer);
		
		return root;
	}
	
	/**
	 * Callback made after inflating this dialog's content layout. Use this method
	 * to capture references to dialog content for later use. The given {@link ViewGroup}
	 * is the parent container for dialog content inflated in {{@link #setContentView(int)}.
	 * @param contentContainer Parent {@link ViewGroup} for content inflated in {@link #setContentView(int)}.
	 */
	protected abstract void onInitializeViewReferences(ViewGroup contentContainer);
	
	/**
	 * Determines if this dialog fragment is currently showing.
	 * @return <code>true</code> if showing, <code>false</code> otherwise.
	 */
	public boolean isShowing() {
		return this.getDialog() != null && this.getDialog().isShowing();
	}
	
	/**
	 * Sets the dialog content from a layout resource. This resource
	 * will be inflated, adding all top-level views to the dialog's content region.
	 * @param layoutResource Resource ID to be inflated.
	 */
	protected void setContentView(int layoutResourceId) {
		LayoutInflater inflater = LayoutInflater.from(this.getActivity());
		inflater.inflate(layoutResourceId, this._contentContainer);
		
		this.onInitializeViewReferences(this._contentContainer);
	}
	
	/**
	 * Sets this dialog's title to the given string.
	 * @param Title string.
	 */
	public void setTitle(String title) {
		TextView titleLabel = 
			(TextView) this.getView().findViewById(R.id.BaseDialogFragment_title);
		if (titleLabel != null)
			titleLabel.setText(title);
		else
			throw new IllegalStateException(EXCEPTION_COULD_NOT_FIND_BASE_COMPONENT);
	}
	
}