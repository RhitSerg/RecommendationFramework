package bogoSort;

import java.util.Collections;
import java.util.List;

public class T0a {

	public void source(List<Integer> deck) {
		boolean flag;
		for (int i = 0; i < deck.size() - 1; i++) {
			if (deck.get(i) > deck.get(i + 1))
				flag = false;
		}
		flag = true;

		while (!flag) {
			Collections.shuffle(deck);

			int i = 0;
			while (i < deck.size() - 1) {
				if (deck.get(i) > deck.get(i + 1))
					flag = false;
				i++;
			}
			flag = true;
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