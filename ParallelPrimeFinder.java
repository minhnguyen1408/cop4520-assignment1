import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParallelPrimeFinder {
    private static final int THREAD_COUNT = 8;
    private static final int RANGE_START = 1;
    private static final int RANGE_END = 100000000;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        List<PrimeThread> threads = new ArrayList<>();

        // Calculate the range for each thread
        int rangePerThread = (RANGE_END - RANGE_START + 1) / THREAD_COUNT;
        int start = RANGE_START;
        int end = start + rangePerThread - 1;

        // Create and start threads
        for (int i = 0; i < THREAD_COUNT; i++) {
            PrimeThread thread = new PrimeThread(start, end);
            threads.add(thread);
            thread.start();

            // Update start and end for the next thread
            start = end + 1;
            end = start + rangePerThread - 1;
        }

        // Wait for all threads to finish
        for (PrimeThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        // Collect results from all threads
        List<Integer> allPrimes = new ArrayList<>();
        for (PrimeThread thread : threads) {
            allPrimes.addAll(thread.getPrimes());
        }

        // Process results
        int totalPrimes = allPrimes.size();
        long sumOfPrimes = allPrimes.stream().mapToLong(Integer::intValue).sum();

        // Output results to primes.txt
        try (FileWriter writer = new FileWriter("primes.txt")) {
            writer.write(executionTime + " " + totalPrimes + " " + sumOfPrimes + "\n");

            // Find and print the top ten maximum primes
            List<Integer> sortedPrimes = new ArrayList<>(allPrimes);
            sortedPrimes.sort(null);
            for (int i = Math.max(0, totalPrimes - 10); i < totalPrimes; i++) {
                writer.write(sortedPrimes.get(i) + " ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Execution Time: " + executionTime + " ms");
        System.out.println("Total Primes Found: " + totalPrimes);
        System.out.println("Sum of Primes Found: " + sumOfPrimes);
    }
}

class PrimeThread extends Thread {
    private final int start;
    private final int end;
    private final List<Integer> primes;

    public PrimeThread(int start, int end) {
        this.start = start;
        this.end = end;
        this.primes = new ArrayList<>();
    }

    @Override
    public void run() {
        for (int num = start; num <= end; num++) {
            if (isPrime(num)) {
                primes.add(num);
            }
        }
    }

    public List<Integer> getPrimes() {
        return primes;
    }

    private boolean isPrime(int num) {
        if (num < 2) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}