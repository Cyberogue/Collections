/*
 * The MIT License
 *
 * Copyright 2016 Alice Quiros <email@aliceq.me>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package me.aliceq.collections;

import java.io.Serializable;
import java.util.Collection;
import java.util.ListIterator;
import java.util.RandomAccess;

/**
 * A custom List-like implementation for sorted lists.
 *
 * An ordered collection (also known as a sequence). Elements are added and
 * stored in a naturally ordered sequence, either by using hashes or a
 * Comparable interface.
 *
 * Sorted lists should be set up such that any subsequence in the list should
 * have the same ordering as the parent list it is from.
 *
 * See java.util.List for a more detailed description
 *
 * @see java.util.List<E>
 * @author Alice Quiros <email@aliceq.me>
 * @param <E> the type of elements in this list
 */
public interface SortedList<E extends Comparable> extends Collection<E>, Iterable<E>, Cloneable, RandomAccess {

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of cloneRange
     * (index < 0 || index >= size())
     */
    public E get(int index);

    /**
     * Returns the smallest element in the list
     *
     * @return the smallest element in the list
     * @throws IndexOutOfBoundsException if the index is out of cloneRange
     * (index < 0 || index >= size())
     */
    public E getSmallest();

    /**
     * Returns the largest element in the list
     *
     * @return the largest element in the list
     * @throws IndexOutOfBoundsException if the index is out of cloneRange
     * (index < 0 || index >= size())
     */
    public E getLargest();

    /**
     * Removes the element at the specified position in this list (optional
     * operation). Shifts any subsequent elements to the left (subtracts one
     * from their indices). Returns the element that was removed from the list.
     *
     * @param index the index of the element to be removed
     * @return the index of the element to be removed
     * @throws UnsupportedOperationException if the remove operation is not
     * supported by this list
     * @throws IndexOutOfBoundsException if the index is out of cloneRange
     * (index < 0 || index
     * >= size())
     */
    public E remove(int index);

    /**
     * Returns the index of the first occurrence of the specified element in
     * this list, or -1 if this list does not contain the element. More
     * formally, returns the lowest index i such that (o==null ? get(i)==null :
     * o.equals(get(i))), or -1 if there is no such index.
     *
     * @param o element to search for
     * @return the index of the first occurrence of the specified element in
     * this list, or -1 if this list does not contain the element
     * @throws ClassCastException if the type of the specified element is
     * incompatible with this list (optional)
     * @throws NullPointerException if the specified element is null and this
     * list does not permit null elements (optional)
     */
    public int indexOf(Object o);

    /**
     * Returns the index of the last occurrence of the specified element in this
     * list, or -1 if this list does not contain the element. More formally,
     * returns the highest index i such that (o==null ? get(i)==null :
     * o.equals(get(i))), or -1 if there is no such index.
     *
     * @param o lement to search for
     * @return the index of the last occurrence of the specified element in this
     * list, or -1 if this list does not contain the element
     * @throws ClassCastException if the type of the specified element is
     * incompatible with this list (optional)
     * @throws NullPointerException if the specified element is null and this
     * list does not permit null elements (optional)
     */
    public int lastIndexOf(Object o);

    /**
     * Returns the position of an object within the list. Unlike indexOf, this
     * does not require that an object exist in the list. If the object already
     * exists, the object's index is returned. If the object doesn't exist, the
     * index points to where the object will be added when inserted. The
     * position of a given object may or may not change are more items are added
     * to the list.
     *
     * @param o element to search for
     * @return the index of the first occurrence of the specified element in
     * this list, or -1 if this list does not contain the element
     * @throws ClassCastException if the type of the specified element is
     * incompatible with this list (optional)
     * @throws NullPointerException if the specified element is null and this
     * list does not permit null elements (optional)
     */
    public int positionOf(Object o);

    /**
     * Returns a list iterator over the elements in this list (in proper
     * sequence).
     *
     * @return a list iterator over the elements in this list (in proper
     * sequence)
     */
    public ListIterator<E> listIterator();

    /**
     * Returns a list iterator over the elements in this list (in proper
     * sequence), starting at the specified position in the list. The specified
     * index indicates the first element that would be returned by an initial
     * call to next. An initial call to previous would return the element with
     * the specified index minus one.
     *
     * @param index index of the first element to be returned from the list
     * iterator (by a call to next)
     * @return index of the first element to be returned from the list iterator
     * (by a call to next)
     * @throws IndexOutOfBoundsException if the index is out of cloneRange
     * (index < 0 || index
     * > size())
     */
    public ListIterator<E> listIterator(int index);

    /**
     * Returns a copy of the sorted list containing the elements at the time
     * between fromIndex (inclusive) and toIndex (exclusive).
     *
     * @param fromIndex low endpoint (inclusive) of the subList
     * @param toIndex high endpoint (exclusive) of the subList
     * @return a copy of the specified range within this list
     * @throws IndexOutOfBoundsException if toIndex or fromIndex are out of
     * range (0 < x < size()) @ throws IllegalArgumentException if fromIndex is
     * greater than toIndex
     */
    public SortedList<E> cloneRange(int fromIndex, int toIndex);

    /**
     * Returns a copy of the sorted list containing all elements in a reversed
     * order. As such, the returned list should be decrementing if the original
     * was ascending, or vice versa.
     *
     * @return a reversed copy of the list
     */
    public SortedList<E> cloneReverse();

    /**
     * Returns a copy of the sorted list containing the elements at the time
     * between fromIndex (inclusive) and toIndex (exclusive) in a reversed
     * order. As such, the returned list should be decrementing if the original
     * was ascending, or vice versa.
     *
     * @param fromIndex low endpoint (inclusive) of the subList
     * @param toIndex high endpoint (exclusive) of the subList
     * @return a reversed copy of the specified range within this list
     * @throws IndexOutOfBoundsException if toIndex or fromIndex are out of
     * range (0 < x < size()) @ throws IllegalArgumentException if fromIndex is
     * greater than toIndex
     */
    public SortedList<E> cloneReverse(int fromIndex, int toIndex);

    /**
     * Returns true if the list is organized in an ascending order, or false if
     * descending order. An ascending list has the smallest value at the first
     * index and largest value at the largest index, and a descending list has
     * the smallest value at the last index and the largest value at the lowest
     * index.
     *
     * @return true if the list is organized in an ascending order
     */
    public boolean isAscending();
}
