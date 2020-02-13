package algorithms.dancinglinks;
import java.util.Arrays;

public class DancingLinksAlgorithm {
	
	private static int BOARD_SIZE = 9;
	private static int SUBSECTION_SIZE = 3;
	private static int NO_VALUE = 0;
	private static int MIN_VALUE = 1;
	private static int MAX_VALUE = 9;
	private static int CONSTRAINTS = 4;
	private static int COVER_START_INDEX = 1;
	
	public static boolean solve(int [][] values) {
		boolean [][] exactCoverBoard = initializeExactCoverBoard(values);
		DancingLinks dlx = new DancingLinks(exactCoverBoard);
		return dlx.solve(values);
	}
	
	private static int getIndex(int row, int column, int num) {
		return (row - 1) * BOARD_SIZE * BOARD_SIZE + (column - 1) * BOARD_SIZE + (num - 1);
	}
	
	private static boolean [][] createExactCoverBoard() {
		boolean [][] exactCoverBoard = new boolean [BOARD_SIZE * BOARD_SIZE * MAX_VALUE][BOARD_SIZE * BOARD_SIZE * CONSTRAINTS];
		
		int hBase = 0;
		hBase = checkCellConstraint(exactCoverBoard, hBase);
		hBase = checkRowConstraint(exactCoverBoard, hBase);
		hBase = checkColumnConstraint(exactCoverBoard, hBase);
		checkSubsectionConstraint(exactCoverBoard, hBase);
		
		return exactCoverBoard;
	}

	private static int checkCellConstraint(boolean[][] exactCoverBoard, int hBase) {
		for(int row = COVER_START_INDEX; row <= BOARD_SIZE; row++) {
			for(int column = COVER_START_INDEX; column <= BOARD_SIZE; column++, hBase++) {
				for(int n = COVER_START_INDEX; n <= BOARD_SIZE; n++) {
					int index = getIndex(row, column, n);
					exactCoverBoard[index][hBase] = true;
				}
			}
		}
		return hBase;
	}

	private static int checkRowConstraint(boolean[][] exactCoverBoard, int hBase) {
		for(int row = COVER_START_INDEX; row <= BOARD_SIZE; row++) {
			for(int n = COVER_START_INDEX; n <= BOARD_SIZE; n++, hBase++) {
				for(int column = COVER_START_INDEX; column <= BOARD_SIZE; column++) {
					int index = getIndex(row, column, n);
					exactCoverBoard[index][hBase] = true;
				}
			}
		}
		return hBase;
	}

	private static int checkColumnConstraint(boolean[][] exactCoverBoard, int hBase) {
		for(int column = COVER_START_INDEX; column <= BOARD_SIZE; column++) {
			for(int n = COVER_START_INDEX; n <= BOARD_SIZE; n++, hBase++) {
				for(int row = COVER_START_INDEX; row <= BOARD_SIZE; row++) {
					int index = getIndex(row, column, n);
					exactCoverBoard[index][hBase] = true;
				}
			}
		}
		return hBase;
	}

	private static int checkSubsectionConstraint(boolean[][] exactCoverBoard, int hBase) {
		for(int row = COVER_START_INDEX; row <= BOARD_SIZE; row += SUBSECTION_SIZE) {
			for(int column = COVER_START_INDEX; column <= BOARD_SIZE; column += SUBSECTION_SIZE) {
				for(int n = COVER_START_INDEX; n <= BOARD_SIZE; n++, hBase++) {
					for(int rowDelta = 0; rowDelta < SUBSECTION_SIZE; rowDelta++) {
						for(int columnDelta = 0; columnDelta < SUBSECTION_SIZE; columnDelta++) {
							int index = getIndex(row + rowDelta, column + columnDelta, n);
							exactCoverBoard[index][hBase] = true;
						}
					}
				}
			}
		}
		return hBase;
	}
	
	private static boolean [][] initializeExactCoverBoard(int [][] board) {
		boolean [][] exactCoverBoard = createExactCoverBoard();
		for(int row = COVER_START_INDEX; row <= BOARD_SIZE; row++) {
			for(int column = COVER_START_INDEX; column <= BOARD_SIZE; column++) {
				int n = board[row - 1][column - 1];
				if(n != NO_VALUE) {
					for(int num = MIN_VALUE; num <= MAX_VALUE; num++) {
						if(num != n) {
							Arrays.fill(exactCoverBoard[getIndex(row, column, num)], false);
						}
					}
				}
			}
		}
		return exactCoverBoard;
	}
}
