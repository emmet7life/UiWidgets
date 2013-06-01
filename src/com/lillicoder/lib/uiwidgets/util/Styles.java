package com.lillicoder.lib.uiwidgets.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import junit.framework.Assert;

/**
 * Utility class that provides easy access to theme and style information.
 */
public class Styles {

    private static final String TAG = "Styles";

    private static final String PRECONDITION_NULL_CONTEXT =
        "Cannot interact with themes with a null context.";

    private static final String DEBUG_GET_ATTRIBUTE_DIMENSION_NOT_A_DIMENSION =
        "Value for attribute %d is not of TYPE_DIMENSION(%d).";

    public static float INVALID_DIMENSION = -1;

    /**
     * Gets the dimension for the given attribute resource ID. The returned dimension will
     * have the device {@link DisplayMetrics} applied, returning a properly scaled dimension value.
     * @param context {@link Context} to access resource information with.
     * @param attributeResourceId Resource ID of the attribute to get the dimension of.
     * @return Dimension of the given attribute resource ID or {@link #INVALID_DIMENSION} if
     *         the given attribute resource ID does not refer to a dimension.
     */
    public static float getDimensionAttribute(final Context context, final int attributeResourceId) {
        Assert.assertTrue(PRECONDITION_NULL_CONTEXT, context != null);

        TypedValue dimension = new TypedValue();

        boolean shouldResolveReferences = true; // Resolve refs to avoid TYPE_REFERENCE results
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(attributeResourceId, dimension, shouldResolveReferences);

        if (dimension.type != TypedValue.TYPE_DIMENSION) {
            Log.d(TAG, String.format(DEBUG_GET_ATTRIBUTE_DIMENSION_NOT_A_DIMENSION,
                                     attributeResourceId,
                                     TypedValue.TYPE_DIMENSION));
            return INVALID_DIMENSION;
        }

        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dimension.getDimension(metrics);
    }

}
