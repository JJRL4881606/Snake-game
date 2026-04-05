package views;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Image;

@SuppressWarnings("serial")
public class GameWindow extends JFrame {

    GameView panel;
    HUDPanel hud;

    public GameWindow() {
    	
        setLayout(new BorderLayout());

        hud = new HUDPanel();
        panel = new GameView();

        add(hud, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        panel.setHUD(hud);
        
        //titulo
        setTitle("Snake Game - Juego");
        
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
