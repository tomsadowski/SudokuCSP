package sudoku;
public class SudokuSolverTest {
	public static void main(String[] args) throws Exception {
		// checks that an argument has been passed
		if (args == null || args.length < 2) {
			System.out.println("The file path to a sudoku puzzle \n"
				+ "must be provided as the first argument, and \n"
				+ "the 'magnitude' of the inference must be provided \n"
				+ "as the second argument. Passing 1 bases the inference off of \n"
				+ "variables with singleton domains, and passing 3 uses 'naked triples'. \n" 
				+ "However, you may pass any number you like, perhaps 2 for 'naked doubles'.\n");
			System.exit(0);
		}		
		SudokuPuzzle sudoku = new SudokuPuzzle(args[0]);
		// prints the initial set of domains, before any constraint propagation or DFS
		System.out.println(sudoku);
		int max = Integer.parseInt(args[1]);
		boolean loop = true;
		// this loop assures that inference only stops when 
		// no magnitudes of inference from 1 to max have changed at least one domain
		while (loop) {
			loop = false; 
			for (int mag = 1; mag <= max; mag++) 
				if (sudoku.inference(mag))
					loop = true;
		}
		// prints the set of domains after inference is complete
		System.out.println(sudoku);
		sudoku = SudokuPuzzle.Search.backtrack(sudoku);		
		// prints the set of domains after DFS
		System.out.println(sudoku);	
		
				
	}
}
