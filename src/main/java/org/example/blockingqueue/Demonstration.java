package org.example.blockingqueue;



class Demonstration {
//    public static void main(String[] args) throws Exception {
//
//        final BlockingQueue<Integer> q = new BlockingQueue<>(5);
//
//        Thread t1 = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                try {
//                    for (int i = 0; i < 50; i++) {
//                        q.enqueue(new Integer(i));
//                        System.out.println("enqueue " + i);
//                    }
//                } catch (Exception e) {
//
//                }
//            }
//        });
//
//        Thread t2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    for (int i = 0; i < 25; i++) {
//                        System.out.println("Thread 2 dequeud " + q.dequeue());
//                    }
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//
//
//        Thread t3 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    for (int i = 0; i < 25; i++) {
//                        System.out.println("Thread 3 dequeud " + q.dequeue());
//                    }
//                } catch (InterruptedException e) {
//
//                }
//            }
//        });
//
//        t1.start();
//        Thread.sleep(4000);
//        t2.start();
//        t2.join();
//        t3.start();
//        t1.join();
//        t3.join();
//    }

    public static void main(String[] args) throws Exception {
        final BlockingQueueWithMutex<Integer> q = new BlockingQueueWithMutex<Integer>(5);

        Thread producer1 = new Thread(new Runnable() {

            public void run() {
                int i = 1;
                try{
                    while(true) {
                        q.enqueue(i);
                        System.out.println("Producer thread 1 enqueued " + i);
                        i++;
                    }
                }
                catch (Exception e) {
                }
            }
        });


        Thread producer2 = new Thread(new Runnable() {

            public void run() {
                int i = 5000;
                try{
                    while(true) {
                        q.enqueue(i);
                        System.out.println("Producer thread 1 enqueued " + i);
                        i++;
                    }
                }
                catch (Exception e) {
                }
            }
        });

        Thread producer3 = new Thread(new Runnable() {

            public void run() {
                int i = 10000;
                try{
                    while(true) {
                        q.enqueue(i);
                        System.out.println("Producer thread 1 enqueued " + i);
                        i++;
                    }
                }
                catch (Exception e) {
                }
            }
        });

        Thread consumer1 = new Thread(new Runnable() {
            public void run() {
                while(true) {
                    System.out.println( "Consumer Thread 1 dequeued" + q.dequeue());
                }
            }
        });

        Thread consumer2 = new Thread(new Runnable() {
            public void run() {
                while(true) {
                    System.out.println( "Consumer Thread 2 dequeued " + q.dequeue());
                }
            }
        });

        Thread consumer3 = new Thread(new Runnable() {
            public void run() {
                while(true) {
                    System.out.println( "Consumer Thread 3 dequeued " + q.dequeue());
                }
            }
        });

        producer1.setDaemon(true);
        producer2.setDaemon(true);
        producer3.setDaemon(true);
        consumer1.setDaemon(true);
        consumer2.setDaemon(true);
        consumer3.setDaemon(true);

        producer1.start();
        producer2.start();
        producer3.start();

        consumer1.start();
        consumer2.start();
        consumer3.start();

        Thread.sleep(1000);
    }
}
