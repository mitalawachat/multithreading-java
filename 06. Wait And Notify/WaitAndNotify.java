import java.util.Scanner;

class Processor {
    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Producer acquired lock");
            System.out.println("Producer going to waiting state...");
            wait();
            System.out.println("Producer resumed");
            System.out.println("Producer releasing lock");
        }
    }

    public void consume() throws InterruptedException {
        Thread.sleep(1000);
        synchronized (this) {
            System.out.println("Consumer acquired lock");
            Scanner scanner = new Scanner(System.in);
            System.out.println("Waiting for return key...");
            scanner.nextLine();
            System.out.println("Pressed return key");
            scanner.close();
            notify();
            Thread.sleep(3000);
            System.out.println("Consumer releasing lock");
        }
    }
}

public class WaitAndNotify {
    public static void main(String[] args) throws InterruptedException {
        final Processor processor = new Processor();

        Thread producerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread consumerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        producerThread.start();
        consumerThread.start();

        producerThread.join();
        consumerThread.join();
    }
}
