import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreExample {
    public static void main(String[] args) throws Exception {
        Semaphore semaphore = new Semaphore(1);
        System.out.println("Permits = " + semaphore.availablePermits());

        semaphore.acquire();
        System.out.println("Permits = " + semaphore.availablePermits());

        semaphore.release();
        System.out.println("Permits = " + semaphore.availablePermits());

        semaphore.acquire();
        System.out.println("Permits = " + semaphore.availablePermits());

        boolean gotLock = semaphore.tryAcquire();
        System.out.println("GotLock=" + gotLock);
        System.out.println("Permits = " + semaphore.availablePermits());

        gotLock = semaphore.tryAcquire(3, TimeUnit.SECONDS);
        System.out.println("GotLock=" + gotLock);
        System.out.println("Permits = " + semaphore.availablePermits());

        semaphore.release();
        System.out.println("Permits = " + semaphore.availablePermits());

        // Semaphore is not limited to the number of permits it was created with
        semaphore.release(); // extra release
        System.out.println("Permits = " + semaphore.availablePermits());
    }
}
