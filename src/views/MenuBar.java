package views;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {

    public MenuBar(GameView panel) {

        JMenu gameOption = new JMenu("Juego");
        add(gameOption);

        // NUEVO JUEGO
        JMenuItem newGame = new JMenuItem("Nuevo");
        newGame.addActionListener(e -> panel.restartGame());
        gameOption.add(newGame);

        // DIFICULTAD
        JMenu level = new JMenu("Dificultad");
        gameOption.add(level);

        JMenuItem hardOption = new JMenuItem("Difícil");
        hardOption.addActionListener(e -> panel.setDificultad("DIFICIL"));
        JMenuItem mediumOption = new JMenuItem("Medio");
        mediumOption.addActionListener(e -> panel.setDificultad("MEDIO"));
        JMenuItem easyOption = new JMenuItem("Fácil");
        easyOption.addActionListener(e -> panel.setDificultad("FACIL"));

        level.add(hardOption);
        level.add(mediumOption);
        level.add(easyOption);

        // PAUSA
        JMenuItem pause = new JMenuItem("Pausar");
        pause.addActionListener(e -> {
            if (panel.pausado) {
                panel.resumeGame();
                pause.setText("Pausar");
            } else {
                panel.pauseGame();
                pause.setText("Reanudar");
            }
        });

        gameOption.add(pause);
    }
}