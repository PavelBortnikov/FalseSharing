package ru.bortnikov.happensbefore;


/*
* В данном случае переход монитора из одного блока синхронизации в другой так же влечет за собой установление
* частичного порядка happens-before. Кроме того в данном случае он будет двунаправленный. В одном направлении, когда
* в блок зайдет один поток, и в другом когда зайдет другой поток.
* */
class HappensBeforeSynchronize {

    private static boolean flag = true;
    private static Object ref = new Object();

    public static void main(String[] args) {

        new Thread(()->{
            synchronized (ref) {
                flag = false;
                Class c = void.class;
                c.getFields();
                System.out.println(c);
            }
        }).start();

        while (true) {
            synchronized (ref) {
                System.out.println(flag);
            }
        }
    }
}
