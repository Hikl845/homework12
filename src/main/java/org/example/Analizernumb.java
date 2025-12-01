package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class Analizernumb {
    private final int n;
    private int current = 1;

    private final BlockingQueue<String> queue = new ArrayBlockingQueue<>(1);

    public Analizernumb(int n) {
        this.n = n;
    }

    public synchronized int nextNumber() {
        return current;
    }

    public synchronized void increment() {
        current++;
        notifyAll();
    }

    private void sendDone() {
        try { queue.put("DONE"); } catch (InterruptedException ignored) {}
    }

    public void fizz() {
        while (true) {
            synchronized (this) {
                while (current <= n && !(current % 3 == 0 && current % 5 != 0)) {
                    try { wait(); } catch (InterruptedException ignored) {}
                }
                if (current > n) {
                    sendDone();
                    return;
                }
                try { queue.put("fizz"); } catch (InterruptedException ignored) {}
                increment();
            }
        }
    }

    public void buzz() {
        while (true) {
            synchronized (this) {
                while (current <= n && !(current % 5 == 0 && current % 3 != 0)) {
                    try { wait(); } catch (InterruptedException ignored) {}
                }
                if (current > n) {
                    sendDone();
                    return;
                }
                try { queue.put("buzz"); } catch (InterruptedException ignored) {}
                increment();
            }
        }
    }

    public void fizzbuzz() {
        while (true) {
            synchronized (this) {
                while (current <= n && !(current % 15 == 0)) {
                    try { wait(); } catch (InterruptedException ignored) {}
                }
                if (current > n) {
                    sendDone();
                    return;
                }
                try { queue.put("fizzbuzz"); } catch (InterruptedException ignored) {}
                increment();
            }
        }
    }

    public void number() {
        while (true) {
            synchronized (this) {
                while (current <= n &&
                        !(current % 3 != 0 && current % 5 != 0)) {
                    try { wait(); } catch (InterruptedException ignored) {}
                }
                if (current > n) {
                    sendDone();
                    return;
                }
                try { queue.put(String.valueOf(current)); }
                catch (InterruptedException ignored) {}
                increment();
            }
        }
    }

    public void output() {
        int doneCount = 0;

        while (doneCount < 4) {
            try {
                String value = queue.take();

                if (value.equals("DONE")) {
                    doneCount++;
                    continue;
                }

                System.out.println(value);

            } catch (InterruptedException ignored) {}

