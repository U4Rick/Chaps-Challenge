package nz.ac.vuw.ecs.swen225.gp20.application;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.commons.Direction;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Levels;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Persistence;
import nz.ac.vuw.ecs.swen225.gp20.recnreplay.Record;
import nz.ac.vuw.ecs.swen225.gp20.recnreplay.Replay;
import nz.ac.vuw.ecs.swen225.gp20.render.BoardRenderer;
import nz.ac.vuw.ecs.swen225.gp20.render.InventoryRenderer;
import nz.ac.vuw.ecs.swen225.gp20.render.SoundRenderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * Builds the Graphic User Interface.
 *
 * @author Keely Haskett 300473212
 */
public abstract class GUI {

	private final JFrame window = new JFrame();
	private JFrame replayWindow;
	private SoundRenderer sounds;

	//Constant values, for designing the UI.
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
	private JMenuItem replayStartItem;
	private JPanel controller;
	private GridBagConstraints controllerConst;
	private BoardRenderer game;
	private InventoryRenderer inventory;
	private Timer gameTimer;
	Timer replayTimer;

	private int timeLeft;
	private boolean canMove;
	private int lastKeyPressed;
	private boolean inReplay = false;
	private boolean debugMode = false;


	/**
	 *  Initializes the maze, and builds the main window.
	 */
	public GUI() {
		if (!processStartFile()) {
			try {
				persistenceLoad(1, true);
			} catch (FileNotFoundException e) {
				produceDialog("There was an error reading the file.\nPlease try again.", "File Error");
			}
		}
		canMove = false;
		buildWindow();
		repaintAll();
	}


	///////////////////////////////////////
	///              BUILD              ///
	///////////////////////////////////////

	/**
	 *  Builds the window with a JMenuBar, a Renderer panel and a Controller panel.
	 */
	public void buildWindow() {
		sounds = new SoundRenderer();

		JMenuBar menu = new JMenuBar();
		menu.setBackground(barColorNormal);
		menu.setOpaque(true);
		menu.setLayout(new FlowLayout(FlowLayout.LEFT));

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
		JMenuItem startRule = new JMenuItem("Click start to begin the game from loaded level or save.");
		setMenuDetails(startRule);
		JMenuItem loadRule = new JMenuItem("Through the load menu, you can load a game from either a level or a save file.");
		setMenuDetails(loadRule);
		JMenuItem saveRule = new JMenuItem("Click save to save your current game.");
		setMenuDetails(saveRule);
		helpStartLoad.add(startRule);
		helpStartLoad.add(loadRule);
		helpStartLoad.add(saveRule);
		JMenu helpGameplay = new JMenu("Gameplay");
		setMenuDetails(helpGameplay);
		JMenuItem timeRule = new JMenuItem("Complete the level by finding the golden lily-pad before time's up!");
		setMenuDetails(timeRule);
		JMenuItem treasuresRule = new JMenuItem("Collect all the treasures to unlock the golden lily-pad area.");
		setMenuDetails(treasuresRule);
		JMenuItem fishRule = new JMenuItem("Collect fish to feed crocodiles of the same colour and unlock new areas.");
		setMenuDetails(fishRule);
		JMenuItem infoRule = new JMenuItem("Stand on the ? to receive helpful tips about the level!");
		setMenuDetails(infoRule);
		JMenuItem enemyRule = new JMenuItem("Watch out for enemies! Game over if they touch you!");
		setMenuDetails(enemyRule);
		helpGameplay.add(timeRule);
		helpGameplay.add(treasuresRule);
		helpGameplay.add(fishRule);
		helpGameplay.add(infoRule);
		helpGameplay.add(enemyRule);
		JMenu helpReplay = new JMenu("Replay");
		setMenuDetails(helpReplay);
		JMenuItem endRule = new JMenuItem("<html>When you either finish the last level, or lose the game, <br> you will be prompted to save your play, which will be loaded for you!</html>");
		setMenuDetails(endRule);
		JMenuItem replayLoadRule = new JMenuItem("<html>You can load your own replay file through the Replay -> Load submenu! <br>If you were playing the game, your game will be stopped on load.</html>");
		setMenuDetails(replayLoadRule);
		JMenuItem replayRule = new JMenuItem("<html>When you click Replay -> Start, a new window with replay controls will open. <br>You can use those controls to adjust the replay, and the window will close on replay end.</html>");
		setMenuDetails(replayRule);
		helpReplay.add(endRule);
		helpReplay.add(replayLoadRule);
		helpReplay.add(replayRule);

		helpMenu.add(helpStartLoad);
		helpMenu.add(helpGameplay);
		helpMenu.add(helpReplay);


		menu.add(gameMenu);
		menu.add(pauseMenuItem);
		menu.add(quitMenuItem);
		menu.add(replayMenu);
		menu.add(helpMenu);


		game = new BoardRenderer(getMaze(), gamePanelDim);
		window.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) { }
			@Override
			public void keyPressed(KeyEvent e) {
				lastKeyPressed = e.getKeyCode();
				processKeyEvent(e);
			}
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
		controllerConst = new GridBagConstraints();

