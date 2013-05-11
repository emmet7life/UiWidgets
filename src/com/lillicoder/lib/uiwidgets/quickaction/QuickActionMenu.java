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

package com.lillicoder.lib.uiwidgets.quickaction;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.*;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.lillicoder.lib.uiwidgets.R;
import junit.framework.Assert;

import java.util.List;

/**
 * {@link View} that displays one or more {@link QuickActionItemView} as a popup menu.
 */
public class QuickActionMenu {

	private static final String EXCEPTION_INVALID_CONSTRUCTOR_PARAMS = 
		"The given view anchor must not be null!";
	
	private ImageView _downArrow;
	private ImageView _upArrow;
	private LinearLayout _itemsContainer;
	private LinearLayout _root;
	private PopupWindow _window;
	private View _anchor;

	/**
	 * Instantiates a new instance with the given {@link View} serving
	 * as the anchor for this menu.
	 * @param anchor {@link View} this menu will be anchored to.
	 */
	public QuickActionMenu(View anchor) {
		this(anchor, null);
	}
	
	/**
	 * Instantiates a new instance populated with the given collection
	 * of {@link QuickActionItem} with the given {@link View} serving
	 * as the anchor for this menu.
	 * @param anchor {@link View} this menu will be anchored to.
	 * @param items {@link List} of {@link QuickActionItem} this menu will display.
	 */
	public QuickActionMenu(View anchor, List<QuickActionItem> items) {
		if (anchor == null)
			throw new IllegalArgumentException(EXCEPTION_INVALID_CONSTRUCTOR_PARAMS);
		
		this._anchor = anchor;
		
		// Initialize layout and view references.
		LayoutInflater inflater = LayoutInflater.from(anchor.getContext());
		this._root = 
			(LinearLayout) inflater.inflate(R.layout.view_quick_action_menu, null);
		this.initializeViewReferences(this._root);

		// Configure popup window for the quick action. It should have no background,
		// wrap its content, and close when touch events are dispatched outside of its 
		// content region (achieved by setting focusable).
		this._window = new PopupWindow(anchor.getContext());
		this._window.setBackgroundDrawable(new BitmapDrawable());
		this._window.setContentView(this._root);
		this._window.setFocusable(true);
		this._window.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
		this._window.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		
		if (items != null)
			this.addQuickActions(items);
	}
	
	/**
	 * Adds the given {@link QuickActionItem} to this menu.
	 * @param item {@link QuickActionItem} to add.
	 */
	public void addQuickAction(QuickActionItem item) {
		if (item != null)
			this.addQuickActionToMenu(item);
	}

	/**
	 * Populates this quick action menu with action items from the
	 * given collection of {@link QuickActionItem}.
	 */
	public void addQuickActions(List<QuickActionItem> items) {
		for (QuickActionItem item : items)
			this.addQuickActionToMenu(item);
	}
	
	/**
	 * Adds the given {@link QuickActionItem} to this menu. This method
	 * generates the actual view instance that is placed into this menu's
	 * item container.
	 * @param item {@link QuickActionItem} to add.
	 */
	private void addQuickActionToMenu(QuickActionItem item) {
		LinearLayout.LayoutParams itemParams = 
			this.getQuickActionItemLayoutParams();
		LinearLayout.LayoutParams ruleParams = 
			this.getQuickActionRuleLayoutParams();
		Context context = this._itemsContainer.getContext();
		
		View quickActionItem = new QuickActionItemView(context, item);
		this._itemsContainer.addView(quickActionItem, itemParams);
		
		View rule = new View(context);
		rule.setBackgroundResource(R.color.QuickActionMenu_ruleBackground);
		this._itemsContainer.addView(rule, ruleParams);
	}
	
	/**
	 * Dismisses this quick action menu if showing. If not showing,
	 * this method does nothing.
	 */
	protected void dismiss() {
		this._window.dismiss();
	}

	/**
	 * Gets the anchor {@link View} for this quick action menu.
	 * @return Anchor {@link View} for this quick action menu.
	 */
	protected View getAnchor() {
		return this._anchor;
	}
	
	/**
	 * Initializes view references for this quick action from the given
	 * root {@link View} of this menu.
	 * @param root Root {@link View} of this menu.
	 */
	private void initializeViewReferences(View root) {
		Assert.assertTrue(root != null);
		
		this._downArrow = 
			(ImageView) root.findViewById(R.id.QuickActionMenu_downArrow);
		this._upArrow = 
			(ImageView) root.findViewById(R.id.QuickActionMenu_upArrow);
		this._itemsContainer = 
			(LinearLayout) root.findViewById(R.id.QuickActionMenu_itemsContainer);
	}
	
