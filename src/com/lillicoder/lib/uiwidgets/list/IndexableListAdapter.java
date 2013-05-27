package com.lillicoder.lib.uiwidgets.list;

import android.util.Log;
import android.widget.BaseExpandableListAdapter;
import android.widget.SectionIndexer;
import junit.framework.Assert;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *     {@link BaseExpandableListAdapter} implementation that can support an
 *     arbitrary collection of {@link IndexableList}.
 * </p>
 *
 * <p>
 *     This adapter is designed to support any collection of indexable lists where each list
 *     has the same indexable key type and list item type. This adapter assumes that there
 *     is a desire to generate two types of views for an associated {@link android.widget.ListView}:
 *     a header view representing the indexable information and child views under a
 *     header representing the list items.
 * </p>
 * @param <K> Type of object each indexable list is indexable by.
 * @param <E> Type of object each indexable list contains.
 */
public abstract class IndexableListAdapter<K extends Comparable<K>, E> extends BaseExpandableListAdapter implements SectionIndexer {

    private List<Object> mFlat;
    private List<IndexableList<K, E>> mData;

    public IndexableListAdapter(List<IndexableList<K, E>> items) {

    }

    private static class Indexer<K extends Comparable<K>, E> implements SectionIndexer {

        private static final String TAG = "IndexableListAdapter.Indexer";

        private static final String PRECONDITION_NULL_ITEMS =
            "Cannot instantiate indexer with null items.";

        private static final String WARNING_POSITION_INDEX_OUT_OF_BOUNDS =
            "Cannot get section index for position %d, positions range is [0,%d].";

        private static final String WARNING_SECTION_INDEX_OUT_OF_BOUNDS =
            "Cannot get starting position for section %d, sections range is [0,%d].";

        private static final int INVALID_POSITION = -1;
        private static final int INVALID_SECTION = -1;

        private CharSequence[] mSections;
        private int[] mSectionSizes;
        private int[] mSectionStartPositions;

        public Indexer(List<IndexableList<K, E>> sections) {
            Assert.assertTrue(PRECONDITION_NULL_ITEMS, sections != null);

            // One section per list
            mSections = new CharSequence[sections.size()];

            // One element for each section's size and starting position
            mSectionSizes = new int[mSections.length];
            mSectionStartPositions = new int[mSections.length];

            // Populate section sizes and starting positions
            int lastPosition = 0;
            for (int sectionPosition = 0; sectionPosition < sections.size(); sectionPosition++) {
                // Section starting position is the last position we reached
                mSectionStartPositions[sectionPosition] = lastPosition;

                IndexableList<K, E> section = sections.get(sectionPosition);
                int sectionSize = section.size();

                // Increase position by section size for next section
                lastPosition += sectionSize;

                // Store section size
                mSectionSizes[sectionPosition] = sectionSize;
            }
        }

        @Override
        public CharSequence[] getSections() {
            return mSections;
        }

        @Override
        public int getPositionForSection(int section) {
            if (section < 0 || section >= mSections.length) {
                Log.w(TAG, String.format(WARNING_SECTION_INDEX_OUT_OF_BOUNDS,
                                         section,
                                         mSections.length));
                return INVALID_POSITION;
            }

            return mSectionStartPositions[section];
        }

        @Override
        public int getSectionForPosition(int position) {
            // Last item position is the last section's start position plus the position of the last item that section
            int lastSectionIndex = mSections.length - 1;
            int lastSectionStartPosition = mSectionStartPositions[lastSectionIndex];
            int lastSectionLastItemPosition = mSectionSizes[lastSectionIndex] - 1;
            int lastItemPosition = lastSectionStartPosition + lastSectionLastItemPosition;
            if (position < 0 || position > lastItemPosition) {
                Log.w(TAG, String.format(WARNING_POSITION_INDEX_OUT_OF_BOUNDS,
                                         position,
                                         lastItemPosition));
                return INVALID_SECTION;
            }

            int closestSection = Arrays.binarySearch(mSectionStartPositions, position);

            /*
             * Consider this example: section positions are 0, 3, 5; the supplied
             * position is 4. The section corresponding to position 4 starts at
             * position 3, so the expected return value is 1. Binary search will not
             * find 4 in the array and thus will return -insertPosition-1, i.e. -3.
             * To get from that number to the expected value of 1 we need to negate
             * and subtract 2.
             */
            return closestSection >= 0 ? closestSection : closestSection - 2;
        }

    }

}
