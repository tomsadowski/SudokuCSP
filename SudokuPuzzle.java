package sudoku;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
public class SudokuPuzzle {
	/*
	* Initializes all entries to 0
	* Each entry corresponds to an entry (domain) in domains
	* Each entry will be updated with the size of its corresponding domain after the variable is explored
	* by the appropriate method of inference, thus eliminating it from any further exploration
	* by the same method of inference
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

	public boolean inference(int _mag) {
		boolean removed = false;
		boolean exploreAgain = true;
		while (exploreAgain) { 
			exploreAgain = false;
			for (int br = 0; br < 3; br++) 
				for (int r = 0; r < 3; r++)   
					for (int bc = 0; bc < 3; bc++)  
						for (int c = 0; c < 3; c++) 
							if (domains[r][c][br][bc].size() == _mag && 
							exploredMatrix[r][c][br][bc] != _mag) {
								exploreAgain = true;
								exploredMatrix[r][c][br][bc] = _mag;
								if (searchUnits(_mag, r, c, br, bc))
									removed = true;
							}
		}
		return removed;
	}

	private boolean searchUnits(int _mag, int _r, _c, _br, _bc) {
		// search row
		int quantity = 1;
		int[][][][] inferenceVariables = new int[3][3][3][3];
		inferenceVariables[_r][_c][_br][_bc] = 1;
		for (int bc = 0; bc < 3; bc++)
			for (int c = 0; c < 3; c++) {
				if (quantity < mag)	
					if (c != _c || bc != _bc)
						if (exploredMatrix != mag && 
						domains[_r][c][_br][bc].size() <= mag && domains[_r][c][_br][bc].size() > 1)
							if (isSubset(_r, _c, _br, _bc, _r, c, _br, bc)) {
								quantity++;
								inferenceVariables[_r][c][_br][bc] = 1;
							}
				if (quantity == mag)
					remove('r', domains[_r][_c][_br][_bc], inferenceVariables);
			}
		// search column
		int quantity = 1;
		int[][][][] inferenceVariables = new int[3][3][3][3];
		inferenceVariables[_r][_c][_br][_bc] = 1;
		for (int br = 0; br < 3; br++)		
			for (int r = 0; r < 3; r++) {
				if (quantity < mag)	
					if (r != _r || br != _br)
						if (exploredMatrix != mag && 
						domains[r][_c][br][_bc].size() <= mag && domains[r][_c][br][_bc].size() > 1)
							if (isSubset(_r, _c, _br, _bc, r, _c, br, _bc)) {
								quantity++;
								inferenceVariables[r][_c][br][_bc] = 1;
							}
				if (quantity == mag)
					remove('c', domains[_r][_c][_br][_bc], inferenceVariables);
			}
		// search block
		int quantity = 1;
		int[][][][] inferenceVariables = new int[3][3][3][3];
		inferenceVariables[_r][_c][_br][_bc] = 1;
		for (int r = 0; r < 3; r++)
			for (int c = 0; c < 3; c++) {
				if (quantity < mag)	
					if (r != _r || c != _c)
						if (exploredMatrix != mag && 
						domains[r][c][_br][_bc].size() <= mag && domains[r][c][_br][_bc].size() > 1)
							if (isSubset(_r, _c, _br, _bc, r, c, _br, _bc)) {
								quantity++;
								inferenceVariables[r][c][_br][_bc] = 1;
							}
				if (quantity == mag)
					remove('b', domains[_r][_c][_br][_bc], inferenceVariables);
			}
	} 

	private boolean isSubset(int r, int c, int br, int bc, int r2, int c2, int br2, int bc2) {
		for (int i = 1; i < domains[r2][c2][br2][bc2].size(); i++) 
			if (!domains[r][c][br][bc].contains(domains[r2][c2][br2][bc2].get(i))
				return false;
		return true;		
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
