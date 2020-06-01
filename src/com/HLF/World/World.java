package com.HLF.World;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


import com.HLF.Entities.*;
import com.HLF.Graphics.SpriteSheet;
import com.HLF.main.Game;

public class World {

	/*CONSTRUTOR*/
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;
	public static int mapSize;
	
	/***/
	
	/*CONSTRUTOR*/
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			mapSize = map.getWidth()*map.getHeight();
			int[] pixels = new int[mapSize];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth()*map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight(); yy++) {
					int pixelAtual = pixels[xx + (yy*map.getWidth())];
					
					tiles[xx + (yy * WIDTH)] = new FloorTile(TILE_SIZE*xx, TILE_SIZE*yy, Tile.TileFloor);
					if(pixelAtual == 0xFF000000){
						// Floor
						tiles[xx + (yy * WIDTH)] = new FloorTile(TILE_SIZE*xx, TILE_SIZE*yy, Tile.TileFloor);
					} else if(pixelAtual == 0xFFFFFFFF) {
						// Walls
						tiles[xx + (yy * WIDTH)] = new WallTile(TILE_SIZE*xx, TILE_SIZE*yy, Tile.TileWall);
						
					} else if(pixelAtual == 0xFF5B0089) {
						// Walls
						tiles[xx + (yy * WIDTH)] = new PortalTile(TILE_SIZE*xx, TILE_SIZE*yy, Tile.TilePortal);
					} else if(pixelAtual == 0xFF0026FF) {
						// Player
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
					} else if(pixelAtual == 0xFFFF0000) {
						// Enemy Cactus
						Enemy en = new Enemy(xx*TILE_SIZE, yy*TILE_SIZE, 16, 16, 0.4, 35, 32, 64,
								128, 16, 0.5);
						Game.entities.add(en);
						Game.enemies.add(en);
						
					} else if(pixelAtual == 0xFFFF6A00) {
						// Enemy Sand Monster
						Enemy en = new Enemy(xx*TILE_SIZE, yy*TILE_SIZE, 16, 16, 0.5, 17, 32, 80,
								144, 16, 0.7);
						Game.entities.add(en);
						Game.enemies.add(en);
						
					} else if(pixelAtual == 0xFFFFD800) {
						// Enemy Muscle Cactus
						Enemy en = new Enemy(xx*TILE_SIZE, yy*TILE_SIZE, 16, 16, 0.6, 70, 32, 96,
									144, 0, 1.0);
						Game.entities.add(en);
						Game.enemies.add(en);
						
					} else if(pixelAtual == 0xFF404040) {
						// Shuriken
						Game.entities.add(new Shuriken(xx*TILE_SIZE, yy*TILE_SIZE, 16, 16, Entity.Shuriken));
					} else if(pixelAtual == 0xFFFF7F7F) {
						// Heart
						Game.entities.add(new Heart(xx*TILE_SIZE, yy*TILE_SIZE, 16, 16, Entity.Heart));
					}//5B0089 - portal
					 //FF6A00 - inimigo 2
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/***/

	/*METODOS*/
	
	public static boolean isFree(int xNext, int yNext) {
		int x1 = xNext / TILE_SIZE;
		int y1 = yNext / TILE_SIZE;
		
		int x2 = (xNext + TILE_SIZE-1) / TILE_SIZE;
		int y2 = yNext / TILE_SIZE;
		
		int x3 = xNext / TILE_SIZE;
		int y3 = (yNext + TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xNext + TILE_SIZE-1) / TILE_SIZE;
		int y4 = (yNext + TILE_SIZE-1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile) || 
				 (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile) ||
				 (tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile) ||
				 (tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile));
	}
	
	public static boolean inPortal(int xNext, int yNext) {
		int x1 = xNext / TILE_SIZE;
		int y1 = yNext / TILE_SIZE;
		
		int x2 = (xNext + TILE_SIZE-1) / TILE_SIZE;
		int y2 = yNext / TILE_SIZE;
		
		int x3 = xNext / TILE_SIZE;
		int y3 = (yNext + TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xNext + TILE_SIZE-1) / TILE_SIZE;
		int y4 = (yNext + TILE_SIZE-1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1 * World.WIDTH)] instanceof PortalTile) || 
				 (tiles[x2 + (y2 * World.WIDTH)] instanceof PortalTile) ||
				 (tiles[x3 + (y3 * World.WIDTH)] instanceof PortalTile) ||
				 (tiles[x4 + (y4 * World.WIDTH)] instanceof PortalTile));
	}
	
	public static void restart(String map) {
		//Inicializando objetos

		Game.entities.clear();
		Game.enemies.clear();
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy>();
		Game.shuri = new ArrayList<ShurikenThrow>();
		Game.shuriw = new ArrayList<ShurikenWall>();
		Game.spritesheet = new SpriteSheet("/spritesheet.png");
		
		//Inicializando o player
		Game.player = new Player(0, 0, 16, 16, Game.spritesheet.getSprite(80, 16, 16, 16));
		Game.entities.add(Game.player);
		Game.world = new World("/"+map);
		Player.ammo = 0;
		return;
	}
	
	public void render(Graphics g) {
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;
		
		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
	
	/***/

	
	
	
}
