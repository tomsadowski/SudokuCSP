package sudoku;
import java.io.File;
import java.util.Scanner;
public class SudokuPuzzle {

	int[][][][] puzzle = new int[3][3][3][3] ;

	public SudokuPuzzle(String filePath) throws Exception {
		Scanner sc = new Scanner(new File(filePath));
 		/* 
		* br = block row
		* r = row
		* bc = block column
		* c = column
		*/
		for (int br = 0; br < 3; br++)
			for (int r = 0; r < 3; r++)  
				for (int bc = 0; bc < 3; bc++) 
					for (int c = 0; c < 3; c++) 
						puzzle[r][c][br][bc] = sc.nextInt();			
	}

	@Override
	public String toString() {
		String str = "";
		for (int br = 0; br < 3; br++) {
			for (int r = 0; r < 3; r++) {  
				for (int bc = 0; bc < 3; bc++) { 
					for (int c = 0; c < 3; c++) {
						str = str + Integer.toString(puzzle[r][c][br][bc]) + " ";		
					}
					str = str + "| ";
				}
				str = str + "\n";
			}
			str = str + "-----------------------\n";
		}	
		return str;
	}	
}
