This program is to be run from the command line. 
It takes two arguments:
	-The first argument must be a filepath to a plaintext file that represents the sudoku puzzle. The 
	cells of the puzzle will be represented by numbers 0 through 9, where 0 means the cell is empty.
	Each cell is seperated by a space, the puzzles used to test this program
	can be found here - http://lipas.uwasa.fi/~timan/sudoku/
	-The second argument is known to the program as 'magnitude'. Magnitude must be a postive integer, 
	and that positive integer indicates the complexity of the inference to be done on the puzzle. Though 
	the highest reasonable number may be lower, it is certainly pointless to pass a number higher than 9. 
	However, the program will accept higher numbers just for fun.
Output:
	There will be three tables that are output to the terminal:
	-The first table is the unsolved puzzle. In place of the 0's, there will be a list of integers that 
	represent the possible values (domain) of that cell, given no knowledge of the cells that surround them.
	-The second table will be the result of inference. The domains of each cell are as reduced as they can be
	given the magnititude of inference that was passed to the main method.
	-The last table will be the solution to the sudoku puzzle. This is accomplished using the backtracking 
	algorithm, which always solves the puzzle.
