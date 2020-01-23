package com.physh_media.blogspot.terraingame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

public class Enemy 
{
	boolean targeted = false;
	int x, y;
	
	Rectangle hitbox;
	Circle targeting_reticle;
	Color purple = new Color(255,10,255);
	
	public Enemy(int start_x, int start_y)
	{
		x = start_x;
		y = start_y;
		
		hitbox = new Rectangle(x-16, y, 32, 64);
		targeting_reticle = new Circle(x-16, y, 32);
	}
	
	public void render(Graphics graphics, int camoffset_x, int camoffset_y)
	{
		hitbox.setLocation(x-camoffset_x, y-camoffset_y);
		targeting_reticle.setLocation((x-16)-camoffset_x, y-camoffset_y);
		
		graphics.setColor(Color.red);
		graphics.draw(hitbox);
		
		if (targeted)
		{
			graphics.setLineWidth(5);
			graphics.setColor(purple);
			graphics.draw(targeting_reticle);
		}
	}
	
	public void update(Block[][] world, Player player, MessageBlock mb)
	{
		//mb.push("" + Math.abs(x-player.getX()));
		if (getDistance(player.getX(), player.getY()) < 200) 
		{
			targeted = true;
		} else {
			targeted = false;
		}
		world[(y/32)+2][x/32].highlighted = true;
		
		if (world[(y/32)+2][x/32].isPassable())
		{
			y = y + 2;
		}
	}
	
	public int getDistance(int target_x, int target_y)
	{
		return (int) Math.round (
						Math.sqrt (
							(Math.abs(x-target_x)^2)+(Math.abs(y-target_y)^2)
						)
					);
	}
}
