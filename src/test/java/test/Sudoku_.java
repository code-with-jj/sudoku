package test;

import org.junit.Assert;
import org.junit.Test;
import test.Sudoku.Cell;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static test.Sudoku_.Example.Initial;


public class Sudoku_ {

	@Test
	public void should_deserialize_empty_text(){
		for (Cell cell : Sudoku.of("").cells())
			assertThat(cell.isEmpty()).isTrue();
	}

	@Test
	public void should_deserialize_text() {
		assertThat(toList(Sudoku.of(Initial))).isEqualTo(List.of(5,3,0,0,7,0,0,0,0,6,0,0,1,9,5,0,0,0,0,9,8,0,0,0,0,6,0,8,0,0,0,6,0,0,0,3,4,0,0,8,0,3,0,0,1,7,0,0,0,2,0,0,0,6,0,6,0,0,0,0,2,8,0,0,0,0,4,1,9,0,0,5,0,0,0,0,8,0,0,7,9));
	}

	@Test
	public void should_be_printed() {
		assertThat(Sudoku.of(Initial).toString().replace('\n', ' ')).isEqualTo(Initial);
	}



	private List<Integer> toList(Sudoku sudoku) {
		List<Integer> result = new ArrayList<>();
		for (Cell cell : sudoku.cells())
			result.add(cell.value());
		return result;
	}

	public static class Example {
		static final String Initial = "53- -7- --- 6-- 195 --- -98 --- -6- 8-- -6- --3 4-- 8-3 --1 7-- -2- --6 -6- --- 28- --- 419 --5 --- -8- -79";

		static final String Solution = "534 678 912 672 195 348 198 342 567 859 761 423 426 853 791 713 924 856 961 537 284 287 419 635 345 286 179";
	}


}
