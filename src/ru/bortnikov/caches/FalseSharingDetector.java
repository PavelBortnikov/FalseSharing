package ru.bortnikov.caches;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/*
* Пример демонстрирует FasleSharing. Т.к. переменные статические, то они выделяются в PermGen и JVM делает выравнивание
* памяти, то можно достаточно смело утверждать, что память под val-переменные выделится рядом.
* Эффект заключается в том, что если брать соседние переменные, то они попадают в одну кэш-линию. Когда на одном ядре
* будет изменяться val0 то будет инвалидироваться вся кэш-линия ее содрежащая. Тогда процессу на другом ядре придется
* перечитывать переменную val1, т.к. она лежит в той же кэш-линии.
* Для переменных из одной кэш-линии и из разных скорость работы программы отличается почти в 10 раз!
* */
public class FalseSharingDetector {

    public static volatile long val0 = 0;
    public static volatile long val1 = 0;
    public static volatile long val2 = 0;
    public static volatile long val3 = 0;
    public static volatile long val4 = 0;
    public static volatile long val5 = 0;
    public static volatile long val6 = 0;
    public static volatile long val7 = 0;
    public static volatile long val8 = 0;

    public static void main(String[] args) throws Exception{
        ExecutorService pool = Executors.newFixedThreadPool(2);
        final CountDownLatch latch0 = new CountDownLatch(2);
        final CountDownLatch latch1 = new CountDownLatch(2);
        
        pool.submit(() -> {
            latch0.countDown();
            try {
                latch0.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long t0 = System.nanoTime();
            for(int k = 0; k < 100_000_000; k++)
                val0 = val0 * k;

            long t1 = System.nanoTime();

            System.out.println((t1 - t0)/1000_000);

            latch1.countDown();

        });


        pool.submit(() -> {
            latch0.countDown();
            try {
                latch0.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long t0 = System.nanoTime();
            for(int k = 0; k < 100_000_000; k++)
                val6 = val6 * k;

            long t1 = System.nanoTime();

            System.out.println((t1 - t0)/1000_000);

            latch1.countDown();

        });

        latch1.await();
        pool.shutdownNow();
    }
}
