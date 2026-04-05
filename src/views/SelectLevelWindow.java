package views;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Image;

@SuppressWarnings("serial")
public class SelectLevelWindow extends JFrame {

    public SelectLevelWindow() {
        setTitle("Snake Game - Selecciona la dificultad");
        
        Image icono = new ImageIcon(
    	    getClass().getResource("/images/cat-icon.png")
    	).getImage();

        setIconImage(icono);
        
        add(new SelectLevelView()); 
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack(); 
        setLocationRelativeTo(null);
        
        setSize(600, 600);
        setVisible(true);
    }
}