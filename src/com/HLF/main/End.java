package com.HLF.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class End {
	
	public String[] options = {"Play again", "Exit"};
	
	public int currentOption = 0, maxOption = (options.length)-1;

	public boolean up, down, enter;
	
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
			enter = false;
			
			if(options[currentOption] == "Play again") {
				Game.restartMap = true;
			}
			
			else if(options[currentOption] == "Exit") {
				System.exit(1);
			}
		}
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(new Color(0,0,0, 100));
		g2.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		
			
		g.setColor(Color.black);
		g.setFont(new Font("arial", Font.BOLD, 40));
		g.drawString("The end", Game.WIDTH / 2 + 165, Game.HEIGHT / 2 - 20);
		g.drawString("Thank you for playing my first game", Game.WIDTH / 2 -100, Game.HEIGHT / 2+ 50);
			
		//Menu options
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 36));
		
		g.drawString("Play again", Game.WIDTH / 2 + 160, Game.HEIGHT / 2 + 120);
		g.drawString("Exit", Game.WIDTH / 2 + 198, Game.HEIGHT / 2 + 240);
		
		if(options[currentOption] == "Play again") {
			g.drawString(">", Game.WIDTH / 2 + 80, Game.HEIGHT / 2 + 120);
		}
			
		else if(options[currentOption] == "Exit") {
			g.drawString(">", Game.WIDTH / 2 + 120, Game.HEIGHT / 2 + 240);
		}
	}
}
