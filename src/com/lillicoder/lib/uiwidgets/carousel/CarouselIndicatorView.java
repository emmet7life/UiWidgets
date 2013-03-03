/**
 * Copyright 2013 Scott Weeden-Moody
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

package com.lillicoder.lib.uiwidgets.carousel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lillicoder.lib.uiwidgets.R;

/**
 * View that serves as a position indicator for {@link CarouselView}.
 */
public class CarouselIndicatorView extends FrameLayout {

	private ImageView mIndicator;
	
	public CarouselIndicatorView(Context context) {
		this(context, null);
	}
	
	public CarouselIndicatorView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public CarouselIndicatorView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.view_carousel_indicator, this);
		
		initializeViewReferences();
	}
	
	/**
	 * Initializes child view references for this view.
	 */
	private void initializeViewReferences() {
		mIndicator = 
			(ImageView) findViewById(R.id.CarouselIndicatorView_indicator);
	}
	
	/**
	 * Sets the view as active. When active, the active color state
	 * is used for this indicator. When inactive, the inactive color state
	 * is used for this indicator.
	 * @param isActive {@code true} to set this indicator as active,
	 * 				   {@code false} to set this indicator as inactive.
	 */
	public void setActive(boolean isActive) {
		if (isActive)
			mIndicator.setImageResource(R.drawable.carousel_indicator_active);
		else
			mIndicator.setImageResource(R.drawable.carousel_indicator_inactive);
	}
	
}