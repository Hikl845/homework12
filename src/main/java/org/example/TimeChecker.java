package org.example;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import static java.util.concurrent.TimeUnit.SECONDS;

public class TimeChecker {

    public static void main(String[] args) {

        long startTime = System.nanoTime();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        // Потік №1 — що 1 секунду показує, скільки часу минуло
        Runnable showElapsedTime = () -> {
            long now = System.nanoTime();
            long elapsedSec = (now - startTime) / 1_000_000_000;
            System.out.println("Минуло: " + elapsedSec + " секунд");
        };

        // Потік №2 — що 5 секунд виводить повідомлення
        Runnable everyFiveSeconds = () -> {
            System.out.println("Минуло 5 секунд");
        };

        // запуск задач
        scheduler.scheduleAtFixedRate(showElapsedTime, 0, 1, SECONDS);
        scheduler.scheduleAtFixedRate(everyFiveSeconds, 5, 5, SECONDS);
    }
}

