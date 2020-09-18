package nz.ac.vuw.ecs.swen225.gp20.application;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public abstract class GUI {

  JFrame window = new JFrame();

  public void aMethod() {

    JMenuBar menu = new JMenuBar();


    JPanel game =  new JPanel(); // = new Renderer();


    JPanel controller = new JPanel();



    window.setJMenuBar(menu);
    window.pack();
    window.setVisible(true);
  }

}
