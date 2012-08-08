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

package com.lillicoder.lib.uiwidgets.quickaction;

import android.view.View.OnClickListener;

/**
 * Model for quick action items. Each item consists
 * of its icon resource and its click behavior.
 */
public class QuickActionItem {

	private int _iconResourceId;
	private OnClickListener _onClickListener;
	
	public QuickActionItem(int iconResourceId, OnClickListener listener) {
		this.setIcon(iconResourceId);
		this.setOnClickListener(listener);
	}
	
	public int getIcon() {
		return this._iconResourceId;
	}
	
	public OnClickListener getOnClickListener() {
		return this._onClickListener;
	}
	
	public void setIcon(int iconResourceId) {
		this._iconResourceId = iconResourceId;
	}
	
	public void setOnClickListener(OnClickListener listener) {
		this._onClickListener = listener;
	}
	
}