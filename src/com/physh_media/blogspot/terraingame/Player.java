package com.physh_media.blogspot.terraingame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class Player
{
	int x, y;
	Rectangle hitbox;
	
	public Player()
	{
		hitbox = new Rectangle(30*32, 15*32, 32, 64);
	}
	
	public Rectangle getHitbox()
	{
		return hitbox;
	}
	
	public void render(Graphics graphics, Block[][] world)
	{
		graphics.setColor(Color.white);
		graphics.draw(hitbox);
	}
}
