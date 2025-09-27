package paginationUsingJava;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.testng.annotations.Test;

public class PaginationTest {
	@Test
	void testPagination() {
		int pageNumber = 1;
		int pageSize = 10;
		boolean morePages = true;

		while (morePages) {

			System.out.println("Fetching page " + pageNumber + " with size " + pageSize);

			if (pageNumber >= 5) {
				morePages = false;
			} else {
				pageNumber++;
			}
		}
	}

	@Test
	void testAnother() {
		// Sample dataset (could be from DB, API, etc.)
		List<String> data = new ArrayList<>();
		for (int i = 1; i <= 25; i++) {
			data.add("Record " + i);
		}

		int pageSize = 5; // number of records per page
		int totalPages = (int) Math.ceil((double) data.size() / pageSize);

		Scanner sc = new Scanner(System.in);
		int currentPage = 1;

		while (true) {
			// Calculate start and end index for the current page
			int start = (currentPage - 1) * pageSize;
			int end = Math.min(start + pageSize, data.size());

			System.out.println("\nPage " + currentPage + " of " + totalPages);
			for (int i = start; i < end; i++) {
				System.out.println(data.get(i));
			}

			// Navigation
			System.out.println("\nEnter command (n = next, p = prev, q = quit): ");
			String command = sc.nextLine();

			if (command.equalsIgnoreCase("n") && currentPage < totalPages) {
				currentPage++;
			} else if (command.equalsIgnoreCase("p") && currentPage > 1) {
				currentPage--;
			} else if (command.equalsIgnoreCase("q")) {
				System.out.println("Exiting pagination.");
				break;
			} else {
				System.out.println("Invalid input or no more pages.");
			}
		}
		sc.close();
	}
}
