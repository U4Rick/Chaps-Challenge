package nz.ac.vuw.ecs.swen225.gp20.application;

import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public abstract class GUI {

  JFrame window = new JFrame();

  public final Dimension counterLabelDim = new Dimension(100, 40);
  public final Insets controllerPanelStandardInsets = new Insets(5, 50, 5, 50);

  public GUI () {
    aMethod();
  }

  public void aMethod() {

    JMenuBar menu = new JMenuBar();

    JMenuItem gameItem = new JMenuItem();
    JMenuItem gameStartItem = new JMenuItem();
    JMenuItem gameLoadItem = new JMenuItem();
    JMenuItem gameSaveItem = new JMenuItem();

    gameItem.add(gameStartItem);
    gameItem.add(gameLoadItem);
    gameItem.add(gameSaveItem);

    JMenuItem pauseItem = new JMenuItem();

    JMenuItem quitItem = new JMenuItem();

    JMenuItem replayItem = new JMenuItem();
    JMenuItem replayStartItem = new JMenuItem();
    JMenuItem replayLoadItem = new JMenuItem();

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


    JPanel game =  new JPanel(); // = new Renderer();


    JPanel controller = new JPanel();

    JLabel timeLabel = new JLabel("Time");

    JLabel timeCounter = new JLabel("1200");
    timeCounter.setPreferredSize(counterLabelDim);
    JLabel levelLabel = new JLabel("Level");

    JLabel levelCounter = new JLabel("1");
    timeCounter.setPreferredSize(counterLabelDim);
    JLabel keysLabel = new JLabel("Keys");

    JLabel keysCounter = new JLabel("4");
    keysCounter.setPreferredSize(counterLabelDim);
    JLabel treasuresLabel = new JLabel("Treasures Left");

    JLabel treasuresCounter = new JLabel("3");
    treasuresCounter.setPreferredSize(counterLabelDim);


    window.add(game);
    window.add(controller);

    window.setJMenuBar(menu);
    window.pack();
    window.setVisible(true);
  }

}
