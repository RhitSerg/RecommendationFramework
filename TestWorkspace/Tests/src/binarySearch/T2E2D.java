package binarySearch;

public class T2E2D {
	public int source(int[] a, int key) {
		int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi - 1) {
            int mid = lo + (hi - lo) / 2;
            if      (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
            lo++;
        }
        return -1;
	}
	
	public int target(int[] a, int key) {
		int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if      (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
	}
}

/*
 * Actual differences: (R) 15: 'lo' assignment 
 * 
 * Expected difference score: ???
 * 
 * Expected recommended edits: 15: Delete unnecessary code
 */