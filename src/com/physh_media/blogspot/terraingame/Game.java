package com.physh_media.blogspot.terraingame;

import java.awt.Font;
import java.util.Random;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import com.physh_media.blogspot.terraingame.structure.Tower;
import com.physh_media.blogspot.terraingame.structure.TreeSmall;

public class Game extends BasicGame
{
	public Game(String title) {super(title);}

	Block[][] world = new Block[100][1000];
	
	Random random = new Random();
	
	int camX = 0;
	int camY = 1000;
	
	boolean inventory = false;
	Color guiColor = new Color(200,200,200,150);
	
	boolean attackGui = false;
	int selectedAttack = 0;
	
	int goffset_x, goffset_y, remainder_x, remainder_y, mouse_x, mouse_y;
	
	int min_visible, max_visible;
	
	MessageBlock mb;
	
	float[][] heightmap = new float[100][1000];
	FastNoise fn = new FastNoise();
	
	Image paralaxBackground;
	
	boolean showMap = false;
	
	TrueTypeFont verdana12;
	TrueTypeFont verdana24;
	
	Player player = new Player();
 	
	@Override
	public void render(GameContainer container, Graphics graphics) throws SlickException
	{
		// World
		for (int y = 0; y < 100; y++)
		{
			for (int x = 0; x < 1000; x++)
			{
				if (x >= min_visible && x <= max_visible)
					world[y][x].render(graphics, world, camX, camY, container, mb);
			}
		}
		
		// Entities
		player.render(graphics, world, camX, camY);
		
		if (inventory)
		{
			for (int y = 0; y < 5; y++)
			{
				for (int x = 0; x < 10; x++) 
				{
					graphics.setColor(guiColor);
					graphics.fillRoundRect(30+(x*100), 30+(y*100), 85, 85, 5);
					
					graphics.setColor(Color.white);
					graphics.drawString(((y*10)+x)+1 + "", 40+(x*100), 40+(y*100));
				}
			}
		}
		
		// Attacks GUI
		if (attackGui)
		{
			graphics.setColor(new Color(20,20,20,100));
			graphics.fillRect(860, 240, 200, 600);
			graphics.fillRect(660, 440, 200, 200);
			graphics.fillRect(1060, 440, 200, 200);
			
			graphics.setLineWidth(2);
			graphics.setColor(Color.white);
			
			if (selectedAttack == 0) {
				graphics.drawRect(860, 240, 200, 200);
			} else if (selectedAttack == 1) {
				graphics.drawRect(1060, 440, 200, 200);
			} else if (selectedAttack == 2) {
				graphics.drawRect(860, 640, 200, 200);
			} else {
				graphics.drawRect(660, 440, 200, 200);
			}
		}
		
		
		// Minimap
		if (showMap)
		{
			for (int y = 0; y < 100; y++) {
				for (int x = 0; x < 1000; x++)
				{
					if (world[y][x].getType().equals("air")) {
						graphics.setColor(new Color(80,80,220));
					} else if (world[y][x].getType().equals("grass")) {
						graphics.setColor(Color.green);
					} else if (world[y][x].getType().equals("dirt")) {
						graphics.setColor(new Color(80, 30, 10));
					} else {
						graphics.setColor(Color.darkGray);
					}
					graphics.fillRect(570+(x*2), 50+(y*2), 2, 2);
				}
			}
		}
		
		mb.render(graphics, container);
	}

