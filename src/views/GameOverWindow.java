package views;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Image;

@SuppressWarnings("serial")
public class GameOverWindow extends JFrame {

    public GameOverWindow(int puntos) {
        setTitle("Snake Game - Game Over");

        Image icono = new ImageIcon(
    	    getClass().getResource("/images/cat-icon.png")
    	).getImage();

        setIconImage(icono);

        add(new GameOverView(puntos)); 

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);

        setSize(600, 600);
        setVisible(true);
    }
}