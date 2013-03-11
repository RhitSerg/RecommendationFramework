package LevenshteinDistance;

public class E0 {

	public static int source(CharSequence str1, CharSequence str2) {
		int[][] distance = new int[str1.length() + 1][str2.length() + 1];

		for (int i = 0; i <= str1.length(); i++)
			distance[i][0] = i;
		for (int j = 1; j <= str2.length(); j++)
			distance[0][j] = j;

		for (int i = 1; i <= str1.length(); i++)
			for (int j = 1; j <= str2.length(); j++)
				distance[i][j] = Math.min(Math.min(distance[i - 1][j] + 1,
						distance[i][j - 1] + 1), distance[i - 1][j - 1]
						+ ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1));

		return distance[str1.length()][str2.length()];
	}

	public static int target(CharSequence str1, CharSequence str2) {
		int[][] distance = new int[str1.length() + 1][str2.length() + 1];

		for (int i = 0; i <= str1.length(); i++)
			distance[i][0] = i;
		for (int j = 1; j <= str2.length(); j++)
			distance[0][j] = j;

		for (int i = 1; i <= str1.length(); i++)
			for (int j = 1; j <= str2.length(); j++)
				distance[i][j] = Math.min(Math.min(distance[i - 1][j] + 1,
						distance[i][j - 1] + 1), distance[i - 1][j - 1]
						+ ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1));

		return distance[str1.length()][str2.length()];
	}

}
/*
 * Actual differences: None
 * 
 * Expected difference score: 0.0
 * 
 * Expected recommended edits: 6: None
 */