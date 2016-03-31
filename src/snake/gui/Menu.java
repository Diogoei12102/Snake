package snake.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import snake.eng.Player;
/**
 * class where all the graphic menus are created and shown
 */
@SuppressWarnings("serial")
public class Menu extends JFrame implements MouseListener {

	private JTextField nameField;
	SoundPlayer overSound;
	SoundPlayer clickSound;
	private JPanel contentPane;
	private CustomPanel menuPanel;
	private CustomPanel startButton;
	private CustomPanel leaderBoardButton;
	private CustomPanel settingsButton;
	private LeaderBoard leaderBoardPanel;
	private CustomPanel saveButton;
	private CustomPanel cancelButton;
	private CustomPanel resumeButton;
	private CustomPanel newGameButton;
	private CustomPanel loadButton;
	private CustomPanel nameMenuPanel;
	CustomPanel exitButton;
	boolean playing;
	SoundPlayer menuMusic;
	SoundPlayer music;
	private String savename;
	Settings settings ;
	GameSettings gameSettings ;
	GameGraphics game;
	SaveGame save;
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	Font font = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Menu() throws FontFormatException, IOException {
		setTitle("Snake");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setUndecorated(true);
		setBounds(100, 100, 1024, 640);
		contentPane = new JPanel();
		setContentPane(contentPane);

		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2
				- getSize().height / 2);
		contentPane.setLayout(null);
		addFont();
		try {
			save = new SaveGame(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		// loadSounds
		menuMusic = new SoundPlayer(Menu.class.getResource("items/menuMusic.wav"));
		overSound = new SoundPlayer(Menu.class.getResource("items/over.wav"));
		clickSound = new SoundPlayer(Menu.class.getResource("items/click.wav"));
		initializeSettingMenus();
		initializeButtons();
		initializePanels();
		getContentPane().add(exitButton);
		getContentPane().add(startButton);
		getContentPane().add(leaderBoardButton);
		getContentPane().add(settingsButton);
		getContentPane().add(menuPanel);
		

		game = new GameGraphics(this);
		// loadLeaderBoard
		loadLeaderBoard();
		menuMusic.play();
		getContentPane().revalidate();
		getContentPane().repaint();
	}
/**
 * Initialize all the settings menus (Settings and GameSettings)
 */
	private void initializeSettingMenus() {
		settings = new Settings(this);
		settings.setLocation(dim.width / 2
				- settings.getSize().width / 2, dim.height / 2
				- settings.getSize().height / 2);
		gameSettings = new GameSettings(this);
		gameSettings.setLocation(dim.width / 2
				- gameSettings.getSize().width / 2, dim.height / 2
				- gameSettings.getSize().height / 2);
	}
/**
 * Initializes the panels that are used as buttons
 */
	private void initializeButtons() {
		// Buttons
		// exitButton
		exitButton = new CustomPanel("items/exit0.png");
		exitButton.setSize(70, 30);
		exitButton.setLocation(getSize().width - 70, 0);
		exitButton.setFocusable(true);
		exitButton.addMouseListener(this);

		// startButton
		startButton = new CustomPanel("items/start_small.png");
		startButton.setSize(600, 50);
		startButton.setLocation(getSize().width / 2 - 300, 370);
		startButton.setFocusable(true);
		startButton.addMouseListener(this);

		// leaderBoardButton
		leaderBoardButton = new CustomPanel("items/leaderboard_small.png");
		leaderBoardButton.setSize(600, 50);
		leaderBoardButton.setLocation(getSize().width / 2 - 300, 430);
		leaderBoardButton.setFocusable(true);
		leaderBoardButton.addMouseListener(this);

		// settingsButton
		settingsButton = new CustomPanel("items/settings_small.png");
		settingsButton.setSize(600, 50);
		settingsButton.setLocation(getSize().width / 2 - 300, 490);
		settingsButton.setFocusable(true);
		settingsButton.addMouseListener(this);

		// Resume Button
		resumeButton = new CustomPanel("items/resume_small.png");
		resumeButton.setSize(600, 50);
		resumeButton.setLocation(getSize().width / 2 - 300, 370);
		resumeButton.setFocusable(true);
		resumeButton.addMouseListener(this);

		// saveButton
		saveButton = new CustomPanel("items/save_small.png");
		saveButton.setSize(600, 50);
		saveButton.setLocation(getSize().width / 2 - 300, 430);
		saveButton.setFocusable(true);
		saveButton.addMouseListener(this);

		// cancel Button
		cancelButton = new CustomPanel("items/back_small.png");
		cancelButton.setSize(600, 50);
		cancelButton.setLocation(getSize().width / 2 - 300, 490);
		cancelButton.setFocusable(true);
		cancelButton.addMouseListener(this);

		// newGameButton
		newGameButton = new CustomPanel("items/newgame_small.png");
		newGameButton.setSize(600, 50);
		newGameButton.setLocation(getSize().width / 2 - 300, 370);
		newGameButton.setFocusable(true);
		newGameButton.addMouseListener(this);

		// loadButton
		loadButton = new CustomPanel("items/load_small.png");
		loadButton.setSize(600, 50);
		loadButton.setLocation(getSize().width / 2 - 300, 430);
		loadButton.setFocusable(true);
		loadButton.addMouseListener(this);

	}

/**
 * Initializes all the panels 
 */
	private void initializePanels() throws FontFormatException, IOException {
		menuPanel = new CustomPanel("items/snakemenu.jpg");
		menuPanel.setBounds(0, 0, 1024, 640);
		// LeaderBoard
		leaderBoardPanel = new LeaderBoard(this);
		leaderBoardPanel.setSize(1024, 640);
		leaderBoardPanel.setBounds(0, 0, 1024, 640);
		leaderBoardPanel.setFocusable(true);
		leaderBoardPanel.addMouseListener(this);
		leaderBoardPanel.setLayout(null);
		// insert Name menu
		nameMenuPanel = new CustomPanel("items/namemenu.jpg");
		nameMenuPanel.setBounds(0, 0, 1024, 640);
		nameField = new JTextField();
		nameField.setSize(600, 60);
		nameField.setLocation(getSize().width / 2 - 300, 400);
		nameField.setOpaque(true);
		nameField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Player player = new Player(nameField.getText(), (int) game
						.getEngine().getScore());
				leaderBoardPanel.addPlayer(player);
				showMainMenu();
				nameField.setText("");

			}
		});

		nameField.setFont(font.deriveFont(25.0f));
		nameField.setHorizontalAlignment(SwingConstants.CENTER);
		nameField.setForeground(Color.black);

	}
