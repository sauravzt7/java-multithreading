package org.example;


import java.util.concurrent.ArrayBlockingQueue;

/**
 * A blocking queue is defined as a queue which blocks the caller of the enqueue method
 * if there’s no more capacity to add the new item being enqueued. Similarly,
 * the queue blocks the dequeue caller if there are no items in the queue.
 * Also, the queue notifies a blocked enqueuing thread when space becomes available
 * and a blocked dequeuing thread when an item becomes available in the queue.
 * @param <T>
 * "size" queue size at any point in time
 * "capacity" total capacity that queue can have
 * "head" pointer to the front of the queue
 * "tail" pointer to the back of the queue
 */

class Demonstration {
    public static void main(String[] args) throws Exception {

        final BlockingQueue<Integer> q = new BlockingQueue<>(5);

        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    for (int i = 0; i < 50; i++) {
                        q.enqueue(new Integer(i));
                        System.out.println("enqueue " + i);
                    }
                }
                catch (Exception e) {

                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 25; i++) {
                        System.out.println("Thread 2 dequeud " + q.dequeue());
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 25; i++) {
                        System.out.println("Thread 3 dequeud " + q.dequeue());
                    }
                } catch (InterruptedException e) {

                }
            }
        });

        t1.start();
        Thread.sleep(4000);
        t2.start();
        t2.join();
        t3.start();
        t1.join();
        t3.join();
    }
}

public class BlockingQueue<T> {
    T[] array;
    Object lock = new Object();
    int size = 0;
    int capacity;
    int head = 0;
    int tail = 0;

    @SuppressWarnings("unchecked")
    public BlockingQueue(int capacity) {
        array = (T[]) new Object[capacity];
        this.capacity = capacity;
    }

    public void enqueue(T t) throws InterruptedException {
        synchronized (lock) {
            while(size == capacity) {
                lock.wait();
            }
            array[tail] = t;
            tail = (tail + 1) % capacity;
            size++;
            lock.notifyAll();
        }

    }

    public T dequeue() throws InterruptedException {
        T item = null;
        synchronized (lock) {
            while(size == 0) {
                lock.wait();
            }

            item = array[head];
            head = (head + 1) % capacity;
            size--;
            lock.notifyAll();
        }
        return item;
    }

}
