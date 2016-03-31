package snake.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import snake.eng.Engine;

/**
 * 
 * Class where the board is painted and receives player inputs
 */
public class GameGraphics extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private int boardsize;
	private int keyUp = KeyEvent.VK_UP;
	private int keyDown = KeyEvent.VK_DOWN;
	private int keyLeft = KeyEvent.VK_LEFT;
	private int keyRight = KeyEvent.VK_RIGHT;
	private int keyPause = KeyEvent.VK_ESCAPE;
	private Settings settings;
	private Menu mainmenu;
	private int move = 2;
	private int lastMove = 2;
	private Image snakeHead;
	private Image snakeBody;
	private Image snakeTail;
	private Image food;
	private Image kill;
	private Image speed;
	private Image slow;
	private Image path;
	private Image wall;
	private Image cut;
	public SoundPlayer eat;
	public SoundPlayer newBomb;
	public SoundPlayer death;
	public SoundPlayer speedSound;
	public SoundPlayer slowSound;
	private SoundPlayer gameOver;
	public SoundPlayer powerUpRespawn;
	public SoundPlayer cutSound;
	private int imageWidth, imageHeight;
	private Timer timer;
	private int frame = 0;
	Graphics2D g2d;

	private Engine gameEngine;

	private GameSettings gameSettings;

	/**
	 * Create the panel.
	 */
	public GameGraphics(Menu mainmenu) {
		this.mainmenu = mainmenu;
		this.settings = mainmenu.getSettings();
		this.gameSettings = mainmenu.getGameSettings();

		setSize(1024, 640);
		setLocation(0, 0);
		addKeyListener(new Keyboard());
		setDoubleBuffered(true);
		mainmenu.music = new SoundPlayer(
		Menu.class.getResource("items/music.wav"));

		
		loadSounds();
		loadSprites();
		setTimer(new Timer(100, (ActionListener) this));

	}
/***
 * loads all the sounds in the game
 */
	private void loadSounds() {
		powerUpRespawn = new SoundPlayer(Menu.class.getResource("items/poweruprespawn.wav"));
		gameOver = new SoundPlayer(Menu.class.getResource("items/loser.wav"));
		eat = new SoundPlayer(Menu.class.getResource("items/eat.wav"));
		cutSound = new SoundPlayer(Menu.class.getResource("items/cutSound.wav"));
		newBomb = new SoundPlayer(Menu.class.getResource("items/newBomb.wav"));
		death = new SoundPlayer(Menu.class.getResource("items/death.wav"));
		speedSound = new SoundPlayer(Menu.class.getResource("items/speed.wav"));
		slowSound = new SoundPlayer(Menu.class.getResource("items/slow.wav"));
		
	}
