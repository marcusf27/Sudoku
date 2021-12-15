package Sudoku;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SudokuTest {

	InterSudoku sudo;
	int[][] testBoard = { { 0, 0, 8, 0, 0, 9, 0, 6, 2 }, { 0, 0, 0, 0, 0, 0, 0, 0, 5 }, { 1, 0, 2, 5, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 2, 1, 0, 0, 9, 0 }, { 0, 5, 0, 0, 0, 0, 6, 0, 0 }, { 6, 0, 0, 0, 0, 0, 0, 2, 8 },
			{ 4, 1, 0, 6, 0, 8, 0, 0, 0 }, { 8, 6, 0, 0, 0, 0, 1, 0, 0 }, { 0, 0, 0, 0, 0, 0, 4, 0, 0 } };

	final int[][] test = { { 0, 3, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 9, 0 }, };

	@BeforeEach
	void setUp() throws Exception {

		sudo = new SudokuSolver();
	}

	@AfterEach
	void tearDown() throws Exception {
		sudo = null;
	}

	/*
	 * Testar så att storleken på sudokut är 9x9 = 81
	 */

	@Test
	void testSize() {
		int size = sudo.getMatrix()[0].length * sudo.getMatrix().length;
		assertEquals(81, size);
	}

	/*
	 * Testar ifall den klarar att lösa ett sudoku med givna ledtrådar enligt
	 * matrisen ovan (från uppgiften)
	 */

	@Test
	void solveSudoWithClues() {
		sudo.setMatrix(testBoard);
		assertTrue(sudo.solve());
	}

	/*
	 * Tittar om det går att lösa en tom sudoku
	 */

	@Test
	void solveEmptySudo() {
		assertTrue(sudo.solve());
	}

	/*
	 * Testar m.h.a test-matrisen (se ovan) om programmet kan hämta korrekta värden
	 * i detta fall ska det vara en trea på rad 0 kolumn 1 och en nia på rad 9
	 * kolumn 8
	 */

	@Test
	void sudoTestGetAndSet() {

		assertTrue(sudo.isEmpty());
		sudo.setMatrix(test);
		assertEquals(3, sudo.get(0, 1));
		assertEquals(9, sudo.get(8, 7));
		assertTrue(!sudo.isEmpty());
	}

	/*
	 * Testar clear funktionen genom att först säkerställa att pusslet är tomt,
	 * sedan läggs ledtrådar till och pusslet icke är tomt längre sedan rensas
	 * bordet och det ska vara tomt igen
	 */

	@Test
	void sudoTestClear() {
		assertTrue(sudo.isEmpty());
		sudo.setMatrix(testBoard);
		assertTrue(!sudo.isEmpty());

		sudo.clear();
		assertTrue(sudo.isEmpty());

	}

	/*
	 * Lägger till siffror på icke tillåtna positioner och därmed gör pusslet
	 * olösbart
	 */

	@Test
	void testNonValidSudo() {
		test[0][2] = 3;
		test[8][8] = 9;
		sudo.setMatrix(test);
		assertTrue(!sudo.isValid());
	}

	/*
	 * Tittar om en trea får sättas in i "första" lilla rutan Vilket inte ska gå då
	 * det redan är en trea i rad 1 kol 2 ( test matrisen )
	 */

	@Test
	void sudoCheckValidBox() {
		sudo.setMatrix(test);
		assertTrue(!sudo.checkBox(0, 0, 3));
	}

	/*
	 * Tittar om en trea får sättas in i första raden Vilket inte ska gå då det är
	 * en trea i första raden redan ( test matrisen )
	 */

	@Test
	void sudoCheckValidRow() {
		sudo.setMatrix(test);
		assertTrue(!sudo.checkRow(0, 3));
	}

	/*
	 * Testar om en nia får sättas in i 8e kolumnen Vilket inte ska gå då det är en
	 * nia där redan ( test matrisen )
	 */

	@Test
	void sudoCheckValidCol() {
		sudo.setMatrix(test);
		assertTrue(!sudo.checkCol(7, 9));
	}

	/*
	 * Testar att lägga till ledrådar i add funktionen Först tom, sen läggs värde
	 * till, då är pusslet inte tomt.
	 */

	@Test
	void addHintsToSudo() {
		assertTrue(sudo.isEmpty());
		sudo.add(0, 0, 5);
		assertTrue(!sudo.isEmpty());
	}

}
