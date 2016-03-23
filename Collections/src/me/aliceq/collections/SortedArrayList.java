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

import java.util.Collection;
import java.util.Comparator;

/**
 * A JCF-like ArrayList which maintains a sorted collection of elements.
 *
 * @author Alice Quiros <email@aliceq.me>
 * @param <E> the type of elements in this list
 */
public class SortedArrayList<E> extends AbstractSortedList<E> {

    protected final Comparator comparator;
    protected final int sortMode;
    protected int size = 0;
    protected E[] data;

    /**
     * Basic constructor creating an empty SortedArrayList in ascending order
     */
    public SortedArrayList() {
        this(0, null, true);
    }

    /**
     * Constructor creating an empty SortedArrayList of specified order
     *
     * @param ascending true if the list is ascending, false if descending
     * @throws IllegalArgumentException if sortMode is not equal to -1 or 1
     */
    public SortedArrayList(boolean ascending) {
        this(0, null, ascending);
    }

    /**
     * Constructor creating an empty SortedArrayList of specified order and
     * initial capacity
     *
     * @param initCapacity the initial capacity of the list
     * @param ascending true if the list is ascending, false if descending
     */
    public SortedArrayList(int initCapacity, boolean ascending) {
        this(initCapacity, null, ascending);
    }

    /**
     * Constructor creating an empty SortedArrayList of specified order and
     * initial capacity
     *
     * @param initCapacity the initial capacity of the list
     * @param c the comparator used to compare elements in the list
     * @param ascending true if the list is ascending, false if descending
     */
    public SortedArrayList(int initCapacity, Comparator<E> c, boolean ascending) {
        this.data = (E[]) new Object[initCapacity];
        this.sortMode = ascending ? -1 : 1;

        if (c == null) {
            this.comparator = new Comparator<E>() {

                @Override
                public int compare(E o1, E o2) {
                    if (o1 instanceof Comparable) {
                        return ((Comparable) o1).compareTo(o2);
                    } else {
                        return o2.hashCode() - o1.hashCode();
                    }
                }
            };
        } else {
            this.comparator = c;
        }
    }

    /**
     * Constructor creating a SortedArrayList initialized with a set of values
     *
     * @param c collection of values to add on initialization. This is done by
     * calling the addAll method.
     */
    public SortedArrayList(Collection<? extends E> c) {
        this(c, null, true);
    }

    /**
     * Constructor creating a SortedArrayList initialized with a set of values
     *
     * @param c collection of values to add on initialization. This is done by
     * calling the addAll method.
     * @param ascending true if the list is ascending, false if descending
     * @throws IllegalArgumentException if sortMode is not equal to -1 or 1
     */
    public SortedArrayList(Collection<? extends E> c, boolean ascending) {
        this(c.size(), null, ascending);
        for (E e : c) {
            this.add(e);
        }
    }

    /**
     * Constructor creating a SortedArrayList initialized with a set of values
     *
     * @param c collection of values to add on initialization. This is done by
     * calling the addAll method.
     * @param comparator the comparator used to compare elements in the list
     * @param ascending true if the list is ascending, false if descending
     * @throws IllegalArgumentException if sortMode is not equal to -1 or 1
     */
    public SortedArrayList(Collection<? extends E> c, Comparator<E> comparator, boolean ascending) {
        this(c.size(), comparator, ascending);
        for (E e : c) {
            this.add(e);
        }
    }

    @Override
    public Object[] toArray() {
        Comparable[] clone = new Comparable[size];
        System.arraycopy(data, 0, clone, 0, size);
        return clone;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length >= size) {
            // Copy and return a
            System.arraycopy(data, 0, a, 0, size);
            for (int i = size; i < a.length; i++) {
                a[i] = null;
            }
            return a;
        } else {
            // Return new array
            if (a instanceof Object[]) {
                return (T[]) toArray(); // This probably won't work....
            } else {
                throw new ClassCastException();
            }
        }
    }

    @Override
    public boolean add(E e) {
        // Increment count
        modCount++;

        // Target array
        E[] target = data;

        // Get the index to insert
        int index = positionOf(e);

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

    @Override
    public E remove(int index) {
        // Increment count
        modCount++;

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        E at = data[index];

        // Shift all back one
        for (int i = index + 1; i < size; i++) {
            data[index - 1] = data[index];
        }

        size--;
        return at;
    }

    @Override
    public void clear() {
        // Increment count
        modCount++;

        size = 0;
    }

    @Override
    public int positionOf(Object o) {
        if (size == 0) {
            return 0;
        }

        int pa = 0, pb = size;

        while (pa < pb) {
            int mid = (pa + pb) / 2;
            int comp = data[mid] == null ? -1 : comparator.compare(data[mid], o) * sortMode;

            if (comp > 0) {
                pa = mid + 1;
            } else if (comp < 0) {
                pb = mid;
            } else {
                return mid;
            }
        }
        return pa;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return data[index];
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(data[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (o.equals(data[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isAscending() {
        return sortMode <= 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Comparator<E> getComparator() {
        return comparator;
    }

    @Override
    public SortedList<E> cloneReverse(int fromIndex, int toIndex) {
        if (fromIndex < 0 || fromIndex > size || toIndex < 0 || toIndex > size) {
            throw new IndexOutOfBoundsException();
        } else if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }

        SortedArrayList<E> newList = new SortedArrayList(this.sortMode > 0);
        newList.size = toIndex - fromIndex;
        newList.data = (E[]) new Comparable[newList.size];
        for (int i = 0; i < newList.size; i++) {
            newList.data[newList.size - i - 1] = this.data[fromIndex + i];
        }
        return newList;
    }

    @Override
    public SortedList<E> cloneRange(int fromIndex, int toIndex) {
        if (fromIndex < 0 || fromIndex > size || toIndex < 0 || toIndex > size) {
            throw new IndexOutOfBoundsException();
        } else if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }

        SortedArrayList<E> newList = new SortedArrayList(this.sortMode <= 0);
        newList.size = toIndex - fromIndex;
        newList.data = (E[]) new Comparable[newList.size];
        System.arraycopy(this.data, fromIndex, newList.data, 0, newList.size);
        return newList;
    }

    @Override
    public boolean remove(Object o) {
        int indexOf = indexOf(o);
        if (indexOf < 0) {
            return false;
        }
        remove(indexOf);
        return true;
    }

    @Override
    public boolean contains(Object o) {
        int index = positionOf(o);
        return data[index].equals(o);
    }

    /**
     * Returns a String representation of the instance. Each item in the list is
     * printed in order between two brackets. The location of the square bracket
     * indicates the smallest item in the list, whereas the location of the
     * curly bracket indicates the largest item in the list. As such, a
     * square-curly bracket sequence indicates ascending order and a
     * curly-square bracket sequence indicates descending order.
     *
     * @return a String representation of the instance
     */
    @Override
    public String toString() {
        if (size == 0) {
            return isAscending() ? "[}" : "{]";
        }

        String s = (isAscending() ? "[" : "{") + data[0];
        for (int i = 1; i < size; i++) {
            s += "," + data[i];
        }
        s += (isAscending() ? "}" : "]");
        return s;
    }
}
