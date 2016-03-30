/*
 * Author: Jesus Chavez
 * Connect 4
 * Adv Obj Oriented Programming
 */
package advobjectoriented.Connect4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class GUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * Gui Constants
	 */
	public final int MAXROW = 7;
	public final int MAXCOLUMN = 8;
	/*
	 * Control
	 */
	public boolean gameOver;
	public boolean hasTie;
	/*
	 * GUI Variables
	 */
	private JPanel board;
	private JPanel turn;
	private JPanel gridControl;
	private JPanel contentPane;
	private JPanel optionPanel;
	private JLabel gridtokenSet[][];
	private JLabel bluePlayerScrn, blackPlayerScrn;
	
	private JButton gridButtonSet[];
	
	private JButton forfeit, restart;
	private ImageIcon buttonIconSet[];
	
	private JMenuBar topMenuBar;
	
	private JMenu connectMenu, aboutMenu;
	private JMenuItem newGame, close, instructions, about;
	private Players player;
	private Grid game;

	// create gui
	public GUI(String p1, String p2) {
		// initialization
		gameOver = false;
		hasTie = false;

		// instances

		topMenuBar = new JMenuBar();
		connectMenu = new JMenu("Connect 4");
		aboutMenu = new JMenu("about");
		newGame = new JMenuItem("new game");
		close = new JMenuItem("close");
		instructions = new JMenuItem("instructions");
		about = new JMenuItem("about");
		optionPanel = new JPanel();
		board = new JPanel();
		gridControl = new JPanel();
		turn = new JPanel();
		contentPane = (JPanel) getContentPane();
		gridtokenSet = new JLabel[MAXROW][MAXCOLUMN];
		gridButtonSet = new JButton[MAXCOLUMN];
		buttonIconSet = new ImageIcon[MAXCOLUMN];

		// layout managers
		board.setLayout(new GridLayout(MAXROW, MAXCOLUMN));
		gridControl.setLayout(new GridLayout(1, MAXCOLUMN));
		optionPanel.setLayout(new FlowLayout());
		contentPane.setLayout(new BorderLayout());
		turn.setLayout(new FlowLayout());
		// set panels
		board.setBackground(Color.white);
		board.setVisible(false);
		player = new Players(p1, p2);
		game = new Grid(MAXROW, MAXCOLUMN);
	}
	/*
	 * Builds internal jframe compontents
	 */
	public void start() {
		buildButtons();
		buildLabels();
		buildMenu();
		buildOptionPanel();
		buildContentPane();
		setSize(800, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	/*
	 * Builds control buttons
	 */
	private void buildButtons() {
		// builds column buttons
		for (int i = 0; i < MAXCOLUMN; i++) {
			buttonIconSet[i] = new ImageIcon(
					"/Users/aexchv/Documents/cs2302/Connect 4 2.01/src/Images/Tokens.png");
			gridButtonSet[i] = new JButton(buttonIconSet[i]);
			gridButtonSet[i].setActionCommand(i + "");
			gridButtonSet[i].setOpaque(false);
			gridButtonSet[i].setContentAreaFilled(false);
			gridButtonSet[i].setBorderPainted(false);

			gridButtonSet[i].addActionListener(new ActionListener() {
				// action listener to place token
				//checks and updates board
				@Override
				public void actionPerformed(ActionEvent e) {

					int column = Integer.parseInt(e.getActionCommand());
					int row = 0;

					try {
						row = game.setToken(player.getCurrentToken(), column);
						Icon currentToken;
						if (player.getCurrentToken().equals("X")) {
							currentToken = new ImageIcon(
									"/Users/aexchv/Documents/cs2302/Connect 4 2.01/src/Images/Blue.jpg");
							gridtokenSet[row][column].setIcon(currentToken);
							blackPlayerScrn.setForeground(Color.red);
							bluePlayerScrn.setForeground(Color.BLACK);

						} else {
							currentToken = new ImageIcon(
									"/Users/aexchv/Documents/cs2302/Connect 4 2.01/src/Images/Black.jpg");
							gridtokenSet[row][column].setIcon(currentToken);
							bluePlayerScrn.setForeground(Color.red);
							blackPlayerScrn.setForeground(Color.BLACK);
						}

						if (game.hasDiagonal(row, column,
								player.getCurrentToken())) {
							JOptionPane.showMessageDialog(contentPane,
									"Winer: " + player.getCurrentPlayer(),
									"game over", 0, currentToken);
							if (player.getCurrentToken().equals("X"))
								bluePlayerScrn.setForeground(Color.green);
							else
								blackPlayerScrn.setForeground(Color.green);
							board.setVisible(false);

						} else if (game.hasTie()) {
							JOptionPane.showMessageDialog(contentPane, "TIE");
							board.setVisible(false);
						}
					} catch (MoveOutOfBoundsException Ex) {
						JOptionPane.showMessageDialog(contentPane,
								"Column Full");
					}
					player.next();
				}

			});
			gridControl.add(gridButtonSet[i]);
		}
	}
	/*
	 * adds components to content pane
	 */
	private void buildContentPane() {
		setContentPane(contentPane);
		contentPane.add(gridControl, BorderLayout.NORTH);
		contentPane.add(board, BorderLayout.CENTER);
		contentPane.add(optionPanel, BorderLayout.SOUTH);
	}
	//labels as token holders
	private void buildLabels() {
		// build labels
		for (int i = 0; i < MAXROW; i++) {
			for (int j = 0; j < MAXCOLUMN; j++) {
				// gridtokenSet[i][j] = new JLabel();
				gridtokenSet[i][j] = new JLabel();
				gridtokenSet[i][j]
						.setHorizontalAlignment(SwingConstants.CENTER);
				gridtokenSet[i][j].setBorder(new LineBorder(Color.black));
				board.add(gridtokenSet[i][j]);
			}
		}
	}
	/*
	 * Builds menu
	 */
	private void buildMenu() {
		// menu item listeners
		newGame.setActionCommand("new");
		newGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				destroyLabels();
				game.init();
				board.setVisible(true);
				if (bluePlayerScrn.getForeground().equals(Color.red))
					blackPlayerScrn.setForeground(Color.black);
				else
					bluePlayerScrn.setForeground(Color.BLACK);
			}
		});
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(ABORT);
			}
		});
		// menu bar
		connectMenu.add(newGame);
		connectMenu.add(close);
		aboutMenu.add(about);
		aboutMenu.add(instructions);
		topMenuBar.add(connectMenu);
		topMenuBar.add(aboutMenu);
		setJMenuBar(topMenuBar);
	}
	/*
	 * forfeit, restart, turn panel
	 */
	private void buildOptionPanel() {
		String[] playerList = player.getPlayers();
		bluePlayerScrn = new JLabel(
				playerList[0],
				new ImageIcon(
						"/Users/aexchv/Documents/cs2302/Connect 4 2.01/src/Images/Blue.jpg"),
				SwingConstants.LEFT);
		blackPlayerScrn = new JLabel(
				playerList[1],
				new ImageIcon(
						"/Users/aexchv/Documents/cs2302/Connect 4 2.01/src/Images/Black.jpg"),
				SwingConstants.LEFT);
		//player.next();
		// builds game option panel
		if (player.getCurrentToken().equals("X")) {
			System.out.println("X");
			bluePlayerScrn.setForeground(Color.RED);
			optionPanel.add(bluePlayerScrn);
			optionPanel.add(blackPlayerScrn);
		} else {
			System.out.println("O");
			//player.next();
			blackPlayerScrn.setForeground(Color.RED);
			optionPanel.add(bluePlayerScrn);
			optionPanel.add(blackPlayerScrn);
		}
		forfeit = new JButton("forfeit");
		restart = new JButton("restart");
		forfeit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Icon currentToken;
				if (player.getCurrentToken().equals("X")) {
					currentToken = new ImageIcon(
							"/Users/aexchv/Documents/cs2302/Connect 4 2.01/src/Images/blue.jpg");
					JOptionPane.showMessageDialog(contentPane, "Winner: "
							+ player.getCurrentPlayer(), "game over", 0,
							currentToken);
				} else {
					currentToken = new ImageIcon(
							"/Users/aexchv/Documents/cs2302/Connect 4 2.01/src/Images/Black.jpg");
					JOptionPane.showMessageDialog(contentPane, "Winner: "
							+ player.getCurrentPlayer(), "game over", 0,
							currentToken);
				}
				board.setVisible(false);
				destroyLabels();
				game.init();
			}
		});
		restart.addActionListener(new ActionListener() {
			//restarts the game
			@Override
			public void actionPerformed(ActionEvent e) {
				destroyLabels();
				board.setVisible(false);
				game.init();
			}
		});
		// build option panel
		optionPanel.add(forfeit);
		optionPanel.add(restart);
	}
	/*
	 * new game set of labels
	 */
	private void destroyLabels() {
		for (int i = 0; i < MAXROW; i++) {
			for (int j = 0; j < MAXCOLUMN; j++) {
				// gridtokenSet[i][j] = new JLabel();
				gridtokenSet[i][j].setIcon(new ImageIcon());
			}
		}
	}

	public static void main(String[] args) throws Throwable {
		String p1 = JOptionPane.showInputDialog(null,
				"enter name for player   1", "game setup");
		String p2 = JOptionPane.showInputDialog(null,
				"enter name for player   2", "game setup");
		//correct names
		//handles cancel
		//if(p1!=null&&p2!=null&&p1.length()>0&&p1.length()>0){
			JOptionPane.showMessageDialog(null, "Connect 4 > new game to start");
			GUI window = new GUI(p1, p2);
			window.start();
		//}
		//System.exit(ABORT);
		
	}
}
