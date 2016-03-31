package snake.gui;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.JDialog;








import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

import snake.eng.Engine;
import snake.eng.Player;

/**
 * Class where all the saves and load are done
 */

public class SaveGame extends JDialog {
	Engine gameEngine;
	Vector<Player> leaderBoard;
	Menu main ;
	boolean exit;
	private String saveName;
	private static final long serialVersionUID = 1L;
	private JTextField saveNameField;
	
	public void setExit(boolean exit) {
		this.exit = exit;
	}
	/**
	 * cria menu de introduçao do nome para gravar
	 */
	public SaveGame(Menu mainMenu) throws IOException {
		main = mainMenu;
		getContentPane().setLayout(new GridLayout(3, 1, 0, 0));
		
		JLabel lblSaveName = new JLabel("Write save name and press Enter :");
		getContentPane().add(lblSaveName);
		
		saveNameField = new JTextField();
		getContentPane().add(saveNameField);
		saveNameField.setColumns(10);
		saveNameField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveName = saveNameField.getText();
				setVisible(false);
				
					try {
						save(saveName);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
					
			}

		});
		
			

		setSize( 300, 100);
	}
	/**
	 * grava o jogo com o nome introduzido pelo utilizador
	 * 
	 *  @param nome nome do save 
	 */
	private void save( String nome) throws IOException {
		ObjectOutputStream os = null;
		gameEngine.setGameGraph(null);
		try {
			os = new ObjectOutputStream(new FileOutputStream(nome+".dat"));
			os.writeObject(gameEngine);
		} catch (IOException e) {
		} finally {
			if (os != null)
				os.close();
		}

	}
	/**
	 * faz load do jogo que foi escolhido pelo utilizador
	 * 
	 */
	public Engine load(String nome) throws IOException, ClassNotFoundException{
	 ObjectInputStream is = null;
	 try {
	 is = new ObjectInputStream(
	 new FileInputStream(nome));
	  gameEngine = (Engine) is.readObject();
	 }
	 catch (IOException e) { }
	 finally { if (is != null) is.close(); }
	return gameEngine;
	}
	/**
	 * substitui o motor de jogo
	 * 
	 *  @param gameEngine
	 */
	public void setMotorJogo(Engine gameEngine) {
		this.gameEngine = gameEngine;
		
	}
	/**
	 * devolve o motor de jogo 
	 *
	 */
	public Engine getMotorJogo() {
		
		return this.gameEngine;
	}
	/**
	 * load LeaderBoard
	 */
	@SuppressWarnings("unchecked")
	public Vector<Player> loadLeaderBoard() throws ClassNotFoundException, IOException{
		ObjectInputStream is = null;
		leaderBoard = null;
		 try {
		 is = new ObjectInputStream(
		 new FileInputStream("LeaderBoard.dat"));
		 leaderBoard  = (Vector<Player>) is.readObject();
		 }
		 catch (IOException e) { }
		 finally { if (is != null) is.close(); }
		return leaderBoard;}
	/**
	 * saves LeaderBoard
	 * @param name
	 */
	public void saveLeaderBoard(LeaderBoard name) throws IOException{
		ObjectOutputStream os = null;
		try {
			os = new ObjectOutputStream(new FileOutputStream("LeaderBoard.dat"));
			os.writeObject(name.getRecords());
		} catch (IOException e) {
		} finally {
			if (os != null)
				os.close();
		}
		
}
	}
