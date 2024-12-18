package org.example;



class SumExample{

    long start;
    long end;
    long sum = 0;
    static long MAX_NUM = Integer.MAX_VALUE;


    public SumExample(long start, long end){
        this.start = start;
        this.end = end;
    }

    public void add(){
        for(long i = start; i <= end; i++){
            sum += i;
        }
    }

    public static void oneThread(){

        long start = System.currentTimeMillis();
        SumExample example = new SumExample(1, MAX_NUM);
        example.add();
        long end = System.currentTimeMillis();
        System.out.println("One Thread " + example.sum + " time taken" + (end - start));
    }


    public static void twoThread() throws InterruptedException {
        long start = System.currentTimeMillis();

        SumExample example = new SumExample(1, MAX_NUM / 2);
        SumExample example2 = new SumExample((MAX_NUM / 2) + 1, MAX_NUM);

        Thread t1 = new Thread(() -> example.add());
        Thread t2 = new Thread(() -> example2.add());

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        long finalCount = example2.sum + example.sum;

        long end = System.currentTimeMillis();
        System.out.println("Two Thread: " + finalCount + " time taken" + (end - start));
    }

    public static void runTest() throws InterruptedException {
        oneThread();
        twoThread();
    }


}

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        SumExample.runTest();
    }
}