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

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * This class provides a skeletal implementation of the Circular interface to
 * provide an easier implementation by subclasses.
 *
 * @author Alice Quiros <email@aliceq.me>
 * @param <E> the type of elements in this structure
 */
public abstract class AbstractCircular<E> implements Circular<E> {

    protected int modCount;

    @Override
    public boolean isEmpty() {
        return count() == 0;
    }

    @Override
    public boolean offer(E e) {
        if (isFull()) {
            return false;
        } else {
            push(e);
            return true;
        }
    }

    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        } else {
            return pop();
        }
    }

    @Override
    public E peek() {
        return peekAhead(0);
    }

    @Override
    public E peekAhead(int index) {
        if (index > count()) {
            return null;
        } else {
            return element(index);
        }
    }

    @Override
    public E element() {
        return element(0);
    }

    @Override
    public Iterator<E> iterator() {
        return new AbstractCircularIterator(this);
    }

    public static class AbstractCircularIterator<E> implements Iterator<E> {

        final private AbstractCircular<E> target;
        private int index = 0;
        private int modCount;

        public AbstractCircularIterator(AbstractCircular<E> target) {
            this.target = target;
            this.modCount = target.modCount;
        }

        private void concurrentModificationCheck() {
            if (this.modCount != target.modCount) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public boolean hasNext() {
            return index < target.count();
        }

        @Override
        public E next() {
            concurrentModificationCheck();
            return target.element(index++);
        }

        @Override
        public void remove() {
            concurrentModificationCheck();
            target.pop();
            modCount++;
        }
    }
}
