package com.physh_media.blogspot.terraingame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Launcher 
{
	public static void main(String[] args)
	{
		try {
			AppGameContainer game = new AppGameContainer(new Game("2D game test"));
			
			game.setDisplayMode(1920, 1080, false);
			game.setTargetFrameRate(60);
			game.setShowFPS(false);
			game.setFullscreen(true);
			game.setVSync(true);
			
			game.start();
		} catch (SlickException error) {}
	}
}
