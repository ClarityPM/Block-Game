package com.physh_media.blogspot.terraingame;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Assets 
{
	public static Image stoneBlock;
	public static Image grassBlock;
	public static Image dirtBlock;
	
	public void setUp() throws SlickException
	{
		grassBlock = new Image("sprite/GrassParticle.png");
		dirtBlock = new Image("sprite/StoneParticle.png");
		stoneBlock = new Image("sprite/DirtParticle.png");
	}
}
