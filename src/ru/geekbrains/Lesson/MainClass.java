package ru.geekbrains.Lesson;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainClass {
    public static final int CARS_COUNT = 4;

    public static void main(String[] args) {
        CyclicBarrier preparation = new CyclicBarrier (CARS_COUNT);
        Semaphore tunnelCapacity = new Semaphore (CARS_COUNT/2);
        CountDownLatch finish = new CountDownLatch (CARS_COUNT);
        Lock winner = new ReentrantLock ();

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(tunnelCapacity), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            final int car = i;
            new Thread(() -> {
                try {
                    System.out.println(cars[car].getName () + " готовится");
                    Thread.sleep(500 + (int)(Math.random() * 800));
                    System.out.println(cars[car].getName ()+ " готов");
                    if (preparation.await () > 0) {
                        Thread.sleep (200);
                    } else {
                        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
                    }
                    for (int j = 0; j < race.getStages().size(); j++) {
                    race.getStages().get(j).go(cars[car]);
                    } if (winner.tryLock ()){
                        System.out.println ("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> ПОБЕДИЛ " + cars[car].getName () + " !!!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    finish.countDown ();
                }
            }).start ();
        }
        try {
            finish.await ();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
    }
}
