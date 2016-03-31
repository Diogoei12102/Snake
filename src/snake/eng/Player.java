package snake.eng;

import java.io.Serializable;

/**
 * Person to put in the leaderboard 
 *
 */
public class Player implements Comparable<Player>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 23L;
	String name;
	int score;

	public Player(String name, int score) {
		this.name = name;
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	@Override
	public int compareTo(Player pl) {
		return this.score < pl.score ? 1 : (this.score > pl.score ? -1 : 0);
	}
}
