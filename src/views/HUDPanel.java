package views;

import javax.swing.*;

import utils.AppFont;
import utils.UIColors;

import java.awt.*;

@SuppressWarnings("serial")
public class HUDPanel extends JPanel {

    JLabel lblPuntos;
    JLabel lblDificultad;

    public HUDPanel() {
        setPreferredSize(new Dimension(600, 40));
        setBackground(new Color(0, 0, 0));
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        setBorder(BorderFactory.createMatteBorder(
        	    0, 0, 2, 0, UIColors.SNAKE_GREEN
        	));

        
        lblPuntos = new JLabel("Puntos: 0");
        lblPuntos.setForeground(UIColors.SNAKE_GREEN);
        lblPuntos.setFont(AppFont.normal());

        lblDificultad = new JLabel("Dificultad: MEDIO");
        lblDificultad.setForeground(UIColors.SNAKE_GREEN);
        lblDificultad.setFont(AppFont.normal());
        
        add(lblPuntos);
        add(lblDificultad);
    }

    public void setPuntos(int puntos) {
        lblPuntos.setText("Puntos: " + puntos);
    }

    public void setDificultad(String nivel) {
        lblDificultad.setText("Dificultad: " + nivel);

        switch (nivel) {
            case "FACIL":
                lblDificultad.setForeground(Color.GREEN);
                break;
            case "MEDIO":
                lblDificultad.setForeground(Color.YELLOW);
                break;
            case "DIFICIL":
                lblDificultad.setForeground(Color.RED);
                break;
        }
    }}