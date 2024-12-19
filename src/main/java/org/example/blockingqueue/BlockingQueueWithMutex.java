package org.example.blockingqueue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueueWithMutex<T> {
    T[] array;
    Lock lock = new ReentrantLock();
    int size = 0;
    int head = 0;
    int tail = 0;
    int capacity;

    public BlockingQueueWithMutex(int capacity) {
        array = (T[]) new Object[capacity];
        this.capacity = capacity;
    }

    public T dequeue() {

        T item = null;

        lock.lock();
        while (size == 0) {
            lock.unlock();
            lock.lock();
        }

        item = array[head];
        array[head] = null;
        head = (head + 1) % capacity;
        size--;
        lock.unlock();
        return item;
    }

    public void enqueue(T t) {
        lock.lock();
        while (size == capacity) {
            lock.unlock();
            lock.lock();
        }

        array[tail] = t;
        size++;
        tail = (tail + 1) % capacity;
        lock.unlock();


    }

}
