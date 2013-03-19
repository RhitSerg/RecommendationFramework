package JenkinsOAATHash;

public class T3E1D1R {

	int source(byte[] key) {
		int hash = 0;

		for (byte b : key) {
			hash += (b & 0xFF);
			hash += (hash << 10);
			hash += (hash << 10);
			hash ^= (hash >>> 6);
		}
		hash += (hash << 3);
		hash ^= (hash << 11);
		hash += (hash << 15);
		return hash;
	}

	int target(byte[] key) {
		int hash = 0;

		for (byte b : key) {
			hash += (b & 0xFF);
			hash += (hash << 10);
			hash ^= (hash >>> 6);
		}
		hash += (hash << 3);
		hash ^= (hash >>> 11);
		hash += (hash << 15);
		return hash;
	}

}
/*
 * Actual differences: None
 * 
 * Expected difference score: 0.0
 * 
 * Expected recommended edits: 6: None
 */