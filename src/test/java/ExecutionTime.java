import org.testng.annotations.Test;

public class ExecutionTime {
	@Test
	void executionTime() {
		   // Record start time
        long start = System.nanoTime();

        // Code to measure
        System.out.println("1");

        // Record end time
        long end = System.nanoTime();

        // Calculate elapsed time
        long elapsedTime = end - start; // in nanoseconds

        System.out.println("Execution time: " + elapsedTime + " ns");
        System.out.println("Execution time in milliseconds: " + (elapsedTime / 1_000_000.0) + " ms");
	}

}
