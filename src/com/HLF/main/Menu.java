package com.HLF.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Menu {
	
	public String[] options = {"Start Game", "Exit"};
	
	public int currentOption = 0, maxOption = (options.length)-1;

	public boolean up, down;
	
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
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(new Color(0,0,0, 100));
		g2.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.setColor(Color.red);
		g.setFont(new Font("arial", Font.BOLD, 56));
		g.drawString("Shuriken Strike", Game.WIDTH / 2 + 20, Game.HEIGHT / 2);
		
		//Menu options
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 36));
		g.drawString("Start Game", Game.WIDTH / 2 + 134, Game.HEIGHT / 2 + 100);
		g.drawString("Exit", Game.WIDTH / 2 + 189, Game.HEIGHT / 2 + 220);
		
		if(options[currentOption] == "Start Game") {
			g.drawString(">", Game.WIDTH / 2 + 80, Game.HEIGHT / 2 + 100);
		}
		
		else if(options[currentOption] == "Exit") {
			g.drawString(">", Game.WIDTH / 2 + 120, Game.HEIGHT / 2 + 220);
		}
		
	}
}
