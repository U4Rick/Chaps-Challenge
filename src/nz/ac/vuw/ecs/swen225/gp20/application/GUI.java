package nz.ac.vuw.ecs.swen225.gp20.application;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.commons.Direction;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Levels;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Persistence;
import nz.ac.vuw.ecs.swen225.gp20.recnreplay.Record;
import nz.ac.vuw.ecs.swen225.gp20.recnreplay.Replay;
import nz.ac.vuw.ecs.swen225.gp20.render.BoardRenderer;
import nz.ac.vuw.ecs.swen225.gp20.render.InventoryRenderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.*;

/**
 * Builds the Graphic User Interface.
 *
 * @author Keely Haskett
 */
public abstract class GUI {

	private JFrame window = new JFrame();
	private JPanel controller;


	public final Dimension counterLabelDim = new Dimension(100, 40);
	public final Dimension gamePanelDim = new Dimension(495, 495);
	public final Dimension controllerPanelDim = new Dimension(200, 500);
	public final Insets controllerPanelStandardInsets = new Insets(5, 0, 5, 0);

	public final Color mainColor =  new Color(76, 175, 80);
	public final Color textColorNormal = new Color(255, 255, 255);
	public final Color barColorNormal = new Color(139, 195, 74);

	public final Font controllerElementsFont = new Font("Calibri", Font.BOLD, 16);

	public static final int MAX_LEVEL = 2;

	//Swing elements that need to be accessed for updates
	private JLabel treasuresCounter;
	private JLabel keysCounter;
	private JLabel levelCounter;
	private JLabel timeCounter;
	private JMenuItem pauseMenuItem;
	private JMenuItem gameSaveItem;
	private JMenuItem replayStartItem;

	private BoardRenderer game;
	private InventoryRenderer inventory;
	private Timer gameTimer;
	private Timer replayTimer;
	public int timeLeft;
	public boolean isPause = false;
	public boolean canMove;

