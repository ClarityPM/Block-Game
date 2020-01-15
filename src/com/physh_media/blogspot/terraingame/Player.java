package com.physh_media.blogspot.terraingame;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

public class Player
{
	int x = 0;
	int y = 1000;

	Rectangle top_hitbox;
	Rectangle right_hitbox;
	Rectangle bottom_hitbox;
	Rectangle left_hitbox;
	
	// Horizontal and vertical velocities
	int h_velocity = 0 ;
	int v_velocity = 0;
	
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
		
		//int x_buffer = ((x+16-(x%32))/32)+30; // This represents the current X coordinate of the player
	}
	
	// Logic
	public void update(GameContainer container)
	{
		parseMovement(container);
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void parseMovement(GameContainer container)
	{
		// Update our horizontal velocities, to a maximum of then pixels per frame
		if (container.getInput().isKeyDown(Input.KEY_A)) // Handle left movement
		{
			// Ensure we can't go into MAXIMUM OVERDRIVE, KRABS (Set an upper limit to our velocity)
			if (h_velocity >= -3)
			{
				h_velocity--;
			}
		} else if (h_velocity < 0 && !container.getInput().isKeyDown(Input.KEY_D)) {
			h_velocity++;
		}
		if (container.getInput().isKeyDown(Input.KEY_D))
		{
			if (h_velocity <= 3)
			{
				h_velocity++;	
			}
			
		} else if (h_velocity > 0 && !container.getInput().isKeyDown(Input.KEY_A)) {
			h_velocity--;
		}
		
		x = x + h_velocity;
		
		// Handle vertical velocity changes such as jumping and falling
		if (container.getInput().isKeyPressed(Input.KEY_W))
		{
			v_velocity = -12;
		}
		if (v_velocity < 0) v_velocity++;
		
		// Handle falling, for now we fall when S is pressed, like the horizontal movement but with no upper bound...
		if (container.getInput().isKeyDown(Input.KEY_S)) // Eventually this needs to be a check if the bottom hitbox intersects a block
		{
			v_velocity++;
		}
		
		y = y + v_velocity;
		
	}
}
