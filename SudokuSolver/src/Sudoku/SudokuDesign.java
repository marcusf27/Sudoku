package Sudoku;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Sudoku.*;

public class SudokuDesign {

	private InterSudoku solver;
	private JPanel sudoPanel;
	private JTextField[][] sudoFields;

	public SudokuDesign(InterSudoku s) {

		this.solver = s;
		this.sudoFields = new JTextField[9][9];

		SwingUtilities.invokeLater(() -> createWindow("Sudoku", 900, 900));
	}

	public void createWindow(String title, int width, int height) {

		JFrame sudoFrame = new JFrame(title);

		// Standard funktioner

		sudoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sudoFrame.setSize(width, height);

		Container pane = sudoFrame.getContentPane();

		// Rutnätet 9x9
		this.sudoPanel = new JPanel();
		sudoPanel.setLayout(new GridLayout(9, 9));

		this.buildBoard(true, false);

		JButton solve = new JButton("Solve");
		JButton clear = new JButton("Clear");

		clear.addActionListener(e -> {
			this.clearBoard();
		});

		solve.addActionListener(e -> {

			System.out.println(solver.solve());

			// Om det är löst så rita om brädet m sudokun
			if (solver.solve()) {
				this.rebuildBoard();
				JOptionPane.showMessageDialog(pane, "The sudoku has been solved");
			} else {
				JOptionPane.showMessageDialog(pane, "The sudoku could not be solved");
			}
		});

		JPanel buttonPanel = new JPanel();

		buttonPanel.add(solve, BorderLayout.EAST);
		buttonPanel.add(clear, BorderLayout.EAST);

		pane.add(buttonPanel, BorderLayout.SOUTH);
		pane.add(sudoPanel, BorderLayout.EAST);

		sudoFrame.pack();
		sudoFrame.setVisible(true);

	}

	public void clearBoard() {
		this.buildBoard(false, true);
	}

	public void rebuildBoard() {
		this.buildBoard(false, false);
	}

	public void buildBoard(boolean inBuild, boolean clear) {

		// Om brädet ska rensas
		if (clear) {
			this.solver.clear();
		}

		// Annars hämta alla värden
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				int numb = this.solver.get(row, col);

				String val = "";

				if (numb > 0) {
					val = String.valueOf(numb);
				}
				JTextField field = new JTextField();
				// Om det är första gången det ritas
				if (inBuild) {
					this.setFields(field, row, col);
					this.sudoPanel.add(field);
					sudoFields[row][col] = field;
				}
				// Annars bara ändra texten
				sudoFields[row][col].setText(val);
			}
		}

	}

	private Color boxBackground(int size, int row, int col) {
		int gridRow = (row / size) * size;
		int gridCol = (col / size) * size;

		// Rutorna enligt uppgiften
		if (gridRow % 2 == 0 && gridCol % 2 == 0 || (gridRow == size && gridCol == size)) {
			return Color.decode("#fec4a3");
		}

		return Color.white;
	}

	private void setFields(JTextField field, int row, int col) {

		field.setPreferredSize(new Dimension(50, 50));
		field.setHorizontalAlignment(JTextField.CENTER);

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Color color = this.boxBackground(3, row, col);
				field.setBackground(color);
			}
		}

		field.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				//field.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void focusLost(FocusEvent e) {
				
				
				
				String t = field.getText();

				// Catchen fångar både IllegalArgumentException från solver.add
				// Och NumberFormatException från Interger.parseInt();
				// Om exception uppstår, sätt värde till 0
				try {
					int nbr = Integer.parseInt(t);

					if (nbr <= 0) {
						throw new IllegalArgumentException();
					}

					solver.add(row, col, nbr);

				} catch (Exception err) {
					solver.add(row, col, 0);
					// Sätter nummer tillbaks till 0
					field.setText("");
				}
			}
		});
	}

	public static void main(String[] args) {

		int[][] sudoTestBoard = { { 0, 0, 8, 0, 0, 9, 0, 6, 2 }, { 0, 0, 0, 0, 0, 0, 0, 0, 5 },
				{ 1, 0, 2, 5, 0, 0, 0, 0, 0 }, { 0, 0, 0, 2, 1, 0, 0, 9, 0 }, { 0, 5, 0, 0, 0, 0, 6, 0, 0 },
				{ 6, 0, 0, 0, 0, 0, 0, 2, 8 }, { 4, 1, 0, 6, 0, 8, 0, 0, 0 }, { 8, 6, 0, 0, 0, 0, 1, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 4, 0, 0 } };

		InterSudoku solver = new SudokuSolver();

		solver.setMatrix(sudoTestBoard);

		new SudokuDesign(solver);
	}

}