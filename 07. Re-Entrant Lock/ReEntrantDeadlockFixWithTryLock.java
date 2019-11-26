import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Account {
    private int balance = 10000;

    public void deposit(int amount) {
        this.balance += amount;
    }

    public void withdraw(int amount) {
        this.balance -= amount;
    }

    public int getBalance() {
        return this.balance;
    }

    public static void transfer(Account sender, Account receiver, int amount) {
        sender.withdraw(amount);
        receiver.deposit(amount);
    }
}

class Runner {
    private Account account1 = new Account();
    private Account account2 = new Account();

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    private void acquireLock(Lock lock1, Lock lock2) throws InterruptedException {
        while (true) {
            boolean gotLock1 = false;
            boolean gotLock2 = false;
            try {
                gotLock1 = lock1.tryLock();
                gotLock2 = lock2.tryLock();
            } finally {
                if (gotLock1 && gotLock2) {
                    return;
                }
                if (gotLock1) {
                    lock1.unlock();
                }
                if (gotLock2) {
                    lock2.unlock();
                }
            }
            Thread.sleep(10);
        }
    }

    public void firstThread() throws InterruptedException {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            acquireLock(lock1, lock2);
            try {
                Account.transfer(account1, account2, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void secondThread() throws InterruptedException {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            acquireLock(lock2, lock1);
            try {
                Account.transfer(account2, account1, random.nextInt(100));
            } finally {
                lock2.unlock();
                lock1.unlock();
            }
        }
    }

    public void finished() {
        System.out.println("Account 1 Balance = " + account1.getBalance());
        System.out.println("Account 2 Balance = " + account2.getBalance());
        System.out.println("Total Balance = " + (account1.getBalance() + account2.getBalance()));
    }
}

public class ReEntrantDeadlockFixWithTryLock {
    public static void main(String[] args) throws InterruptedException {
        final Runner runner = new Runner();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runner.firstThread();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runner.secondThread();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        runner.finished();
    }
}
