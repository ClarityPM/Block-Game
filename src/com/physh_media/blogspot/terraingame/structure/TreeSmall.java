package com.physh_media.blogspot.terraingame.structure;

public class TreeSmall extends Structure
{
	public TreeSmall()
	{
		super(7, 8);
	}
	
	@Override 
	void setUp()
	{
		// Air base
		for (int y = 0; y < 8; y++)
		{
			for (int x = 0; x < 7; x++)
			{
				buffer[y][x] = "air";
			}
		}
		
		// Leaves
		for (int iy = 0; iy < 8; iy++)
		{
			if (iy <= 1)
			{
				for (int ix = 1; ix < 6; ix++)
				{
					buffer[iy][ix] = "leaves";
				}
			} else if (iy > 1 && iy < 5) {
				for (int ix = 0; ix < 7; ix++)
				{
					buffer[iy][ix] = "leaves";
				}
			}
		}
		
		for (int iy = 1; iy < 8; iy++)
		{
			buffer[iy][3] = "wood";
		}
	}
}
