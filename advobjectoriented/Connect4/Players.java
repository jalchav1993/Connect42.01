/*
 * Author: Jesus Chavez
 * Connect 4
 * Adv Obj Oriented Programming
 */
package advobjectoriented.Connect4;

public class Players {
	private String p1;
	private String p2;
	private String p1Token;
	private String p2Token;
	private String current;
	/*
	 * Constructor randomly assigns
	 * @param p1 player 1 name
	 * @param p1 player 2 name
	 */
	public Players(String p1, String p2){
		this.p1 = p1;
		this.p2 = p2;
		p1Token = "X";
		p2Token = "O";
		//random assign
		double test1 = Math.random();
		double test2 = Math.random();
		if(test1>test2){
			System.out.println("test1");
			current = p1;
		}
		else{
			System.out.println("test2");
			current = p2;
		}
	}
	/*
	 * returns current player state
	 */
	public String getCurrentPlayer(){
		return current;
	}
	/*
	 * returns current player state token
	 */
	public String getCurrentToken(){
		if(current.equals(p1)){
			return p1Token;
		}else{
			return p2Token;
		}
	}
	/*
	 * advances to next player state
	 */
	public String next(){
		if(current.equals(p1)){
			current = p2;
		}else{
			current = p1;
		}
		return current;
	}
	/*
	 * returns all the players
	 */
	public String[] getPlayers(){
		return new String[] {p1,p2};
	}
}
