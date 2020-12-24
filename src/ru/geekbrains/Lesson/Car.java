package ru.geekbrains.Lesson;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Car  {


    public static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    public static int getCarsCount() {
        return CARS_COUNT;
    }
}

