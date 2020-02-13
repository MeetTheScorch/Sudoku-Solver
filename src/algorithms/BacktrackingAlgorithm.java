package algorithms;
import java.util.stream.IntStream;

public class BacktrackingAlgorithm {

	/**
	 * Return true if properly solved.
	 * */
	public static boolean solve(int[][] values) {
		for(int row = 0; row < 9; row++) {
			for(int column = 0; column < 9; column++) {
				if(values[row][column] == 0) {
					for(int n = 1; n <= 9; n++) {
						values[row][column] = n;
						if(isValid(values, row, column, n) && solve(values)) {
							return true;
						}
						values[row][column] = 0;
					}
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Return true if there are no duplicates of a number in the given row, column & subsection.
	 * */
	private static boolean isValid(int[][] values, int row, int column, int number) {
		for(int c = 0; c < 9; c++) {
			if(values[row][c] == number && column != c) return false;
		}
		
		for(int r = 0; r < 9; r++) {
			if(values[r][column] == number && row != r) return false;
		}
		
		int boxRowStart = (row / 3) * 3;
		int boxRowEnd = boxRowStart + 3;
		int boxColumnStart = (column / 3) * 3;
		int boxColumnEnd = boxColumnStart + 3;
		
		for(int r = boxRowStart; r < boxRowEnd; r++) {
			for(int c = boxColumnStart; c < boxColumnEnd; c++) {
				if(values[r][c] == number && column != c && row != r) return false;
			}
		}
		return true;
	}
	
	// --------------------------------------------------------------------------------------------------------------------
	
	private static boolean isValidBael(int[][] values, int row, int column) {
		return rowConstraint(values, row) && columnConstraint(values, column) && subsectionConstraint(values, row, column);
	}

	private static boolean rowConstraint(int[][] values, int row) {
		boolean[] constraint = new boolean[9];
		return IntStream.range(0, 9).allMatch(column -> checkConstraint(values, row, constraint, column));
	}
	
	private static boolean columnConstraint(int[][] values, int column) {
		boolean[] constraint = new boolean[9];
		return IntStream.range(0, 9).allMatch(row -> checkConstraint(values, row, constraint, column));
	}
	
	private static boolean subsectionConstraint(int[][] values, int row, int column) {
		boolean[] constraint = new boolean[9];
		int subsectionRowStart = (row / 3) * 3;
		int subsectionRowEnd = subsectionRowStart + 3;
		
		int subsectionColumnStart = (column / 3) * 3;
		int subsectionColumnEnd = subsectionColumnStart + 3;
		
		for(int r = subsectionRowStart; r < subsectionRowEnd; r++) {
			for(int c = subsectionColumnStart; c < subsectionColumnEnd; c++) {
				if(!checkConstraint(values, r, constraint, c)) return false;
			}
		}
		return true;
	}
	
	private static boolean checkConstraint(int[][] values, int row, boolean[] constraint, int column) {
		if(values[row][column] != 0) {
			if(!constraint[values[row][column] - 1]) { 
				constraint[values[row][column] - 1] = true;
			} else {
				return false;
			}
		}
		return true;
	}
}
