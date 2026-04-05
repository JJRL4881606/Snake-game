package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Window;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import components.RoundButton;
import components.RoundedPanel;
import utils.AppFont;
import utils.UIColors;

@SuppressWarnings("serial")
public class GameOverView extends JPanel {
	
	//la musica de fondo
	Clip musica;
	
	int puntos;
	
	Image backgroundGif;
	
	public GameOverView(int puntos) {
	    this.puntos = puntos;

		setLayout(new GridBagLayout());
		this.setOpaque(false);
		
		backgroundGif = new ImageIcon(
		    getClass().getResource("/images/galaxy-gameover-bg.gif")
		).getImage();
		
	    
	    playMusic();

	    initializeComponents();
	}
    
	private void initializeComponents() {
		RoundedPanel card = new RoundedPanel(50);
		card.setOpaque(false); // transparente
		card.setBackground(new Color(0, 0, 0, 100)); // negro semi transparente
		card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
		card.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));
	    card.putClientProperty("FlatLaf.style", "arc:20");

		card.setBorder(BorderFactory.createCompoundBorder(
		    BorderFactory.createLineBorder(UIColors.SNAKE_GREEN, 3, true), // borde verde redondeado
		    BorderFactory.createEmptyBorder(25, 35, 25, 35) // padding interno
		));		
		
	    card.add(createTitle());
	    card.add(Box.createRigidArea(new Dimension(0, 10)));
	    card.add(createScore());
	    card.add(Box.createRigidArea(new Dimension(0, 20)));
	    card.add(createCat());		    
	    card.add(Box.createRigidArea(new Dimension(0, 20))); 
	    card.add(createButton());	    	    
	    
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.insets = new Insets(20, 20, 20, 20); 
	    add(card, gbc);
	}
	
	private JPanel createTitle() {
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setOpaque(false);	    
	    
		JLabel lblGameOver = new JLabel("GAME");
		lblGameOver.setForeground(UIColors.SNAKE_GREEN);
		lblGameOver.setBorder(new EmptyBorder(10, 20, 0, 20)); 
		lblGameOver.setFont(AppFont.title());
		lblGameOver.setAlignmentX(CENTER_ALIGNMENT);
		
		JLabel lblGameOver2 = new JLabel("OVER");
		lblGameOver2.setForeground(UIColors.SNAKE_GREEN);
		lblGameOver2.setBorder(new EmptyBorder(0, 20, 10, 20)); 
		lblGameOver2.setFont(AppFont.title());
		lblGameOver2.setAlignmentX(CENTER_ALIGNMENT);

	    panel.add(lblGameOver);
	    panel.add(lblGameOver2);
	    
	    return panel;
	}
	
	private JPanel createCat() {
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setOpaque(false);

	    ImageIcon cat = new ImageIcon(
	        getClass().getResource("/images/game-over-cat.png")
	    );

	    JLabel lblCat = new JLabel(cat);
	    lblCat.setAlignmentX(CENTER_ALIGNMENT);

	    panel.add(lblCat);

	    return panel;
	}
	
	private JPanel createButton() {
	    JPanel panelButton = new JPanel();
	    panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.Y_AXIS));
	    panelButton.setBorder(new EmptyBorder(5, 20, 10, 20));
	    panelButton.setOpaque(false);

	    JButton btnPlay = new RoundButton(
	        "PLAY",
	        new ImageIcon(getClass().getResource("/images/play.png"))
	    );

	    btnPlay.setBackground(UIColors.BUTTON);
	    btnPlay.setForeground(UIColors.BUTTON_TEXT);
	    btnPlay.setFont(AppFont.subtitle());
	    btnPlay.setPreferredSize(new Dimension(180, 60));
	    btnPlay.setMaximumSize(new Dimension(180, 60));
	    btnPlay.setMinimumSize(new Dimension(180, 60));
	    btnPlay.setAlignmentX(CENTER_ALIGNMENT);
	    btnPlay.setBorder(new EmptyBorder(10, 20, 10, 20));
	    btnPlay.setToolTipText("Haz clic para jugar!");
	    btnPlay.addActionListener(e -> {
	        new GameWindow();

	        Window window = SwingUtilities.getWindowAncestor(btnPlay);
	        if (window != null) {
	            musica.close();
	            window.dispose();
	        }
	    });
	    
	    JButton btnHome = new RoundButton(
	        "MENU",
	        new ImageIcon(getClass().getResource("/images/home.png"))
	    );

	    btnHome.setBackground(UIColors.BUTTON);
	    btnHome.setForeground(UIColors.BUTTON_TEXT);
	    btnHome.setFont(AppFont.subtitle());
	    btnHome.setPreferredSize(new Dimension(180, 60));
	    btnHome.setMaximumSize(new Dimension(180, 60));
	    btnHome.setMinimumSize(new Dimension(180, 60));
	    btnHome.setAlignmentX(CENTER_ALIGNMENT);
	    btnHome.setBorder(new EmptyBorder(10, 20, 10, 20));
	    btnHome.setToolTipText("Haz clic para regresar al menú!");
	    btnHome.addActionListener(e -> {
	    	new HomeWindow();

	        Window window = SwingUtilities.getWindowAncestor(btnHome);
	        if (window != null) {
	            musica.close();
	            window.dispose();
	        }
	    });

	    panelButton.add(Box.createVerticalStrut(5));
	    panelButton.add(btnPlay);
	    panelButton.add(Box.createRigidArea(new Dimension(0, 10)));
	    panelButton.add(btnHome);
	    panelButton.add(Box.createVerticalStrut(5));
	    
	    return panelButton;
	}
	
	private JPanel createScore() {
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setOpaque(false);

	    JLabel lblScore = new JLabel("PUNTOS: " + puntos);
	    lblScore.setFont(AppFont.subtitle());
	    lblScore.setForeground(UIColors.SNAKE_GREEN);
	    lblScore.setAlignmentX(CENTER_ALIGNMENT);

	    panel.add(lblScore);

	    return panel;
	}
	
	public void playMusic() {
	    try {
	        // Si ya hay música sonando, detenerla primero
	        if (musica != null && musica.isRunning()) {
	            musica.stop();
	            musica.close();
	        }

	        File archivo = new File("src/music/game-over.wav");
	        AudioInputStream audio = AudioSystem.getAudioInputStream(archivo);

	        musica = AudioSystem.getClip();
	        musica.open(audio);

	        musica.loop(Clip.LOOP_CONTINUOUSLY);
	        musica.start();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);

	    // dibujar gif de fondo
	    g.drawImage(backgroundGif, 0, 0, getWidth(), getHeight(), this);
	}

}
