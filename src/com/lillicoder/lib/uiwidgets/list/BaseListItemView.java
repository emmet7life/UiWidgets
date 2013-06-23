package com.lillicoder.lib.uiwidgets.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import com.lillicoder.lib.uiwidgets.util.Styles;

public abstract class BaseListItemView extends LinearLayout {

    public BaseListItemView(Context context) {
        super(context);
        initialize();
    }

    public BaseListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public BaseListItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    /**
     * Initializes this view.
     */
    private void initialize() {
        Context context = getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(getLayoutResource(), this);

        int height = (int) Styles.getDimensionAttribute(context, android.R.attr.listPreferredItemHeight);
        if (height == Styles.INVALID_DIMENSION) {
            height = AbsListView.LayoutParams.WRAP_CONTENT;
        }

        AbsListView.LayoutParams params =
            new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, height);
        setLayoutParams(params);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    /**
     * Gets the resource ID of the layout for this view.
     * @return Resource ID of the layout for this view.
     */
    public abstract int getLayoutResource();

}
