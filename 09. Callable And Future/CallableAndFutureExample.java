import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableAndFutureExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        Future<Integer> future = executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("Starting callable...");

                Random random = new Random();
                int duration = random.nextInt(3000);

                if (duration > 2000) {
                    throw new IOException("Sleeping for too long...");
                }

                Thread.sleep(duration);
                System.out.println("Finished callable.");

                return duration;
            }
        });

        executor.shutdown();

        try {
            System.out.println("Waiting for callable to complete...");
            System.out.println("Result=" + future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            IOException exception = (IOException) e.getCause();
            System.out.println("Exception=" + exception.getMessage());
        }
    }
}
