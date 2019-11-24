import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SynchronizedBlockExample {

    private Random random = new Random();

    private List<Integer> list1 = new ArrayList<>();
    private List<Integer> list2 = new ArrayList<>();

    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public void process() {
        for (int i = 0; i < 1000; i++) {
            processStageOne();
            processStageTwo();
        }
    }

    public void processStageOne() {
        synchronized (lock1) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list1.add(random.nextInt());
        }
    }

    public void processStageTwo() {
        synchronized (lock2) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list2.add(random.nextInt());
        }
    }

    public static void main(String[] args) {
        SynchronizedBlockExample example = new SynchronizedBlockExample();

        System.out.println("Starting...");

        long start = System.currentTimeMillis();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                example.process();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                example.process();
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        System.out.print("Time Taken = " + (end - start) + "; ");
        System.out.print("List 1 Size = " + example.list1.size() + "; ");
        System.out.print("List 2 Size = " + example.list2.size() + "\n");
    }
}
