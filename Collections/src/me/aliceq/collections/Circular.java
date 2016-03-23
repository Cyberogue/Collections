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

/**
 * A Queue-like interface for circular buffers and queues. A circular data
 * structure is one which stores items in a rotating order.
 *
 * @author Alice Quiros <email@aliceq.me>
 * @param <E> the type of elements in this structure
 */
public interface Circular<E> extends Iterable<E> {

    /**
     * Returns the number of elements in the structure
     *
     * @return the number of elements in the structure
     */
    public int count();

    /**
     * Returns true if the structure contains no elements. Attempting to pop an
     * object when there is no more room should throw an IllegalStateException.
     *
     * @return true if the structure is empty
     */
    public boolean isEmpty();

    /**
     * Returns true if the structure can not have any further elements added to
     * it. For a data structure of dynamic size, this should return false.
     * Attempting to push an object when there is no more room should throw an
     * IllegalStateException.
     *
     * @return true if the structure is full
     */
    public boolean isFull();

    /**
     * Clears the queue, removing all elements
     */
    public void clear();

    /**
     * The head pointer marks the front of the data structure and is usually
     * where objects are added.
     *
     * @return the head position
     */
    public int head();

    /**
     * The tail pointer marks the back of the data structure and is usually
     * where objects are removed from. The tail pointer is always behind the
     * head pointer but may not necessarily be less than the head pointer.
     *
     * @return the tail position
     */
    public int tail();

    /**
     * Pushes an element into the data structure.
     *
     * @param e element to push
     * @throws IllegalStateException if the structure is full
     */
    public void push(E e);

    /**
     * Removes the element in the data structure.
     *
     * @return the element
     * @throws IllegalStateException if the structure is empty
     */
    public E pop();

    /**
     * Returns but does not remove the element in the data structure.
     *
     * @return the element
     * @throws IllegalStateException if the structure is empty
     */
    public E element();

    /**
     * Returns but does not remove an element in the data structure.
     *
     * @param index the index of the element relative to the tail pointer
     * @return the element at the given position
     * @throws IllegalStateException if the structure is empty
     * @throws IndexOutOfBoundsException if the index is larger than the number
     * of elements in the list
     */
    public E element(int index);

    /**
     * Pushes an element into the data structure.
     *
     * @param e element to push
     * @return true if the element was successfully added
     */
    public boolean offer(E e);

    /**
     * Removes the element in the data structure.
     *
     * @return the element or null if the structure is empty
     */
    public E poll();

    /**
     * Returns but does not pop the element in the data structure.
     *
     * @return the element or null if the structure is empty
     */
    public E peek();

    /**
     * Returns but does not remove an element in the data structure. If no
     * element exists at the given index, null is returned. Note that this
     * method still throws an IndexOutOfboundsException.
     *
     * @param index the index of the element relative to the tail pointer
     * @return the element at the given position or null
     * @throws IndexOutOfBoundsException if the index is larger than the number
     * of elements in the list
     */
    public E peekAhead(int index);

}
