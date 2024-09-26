package JavaThread;

/**
 * JavaThread.CalculatePrimes -- calculate as many primes as we can in ten seconds
 * Two threads in this example:
 * Main Thread: Handles the setup, starts the CalculatePrimes thread, sleeps for 10 seconds, and then signals the
 *              prime-calculating thread to stop.
 * CalculatePrimes Thread: Runs the run() method, calculating prime numbers while the main thread sleeps, until it is
 *                         stopped after 10 seconds.
 */
//extends to Thread class means this class is a thread and can be executed concurrently with other threads
public class CalculatePrimes extends Thread {
    public static final int MAX_PRIMES = 1000000;
    public static final int TEN_SECONDS = 10000;

    //volatile will allow changes on this variable is synchronized among threads: main and CalculatePrimes
    public volatile boolean finished = false;

    //when a thread is started, this method is invoked automatically in the new thread of execution
    public void run() {
        int[] primes = new int[MAX_PRIMES];
        int count = 0;
        for (int i=2; count<MAX_PRIMES; i++) {
            // Check to see if the timer has expired
            if (finished) {
                break;
            }
            boolean prime = true;
            //If i is divisible by any non-prime number, it would also be divisible by some prime factor of that number.
            //Therefore, checking divisibility only by primes is sufficient to determine whether i is prime.
            for (int j=0; j<count; j++) {
                if (i % primes[j] == 0) {
                    prime = false;
                    break;
                }
            }
            if (prime) {
                primes[count++] = i;
                System.out.println("Found prime: " + i);
            }
        }
    }
    public static void main(String[] args) {
        CalculatePrimes calculator = new CalculatePrimes();
        calculator.start(); //start a new thread
        try {
            Thread.sleep(TEN_SECONDS);
        } catch (InterruptedException e) {
            // fall through
        }
        calculator.finished = true;
    }
}