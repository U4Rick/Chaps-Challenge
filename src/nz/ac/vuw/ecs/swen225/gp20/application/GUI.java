package nz.ac.vuw.ecs.swen225.gp20.application;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze.Direction;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Persistence;
import nz.ac.vuw.ecs.swen225.gp20.recnreplay.Record;
import nz.ac.vuw.ecs.swen225.gp20.recnreplay.Replay;
import nz.ac.vuw.ecs.swen225.gp20.render.BoardRenderer;
import nz.ac.vuw.ecs.swen225.gp20.render.InventoryRenderer;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

/**
 * Builds the Graphic User Interface.
 *
 * @author Keely Haskett
 */
public abstract class GUI {

	JFrame window = new JFrame();

	public final Dimension counterLabelDim = new Dimension(100, 40);
	public final Dimension gamePanelDim = new Dimension(495, 495);
	public final Dimension controllerPanelDim = new Dimension(200, 500);
	public final Insets controllerPanelStandardInsets = new Insets(5, 0, 5, 0);

	public final Color mainColor =  new Color(76, 175, 80);
	public final Color textColorNormal = new Color(255, 255, 255);
	public final Color barColorNormal = new Color(139, 195, 74);
	public final Color barColorHover = new Color(76, 175, 80);

	public final Font controllerElementsFont = new Font("Calibri", Font.BOLD, 16);

	private BoardRenderer game;
	private Timer gameTimer;
	public static final int TOTAL_GAME_TIME = 60;
	public int timeLeft;
	public boolean pause = false;
	public boolean canMove;


	/**
	 *  Intializes the maze, and builds the main window.
	 */
	public GUI() {
		createMaze();
		canMove = false;
		buildWindow();
	}


