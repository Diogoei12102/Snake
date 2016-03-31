package snake.eng;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import snake.gui.GameGraphics;

/**
 * Does all the operations related with the logic of the game 
 *
 */
public class Engine {
	String board[][];
	double speed = 1.0;
	private int boardsize;
	private int width;
	private PowerUps nextpowerup;
	private boolean end = false;
	Snake snake;
	int lastDir = 2;
	int movedelay = 500;
	private double score = 0;
	Timer moveTimer;
	TimerTask moveTimerTask;
	Timer powerTimer;
	TimerTask powerTimerTask;
	boolean hasStarted = false;
	GameGraphics mainGame = null;
	int cutTime = 1;
	boolean passWalls = true;
	int animationTime;

	
/**
 * starts a game previously saved 
 */
	public void startLoaded() {
		startTimers();

	}
/**
 * starts the game
 */
	public void startGame() {
		hasStarted = true;
		moveTimer = new Timer();
		moveTimerStart();
		powerTimer = new Timer();
		powerTimerTask = new TimerTask() {
			public void run() {

				powerUpsTask();

			}
		};

		powerTimer.schedule(powerTimerTask, 10000, 5000);
	}

	/**
	 * starts the snake move timer
	 */
	public void moveTimerStart() {
		moveTimer.cancel();
		moveTimer = new Timer();
		moveTimerTask = new TimerTask() {
			public void run() {

				timertask();

			}
		};
		animationTime = (int) (movedelay / speed);
		moveTimer.schedule(moveTimerTask, (int) (movedelay / speed),
				(int) (movedelay / speed));

	}

	/**
	 * the task of the power up timer
	 */
	public void powerUpsTask() {
		if (nextpowerup != null)
			deleteLastPowerUp();
		nextPowerUp();
		placeNextPowerUp();

	}

	/**
	 * deletes last PowerUP
	 */
	private synchronized void deleteLastPowerUp() {
		board[nextpowerup.getY()][nextpowerup.getX()] = " ";

	}

	public void timertask() {

		if (!getEnd()) {

			moveSnake();
			drawSnake();
		}
	}

	/**
	 * starts a new game Engine
	 * 
	 * @param size
	 * 
	 * @param speed
	 */
	public Engine(int size, int speed) {
		this.setBoardsize(size);
		setWidth((size * 16) / 10);
		this.speed = speed;
		createNewBoard(getBoardsize());
		snake = new Snake(getBoardsize() / 2, getWidth() / 2);
		snake.addPiece((getBoardsize() / 2) - 1, (getWidth() / 2));
		drawSnake();
		placeFoodRandom();

	}

