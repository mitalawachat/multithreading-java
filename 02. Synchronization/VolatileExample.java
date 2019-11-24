import java.util.Scanner;

class Processor extends Thread {

    // in theory, without `volatile` keyword, the variable MAY be cached
    // if it is cached then thread may not see updated value
    private volatile boolean running = true;

    @Override
    public void run() {
        while (running) {
            System.out.println("hello");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown() {
        running = false;
    }
}

public class VolatileExample {
    public static void main(String[] args) {
        Processor processor = new Processor();
        processor.start();

        System.out.println("Press return to stop...");

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        scanner.close();

        processor.shutdown();
    }
}
