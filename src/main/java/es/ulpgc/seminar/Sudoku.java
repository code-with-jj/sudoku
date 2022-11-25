package es.ulpgc.seminar;

import java.util.Iterator;

import static java.lang.Character.isDigit;

public interface Sudoku {
	int SIZE = 9;

	Iterable<Cell> cells();
	default boolean hasEmptyCells() {
		return nextEmptyCell() != null;
	}
	default Cell nextEmptyCell() {
		for (Cell cell : cells())
			if (cell.isEmpty()) return cell;
		return null;
	}



	static Sudoku of(String text) {
		return of(text.replaceAll("[\\s\\n]","").toCharArray());
	}

	static Sudoku of(char[] chars) {
		return createSudoku(toInt(chars));
	}

	static int[][] toInt(char[] chars) {
		int[][] result = new int[SIZE][SIZE];
		int index = 0;
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++)
				result[i][j] = index < chars.length ? toInt(chars[index++]) : 0;
		return result;
	}

	static int toInt(char c) {
		return isDigit(c) ? c - '0' : 0;
	}

	private static Sudoku createSudoku(int[][] values) {
		return new Sudoku() {
			@Override
			public Iterable<Cell> cells() {
				return new Iterable<Cell>() {
					@Override
					public Iterator<Cell> iterator() {
						return createIterator();
					}
				};
			}

			private Iterator<Cell> createIterator() {
				return new Iterator<>() {
					int index = 0;

					@Override
					public boolean hasNext() {
						return index < SIZE * SIZE;
					}

					@Override
					public Cell next() {
						return createCell(index++);
					}
				};
			}

			private Cell createCell(int index) {
				return new Cell() {
					int value = values[row()][col()];
					@Override
					public boolean isEmpty() {
						return value == 0;
					}

					@Override
					public int row() {
						return index / SIZE;
					}

					@Override
					public int col() {
						return index % SIZE;
					}

					@Override
					public int value() {
						return value;
					}

					@Override
					public void set(int number) {
						values[row()][col()] = number;
					}

					@Override
					public void unset() {
						set(0);
					}

					@Override
					public boolean hasInRow(int number) {
						for (int i = 0; i < SIZE; i++)
							if (values[row()][i] == number) return true;
						return false;
					}

					@Override
					public boolean hasInColumn(int number) {
						for (int i = 0; i < SIZE; i++)
							if (values[i][col()] == number) return true;
						return false;
					}

					@Override
					public boolean hasInSquare(int number) {
						int ox = 3 * (row() / 3);
						int oy = 3 * (col() / 3);
						for (int i = 0; i < 3; i++)
							for (int j = 0; j < 3; j++)
								if (values[ox+i][oy+j] == number) return true;
						return false;
					}

					@Override
					public boolean equals(Object obj) {
						if (obj == null) return false;
						return obj == this || obj instanceof Cell && equals((Cell) obj);
					}

					private boolean equals(Cell cell) {
						return row() == cell.row() && col() == cell.col();
					}
				};
			}

			@Override
			public String toString() {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < SIZE; i++) {
					for (int j = 0; j < SIZE; j++) {
						sb.append(values[i][j] != 0 ? values[i][j] + "" : "-");
						if (j == 2 || j == 5) sb.append(' ');
					}
					sb.append('\n');
				}
				return sb.toString().trim();
			}

			@Override
			public boolean equals(Object obj) {
				if (obj == null) return false;
				return obj == this || obj instanceof Sudoku && equals((Sudoku) obj);
			}

			private boolean equals(Sudoku sudoku) {
				int index = 0;
				for (Cell cell : sudoku.cells()) {
					if (cell.value() != values[index / SIZE][index % SIZE]) return false;
					index++;
				}
				return true;
			}
		};
	}


	interface Cell {
		boolean isEmpty();

		int row();
		int col();
		int value();

		void set(int number);
		void unset();

		boolean hasInRow(int number);

		boolean hasInColumn(int number);

		boolean hasInSquare(int number);

		default boolean rejects(int number) {
			return hasInColumn(number) || hasInRow(number) || hasInSquare(number);
		}
	}

	interface Solver {
		static boolean solve(Sudoku sudoku) {
			if (!sudoku.hasEmptyCells()) return true;
			Cell cell = sudoku.nextEmptyCell();
			for (int number = 1; number <= 9; number++) {
				if (cell.rejects(number)) continue;
				cell.set(number);
				if (solve(sudoku)) return true;
			}
			cell.unset();
			return false;
		}
	}
}