/***
 * loads all the image in the game
 */
	private void loadSprites() {

		try {
			snakeHead = ImageIO.read(Menu.class.getResource("items/heads.png"));
			snakeBody = ImageIO.read(Menu.class.getResource("items/snake.png"));
			snakeTail = ImageIO.read(Menu.class.getResource("items/tails.png"));
			slow = ImageIO.read(Menu.class.getResource("items/slow.png"));
			path = ImageIO.read(Menu.class.getResource("items/path.jpg"));
			kill = ImageIO.read(Menu.class.getResource("items/kill.png"));
			speed = ImageIO.read(Menu.class.getResource("items/speed.png"));
			food = ImageIO.read(Menu.class.getResource("items/food.png"));
			wall = ImageIO.read(Menu.class.getResource("items/wall.png"));
			cut = ImageIO.read(Menu.class.getResource("items/cut.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * starts a loaded game with the engine provided
	 * 
	 * @param gameEngine;
	 */
	public void startLoadedGame(Engine gameEngine) {
		setKeys();
		this.gameEngine = gameEngine;
		this.gameEngine.setGameGraph(this);
		// this.gameEngine.startGame();
		setTimer(new Timer(gameEngine.getAnimationTime(), (ActionListener) this));
		getTimer().start();
		requestFocus();
	}

	/**
	 * starts a new game with the selected settings
	 * 
	 */
	public void Start() {
		setKeys();
		gameEngine = new Engine(gameSettings.getBoardSize(),
				gameSettings.getSpeed());
		gameEngine.setPassWalls(gameSettings.getWalls());
		gameEngine.setGameGraph(this);
		gameEngine.startGame();
		setTimer(new Timer(gameEngine.getAnimationTime(), (ActionListener) this));
		getTimer().start();
		requestFocus();

	}

	/**
	 *  
	 *  make the Moves on the game engine when player presses a key
	 */
	public void play() {
		boolean valid = true;
		if (move == 1) {
			if (gameEngine.getLastDir() == 0)
				valid = false;
		} else if (move == 0) {
			if (gameEngine.getLastDir() == 1)
				valid = false;
		} else if (move == 2) {
			if (gameEngine.getLastDir() == 3)
				valid = false;
		} else if (move == 3) {
			if (gameEngine.getLastDir() == 2)
				valid = false;
		}
		if (valid) {
			gameEngine.setLastDir(move);
			lastMove = gameEngine.getLastDir();
			gameEngine.moveSnake();

			gameEngine.drawSnake();
			gameEngine.moveTimerStart();
		}

	}
	/**
	 * check if the game has ended
	 */
	private void checkEnd() {
		if (gameEngine.getGameOver()) {
			gameEngine.stopTimers();
			mainmenu.showNameMenu();
			mainmenu.setPlaying(false);
			gameOver.playSoundOnce();
			mainmenu.music.stop();
			mainmenu.menuMusic.play();

		}
	}

	public void actionPerformed(ActionEvent e) {
		repaint();

	}

	public void paintComponent(Graphics g) {
		mainmenu.exitButton.repaint();
		super.paintComponent(g);
		g2d = (Graphics2D) g;
		checkEnd();

		drawAll(g2d);

		frame++;
		if (frame == 4)
			frame = 0;
		// repaint();

	}
	/**
	 * Draw all the components
	 * 
	 * @param g2d
	 * 
	 * @param x
	 * 
	 * @param y
	 */
	private synchronized void drawAll(Graphics2D g2d) {

		int size = gameEngine.getBoardsize();
		int width = gameEngine.getWidth();
		imageWidth = mainmenu.getContentPane().getWidth() / width;
		imageHeight = mainmenu.getContentPane().getHeight() / size;
		drawPath(g2d);
		String[][] board = gameEngine.getBoard();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < width; j++) {

				if (board[i][j].equals("X"))
					drawWall(g2d, j, i);
				else if (board[i][j].equals("S"))
					drawFast(g2d, j, i);
				else if (board[i][j].equals("L"))
					drawSlow(g2d, j, i);
				else if (board[i][j].equals("F"))
					drawFood(g2d, j, i);
				else if (board[i][j].equals("K"))
					drawKill(g2d, j, i);
				else if (board[i][j].equals("H"))
					drawCut(g2d,j,i);
				else if (board[i][j].equals("C"))
					drawHead(g2d, j, i);
				else if (board[i][j].equals("c"))
					drawBody(g2d, j, i);
				else if (board[i][j].equals("t"))
					drawTail(g2d, j, i);
			}
		}

	}

	/**
	 * Draw background
	 * 
	 * @param g2d
	 * 
	 * @param x
	 * 
	 * @param y
	 */
	private void drawPath(Graphics2D g2d2) {
		g2d2.drawImage(path, 0, 0, getWidth(), getHeight(), null);
	}


	/**
	 * Draw fast powerup
	 * 
	 * @param g2d
	 * 
	 * @param x
	 * 
	 * @param y
	 */
	private void drawFast(Graphics2D g2d2, int x, int y) {
		int dx, dy;
		dx = imageWidth * x;
		dy = imageHeight * y;

		g2d.drawImage(speed, dx, dy, dx + imageWidth, dy + imageHeight, 0, 0,
				speed.getWidth(null), speed.getHeight(null), null);

	}
	/**
	 * Draw cut powerup
	 * 
	 * @param g2d
	 * 
	 * @param x
	 * 
	 * @param y
	 */
	private void drawCut(Graphics2D g2d2, int x, int y) {
		int dx, dy;
		dx = imageWidth * x;
		dy = imageHeight * y;

		g2d.drawImage(cut, dx, dy, dx + imageWidth, dy + imageHeight, 0, 0,
				cut.getWidth(null), cut.getHeight(null), null);
	}
	/**
	 * Draw bombs
	 * 
	 * @param g2d
	 * 
	 * @param x
	 * 
	 * @param y
	 */
	private void drawKill(Graphics2D g2d2, int x, int y) {
		int dx, dy;
		dx = imageWidth * x;
		dy = imageHeight * y;

		g2d.drawImage(kill, dx, dy, dx + imageWidth, dy + imageHeight, 0, 0,
				kill.getWidth(null), kill.getHeight(null), null);

	}

	/**
	 * Draw snake tail
	 * 
	 * @param g2d
	 * 
	 * @param x
	 * 
	 * @param y
	 */
	private void drawTail(Graphics2D g2d2, int x, int y) {
		int dx, dy;
		String[][] temp = gameEngine.getBoard();
		int snakeMove = 0;
		dx = imageWidth * x;
		dy = imageHeight * y;
		if (temp[y-1][x].equals("c") || temp[y-1][x].equals("C"))
			snakeMove = 3;
		else if (temp[y+1][x].equals("c") || temp[y+1][x].equals("C"))
			snakeMove = 2;
		else if (temp[y][x-1].equals("c") || temp[y][x-1].equals("C"))
			snakeMove = 0;
		else if (temp[y][x+1].equals("c") || temp[y][x+1].equals("C"))
			snakeMove = 1;
		
		
		g2d.drawImage(snakeTail, dx, dy, dx + imageWidth, dy + imageHeight,
				0 ,
				snakeMove *snakeTail.getHeight(null) / 4, snakeTail.getWidth(null),
			(snakeMove + 1) * snakeTail.getHeight(null) / 4, null);
	}

	/**	
	 * Draw snakeBody pieces
	 * 
	 * @param g2d
	 * 
	 * @param x
	 * 
	 * @param y
	 */

	private void drawBody(Graphics2D g2d2, int x, int y) {
		int dx, dy;
		dx = imageWidth * x;
		dy = imageHeight * y;

		g2d.drawImage(snakeBody, dx, dy, dx + imageWidth, dy + imageHeight, 0,
				0, snakeBody.getWidth(null), snakeBody.getHeight(null), null);

	}

	/**
	 * Draw snakeHead
	 * 
	 * @param g2d
	 * 
	 * @param x
	 * 
	 * @param y
	 */
	private void drawHead(Graphics2D g2d2, int x, int y) {
		int dx, dy;
		dx = imageWidth * x;
		dy = imageHeight * y;
		g2d.drawImage(snakeHead, dx, dy, dx + imageWidth, dy + imageHeight,
				frame * snakeHead.getWidth(null) / 4,
				lastMove * snakeHead.getHeight(null) / 4,
				(frame + 1) * snakeHead.getWidth(null) / 4,
				(lastMove + 1) * snakeHead.getHeight(null) / 4, null);
		

	}

	/**
	 * Draw all the Walls
	 * 
	 * @param g2d
	 * 
	 * @param x
	 * 
	 * @param y
	 */
	private void drawWall(Graphics2D g2d, int x, int y) {

		int dx, dy;
		dx = imageWidth * x;
		dy = imageHeight * y;

		g2d.drawImage(wall, dx, dy, dx + imageWidth, dy + imageHeight, 0, 0,
				wall.getWidth(null), wall.getHeight(null), null);
	}

	/**
	 * Draw food
	 * 
	 * @param g2d
	 * 
	 * @param x
	 * 
	 * @param y
	 */
	private void drawFood(Graphics2D g2d, int x, int y) {

		int dx, dy;
		dx = imageWidth * x;
		dy = imageHeight * y;

		g2d.drawImage(food, dx, dy, dx + imageWidth, dy + imageHeight, 0, 0,
				food.getWidth(null), food.getHeight(null), null);
	}

	/**
	 * Draw slow powerUp
	 * 
	 * @param g2d
	 * 
	 * @param x
	 * 
	 * @param y
	 */
	private void drawSlow(Graphics2D g2d, int x, int y) {
		int dx, dy;
		dx = imageWidth * x;
		dy = imageHeight * y;
		g2d.drawImage(slow, dx, dy, dx + imageWidth, dy + imageHeight, 0, 0,
				slow.getWidth(null), slow.getHeight(null), null);
	}

/**
 * Keyboard Inputs
 */
	private class Keyboard extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			boolean valid = false;
			if (key == keyLeft) {
				move = 2;
				valid = true;
			} else if (key == keyRight) {
				move = 3;
				valid = true;
			} else if (key == keyUp) {
				move = 1;
				valid = true;
			} else if (key == keyDown) {
				move = 0;
				valid = true;
			} else if (key == keyPause) {
				getEngine().stopTimers();
				mainmenu.showPauseMenu();
			}
			if (valid) {
				play();
				checkEnd();
			}
		}
	}

	public Engine getEngine() {

		return gameEngine;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public void setEngine(Engine load) {
		gameEngine = load;

	}

	public void setSettings(GameSettings gameSettings) {
		this.gameSettings = gameSettings;

	}

	public void setSettings(Settings gameSettings) {
		settings = gameSettings;

	}

	public int getBoardSize() {
		return boardsize;
	}

	public void setBoardSize(int dimensao) {
		this.boardsize = dimensao;
	}

	private void setKeys() {

		keyUp = settings.getKeyUp();
		keyDown = settings.getKeyDown();
		keyLeft = settings.getKeyLeft();
		keyRight = settings.getKeyRight();
		keyPause = settings.getKeyPause();
	}
}
