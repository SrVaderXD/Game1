package com.HLF.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import com.HLF.Entities.Enemy;
import com.HLF.Entities.Entity;
import com.HLF.Entities.Player;
import com.HLF.Entities.ShurikenThrow;
import com.HLF.Entities.ShurikenWall;
import com.HLF.Graphics.SpriteSheet;
import com.HLF.Graphics.UI;
import com.HLF.World.World;
import java.util.*;



public class Game extends Canvas implements Runnable, KeyListener {
	
	/*CONSTANTES*/

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 3;
	
	private int currentLevel = 1, maxLevel = 4;
	
	private BufferedImage image;
	
	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static List<ShurikenThrow> shuri;
	public static List<ShurikenWall> shuriw;
	
	public static SpriteSheet spritesheet;
	
	public static Player player;
	
	public static World world;
	
	public static UI ui;
	
	public static String GameState = "Menu";
	private boolean GameOver, NextLevel;
	private int framesGameOver = 0, framesNextLevel = 0;
	public static boolean restartMap = false;
	
	public Menu menu;
	
	public End end;
	
	public static Random rand;
	
	/***/
	
	/*CONSTRUTOR*/
	
	public Game() {
		Sound.backGround.loop();
		rand = new Random();
		addKeyListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		//Inicializando objetos
		ui = new UI();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		shuri = new ArrayList<ShurikenThrow>();
		shuriw = new ArrayList<ShurikenWall>();
		spritesheet = new SpriteSheet("/spritesheet.png");
		menu = new Menu();
		end = new End();
		
		//Inicializando o player
		player = new Player(0, 0, 16, 16, spritesheet.getSprite(80, 16, 16, 16));
		entities.add(player);
		world = new World("/map1.png");

	}
	
	/***/
	
	/*METODOS*/
	
	public void initFrame() {
		frame = new JFrame("Game #1");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		
		try {
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public void update() {
		
		if(GameState == "Normal" || GameState == "StageClear") {
			restartMap = false;
			
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.update();
			}
			
			for(int i = 0; i < shuri.size(); i++)
				shuri.get(i).update();
			
			if(player.noEnemies()) {
				framesNextLevel++;
				if(framesNextLevel == 30) {
					framesNextLevel = 0;
					if(NextLevel)
						NextLevel = false;
					else
						NextLevel = true;
				}
			}
			
			nextLevel();
			
			if(currentLevel == maxLevel) {
				GameState = "TheEnd";
				currentLevel = 1;
			}
		
		} 
		
		else if(GameState == "End") {
			entities.clear();
			framesGameOver++;
			if(framesGameOver == 30) {
				framesGameOver = 0;
				if(GameOver)
					GameOver = false;
				else
					GameOver = true;
			}
		
			if(restartMap) {
				restartMap = false;
				GameState = "Normal";
				String map = "map"+currentLevel+".png";
				World.restart(map);
			}
		}
		
		else if(GameState == "Menu") {
			menu.update();
		}
		
		else if(GameState == "TheEnd") {
			entities.clear();
			end.update();
			
			if(restartMap) {
				restartMap = false;
				GameState = "Normal";
				String map = "map"+currentLevel+".png";
				World.restart(map);
			}
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		g.setColor(new Color(0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		world.render(g);
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.update();
			e.render(g);
		}
		
		for(int i = 0; i < shuri.size(); i++) {
			shuri.get(i).render(g);
		}
		
		if(GameState == "Normal" || GameState == "StageClear")
			ui.render(g);
		
		/***/
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		
		g.setFont(new Font("arial", Font.BOLD, 36));
		g.setColor(Color.black);
		
		if(GameState == "End") {
			Graphics2D g2 = (Graphics2D)g;
			g2.setColor(new Color(0,0,0,100));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g.setFont(new Font("arial", Font.BOLD, 72));
			g.setColor(Color.white);
			g.drawString("GAME OVER", WIDTH/2 + 8, HEIGHT/2 + 130);
			g.setFont(new Font("arial", Font.BOLD, 36));
			g.setColor(Color.white);
			if(GameOver)
				g.drawString("Press 'R' to restart", WIDTH/2 + 72, HEIGHT/2 + 170);
		}
		
		else if(GameState == "Menu") {
			menu.render(g);
		}
		
		else if(GameState == "TheEnd") {
			entities.clear();
			end.render(g);
		}
		
		if(GameState == "StageClear") {
			g.drawString("STAGE CLEAR", WIDTH/2 + 94, HEIGHT/2 + 90);
			if(NextLevel)
				g.setColor(new Color(91, 0, 137));
				g.drawString("Go to the Portal", WIDTH/2 + 90, HEIGHT/2 + 140);
		}
			
		bs.show();
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfUP = 60.0;
		double ns = 1000000000 / amountOfUP;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if(delta >= 1) {
				update();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
		}
		
		stop();
		

	}

	public void keyPressed(KeyEvent k) {
		if(k.getKeyCode() == KeyEvent.VK_D) {
			if(GameState == "Normal" || GameState == "StageClear")
				player.right = true;
		} 
		
		else if(k.getKeyCode() == KeyEvent.VK_A) {
			if(GameState == "Normal" || GameState == "StageClear")
				player.left = true;
		}
		
		if(k.getKeyCode() == KeyEvent.VK_W) {
			if(GameState == "Normal" || GameState == "StageClear")
				player.up = true;
			
			if(GameState == "Menu")
				menu.up = true;
			
			if(GameState == "TheEnd")
				end.up = true;
		} 
		
		else if(k.getKeyCode() == KeyEvent.VK_S) {
			if(GameState == "Normal" || GameState == "StageClear")
				player.down = true;
			
			if(GameState == "Menu")
				menu.down = true;
			
			if(GameState == "TheEnd")
				end.down = true;
		}
		
		if(k.getKeyCode() == KeyEvent.VK_SPACE) {
			if(GameState == "Normal" || GameState == "StageClear")
				player.shuriT = true;
		}
		
		if(k.getKeyCode() == KeyEvent.VK_R) {
				restartMap = true;
		}
		
		if(k.getKeyCode() == KeyEvent.VK_ENTER) {
			if(GameState == "Menu")
				menu.enter = true;
			
			if(GameState == "TheEnd")
				end.enter = true;
		}
		
		if(k.getKeyCode() == KeyEvent.VK_ESCAPE){
			if(GameState == "Normal" || GameState == "StageClear") {
				GameState = "Menu";
				menu.pause = true;
			}
		}
	}

	public void keyReleased(KeyEvent k) {
		if(k.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		} else if(k.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
		
		if(k.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
		} else if(k.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		}
	}

	public void keyTyped(KeyEvent k) {
		
	}
	
	public void nextLevel() {
		if(player.noEnemies() && player.inPortal) {
			currentLevel++;
			GameState = "Normal";
			
			String map = "map"+currentLevel+".png";
			World.restart(map);		
				
		}
	}
	

	
		/***/
}
