package sudoku;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
public class SudokuPuzzle {

	int[][][][] puzzle = new int[3][3][3][3];
	ArrayList[][][][] domains = new ArrayList[3][3][3][3];

	public SudokuPuzzle(String filePath) throws Exception {
		Scanner sc = new Scanner(new File(filePath));
 		/* 
		* Loads the puzzle and the initial domains of the puzzle
		* br = block row
		* r = row
		* bc = block column
		* c = column
		*/
 		for (int br = 0; br < 3; br++)
			for (int r = 0; r < 3; r++)  
				for (int bc = 0; bc < 3; bc++) 
					for (int c = 0; c < 3; c++) { 
						puzzle[r][c][br][bc] = sc.nextInt();	
						if (puzzle[r][c][br][bc] != 0) {	
				  			domains[r][c][br][bc] = new ArrayList();
							domains[r][c][br][bc].add(puzzle[r][c][br][bc]);
						}
						else {
							domains[r][c][br][bc] = new ArrayList(9);
							for (int i = 1; i <= 9; i++)
								domains[r][c][br][bc].add(i);
						}
					 }	
	}

	@Override
	public String toString() {
		String str = "";
		// print puzzle
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
		str = str + "\n\n";
		// print domains
		for (int br = 0; br < 3; br++) {
			for (int r = 0; r < 3; r++) {  
				for (int bc = 0; bc < 3; bc++) { 
					for (int c = 0; c < 3; c++) {
						str = str + String.format("%10s", domainString(domains[r][c][br][bc]));		
					}
					str = str + "| ";
				}
				str = str + "\n";
			}
			str = str + "\n";
		}	 
		return str;
	}	
	
	private String domainString(ArrayList domain) {
		String str = "";
		for (int i = 0; i < domain.size(); i++)
			str = str + domain.get(i);
		return str;
	}
}
