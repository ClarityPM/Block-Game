package com.physh_media.blogspot.terraingame.structure;

public class Tower extends Structure
{
	public Tower()
	{
		super(10, 25);
	}
	
	@Override
	void setUp()
	{
		// Stone base
		for (int y = 0; y < 25; y++)
		{
			for (int x = 0; x < 10; x++)
			{
				buffer[y][x] = "stone";
			}
		}
		
		// Carve out hollows
		for (int i = 0; i < 5; i++)
		{
			for (int y = 0; y< 4; y++) {
				for (int x = 1; x < 9; x++)
				{
					buffer[(y+1)+(i*5)][x] = "air";
				}
			}
		}
		
	}

}
