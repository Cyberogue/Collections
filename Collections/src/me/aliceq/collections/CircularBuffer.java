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
 * A circular buffer is a fixed-size queue with a circular data implementation.
 *
 * @author Alice Quiros <email@aliceq.me>
 * @param <E> the type of elements in this structure
 */
public class CircularBuffer<E> extends AbstractCircular<E> {

    protected final E[] data;
    protected int head, tail;

    /**
     * Creates a circular buffer. By default the buffer can hold 16 objects.
     */
    public CircularBuffer() {
        this(16);
    }

    /**
     * Creates a circular buffer
     *
     * @param size the size of the buffer
     */
    public CircularBuffer(int size) {
        this.data = (E[]) new Object[size];
        this.head = 0;
        this.tail = 0;
        this.modCount = 0;
    }

    @Override
    public int count() {
        int rtail = relativeTail();
        return head - rtail;
    }

    @Override
    public boolean isFull() {
        return count() == data.length;
    }

    @Override
    public int head() {
        return head;
    }

    @Override
    public int tail() {
        return tail;
    }

    @Override
    public synchronized void push(E e) {
        if (isFull()) {
            throw new IllegalStateException("Adding to full buffer");
        } else if (head >= data.length) {
            head = 0;
        }
        data[head++] = e;
        modCount++;
    }

    @Override
    public synchronized E pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Removing from empty buffer");
        } else if (tail >= data.length) {
            tail = 0;
        }
        E e = data[tail];
        data[tail] = null;
        tail++;
        modCount++;
        return e;
    }

    @Override
    public synchronized E element(int index) {
        if (index < 0 || index > count()) {
            throw new IndexOutOfBoundsException();
        }

        int i = tail + index;
        if (i >= data.length) {
            i -= data.length;
        }
        return data[i];
    }

    @Override
    public void clear() {
        tail = 0;
        head = 0;
    }

    @Override
    public String toString() {
        if (tail == head || data.length == 0) {
            return "{}";
        }
        int index = tail >= data.length ? tail - data.length : tail;
        String s = "{" + data[index++];
        while (index != head) {
            if (index >= data.length) {
                index -= data.length;
            }
            s += "," + data[index++];
        }
        s += "}";
        return s;
    }

    /**
     * The tail pointer relative to the head pointer. This makes sure that tail
     * remains less than head.
     *
     * @return a new tail pointer lower than head
     */
    public int relativeTail() {
        if (tail <= head) {
            return tail;
        } else {
            return tail - data.length;
        }
    }

    public static void main(String[] args) {
        Circular<Character> b = new CircularBuffer(4);
        java.util.Random r = new java.util.Random();

        b.push('a');
        b.push('a');
        b.push('a');
        b.push('a');
        b.push('a');
        b.push('a');
    }
}
