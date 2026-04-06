package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import utils.AppFont;
import utils.UIColors;

@SuppressWarnings("serial")
public class GameView extends JPanel {

	// Dimensiones del área de juego
	static final int WIDTH = 600;
	static final int HEIGHT = 600;

	// Tamaño de cada celda del tablero (grid)
	static final int UNIT_SIZE = 25;

	// Timer que controla el ciclo del juego
	Timer timer;

	//cantidad de manzanas comidas(puntos)
	int puntos;
	
	//velocidad relacionada alos puntos
	int contadorVelocidad;
	
	//animacion glow de la manzana
	double animacion = 0;
	
	//la musica de fondo
	Clip musica;
	
	// Coordenadas de la cabeza de la serpiente
	int snakeX;
	int snakeY;
	
	// Coordenadas de la cabeza de la manzana
	int appleX;
	int appleY;

	// Dirección actual de movimiento de la serpiente
	// U = Up, D = Down, L = Left, R = Right
	char direction;

	// Lista enlazada que almacena todas las posiciones del cuerpo de la serpiente
	LinkedList<Point> snakeBody;
	
	// pausado o no
	boolean pausado = false;
	
	//gato jugador
	Image catImage;
	
	//fondo
	Image background;
	
	//gato de pausa
	Image pauseCat;
	
	//dificultad
	String dificultad = "MEDIO";
	
	HUDPanel hud;
	
	Random rand = new Random();
	
	private static final Image BACKGROUND =
	    new ImageIcon(GameView.class.getResource("/images/galaxy-game-bg.gif")).getImage();

	private static final Image CAT =
	    new ImageIcon(GameView.class.getResource("/images/snake-head.gif")).getImage();

	private static final Image PAUSE_CAT =
	    new ImageIcon(GameView.class.getResource("/images/pause-cat.gif")).getImage();

