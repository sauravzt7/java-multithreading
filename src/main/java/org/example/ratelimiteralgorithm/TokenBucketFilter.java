package org.example.ratelimiteralgorithm;


import java.util.HashSet;
import java.util.Set;

/**
 * This is an actual interview question asked at Uber and Oracle.
 * Imagine you have a bucket that gets filled with tokens at the rate of 1 token per second.
 * The bucket can hold a maximum of N tokens. Implement a thread-safe class
 * that lets threads get a token when one is available.
 * If no token is available, then the token-requesting threads should block.
 * The class should expose an API called getToken that various threads can call to get a token
 */
public class TokenBucketFilter {

    private int MAX_TOKEN_BUCKET;
    private long lastRequestTime;
    long possibleTokens = 0;


    public TokenBucketFilter(int maxTokens) {
        this.MAX_TOKEN_BUCKET = maxTokens;
        this.lastRequestTime = System.currentTimeMillis();
    }

    public synchronized void getToken() throws InterruptedException {

        possibleTokens += (System.currentTimeMillis() - lastRequestTime) / 1000;
        possibleTokens = Math.min(MAX_TOKEN_BUCKET, possibleTokens);

        if(possibleTokens == 0) {
            Thread.sleep(1000);
        }
        else {
            possibleTokens--;
        }

        lastRequestTime = System.currentTimeMillis();
        System.out.println("Granting " + Thread.currentThread().getName() + " token at " + (System.currentTimeMillis() / 1000) + " " + possibleTokens + " tokens");
    }

    public static void runTestMaxTokenIs1() throws InterruptedException {

        Set<Thread> threadSet = new HashSet<>();

        final TokenBucketFilter tokenBucketFilter = new TokenBucketFilter(1);
        for(int i = 1; i <= 10; i++) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    try {
                        tokenBucketFilter.getToken();
                    }
                    catch (InterruptedException e) {
                        System.out.println("Thread " + Thread.currentThread().getName() + " interrupted");
                    }
                }
            });
            thread.setName("Thread-" + (i));
            threadSet.add(thread);
        }

        for(Thread thread : threadSet) {
            thread.start();
        }
        for(Thread thread : threadSet) {
            thread.join();
        }
    }

    public static void runTestMaxTokenIsTen() throws InterruptedException {

        Set<Thread> threadSet = new HashSet<>();

        final TokenBucketFilter tokenBucketFilter = new TokenBucketFilter(10);
        Thread.sleep(10000);

        for(int i = 1; i <= 12; i++) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    try {
                        tokenBucketFilter.getToken();
                    }
                    catch (InterruptedException e) {
                        System.out.println("Thread " + Thread.currentThread().getName() + " interrupted");
                    }
                }
            });
            thread.setName("Thread-" + (i + 1));
            threadSet.add(thread);
        }

        for(Thread thread : threadSet) {
            thread.start();
        }
        for(Thread thread : threadSet) {
            thread.join();
        }
    }



}
