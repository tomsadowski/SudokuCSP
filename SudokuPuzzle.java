package sudoku;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
public class SudokuPuzzle {
	/*
	* Initializes all entries to false
	* Each entry corresponds to an entry (domain) in domains
	* Each entry will be updated with the size of its corresponding domain after the variable is explored
	* by the appropriate method of inference, thus eliminating it from any further exploration
	* by the same method of inference
	*/
	private boolean[][][][] exploredSingletons;
	/*
	* Initializes all non-singleton variables to the widest domain
	* Intializes all singleton variables to the value from the puzzle
	*/
	private ArrayList[][][][] domains;

	public SudokuPuzzle(String filePath) throws Exception {
		Scanner sc = new Scanner(new File(filePath));
		exploredSingletons = new boolean[3][3][3][3];
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
			boolean[][][][] explored = new boolean[3][3][3][3];
			exploreAgain = false;
			for (int br = 0; br < 3; br++) 
				for (int r = 0; r < 3; r++)   
					for (int bc = 0; bc < 3; bc++)  
						for (int c = 0; c < 3; c++) 
							if (_mag != 1 || !exploredSingletons[r][c][br][bc])
								if (domains[r][c][br][bc].size() == _mag) {
									if (_mag == 1)
										exploredSingletons[r][c][br][bc] = true;
									explored[r][c][br][bc] = true;	
									if (searchUnits(explored, r, c, br, bc)) {
										exploreAgain = true;
										removed = true;
									}
								}
		}
		return removed;
	}

	private boolean searchUnits(boolean[][][][] _explored, int _r, int _c, int _br, int _bc) {
		int mag = domains[_r][_c][_br][_bc].size();
		boolean removed = false;
		// search row
		int quantity = 1;
		boolean[][][][] inferenceVariables = new boolean[3][3][3][3];
		inferenceVariables[_r][_c][_br][_bc] = true;
		for (int bc = 0; bc < 3; bc++)
			for (int c = 0; c < 3; c++) {
				if (quantity < mag)	
					if (!_explored[_r][c][_br][bc] && 
					domains[_r][c][_br][bc].size() <= mag && domains[_r][c][_br][bc].size() > 1)
						if (isSubset(domains[_r][_c][_br][_bc], domains[_r][c][_br][bc])) {
							quantity++;
							inferenceVariables[_r][c][_br][bc] = true;
						}
				if (quantity == mag) 
					if (remove('r', _r, _c, _br, _bc, inferenceVariables))
						removed = true;
			}
		// search column
		quantity = 1;
		inferenceVariables = new boolean[3][3][3][3];
		inferenceVariables[_r][_c][_br][_bc] = true;
		for (int br = 0; br < 3; br++)		
			for (int r = 0; r < 3; r++) {
				if (quantity < mag)	
					if (!_explored[r][_c][br][_bc] && 
					domains[r][_c][br][_bc].size() <= mag && domains[r][_c][br][_bc].size() > 1)
						if (isSubset(domains[_r][_c][_br][_bc], domains[r][_c][br][_bc])) {
							quantity++;
							inferenceVariables[r][_c][br][_bc] = true;
						}
				if (quantity == mag)
					if (remove('c', _r, _c, _br, _bc, inferenceVariables))
						removed = true;
			}
		// search block
		quantity = 1;
		inferenceVariables = new boolean[3][3][3][3];
		inferenceVariables[_r][_c][_br][_bc] = true;
		for (int r = 0; r < 3; r++)
			for (int c = 0; c < 3; c++) {
				if (quantity < mag)	
					if (!_explored[r][c][_br][_bc] && 
					domains[r][c][_br][_bc].size() <= mag && domains[r][c][_br][_bc].size() > 1)
						if (isSubset(domains[_r][_c][_br][_bc], domains[r][c][_br][_bc])) {
							quantity++;
							inferenceVariables[r][c][_br][_bc] = true;
						}
				if (quantity == mag)
					if (remove('b', _r, _c, _br, _bc, inferenceVariables))
						removed = true;
			}
		return removed;
	} 

	private boolean remove(char _unit, int _r, int _c, int _br, int _bc, boolean[][][][] _inferenceVariables) {
		boolean removed = false;
		switch (_unit) {
			// removes values from row
			case 'r':
				for (int bc = 0; bc < 3; bc++)
					for (int c = 0; c < 3; c++) 
						if (!_inferenceVariables[_r][c][_br][bc])
							for (int i = 0; i < domains[_r][_c][_br][_bc].size(); i++) {
								int index = domains[_r][c][_br][bc].indexOf(
								domains[_r][_c][_br][_bc].get(i));
								if (index != -1) {
									domains[_r][c][_br][bc].remove(index);
									removed = true;
								}
							}
				break;
			// removes values from column
			case 'c':
				for (int br = 0; br < 3; br++)		
					for (int r = 0; r < 3; r++) 
						if (!_inferenceVariables[r][_c][br][_bc])
							for (int i = 0; i < domains[_r][_c][_br][_bc].size(); i++) {
								int index = domains[r][_c][br][_bc].indexOf(
								domains[_r][_c][_br][_bc].get(i));
								if (index != -1) {
									domains[r][_c][br][_bc].remove(index);
									removed = true;
								}
							}
				break;
			// removes values from block
			case 'b': 
				for (int r = 0; r < 3; r++)
					for (int c = 0; c < 3; c++) 
						if (!_inferenceVariables[r][c][_br][_bc])
							for (int i = 0; i < domains[_r][_c][_br][_bc].size(); i++) {
								int index = domains[r][c][_br][_bc].indexOf(
								domains[_r][_c][_br][_bc].get(i));
								if (index != -1) {
									domains[r][c][_br][_bc].remove(index);
									removed = true;
								}
							}
				break;
			default:
				break;
		}
		return removed;
	}  

	private boolean isSubset(ArrayList _domain1, ArrayList _domain2) {
		for (int i = 0; i < _domain2.size(); i++) 
			if (!_domain1.contains(_domain2.get(i)))
				return false;
		return true;		
	}
	
	public boolean solved() {
		for (int br = 0; br < 3; br++) 
			for (int r = 0; r < 3; r++)   
				for (int bc = 0; bc < 3; bc++)  
					for (int c = 0; c < 3; c++) 
						if (domains[r][c][br][bc].size() != 1)
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
						str = str + String.format("%-6b", exploredSingletons[r][c][br][bc]);		
					}
					str = str + "| ";
				}
				str = str + "\n";
			}
			str = str + "\n";
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
