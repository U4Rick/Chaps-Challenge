package nz.ac.vuw.ecs.swen225.gp20.application;

import java.awt.*;
import javax.swing.*;

public abstract class GUI {

	JFrame window = new JFrame();

	public final Dimension counterLabelDim = new Dimension(100, 40);
	public final Dimension gamePanelDim = new Dimension(500, 500);
	public final Dimension controllerPanelDim = new Dimension(200, 500);
	public final Insets controllerPanelStandardInsets = new Insets(5, 50, 5, 50);

	public final Font controllerElementsFont = new Font("Helvetica", Font.PLAIN, 15);

	public GUI() {
		aMethod();
	}

	public void aMethod() {

		JMenuBar menu = new JMenuBar();
		menu.setBackground(Color.MAGENTA);
		menu.setOpaque(true);

		JMenu gameMenu = new JMenu("Game");
		JMenu gameStart = new JMenu("Start");
		JMenu gameLoad = new JMenu("Load");
		JMenu gameSave = new JMenu("Save");

		gameMenu.add(gameStart);
		gameMenu.add(gameLoad);
		gameMenu.add(gameSave);

		JMenu pauseMenu = new JMenu("Pause");

		JMenu quitMenu = new JMenu("Quit");

		JMenu replayMenu = new JMenu("Replay");
		JMenu replayStart = new JMenu("Start");
		JMenu replayLoad = new JMenu("Load");

		replayMenu.add(replayStart);
		replayMenu.add(replayLoad);

		JMenu helpMenu = new JMenu("Help");
		JMenu helpStartLoad = new JMenu("Start/Load");
		JMenu helpGameplay = new JMenu("Gameplay");
		JMenu helpReplay = new JMenu("Replay");

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
		game.setBackground(Color.BLUE);

		JPanel controller = new JPanel();
		controller.setPreferredSize(controllerPanelDim);
		controller.setBackground(Color.GREEN);

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
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	private void setControllerElementDetails(JLabel label) {
		label.setPreferredSize(counterLabelDim);
		label.setFont(controllerElementsFont);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		//set colour

	}

}
