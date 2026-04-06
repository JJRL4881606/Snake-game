package views;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {

    public MenuBar(GameView panel) {

        JMenu gameOption = new JMenu("Juego - Ajustes");
        add(gameOption);

        // NUEVO JUEGO
        JMenuItem newGame = new JMenuItem("Nuevo");
        newGame.addActionListener(e -> panel.restartGame());
        gameOption.add(newGame);

        // DIFICULTAD
        JMenu level = new JMenu("Dificultad");
        gameOption.add(level);

        JMenuItem hardOption = new JMenuItem("Difícil");
        hardOption.addActionListener(e -> panel.setDifficulty("DIFICIL"));
        JMenuItem mediumOption = new JMenuItem("Medio");
        mediumOption.addActionListener(e -> panel.setDifficulty("MEDIO"));
        JMenuItem easyOption = new JMenuItem("Fácil");
        easyOption.addActionListener(e -> panel.setDifficulty("FACIL"));

        level.add(hardOption);
        level.add(mediumOption);
        level.add(easyOption);
        
        // MENU
        JMenuItem menu = new JMenuItem("Regresar al menú");
        menu.addActionListener(e -> {
            if (panel.musica != null) {
                panel.musica.stop();
                panel.musica.close();
            }
            new HomeWindow();
            SwingUtilities.getWindowAncestor(panel).dispose();
        });
        gameOption.add(menu);

        // PAUSA
        JMenuItem pause = new JMenuItem("Pausar / Reanudar");
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