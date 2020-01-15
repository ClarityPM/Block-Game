package com.physh_media.blogspot.terraingame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class Player
{
	int x, y;

	Rectangle top_hitbox;
	Rectangle right_hitbox;
	Rectangle bottom_hitbox;
	Rectangle left_hitbox;
	
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
	
	boolean isBottomIntersecting;
	// Draw the player to the screen
	public void render(Graphics graphics, Block[][] world, int cam_x, int cam_y)
	{
		isBottomIntersecting = false;
		for (int iy = 0; iy < 100; iy++) {
			for (int ix = 0; ix < 1000; ix++)
			{
				if (world[iy][ix].doesPlayerIntersect(this).equals("bottom") && !world[iy][ix].getType().equals("air"))
				{
					isBottomIntersecting = true;
				}
			}
		}
		
		if (!isBottomIntersecting)
		{
			y++;
		}
		
		graphics.setColor(Color.white);
		
		graphics.setLineWidth(1);
		graphics.draw(top_hitbox);
		graphics.draw(bottom_hitbox);
		graphics.draw(left_hitbox);
		graphics.draw(right_hitbox);
	}
	
	
}
