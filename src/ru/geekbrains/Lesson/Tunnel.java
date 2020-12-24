package ru.geekbrains.Lesson;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    Semaphore tunnelCapacity;
    public Tunnel( Semaphore tunnelCapacity) {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        this.tunnelCapacity = new Semaphore (100);
    }
    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                tunnelCapacity.acquire ();
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                tunnelCapacity.release ();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}