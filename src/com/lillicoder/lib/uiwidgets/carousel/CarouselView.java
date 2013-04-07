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
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.lillicoder.lib.uiwidgets.R;

/**
 * View that displays a page-able set of items with item position indicators.
 */
public class CarouselView extends FrameLayout {
	
	private PagerAdapter mPagerAdapter;
	
	private LinearLayout mIndicatorsContainer;
	private ViewPager mViewPager;
	
	/**
	 * {@link OnPageChangeListener} that handles setting the correct carousel indicator
	 * colors as views are paged.
	 */
	private OnPageChangeListener mIndicatorChangeListener = new OnPageChangeListener() {
		@Override
		public void onPageSelected(int position) {
			int currentPosition = position;
			int itemCount = mPagerAdapter.getCount();
			
			// If we are currently looking at the first item, 
			// we should loop to the last item
			int previousPosition;
			if (currentPosition == 0)
				previousPosition = (itemCount - 1);
			else
				previousPosition = currentPosition - 1;
			
			// If we are currently looking at the last item, 
			// we should should loop to the first item.
			int nextPosition;
			if (currentPosition == itemCount - 1)
				nextPosition = 0;
			else
				nextPosition = currentPosition + 1;
			
			// Update indicator state
			CarouselIndicatorView currentIndicator = getIndicator(position);
			CarouselIndicatorView previousIndicator = getIndicator(previousPosition);
			CarouselIndicatorView nextIndicator = getIndicator(nextPosition);
			if (currentIndicator != null)
				currentIndicator.setActive(true);
			if (previousIndicator != null)
				previousIndicator.setActive(false);
			if (nextIndicator != null)
				nextIndicator.setActive(false);
		}
		
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
		
		@Override
		public void onPageScrollStateChanged(int state) {}
	};
	
	public CarouselView(Context context) {
		this(context, null);
	}
	
	public CarouselView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public CarouselView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.view_carousel, this);
		
		initializeViewReferences();
		
		// Attach the page change listener to handle carousel indicators
		mViewPager.setOnPageChangeListener(mIndicatorChangeListener);
	}
	
	/**
	 * Initializes child view references for this view.
	 */
	private void initializeViewReferences() {
		mIndicatorsContainer = 
			(LinearLayout) findViewById(R.id.CarouselView_indicatorsContainer);
		mViewPager = 
			(ViewPager) findViewById(R.id.CarouselView_viewPager);
	}

	/**
	 * Populates this carousel with the given number of indicators.
	 * @param count Number of item indicators to display.
	 */
	private void generateIndicators(int count) {
		mIndicatorsContainer.removeAllViews();
		
		for (int i = 0; i < count; i++) {
			CarouselIndicatorView indicator = new CarouselIndicatorView(getContext());
			indicator.setActive(i == 0); // We should active the first item by default since the 
										 // page change listener will not fire until paging occurs
			
			mIndicatorsContainer.addView(indicator);
		}
	}
	
	/**
	 * Gets the {@link CarouselIndicatorView} at the given position.
	 * @param position Position of the indicator to get.
	 * @return {@link CarouselIndicatorView} at the given position,
	 * 		   {@code null} if there is no indicator at the given position.
	 */
	private CarouselIndicatorView getIndicator(int position) {
		return (CarouselIndicatorView) mIndicatorsContainer.getChildAt(position);
	}
	
	/**
	 * Set a PagerAdapter that will supply views for this carousel as needed.
	 * @param adapter Adapter to use.
	 */
	public void setPagerAdapter(PagerAdapter adapter) {
		mPagerAdapter = adapter;
		
		mViewPager.setAdapter(adapter);
		generateIndicators(adapter.getCount());
	}
	
}