package com.HLF.Entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.HLF.World.Camera;
import com.HLF.main.Game;

public class Entity {
	
	/*VARIAVEIS*/
	public static BufferedImage Heart = Game.spritesheet.getSprite(96, 0, 16, 16);
	public static BufferedImage Cactus = Game.spritesheet.getSprite(128, 16, 16, 16);
	public static BufferedImage Shuriken = Game.spritesheet.getSprite(128, 0, 16, 16);
	public static BufferedImage ShurikenR = Game.spritesheet.getSprite(96, 48, 16, 16);
	public static BufferedImage ShurikenL = Game.spritesheet.getSprite(112, 48, 16, 16);
	public static BufferedImage ShurikenB = Game.spritesheet.getSprite(128, 48, 16, 16);
	public static BufferedImage ShurikenF = Game.spritesheet.getSprite(144, 48, 16, 16);
	public static BufferedImage MoveShuri = Game.spritesheet.getSprite(96, 64, 16, 16);
	public static BufferedImage StopedShuriR = Game.spritesheet.getSprite(96, 80, 16, 16);
	public static BufferedImage StopedShuriL = Game.spritesheet.getSprite(112, 80, 16, 16);
	public static BufferedImage StopedShuriU = Game.spritesheet.getSprite(144, 80, 16, 16);
	public static BufferedImage StopedShuriD = Game.spritesheet.getSprite(128, 80, 16, 16);
	public static BufferedImage enemyDamage = Game.spritesheet.getSprite(144, 16, 16, 16);
	
	
	protected double x;
	protected double y;
	protected double width;
	protected double height;
	private BufferedImage sprite;
	public int maskX, maskY, mWidth, mHeight;
	/***/

	/*CONSTRUTOR*/

	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskX = 0;
		this.maskY = 0;
		this.mWidth = width;
		this.mHeight = height;
		
	}
	/***/
	
	/*METODOS SETTERS*/
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public void setMask(int maskX, int maskY, int mWidth, int mHeight) {
		this.maskX = maskX;
		this.maskY = maskY;
		this.mWidth = mWidth;
		this.mHeight = mHeight;
	}
	
	/***/

	/*METODOS GETTERS*/

	public int getX() {
		return (int) this.x;
	}
	
	public int getY() {
		return (int) this.y;
	}
	
	public int getWidth() {
		return (int) this.width;
	}
	
	public int getHeight() {
		return (int) this.height;
	}
	
	/***/
	
	/*UPDATING OBJETOS*/
	public void update() {
		
	}
	/***/
	
	public static boolean isColliding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskX, e1.getY() + e1.maskY, e1.mWidth, e1.mHeight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskX, e2.getY() + e2.maskY, e2.mWidth, e2.mHeight);
		
		return e1Mask.intersects(e2Mask);
	}
	
	
	/*RENDERIZAR OBJETOS*/
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
	/***/



}
