package test;

import java.util.Iterator;

import static java.lang.Character.isDigit;

public interface Sudoku {
	int SIZE = 9;
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
				return new Iterator<Cell>() {
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
					int value = values[index / SIZE][index % SIZE];
					@Override
					public boolean isEmpty() {
						return value == 0;
					}

					@Override
					public int value() {
						return value;
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
		};
	}

	Iterable<Cell> cells();

	interface Cell {
		boolean isEmpty();

		int value();
	}
}