		controllerConst.gridx = 0;
		controllerConst.gridy = 0;
		controllerConst.insets = controllerPanelStandardInsets;
		controller.add(timeLabel, controllerConst);

		controllerConst.gridy++;
		controller.add(timeCounter, controllerConst);

		controllerConst.gridy++;
		controller.add(levelLabel, controllerConst);

		controllerConst.gridy++;
		controller.add(levelCounter, controllerConst);

		controllerConst.gridy++;
		controller.add(keysLabel, controllerConst);

		controllerConst.gridy++;
		controller.add(keysCounter, controllerConst);

		controllerConst.gridy++;
		controller.add(treasuresLabel, controllerConst);

		controllerConst.gridy++;
		controller.add(treasuresCounter, controllerConst);

		controllerConst.gridy++;
		controller.add(inventory, controllerConst);

		gameStartItem.addActionListener(e -> gameStart());

		gameLoadLevel.addActionListener(e -> persistenceLoad(false));

		gameLoadState.addActionListener(e -> persistenceLoad(true));

		gameSaveItem.addActionListener(e -> persistenceSave());

		pauseMenuItem.addActionListener(e -> openPauseDialog());

		quitMenuItem.addActionListener(e -> System.exit(0));

		replayStartItem.addActionListener(e -> buildReplayControls());

		replayLoadItem.addActionListener(e -> replayLoad());


		window.setLayout(new FlowLayout());
		window.add(game);
		window.add(controller);

