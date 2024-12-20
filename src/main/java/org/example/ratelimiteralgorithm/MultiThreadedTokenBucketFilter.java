package org.example.ratelimiteralgorithm;

import java.util.HashSet;
import java.util.Set;

public class MultiThreadedTokenBucketFilter {
    private long possibleTokens = 0;
    private int MAX_TOKEN;
    private final int ONE_SEC = 1000;

    public MultiThreadedTokenBucketFilter(int maxToken) {
        this.MAX_TOKEN = maxToken;
        Thread dt = new Thread(() ->{
            daemonThread();
        });
        dt.setDaemon(true);
        dt.start();
    }


    public void daemonThread(){
        while(true){
            synchronized(this){
                if(possibleTokens < MAX_TOKEN){
                    possibleTokens++;
                }
                this.notify();
            }
            try{
                Thread.sleep(ONE_SEC);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void getToken() throws InterruptedException {

        synchronized(this){
            while(possibleTokens == 0){
                this.wait();
            }
            possibleTokens--;
        }

        System.out.println(Thread.currentThread().getName() + " getToken() at " + System.currentTimeMillis() / ONE_SEC + " " + possibleTokens + " tokens Left");
    }

    public static void runTests() throws InterruptedException {
        Set<Thread> allThreads = new HashSet<Thread>();
        final MultiThreadedTokenBucketFilter tokenBucketFilter = new MultiThreadedTokenBucketFilter(1);

        for (int i = 0; i < 10; i++) {

            Thread thread = new Thread(new Runnable() {

                public void run() {
                    try {
                        tokenBucketFilter.getToken();
                    } catch (InterruptedException ie) {
                        System.out.println("We have a problem");
                    }
                }
            });
            thread.setName("Thread_" + (i + 1));
            allThreads.add(thread);
        }

        for (Thread t : allThreads) {
            t.start();
        }

        for (Thread t : allThreads) {
            t.join();
        }

     }
}
