package sudoku;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
public class SudokuPuzzle {

	int[][][][] puzzle;
	ArrayList[][][][] domains;

	public SudokuPuzzle(String filePath) throws Exception {
		Scanner sc = new Scanner(new File(filePath));
		puzzle = new int[3][3][3][3];
		domains = new ArrayList[3][3][3][3];

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
						int val = sc.nextInt();	
						if (val != 0) {	
				  			domains[r][c][br][bc] = new ArrayList();
							domains[r][c][br][bc].add(val);
						}
						else {
							domains[r][c][br][bc] = new ArrayList(9);
							for (int i = 1; i <= 9; i++)
								domains[r][c][br][bc].add(i);
						}
					 }	
	}

	public void propagateConstraints() {
		boolean exploreAgain = true;
		while (exploreAgain) { 
			exploreAgain = false;
			for (int br = 0; br < 3; br++) 
				for (int r = 0; r < 3; r++)   
					for (int bc = 0; bc < 3; bc++)  
						for (int c = 0; c < 3; c++) {
							if (domains[r][c][br][bc].size() == 1 && puzzle[r][c][br][bc] == 0) {
								exploreAgain = true;
								puzzle[r][c][br][bc] = (int)domains[r][c][br][bc].get(0);
								removeFromNeighbors(r, c, br, bc);  
							}
						}			
		}
	}

	private void removeFromNeighbors(int _r, int _c, int _br, int _bc) {
		for (int r = 0; r < 3; r++)  
			for (int c = 0; c < 3; c++) 
				if (r != _r || c != _c) {
					int index = domains[r][c][_br][_bc].indexOf(puzzle[_r][_c][_br][_bc]);
					if (index != -1)
						domains[r][c][_br][_bc].remove(index);
				}
		for (int br = 0; br < 3; br++) 
			if (br != _br) 
				for (int r = 0; r < 3; r++) {
					int index = domains[r][_c][br][_bc].indexOf(puzzle[_r][_c][_br][_bc]);
					if (index != -1)
						domains[r][_c][br][_bc].remove(index);
				}
		for (int bc = 0; bc < 3; bc++) 
			if (bc != _bc) 
				for (int c = 0; c < 3; c++) {
					int index = domains[_r][c][_br][bc].indexOf(puzzle[_r][_c][_br][_bc]);
					if (index != -1)
						domains[_r][c][_br][bc].remove(index);
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
