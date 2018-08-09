package ru.bortnikov.happensbefore;

class HappensBeforeThread {

    private static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(()->{
            flag = false;
        });

        thread.start();

        while (thread.isAlive());

        while (flag);
    }
}