/**
 * adds the font to the local Graphics environment
 */
	private void addFont() {
		try {
			font = Font.createFont(Font.TRUETYPE_FONT,
					Menu.class.getResourceAsStream("items/8bitfont.TTF"));
			GraphicsEnvironment.getLocalGraphicsEnvironment()
					.registerFont(font);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
/**
 * loads the saved Leaderboard
 */
	private void loadLeaderBoard() throws IOException {
		try {
			leaderBoardPanel.setRecords(save.loadLeaderBoard());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {
		overSound.playSoundOnce();
		JPanel temp = (JPanel) e.getSource();
		if (temp == startButton) {
			startButton.setImage("items/start_big.png");
		} else if (temp == leaderBoardButton) {

			leaderBoardButton.setImage("items/leaderboard_big.png");
		} else if (temp == settingsButton) {
			settingsButton.setImage("items/settings_big.png");
		} else if (temp == exitButton) {
			exitButton.setImage("items/exit1.png");
		} else if (temp == resumeButton) {
			resumeButton.setImage("items/resume_big.png");
		} else if (temp == saveButton) {
			saveButton.setImage("items/save_big.png");
		} else if (temp == cancelButton) {
			cancelButton.setImage("items/back_big.png");

		} else if (temp == newGameButton) {
			newGameButton.setImage("items/newgame_big.png");

		} else if (temp == loadButton) {
			loadButton.setImage("items/load_big.png");
		}

		getContentPane().revalidate();
		getContentPane().repaint();

	}

	public void mouseExited(MouseEvent e) {
		JPanel temp = (JPanel) e.getSource();
		if (temp == startButton) {
			startButton.setImage("items/start_small.png");

		} else if (temp == leaderBoardButton) {
			leaderBoardButton.setImage("items/leaderboard_small.png");
		} else if (temp == settingsButton) {
			settingsButton.setImage("items/settings_small.png");
		} else if (temp == exitButton) {
			exitButton.setImage("items/exit0.png");
		} else if (temp == resumeButton) {
			resumeButton.setImage("items/resume_small.png");
		} else if (temp == saveButton) {
			saveButton.setImage("items/save_small.png");
		} else if (temp == cancelButton) {
			cancelButton.setImage("items/back_small.png");
		} else if (temp == newGameButton) {
			newGameButton.setImage("items/newgame_small.png");
		} else if (temp == loadButton) {
			loadButton.setImage("items/load_small.png");
		}
		getContentPane().revalidate();
		getContentPane().repaint();
	}

	public void mousePressed(MouseEvent e) {
		clickSound.playSoundOnce();
		JPanel temp = (JPanel) e.getSource();
		if (temp == startButton) {
			showStartMenu();
		} else if (temp == leaderBoardButton) {
			try {
				showLeaderBoard();
				leaderBoardButton.setImage("items/leaderboard_small.png");
			} catch (FontFormatException e1) {
				
				e1.printStackTrace();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}

		} else if (temp == resumeButton) {
			getContentPane().removeAll();
			getContentPane().add(game);
			resumeButton.setImage("items/resume_small.png");
			getContentPane().revalidate();
			getContentPane().repaint();
			game.requestFocus();
		} else if (temp == saveButton) {
			saveButton.setImage("items/save_small.png");
			save.setMotorJogo(game.getEngine());
			save.setLocation(dim.width / 2
					- save.getSize().width / 2, dim.height / 2
					- save.getSize().height / 2);
			save.setVisible(true);
		} else if (temp == cancelButton) {
			cancelButton.setImage("items/back_small.png");
			game.getTimer().stop();
			showMainMenu();
			playing = false;

		} else if (temp == leaderBoardPanel) {
			showMainMenu();

		} else if (temp == settingsButton) {
			settingsButton.setImage("items/settings_small.png");
			settings.setVisible(true);
		} else if (temp == newGameButton) {
			newGameButton.setImage("items/newgame_small.png");
			gameSettings.setVisible(true);
			actualizeGame();
			
		} else if (temp == loadButton) {
			loadButton.setImage("items/load_small.png");
			final JFileChooser fileChooser = new JFileChooser(Menu.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"DAT File", "dat" );
			fileChooser.setFileFilter(filter);
			
			int temp2 = fileChooser.showOpenDialog(null);
			if (temp2 == JFileChooser.APPROVE_OPTION) {
				savename = fileChooser.getSelectedFile().getAbsolutePath();
				try {
					save.load(savename);
				} catch (ClassNotFoundException | IOException e1) {
					e1.printStackTrace();
				}

				game = new GameGraphics(this);
				getContentPane().removeAll();
				getContentPane().add(game);
				game.startLoadedGame(save.getMotorJogo());
				game.requestFocus();
				getContentPane().revalidate();
				getContentPane().repaint();

			}
		} else if (temp == exitButton) {
			if (playing) {
				if ((JOptionPane.showConfirmDialog(rootPane, "Are you sure?",
						"Play", JOptionPane.YES_NO_OPTION)) == JOptionPane.YES_OPTION) {
					System.exit(0);

				}
			} else {
				try {
					save.saveLeaderBoard(leaderBoardPanel);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(0);
			}
		}

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	
/**
 * shows the main/initial Menu
 */
	public void showMainMenu() {
		// music.stop();
		getContentPane().removeAll();
		getContentPane().add(exitButton);
		getContentPane().add(startButton);
		getContentPane().add(leaderBoardButton);
		getContentPane().add(settingsButton);
		getContentPane().add(menuPanel);
		getContentPane().revalidate();
		getContentPane().repaint();
		// menumusic.play();

	}

	/**
	 * shows the game Selection menu
	 */
	public void showStartMenu() {
		getContentPane().removeAll();
		getContentPane().add(exitButton);
		getContentPane().add(newGameButton);
		getContentPane().add(loadButton);
		getContentPane().add(cancelButton);
		getContentPane().add(menuPanel);
		getContentPane().revalidate();
		getContentPane().repaint();
	}
/**
 * shows the Pause Menu
 */
	public void showPauseMenu() {
		getContentPane().removeAll();
		getContentPane().add(exitButton);
		getContentPane().add(resumeButton);
		getContentPane().add(saveButton);
		getContentPane().add(cancelButton);
		getContentPane().add(menuPanel);
		getContentPane().revalidate();
		getContentPane().repaint();
	}

/**
 * shows the menu where the player enter his/her name
 */
	public void showNameMenu() {
		getContentPane().removeAll();
		getContentPane().add(nameMenuPanel);
		getContentPane().add(nameField);
		nameField.requestFocus();
		getContentPane().revalidate();
		getContentPane().repaint();

	}
/**
 * shows the leaderBoard
 */
	public void showLeaderBoard() throws FontFormatException, IOException {
		getContentPane().removeAll();
		getContentPane().add(exitButton);
		getContentPane().add(leaderBoardPanel);
		leaderBoardPanel.showRecords();
		getContentPane().revalidate();
		getContentPane().repaint();
	}
/**
 * sets the screenSize
 * @param screenSize
 * @param fullscreen
 */
	public void setScreenSize(int[] screenSize, boolean fullscreen) {
		int i = 0;
		if (!fullscreen) {
			setSize(screenSize[0], screenSize[1]);
			setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2
					- getSize().height / 2);
			menuPanel.setSize(screenSize[0], screenSize[1]);
			leaderBoardPanel.setSize(screenSize[0], screenSize[1]);
			nameMenuPanel.setSize(screenSize[0], screenSize[1]);
			game.setSize(screenSize[0], screenSize[1]);
			if (screenSize[0] < 1024) {
				i = 100;
				nameField.setLocation(getSize().width / 2 - 300, 320);
			} else
				nameField.setLocation(getSize().width / 2 - 300, 400);
			newGameButton.setLocation(getSize().width / 2 - 300, 370 - i);
			loadButton.setLocation(getSize().width / 2 - 300, 430 - i);
			cancelButton.setLocation(getSize().width / 2 - 300, 490 - i);
			saveButton.setLocation(getSize().width / 2 - 300, 430 - i);
			resumeButton.setLocation(getSize().width / 2 - 300, 370 - i);
			settingsButton.setLocation(getSize().width / 2 - 300, 490 - i);
			leaderBoardButton.setLocation(getSize().width / 2 - 300, 430 - i);
			startButton.setLocation(getSize().width / 2 - 300, 370 - i);
			exitButton.setLocation(getSize().width - 70, 0);

		} else {
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			setSize(dim);
			setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2
					- getSize().height / 2);
			menuPanel.setSize(dim);
			leaderBoardPanel.setSize(dim);
			nameMenuPanel.setSize(screenSize[0], screenSize[1]);
			game.setSize(dim);
			newGameButton.setLocation(getSize().width / 2 - 300, 370);
			loadButton.setLocation(getSize().width / 2 - 300, 430);
			cancelButton.setLocation(getSize().width / 2 - 300, 490);
			saveButton.setLocation(getSize().width / 2 - 300, 430);
			resumeButton.setLocation(getSize().width / 2 - 300, 370);
			settingsButton.setLocation(getSize().width / 2 - 300, 490);
			leaderBoardButton.setLocation(getSize().width / 2 - 300, 430);
			startButton.setLocation(getSize().width / 2 - 300, 370);
			exitButton.setLocation(getSize().width - 70, 0);
			nameMenuPanel.setSize(dim);
			nameField.setLocation(getSize().width / 2 - 300, 490);
		}
	}

	public GameSettings getGameSettings() {
		
		return gameSettings;
	}
	Settings getSettings() {

		return settings;
	}

	private void actualizeGame() {
		game.setSettings(settings);
		game.setSettings(gameSettings);

	}

	public void setPlaying(boolean b) {
		playing = b;

	}

}
