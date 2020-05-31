package com.HLF.Entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.HLF.World.Camera;
import com.HLF.World.World;
import com.HLF.main.Game;

public class ShurikenThrow extends Entity{
	
	private int dx, dy;
	private double spd = 3;
	
	private int lf = 24, curlf = 0;
	
	public static boolean wall, ground;
	public boolean stopShuri;
	
	public ShurikenThrow(int x, int y, int width, int height, BufferedImage sprite, int dx, int dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
	}

	public void update() {
		
		move();
		stopShuri();
	
	}
	
	public void render(Graphics g) {
		
		if(!wall && !ground) {
			g.drawImage(MoveShuri, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		
		
		else if(ground && !wall)
			g.drawImage(Shuriken, this.getX() - Camera.x, this.getY() - Camera.y, null);
		
		else {	

			if(dx == 1 )
				g.drawImage(StopedShuriR, this.getX() - Camera.x, this.getY() - Camera.y, null);
			else if(dx == -1)
				g.drawImage(StopedShuriL, this.getX() - Camera.x, this.getY() - Camera.y, null);
			else if(dy == -1)
				g.drawImage(StopedShuriU, this.getX() - Camera.x, this.getY() - Camera.y, null);
			else if(dy == 1)
				g.drawImage(StopedShuriD, this.getX() - Camera.x, this.getY() - Camera.y, null);}
	}
	
	public void move() {
		
		if((World.isFree((int)(x+spd), this.getY()) || World.isFree(this.getX(), (int)(y+spd))) && !stopShuri) {
			
			x+=dx*spd;
			y+=dy*spd;
			wall = false;
			ground = false;
			Game.player.removeShuri = false;
		}
		
		else {
			if(!(World.isFree((int)(x+spd), this.getY()) || World.isFree(this.getX(), (int)(y+spd))))
			wall = true;
			ground = true;
			ShurikenWall shuriw = new ShurikenWall(this.getX(), this.getY(), 16, 16, null);
			Game.shuriw.add(shuriw);
			if(Game.player.removeShuri)
				Game.shuri.remove(this);
	
		}
	}
	
	public void stopShuri() {
		curlf++;
		if(curlf == lf) {
			stopShuri = true;
			return;
		}
	}
	
}