	/**
	 * Creates a new board with the desired size
	 * 
	 * @param size
	 */
	private void createNewBoard(int size) {

		String[][] newboard = new String[size][getWidth()];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < getWidth(); j++) {

				if (i == 0 || i == (size - 1) || j == 0
						|| j == (getWidth() - 1)) {
					newboard[i][j] = "X";
				} else
					newboard[i][j] = " ";

			}

		}
		board = newboard;
	}

	/**
	 * Prints board on console
	 */
	void printBoard() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				System.out.print(board[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
	}

	/**
	 * Selects the nextPowerUP
	 */
	private void nextPowerUp() {
		String[] types = { "S", "L", "K", "H" };
		int selection;
		String nextPowerUp;
		Random r = new Random();
		selection = r.nextInt(4);
		cutTime++;
		if (cutTime > 5)
			cutTime = 0;
		while (selection == 3 && cutTime != 0) {
			selection = r.nextInt(4);
		}
		nextPowerUp = types[selection];
		this.nextpowerup = new PowerUps(nextPowerUp);
	}

	/**
	 * Places next powerUp ramdomly
	 */
	private synchronized void placeNextPowerUp() {
		boolean done = false;
		int ypos, xpos;
		Random r = new Random();
		do {

			ypos = r.nextInt(getBoardsize());
			xpos = r.nextInt(getWidth());
			if (board[ypos][xpos].equals(" ")) {
				if (mainGame != null)
					if (nextpowerup.getType().equals("K"))
						mainGame.newBomb.playSoundOnce();
					else
						mainGame.powerUpRespawn.playSoundOnce();
				board[ypos][xpos] = nextpowerup.getType();
				nextpowerup.setY(ypos);
				nextpowerup.setX(xpos);
				done = true;

			}

		} while (!done);

	}

	/**
	 * Places food randomly
	 */
	private synchronized void placeFoodRandom() {
		boolean done = false;
		int ypos, xpos;
		Random r = new Random();
		do {

			ypos = r.nextInt(getBoardsize() - 2);
			xpos = r.nextInt(getWidth() - 2);
			ypos++;
			xpos++;
			if (board[ypos][xpos].equals(" ")) {
				board[ypos][xpos] = "F";
				done = true;
			}

		} while (!done);

	}

	/**
	 * Draws snake on the board
	 */
	public synchronized void drawSnake() {
		Vector<int[]> temp = snake.getPieces();
		for (int i = 0; i < temp.size(); i++) {
			if (i == 0) {
				board[temp.get(i)[0]][temp.get(i)[1]] = "C";

			} else if (i == (temp.size() - 1))
				board[temp.get(i)[0]][temp.get(i)[1]] = "t";
			else
				board[temp.get(i)[0]][temp.get(i)[1]] = "c";
		}

	}

	/**
	 * removes the pieces of the snake that are cut by the H powerUp
	 */
	public synchronized void removeSnake() {
		Vector<int[]> temp = snake.getRemove();
		if (temp.size() > 0)
			for (int i = 0; i < temp.size(); i++) {
				board[temp.get(i)[0]][temp.get(i)[1]] = " ";

			}

		snake.cleanRemover();
	}

	/**
	 * Move the snake to next position. Verifies the content of the next
	 * position
	 */
	public synchronized void moveSnake() {
		int dx = 0;
		int dy = 0;
		if (lastDir == 1) {
			if (snake.getDirection() != 0) {
				dx = 0;
				dy = -1;

			}
		} else if (lastDir == 0) {
			if (snake.getDirection() != 1) {
				dx = 0;
				dy = 1;
			}
		} else if (lastDir == 2) {
			if (snake.getDirection() != 3) {
				dx = -1;
				dy = 0;
			}
		} else if (lastDir == 3) {
			if (snake.getDirection() != 2) {
				dx = 1;
				dy = 0;

			}
		}
		Vector<int[]> temp = snake.getPieces();
		snake.setDirection(lastDir);
		if (board[temp.elementAt(0)[0] + dy][temp.elementAt(0)[1] + dx]
				.equals(" ")
				|| board[temp.elementAt(0)[0] + dy][temp.elementAt(0)[1] + dx]
						.equals("S")
				|| board[temp.elementAt(0)[0] + dy][temp.elementAt(0)[1] + dx]
						.equals("L")
				|| board[temp.elementAt(0)[0] + dy][temp.elementAt(0)[1] + dx]
						.equals("H")
				|| (board[temp.elementAt(0)[0] + dy][temp.elementAt(0)[1] + dx]
						.equals("X") && passWalls)) {
			board[temp.lastElement()[0]][temp.lastElement()[1]] = " ";
			if (board[temp.elementAt(0)[0] + dy][temp.elementAt(0)[1] + dx]
					.equals("H")) {
				if (snake.getPieces().size() >= 4) {
					snake.cutSnakeHalf();
					removeSnake();
					temp = snake.getPieces();
					board[temp.lastElement()[0]][temp.lastElement()[1]] = " ";
				}
				if (mainGame != null)
					mainGame.cutSound.playSoundOnce();

			} else if (board[temp.elementAt(0)[0] + dy][temp.elementAt(0)[1]
					+ dx].equals("S")) {
				speed = speed * 1.5;
				if (mainGame != null)
					mainGame.speedSound.playSoundOnce();
				if (hasStarted) {
					moveTimer.cancel();
					moveTimerStart();
				}
			} else if (board[temp.elementAt(0)[0] + dy][temp.elementAt(0)[1]
					+ dx].equals("L")) {
				if (mainGame != null)
					mainGame.slowSound.playSoundOnce();
				speed = speed / 1.5;
				if (hasStarted) {
					moveTimer.cancel();
					moveTimerStart();
				}

			}
			for (int i = temp.size() - 1; i >= 0; i--) {
				int[] newPos = { 0, 0 };
				if (i == 0) {
					newPos[0] = (dy + temp.elementAt(i)[0]);
					newPos[1] = (dx + temp.elementAt(i)[1]);
					if (passWalls && board[newPos[0]][newPos[1]] == "X") {
						if (newPos[0] == 0) {
							newPos[0] = board.length - 2;
							verifyFood(newPos[0], newPos[1], temp, dy, dx);
						}
						if (newPos[0] == board.length - 1) {
							newPos[0] = 1;
							verifyFood(newPos[0], newPos[1], temp, dy, dx);
						}
						if (newPos[1] == 0) {
							newPos[1] = board[0].length - 2;
							verifyFood(newPos[0], newPos[1], temp, dy, dx);
						}
						if (newPos[1] == board[0].length - 1) {
							newPos[1] = 1;
							verifyFood(newPos[0], newPos[1], temp, dy, dx);
						}
					}
					snake.actualizePiece(i, newPos);
				} else {
					newPos[0] = temp.elementAt(i - 1)[0];
					newPos[1] = temp.elementAt(i - 1)[1];
					snake.actualizePiece(i, newPos);
				}

			}
		} else if (board[temp.elementAt(0)[0] + dy][temp.elementAt(0)[1] + dx]
				.equals("F")) {
			snake.addPiece(temp.elementAt(0)[0] + dy, temp.elementAt(0)[1] + dx);
			if (mainGame != null)
				mainGame.eat.playSoundOnce();
			setScore(getScore() + speed);
			placeFoodRandom();
		} else if (board[temp.elementAt(0)[0] + dy][temp.elementAt(0)[1] + dx]
				.equals("K")) {
			setEnd(true);
			if (mainGame != null)
				mainGame.death.playSoundOnce();
		} else if ((board[temp.elementAt(0)[0] + dy][temp.elementAt(0)[1] + dx]
				.equals("X") && !passWalls)
				|| board[temp.elementAt(0)[0] + dy][temp.elementAt(0)[1] + dx]
						.equals("c")
				|| board[temp.elementAt(0)[0] + dy][temp.elementAt(0)[1] + dx]
						.equals("t")) {
			setEnd(true);
		}
	}

	/**
	 * When the food is next to some wall verifies what kind of food it is
	 * 
	 * @param posX
	 *            Coordinate x of the food
	 * @param posY
	 *            Coordinate y of the food
	 * @param temp
	 *            Snake
	 * @param dy
	 *            Move in relation to y
	 * @param dx
	 *            Move in relation to x
	 */
	private void verifyFood(int posX, int posY, Vector<int[]> temp, int dy,
			int dx) {
		if (board[posX][posY].equals("S")) {
			speed = speed * 1.5;
			if (mainGame != null)
				mainGame.speedSound.playSoundOnce();
			if (hasStarted) {
				moveTimer.cancel();
				moveTimerStart();
			}
		} else if (board[posX][posY].equals("L")) {
			if (mainGame != null)
				mainGame.slowSound.playSoundOnce();
			speed = speed / 1.5;
			if (hasStarted) {
				moveTimer.cancel();
				moveTimerStart();
			}
		} else if (board[posX][posY].equals("F")) {
			snake.addPiece(temp.elementAt(0)[0] + dy, temp.elementAt(0)[1] + dx);
			if (mainGame != null)
				mainGame.eat.playSoundOnce();
			setScore(getScore() + speed);
			placeFoodRandom();
		} else if (board[posX][posY].equals("K")) {
			setEnd(true);
			if (mainGame != null)
				mainGame.death.playSoundOnce();
		}
	}

	public String[][] getBoard() {
		return board;
	}

	public boolean getGameOver() {
		return getEnd();
	}

	public int getLastDir() {

		return lastDir;
	}

	public int getScore() {
		return (int) score;
	}

	public void setScore(double d) {
		this.score = d;
	}

	public void stopTimers() {
		powerTimer.cancel();
		moveTimer.cancel();
	}

	public void startTimers() {
		moveTimerStart();
		powerTimer = new Timer();
		powerTimer.schedule(powerTimerTask, 10000, 5000);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getBoardsize() {
		return boardsize;
	}

	public void setBoardsize(int boardsize) {
		this.boardsize = boardsize;
	}

	public double getSpeed() {

		return speed;
	}

	public void setBoardItem(int i, int j, String string) {
		board[i][j] = string;

	}

	public Snake getSnake() {

		return snake;
	}

	public void setSnake(Snake snake) {
		this.snake = snake;

	}

	public boolean getEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	public void setGameGraph(GameGraphics game) {
		mainGame = game;
	}

	public void setPassWalls(boolean passWalls) {
		this.passWalls = passWalls;
	}

	public int getAnimationTime() {

		return animationTime;
	}
	public void setLastDir(int lastDir) {
		this.lastDir = lastDir;
	}
}
