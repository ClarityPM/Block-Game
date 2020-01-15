package com.physh_media.blogspot.terraingame;

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
	
	// Horizontal and vertical velocities
	int h_velocity = 0 ;
	int v_velocity = 0;
	boolean limitVelocity = false;
	
	// Class representing the player's stats and hitbox
	public Player()
	{
		top_hitbox = new Rectangle((30*32)+1, (15*32), 30, 1);
		right_hitbox = new Rectangle(30*32, (15*32)+1, 1, 62);
		bottom_hitbox = new Rectangle((30*32)+1, 17*32, 30, 1);
		left_hitbox = new Rectangle((31*32), (15*32), 1, 62);
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
	}
	
	// Logic
	public void update(GameContainer container, Block[][] world)
	{
		parseMovement(container, world);
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
		if (container.getInput().isKeyDown(Input.KEY_A) && world[((y-(y%32))/32)+16][((x-(x%32))/32)+29].getType().equals("air")) // Handle left movement
		{
			// Ensure we can't go into MAXIMUM OVERDRIVE, KRABS (Set an upper limit to our velocity)
			if (h_velocity >= -3)
			{
				h_velocity--;
			}
		} else if (h_velocity < 0 && !container.getInput().isKeyDown(Input.KEY_D)) {
			h_velocity++;
		}
		if (container.getInput().isKeyDown(Input.KEY_D) && !container.getInput().isKeyDown(Input.KEY_A) && world[((y-(y%32))/32)+16][((x-(x%32))/32)+31].getType().equals("air"))
		{
			if (h_velocity <= 3)
			{
				h_velocity++;	
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
		if (world[y_buffer][x_buffer].getType().equals("air")) // Eventually this needs to be a check if the bottom hitbox intersects a block
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
