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
import utils.UIText;
import utils.AppFont;
import utils.UIColors;

@SuppressWarnings("serial")
public class HomeView extends JPanel {
	
	//la musica de fondo
	Clip musica;
	
	//gif de fondo
	Image backgroundGif;
	
	public HomeView() {
		setLayout(new GridBagLayout());
		this.setOpaque(false);
		
		backgroundGif = new ImageIcon(
		    getClass().getResource("/images/galaxy-intro-bg.gif")
		).getImage();
		
		playMusic();

	    initializeComponents();
	}
	
	private void initializeComponents() {
		RoundedPanel card = new RoundedPanel(50);
		card.setOpaque(false); 
		card.setBackground(new Color(0, 0, 0, 100)); 
		card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
		card.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));
	    card.putClientProperty("FlatLaf.style", "arc:20");

		card.setBorder(BorderFactory.createCompoundBorder(
		    BorderFactory.createLineBorder(UIColors.BORDER_GREEN, 3, true), 
		    BorderFactory.createEmptyBorder(25, 35, 25, 35) 
		));		

	    card.add(createTitle());
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
	    
		JLabel lblTitle1 = new JLabel("SNAKE");
		JLabel lblTitle2 = new JLabel("GAME");
		lblTitle1.setBorder(new EmptyBorder(30, 20, 5, 20)); 
		lblTitle2.setBorder(new EmptyBorder(5, 20, 20, 20)); 
		lblTitle1.setFont(AppFont.title());
		lblTitle2.setFont(AppFont.title());
		lblTitle1.setForeground(UIColors.SNAKE_GREEN);
		lblTitle2.setForeground(UIColors.SNAKE_GREEN);
		lblTitle1.setAlignmentX(CENTER_ALIGNMENT);
		lblTitle2.setAlignmentX(CENTER_ALIGNMENT);

	    panel.add(lblTitle1);
	    panel.add(lblTitle2);
	    
	    return panel;
	}
	
	private JPanel createCat() {
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setOpaque(false);

	    ImageIcon gif = new ImageIcon(
	        getClass().getResource("/images/cat.gif")
	    );

	    JLabel lblCat = new JLabel(gif);
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
	    btnPlay.setToolTipText("Haz clic para jugar!");

	    btnPlay.setFont(AppFont.subtitle());

	    btnPlay.setPreferredSize(new Dimension(180, 60));
	    btnPlay.setMaximumSize(new Dimension(180, 60));
	    btnPlay.setMinimumSize(new Dimension(180, 60));

	    btnPlay.setAlignmentX(CENTER_ALIGNMENT);
	    btnPlay.setBorder(new EmptyBorder(10, 20, 10, 20));
	    
	    btnPlay.addActionListener(e -> {
	        new GameWindow();

	        Window window = SwingUtilities.getWindowAncestor(btnPlay);
	        if (window != null) {
	            musica.close();
	            window.dispose();
	        }
	    });

	    panelButton.add(btnPlay);

	    return panelButton;
	}
	
	public void playMusic() {
	    try {
	        // Si ya hay música sonando, detenerla primero
	        if (musica != null && musica.isRunning()) {
	            musica.stop();
	            musica.close();
	        }

	        File archivo = new File("src/music/oiia-cat-intro-play.wav");
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