	/**
	 *  Builds the window with a JMenuBar, a Renderer panel and a Controller panel.
	 */
	public void buildWindow() {

		JMenuBar menu = new JMenuBar();
		menu.setBackground(barColorNormal);
		menu.setOpaque(true);

		JMenu gameMenu = new JMenu("Game");
		setMenuDetails(gameMenu);
		JMenuItem gameStartItem = new JMenuItem("Start");
		setMenuDetails(gameStartItem);

		JMenu gameLoad = new JMenu("Load");
		setMenuDetails(gameLoad);

		JMenuItem gameLoadLevel = new JMenuItem("From Level");
		setMenuDetails(gameLoadLevel);

		JMenuItem gameLoadState = new JMenuItem("From State");
		setMenuDetails(gameLoadState);

		gameLoad.add(gameLoadLevel);
		gameLoad.add(gameLoadState);

		JMenuItem gameSaveItem = new JMenuItem("Save");
		setMenuDetails(gameSaveItem);

		gameMenu.add(gameStartItem);
		gameMenu.add(gameLoad);
		gameMenu.add(gameSaveItem);

		JMenuItem pauseMenuItem = new JMenuItem("Pause");
		setMenuDetails(pauseMenuItem);
		pauseMenuItem.setEnabled(false);

		JMenuItem quitMenuItem = new JMenuItem("Quit");
		setMenuDetails(quitMenuItem);

		JMenu replayMenu = new JMenu("Replay");
		setMenuDetails(replayMenu);

		JMenuItem replayStartItem = new JMenuItem("Start");
		setMenuDetails(replayStartItem);
		replayStartItem.setEnabled(false);

		JMenuItem replayLoadItem = new JMenuItem("Load");
		setMenuDetails(replayLoadItem);

		replayMenu.add(replayStartItem);
		replayMenu.add(replayLoadItem);

		JMenu helpMenu = new JMenu("Help");
		setMenuDetails(helpMenu);
		JMenu helpStartLoad = new JMenu("Start/Load");
		setMenuDetails(helpStartLoad);
		JMenu helpGameplay = new JMenu("Gameplay");
		setMenuDetails(helpGameplay);
		JMenu helpReplay = new JMenu("Replay");
		setMenuDetails(helpReplay);

		helpMenu.add(helpStartLoad);
		helpMenu.add(helpGameplay);
		helpMenu.add(helpReplay);

		menu.setLayout(new GridBagLayout());
		GridBagConstraints menuConstraints = new GridBagConstraints();
		menuConstraints.gridy = 0;
		menuConstraints.gridx = 0;
		menuConstraints.anchor = GridBagConstraints.LINE_START;
		menu.add(gameMenu, menuConstraints);

		menuConstraints.gridx++;
		menu.add(pauseMenuItem, menuConstraints);

		menuConstraints.gridx++;
		menu.add(quitMenuItem, menuConstraints);

		menuConstraints.gridx++;
		menuConstraints.insets = new Insets(0, 360, 0,130);
		menu.add(replayMenu, menuConstraints);

		menuConstraints.gridx++;
		menuConstraints.gridwidth = 10;
		menuConstraints.insets = new Insets(0, 0, 0,0);
		menuConstraints.anchor = GridBagConstraints.LINE_END;
		menu.add(helpMenu, menuConstraints);


		game = new BoardRenderer(getMaze(), gamePanelDim);
		window.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) { }
			@Override
			public void keyPressed(KeyEvent e) {
				if (canMove) {
					Direction direction = getDirectionFromKey(e);
					if (direction != null) {
						movePlayer(direction);
					}
				}
			}
			@Override
			public void keyReleased(KeyEvent e) { }
		});

		JPanel controller = new JPanel();
		controller.setPreferredSize(controllerPanelDim);
		controller.setBackground(mainColor);

		JLabel timeLabel = new JLabel("Time");
		setControllerElementDetails(timeLabel);

		JLabel timeCounter = new JLabel("1200");
		setControllerElementDetails(timeCounter);

		ActionListener timerListener = e -> {
			if (timeLeft >= 1) {
				timeLeft--;
				timeCounter.setText(String.valueOf(timeLeft));

			} else {
				canMove = false;
				//TODO: stop game
				//TODO: JDialog game over
				try {
					getRecord().writeToFile();
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
				//TODO: load replay into field
			}
		};

		gameTimer = new Timer(1000, timerListener);
		timeCounter.setText(String.valueOf(timeLeft));

		JLabel levelLabel = new JLabel("Level");
		setControllerElementDetails(levelLabel);

		JLabel levelCounter = new JLabel("1");
		setControllerElementDetails(levelCounter);

		JLabel keysLabel = new JLabel("Keys");
		setControllerElementDetails(keysLabel);

		JLabel keysCounter = new JLabel("4");
		setControllerElementDetails(keysCounter);

		JLabel treasuresLabel = new JLabel("Treasures Left");
		setControllerElementDetails(treasuresLabel);

		JLabel treasuresCounter = new JLabel("3");
		setControllerElementDetails(treasuresCounter);

		InventoryRenderer inventory = new InventoryRenderer(getMaze(), controllerPanelDim.width);

		controller.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridx = 0;
		constraints.gridy = 0;
		//constraints.insets = controllerPanelStandardInsets;
		controller.add(timeLabel, constraints);

		constraints.gridy++;
		controller.add(timeCounter, constraints);

		constraints.gridy++;
		controller.add(levelLabel, constraints);

		constraints.gridy++;
		controller.add(levelCounter, constraints);

		constraints.gridy++;
		controller.add(keysLabel, constraints);

		constraints.gridy++;
		controller.add(keysCounter, constraints);

		constraints.gridy++;
		controller.add(treasuresLabel, constraints);

		constraints.gridy++;
		//constraints.insets = new Insets(5, 50, 0, 50);
		controller.add(treasuresCounter, constraints);

		constraints.gridy++;
		controller.add(inventory, constraints);

		gameStartItem.addActionListener(e -> {
			timeLeft = TOTAL_GAME_TIME;
			gameTimer.start();
			canMove = true;
			setRecord(new Record());
			pauseMenuItem.setEnabled(true);
			timeCounter.setText(String.valueOf(timeLeft));
		});

		gameLoadLevel.addActionListener(e -> persistenceLoad(false));

		gameLoadState.addActionListener(e -> persistenceLoad(true));

		gameSaveItem.addActionListener(e -> persistenceSave());

		pauseMenuItem.addActionListener(e -> {
			if (pause) {
				gameTimer.start();
				pause = false;
				pauseMenuItem.setText("Pause");
				canMove = true;
				gameSaveItem.setEnabled(false);
			}
			else {
				gameTimer.stop();
				pause = true;
				pauseMenuItem.setText("Play");
				canMove = false;
				gameSaveItem.setEnabled(true);
			}
		});

		quitMenuItem.addActionListener(e -> System.exit(0));

		replayStartItem.addActionListener(e -> runReplay());

		replayLoadItem.addActionListener(e -> {
			replayLoad();
			replayStartItem.setEnabled(true);
		});

		window.setLayout(new FlowLayout());
		window.add(game);
		window.add(controller);

		window.setJMenuBar(menu);
		window.getContentPane().setBackground(mainColor);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	/**
	 *  Load the replay file and reset the replay object.
	 */
	public void replayLoad() {
		JFileChooser chooser = new JFileChooser();
		int choice = chooser.showOpenDialog(window);
		if (choice == JFileChooser.APPROVE_OPTION) {
			File toLoadFrom = chooser.getSelectedFile();
			Replay replay = new Replay(toLoadFrom);
			setReplay(replay);
		}
	}

	/**
	 * Load the level file to play from.
	 * @param isState   If true, load a pre-played and saved level.
	 *                  If false, load a fresh version of a level.
	 */
	public void persistenceLoad(boolean isState) {
		JFileChooser chooser = new JFileChooser();
		int choice = chooser.showOpenDialog(window);
		if (choice == JFileChooser.APPROVE_OPTION) {
			File toLoadFrom = chooser.getSelectedFile();
			Maze maze;
			if (isState) {
				maze = Persistence.loadGameState(toLoadFrom);
			} else {
				maze = Persistence.loadLevelFromFile(toLoadFrom);

			}
			setMaze(maze);
			game = new BoardRenderer(getMaze(), gamePanelDim);
		}
	}


	/**
	 * Save the current game state to a file
	 */
	public void persistenceSave() {
		JFileChooser chooser = new JFileChooser();
		int choice = chooser.showSaveDialog(window);
		if (choice == JFileChooser.APPROVE_OPTION) {
			File toSaveTo = chooser.getSelectedFile();
			Persistence.saveGameState(getMaze(), toSaveTo);
		}
	}

	/**
	 * Helper method to move the player and repaint the renderer.
	 * @param direction Direction to move player.
	 */
	public void movePlayer(Direction direction) {
		getMaze().moveChap(direction);
		if (getRecord() != null) { getRecord().addMove(direction); } // for tests
		game.revalidate();
		game.repaint();
	}

	/**
	 *  Runs the replay.
	 */
	public void runReplay() {
		Replay replay = getReplay();
		if (replay != null) {
			for (String move : getReplay().processActionsJson()) {
				switch (move) {
					case "DOWN":
						movePlayer(Maze.Direction.DOWN);
						break;
					case "RIGHT":
						movePlayer(Maze.Direction.RIGHT);
						break;
					case "UP":
						movePlayer(Maze.Direction.UP);
						break;
					case "LEFT":
						movePlayer(Maze.Direction.LEFT);
						break;
				}
			}
		}
		else {
			//JDialog saying no active replay
		}
	}

	/**
	 * Sets the visual details of elements in the controller.
	 * @param label JLabel to set
	 */
	private void setControllerElementDetails(JLabel label) {
		label.setPreferredSize(counterLabelDim);
		label.setFont(controllerElementsFont);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(textColorNormal);
	}

	/**
	 * Sets the visual details of elements of the JMenuBar.
	 * @param menu JComponent to set
	 */
	private void setMenuDetails (JComponent menu) {
		menu.setForeground(textColorNormal);
		menu.setBackground(barColorNormal);
		menu.setFont(controllerElementsFont);
		menu.setOpaque(true);
	}

	/**
	 * Get Direction enum value from KeyEvent.
	 * @param e KeyEvent to get direction from
	 * @return  Returns Direction if applicable, or null if not.
	 */
	public Direction getDirectionFromKey(KeyEvent e) {
		Direction direction;
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				direction = Direction.LEFT;
				break;
			case KeyEvent.VK_RIGHT:
				direction = Direction.RIGHT;
				break;
			case KeyEvent.VK_UP:
				direction = Direction.UP;
				break;
			case KeyEvent.VK_DOWN:
				direction = Direction.DOWN;
				break;
			default:
				direction = null;
		}
		return direction;
	}


	/**
	 *  Creates maze object.
	 */
	protected abstract void createMaze();

	/**
	 *  Gets the record object.
	 * @return  Replay Object
	 */
	protected abstract Record getRecord();

	/**
	 * Sets the replay object.
	 * @param record    Record to set with.
	 */
	protected abstract void setRecord(Record record);

	/**
	 * Gets replay object.
	 * @return  Replay Object
	 */
	protected abstract Replay getReplay();

	/**
	 * Sets the replay object.
	 * @param replay    Replay to set with.
	 */
	protected abstract void setReplay(Replay replay);

	/**
	 * Gets Maze.
	 * @return  Maze Object
	 */
	public abstract Maze getMaze();

	/**
	 * Sets maze to a preexisting maze object.
	 * @param maze  Maze to set with.
	 */
	protected abstract void setMaze(Maze maze);


}
