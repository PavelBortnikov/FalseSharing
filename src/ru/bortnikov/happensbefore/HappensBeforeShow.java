package ru.bortnikov.happensbefore;

/*
* Чтение и запись volatile переменной устанавливает happends-before отношение. Если flag не был бы volatile, тогда
* нельзя было бы гарантирвать, что цикл вообще остановится! И даже если он остановится, то нельзя гарантировать, что
* последняя выведенная цифра будет 1. В случае с volatile это гарантировать можно, т.к. устанавливается частичный
* порядок. Т.к. установка value в 1 происходит до записи-чтения volatile переменной, то на момент чтения flag значение
* value будет 1. Это показывает, что volatile это не просто отключение кэшей для этой переменной, а еще некоторый
* барьер синхронизации.
* */

public class HappensBeforeShow {

    private volatile static boolean flag = true;
    private static int value = 0;

    public static void main(String[] args) throws InterruptedException {

        new Thread(()->{
            while (flag)
                System.out.println(value);
            System.out.println(value);
        }).start();

        Thread.sleep(1);
        value = 1;
        flag = false;
    }
}

