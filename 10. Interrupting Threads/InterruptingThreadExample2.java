import java.util.Random;

public class InterruptingThreadExample2 {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting...");

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();

                // 1E8 = 1*Math.pow(10,8)
                for (int i = 0; i < 1E8; i++) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        System.out.println("Thread is interrupted");
                        break;
                    }
                    Math.sin(random.nextDouble());
                }
            }
        });

        thread1.start();

        Thread.sleep(500);

        thread1.interrupt();

        thread1.join();

        System.out.println("Finished.");
    }
}
