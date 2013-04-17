package JenkinsOAATHash;

public class T2E1I {

	int source(byte[] key) {
		int hash = 0;

		for (byte b : key) {
			hash += (b & 0xFF);
			hash += (hash << 10);
			hash ^= (hash >>> 6);
		}
		hash += (hash << 3);
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