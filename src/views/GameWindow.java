package views;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.Image;

@SuppressWarnings("serial")
public class GameWindow extends JFrame {

    GameView panel;
    HUDPanel hud;

    public GameWindow() {
    	
        setLayout(new java.awt.BorderLayout());

        hud = new HUDPanel();
        panel = new GameView();

        add(hud, java.awt.BorderLayout.NORTH);
        add(panel, java.awt.BorderLayout.CENTER);

        panel.setHUD(hud);
        
        //titulo
        setTitle("Snake Game");
        
        //agregar menubar
        setJMenuBar(new MenuBar(panel));
        
        Image icono = new ImageIcon(
    	    getClass().getResource("/images/cat-icon.png")
    	).getImage();

        setIconImage(icono);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    

}