	/**
	 *  Initializes the maze, and builds the main window.
	 */
	public GUI() {
		createMaze();
		canMove = false;
		buildWindow();
		repaintAll();
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

		gameSaveItem = new JMenuItem("Save");
		setMenuDetails(gameSaveItem);

		gameMenu.add(gameStartItem);
		gameMenu.add(gameLoad);
		gameMenu.add(gameSaveItem);

		pauseMenuItem = new JMenuItem("Pause");
		setMenuDetails(pauseMenuItem);
		pauseMenuItem.setEnabled(false);

		JMenuItem quitMenuItem = new JMenuItem("Quit");
		setMenuDetails(quitMenuItem);

		JMenu replayMenu = new JMenu("Replay");
		setMenuDetails(replayMenu);

		replayStartItem = new JMenuItem("Start");
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
			public void keyPressed(KeyEvent e) { processKeyEvent(e); }
			@Override
			public void keyReleased(KeyEvent e) { }
		});

		controller = new JPanel();
		controller.setPreferredSize(controllerPanelDim);
		controller.setBackground(mainColor);

		JLabel timeLabel = new JLabel("Time");
		setControllerElementDetails(timeLabel);

		timeCounter = new JLabel();
		setControllerElementDetails(timeCounter);

		ActionListener timerListener = e -> onGameTimeTick();

		gameTimer = new Timer(1000, timerListener);

		JLabel levelLabel = new JLabel("Level");
		setControllerElementDetails(levelLabel);

		levelCounter = new JLabel();
		setControllerElementDetails(levelCounter);

		JLabel keysLabel = new JLabel("Keys");
		setControllerElementDetails(keysLabel);

		keysCounter = new JLabel();
		setControllerElementDetails(keysCounter);

		JLabel treasuresLabel = new JLabel("Treasures Left");
		setControllerElementDetails(treasuresLabel);

		treasuresCounter = new JLabel();
		setControllerElementDetails(treasuresCounter);

		inventory = new InventoryRenderer(getMaze(), controllerPanelDim.width-20);

		controller.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = controllerPanelStandardInsets;
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
		controller.add(treasuresCounter, constraints);

		constraints.gridy++;
		controller.add(inventory, constraints);

		gameStartItem.addActionListener(e -> gameStart());

		gameLoadLevel.addActionListener(e -> persistenceLoad(false));

		gameLoadState.addActionListener(e -> persistenceLoad(true));

		gameSaveItem.addActionListener(e -> persistenceSave());

		pauseMenuItem.addActionListener(e -> togglePause());

		quitMenuItem.addActionListener(e -> System.exit(0));

		replayStartItem.addActionListener(e -> runReplay());

		replayLoadItem.addActionListener(e -> replayLoad());

		window.setLayout(new FlowLayout());
		window.add(game);
		window.add(controller);

		window.setTitle("Chap's Challenge");
		window.setJMenuBar(menu);
		window.getContentPane().setBackground(mainColor);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	/**
	 * Actions to be processed on every gameTimer tick.
	 * Does actions for game end.
	 */
	public void onGameTimeTick() {
		timeLeft--;
		getMaze().setTimeLeft(timeLeft);
		repaintAll();
		if (timeLeft <= 0) { gameStop("Game Over!", "You ran out of time!"); }
	}


	/**
	 * Stops the game running, with a JDialog containing custom messages.
	 * Saves and loads replay of game just played.
	 * @param dialogMessage Message to be on JDialog.
	 * @param dialogTitle   Title of the JDialog.
	 */
	public void gameStop(String dialogMessage, String dialogTitle) {
		canMove = false;
		gameTimer.stop();

		produceDialog(dialogMessage, dialogTitle);

		JFileChooser chooser = new JFileChooser();
		int replayChoice = chooser.showSaveDialog(window);
		if (replayChoice == JFileChooser.APPROVE_OPTION) {
			File replayFile = chooser.getSelectedFile();
			getRecord().writeToFile(replayFile);
			replayLoad(replayFile);
		}
	}

	/**
	 * Create a new JDialog and handle closing it.
	 * @param message   Message of the dialog.
	 * @param title Title of the dialog.
	 */
	public void produceDialog(String message, String title) {
		JOptionPane option = new JOptionPane(JOptionPane.DEFAULT_OPTION);
		option.setMessage(message);
		JDialog dialog = option.createDialog(title);
		dialog.pack();
		dialog.setVisible(true);
		int choice = (Integer) option.getValue();
		if (choice == JOptionPane.OK_OPTION) {
			dialog.setVisible(false);
		}
	}

	/**
	 * Process any KeyEvent encountered.
	 * @param e KeyEvent to process
	 */
	public void processKeyEvent(KeyEvent e) {
		if (e.isControlDown()) {
			switch (e.getKeyCode()) {
				//exit the game, the current game state will be lost, the next time the game is started, it will resume from the last unfinished level
				case KeyEvent.VK_X:
					System.out.println("x");
					break;


				//exit the game, saves the game state, game will resume next time the application will be started
				case KeyEvent.VK_S:
					System.out.println("s");
					break;


				//resume a saved game
				case KeyEvent.VK_R:
					System.out.println("r");
					break;


				//start a new game at the last unfinished level
				case KeyEvent.VK_P:
					System.out.println("p");
					break;


				//start a new game at level 1
				case KeyEvent.VK_1:
					System.out.println("1");
					break;


			}
		} else {
			switch (e.getKeyCode()) {
				//pause the game
				case KeyEvent.VK_ESCAPE:
					togglePause(false);
					break;

				//resume the game
				case KeyEvent.VK_SPACE:
					togglePause(true);
					break;

				//if not anything else, assume user is trying to move chap
				default:
					checkMove(e);
					break;
			}
		}
	}

	/**
	 * Process KeyEvent determined to be a move, checking if it could be a valid move,
	 * and call associated methods if true.
	 * @param e KeyEvent that occurred.
	 */
	public void checkMove(KeyEvent e) {
		if (canMove) {
			Direction direction = getDirectionFromKey(e);
			if (direction != null) {
				movePlayer(direction);
			}
		}
	}

	/**
	 * Start the game process.
	 */
	public void gameStart() {
		timeLeft = getMaze().getTimeAvailable();
		gameTimer.start();
		canMove = true;
		setRecord(new Record());
		pauseMenuItem.setEnabled(true);
		timeCounter.setText(String.valueOf(timeLeft));
	}

	/**
	 * Perform necessary actions on pause/play of game.
	 */
	public void togglePause() {
		if (isPause) {
			play();
		}
		else {
			pause();
		}
	}

	/**
	 * Perform necessary actions on pause/play of game, with a predefined game state.
	 */
	public void togglePause(boolean toggle) {
		if (!toggle) {
			play();
		}
		else {
			pause();
		}
	}

	public void pause() {
		gameTimer.stop();
		isPause = true;
		pauseMenuItem.setText("Play");
		canMove = false;
		gameSaveItem.setEnabled(true);
	}

	public void play() {

		gameTimer.start();
		isPause = false;
		pauseMenuItem.setText("Pause");
		canMove = true;
		gameSaveItem.setEnabled(false);
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
			replay.loadFile(toLoadFrom);
			setReplay(replay);
		}
		replayStartItem.setEnabled(true);
	}

	/**
	 * Load the replay from a specified file.
	 * @param file  File to load from.
	 */
	public void replayLoad(File file) {
		Replay replay = new Replay(file);
		replay.loadFile(file);
		setReplay(replay);
		replayStartItem.setEnabled(true);
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
				maze = Levels.loadLevelFromFile(toLoadFrom);

			}
			if (maze != null) {
				setMaze(maze);
				window.remove(game);
				game = new BoardRenderer(getMaze(), gamePanelDim);
				window.add(game,0);
				controller.remove(inventory);
				inventory = new InventoryRenderer(getMaze(),controllerPanelDim.width-20);
				controller.add(inventory);
				levelCounter.setText(String.valueOf(maze.getLevelNumber()));
				timeLeft = maze.getTimeLeft();
				repaintAll();
			}
			else {
				produceDialog("There was an error reading the file.\nPlease try again.", "File Error");
			}
		}
	}

	/**
	 * Load the level file to play from.
	 * @param levelNum Number of the level.
	 */
	public void persistenceLoad(int levelNum) throws FileNotFoundException {
		Maze maze;
		maze = Levels.loadLevel(levelNum);
		setMaze(maze);
		window.remove(game);
		game = new BoardRenderer(getMaze(), gamePanelDim);
		window.add(game,0);
		controller.remove(inventory);
		inventory = new InventoryRenderer(getMaze(),controllerPanelDim.width-20);
		controller.add(inventory);
		levelCounter.setText(String.valueOf(maze.getLevelNumber()));
	}


	/**
	 * Save the current game state to a file
	 */
	public void persistenceSave() {
		JFileChooser chooser = new JFileChooser();
		int choice = chooser.showSaveDialog(window);
		if (choice == JFileChooser.APPROVE_OPTION) {
			System.out.println(chooser.getCurrentDirectory());
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
		if (getMaze().getChapWin()) {
			if (getMaze().getLevelNumber() < MAX_LEVEL) {
				try {
					persistenceLoad(getMaze().getLevelNumber()+1);
				} catch (FileNotFoundException e) { e.printStackTrace(); }
				gameStart();
			}
			else { gameStop("You win!", "Game won!"); }
		}
		repaintAll();
	}

	/**
	 *  Runs the replay.
	 */
	public void runReplay() {
		AtomicInteger moveNum = new AtomicInteger();
		ActionListener replayListener = e -> {
			switch (getReplay().processActionsJson().get(moveNum.get())) {
				case "DOWN":
					movePlayer(Direction.DOWN);
					break;
				case "RIGHT":
					movePlayer(Direction.RIGHT);
					break;
				case "UP":
					movePlayer(Direction.UP);
					break;
				case "LEFT":
					movePlayer(Direction.LEFT);
					break;
			}
			moveNum.getAndIncrement();
			if (moveNum.get() >= getReplay().processActionsJson().size()) {
				replayTimer.stop();
			}
		};
		replayTimer = new Timer(500, replayListener);
		Replay replay = getReplay();


		if (replay != null) {
			replayTimer.start();
		}
		else {
			produceDialog("No active replay file loaded in!", "Please load replay!");
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
			case KeyEvent.VK_A:
				direction = Direction.LEFT;
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				direction = Direction.RIGHT;
				break;
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
				direction = Direction.UP;
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
				direction = Direction.DOWN;
				break;
			default:
				direction = null;
		}
		return direction;
	}

	/**
	 * Update and repaint all components which tend to  change regularly, such as panel repainting and counter texts.
	 */
	public void repaintAll() {
		treasuresCounter.setText(String.valueOf(getMaze().getTREASURES_NUM() - getMaze().getTreasuresPickedUp()));
		keysCounter.setText(String.valueOf(getMaze().getChap().getKeyInventory().size()));
		levelCounter.setText(String.valueOf(getMaze().getLevelNumber()));
		timeCounter.setText(String.valueOf(timeLeft));
		game.revalidate();
		game.repaint();
		inventory.revalidate();
		inventory.repaint();
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
