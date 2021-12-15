package Sudoku;

/**
 * Interface for SudokuSolver
 * 
 * @author Marcus Froeberg
 *
 */

public interface InterSudoku {
	/**
	 * @return If the puzzle can be solve
	 */
	boolean solve();

	/**
	 * Sets the wanted value to given position
	 * 
	 * @param row   The row
	 * @param col   The column
	 * @param digit Value of number
	 * @throws IllegalArgumentException If number not between 0-9
	 *                                  
	 */
	void add(int row, int col, int digit);

	/**
	 * Removes the value from given position
	 * 
	 * @param row The row
	 * @param col The column 
	 */
	void remove(int row, int col);

	/**
	 * Retrieves wanted value
	 * 
	 * @param row	The row
	 * @param col	The column
	 * @return int	The value
	 */
	int get(int row, int col);

	/**
	 * Checks that all the sudoku rules are followed
	 * 
	 * @return boolean	If all rules are followed
	 */
	boolean isValid();

	/**
	 * Cleares the board, sets all values to 0
	 */
	void clear();

	/**
	 * Fills the board with values from m
	 * 
	 * @param m 	Matrix to insert
	 * @throws IllegalArgumentException	If dimension are out of range or values not between 0-9
	 * 	
	 */
	void setMatrix(int[][] m);

	/**
	 * 
	 * Retrieves the whole puzzle
	 * 
	 * @return int[][] 	The matrix with all values
	 * 
	 */
	int[][] getMatrix();
	

	/**
	 * Checks if the board is empty
	 * 
	 * @return boolean If the board is empty or not
	 */
	
	boolean isEmpty();
	
	/**
	 * 
	 * Checks so all values in the puzzle follow the rules
	 * 
	 * @param row	The row
	 * @param col	The column
	 * @param digit	The value
	 * @return boolean 	If all values are ok
	 */

	boolean checkAllValid(int row, int col, int digit);
	
	/**
	 * Checks if a value can be inserted to a given row
	 * 
	 * @param row	The row
	 * @param digit	The value
	 * @return boolean 	If the value can be inserted
	 */

	boolean checkRow(int row, int digit);

	/**
	 * Checks if a value can be inserted to a given column
	 * 
	 * @param col	The column
	 * @param digit	The value
	 * @return boolean 	If the value can be inserted
	 */
	boolean checkCol(int col, int digit);

	/**
	 * Checks if a value can be inserted to a given box
	 * 
	 * @param row	The row
	 * @param col	The column
	 * @param digit	The value
	 * @return boolean 	If the value can be inserted
	 */
	boolean checkBox(int row, int col, int digit);

}