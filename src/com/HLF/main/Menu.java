package com.HLF.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Menu {
	
	public String[] options = {"Start Game", "How to play", "Exit", "Back"};
	
	public int currentOption = 0, maxOption = (options.length)-1;

	public boolean up, down, enter;
	public boolean pause = false;
	public boolean tutorial = false;
	
	public void update() {
		
		if(up) {
			up = false;
			currentOption--;
			if(currentOption < 0)
				currentOption = maxOption;
		}
		
		if(down) {
			down = false;
			currentOption++;
			if(currentOption > maxOption)
				currentOption = 0;
		}
		
		if(enter) {
			Sound.backGround.loop();
			enter = false;
			
			if(options[currentOption] == "Start Game" || options[currentOption] == "Resume") {
				Game.GameState = "Normal";
				pause = false;
				tutorial = false;
			}
			
			else if(options[currentOption] == "How to play") {
				tutorial = true;
			}
			
			else if(options[currentOption] == "Exit") {
				System.exit(1);
			}
			
			if(options[currentOption] == "Back") {
				tutorial = false;
				currentOption = 0;
			}
		}
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(new Color(0,0,0, 100));
		g2.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		
		if(!tutorial) {
			
			g.setColor(Color.red);
			g.setFont(new Font("arial", Font.BOLD, 56));
			g.drawString("Shuriken Strike", Game.WIDTH / 2 + 20, Game.HEIGHT / 2);
			
			//Menu options
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 36));
			
			
			if(!pause)
				g.drawString("Start Game", Game.WIDTH / 2 + 134, Game.HEIGHT / 2 + 100);
			
			else
				g.drawString("Resume", Game.WIDTH / 2 + 154, Game.HEIGHT / 2 + 100);
			
			g.drawString("How to play", Game.WIDTH / 2 + 126, Game.HEIGHT / 2 + 220);
			
			g.drawString("Exit", Game.WIDTH / 2 + 189, Game.HEIGHT / 2 + 340);
		
		
			if(options[currentOption] == "Start Game") {
				g.drawString(">", Game.WIDTH / 2 + 80, Game.HEIGHT / 2 + 100);
			}
			
			else if(options[currentOption] == "How to play") {
				g.drawString(">", Game.WIDTH / 2 + 80, Game.HEIGHT / 2 + 220);
			}
			
			else if(options[currentOption] == "Exit") {
				g.drawString(">", Game.WIDTH / 2 + 120, Game.HEIGHT / 2 + 340);
			}
		}
		
		if(tutorial) {
			currentOption = 3 ;
			g.setColor(Color.BLACK);
			g.setFont(new Font("arial", Font.BOLD, 26));
			g.drawString("W,A,S,D - MOVE", Game.WIDTH /2 - 100, Game.HEIGHT / 2 + 35);
			g.drawString("ENTER - SELECT OPTIONS", Game.WIDTH /2 - 100, Game.HEIGHT / 2 + 195);
			g.drawString("SPACE - THROW SHURIKEN", Game.WIDTH / 2 -100, Game.HEIGHT / 2 + 115);
			g.drawString("ESC - PAUSE", Game.WIDTH / 2 - 100, Game.HEIGHT / 2 + 275);
			g.drawString("Goal: ", Game.WIDTH / 2 + 280, Game.HEIGHT / 2 + -20);
			g.drawString("Kill all the enemies and", Game.WIDTH / 2 + 280, Game.HEIGHT / 2 + 10);
			g.drawString("step into the portal to", Game.WIDTH / 2 + 280, Game.HEIGHT / 2 + 40);
			g.drawString("advance to next the stage", Game.WIDTH / 2 + 280, Game.HEIGHT / 2 + 70);

			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 36));
			g.drawString("Back", Game.WIDTH / 2 + 460, Game.HEIGHT / 2 + 370);
			
			if(options[currentOption] == "Back") {
				g.drawString(">", Game.WIDTH / 2 + 389, Game.HEIGHT / 2 + 370);
			}
		}
	}
}
