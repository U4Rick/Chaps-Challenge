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

    JMenuItem gameItem = new JMenuItem("Game");
    JMenuItem gameStartItem = new JMenuItem("Start");
    JMenuItem gameLoadItem = new JMenuItem("Load");
    JMenuItem gameSaveItem = new JMenuItem("Save");

    gameItem.add(gameStartItem);
    gameItem.add(gameLoadItem);
    gameItem.add(gameSaveItem);

    JMenuItem pauseItem = new JMenuItem("Pause");

    JMenuItem quitItem = new JMenuItem("Quit");

    JMenuItem replayItem = new JMenuItem("Replay");
    JMenuItem replayStartItem = new JMenuItem("Start");
    JMenuItem replayLoadItem = new JMenuItem("Load");

    replayItem.add(replayStartItem);
    replayItem.add(replayLoadItem);

    JMenuItem helpItem = new JMenuItem();
    JMenuItem helpStartLoadItem = new JMenuItem();
    JMenuItem helpGameplayItem = new JMenuItem();
    JMenuItem helpReplayItem = new JMenuItem();

    helpItem.add(helpStartLoadItem);
    helpItem.add(helpGameplayItem);
    helpItem.add(helpReplayItem);

    menu.add(gameItem);
    menu.add(pauseItem);
    menu.add(quitItem);
    menu.add(replayItem);
    menu.add(helpItem);


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

    constraints.gridx = 1;
    constraints.gridy = 1;
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