		window.setTitle("Chap's Challenge");
		window.setJMenuBar(menu);
		window.getContentPane().setBackground(mainColor);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				processToFile(false, false);
				super.windowClosing(e);
			}});

		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}


	/**
	 * Build the replay controls window to control how the replay is run.
	 */
	public void buildReplayControls() {
		inReplay = true;
		try {
			persistenceLoad(getReplay().currentLevel, false);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		replayWindow = new JFrame();
		replayWindow.setLayout(new GridBagLayout());
		JRadioButton stepRadioButton = new JRadioButton("Step-by-Step");
		stepRadioButton.setEnabled(true);
		JRadioButton autoRadioButton = new JRadioButton("Auto");
		autoRadioButton.setSelected(true);
		autoRadioButton.setEnabled(true);
		ButtonGroup buttons = new ButtonGroup();
		buttons.add(stepRadioButton);
		buttons.add(autoRadioButton);

		JButton stepButton = new JButton("Step");

		JSlider replaySlider = new JSlider(1, 20);
		replaySlider.setValue(2);
		replaySlider.setPaintLabels(true);
		replaySlider.setPaintTicks(true);
		replaySlider.setMinorTickSpacing(1);

		GridBagConstraints constraints = new GridBagConstraints();

		AtomicInteger moveNum = new AtomicInteger(0);
		ArrayList<String> actions = (ArrayList<String>) getReplay().processActionsJson();

		ActionListener replayListener = e -> moveNum.set(replayStep(true, actions.get(moveNum.get()), moveNum.get(), actions.size()));

		replayTimer = new Timer(500, replayListener);
		replayTimer.start();

		stepRadioButton.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				replayWindow.add(stepButton, constraints);
				replayWindow.revalidate();
				replayWindow.repaint();
			}
			else if (e.getStateChange() == ItemEvent.DESELECTED) {
				replayWindow.remove(stepButton);
				replayWindow.revalidate();
				replayWindow.repaint();
			}
		});

		autoRadioButton.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				replayWindow.add(replaySlider, constraints);
				replayWindow.revalidate();
				replayWindow.repaint();
				replayTimer.start();
			}
			else if (e.getStateChange() == ItemEvent.DESELECTED) {
				replayWindow.remove(replaySlider);
				replayWindow.revalidate();
				replayWindow.repaint();
				replayTimer.stop();
			}
		});

		stepButton.addActionListener(e -> moveNum.set(replayStep(false, actions.get(moveNum.get()), moveNum.get(), actions.size())));

		replaySlider.addChangeListener(e -> {
			replayTimer.setDelay(1000 / replaySlider.getValue());
			replayTimer.start();
		});

		constraints.insets = new Insets(10,10,10,10);
		constraints.gridx = 0;
		constraints.gridy = 0;
		replayWindow.add(stepRadioButton, constraints);
		constraints.gridx = 1;
		replayWindow.add(autoRadioButton, constraints);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		replayWindow.add(replaySlider, constraints);

		replayWindow.setTitle("Replay Controls");
		replayWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


		replayWindow.setResizable(false);
		replayWindow.pack();
		replayWindow.setLocationRelativeTo(null);
		replayWindow.setVisible(true);
	}


	/**
	 * Open the dialog for game pause and handle it closing.
	 */
	public void openPauseDialog() {
		if (!debugMode) {
			if (gameTimer != null) {
				gameTimer.stop();
			}

			JOptionPane option = new JOptionPane(JOptionPane.DEFAULT_OPTION);
			option.setMessage("Game is paused.");
			JDialog dialog = option.createDialog("Paused");
			dialog.pack();
			dialog.setVisible(true);
			int choice = (Integer) option.getValue();
			if (choice == JOptionPane.OK_OPTION || lastKeyPressed == KeyEvent.VK_ESCAPE) {
				if (gameTimer != null) {
					gameTimer.start();
				}
				dialog.setVisible(false);
			}
		}
	}

	/**
	 * Create a new JDialog and handle closing it.
	 * @param message   Message of the dialog.
	 * @param title Title of the dialog.
	 */
	public void produceDialog(String message, String title) {
		if (!debugMode) {
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
	}

	/**
	 * Enable or disable debug mode, no dialogs will be produced while in debug mode.
	 * @param debug State of the mode.
	 */
	public void setDebugMode(boolean debug) {
		debugMode = debug;
	}


	///////////////////////////////////////
	///            GAMEPLAY             ///
	///////////////////////////////////////

	/**
	 * Actions to be processed on every gameTimer tick.
	 * Does actions for game end.
	 */
	public void onGameTimeTick() {
		timeLeft--;
		getMaze().setTimeLeft(timeLeft);
		getMaze().moveNPCs();
		repaintAll();
		if (timeLeft <= 0) { gameStop("Game Over!", "You ran out of time!", true); }
	}

	/**
	 * Start the game process.
	 */
	public void gameStart() {
		//reload the level in case the game is mid-way
		try {
			persistenceLoad(getMaze().getLevelNumber(), false);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		timeLeft = getMaze().getTimeAvailable();
		gameTimer.start();
		canMove = true;
		setRecord(new Record(getMaze().getLevelNumber()));
		pauseMenuItem.setEnabled(true);
		timeCounter.setText(String.valueOf(timeLeft));
	}

	/**
	 * Stops the game running, with a JDialog containing custom messages.
	 * Saves and loads replay of game just played.
	 * @param dialogMessage Message to be on JDialog.
	 * @param dialogTitle   Title of the JDialog.
	 */
	public void gameStop(String dialogMessage, String dialogTitle, boolean gameOver) {
		canMove = false;
		gameTimer.stop();

		produceDialog(dialogMessage, dialogTitle);

		if (gameOver && !debugMode) {
			JFileChooser chooser = new JFileChooser("../chapschallenge/saves/");
			int replayChoice = chooser.showSaveDialog(window);
			if (replayChoice == JFileChooser.APPROVE_OPTION) {
				File replayFile;
				if (!chooser.getSelectedFile().toString().toLowerCase().endsWith(".json")) {
					replayFile = new File(chooser.getSelectedFile().getPath() + ".json");
				} else {
					replayFile = chooser.getSelectedFile();
				}
				getRecord().writeToFile(replayFile);
				replayLoad(replayFile);
			}
		}
	}

	/**
	 * Helper method to move the player and repaint the renderer.
	 * @param direction Direction to move player.
	 */
	public void movePlayer(Direction direction) {
		getMaze().moveChap(direction);
		if (!debugMode) { sounds.playSound(getMaze().getChapCurrentMove());}

		if (getRecord() != null) { getRecord().addMove(direction); }

		if (getMaze().getChapWin() && !inReplay) {
			//if there's another level to progress to
			if (getMaze().getLevelNumber() < MAX_LEVEL) {
				try {
					persistenceLoad(getMaze().getLevelNumber()+1, false);
				} catch (FileNotFoundException e) { produceDialog("There was an error reading the file.\nPlease try again.", "File Error"); }

				gameStart();
			}
			else { gameStop("You win!", "Game won!", true); }
		}
		repaintAll();
	}

	/**
	 * Perform a step in the replay, specified by the point and it's action.
	 * Shuts down the controls window if finished.
	 * @param auto If true, the replay is running on a timer, if false, no timer
	 * @param action    The action to perform
	 * @param step  The number of the step
	 * @param size  Size of the original list of actions.
	 * @return  Returns the position in original list post incrementation
	 */
	public int replayStep(boolean auto, String action, int step, int size) {
		switch (action) {
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
			default:
				break;
		}

		step++;
		if (step >= size) {
			if (auto) { replayTimer.stop(); }
			replayWindow.setVisible(false);
			inReplay = false;
		}
		return step;
	}


	///////////////////////////////////////
	///           KEY EVENTS            ///
	///////////////////////////////////////

	/**
	 * Process any KeyEvent encountered.
	 * @param e KeyEvent to process
	 */
	public void processKeyEvent(KeyEvent e) {
		if (e.isControlDown()) {
			switch (e.getKeyCode()) {
				//exit the game, the current game state will be lost, the next time the game is started, it will resume from the last unfinished level
				case KeyEvent.VK_X:
					processToFile(true, true);
					System.exit(0);
					break;

				//exit the game, saves the game state, game will resume next time the application will be started
				case KeyEvent.VK_S:
					processToFile(true, false);
					System.exit(0);
					break;

				//resume a saved game
				case KeyEvent.VK_R:
					persistenceLoad(true);
					gameStart();
					break;

				//start a new game at the last unfinished level
				case KeyEvent.VK_P:
					try {
						persistenceLoad(getMaze().getLevelNumber(), false);
						gameStart();
					} catch (FileNotFoundException ignored) {
						produceDialog("There was an error reading the file.\nPlease try again.", "File Error");
					}

					break;

				//start a new game at level 1
				case KeyEvent.VK_1:
					try {
						persistenceLoad(1, false);
						gameStart();
					} catch (FileNotFoundException ignored) {
						produceDialog("There was an error reading the file.\nPlease try again.", "File Error");
					}

					break;

				default:
					break;
			}
		} else {
			//pause the game
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				openPauseDialog();
			}

			//if not anything else, assume user is trying to move chap
			else {
				checkMove(e);
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


	///////////////////////////////////////
	///        STYLING + PAINTING       ///
	///////////////////////////////////////

	/**
	 * Sets the visual details of elements in the controller.
	 * @param label JLabel to set
	 */
	private void setControllerElementDetails(JComponent label) {
		label.setPreferredSize(counterLabelDim);
		label.setFont(controllerElementsFont);
		if (label instanceof JLabel) {
			((JLabel) label).setHorizontalAlignment(SwingConstants.CENTER);
		}
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


	///////////////////////////////////////
	///          FILE HANDLING          ///
	///////////////////////////////////////

	/**
	 * Process the final game state to a final for next opening.
	 * @param hasContent    True if something needs to be stored, false is not
	 * @param freshLevel    True if opening new level, false if opening save
	 */
	public void processToFile(boolean hasContent, boolean freshLevel) {
		File file = new File("../chapschallenge/status.txt");
		StringBuilder b = new StringBuilder();

		if (!hasContent) {
			b.append("clean");
		}
		else if (freshLevel) {
			b.append("level").append("\n");
			b.append(getMaze().getLevelNumber());
		}
		else {
			b.append("save").append("\n");
			b.append(persistenceSaveClose());
		}

		try (FileWriter w = new FileWriter(file)) {
			w.write(b.toString());
		} catch (IOException e) {
			produceDialog("There was an error writing to the file.\nPlease try again.", "File Error");
		}
		if (hasContent) {
			produceDialog("Game preferences, ready on next application start", "Stored Preferences");
		}
	}

	/**
	 * Process the start file, status.txt, and process any actions it requires from the last
	 * time the application was run.
	 * @return  Returns true if new game is created, false if not.
	 */
	public boolean processStartFile() {
		try (Scanner sc = new Scanner(new FileReader("../chapschallenge/status.txt"))) {
			String initialLine = sc.nextLine();
			switch (initialLine) {
				case "level":
					persistenceLoad(Integer.parseInt(sc.next()), true);
					sc.close();
					return true;
				case "save":
					File file = new File(sc.nextLine());
					persistenceLoad(file);
					sc.close();
					return true;
				default:
					sc.close();
					return false;
			}

		} catch (FileNotFoundException e) {
			produceDialog("There was an error finding status.txt", "File Error");
			return false;
		}
	}

	/**
	 *  Load the replay file and reset the replay object.
	 */
	public void replayLoad() {
		JFileChooser chooser = new JFileChooser("../chapschallenge/saves/");
		int choice = chooser.showOpenDialog(window);
		if (choice == JFileChooser.APPROVE_OPTION) {
			File toLoadFrom = chooser.getSelectedFile();
			Replay replay = new Replay(toLoadFrom);
			replay.loadFile(toLoadFrom);
			setReplay(replay);
		}
		gameStop("Replay process started, game stopped", "Game Stopped by User", false);
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
		try {
			persistenceLoad(replay.currentLevel, false);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		replayStartItem.setEnabled(true);
	}

	/**
	 * Load the level file to play from.
	 * @param isState   If true, load a pre-played and saved level.
	 *                  If false, load a fresh version of a level.
	 */
	public void persistenceLoad(boolean isState) {
		JFileChooser chooser = new JFileChooser("../chapschallenge/saves/");
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
				controller.add(inventory, controllerConst);
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
	public void persistenceLoad(int levelNum, boolean startOfGame) throws FileNotFoundException {
		Maze maze;
		maze = Levels.loadLevel(levelNum);
		setMaze(maze);
		if (!startOfGame) {
			window.remove(game);
			game = new BoardRenderer(getMaze(), gamePanelDim);
			window.add(game,0);
			controller.remove(inventory);
			inventory = new InventoryRenderer(getMaze(),controllerPanelDim.width-20);
			controller.add(inventory, controllerConst);
			levelCounter.setText(String.valueOf(maze.getLevelNumber()));
		}
	}

	/**
	 * Load a save from specific file.
	 * @param file  File to load save from.
	 */
	public void persistenceLoad(File file){
		Maze maze;
		maze = Persistence.loadGameState(file);
		if (maze != null) { setMaze(maze); }
		else { produceDialog("There was an error reading the file.\nPlease try again.", "File Error"); }
	}

	/**
	 * Save the current game state to a file
	 */
	public void persistenceSave() {
		gameTimer.stop();
		JFileChooser chooser = new JFileChooser("../chapschallenge/saves/");
		int choice = chooser.showSaveDialog(window);
		if (choice == JFileChooser.APPROVE_OPTION) {
			File toSaveTo;
			if (!chooser.getSelectedFile().toString().toLowerCase().endsWith(".json")) {
				toSaveTo = new File(chooser.getSelectedFile().getPath() + ".json");
			}
			else {
				toSaveTo = chooser.getSelectedFile();
			}
			Persistence.saveGameState(getMaze(), toSaveTo);
		}
		gameTimer.start();
	}

	/**
	 * Save the current game state to a file, used at window close with game save.
	 */
	public String persistenceSaveClose() {
		gameTimer.stop();
		JFileChooser chooser = new JFileChooser("../chapschallenge/saves/");
		int choice = chooser.showSaveDialog(window);
		if (choice == JFileChooser.APPROVE_OPTION) {
			System.out.println(chooser.getCurrentDirectory());
			File toSaveTo = chooser.getSelectedFile();
			Persistence.saveGameState(getMaze(), toSaveTo);
			return chooser.getSelectedFile().getPath();
		}
		return null;
	}

	///////////////////////////////////////
	///            OVERRIDES            ///
	///////////////////////////////////////

	/**
	 *  Gets the record object.
	 * @return  Replay Object
	 */
	protected abstract Record getRecord();

	/**
	 * Sets the record object.
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
