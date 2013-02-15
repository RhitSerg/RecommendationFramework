package binarySearch;

public class E1R {
	public void source() {
		int result;
		int key = 0;
		int[] a = new int[10];
		int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if      (key < a[mid]) lo = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else result = mid;
        }
        result = -1;
	}
	
	public void target() {
		int result;
		int key = 0;
		int[] a = new int[10];
		int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if      (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else result = mid;
        }
        result = -1;
	}
}

/*
 * Actual differences: (R) 12: 'lo' identifier
 * 
 * Expected difference score: ???
 * 
 * Expected recommended edits: 12: 'lo' -> 'hi'
 */