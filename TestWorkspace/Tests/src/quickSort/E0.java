package quickSort;

import java.util.ArrayList;

public class E0 {

	public static ArrayList<Integer> source(ArrayList<Integer> numbers) {
		if (numbers.size() <= 1)
			return numbers;
		int pivot = numbers.size() / 2;
		ArrayList<Integer> lesser = new ArrayList<Integer>();
		ArrayList<Integer> greater = new ArrayList<Integer>();
		int sameAsPivot = 0;
		for (int number : numbers) {
			if (number > numbers.get(pivot))
				greater.add(number);
			else if (number < numbers.get(pivot))
				lesser.add(number);
			else
				sameAsPivot++;
		}
		lesser = source(lesser);
		for (int i = 0; i < sameAsPivot; i++)
			lesser.add(numbers.get(pivot));
		greater = source(greater);
		ArrayList<Integer> sorted = new ArrayList<Integer>();
		for (int number : lesser)
			sorted.add(number);
		for (int number : greater)
			sorted.add(number);
		return sorted;
	}

	public static ArrayList<Integer> target(ArrayList<Integer> numbers) {
		if (numbers.size() <= 1)
			return numbers;
		int pivot = numbers.size() / 2;
		ArrayList<Integer> lesser = new ArrayList<Integer>();
		ArrayList<Integer> greater = new ArrayList<Integer>();
		int sameAsPivot = 0;
		for (int number : numbers) {
			if (number > numbers.get(pivot))
				greater.add(number);
			else if (number < numbers.get(pivot))
				lesser.add(number);
			else
				sameAsPivot++;
		}
		lesser = target(lesser);
		for (int i = 0; i < sameAsPivot; i++)
			lesser.add(numbers.get(pivot));
		greater = target(greater);
		ArrayList<Integer> sorted = new ArrayList<Integer>();
		for (int number : lesser)
			sorted.add(number);
		for (int number : greater)
			sorted.add(number);
		return sorted;
	}

}
/*
 * Actual differences: None
 * 
 * Expected difference score: 0.0
 * 
 * Expected recommended edits: 6: None
 */