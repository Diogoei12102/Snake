package snake.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
/**
 * class where game settings (speed,size,walls) are set
 */
@SuppressWarnings("serial")
public class GameSettings extends JDialog {
	private int boardSize = 20;
	private int speed = 5;
	private final JPanel contentPanel = new JPanel();
	private JComboBox<Integer> boardSizeBox;
	private JComboBox<Integer> speedBox;
	private JPanel boardSizePanel;
	private JPanel speedPanel;
	boolean walls = true;

	/**
	 * Create the dialog.
	 */
	public GameSettings(final Menu mainmenu) {
		setLocation(mainmenu.dim.width / 2
				- this.getSize().width / 2, mainmenu.dim.height / 2
				- this.getSize().height / 2);
		setBounds(200, 200, 285, 303);
		getContentPane().setLayout(new BorderLayout(0, 0));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);

		{

			{
				{
					speedPanel = new JPanel();
					speedPanel.setVisible(true);
					boardSizePanel = new JPanel();
					boardSizePanel.setVisible(true);
					contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
					contentPanel.add(boardSizePanel);
					boardSizePanel.setLayout(new GridLayout(1, 2, 0, 0));
					{
						JLabel boardSizeLabel = new JLabel("Board size:");
						boardSizeLabel
								.setHorizontalAlignment(SwingConstants.CENTER);
						boardSizePanel.add(boardSizeLabel);
					}
					{
						boardSizeBox = new JComboBox<Integer>();
						boardSizeBox.setModel(new DefaultComboBoxModel<Integer>(new Integer []{10,20,30}));
						boardSizeBox.setMaximumRowCount(3);
						boardSizeBox.setSelectedIndex(1);
						boardSizePanel.add(boardSizeBox);
						boardSizeBox.addActionListener(new ActionListener() {
							
							public void actionPerformed(ActionEvent arg0) {
								boardSize = (int) boardSizeBox.getSelectedItem();
								
							}
						});

					}
					contentPanel.add(speedPanel);
					speedPanel.setLayout(new GridLayout(1, 2, 0, 0));
					{
						JLabel lblD = new JLabel("Speed:");
						lblD.setHorizontalAlignment(SwingConstants.CENTER);
						speedPanel.add(lblD);
					}
					{
						speedBox = new JComboBox<Integer>();
						speedBox.setModel(new DefaultComboBoxModel<Integer>(
								new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }));
						speedBox.setMaximumRowCount(10);
						speedBox.setSelectedIndex(4);
						speedPanel.add(speedBox);
						speedBox.addActionListener(new ActionListener() {

							public void actionPerformed(ActionEvent arg0) {
								speed = (int) speedBox.getSelectedItem();
							}

						});

					}
				}
				JPanel keyPanel = new JPanel();
				contentPanel.add(keyPanel);
				keyPanel.setLayout(new GridLayout(1, 2, 0, 0));

				{
					JLabel WallsLabel = new JLabel("Walls:");
					WallsLabel.setHorizontalAlignment(SwingConstants.CENTER);
					keyPanel.add(WallsLabel);
							JPanel panel = new JPanel();
					keyPanel.add(panel);
					panel.setLayout(new GridLayout(2, 1, 0, 0));
					ButtonGroup button = new ButtonGroup(); 
					{
						JRadioButton rdbtnWalls = new JRadioButton("Yes");
						button.add(rdbtnWalls);
						panel.add(rdbtnWalls);
						rdbtnWalls.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								walls = false;
								
							}
							
						});
					}
					{
						JRadioButton rdbtnNoWalls = new JRadioButton("No");
						rdbtnNoWalls.setSelected(true);
						panel.add(rdbtnNoWalls);
						button.add(rdbtnNoWalls);
						rdbtnNoWalls.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								walls = true;
								
							}
							
						});
					}

				}
			}

			{

				{
					JPanel buttonPane = new JPanel();
					buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
					getContentPane().add(buttonPane, BorderLayout.SOUTH);
					{
						JButton okButton = new JButton("OK");
						okButton.setActionCommand("OK");
						buttonPane.add(okButton);
						getRootPane().setDefaultButton(okButton);
						okButton.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								setVisible(false);
								mainmenu.getContentPane().removeAll();
								mainmenu.getContentPane().add(mainmenu.game);
								mainmenu.getContentPane().revalidate();
								mainmenu.getContentPane().repaint();
								mainmenu.game.Start();
								mainmenu.menuMusic.stop();
								mainmenu.music.play();
								mainmenu.playing = true;
							}

						});
					}
					{
						JButton cancelButton = new JButton("Cancel");
						cancelButton.setActionCommand("Cancel");
						buttonPane.add(cancelButton);
						cancelButton.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								setVisible(false);

							}

						});
					}
				}
			}

		}
	}

	

	public int getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}



	public boolean getWalls() {
		return walls;
	}

	
}
