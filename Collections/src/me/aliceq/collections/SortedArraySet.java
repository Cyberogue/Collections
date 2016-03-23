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

import java.util.Set;

/**
 * An implementation of SortedArrayList which prevents duplicate objects from
 * being added
 *
 * @author Alice Quiros <email@aliceq.me>
 * @param <E> the type of elements in this list
 */
public class SortedArraySet<E> extends SortedArrayList<E> implements Set<E> {

    /**
     * Adds an element to the list at its sorted position. If the object already
     * exists, false is returned and nothing is added.
     *
     * @param e object to add
     * @return true if the object was added, false if the object already exists
     * in the list
     */
    @Override
    public boolean add(E e) {
        // Increment count
        modCount++;

        // Target array
        E[] target = data;

        // Get the index to insert
        int index = positionOf(e);

        // Check existance
        if (index < size && data[index] == e) {
            return false;
        }

        // If current array isn't big enough, make a new one but only copy up to the index
        if (++size >= data.length) {
            // Increase array size
            int newCapacity = (data.length * 3) / 2 + 1;  // Sun implementation
            target = (E[]) new Comparable[newCapacity];
            System.arraycopy(data, 0, target, 0, index);
        }

        // Shift values forward
        System.arraycopy(data, index, target, index + 1, size - index - 1);

        // Set index
        target[index] = e;

        data = target;
        return true;
    }

}
