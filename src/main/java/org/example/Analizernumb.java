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

    public void fizz() {
        while (true) {
            synchronized (this) {
                while (current <= n && !(current % 3 == 0 && current % 5 != 0)) {
                    try { wait(); } catch (InterruptedException ignored) {}
                }
                if (current > n) return;
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
                if (current > n) return;
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
                if (current > n) return;
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
                if (current > n) return;

                try { queue.put(String.valueOf(current)); }
                catch (InterruptedException ignored) {}

                increment();
            }
        }
    }

    public void output() {
        while (true) {
            try {
                String value = queue.take();
                System.out.println(value);
                if (value.matches("\\d+") && Integer.parseInt(value) == n)
                    return;
            } catch (InterruptedException ignored) {}
        }
    }

    public static void main(String[] args) {
        Analizernumb fb = new Analizernumb(15);

        Thread A = new Thread(fb::fizz);
        Thread B = new Thread(fb::buzz);
        Thread C = new Thread(fb::fizzbuzz);
        Thread D = new Thread(fb::number);
        Thread OUT = new Thread(fb::output);

        A.start();
        B.start();
        C.start();
        D.start();
        OUT.start();
    }
}
