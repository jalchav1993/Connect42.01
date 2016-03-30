/*
 * Author: Jesus Chavez
 * Connect 4
 * Adv Obj Oriented Programming
 */
package advobjectoriented.Connect4;

import java.awt.Dimension;

public class Grid {
	boolean isTie;
	boolean hasDiagonal;
	String[][] board;
	/*
	 * Creates new grid
	 * @param i row
	 * @param j column
	 */
	//i is rows
	//j is columns
	Grid(int i, int j){
		board = new String[i][j];
		hasDiagonal = false;
		isTie = false;
	}
	/*
	 * Initialize board
	 */
	public void init(){
		//initializes the board
		//sets '-'as empty space
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j <board[i].length; j++) {
				board[i][j] = "_";
			}
		}
	}
	/*
	 * Sets a new token in board[][]
	 * @param token players token
	 * @param j column
	 */
	public int setToken(String token, int j) throws MoveOutOfBoundsException{
		//sets a token in the board updates game status, win, tie or continue
		
		int i = nextEmptyBox(0, j);
		System.out.println(i);
		if(i == 0 && !board[0][j].equals("_"))
			throw new MoveOutOfBoundsException();
		board[i][j] = token;
		hasDiagonal = hasDiagonal(i,j,token);
		isTie = hasTie();
		drawBoard();
		return i;
	}
	/*
	 * Determines if tie in board by checking last row
	 */
	public boolean hasTie() {
		//returns true if the board is filled
		//by checking the j columns in the first row
		for (int j = 0; j < board[0].length; j++) {
			if(board[0][j].equals("_"))
				return false;
		}
		return true;
	}
	/*
	 * Finds the next available position
	 * @param i row
	 * @param j column
	 */
	private int nextEmptyBox(int i, int j){
	//returns the next empty box by doing a search tree
		//next empty box is not'_'
		if((i>=board.length-1)||(!board[i+1][j].equals("_")))
			//fixing bug where 
			return i;
		return nextEmptyBox(i+1, j);
	}
	/*
	 * Sets a new token in board[][]
	 * @param i row
	 * @param j column
	 * @param token token type
	 */
	public boolean hasDiagonal(int i, int j, String token){
		//search tree looks for diagonal in current x position i j
		//only searches consecutive places
		//takes care of diagonals in between by adding all the x consecutive diagonal
		//tokens in the board starting from x token
		System.out.println(i+" "+j);
		int leftDiagonal = 1 + upLeft(i-1, j-1, token)
							 + downRight(i+1, j+1, token);
		int rightDiagonal = 1 + upRight(i-1, j+1, token)
							  + downLeft(i+1, j-1, token);
		int vertical = 1 + rightVertical(i, j+1, token)
						 + leftVertical(i, j-1, token);
		int horizontal = 1 + northHorizontal(i-1, j, token)
						   + southHorizontalz(i+1, j, token);
		if(leftDiagonal>=4
				||rightDiagonal>=4
				||vertical>=4
				||horizontal >= 4
				)
			return true;
		return false;
	}
	/*
	 * Searches the 3 of the consecutive veritical grid locations to the right
	 * @param i row
	 * @param j column
	 * @param token token type
	 */
	private int rightVertical(int i, int j, String token){
		if(j<board[0].length&&board[i][j].equals(token))
			return 1+ rightVertical(i, j+1, token);
		return 0;
	}
	/*
	 * Searches the 3 of the consecutive vertical grid locations to the left
	 * @param i row
	 * @param j column
	 * @param token token type
	 */
	private int leftVertical(int i, int j, String token){
		if(j>=0&&board[i][j].equals(token))
			return 1+ leftVertical(i, j-1, token);
		return 0;
	}
	/*
	 * Searches the 3 of the consecutive horizontal grid locations above
	 * @param i row
	 * @param j column
	 * @param token token type
	 */
	private int northHorizontal(int i, int j, String token){
		if(i>=0&&board[i][j].equals(token))
			return 1 + northHorizontal(i-1, j, token);
		return 0;
	}
	/*
	 * Searches the 3 of the consecutive horizontal grid locations bellow
	 * @param i row
	 * @param j column
	 * @param token token type
	 */
	private int southHorizontalz(int i, int j, String token){
		//searches down right
		if(i<board.length&&board[i][j].equals(token))
			return 1 + southHorizontalz(i+1, j, token);
		return 0;
	}
	/*
	 * Searches the 3 of the consecutive diagonal grid locations above-left
	 * @param i row
	 * @param j column
	 * @param token token type
	 */
	private int upLeft(int i, int j, String type){
		//searches up left
		if(i>=0&&j>=0&&board[i][j].equals(type))
			return 1 + upLeft(i-1, j-1, type);
		return 0;
	}
	/*
	 * Searches the 3 of the consecutive diagonal grid locations above-right
	 * @param i row
	 * @param j column
	 * @param token token type
	 */
	private int upRight(int i, int j, String type){
		//searches for diagonals up right
		if(i>=0&&j<board[0].length&&board[i][j].equals(type))
			return 1 + upRight(i-1, j+1, type);
		return 0;
	}
	/*
	 * Searches the 3 of the consecutive diagonal grid locations down-left
	 * @param i row
	 * @param j column
	 * @param token token type
	 */
	private int downLeft(int i, int j, String type){
		//searches down left
		if(i<board.length&&j>=0&&board[i][j].equals(type))
			return 1 + downLeft(i+1, j-1, type);
		return 0;
	}
	/*
	 * Searches the 3 of the consecutive diagonal grid locations down-right
	 * @param i row
	 * @param j column
	 * @param token token type
	 */
	private int downRight(int i, int j, String type){
		//searches down right
		if(i<board.length&&j<board[0].length&&board[i][j].equals(type))
			return 1 + downRight(i+1, j+1, type);
		return 0;
	}
	/*
	 * Draws Board
	 */
	public void drawBoard(){
		//draws the current board
		System.out.println("new turn");
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
	}
}
	
