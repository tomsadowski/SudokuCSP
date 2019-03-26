package sudoku;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
public class SudokuPuzzle {
	/*
	* Initializes all entries to 0
	* Each entry corresponds to an entry (domain) in domains
	* Each entry will be updated with the size of its corresponding domain after the variable is explored
	* by the appropriate means of inference, thus eliminating it from any further exploration
	* by the same means of inference
	*/
	private int[][][][] exploredMatrix;
	/*
	* Initializes all non-singleton variables to the widest domain
	* Intializes all singleton variables to the value from the puzzle
	*/
	private ArrayList[][][][] domains;

	public SudokuPuzzle(String filePath) throws Exception {
		Scanner sc = new Scanner(new File(filePath));
		exploredMatrix = new int[3][3][3][3];
		domains = new ArrayList[3][3][3][3];
 		/* 
		* Loads the initial domains
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

	public void singletonInference() {
		boolean exploreAgain = true;
		while (exploreAgain) { 
			exploreAgain = false;
			for (int br = 0; br < 3; br++) 
				for (int r = 0; r < 3; r++)   
					for (int bc = 0; bc < 3; bc++)  
						for (int c = 0; c < 3; c++) 
							if (domains[r][c][br][bc].size() == 1 && 
							exploredMatrix[r][c][br][bc] != 1) {
								exploreAgain = true;
								exploredMatrix[r][c][br][bc] = 1;
								removeSingletonValue((int)domains[r][c][br][bc].get(0),
								r, c, br, bc);  
							}
		}
	}

	// helper for singletonInference 
	private void removeSingletonValue(int _val, int _r, int _c, int _br, int _bc) {
		// removes value from variable's block
		for (int r = 0; r < 3; r++)  
			for (int c = 0; c < 3; c++) 
				if (r != _r || c != _c) {
					int index = domains[r][c][_br][_bc].indexOf(_val);
					if (index != -1)
						domains[r][c][_br][_bc].remove(index);
				}
		// removes value from variable's column
		for (int br = 0; br < 3; br++) 
			if (br != _br) 
				for (int r = 0; r < 3; r++) {
					int index = domains[r][_c][br][_bc].indexOf(_val);
					if (index != -1)
						domains[r][_c][br][_bc].remove(index);
				}
		// removes value from variable's row
		for (int bc = 0; bc < 3; bc++) 
			if (bc != _bc) 
				for (int c = 0; c < 3; c++) {
					int index = domains[_r][c][_br][bc].indexOf(_val);
					if (index != -1)
						domains[_r][c][_br][bc].remove(index);
				}
	}

	public boolean isSolved() {
		for (int br = 0; br < 3; br++) 
			for (int r = 0; r < 3; r++)   
				for (int bc = 0; bc < 3; bc++)  
					for (int c = 0; c < 3; c++) 
						if (exploredMatrix[r][c][br][bc] != 1)
							return false;
		return true;		
	}

	private String domainString(ArrayList domain) {
		String str = "";
		for (int i = 0; i < domain.size(); i++)
			str = str + domain.get(i);
		return str;
	}

	@Override
	public String toString() {
		String str = "";
		// print exploredMatrix 
		for (int br = 0; br < 3; br++) {
			for (int r = 0; r < 3; r++) {  
				for (int bc = 0; bc < 3; bc++) { 
					for (int c = 0; c < 3; c++) {
						str = str + Integer.toString(exploredMatrix[r][c][br][bc]) + " ";		
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
}
