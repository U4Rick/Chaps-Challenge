package nz.ac.vuw.ecs.swen225.gp20.application;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public abstract class GUI {

	JFrame window = new JFrame();

	public final Dimension counterLabelDim = new Dimension(100, 40);
	public final Dimension gamePanelDim = new Dimension(500, 500);
	public final Dimension controllerPanelDim = new Dimension(200, 500);
	public final Insets controllerPanelStandardInsets = new Insets(5, 0, 5, 0);

	public final Color mainColor =  new Color(76, 175, 80);
	public final Color textColorNormal = new Color(255, 255, 255);
	public final Color barColorNormal = new Color(139, 195, 74);
	public final Color barColorHover = new Color(76, 175, 80);

	public final Font controllerElementsFont = new Font("Calibri", Font.BOLD, 16);

	public GUI() {
		aMethod();
	}

	public void aMethod() {

		JMenuBar menu = new JMenuBar();
		menu.setBackground(barColorNormal);
		menu.setOpaque(true);

		JMenu gameMenu = new JMenu("Game");
		setMenuDetails(gameMenu);
		JMenu gameStart = new JMenu("Start");
		setMenuDetails(gameStart);
		gameStart.addMenuListener(new MenuListener() {
			@Override
			public void menuSelected(MenuEvent e) {

			}

			@Override
			public void menuDeselected(MenuEvent e) {

			}

			@Override
			public void menuCanceled(MenuEvent e) {

			}
		});
		JMenu gameLoad = new JMenu("Load");
		setMenuDetails(gameLoad);
		gameLoad.addMenuListener(new MenuListener() {
			@Override
			public void menuSelected(MenuEvent e) {

			}

			@Override
			public void menuDeselected(MenuEvent e) {

			}

			@Override
			public void menuCanceled(MenuEvent e) {

			}
		});
		JMenu gameSave = new JMenu("Save");
		setMenuDetails(gameSave);
		gameSave.addMenuListener(new MenuListener() {
			@Override
			public void menuSelected(MenuEvent e) {

			}

			@Override
			public void menuDeselected(MenuEvent e) {

			}

			@Override
			public void menuCanceled(MenuEvent e) {

			}
		});

		gameMenu.add(gameStart);
		gameMenu.add(gameLoad);
		gameMenu.add(gameSave);

		JMenu pauseMenu = new JMenu("Pause");
		setMenuDetails(pauseMenu);
		pauseMenu.addMenuListener(new MenuListener() {
			@Override
			public void menuSelected(MenuEvent e) {

			}

			@Override
			public void menuDeselected(MenuEvent e) {

			}

			@Override
			public void menuCanceled(MenuEvent e) {

			}
		});
		JMenu quitMenu = new JMenu("Quit");
		setMenuDetails(quitMenu);
		quitMenu.addMenuListener(new MenuListener() {
			@Override
			public void menuSelected(MenuEvent e) {
				System.exit(0);
			}

			@Override
			public void menuDeselected(MenuEvent e) { }

			@Override
			public void menuCanceled(MenuEvent e) { }
		});

		JMenu replayMenu = new JMenu("Replay");
		setMenuDetails(replayMenu);
		JMenu replayStart = new JMenu("Start");
		setMenuDetails(replayStart);
		replayStart.addMenuListener(new MenuListener() {
			@Override
			public void menuSelected(MenuEvent e) {

			}

			@Override
			public void menuDeselected(MenuEvent e) {

			}

			@Override
			public void menuCanceled(MenuEvent e) {

			}
		});
		JMenu replayLoad = new JMenu("Load");
		setMenuDetails(replayLoad);
		replayLoad.addMenuListener(new MenuListener() {
			@Override
			public void menuSelected(MenuEvent e) {

			}

			@Override
			public void menuDeselected(MenuEvent e) {

			}

			@Override
			public void menuCanceled(MenuEvent e) {

			}
		});

		replayMenu.add(replayStart);
		replayMenu.add(replayLoad);

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
		menu.add(pauseMenu, menuConstraints);

		menuConstraints.gridx++;
		menu.add(quitMenu, menuConstraints);

		menuConstraints.gridx++;
		menuConstraints.insets = new Insets(0, 360, 0,130);
		menu.add(replayMenu, menuConstraints);

		menuConstraints.gridx++;
		menuConstraints.gridwidth = 10;
		menuConstraints.insets = new Insets(0, 0, 0,0);
		menuConstraints.anchor = GridBagConstraints.LINE_END;
		menu.add(helpMenu, menuConstraints);


		JPanel game = new JPanel(); // = new Renderer();
		game.setPreferredSize(gamePanelDim);
		game.setBackground(Color.BLACK);
		game.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyCode() == (KeyEvent.VK_LEFT) || e.getKeyCode() == (KeyEvent.VK_RIGHT) || e.getKeyCode() == (KeyEvent.VK_UP) || e.getKeyCode() == (KeyEvent.VK_DOWN)) {
					movePlayer(e);
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});

		JPanel controller = new JPanel();
		controller.setPreferredSize(controllerPanelDim);
		controller.setBackground(mainColor);

		JLabel timeLabel = new JLabel("Time");
		setControllerElementDetails(timeLabel);

		JLabel timeCounter = new JLabel("1200");
		setControllerElementDetails(timeCounter);

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
		constraints.insets = new Insets(5, 50, 50, 50);
		controller.add(treasuresCounter, constraints);

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

	public void movePlayer(KeyEvent e) {
		Maze.Direction d = null;
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			d = Maze.Direction.LEFT;
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			d = Maze.Direction.RIGHT;
		}
		else if (e.getKeyCode() == KeyEvent.VK_UP) {
			d = Maze.Direction.UP;
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			d = Maze.Direction.DOWN;
		}
		getMaze().moveChap(d);
	}

	public abstract Maze getMaze();

	private void setControllerElementDetails(JLabel label) {
		label.setPreferredSize(counterLabelDim);
		label.setFont(controllerElementsFont);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(textColorNormal);
	}

	private void setMenuDetails (JMenu menu) {
		menu.setForeground(textColorNormal);
		menu.setBackground(barColorNormal);
		menu.setFont(controllerElementsFont);
		menu.setOpaque(true);
	}

}
