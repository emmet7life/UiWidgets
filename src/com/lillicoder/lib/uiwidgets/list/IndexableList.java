package com.lillicoder.lib.uiwidgets.list;

import java.util.*;

/**
 * <p>
 *     {@link List} that supports the {@link Indexable} interface.
 * </p>
 *
 * <p>
 *     This list implementation supports any arbitrary index key key and
 *     any arbitrary list item type. The index key type must implement
 *     {@link Comparable} to allow for sorting operations.
 * </p>
 * @param <K> Type of object this list is indexable by.
 * @param <E> Type of object this list contains.
 */
public class IndexableList<K extends Comparable<K>, E> implements Indexable<K>, List<E> {

    private List<E> mItems;

    private K mKey;
    private CharSequence mLabel;

    public IndexableList(K key, CharSequence label) {
        mKey = key;
        mLabel = label;

        mItems = new ArrayList<E>();
    }

    @Override
    public K getIndexKey() {
        return mKey;
    }

    @Override
    public CharSequence getIndexLabel() {
        return mLabel;
    }

    @Override
    public void add(int location, E object) {
        mItems.add(location, object);
    }

    @Override
    public boolean add(E object) {
        return mItems.add(object);
    }

    @Override
    public boolean addAll(int location, Collection<? extends E> collection) {
        return mItems.addAll(location, collection);
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        return mItems.addAll(collection);
    }

    @Override
    public void clear() {
        mItems.clear();
    }

    @Override
    public boolean contains(Object object) {
        return mItems.contains(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return mItems.containsAll(collection);
    }

    @Override
    public E get(int location) {
        return mItems.get(location);
    }

    @Override
    public int indexOf(Object object) {
        return mItems.indexOf(object);
    }

    @Override
    public boolean isEmpty() {
        return mItems.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return mItems.iterator();
    }

    @Override
    public int lastIndexOf(Object object) {
        return mItems.lastIndexOf(object);
    }

    @Override
    public ListIterator<E> listIterator() {
        return mItems.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int location) {
        return mItems.listIterator(location);
    }

    @Override
    public E remove(int location) {
        return mItems.remove(location);
    }

    @Override
    public boolean remove(Object object) {
        return mItems.remove(object);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return mItems.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return mItems.retainAll(collection);
    }

    @Override
    public E set(int location, E object) {
        return mItems.set(location, object);
    }

    @Override
    public int size() {
        return mItems.size();
    }

    @Override
    public List<E> subList(int start, int end) {
        return mItems.subList(start, end);
    }

    @Override
    public Object[] toArray() {
        return mItems.toArray();
    }

    @Override
    public <T> T[] toArray(T[] array) {
        return mItems.toArray(array);
    }

    @Override
    public int compareTo(Indexable<K> another) {
        K anotherKey = another.getIndexKey();
        return mKey.compareTo(anotherKey);
    }

}
