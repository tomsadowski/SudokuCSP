package sudoku;
public class SudokuSolverTest {
	public static void main(String[] args) throws Exception {
		// checks that an argument has been passed
		if (args == null || args.length == 0) {
			System.out.println("A file path to a sudoku puzzle"
				+ "must be provided as an argument.");
			System.exit(0);
		}
		
		SudokuPuzzle sudoku = new SudokuPuzzle(args[0]);
		System.out.println(sudoku);
		sudoku.singletonInference();
		System.out.println(sudoku);	
		
				
	}
}
