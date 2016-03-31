package snake.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import snake.eng.Player;
/**
 * Panel with the Top10 Scores
 */
public class LeaderBoard extends JPanel {
	private static final long serialVersionUID = 45L;
	private JLabel text;
	private JPanel textPanel;
	Menu menu;
	private Vector<Player> records = new Vector<Player>();

	/**
	 * Initializes the panel 
	 * @param menu MainMenu
	 */
	LeaderBoard(Menu menu) throws FontFormatException, IOException {
		this.menu = menu;
		text = new JLabel();

		textPanel = new JPanel();
		textPanel.setBounds(0, 140, 1024, 500);
		textPanel.setLayout(new BorderLayout(0, 0));
		textPanel.setOpaque(false);
		this.add(textPanel);
		text.setHorizontalAlignment(SwingConstants.CENTER);
		text.setVerticalAlignment(SwingConstants.CENTER);
		text.setFont(this.menu.font.deriveFont(25.0f));
		text.setBounds(0, 0, 1024, 640);
		text.setForeground(Color.black);
		textPanel.add(text, BorderLayout.CENTER);

	}

	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		ImageIcon button = null;
		Image img;
		textPanel.repaint();
		button = new ImageIcon(this.getClass().getResource(
				"items/leaderboard.jpg"));
		img = button.getImage();
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
	}

	public void addPlayer(Player player) {
		getRecords().add(player);
	}

	/**
	 * actualizes the top 10 on the textPanel
	 */
	public void showRecords() throws FontFormatException, IOException {

		String all = "";
		if (getRecords() != null) {
			Collections.sort(getRecords());
			int number = 1;
			for (int i = 0; i < getRecords().size() && i < 10; i++) {

				all += "<html><p>" + number + " - "
						+ getRecords().elementAt(i).getName() + " - "
						+ getRecords().elementAt(i).getScore() + "</p>";
				number++;
			}
		}

		text.setText(all);
	}

/**
 * get the vector with the records
 */
	public Vector<Player> getRecords() {
		return records;
	}
/**
 * set the vector with the top10
 */
	public void setRecords(Vector<Player> records) {
		if (records != null)
			this.records = records;
	}
}
