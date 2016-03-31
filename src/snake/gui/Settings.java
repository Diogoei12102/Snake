package snake.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
/**
 * Class where Screen and Key settings are set
 */
@SuppressWarnings("serial")
public class Settings extends JDialog {
	private int newKey;
	private int keyUp = KeyEvent.VK_UP;
	private int keyDown = KeyEvent.VK_DOWN;
	private int keyLeft = KeyEvent.VK_LEFT;
	private int keyRight = KeyEvent.VK_RIGHT;
	private int keyPause = KeyEvent.VK_ESCAPE;
	private int tempKeyUp = KeyEvent.VK_UP;
	private int tempKeyDown = KeyEvent.VK_DOWN;
	private int tempKeyLeft = KeyEvent.VK_LEFT;
	private int tempKeyRight = KeyEvent.VK_RIGHT;
	private int tempKeyPause = KeyEvent.VK_ESCAPE;
	private int screenSize[] = { 1024, 640 };
	private final JPanel contentPanel = new JPanel();
	private JComboBox<String> comboBox;
	private JPanel screenSizePanel;
	private boolean fullscreen;
	/**
	 * Create the dialog.
	 * @param mainmenu InitialMenu 
	 */
	public Settings(final Menu mainmenu) {
		
		setBounds(200, 200, 285, 303);
		getContentPane().setLayout(new BorderLayout(0, 0));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);

		{

			{
				{
					screenSizePanel = new JPanel();
					screenSizePanel.setVisible(true);
					contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
					contentPanel.add(screenSizePanel);
					screenSizePanel.setLayout(new GridLayout(1, 2, 0, 0));
					{
						JLabel lblScreenSize = new JLabel("Screen Size:");
						lblScreenSize
								.setHorizontalAlignment(SwingConstants.CENTER);
						screenSizePanel.add(lblScreenSize);
					}
					{
						comboBox = new JComboBox<String>();
						comboBox.setModel(new DefaultComboBoxModel<String>(
								new String[] { "800x500", "1024x640",
										"FullScreen" }));
						comboBox.setMaximumRowCount(3);
						screenSizePanel.add(comboBox);
						comboBox.addActionListener(new ActionListener() {

						

							public void actionPerformed(ActionEvent arg0) {

								if (comboBox.getSelectedItem()
										.equals("800x500")) {

									screenSize[0] = 800;
									screenSize[1] = 500;
									fullscreen = false;
								}

								else if (comboBox.getSelectedItem().equals(
										"1024x640")) {
									screenSize[0] = 1024;
									screenSize[1] = 640;
									fullscreen = false;
								} else if (comboBox.getSelectedItem().equals(
										"FullScreen")) {
									fullscreen = true;
									
								}
							}
						});

					}
				}
				JPanel keyPanel = new JPanel();
				contentPanel.add(keyPanel);
				keyPanel.setLayout(new GridLayout(1, 2, 0, 0));

				{
					JLabel keyLabel = new JLabel("Keys:");
					keyLabel.setHorizontalAlignment(SwingConstants.CENTER);
					keyPanel.add(keyLabel);
				}
				{
					JPanel panel = new JPanel();
					keyPanel.add(panel);
					panel.setLayout(new GridLayout(5, 1, 0, 0));
					{
						JButton btnUp = new JButton("UP");
						btnUp.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								tempKeyUp = keySelectorDialog();
							}
						});
						panel.add(btnUp);
					}
					{
						JButton btnDown = new JButton("DOWN");
						btnDown.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								tempKeyDown = keySelectorDialog();
							}
						});
						panel.add(btnDown);
					}
					{
						JButton btnLeft = new JButton("LEFT");
						btnLeft.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								tempKeyLeft = keySelectorDialog();
							}
						});
						panel.add(btnLeft);
					}
					{
						JButton btnRight = new JButton("RIGHT");
						btnRight.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								tempKeyRight = keySelectorDialog();
							}
						});
						panel.add(btnRight);
					}
					{
						JButton btnPause = new JButton("PAUSE");
						btnPause.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								tempKeyPause = keySelectorDialog();
							}
						});
						panel.add(btnPause);
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
								setKeyUp(tempKeyUp);
								setKeyDown(tempKeyDown);
								setKeyLeft(tempKeyLeft);
								setKeyRight(tempKeyRight);
								setKeyPause(tempKeyPause);
								mainmenu.setScreenSize(screenSize , fullscreen);
								setVisible(false);
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

	private int keySelectorDialog() {

		JDialog dialog = new JDialog();
		dialog.setModal(true);
		dialog.setResizable(false);
		dialog.setSize(200, 100);
		JLabel label = new JLabel("Press a key.");
		dialog.setLocationRelativeTo(getContentPane());
		label.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				newKey = e.getKeyCode();
				SwingUtilities.getWindowAncestor(e.getComponent()).dispose();
			}
		});
		label.setFocusable(true);
		dialog.getContentPane().add(label);
		dialog.setVisible(true);

		return newKey;
	}


	public int getKeyUp() {
		return keyUp;
	}

	public void setKeyUp(int keyUp) {
		this.keyUp = keyUp;
	}

	public int getKeyDown() {
		return keyDown;
	}

	public void setKeyDown(int keyDown) {
		this.keyDown = keyDown;
	}

	public int getKeyLeft() {
		return keyLeft;
	}

	public void setKeyLeft(int keyLeft) {
		this.keyLeft = keyLeft;
	}

	public int getKeyRight() {
		return keyRight;
	}

	public void setKeyRight(int keyRight) {
		this.keyRight = keyRight;
	}

	public int getKeyPause() {
		return keyPause;
	}

	public void setKeyPause(int keyPause) {
		this.keyPause = keyPause;
	}
}