	public GameView() {
		// Posición inicial de la cabeza de la serpiente
		snakeX = 250;
		snakeY = 100;

		// Dirección inicial de movimiento
		direction = 'R';

		// Crear la lista que almacenará el cuerpo de la serpiente
		snakeBody = new LinkedList<Point>();
		createSnake();
		
		//CREAR la manzana
		createApple();
		
		//Sonido de fondo
		playMusic();
		
		//fondo
		background = BACKGROUND;	
		
		//gato jugador
		catImage = CAT;
		
		//gato de pausa
		pauseCat = PAUSE_CAT;
		
		// Configurar tamaño del panel
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		// Permitir que el panel reciba eventos de teclado
		this.setFocusable(true);

		// Evita que las teclas de navegación (como el TAB) cambien el foco entre componentes
		setFocusTraversalKeysEnabled(false);

		// Solicitar el foco cuando el panel ya esté visible
		SwingUtilities.invokeLater(() -> requestFocusInWindow());
		
		// Listener para detectar las teclas presionadas
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				// Cambiar la dirección dependiendo de la tecla presionada
				switch (e.getKeyCode()) 
				{
				case KeyEvent.VK_LEFT:
					if(direction != 'R') {
						direction = 'L';
					}
					break;

				case KeyEvent.VK_RIGHT:
					if(direction != 'L') {
						direction = 'R';
					}
					break;

				case KeyEvent.VK_UP:
					if(direction != 'D') {
						direction = 'U';
					}
					break;

				case KeyEvent.VK_DOWN:
					if(direction != 'U') {
						direction = 'D';
					}
					break;
					
				case KeyEvent.VK_R: //reiniciar
				    restartGame();
				    break;
				
				case KeyEvent.VK_P: //pausar
				    if (pausado) {
				        resumeGame();
				    } else {
				        pauseGame();
				    }
				    break;
				}
			}
		});	
		
		// pausa cuando se salga de la ventana
		this.addFocusListener(new FocusAdapter() {
		    public void focusLost(FocusEvent e) {
				pauseGame();

		    }
		});

		// Timer que ejecuta el ciclo del juego cada 150 ms
		timer = new Timer(150, e -> 
		{
			move(); // Actualiza la posición de la serpiente
			
			//animacion del glow de la manzana
		    animacion += 0.02;
			
		    if (hud != null) {
		        hud.setPuntos(puntos * 100);
		    }
		    
			for(int i = 1; i < snakeBody.size(); i++) 
			{
				if(snakeX == snakeBody.get(i).getX() && snakeY == snakeBody.get(i).getY()) 
				{
					gameOver();
				}
			}
			
			if(snakeX < 0 || snakeX >= WIDTH || snakeY < 0 || snakeY >= HEIGHT){
				gameOver();
			}
			
			comerManzana();
			repaint();  // Redibuja el panel
		});

		timer.start(); //Inicia el timer
	}

	// Método encargado de dibujar los elementos del juego
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    
	    Graphics2D g2 = (Graphics2D) g;

	    g2.setRenderingHint(
	        RenderingHints.KEY_TEXT_ANTIALIASING,
	        RenderingHints.VALUE_TEXT_ANTIALIAS_ON
	    );

	    g2.setRenderingHint(
	        RenderingHints.KEY_ANTIALIASING,
	        RenderingHints.VALUE_ANTIALIAS_ON
	    );
	    
	    // fondo
	    g2.drawImage(background, 0, 0, getWidth(), getHeight(), this);
	    
	    // overlay oscuro 
	    g2.setColor(new Color(0, 0, 0, 120));
	    g2.fillRect(0, 0, getWidth(), getHeight());
	    
	    // Grid neon 
	    g2.setColor(new Color(0, 255, 100, 40));
	    
	    // Líneas verticales
	    for (int i = 0; i < WIDTH / UNIT_SIZE; i++) {
	        g2.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, HEIGHT);
	    }

	    //horizontales
	    for (int i = 0; i < HEIGHT / UNIT_SIZE; i++) {
	        g2.drawLine(0, i * UNIT_SIZE, WIDTH, i * UNIT_SIZE);
	    }

		// Recorrer todas las partes de la serpiente
	    for (int i = 0; i < snakeBody.size(); i++) {

	        int x = snakeBody.get(i).x;
	        int y = snakeBody.get(i).y;

	        //capa externa brillosa
	        g2.setColor(new Color(0, 255, 100, 40));
	        g2.fillRect(x - 4, y - 4, UNIT_SIZE + 8, UNIT_SIZE + 8);

	        // CUERPO
	        if (i == 0) {
	            g2.drawImage(catImage, x, y, UNIT_SIZE, UNIT_SIZE, this);
	        } else {
	            // Degradado en el cuerpo
	            int intensidad = Math.max(100, 255 - (i * 10));
	            g2.setColor(new Color(0, intensidad, 100));
	        }

	        g2.fillRect(x, y, UNIT_SIZE, UNIT_SIZE);
	    }
	    
	    //manzana
	    int x = appleX;
	    int y = appleY;

	    // Color arcoiris 
	    float tonoArcoiris  = (float)(animacion % 1.0); //Un valor entre 0 y 1
	    Color colorArcoiris = Color.getHSBColor(tonoArcoiris , 1.0f, 1.0f);

	    // Glow externo
	    g2.setColor(new Color(colorArcoiris.getRed(), colorArcoiris.getGreen(), colorArcoiris.getBlue(), 40));
	    g2.fillOval(x - 6, y - 6, UNIT_SIZE + 12, UNIT_SIZE + 12);

	    // glow medio
	    g2.setColor(new Color(colorArcoiris.getRed(), colorArcoiris.getGreen(), colorArcoiris.getBlue(), 80));
	    g2.fillOval(x - 3, y - 3, UNIT_SIZE + 6, UNIT_SIZE + 6);

	    // centro
	    g2.setColor(colorArcoiris);
	    g2.fillOval(x, y, UNIT_SIZE, UNIT_SIZE);
	    
	    //borde verde
	    g2.setColor(UIColors.SNAKE_GREEN);
	    g2.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
	    
	    //texto pausa
	    if (pausado) {
	        g2.setColor(new Color(0, 0, 0, 150));
	        g2.fillRect(0, 0, WIDTH, HEIGHT);

	        g2.setColor(UIColors.SNAKE_GREEN);
	        g2.setFont(AppFont.title());

	        String texto = "PAUSA";
	        int anchoTexto = g2.getFontMetrics().stringWidth(texto);
	        g2.drawString(texto, (WIDTH - anchoTexto) / 2, (HEIGHT / 2) - 100 );
	        
	        //gato de pausa
	        g2.drawImage(pauseCat, WIDTH/2 - 50, HEIGHT/2 - 40, 100, 100, this);
	        
	        // texto ayuda
	        g2.setFont(AppFont.big());
	        g2.setColor(UIColors.SNAKE_GREEN);

	        String ayuda = "Presiona P para reanudar";
	        int anchoAyuda = g2.getFontMetrics().stringWidth(ayuda);

	        int xTexto = (WIDTH - anchoAyuda) / 2;
	        int yTexto = HEIGHT/2 + 120;

	        g2.drawString(ayuda, xTexto, yTexto);
	    }
	}

	// Método que actualiza la posición de la serpiente
	public void move() {

		// Cambiar la posición de la cabeza dependiendo de la dirección
		switch (direction) {
		case 'U':
			snakeY -= UNIT_SIZE;
			break;

		case 'D':
			snakeY += UNIT_SIZE;
			break;

		case 'L':
			snakeX -= UNIT_SIZE;
			break;

		case 'R':
			snakeX += UNIT_SIZE;
			break;
		}

		// Agregar una nueva cabeza en la posición actual
		snakeBody.addFirst(new Point(snakeX, snakeY));

		// Eliminar el ultimo elemento para mantener el mismo tamaño
		snakeBody.removeLast();
	}
	
	//método para crear la manzana
	public void createApple() 
	{
	    boolean posicionValida;

	    //repetir hasta encontrar una posición que no esté ocupada por la serpiente
	    do {
	        posicionValida = true;

	        // Generar coordenadas aleatorias dentro del grid
	        appleX = (rand.nextInt(WIDTH / UNIT_SIZE) * UNIT_SIZE);
	        appleY = (rand.nextInt(HEIGHT / UNIT_SIZE) * UNIT_SIZE);

	        //checar que la manzana no aparezca encima de la serpiente
	        for (Point punto : snakeBody) {
	            if (punto.x == appleX && punto.y == appleY) {
	                posicionValida = false;
	                break;
	            }
	        }
	    } while (!posicionValida); // Reintentar si la posicion no es valida
	}
	
	//metodo de comer la manzana
	public void comerManzana() 
	{
	    // Si la cabeza de la serpiente coincide con la posición de la manzana
		if(snakeX == appleX && snakeY == appleY) 
		{
			createApple();
			snakeBody.addLast(new Point(snakeX,snakeY));
			puntos++;
			contadorVelocidad++;

			int aumento;

			switch (dificultad) {
			    case "FACIL":
			        aumento = 5;
			        break;
			    case "MEDIO":
			        aumento = 3;
			        break;
			    case "DIFICIL":
			        aumento = 2;
			        break;
			    default:
			        aumento = 3;
			}

			if (contadorVelocidad == aumento) {
				
				int nuevoDelay = Math.max(40, timer.getDelay() - 10);
				timer.setDelay(nuevoDelay);
			    
				contadorVelocidad = 0;
			}
			
			playSoundEffect("src/music/eat.wav");
		}
	}
	
	//música de fondo
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
	
	//sonidos de una sola vez
	public void playSoundEffect(String ruta) {
	    try {
	    	AudioInputStream audio = AudioSystem.getAudioInputStream(
    		    getClass().getResource("/music/eat.wav")
    		);
	    	
	    	Clip clip = AudioSystem.getClip();
	        clip.open(audio);
	        clip.start();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	//perder
	public void gameOver() {
	    timer.stop();

	    if (musica != null) {
	        musica.stop();
	        musica.close();
	    }

	    // Pasar puntos al GameOver
	    new GameOverWindow(puntos * 100);

	    // cerrar ventana actual
	    Window window = SwingUtilities.getWindowAncestor(this);
	    if (window != null) {
	        window.dispose();
	    }
	}
	
	//Reiniciar el juego
	public void restartGame() {
		resumeGame();
		snakeX = 250;
	    snakeY = 100;
	    direction = 'R';

	    createSnake();

	    puntos = 0;
	    contadorVelocidad = 0;
	    
	    timer.setDelay(150);
	    createApple();
	    playMusic();
	    
	    setDifficulty(dificultad);
	}
	
	public void pauseGame() {
	    if (!pausado) {
	        timer.stop();
	        pausado = true;
	        repaint();
	    }
	}

	public void resumeGame() {
	    if (pausado) {
	        timer.start();
	        pausado = false;
	        repaint();
	    }
	}
	
	private void createSnake() {
	    snakeBody.clear();
		// Agregar los primeros segmentos de la serpiente
	    snakeBody.add(new Point(250, 100));
	    snakeBody.add(new Point(225, 100));
	    snakeBody.add(new Point(200, 100));
	}
	
	public void setHUD(HUDPanel hud) {
	    this.hud = hud;
	}
	
	public void setDifficulty(String level) {
	    this.dificultad = level;

	    switch (level) {
	        case "FACIL":
	            timer.setDelay(150);
	            break;

	        case "MEDIO":
	            timer.setDelay(100);
	            break;

	        case "DIFICIL":
	            timer.setDelay(70);
	            break;
	    }
	    
	    if (hud != null) {
	        hud.setDifficulty(level);
	    }
	}

}