	/**
	 * Gets the {@link LinearLayout.LayoutParams} that defines quick action item sizing.
	 * @return {@link LinearLayout.LayoutParams} that defines quick action item sizing.
	 */
	protected LinearLayout.LayoutParams getQuickActionItemLayoutParams() {
		LinearLayout.LayoutParams quickActionItemParams = 
			new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
		quickActionItemParams.weight = 1;
		
		return quickActionItemParams;
	}
	
	/**
	 * Gets the {@link LinearLayout.LayoutParams} that defines quick action item rule sizing.
	 * @return{@link LinearLayout.LayoutParams} that defines quick action item rule sizing.
	 */
	protected LinearLayout.LayoutParams getQuickActionRuleLayoutParams() {
		float ruleWidth = 
			this._itemsContainer.getResources().getDimension(R.dimen.QuickActionMenu_ruleWidth);
		float ruleHeight = 
			this._itemsContainer.getResources().getDimension(R.dimen.QuickActionMenu_ruleHeight);
		LinearLayout.LayoutParams ruleParams = 
			new LinearLayout.LayoutParams(Math.round(ruleWidth), Math.round(ruleHeight));
		
		return ruleParams;
	}
	
	/**
	 * Sets the animation style for this quick action menu.
	 * @param showOnTop Boolean indicating if this popup window will show above or below the anchor.
	 */
	protected void setAnimation(boolean showOnTop) {
		// TODO Allow passing of new animation styles into this method.
		// Default animation is same for top or bottom position.
		this._window.setAnimationStyle(R.style.UiWidgets_Animation_QuickActionMenu);
	}
	
	/**
	 * Shows this quick action menu.
	 */
	public void show() {
		// Popup windows do not compensate for screen boundaries when
		// displaying. We should ensure that we do not show
		// the quick action menu below the screen when showing menus
		// anchored to views near the bottom of the device screen.
		// This is done by comparing the positioning of the height of the
		// content region against the device display's height.
		
		// Generate a Rect for the coorindate information of the anchor.
		int[] anchorLocation = new int[2];
		this._anchor.getLocationOnScreen(anchorLocation);
		Rect anchorRect = new Rect(anchorLocation[0], 
								   anchorLocation[1], 
								   anchorLocation[0] + this._anchor.getWidth(), 
								   anchorLocation[1] + this._anchor.getHeight());
		
		// Measure the window contents and find the measured height. We must
		// provide a MeasureSpec that is unspecified as LinearLayout will be adjusting
		// action item sizing in order to fill the height.
		this._root.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		this._root.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), 
						   MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		int windowContentsHeight = this._root.getMeasuredHeight();
		
		// Get the total screen height.
		WindowManager manager = 
			(WindowManager) this._root.getContext().getSystemService(Context.WINDOW_SERVICE);
		int screenWidth = manager.getDefaultDisplay().getWidth();
		
		boolean isOffscreen = windowContentsHeight > anchorRect.top - (anchorRect.height() / 2);
		
		this.showArrow(! isOffscreen, screenWidth - anchorRect.centerX());
		
		this.setAnimation(! isOffscreen);
		
		if (isOffscreen)
			// Adjust popup position by magic factor to keep window on the screen.
			this._window.showAtLocation(this._root, Gravity.NO_GRAVITY, anchorRect.right, anchorRect.bottom - (windowContentsHeight / 4));
		else
			// Popup will show properly, display as a normal drop down with no offset.
			this._window.showAtLocation(this._root, Gravity.NO_GRAVITY, anchorRect.right, (anchorRect.top - windowContentsHeight) + (windowContentsHeight / 4));
	}
	
	private void showArrow(boolean showOnTop, int rightMargin) {
		final View showArrow = (showOnTop) ? this._downArrow : this._upArrow;
        final View hideArrow = (showOnTop) ? this._upArrow : this._downArrow;

        showArrow.setVisibility(View.VISIBLE);
        
        // We need to remove half of the length of the arrow itself in order to get the point
        // of the arrow to align with the center of the anchor.
        showArrow.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
        				  MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        int adjustedRightMargin = rightMargin - (showArrow.getMeasuredWidth() / 2);
        
        ViewGroup.MarginLayoutParams param = 
        	(ViewGroup.MarginLayoutParams) showArrow.getLayoutParams();
        param.rightMargin = adjustedRightMargin;
      
        hideArrow.setVisibility(View.INVISIBLE);
	}
	
}
