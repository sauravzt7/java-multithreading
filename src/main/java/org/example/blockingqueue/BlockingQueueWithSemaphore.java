package org.example.blockingqueue;

import org.example.semaphores.CountingSemaphore;

import java.util.concurrent.Semaphore;

public class BlockingQueueWithSemaphore<T> {

    T[] array;
    int size = 0;
    int head = 0;
    int tail = 0;
    int capacity;

    CountingSemaphore semLock = new CountingSemaphore(1, 1);
    CountingSemaphore semProducer;
    CountingSemaphore semConsumer;


    @SuppressWarnings("unchecked")
    public BlockingQueueWithSemaphore(int capacity) {
        array = (T[]) new Object[capacity];
        this.capacity = capacity;
        this.semProducer = new CountingSemaphore(capacity, capacity);
        this.semConsumer = new CountingSemaphore(capacity, 0);
    }

    public T dequeue() throws InterruptedException {
        T item = null;
        semConsumer.acquire();
        semLock.acquire();
        item = array[head];
        head = (head + 1) % capacity;
        size--;
        semLock.release();
        semProducer.release();
        return item;
    }

    public void enqueue(T t) throws InterruptedException {

        semProducer.acquire();
        semLock.acquire();

        array[tail] = t;
        tail = (tail + 1) % capacity;
        size++;
        semLock.release();
        semConsumer.release();
    }

}
