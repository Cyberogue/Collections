package me.aliceq.collections;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;

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
/**
 * This class provides a skeletal implementation of the SortedList interface to
 * provide an easier implementation by subclasses.
 *
 * @see java.util.AbstractList<E>
 * @author Alice Quiros <email@aliceq.me>
 * @param <E> the type of elements in this list
 */
public abstract class AbstractSortedList<E> implements SortedList<E> {

    protected int modCount = 0;

    @Override
    public ListIterator<E> listIterator() {
        return new AbstractSortedListIterator(this, 0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        return new AbstractSortedListIterator(this, index);
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new AbstractSortedListIterator(this, 0);
    }

    @Override
    public boolean contains(Object o) {
        for (E e : this) {
            if (e.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!this.contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            modified |= this.add(e);
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (E e : this) {
            if (c.contains(e)) {
                modified |= this.remove(e);
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        for (E e : this) {
            if (!c.contains(e)) {
                modified |= this.remove(e);
            }
        }
        return modified;
    }

    @Override
    public SortedList<E> cloneReverse() {
        return cloneReverse(0, size());
    }

    @Override
    public E getSmallest() {
        if (isAscending()) {
            return get(0);
        } else {
            return get(size() - 1);
        }
    }

    @Override
    public E getLargest() {
        if (isAscending()) {
            return get(size() - 1);
        } else {
            return get(0);
        }
    }

    /**
     * ListIterator for an AbstractSortedList
     *
     * @param <E> the type of elements in the list
     */
    protected static class AbstractSortedListIterator<E> implements ListIterator<E> {

        private int modCount;
        private int cursor;
        private final AbstractSortedList<E> list;

        public AbstractSortedListIterator(AbstractSortedList<E> list, int initCursor) {
            this.list = list;
            this.modCount = list.modCount;
            this.cursor = initCursor;
        }

        private void concurrentModificationCheck() {
            if (this.modCount != list.modCount) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public boolean hasNext() {
            return cursor < list.size();
        }

        @Override
        public E next() {
            concurrentModificationCheck();
            if (cursor >= list.size()) {
                throw new IndexOutOfBoundsException();
            }
            return list.get(cursor++);
        }

        @Override
        public boolean hasPrevious() {
            return cursor > 0;
        }

        @Override
        public E previous() {
            concurrentModificationCheck();
            if (cursor <= 0) {
                throw new IndexOutOfBoundsException();
            }
            return list.get(--cursor);
        }

        @Override
        public int nextIndex() {
            concurrentModificationCheck();
            if (cursor > list.size()) {
                throw new IndexOutOfBoundsException();
            }

            return ++cursor;
        }

        @Override
        public int previousIndex() {
            concurrentModificationCheck();
            if (cursor < 1) {
                throw new IndexOutOfBoundsException();
            }

            return --cursor;
        }

        @Override
        public void remove() {
            list.remove(cursor);
            if (cursor > list.size()) {
                cursor = list.size();
            }
            this.modCount++;
        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(E e) {
            list.add(e);
            this.modCount++;
        }
    }
}
