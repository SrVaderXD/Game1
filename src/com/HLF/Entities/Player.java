package com.HLF.Entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.HLF.World.Camera;
import com.HLF.World.World;
import com.HLF.main.Game;

public class Player extends Entity{
	
	/*CONSTANTES*/
	
	public boolean right, left, up, down;
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	public int dir = right_dir;
	public double speed = 0.8;
	
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private boolean moved = false;
	private BufferedImage rightPlayer[]; // Sprites para a direção do player
	private BufferedImage leftPlayer[];
	private BufferedImage upPlayer[];
	private BufferedImage downPlayer[];
	
	private BufferedImage playerDamage;
	
	public static int ammo;
	
	private boolean weapon = false;
	
	public boolean damage = false;
	private int damageFrames = 0;
	
	public double life = 8, maxLife = 8;
	
	public boolean shuriT = false;
	
	public boolean removeShuri;
	
	public boolean inPortal = false;

	
	/***/
	
	/*CONSTRUTOR*/

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		
		playerDamage = Game.spritesheet.getSprite(112, 16, 16, 16);
		
		for(int i = 0; i < 4; i++) { // right
			rightPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 0, 16, 16);
		}
		
		for(int i = 0; i < 4; i++) { // left
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 16, 16, 16);
		}
		
		for(int i = 0; i < 4; i++) { // up
			upPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 48, 16, 16);
		}
		
		for(int i = 0; i < 4; i++) { // down
			downPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 32, 16, 16);
		}
		

	}
	
	/***/
	
	/*METODOS*/
	
	public void update() {
		
		if(life <= 0) {
			life = 0;
			Game.GameState = "End";
		}
		
		moved = false;
		
		if(right && World.isFree((int)(x + speed), this.getY())) {
			moved = true;
			dir = right_dir;
			x += speed;
		}
		
		else if(left && World.isFree((int)(x - speed), this.getY())) {
			moved = true;
			dir = left_dir;
			x -= speed;
		}
		
		if(up && World.isFree(this.getX(), (int)(y - speed))) {
			moved = true;
			dir = up_dir;
			y -= speed;
		}
		
		else if(down && World.isFree(this.getX(), (int)(y + speed))) {
			moved = true;
			dir = down_dir;
			y += speed;
		}
		
		if(noEnemies())
			checkCollisionPortal();
		
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) 
					index = 0;
			}
		}
		
		checkCollisionShuriken();
		checkCollisionShuriken1();
		checkCollisionHeart();
		
		if(damage) {
			this.damageFrames ++;
			if(this.damageFrames == 15) {
				this.damageFrames = 0;
				damage = false;
			}
		}
		
		if(shuriT) {
			shuriT = false;
			
			if(weapon && ammo>0) {
				ammo--;
				
				if(ammo == 0)
					weapon = false;
				
				int dx = 0, dy = 0, px = 0, py = 0;
				
				if(dir == right_dir) {
					dx = 1;
					px = 4;
				} else if(dir == left_dir) {
					dx = -1;
				}
				
				if(dir == up_dir) {
					dy = -1;
				} else if(dir == down_dir) {
					dy = 1;
				}
				
				ShurikenThrow shuri = new ShurikenThrow(this.getX() + px, this.getY() + py, 16, 16, null, dx, dy);
				Game.shuri.add(shuri);
			}
		}
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT*16 - Game.HEIGHT);
	}
	
	public void checkCollisionShuriken() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Shuriken) {
				if(Entity.isColliding(this, atual)) {
					ammo+=1;
					weapon = true;
					Game.entities.remove(i);
				}
			}
		}
	}
	
	
	public void checkCollisionShuriken1() {
		
		for(int i = 0; i < Game.shuriw.size(); i++) {
			Entity atual = Game.shuriw.get(i);
			if(atual instanceof ShurikenWall) {
				if(Entity.isColliding(this, atual)) {
					ammo+=1;
					if(ammo > 1)
						ammo = 1;
					weapon = true;
					removeShuri = true;
					Game.shuriw.clear();
				}
			}
		}
	}
	
	public void checkCollisionHeart() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Heart) {
				if(Entity.isColliding(this, atual)) {
					life+=2;
					if(life > 8)
						life = 8;
					Game.entities.remove(i);
				}
			}
		}
	}
	
	public void checkCollisionPortal() {
		if(!(World.inPortal((int)(x + speed), this.getY())) || 
				!(World.inPortal((int)(x - speed), this.getY())) || 
				!(World.inPortal(this.getX(), (int)(y - speed))) || 
				!(World.inPortal(this.getX(), (int)(y + speed))))
			inPortal = true;
	}
	
	public boolean noEnemies() {
		if(Game.enemies.size() == 0)
			return true;
		
		return false;
	}
	
	
	public void render(Graphics g) {
		if(!damage) {
			if(dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				
				if(weapon) {
					g.drawImage(Entity.ShurikenR, this.getX() - 4 - Camera.x, this.getY() + 3 - Camera.y, null);
				}
				
			} else if(dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				
				if(weapon) {
					g.drawImage(Entity.ShurikenL, this.getX() + 4 - Camera.x, this.getY() + 3 - Camera.y, null);
				}
				
			} else if(dir == up_dir) {
				g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				
				if(weapon) {
					g.drawImage(Entity.ShurikenF, this.getX() + 4 - Camera.x, this.getY() + 3 - Camera.y, null);
				}
				
			} else if(dir == down_dir) {
				g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				
				if(weapon) {
					g.drawImage(Entity.ShurikenB, this.getX() + 4 - Camera.x, this.getY() + 3 - Camera.y, null);
				}
				
			} 
		}
		
		else {
			g.drawImage(playerDamage, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}
	
	/***/

}
