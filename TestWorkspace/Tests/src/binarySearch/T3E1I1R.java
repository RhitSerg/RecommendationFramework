package binarySearch;

public class T3E1I1R {
	public int source(int[] a, int key) {
		int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if      (key < a[mid]) hi = mid + 1;
            else if (key > a[mid]) lo = mid + 1;
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