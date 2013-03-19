package bogoSort;

import java.util.Collections;
import java.util.List;

public class T3E1I1D {

	public void source(List<Integer> deck) {
		boolean flag;

		for (int i = 0; i < deck.size() - 1; i++) {
			if (deck.get(i) > deck.get(i + 1))
				flag = false;
		}
		flag = true;

		while (!flag) {
			Collections.shuffle(deck);

			for (int i = 0; i < deck.size() - 1; i++) {
				flag = true;
				if (deck.get(i) > deck.get(i + 1))
					flag = false;
			}
		}
	}

	public void target(List<Integer> deck) {
		boolean flag;

		for (int i = 0; i < deck.size() - 1; i++) {
			if (deck.get(i) > deck.get(i + 1))
				flag = false;
		}
		flag = true;

		while (!flag) {
			Collections.shuffle(deck);

			for (int i = 0; i < deck.size() - 1; i++) {
				if (deck.get(i) > deck.get(i + 1))
					flag = false;
			}
			flag = true;
		}
	}

}
/*
 * Actual differences: None
 * 
 * Expected difference score: 0.0
 * 
 * Expected recommended edits: 6: None
 */