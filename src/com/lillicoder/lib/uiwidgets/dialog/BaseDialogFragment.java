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

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lillicoder.lib.uiwidgets.R;

/**
 * Base class for custom {@link DialogFragment}. This class contains additional utility code
 * designed to make working with dialogs easier. This class removes all default UI framing,
 * so content is controlled by what is returned from {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
 * while providing some basic dialog styling.
 */
public abstract class BaseDialogFragment extends DialogFragment {

	private TextView mTitle;
    private View mTitleRule;
    private ViewGroup mButtonContainer;
	private ViewGroup mContentContainer;
	
	private int mStyleResource;
	private int mTitleVisibility;
	private OnDismissListener mDismissListener;

	/**
	 * Instantiates this dialog with the default
	 * dialog theme.
	 */
	protected BaseDialogFragment() {
		this(R.style.UiWidgets_Theme_Dialog);
	}
	
	/**
	 * Instantiates this dialog with the given theme.
	 * @param themeResource Resource ID of the theme for this dialog.
	 */
	protected BaseDialogFragment(int themeResource) {
		mStyleResource = themeResource;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Remove all dialog framing, we will completely style this dialog.
		setStyle(DialogFragment.STYLE_NO_FRAME, R.style.UiWidgets_Theme_Dialog);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// If this dialog was embedded, it will inherit the parent activity's theme rather than
        // have the one set in onCreate(), so we must use a wrapper to apply the style
        ContextThemeWrapper wrapper = new ContextThemeWrapper(getActivity(), mStyleResource);
        LayoutInflater wrappedInflater = inflater.cloneInContext(wrapper);
        View root = wrappedInflater.inflate(R.layout.dialog_base, null);
        
        mTitle =
            (TextView) root.findViewById(R.id.BaseDialogFragment_title);
        mTitleRule =
            root.findViewById(R.id.BaseDialogFragment_titleRule);
        mButtonContainer =
            (ViewGroup) root.findViewById(R.id.BaseDialogFragment_buttonContainer);
		mContentContainer = 
			(ViewGroup) root.findViewById(R.id.BaseDialogFragment_contentContainer);

        // Set title visibility now that views are available
        mTitle.setVisibility(mTitleVisibility);
        mTitleRule.setVisibility(mTitleVisibility);
        
        // Add main dialog content into layout
        int layoutResourceId = getContentResource();
        inflater.inflate(layoutResourceId, mContentContainer);
        onInitializeViewReferences(mContentContainer);

		return root;
	}
	
	@Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        if (mDismissListener != null) {
            mDismissListener.onDismiss(dialog);
        }
    }
	
	/**
     * Gets the resource ID of the layout whose content will be displayed.
     * This resource will be inflated, adding all top-level views to this dialog's
     * content region.
     * @return Resource ID of the layout containing dialog content.
     */
    protected abstract int getContentResource();
	
	/**
	 * Callback made after inflating this dialog's content layout. Use this method
	 * to capture references to dialog content for later use. The given {@link ViewGroup}
	 * is the parent container for dialog content inflated in {{@link #setContentView(int)}.
	 * @param contentContainer Parent {@link ViewGroup} for content inflated in {@link #setContentView(int)}.
	 */
	protected abstract void onInitializeViewReferences(ViewGroup contentContainer);
	
	/**
     * Adds a new {@link Button} to this dialog's button content area.
     * @param buttonType Which type of button to add, can be one of
     *                   {@link DialogInterface#BUTTON_POSITIVE},
     *                   {@link DialogInterface#BUTTON_NEGATIVE},
     *                   or {@link DialogInterface#BUTTON_NEUTRAL}.
     * @param label Button text to display.
     * @param clickListener {@link DialogInterface.OnClickListener} to receive callbacks
     *                      when the button is clicked. Note that this dialog will dismiss
     *                      on button click even if the given listener is null.
     */
    public void addButton(final int buttonType, CharSequence label, final OnClickListener clickListener) {
        Button button = new Button(getActivity());
        button.setTag(buttonType);
        button.setText(label);

        // This button should evenly fill layout space with any other button(s)
        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 1;

        if (clickListener != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClick(getDialog(), buttonType);
                }
            });
        }

        mButtonContainer.addView(button, params);

        // The button container is hidden by default, make it visible now that we have content
        mButtonContainer.setVisibility(View.VISIBLE);
    }
    
    /**
     * Adds a new {@link Button} to this dialog's button content area.
     * @param buttonType Which type of button to add, can be one of
     *                   {@link DialogInterface#BUTTON_POSITIVE},
     *                   {@link DialogInterface#BUTTON_NEGATIVE},
     *                   or {@link DialogInterface#BUTTON_NEUTRAL}.
     * @param labelResourceId Resource ID of the text for the button.
     * @param clickListener {@link DialogInterface.OnClickListener} to receive callbacks
     *                      when the button is clicked. Note that this dialog will dismiss
     *                      on button click even if the given listener is null.
     */
    public void addButton(int buttonType, int labelResourceId, final OnClickListener clickListener) {
        String label = getString(labelResourceId);
        addButton(buttonType, label, clickListener);
    }
    
    /**
     * Determines if this dialog fragment is currently showing.
     * @return true if showing, false otherwise.
     */
    public boolean isShowing() {
        Dialog dialog = getDialog();
        return dialog != null && dialog.isShowing();
    }

    /**
     * Sets whether this dialog is canceled when touched outside the window's bounds.
     * If setting to true, the dialog is set to be cancelable if not already set.
     * @param cancel Whether this dialog should be canceled when touched outside the window.
     */
    public void setCancelOnTouchOutside(boolean cancel) {
        Dialog dialog = getDialog();
        dialog.setCanceledOnTouchOutside(cancel);
    }
    
    /**
     * Sets the {@link OnDismissListener} to receive events when this dialog is dismissed.
     * @param listener Listener whose {@link OnDismissListener#onDismiss(DialogInterface)}
     *                 method will be called on dismiss.
     */
    public void setOnDismissListener(OnDismissListener listener) {
        mDismissListener = listener;
    }
    
    /**
     * Enables or disables the dialog title for this fragment.
     * Disabling the title also hides the title rule that separates the
     * title from dialog content.
     * @param shouldShowTitle {@code true} to show the title,
     *                        {@code false} to hide the title.
     */
    public void setShowTitle(boolean shouldShowTitle) {
        mTitleVisibility = shouldShowTitle ? View.VISIBLE : View.GONE;

        // Only attempt to set the visibility now if the views
        // are available, otherwise wait until onCreateView() has occurred
        if (mTitle != null && mTitleRule != null) {
            mTitle.setVisibility(mTitleVisibility);
            mTitleRule.setVisibility(mTitleVisibility);
        }
    }
    
    /**
     * Sets this dialog's title to the given string.
     * @param title Title to set.
     */
    public void setTitle(CharSequence title) {
        mTitle.setText(title);
    }
    
    /**
     * Sets this dialog's title to the given string resource.
     * If the given resource ID does not refer to a string resource,
     * the dialog title will be set to null.
     * @param titleResourceId Resource ID of the title to set.
     */
    public void setTitle(int titleResourceId) {
        String title = getString(titleResourceId);
        setTitle(title);
    }
	
}