	@Override
	public void init(GameContainer container) throws SlickException
	{
		verdana12 = new TrueTypeFont(new Font("font/VERDANA.TTF", Font.TRUETYPE_FONT, 12), true);
		verdana24 = new TrueTypeFont(new Font("font/VERDANA.TTF", Font.TRUETYPE_FONT, 24), true);
		
		mb = new MessageBlock(Color.white, verdana24);
		
		generate();
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException
	{
		// Calculate the visible range
		// Min = world[y][(cam_x(cam_x%32))/32]
		// Max = world[y](60+(cam_x(cam_x%32))/32)]
		min_visible = (camX-(camX%32))/32;
		max_visible = 60+((camX-(camX%32))/32);
		
		
		if (container.getInput().isKeyDown(Input.KEY_ESCAPE)) System.exit(0);
		
		if (container.getInput().isKeyPressed(Input.KEY_TAB))
		{
			if (inventory)
			{
				inventory = false;
			} else {
				inventory = true;
			}
		}
		if (container.getInput().isKeyPressed(Input.KEY_F3))
		{
			if (showMap) 
			{
				showMap = false;
			} else {
				showMap = true;
			}
		}
		if (container.getInput().isKeyDown(Input.KEY_LSHIFT))
		{
			attackGui = true;
			
			if (container.getInput().isKeyPressed(Input.KEY_W)) {
				mb.push("<Info> A generic attack.");
				selectedAttack = 0;
			}
			if (container.getInput().isKeyPressed(Input.KEY_D)) {
				mb.push("<Info> A generic attack.");
				selectedAttack = 1;
			}
			if (container.getInput().isKeyPressed(Input.KEY_S)) {
				mb.push("<Info> A generic attack.");
				selectedAttack = 2;
			}
			if (container.getInput().isKeyPressed(Input.KEY_A)) {
				mb.push("<Info> A generic attack.");
				selectedAttack = 3;
			}
		} else {
			attackGui = false;
			
			if (container.getInput().isKeyDown(Input.KEY_W)) camY = camY - 5;
			if (container.getInput().isKeyDown(Input.KEY_S)) camY = camY + 5;
			if (container.getInput().isKeyDown(Input.KEY_A)) camX = camX - 5;
			if (container.getInput().isKeyDown(Input.KEY_D)) camX = camX + 5;
		}
		
		
	}
	
	//
	// Misc. methods
	//
	public int[] getGrassBlock()
	{
		int[] toReturn = {0, 0};
		
		int targetx = random.nextInt(1000);
		for (int i = 0; i < 100; i++)
		{
			if (world[i][targetx].getType().equals("grass"))
			{
				toReturn[0] = targetx;
				toReturn[1] = i;
				
				return toReturn;
			}
		}
		
		return getGrassBlock(); // Yay recursion
	}

	public void generate()
	{
		long start = System.currentTimeMillis();
		
		for (int y = 0; y < 100; y++)
		{
			for (int x = 0; x < 1000; x++)
			{
				// Fill the world with air
				world[y][x] = new Block(x, y, "air");
			}
		}
		
		fn.SetSeed((int) System.currentTimeMillis()); 
		fn.SetFrequency((float) 0.033); // For good terrain generation
		//fn.SetFrequency((float) 0.99); // For odd terrain
		
		// Terrain heightmap
		for (int y = 0; y < 100; y++) {
			for (int x = 0; x < 1000; x++)
			{
				heightmap[y][x] = fn.GetPerlin(x, y);
			}
		}
		
		// Create a line of grass
		// Seed the first tile, then use our terrain heightmap to get 
		int buffer;
		int[] grassBuffer = new int[1000];
		
		for (int x = 0; x < 1000; x++)
		{
			buffer = 45+Math.round((15*Math.abs(heightmap[0][x])));
			world[buffer][x] = new Block(x, buffer, "grass");
			grassBuffer[x] = buffer;
		}
		
		// Take the list of grass tiles and generate dirt & stone below it all
		for (int i = 0; i < 1000; i++)
		{
			for (int b = grassBuffer[i]+1; b < 100; b++)
			{
				world[b][i] = new Block(i, b, "dirt");
			}
		}
		
		for (int y = 70; y < 100; y++)
		{
			for (int x = 0; x < 1000; x++)
			{
				world[y][x] = new Block(x, y, "stone");
			}
		}
		
		// Put our trees
		int[] tree_buffer;
		for (int i  = 0; i < 100; i++)
		{
			tree_buffer = getGrassBlock();
			new TreeSmall().generate(tree_buffer[0]-3, tree_buffer[1]-8, world);
		}
		
		//new Tower().generate(5, 20, world);
		
		mb.push("Generation took " + (System.currentTimeMillis()-start) + " ms");
	}
}
