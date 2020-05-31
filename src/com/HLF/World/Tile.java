package com.HLF.World;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.HLF.main.Game;

public class Tile {
	
	/*VARIAVEIS*/
	
	public static BufferedImage TileFloor = Game.spritesheet.getSprite(0, 16, 16, 16);
	public static BufferedImage TileWall = Game.spritesheet.getSprite(16, 16, 16, 16);
	public static BufferedImage TilePortal = Game.spritesheet.getSprite(0, 32, 16, 16);
	
	private BufferedImage sprite;
	private int x, y;
	
	/***/
	
	/*CONSTRUTOR*/
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	/***/
	
	/*METODOS*/

	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}
	
	/***/
	

	
}
