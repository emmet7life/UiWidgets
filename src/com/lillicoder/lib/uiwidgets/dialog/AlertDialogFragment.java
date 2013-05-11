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

package com.lillicoder.lib.uiwidgets.dialog;

import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.lillicoder.lib.uiwidgets.R;

/**
 * {@link BaseDialogFragment} suitable for use as an {@link android.app.AlertDialog}.
 */
public class AlertDialogFragment extends BaseDialogFragment {

    private Button mNegativeButton;
    private Button mNeutralButton;
    private Button mPositiveButton;
    private ViewGroup mButtonContainer;

    private DialogInterface.OnClickListener mNegativeButtonDialogClickListener;
    private View.OnClickListener mNegativeButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mNegativeButtonDialogClickListener != null) {
                mNegativeButtonDialogClickListener.onClick(getDialog(), DialogInterface.BUTTON_NEGATIVE);
            }
        }
    };

    private DialogInterface.OnClickListener mNeutralButtonDialogClickListener;
    private View.OnClickListener mNeutralButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mNeutralButtonDialogClickListener != null) {
                mNeutralButtonDialogClickListener.onClick(getDialog(), DialogInterface.BUTTON_NEUTRAL);
            }
        }
    };

    private DialogInterface.OnClickListener mPositiveButtonDialogClickListener;
    private View.OnClickListener mPositiveButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mPositiveButtonDialogClickListener != null) {
                mPositiveButtonDialogClickListener.onClick(getDialog(), DialogInterface.BUTTON_POSITIVE);
            }
        }
    };

    @Override
    protected int getContentResource() {
        return R.layout.dialog_alert;
    }

    @Override
    protected void onInitializeViewReferences(ViewGroup contentContainer) {
        mNegativeButton =
            (Button) contentContainer.findViewById(R.id.AlertDialogFragment_negativeButton);
        mNeutralButton =
            (Button) contentContainer.findViewById(R.id.AlertDialogFragment_neutralButton);
        mPositiveButton =
            (Button) contentContainer.findViewById(R.id.AlertDialogFragment_positiveButton);
        mButtonContainer =
            (ViewGroup) contentContainer.findViewById(R.id.AlertDialogFragment_buttonContainer);

        // Attach click listeners to each button
        mNegativeButton.setOnClickListener(mNegativeButtonClickListener);
        mNeutralButton.setOnClickListener(mNeutralButtonClickListener);
        mPositiveButton.setOnClickListener(mPositiveButtonClickListener);
    }

    // TODO Implement a builder that returns itself for set button methods
    /**
     * Set a listener to be invoked when the negative button of the dialog is pressed.
     * @param textId The resource id of the text to display in the negative button
     * @param listener The {@link DialogInterface.OnClickListener} to use.
     */
    public void setNegativeButton(final int textId, final DialogInterface.OnClickListener listener) {
        mNegativeButton.setText(textId);
        mNegativeButtonDialogClickListener = listener;
    }

    /**
     * Set a listener to be invoked when the negative button of the dialog is pressed.
     * @param text The text to display in the negative button
     * @param listener The {@link DialogInterface.OnClickListener} to use.
     */
    public void setNegativeButton(final CharSequence text, final DialogInterface.OnClickListener listener) {
        mNegativeButton.setText(text);
        mNegativeButtonDialogClickListener = listener;
    }

    /**
     * Set a listener to be invoked when the neutral button of the dialog is pressed.
     * @param textId The resource id of the text to display in the neutral button
     * @param listener The {@link DialogInterface.OnClickListener} to use.
     */
    public void setNeutralButton(final int textId, final DialogInterface.OnClickListener listener) {
        mNeutralButton.setText(textId);
        mNeutralButtonDialogClickListener = listener;
    }

    /**
     * Set a listener to be invoked when the neutral button of the dialog is pressed.
     * @param text The text to display in the neutral button
     * @param listener The {@link DialogInterface.OnClickListener} to use.
     */
    public void setNeutralButton(final CharSequence text, final DialogInterface.OnClickListener listener) {
        mNeutralButton.setText(text);
        mNeutralButtonDialogClickListener = listener;
    }

    /**
     * Set a listener to be invoked when the positive button of the dialog is pressed.
     * @param textId The resource id of the text to display in the positive button
     * @param listener The {@link DialogInterface.OnClickListener} to use.
     */
    public void setPositiveButton(final int textId, final DialogInterface.OnClickListener listener) {
        mPositiveButton.setText(textId);
        mPositiveButtonDialogClickListener = listener;
    }

    /**
     * Set a listener to be invoked when the positive button of the dialog is pressed.
     * @param text The text to display in the positive button
     * @param listener The {@link DialogInterface.OnClickListener} to use.
     */
    public void setPositiveButton(final CharSequence text, final DialogInterface.OnClickListener listener) {
        mPositiveButton.setText(text);
        mPositiveButtonDialogClickListener = listener;
    }

}
