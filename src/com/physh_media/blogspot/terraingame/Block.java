package com.physh_media.blogspot.terraingame;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleSystem;

public class Block
{
	int x, y;
	int depth;
	
	String type;
	String backtype;
	
	boolean highlighted;
	
	Color color;
	int colorOffset = 0;
	
	Rectangle rect;
	Random random = new Random();
	
	ParticleSystem particle;
	ParticleEmitter pm;
	Image particle_sprite;
	
	public Block(int x, int y, String type)
	{
		this.x = x;
		this.y = y;
		
		this.type = type;
		
		if (random.nextInt(10) == 0)
		{
			colorOffset = random.nextInt(10);
		}
		
		// Define our backing type
		if (y >= 65)
		{
			backtype = "solid";
		} else {
			backtype = "air";
		}
		
		rect = new Rectangle(x, y, x+32, y+32);
		
		if (type.equals("grass")) {
			particle_sprite = Assets.grassBlock;
		} else if (type.equals("dirt")) {
			particle_sprite = Assets.dirtBlock;
		} else {
			particle_sprite = Assets.stoneBlock;
		}
		
		particle = new ParticleSystem(particle_sprite, 16);
	}
	
	int darkModifier;
	int[] darkBuffer;
	public void render(Graphics graphics, Block[][] world, int offset_x, int offset_y, GameContainer container, String replace_type)
	{		
		// Update our hitbox
		if (!rect.equals(new Rectangle((x*32)-offset_x, (y*32)-offset_y, 32, 32)))
		{
			rect = new Rectangle((x*32)-offset_x, (y*32)-offset_y, 32, 32);
			particle.setPosition((x*32)-offset_x, (y*32)-offset_y);
		}
		
		// Check if the box contains the cursor
		if (rect.contains(container.getInput().getAbsoluteMouseX(), container.getInput().getAbsoluteMouseY())) 
		{
			if (container.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) 
			{
				breakBlock();
			} else if (container.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
				setType(replace_type);
			}
			highlighted = true;
		}
		
		// Find our depth for use in shading
		if (type.equals("stone") || type.equals("dirt") || type.equals("leaves") || type.equals("wood"))
		{
			darkBuffer = findDepth(world);
			
			if (darkBuffer[1] == 0) {
				darkModifier = 9*darkBuffer[0];
			} else {
				darkModifier = 18*darkBuffer[0];
			}
			
			//darkModifier = 0*findDepth(world); // To disable shading
		}
		
		// Draw
		if (type.equals("stone")) {
			color = new Color(nonZeroSub(100,darkModifier,colorOffset),nonZeroSub(100,darkModifier,colorOffset),nonZeroSub(100,darkModifier,colorOffset));
		} else if (type.equals("dirt")) {
			color = new Color(nonZeroSub(100,darkModifier,colorOffset),nonZeroSub(50, darkModifier,colorOffset),nonZeroSub(10, darkModifier,colorOffset));
		} else if (type.equals("grass")) {
			color = new Color(50-colorOffset,100-colorOffset,50-colorOffset);
		} else if (type.equals("leaves")) {
			color = new Color(nonZeroSub(23,darkModifier,colorOffset), nonZeroSub(87,darkModifier,colorOffset), nonZeroSub(4,darkModifier,colorOffset));
		} else if (type.equals("wood")) {
			color = new Color(134,94,69);
		} else {
			if (backtype.equals("air"))
			{
				color = new Color(100,100,240);
			} else {
				if (y > 70) {
					color = new Color(40, 40, 40);
				} else {
					color = new Color(50, 30, 20);
				}
				
			}
			
		}
		
		
		if (!highlighted) {
			graphics.setColor(color);
		} else {
			graphics.setColor(Color.white);
		}
		
		graphics.fillRect(x*32-offset_x, (y*32)-offset_y, 32, 32);
		
		highlighted = false;
		
		graphics.setColor(Color.white);
		//graphics.draw(rect); // Shows us our world grid
		
	}
	
	// Handle the breaking of a block
	public void breakBlock()
	{
		setType("air");
		
	}
	
	public int[] findDepth(Block[][] world)
	{
		int[] toReturn = {0,0};
		for (int i = 0; i < 100; i++)
		{
			try {
				if (world[y-i][x].getType().equals("air") ||
						world[y-i][x].getType().equals("wood") ||
						world[y-i][x].getType().equals("leaves")
				)
				{
					toReturn[0] = i;
					if (world[y-i][x].backtype.equals("air")) {
						toReturn[1] = 0;
					} else {
						toReturn[1] = 1;
					}
					break;
				}
			} catch (ArrayIndexOutOfBoundsException error) {
				toReturn[0] = 0;
				toReturn[1] = 0;
				return toReturn;
			}
			
		}
		return toReturn;
	}
	
	public String getType()
	{
		return type;
	}
	
	// Used for shading
	public int nonZeroSub(int value1, int value2)
	{
		if (value1-value2 < 0) {
			return 0;
		} else return (value1-value2);
	}
	
	public int nonZeroSub(int value1, int value2, int value3)
	{
		if (value1-value2-value3 < 0) {
			return 0;
		} else return (value1-value2-value3);
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public Rectangle getHitbox()
	{
		return rect;
	}
	
	public String doesPlayerIntersect(Player target)
	{
		if (target.getHitbox()[0].intersects(rect)) {
			return "top";
		} else if (target.getHitbox()[1].intersects(rect)) {
			return "right";
		} else if (target.getHitbox()[2].intersects(rect)) {
			return "bottom";
		} else if (target.getHitbox()[3].intersects(rect)) {
			return "left";
		}
		return "none";
	}
	
	public boolean isPassable()
	{
		if (type.equals("air") || type.equals("leaves") || type.equals("wood"))
		{
			return true;
		} else {
			return false;
		}
	}
}


















