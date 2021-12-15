package Sudoku;

import java.util.Random;

public class SudokuSolver implements InterSudoku {

	private int[][] sudoBoard;

	public SudokuSolver() {
		this(9);
	}

	public SudokuSolver(int size) {

		this.sudoBoard = new int[size][size];

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				sudoBoard[row][col] = 0; // Nollställ från början
			}
		}
	}
	
	/*
	 * Sätter angivet nummer på önskad rad och kolumn
	 */

	public void add(int row, int col, int digit) {

		checkInputValues(row, col, digit);

		this.sudoBoard[row][col] = digit;
	}
	
	/*
	 * Tar bort numret från önskad rad och kolumn
	 */

	public void remove(int row, int col) {
		checkInputValues(row, col, 1);

		this.sudoBoard[row][col] = 0;
	}

	/*
	 * Hämtar önskad värde 
	 */
	
	public int get(int row, int col) {
		checkInputValues(row, col, 1);

		return this.sudoBoard[row][col];
	}
	
	/*
	 * Nollställer pusslet
	 */

	public void clear() {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				this.sudoBoard[row][col] = 0;
			}
		}

	}

	/*
	 * Undersöker om siffran får sättas in i raden
	 */
	
	
	public boolean checkRow(int row, int digit) {

		for (int i = 0; i < sudoBoard[0].length; i++) {
			// Om numret finns någonstans i raden är den inte lösbar
			if (digit == sudoBoard[row][i]) {
				return false;
			}
		}
		return true;
	}
	/*
	 * Undersöker om siffran får sättas in i kolumnen
	 */
	

	public boolean checkCol(int col, int digit) {

		for (int i = 0; i < sudoBoard.length; i++) {
			// Om numret finns någonstans i kolumnen är den inte lösbar
			if (sudoBoard[i][col] == digit) {
				return false;
			}
		}
		return true;
	}

	
	/*
	 * Undersöker om siffran får sättas in i lilla rutan
	 */
	
	public boolean checkBox(int row, int col, int digit) {

		int boxSize = 3;

		int initialRow = (row / boxSize) * boxSize;
		int initialCol = (col / boxSize) * boxSize;

		for (int i = initialRow; i < initialRow + boxSize; i++) {
			for (int k = initialCol; k < initialCol + boxSize; k++) {
				// Om numret finns någonstans i rutan är den inte lösbar
				if (this.sudoBoard[i][k] == digit) {
					return false;
				}
			}
		}
		return true;
	}

	/*
	 * Undersöker om hela pusslet är valid
	 */
	
	
	public boolean isValid() {
		for (int row = 0; row < this.sudoBoard.length; row++) {
			for (int col = 0; col < this.sudoBoard.length; col++) {
				if (!checkAllValid(row, col, this.sudoBoard[row][col])) {
					return false;
				}
			}
		}
		return true;
	}

	/*
	 * Undersöker om siffran är OK i hela pusslet
	 */
	
	public boolean checkAllValid(int row, int col, int digit) {
		checkInputValues(row, col, digit);

		// Sparar i temp variable ifall något skulle ändras
		int numb = this.sudoBoard[row][col];
		this.sudoBoard[row][col] = 0;

		//Tittar så den är valid på alla tre sätt
		boolean result = this.checkRow(row, digit) && this.checkCol(col, digit) && this.checkBox(row, col, digit);

		// Sätter tillbaks vädert
		this.sudoBoard[row][col] = numb;

		return result;

	}

	@Override
	public boolean solve() {
		return solve(0, 0);
	}

	public boolean solve(int row, int col) {

		// Om sista raden
		if (row == 9) {
			return true;
		}

		int nextRow, nextCol;

		if ((col + 1) < 9) {
			nextCol = col + 1;
			nextRow = row;
		} else {
			nextCol = 0;
			nextRow = row + 1;
		}

		// Tittar så att inget värde är satt
		if (this.sudoBoard[row][col] == 0) {
			for (int i = 1; i <= 9; i++) {

				// Tittar ifall man får sätta in värdet på den punkten
				if (this.checkAllValid(row, col, i)) {

					// Isåfall sätt in värdet och gå till nästa
					this.sudoBoard[row][col] = i;

					// Om nästa också kan lösas return true
					if (this.solve(nextRow, nextCol)) {
						return true;
					}

					// Annars sett tillbaks värdet
					this.sudoBoard[row][col] = 0;

				}

			}

			return false;
		}

		// Om ett värde är satt, returna om det är okej enligt reglerna och om nästa kan
		// lösas

		return this.checkAllValid(row, col, this.sudoBoard[row][col]) && this.solve(nextRow, nextCol);

	}

	/*
	 * Returnerar hela matrisen
	 */
	
	public int[][] getMatrix() {
		int[][] copyOfMatrix = new int[9][9];

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				copyOfMatrix[row][col] = this.sudoBoard[row][col];
			}
		}
		return copyOfMatrix;
	}
	
	/*
	 * Sätter in önskad matris
	 */

	public void setMatrix(int[][] m) {

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				this.sudoBoard[row][col] = m[row][col];
			}
		}
	}

	/*
	 * Tittar om insatta värden är ok
	 */
	
	public void checkInputValues(int row, int col, int digit) {

		if (row < 0 || row > 8 || col < 0 || col > 8 || digit > 9 || digit < 0) {
			throw new IllegalArgumentException();
		}

	}
	
	/*
	 * Tittar om matrisen är tom
	 */

	public boolean isEmpty() {

		boolean result = true;

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (this.sudoBoard[row][col] != 0) {
					result = false;
				}
			}
		}
		return result;

	}
	/*
	 * public void newGame() { int randRow, randCol, randVal; int amount = 0; Random
	 * rand = new Random();
	 * 
	 * while(amount < 50) { // Kör så att det alltid finns minst 24 ledtrådar, // 17
	 * är minimum för att en sudoku ska vara lösbar
	 * 
	 * 
	 * 
	 * // i < X, där X är antalet försök till att skapa ledtrådar randRow =
	 * rand.nextInt(9); randCol = rand.nextInt(9); randVal = rand.nextInt(9) +1;
	 * 
	 * while(!this.checkAllValid(randRow,randCol,randVal) && get(randRow,randCol) !=
	 * randVal) { randRow = rand.nextInt(9); randCol = rand.nextInt(9); randVal =
	 * rand.nextInt(9) +1; } if(randVal<10) {
	 * 
	 * add(randRow,randCol,randVal); amount++; }
	 * 
	 * } }
	 */

}