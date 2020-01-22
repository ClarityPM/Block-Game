package com.physh_media.blogspot.terraingame;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

public class Player
{
	int x = 0;
	int y = 800;

	Rectangle top_hitbox;
	Rectangle right_hitbox;
	Rectangle bottom_hitbox;
	Rectangle left_hitbox;
	
	Rectangle body_hitbox;
	
	ArrayList<String> passable_blocks = new ArrayList<String>();
	
	// Horizontal and vertical velocities
	int h_velocity = 0 ;
	int v_velocity = 0;
	boolean limitVelocity = false;
	
	// Class representing the player's stats and hitbox
	public Player()
	{
		top_hitbox = new Rectangle((30*32)+1, (15*32), 30, 2);
		right_hitbox = new Rectangle(30*32, (15*32)+1, 2, 62);
		bottom_hitbox = new Rectangle((30*32)+1, 17*32, 30, 2);
		left_hitbox = new Rectangle((31*32), (15*32), 2, 62);
		
		body_hitbox = new Rectangle(30*32, 15*32, 32, 64);
		
		passable_blocks.add("air");
		passable_blocks.add("leaves");
		passable_blocks.add("wood");
	}
	
	// Returns the player's hitbox in a clockwise fashion
	public Rectangle[] getHitbox()
	{
		Rectangle[] hitbox = {top_hitbox, right_hitbox, bottom_hitbox, left_hitbox};
		return hitbox;
	}
	
	// Draw the player to the screen
	public void render(Graphics graphics, Block[][] world, int cam_x, int cam_y)
	{	
		graphics.setColor(Color.white);
		
		graphics.setLineWidth(1);
		graphics.draw(top_hitbox);
		graphics.draw(bottom_hitbox);
		graphics.draw(left_hitbox);
		graphics.draw(right_hitbox);
		
		graphics.draw(body_hitbox);
	}
	
	// Logic
	public void update(GameContainer container, Block[][] world)
	{
		parseMovement(container, world);
		
		if (world[((y-(y%32))/32)+16][((x-(x%32))/32)+29].getHitbox().intersects(body_hitbox))
		{
			System.out.println("Detected intersection");
		}
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void parseMovement(GameContainer container, Block[][] world)
	{
		// Update our horizontal velocities, to a maximum of then pixels per frame
		if (container.getInput().isKeyDown(Input.KEY_A) && 
			!container.getInput().isKeyDown(Input.KEY_D)) // Handle left movement
		{
			if (//!world[((y-(y%32))/32)+16][((x-(x%32))/32)+29].getHitbox().intersects(left_hitbox) && 
				passable_blocks.contains(world[(y/32)+16][(x/32)+29].getType()))
			{
				// Ensure we can't go into MAXIMUM OVERDRIVE, KRABS (Set an upper limit to our velocity)
				if (h_velocity >= -3)
				{
					h_velocity--;
				}
			}
				
			
		} else if (h_velocity < 0 && !container.getInput().isKeyDown(Input.KEY_D)) {
			h_velocity++;
		}
		
		if (container.getInput().isKeyDown(Input.KEY_D) && 
			!container.getInput().isKeyDown(Input.KEY_A))
		{
			if (//!world[((y-(y%32))/32)+16][((x-(x%32))/32)+31].getHitbox().intersects(right_hitbox) &&
				passable_blocks.contains(world[((y-(y%32))/32)+16][((x-(x%32))/32)+31].getType()))
			{
				if (h_velocity <= 3)
				{
					h_velocity++;	
				}
			}
			
			
		} else if (h_velocity > 0) {
			h_velocity--;
		}
		
		x = x + h_velocity; // Apply movement
		
		// Handle vertical velocity changes such as jumping and falling
		if (container.getInput().isKeyPressed(Input.KEY_W))
		{
			y = y - 10;
			v_velocity = -20;
		}
		if (v_velocity < 0) v_velocity++;
		
		int x_buffer = ((x-(x%32))/32)+30; // This represents the current X coordinate of the player, use it to check if we're able to fall
		int y_buffer = ((y-(y%32))/32)+17; // Likewise
		
		world[y_buffer][x_buffer].highlighted = true; // Bottom
		world[((y-(y%32))/32)+16][((x-(x%32))/32)+31].highlighted = true;
		world[((y-(y%32))/32)+16][((x-(x%32))/32)+29].highlighted = true;
		if (passable_blocks.contains(world[y_buffer][x_buffer].getType())) // Eventually this needs to be a check if the bottom hitbox intersects a block
		{
			if (!limitVelocity)
			{
				v_velocity++;
				limitVelocity = true;
			} else {
				limitVelocity = false;
			}
			
		} else {
			v_velocity = 0;
		}
		
		y = y + v_velocity; // Apply movement
		
	}
}
