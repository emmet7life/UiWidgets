package com.lillicoder.lib.uiwidgets.list;

/**
 * Interface describing an object that can be indexed by an arbitrary key.
 * @param <T> Type of index key implementers may be indexed by.
 */
public interface Indexable<T extends Comparable<T>> extends Comparable<Indexable<T>> {

    /**
     * Gets the index key {@link T} for this object.
     * @return Index key.
     */
    public T getIndexKey();

    /**
     * <p>Gets the label {@link CharSequence} for this object's index key.</p>
     *
     * <p>
     *     The label is a display value that represents a human
     *     readable representation of an index. This label does
     *     not have to correspond to any particular value of
     *     the index key. This value should not be used when indexing
     *     or sorting but rather for logging and display purposes.
     * </p>
     * @return Index label.
     */
    public CharSequence getIndexLabel();

}
