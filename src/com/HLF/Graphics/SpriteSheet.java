package com.HLF.Graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	/*VARIAVEIS*/
	
	private BufferedImage spritesheet;
	
	/***/	
	
	/*CONSTRUTOR*/

	
	public SpriteSheet(String path) {
		
		try {
			spritesheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public BufferedImage getSprite(int x, int y, int width, int height) {
		return spritesheet.getSubimage(x, y, width, height);
	}
}
