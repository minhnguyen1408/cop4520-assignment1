# Parallel Prime Finder

This Java program finds all prime numbers between 1 and 10^8 using parallel threads. The program spawns 8 threads to distribute the computation and ensures even distribution of work among them.

## Instructions

1. **Compile the Program:**
   - Open a command prompt or terminal.
   - Navigate to the directory containing the `ParallelPrimeFinder.java` file.
   - Compile the program using the following command:
     ```bash
     javac ParallelPrimeFinder.java
     ```

2. **Run the Program:**
   - Run the compiled program using the following command:
     ```bash
     java ParallelPrimeFinder
     ```

3. **Check the Output:**
   - The program will create an output file named `primes.txt` in the same directory.
   - The file will contain the execution time, total number of primes found, sum of primes, and the top ten maximum primes.

## Notes

- Ensure that you have Java installed on your machine.
- The program may take some time to execute based on your machine's performance.

## About the Program

- The program uses multi-threading to efficiently find prime numbers.
- It avoids integer overflow issues when calculating the sum of primes.

