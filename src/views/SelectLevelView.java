
package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Window;
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

import components.RoundedButton;
import components.RoundedPanel;
import utils.AppFont;
import utils.UIColors;

@SuppressWarnings("serial")
public class SelectLevelView extends JPanel {
	
	//la musica de fondo
	Clip musica;
	
	//gif de fondo
	Image backgroundGif;
	
	//cargar imagenes
	private static final ImageIcon EASY_ICON =
		    new ImageIcon(SelectLevelView.class.getResource("/images/Easy.png"));

	private static final ImageIcon MID_ICON =
	    new ImageIcon(SelectLevelView.class.getResource("/images/Mid.png"));

	private static final ImageIcon HARD_ICON =
	    new ImageIcon(SelectLevelView.class.getResource("/images/Hard.png"));

	private static final ImageIcon HOME_ICON =
	    new ImageIcon(SelectLevelView.class.getResource("/images/home.png"));
	
	public SelectLevelView() {
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
		    BorderFactory.createLineBorder(UIColors.SNAKE_GREEN, 3, true), 
		    BorderFactory.createEmptyBorder(25, 35, 25, 35) 
		));		

	    card.add(createTitle());
	    card.add(Box.createRigidArea(new Dimension(0, 20))); 
	    card.add(createButtons());	
	    	    
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
	    
		JLabel lblTitle1 = new JLabel("SELECCIONE");
		lblTitle1.setForeground(UIColors.SNAKE_GREEN);
		lblTitle1.setBorder(new EmptyBorder(10, 20, 0, 20)); 
		lblTitle1.setFont(AppFont.subtitle());
		lblTitle1.setAlignmentX(CENTER_ALIGNMENT);
		
		JLabel lblTitle2 = new JLabel("LA DIFICULTAD");
		lblTitle2.setForeground(UIColors.SNAKE_GREEN);
		lblTitle2.setBorder(new EmptyBorder(0, 20, 10, 20)); 
		lblTitle2.setFont(AppFont.subtitle());
		lblTitle2.setAlignmentX(CENTER_ALIGNMENT);

	    panel.add(lblTitle1);
	    panel.add(lblTitle2);
	    
	    return panel;
	}
	
	private JPanel createButtons() {
	    JPanel panelButton = new JPanel();
	    panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.Y_AXIS));
	    panelButton.setBorder(new EmptyBorder(5, 20, 10, 20));
	    panelButton.setOpaque(false);

	    JButton btnEasy = new RoundedButton("Facil", EASY_ICON);
	    JButton btnMid = new RoundedButton("Media", MID_ICON);
	    JButton btnHard = new RoundedButton("Dificil", HARD_ICON);
	    JButton btnHome = new RoundedButton("MENU", HOME_ICON);

	    styleButton(btnEasy, Color.GREEN);
	    styleButton(btnMid, Color.YELLOW);
	    styleButton(btnHard, Color.RED);
	    styleButton(btnHome, UIColors.SNAKE_GREEN);
	    
	    btnEasy.addActionListener(e -> startGame("FACIL"));
	    btnMid.addActionListener(e -> startGame("MEDIO"));
	    btnHard.addActionListener(e -> startGame("DIFICIL"));
	    btnHome.addActionListener(e -> {
	    	new HomeWindow();

	        Window window = SwingUtilities.getWindowAncestor(btnHome);
	        if (window != null) {
	        	if (musica != null) {
	        	    musica.stop();
	        	    musica.close();
	        	}
	            window.dispose();
	        }
	    });

	    panelButton.add(Box.createVerticalStrut(5));
	    panelButton.add(btnEasy);
	    panelButton.add(Box.createRigidArea(new Dimension(0, 10)));
	    panelButton.add(btnMid);
	    panelButton.add(Box.createRigidArea(new Dimension(0, 10)));
	    panelButton.add(btnHard);
	    panelButton.add(Box.createRigidArea(new Dimension(0, 10)));
	    panelButton.add(btnHome);
	    panelButton.add(Box.createVerticalStrut(5));

	    return panelButton;
	}
	
    private void startGame(String dificultad) {
        new GameWindow(dificultad);

        // cerrar ventana actual
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
        	if (musica != null) {
        	    musica.stop();
        	    musica.close();
        	}
            window.dispose();
        }
    }

	
	public void playMusic() {
	    try {
	        // Si ya hay música sonando, detenerla primero
	        if (musica != null && musica.isRunning()) {
	            musica.stop();
	            musica.close();
	        }

	        AudioInputStream audio = AudioSystem.getAudioInputStream(
        	    getClass().getResource("/music/oiia-cat-intro-play.wav")
        	);
	        
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

	private void styleButton(JButton btn, Color color) {
	    btn.setBackground(color);
	    btn.setForeground(UIColors.BUTTON_TEXT);
	    btn.setFont(AppFont.subtitle());
	    btn.setPreferredSize(new Dimension(240, 60));
	    btn.setMaximumSize(new Dimension(240, 60));
	    btn.setMinimumSize(new Dimension(240, 60));
	    btn.setAlignmentX(CENTER_ALIGNMENT);
	    btn.setBorder(new EmptyBorder(10, 20, 10, 20));
	}

}
